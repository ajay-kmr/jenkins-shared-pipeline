import com.example.pipeline.enums.Stage
import com.example.pipeline.model.BuildRequestDetails
import com.example.pipeline.stage.runner.PipeLineStageRunner

def call(Closure buildConfig) {
    echo "******************** Building project using script:- buildLibrary ********************"

    BuildRequestDetails buildRequest = BuildRequestDetails.getInstance(buildConfig)
    if (!buildRequest.stages) {
        buildRequest.stages = [
                Stage.PREPARE,
                Stage.BUILD,
                Stage.FORTIFY,
//            Stage.ACCEPTANCE_TEST,
//            Stage.SONAR_CHECK,
                Stage.GENERATE_REPORT,
                Stage.PUBLISH_ARTIFACT,
        ]
    }
    PipeLineStageRunner.run(this, buildRequest)
}