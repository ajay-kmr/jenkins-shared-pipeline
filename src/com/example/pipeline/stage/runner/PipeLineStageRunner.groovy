package com.example.pipeline.stage.runner

import com.example.pipeline.enums.StageStatus
import com.example.pipeline.factory.PipeLineStageFactory
import com.example.pipeline.model.BuildRequestDetails
import com.example.pipeline.model.ResponseDetails
import com.example.pipeline.model.SharedProperties
import com.example.pipeline.stage.IPipeLineStage

class PipeLineStageRunner {
    final def script
    final SharedProperties sharedProperties
    final List<IPipeLineStage> pipeLineSteps

    private PipeLineStageRunner(script, BuildRequestDetails buildRequest) {
        this.script = script
        this.sharedProperties = new SharedProperties(script, buildRequest)

        this.script.echo "Starting the pipeline. The various properties configured are:- "
        try {
//            this.script.echo sharedProperties?.buildRequestDetails?.toString()
            this.pipeLineSteps = PipeLineStageFactory.createPipeLineStages(sharedProperties)
        } catch (Exception e) {
            e.printStackTrace()
        }

    }

    /**
     * Run the various command available in script with the given buildRequest
     * @param script :- The script having the various command which can be used to
     * runPipeLineStages against the provided request
     * @param buildRequest :- The buildRequest which acts as a parameter to define the
     * various action or condition or input for running the various pipeline stages.
     * The pipeline stages are defined using the commands defined in stages and the input provided by buildRequest
     */
    static void run(script, BuildRequestDetails buildRequest) {
        PipeLineStageRunner stageRunner = new PipeLineStageRunner(script, buildRequest)
        stageRunner.runPipeLineStages()
    }

    private void runPipeLineStages() {
        script.echo "Inside PipeLineStepRunner"
        try {
            pipeLineSteps?.grep()?.each { step ->
                //TODO:- Add pre runPipeLineStages activities after each runPipeLineStages
                script.echo "Before running stage:- ${step.stage.displayName}"
                ResponseDetails responseDetails = step.run()
                sharedProperties.stageOutput.put(step.stage, responseDetails)
                script.echo "After running stage:- ${step.stage.displayName}"
                //TODO:- Add post runPipeLineStages activities after each stage
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
