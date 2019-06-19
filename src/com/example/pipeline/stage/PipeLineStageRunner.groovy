package com.example.pipeline.stage

import com.example.pipeline.enums.BuildStatus
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
                script.echo "Before running stage:- ${step.stage}"
                ResponseDetails responseDetails = step.run()
                sharedProperties.stageOutput.put(step.stage, responseDetails)
                script.echo "After running stage:- ${step.stage}"
                //TODO:- Add post run activities after each stage
            }
        } catch (InterruptedException ignored) {
            ignored.printStackTrace()
            script.currentBuild.result = BuildStatus.SUCCESS
        } catch (Exception ignored) {
            ignored.printStackTrace()
            if (script.env.ABORT) {
                script.currentBuild.result = BuildStatus.ABORTED
            } else {
                script.currentBuild.result = BuildStatus.FAILURE
            }
        }
    }
}
