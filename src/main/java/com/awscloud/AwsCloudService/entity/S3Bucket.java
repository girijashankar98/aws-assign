package com.awscloud.AwsCloudService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("s3_bucket")
public class S3Bucket {

    @Id
    private String id;

    private String bucketName;

    private String objectKey;
}
