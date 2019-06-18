package com.example.pipeline.step

import com.example.pipeline.enums.BuildStatus

class PipeLineStepRunner {
    List<IPipeLineStep> pipeLineSteps
    def script

    PipeLineStepRunner(script, List<IPipeLineStep> pipeLineSteps) {
        this.pipeLineSteps = pipeLineSteps
        this.script = script
    }

    void run() {
        script.echo "Inside PipeLineStepRunner"
        try {
            pipeLineSteps.each { step ->
                //TODO:- Add pre run activities after each run
                script.echo "Before running ${step.stageName}"
                step.run()
                script.echo "After running ${step.stageName}"
                //TODO:- Add post run activities after each step
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
