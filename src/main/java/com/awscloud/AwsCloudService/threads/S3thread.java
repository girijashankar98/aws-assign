package com.awscloud.AwsCloudService.threads;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.awscloud.AwsCloudService.entity.S3Bucket;
import com.awscloud.AwsCloudService.repo.S3BucketRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class S3thread extends Thread{
    @Autowired
    AmazonS3 amazonS3;

    @Autowired
    S3BucketRepo s3BucketRepo;

    @Override
    public void run() {
        List<Bucket> buckets = amazonS3.listBuckets();

        List<S3Bucket> s3Buckets = buckets.stream()
                .map(bucket -> {
                    S3Bucket s3Bucket = new S3Bucket();
                    s3Bucket.setBucketName(bucket.getName());
                    return s3Bucket;
                })
                .collect(Collectors.toList());

        s3BucketRepo.saveAll(s3Buckets);
    }
}
