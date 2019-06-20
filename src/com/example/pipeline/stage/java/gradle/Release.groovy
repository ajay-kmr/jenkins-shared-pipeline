package com.example.pipeline.stage.java.gradle

import com.example.pipeline.enums.Stage
import com.example.pipeline.enums.StageStatus
import com.example.pipeline.model.ResponseDetails
import com.example.pipeline.model.SharedProperties
import com.example.pipeline.stage.PipeLineStageImpl

class Release extends PipeLineStageImpl<String> {

    Release(SharedProperties sharedProperties) {
        super(sharedProperties, 'any', 'master', Stage.RELEASE)
    }

    @Override
    ResponseDetails<String> run() {
        ResponseDetails<String> responseDTO = new ResponseDetails<>(status: false, message: "Stage ${stage} failed")
        script.node {
            script.stage(stageName) {
                script.echo "Running stage ${stageName}.."

                /**
                 * Checkout Git Project
                 *
                 * Gradle's docker plugin does not support credentials to push to docker hub.
                 * So need to login first
                 * Login to Docker to upload the image of current build
                 *
                 * Use ./gradlew release command to release to docker hub
                 *
                 */

//                script.stash name: stageName, useDefaultExcludes: false
                responseDTO.stashName = stageName
                stageStatus = StageStatus.SUCCESS
            }
        }
        responseDTO.status = true
        responseDTO.message = "Stage ${stage} completed successfully"
        return responseDTO
    }
}