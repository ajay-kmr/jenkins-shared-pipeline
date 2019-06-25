package com.example.pipeline.stage.runner

import com.example.pipeline.enums.BuildTool
import com.example.pipeline.enums.StageStatus
import com.example.pipeline.factory.GradlePipeLineStageFactory
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
        PipeLineStageFactory factory = new PipeLineStageFactory()
        sharedProperties.jenkinsScript.echo "Created instance of PipeLineStageFactory:- ${factory}"
//        this.pipeLineSteps = factory.createPipeLineStages(sharedProperties)
        this.pipeLineSteps = createPipeLineStages(sharedProperties)
        this.script.echo this?.sharedProperties?.buildRequestDetails?.toString() ?: "Unable to evaluate:- buildRequestDetails "
    }

    /**
     * TODO:- Need to remove below method
     *
     */

    List<IPipeLineStage> createPipeLineStages(SharedProperties sharedProperties) {
        sharedProperties.jenkinsScript.echo "Creating pipeline stages"
        List<IPipeLineStage> pipeLineStageList = []

        BuildTool currentBuildTool = sharedProperties?.buildRequestDetails?.buildTool
        if (!currentBuildTool) {
            sharedProperties.jenkinsScript.echo "No BuildTool specified. Possible values are ${BuildTool.values()}"
//            throw new IllegalArgumentException("No BuildTool specified. Possible values are ${BuildTool.values()}")
        }
        switch (currentBuildTool) {
            case BuildTool.GRADLE: pipeLineStageList = GradlePipeLineStageFactory.getPipeLineStages(sharedProperties)
                break
            default:
                sharedProperties.jenkinsScript.echo "No Build mechanism defined yet for ${currentBuildTool}"
//                throw new IllegalArgumentException("No Build mechanism defined yet for ${currentBuildTool}")
        }
        pipeLineStageList
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
