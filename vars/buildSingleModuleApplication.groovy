import com.example.pipeline.enums.Stage
import com.example.pipeline.model.BuildRequestDetails
import com.example.pipeline.model.SharedProperties
import com.example.pipeline.stage.IPipeLineStage
import com.example.pipeline.stage.PipeLineStageRunner
import com.example.pipeline.stage.Prepare
import com.example.pipeline.stage.java.gradle.*

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
        sharedProperties.buildRequestDetails.stages = [
                Stage.PREPARE,
                Stage.BUILD,
                Stage.FORTIFY,
//                Stage.ACCEPTANCE_TEST,
//                Stage.SONAR_CHECK,
                Stage.GENERATE_REPORT,
                Stage.RELEASE,
                Stage.DEPLOY,
                Stage.CONCLUDE,
        ]
    }
    List<IPipeLineStage> pipeLineSteps = []
    sharedProperties.buildRequestDetails.stages.collect { stage ->
        switch (stage) {
            case Stage.PREPARE: pipeLineSteps.add(new Prepare(sharedProperties))
                break
            case Stage.BUILD: pipeLineSteps.add(new Build(sharedProperties))
                break
            case Stage.FORTIFY: pipeLineSteps.add(new Fortify(sharedProperties))
                break
            case Stage.ACCEPTANCE_TEST: pipeLineSteps.add(new AcceptanceTest(sharedProperties))
                break
            case Stage.SONAR_CHECK: pipeLineSteps.add(new Sonar(sharedProperties))
                break
            case Stage.GENERATE_REPORT: pipeLineSteps.add(new GenerateReport(sharedProperties))
                break
            case Stage.RELEASE: pipeLineSteps.add(new Release(sharedProperties))
                break
            case Stage.DEPLOY: pipeLineSteps.add(new Deploy(sharedProperties))
                break
            case Stage.CONCLUDE: pipeLineSteps.add(new Conclude(sharedProperties))
                break
            default:
                throw new IllegalArgumentException("No action defined for ${stage}")
        }
    }
    pipeLineSteps
}