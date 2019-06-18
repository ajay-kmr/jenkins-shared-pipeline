package com.example.pipeline.step

import com.example.pipeline.enums.BuildStatus

class PipeLineStepRunner {
    List<PipeLineStep> pipeLineSteps
    def script

    PipeLineStepRunner(script, List<PipeLineStep> pipeLineSteps) {
        this.pipeLineSteps = pipeLineSteps
        this.script = script
    }

    void run() {
        script.echo "Inside PipeLineStepRunner"
        try {
            pipeLineSteps.each {
                //TODO:- Add pre run activities after each run
                script.echo "Before running ${it.stageName}"
                it.run()
                script.echo "After running ${it.stageName}"
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
