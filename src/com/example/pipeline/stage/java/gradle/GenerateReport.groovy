package com.example.pipeline.stage.java.gradle

import com.example.pipeline.enums.Stage
import com.example.pipeline.enums.StageStatus
import com.example.pipeline.model.ResponseDetails
import com.example.pipeline.model.SharedProperties
import com.example.pipeline.stage.PipeLineStageImpl

class GenerateReport extends PipeLineStageImpl<String> {

    GenerateReport(SharedProperties sharedProperties) {
        super(sharedProperties, 'any', 'master', Stage.GENERATE_REPORT)
    }

    @Override
    ResponseDetails<String> run() {
        ResponseDetails<String> responseDTO = new ResponseDetails<>(status: false, message: "Stage ${stage} failed")
        script.node {
            script.stage(stageName) {
                script.echo "Running stage ${stageName}.."
                script.unstash Stage.PREPARE.displayName
                /**
                 * Generate the various report eg Test Report and add to Jenkins dashboard
                 */
                script.stash name: stageName, useDefaultExcludes: false
                responseDTO.stashName = stageName
                stageStatus = StageStatus.SUCCESS
            }
        }
        responseDTO.status = true
        responseDTO.message = "Stage ${stage} completed successfully"
        return responseDTO
    }
}