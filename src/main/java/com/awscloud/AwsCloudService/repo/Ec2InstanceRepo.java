package com.awscloud.AwsCloudService.repo;

import com.awscloud.AwsCloudService.entity.EC2Instance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Ec2InstanceRepo extends MongoRepository<EC2Instance, String> {
}
