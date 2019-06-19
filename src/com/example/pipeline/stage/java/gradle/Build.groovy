package com.example.pipeline.stage.java.gradle

import com.example.pipeline.enums.StageStatus
import com.example.pipeline.enums.Stage
import com.example.pipeline.model.ResponseDetails
import com.example.pipeline.model.SharedProperties
import com.example.pipeline.stage.PipeLineStageImpl

class Build extends PipeLineStageImpl<String> {

    Build(SharedProperties sharedProperties) {
        super(sharedProperties, 'any', 'master', Stage.BUILD)
    }

    @Override
    ResponseDetails<String> run() {
        ResponseDetails<String> responseDTO = new ResponseDetails<>(status: false, message: "Stage ${stage} failed")
        script.node {
            script.stage(stage) {
                script.echo "Running stage ${stage}.."
                stageStatus = StageStatus.SUCCESS
            }
        }
        responseDTO.status = true
        responseDTO.message = "Stage ${stage} completed successfully"
        return responseDTO
    }
}