package com.awscloud.AwsCloudService.repo;

import com.awscloud.AwsCloudService.entity.S3Bucket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface S3BucketRepo extends MongoRepository<S3Bucket,String> {
    int countByBucketName(String bucketName);
}
