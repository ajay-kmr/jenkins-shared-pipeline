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
        try {
            pipeLineSteps.each {
                //TODO:- Add pre run activities after each run
                it.run()
                //TODO:- Add post run activities after each step
            }
        } catch (InterruptedException ignored) {
            script.currentBuild.result = BuildStatus.SUCCESS
        } catch (Exception ignored) {
            if (script.env.ABORT) {
                script.currentBuild.result = BuildStatus.ABORTED
            } else {
                script.currentBuild.result = BuildStatus.FAILURE
            }
        }
    }
}
