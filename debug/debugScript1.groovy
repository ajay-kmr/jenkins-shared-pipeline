@Library('buildSingleModuleApplication@code-cleanup') _

import com.example.pipeline.enums.BuildTool
import com.example.pipeline.enums.Stage
import com.example.pipeline.factory.PipeLineStageFactory
import com.example.pipeline.model.BuildRequestDetails
import com.example.pipeline.model.SharedProperties
import com.example.pipeline.stage.IPipeLineStage

/**
 * Steps to Debug:-
 *      Step 1:- Open Jenkins
 *      Step 2:- Select the Jenkins Job that you want to debug
 *      Step 3:- Select any build from build history which got pass/fail that you want to debug
 *      Step 4:- Click on- Replay
 *      Step 5:- Copy paste below code and Click on Run button to see the output
 * @param sharedProperties
 * @return
 */
//******************

List<IPipeLineStage> getPipeLineStages(SharedProperties sharedProperties) {
    setDefaultPipeLineStagesIfNotDefined(sharedProperties)
    List<IPipeLineStage> pipeLineSteps = sharedProperties.buildRequestDetails.stages.collect { stage ->
        getPipeLineStage(stage, sharedProperties)
    }
//        Collections.unmodifiableList(pipeLineSteps)
    pipeLineSteps
}

IPipeLineStage getPipeLineStage(Stage stage, SharedProperties sharedProperties) {
    switch (stage) {
        case Stage.PREPARE: return new Prepare(sharedProperties)
        case Stage.BUILD: return new Build(sharedProperties)
        case Stage.FORTIFY: return new Fortify(sharedProperties)
        case Stage.ACCEPTANCE_TEST: return new AcceptanceTest(sharedProperties)
        case Stage.SONAR_CHECK: return new Sonar(sharedProperties)
        case Stage.GENERATE_REPORT: return new GenerateReport(sharedProperties)
        case Stage.PUBLISH_ARTIFACT: return new PublishArtifact(sharedProperties)
        case Stage.RELEASE: return new Release(sharedProperties)
        case Stage.DEPLOY: return new Deploy(sharedProperties)
        case Stage.CONCLUDE: return new Conclude(sharedProperties)
        default:
            throw new IllegalArgumentException("No action defined for ${stage}");
    }
}


void setDefaultPipeLineStagesIfNotDefined(SharedProperties sharedProperties) {
    if (!sharedProperties?.buildRequestDetails?.stages) {
        //If Application has not defined any Stage, then define the default one
        sharedProperties.buildRequestDetails.stages = [
                Stage.PREPARE,
                Stage.BUILD,
                Stage.FORTIFY,
//                Stage.ACCEPTANCE_TEST,
//                Stage.SONAR_CHECK,
                Stage.GENERATE_REPORT,
                Stage.PUBLISH_ARTIFACT,
                Stage.RELEASE,
                Stage.DEPLOY,
                Stage.CONCLUDE,
        ]
        sharedProperties.jenkinsScript.echo "No stages ware defined. So using defaults:-"
        sharedProperties.jenkinsScript.echo "${sharedProperties.buildRequestDetails.stages}"
    }
}

//******************

def buildConfig = {
    applicationName = "Hello applicationName for testing"
    serviceType = "Hello serviceType for testing"
    deploymentRegion = "Hello deploymentRegion for testing"
}

BuildRequestDetails buildRequest = BuildRequestDetails.getInstance(buildConfig)

echo "Hello " + buildRequest.toString()

def sharedProperties = new SharedProperties(this, buildRequest)

sharedProperties.jenkinsScript.echo "Hello Echo this.sharedProperties.jenkinsScript"

BuildTool currentBuildTool = sharedProperties?.buildRequestDetails?.buildTool

echo "Current Build tool is " + currentBuildTool

final PipeLineStageFactory factory = new PipeLineStageFactory()

//final List<IPipeLineStage> pipeLineSteps = factory.createPipeLineStages(sharedProperties)

//GradlePipeLineStageFactory.getPipeLineStages(sharedProperties)

final List<IPipeLineStage> pipeLineSteps = getPipeLineStages(sharedProperties)
/**
 def stages = pipeLineSteps?.collect{step->
 step.getStage()}**/
//echo stages