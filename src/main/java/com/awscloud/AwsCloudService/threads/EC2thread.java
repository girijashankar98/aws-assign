package com.awscloud.AwsCloudService.threads;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.awscloud.AwsCloudService.entity.EC2Instance;
import com.awscloud.AwsCloudService.repo.Ec2InstanceRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class EC2thread extends Thread{

    @Autowired
    private AmazonEC2 amazonEC2;

    @Autowired
    private Ec2InstanceRepo ec2InstanceRepo;

    @Override
    public void run() {
            DescribeInstancesRequest request = new DescribeInstancesRequest();
            DescribeInstancesResult result = amazonEC2.describeInstances(request);

            List<EC2Instance> instances = result.getReservations().stream()
                .flatMap(reservation -> reservation.getInstances().stream())
                .map(instance -> {
                    EC2Instance ec2Instance = new EC2Instance();
                    ec2Instance.setInstanceId(instance.getInstanceId());
                    return ec2Instance;
                })
                .collect(Collectors.toList());

            ec2InstanceRepo.saveAll(instances);
    }
}
