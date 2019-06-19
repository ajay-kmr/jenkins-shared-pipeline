import com.example.pipeline.enums.Stage
import com.example.pipeline.model.BuildRequestDetails
import com.example.pipeline.model.SharedProperties
import com.example.pipeline.stage.IPipeLineStage
import com.example.pipeline.stage.PipeLineStageRunner
import com.example.pipeline.stage.Prepare
import com.example.pipeline.stage.java.gradle.Build

def call(Closure buildConfig) {
    echo "******************** Building project using script:- buildSingleModuleApplication ********************"
    BuildRequestDetails buildRequest = BuildRequestDetails.getInstance(buildConfig)
    SharedProperties sharedProperties = new SharedProperties(this, buildRequest)

    echo "Starting the pipeline. The various properties configured are:- "
    echo sharedProperties.buildRequestDetails.toString()

    List<IPipeLineStage> pipeLineSteps = getPipeLineSteps(sharedProperties)
    PipeLineStageRunner stepRunner = new PipeLineStageRunner(this, pipeLineSteps, sharedProperties)
    stepRunner.run()
}

private static List<IPipeLineStage> getPipeLineSteps(SharedProperties sharedProperties) {
    if (!sharedProperties?.buildRequestDetails?.stages) {
        //If Application has not defined any Stage, then define the default one
        sharedProperties.buildRequestDetails.stages = [Stage.PREPARE, Stage.BUILD]
    }
    List<IPipeLineStage> pipeLineSteps = []
    sharedProperties.buildRequestDetails.stages.collect { stage ->
        switch (stage) {
            case Stage.PREPARE: pipeLineSteps.add(new Prepare(sharedProperties))
                break
            case Stage.BUILD: pipeLineSteps.add(new Build(sharedProperties))
                break
            default:
                throw new IllegalArgumentException("No action defined for ${stage}")
        }
    }
    pipeLineSteps
}