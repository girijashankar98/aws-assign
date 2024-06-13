package com.awscloud.AwsCloudService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultResponse {

    private int code;
    private String message;
    private String description;
    private String status;
}
