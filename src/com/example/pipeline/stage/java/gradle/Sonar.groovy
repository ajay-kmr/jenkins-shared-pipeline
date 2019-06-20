package com.example.pipeline.stage.java.gradle

import com.example.pipeline.enums.Stage
import com.example.pipeline.enums.StageStatus
import com.example.pipeline.model.ResponseDetails
import com.example.pipeline.model.SharedProperties
import com.example.pipeline.stage.PipeLineStageImpl

class Sonar extends PipeLineStageImpl<String> {

    Sonar(SharedProperties sharedProperties) {
        super(sharedProperties, 'any', 'master', Stage.SONAR_CHECK)
    }

    @Override
    ResponseDetails<String> run() {
        ResponseDetails<String> responseDTO = new ResponseDetails<>(status: false, message: "Stage ${stage} failed")
        script.node {
            script.stage(stageName) {
                script.echo "Running stage ${stageName}.."

                script.unstash Stage.BUILD.displayName

                script.withCredentials([script.string(credentialsId: "sonar_token", variable: "SONAR_TOKEN")]) {
                    script.sh './gradlew -Dsonar.host.url=http://sonar.com:9000 -Dsonar.login=${SONAR_TOKEN} sonar'
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