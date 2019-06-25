package com.example.pipeline.stage.java.gradle

import com.example.pipeline.enums.Stage
import com.example.pipeline.enums.StageStatus
import com.example.pipeline.model.ResponseDetails
import com.example.pipeline.model.SharedProperties
import com.example.pipeline.stage.PipeLineStageImpl

class PublishArtifact extends PipeLineStageImpl<String> {

    PublishArtifact(SharedProperties sharedProperties) {
        super(sharedProperties, 'any', 'master', Stage.PUBLISH_ARTIFACT)
    }

    @Override
    ResponseDetails<String> run() {
        ResponseDetails<String> responseDTO = new ResponseDetails<>(status: false, message: "Stage ${stage} failed")
        script.node {
            script.stage(stageName) {
                script.echo "Running stage ${stageName}.."

                /**
                 * Checkout Git Project
                 * Publish the jar or war to Artifactory
                 * Eg ./gradlew publishToMavenLocal command to publish the artifact to local maven repository
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