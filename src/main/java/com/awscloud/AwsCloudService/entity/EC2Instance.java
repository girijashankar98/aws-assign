package com.awscloud.AwsCloudService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("ec2_instance")
public class EC2Instance {

    private String id;

    private String instanceId;
}
