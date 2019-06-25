
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

@Library('buildSingleModuleApplication@code-cleanup') _
import com.example.pipeline.model.SharedProperties;
import com.example.pipeline.factory.PipeLineStageFactory;
import com.example.pipeline.model.BuildRequestDetails;
import com.example.pipeline.stage.IPipeLineStage;
import com.example.pipeline.enums.BuildTool;
import com.example.pipeline.factory.GradlePipeLineStageFactory;
import com.example.pipeline.stage.*;
import com.example.pipeline.stage.java.gradle.*;
import com.example.pipeline.enums.Stage;


//******************

List<IPipeLineStage> getPipeLineStages(SharedProperties sharedProperties) {
    setDefaultPipeLineStagesIfNotDefined(sharedProperties)
    List<IPipeLineStage> pipeLineSteps = sharedProperties.buildRequestDetails.stages.collect { stage ->
        getPipeLineStage(stage, sharedProperties)
    }
//        Collections.unmodifiableList(pipeLineSteps)

    sharedProperties.jenkinsScript.echo "Pipeline steps created successfully"
    pipeLineSteps.each{st->
        sharedProperties.jenkinsScript.echo  "Hello:-  " + st.getStage()
    }

    pipeLineSteps
}

IPipeLineStage getPipeLineStage(Stage stage, SharedProperties sharedProperties) {
    IPipeLineStage res = null;
    switch (stage) {
        case Stage.PREPARE: res= new Prepare(sharedProperties); break;
        case Stage.BUILD: res= new Build(sharedProperties); break;
        case Stage.FORTIFY: res= new Fortify(sharedProperties); break;
        case Stage.ACCEPTANCE_TEST: res= new AcceptanceTest(sharedProperties); break;
        case Stage.SONAR_CHECK: res= new Sonar(sharedProperties); break;
        case Stage.GENERATE_REPORT: res= new GenerateReport(sharedProperties); break;
        case Stage.PUBLISH_ARTIFACT: res= new PublishArtifact(sharedProperties); break;
        case Stage.RELEASE: res= new Release(sharedProperties); break;
        case Stage.DEPLOY: res= new Deploy(sharedProperties); break;
        case Stage.CONCLUDE: res= new Conclude(sharedProperties); break;
        default:
            throw new IllegalArgumentException("No action defined for ${stage}");
    }
    sharedProperties.jenkinsScript.echo "The PipeLineStage is:- "+res;
    sharedProperties.jenkinsScript.echo "** The PipeLineStage is:- "+res.getStage();
    res
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
    applicationName="Hello applicationName for testing"
    serviceType="Hello serviceType for testing"
    deploymentRegion="Hello deploymentRegion for testing"
}

BuildRequestDetails buildRequest = BuildRequestDetails.getInstance(buildConfig)

echo "Hello "+buildRequest.toString()

def sharedProperties = new SharedProperties(this, buildRequest)

sharedProperties.jenkinsScript.echo "Hello Echo this.sharedProperties.jenkinsScript"

BuildTool currentBuildTool = sharedProperties?.buildRequestDetails?.buildTool

echo "Current Build tool is "+currentBuildTool

final PipeLineStageFactory factory = new PipeLineStageFactory()



//final List<IPipeLineStage> pipeLineSteps = factory.createPipeLineStages(sharedProperties)


//GradlePipeLineStageFactory.getPipeLineStages(sharedProperties)

final List<IPipeLineStage> pipeLineSteps = getPipeLineStages(sharedProperties)

pipeLineSteps?.each{step->
    sharedProperties.jenkinsScript.echo "Last:- " + step.getStage()

}