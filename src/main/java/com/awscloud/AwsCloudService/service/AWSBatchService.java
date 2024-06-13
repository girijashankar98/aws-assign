package com.awscloud.AwsCloudService.service;

import com.amazonaws.services.batch.AWSBatch;
import com.amazonaws.services.batch.model.DescribeJobsRequest;
import com.amazonaws.services.batch.model.DescribeJobsResult;
import com.amazonaws.services.batch.model.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AWSBatchService {

    @Autowired
    private AWSBatch awsBatch;

    public String getJobStatus(String jobId) {
        DescribeJobsRequest request = new DescribeJobsRequest()
                .withJobs(jobId);

        DescribeJobsResult result = awsBatch.describeJobs(request);
        JobDetail jobDetail = result.getJobs().get(0);

        return jobDetail.getStatus();
    }
}
