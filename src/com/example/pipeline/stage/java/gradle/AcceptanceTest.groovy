package com.example.pipeline.stage.java.gradle

import com.example.pipeline.enums.Stage
import com.example.pipeline.enums.StageStatus
import com.example.pipeline.model.ResponseDetails
import com.example.pipeline.model.SharedProperties
import com.example.pipeline.stage.PipeLineStageImpl

class AcceptanceTest extends PipeLineStageImpl<String> {

    AcceptanceTest(SharedProperties sharedProperties) {
        super(sharedProperties, 'any', 'master', Stage.ACCEPTANCE_TEST)
    }

    @Override
    ResponseDetails<String> run() {
        ResponseDetails<String> responseDTO = new ResponseDetails<>(status: false, message: "Stage ${stage} failed")
        script.node {
            script.stage(stageName) {
                script.echo "Running stage ${stageName}.."
                //Trigger a separate Job for Acceptance Testing for current service
                // Using propagate: false will ensure that the pipeline continue even on job failure
                // propagate: false doesn't mean that the current execution will not wait for the result.
                // It only means that if acceptance test job fail then current execution will not have any impact

                String appName = sharedProperties.buildRequestDetails.applicationName
                def acceptanceTestingJob = script.build(
                        job: "acceptance-testing-job-name",
                        parameters: [
                                script.string(name: "serviceName", value: appName),
                                script.string(name: "tags", value: "not @ignore and @Automated")
                        ],
                        propagate: false
                )

                String acceptanceTestUrl = acceptanceTestingJob.absoluteUrl

                script.echo "Acceptance Test result:- ${acceptanceTestingJob.result}"
                script.echo "Acceptance Test URL:- ${acceptanceTestUrl}"

                //If acceptance Test fails then send notification through email and print the output
                if (acceptanceTestingJob.result == "FAILURE" || acceptanceTestingJob.result == "UNSTABLE") {
                    script.echo "Acceptance Test fails but continuing further pipeline execution..."
                    //TODO:- Send the mail
                }

                script.stash stageName
                responseDTO.stashName = stageName
                stageStatus = StageStatus.SUCCESS
            }
        }
        responseDTO.status = true
        responseDTO.message = "Stage ${stage} completed successfully"
        return responseDTO
    }
}