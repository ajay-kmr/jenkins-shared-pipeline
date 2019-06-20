package com.example.pipeline.stage.java.gradle

import com.example.pipeline.enums.Stage
import com.example.pipeline.enums.StageStatus
import com.example.pipeline.model.ResponseDetails
import com.example.pipeline.model.SharedProperties
import com.example.pipeline.stage.PipeLineStageImpl

class Conclude extends PipeLineStageImpl<String> {

    Conclude(SharedProperties sharedProperties) {
        super(sharedProperties, 'any', 'master', Stage.CONCLUDE)
    }

    @Override
    ResponseDetails<String> run() {
        ResponseDetails<String> responseDTO = new ResponseDetails<>(status: false, message: "Stage ${stage} failed")
        script.node {
            script.stage(stageName) {
                script.echo "Running stage ${stageName}.."

                /**
                 * Create a file called ${environmentName}-approval.txt if not exist
                 * Update this Approval file with the details eg:-
                 *      1) The Person who approve the this build
                 *      2) Build Time
                 *      3) Branch Name
                 *      4) Deployment Region etc
                 *
                 *  Add this file to git
                 *  git add ${environmentName}-approval.txt
                 *
                 *  Commit this file
                 *  Push this file
                 *
                 *  Send a mail to stakeholders and other developers about the deployment status
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