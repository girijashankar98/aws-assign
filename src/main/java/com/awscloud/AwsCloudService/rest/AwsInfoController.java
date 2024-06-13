package com.awscloud.AwsCloudService.rest;

import com.awscloud.AwsCloudService.service.AWSBatchService;
import com.awscloud.AwsCloudService.service.EC2andS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/awsInfo")
public class AwsInfoController {

    @Autowired
    EC2andS3Service ec2andS3Service;

    @Autowired
    AWSBatchService awsBatchService;


    @PostMapping("/discover")
    public void discoverServices(@RequestBody List<String> services) throws InterruptedException {
        ec2andS3Service.getAllEC2andS3Instances(services);
    }

    @GetMapping("/job/{jobId}/status")
    public String getJobStatus(@PathVariable String jobId) {
        return awsBatchService.getJobStatus(jobId);
    }

    @GetMapping("/discoveryResult/{service}")
    List<String> getDiscoveryResult(@PathVariable String service) {
        return ec2andS3Service.getDiscoveryResult(service);
    }

    @GetMapping("/buckets/{bucketName}/objects")
    public String getS3BucketObjects(@PathVariable String bucketName) {
        return ec2andS3Service.getS3BucketObjects(bucketName);
    }

    @GetMapping("/buckets/{bucketName}/objectCount")
    public int getS3BucketObjectCount(@PathVariable String bucketName) {
        return ec2andS3Service.getS3BucketObjectCount(bucketName);
    }

    @GetMapping("/buckets/{bucketName}/objectLike")
    public List<String> getS3BucketObjectLike(
            @PathVariable String bucketName,
            @RequestParam(name = "pattern") String pattern) {
        return ec2andS3Service.getS3BucketObjectLike(bucketName, pattern);
    }
}
