package com.awscloud.AwsCloudService.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.awscloud.AwsCloudService.entity.EC2Instance;
import com.awscloud.AwsCloudService.entity.S3Bucket;
import com.awscloud.AwsCloudService.repo.Ec2InstanceRepo;
import com.awscloud.AwsCloudService.repo.S3BucketRepo;
import com.awscloud.AwsCloudService.threads.EC2thread;
import com.awscloud.AwsCloudService.threads.S3thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EC2andS3Service {
    EC2thread ec2thread;
    S3thread s3thread;

    @Autowired
    Ec2InstanceRepo ec2InstanceRepo;
    @Autowired
    S3BucketRepo s3BucketRepo;

    @Autowired
    AmazonS3 amazonS3;

    public void getAllEC2andS3Instances(List<String> services) throws InterruptedException {
        if(services.contains("EC2"))
        ec2thread.run();
        if(services.contains("S3"))
        s3thread.run();
    }

    public List<String> getDiscoveryResult(String service) {
        if(service.equals("EC2"))
            return ec2InstanceRepo.findAll().stream().map(EC2Instance::getInstanceId).collect(Collectors.toList());
        else if(service.equals("S3"))
            return s3BucketRepo.findAll().stream().map(S3Bucket::getBucketName).collect(Collectors.toList());
        else
            throw new IllegalArgumentException("Invalid service");
    }


    public String getS3BucketObjects(String bucketName) {
        List<S3ObjectSummary> objectSummaries = new ArrayList<>();
        ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucketName);
        ListObjectsV2Result result;
        do {
            result = amazonS3.listObjectsV2(request);
            objectSummaries.addAll(result.getObjectSummaries());
            request.setContinuationToken(result.getNextContinuationToken());
        } while (result.isTruncated());

        List<S3Bucket> s3Objects = new ArrayList<>();
        for (S3ObjectSummary summary : objectSummaries) {
            S3Bucket s3Object = new S3Bucket();
            s3Object.setBucketName(bucketName);
            s3Object.setObjectKey(summary.getKey());
            s3Objects.add(s3Object);
        }
        s3BucketRepo.saveAll(s3Objects);
        String jobId = UUID.randomUUID().toString();
        return jobId;
    }

    public int getS3BucketObjectCount(String bucketName) {
        return s3BucketRepo.countByBucketName(bucketName);
    }

    public List<String> getS3BucketObjectLike(String bucketName, String pattern) {
        ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucketName).withPrefix(pattern);
        ListObjectsV2Result result = amazonS3.listObjectsV2(request);

        List<String> objectKeys = new ArrayList<>();
        for (S3ObjectSummary summary : result.getObjectSummaries()) {
            objectKeys.add(summary.getKey());
        }

        return objectKeys;
    }
}
