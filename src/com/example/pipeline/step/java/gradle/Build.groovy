package com.example.pipeline.step.java.gradle

import com.example.pipeline.enums.BuildStatus
import com.example.pipeline.model.ResponseDetails
import com.example.pipeline.model.SharedProperties
import com.example.pipeline.step.PipeLineStepImpl

class Build extends PipeLineStepImpl {

    Build(SharedProperties sharedProperties) {
        super(sharedProperties, 'any', 'master', Build.class.simpleName)
    }

    @Override
    ResponseDetails run() {
        ResponseDetails<String> responseDTO = new ResponseDetails<>(status: false, message: "Stage ${stageName} failed")
        script.node {
            script.stage(stageName) {
                script.echo "Running stage ${stageName}..".toString()
                buildStatus = BuildStatus.SUCCESS
            }
        }
        responseDTO.status = true
        responseDTO.message = "Stage ${stageName} completed successfully"
        return responseDTO
    }
}