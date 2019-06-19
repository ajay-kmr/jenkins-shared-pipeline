package com.example.pipeline.stage

import com.example.pipeline.enums.StageStatus
import com.example.pipeline.model.ResponseDetails
import com.example.pipeline.model.SharedProperties

class PipeLineStageRunner {
    final List<IPipeLineStage> pipeLineSteps
    final def script
    final SharedProperties sharedProperties

    PipeLineStageRunner(script, final List<IPipeLineStage> pipeLineSteps, final SharedProperties sharedProperties) {
        this.pipeLineSteps = pipeLineSteps
        this.script = script
        this.sharedProperties = sharedProperties
    }

    void run() {
        script.echo "Inside PipeLineStepRunner"
        try {
            pipeLineSteps.each { step ->
                //TODO:- Add pre run activities after each run
                script.echo "Before running stage:- ${step.stage.displayName}"
                ResponseDetails responseDetails = step.run()
                sharedProperties.stageOutput.put(step.stage, responseDetails)
                script.echo "After running stage:- ${step.stage.displayName}"
                //TODO:- Add post run activities after each stage
            }
            script.echo "The overall status of pipeline execution"
            sharedProperties.stageOutput?.each { stageResult ->
                script.echo "${stageResult.key} ::: ${stageResult.value.status}"
            }
        } catch (InterruptedException ignored) {
            ignored.printStackTrace()
            script.currentBuild.result = StageStatus.SUCCESS
        } catch (Exception ignored) {
            ignored.printStackTrace()
            if (script.env.ABORT) {
                script.currentBuild.result = StageStatus.ABORTED
            } else {
                script.currentBuild.result = StageStatus.FAILURE
            }
        }
    }
}
