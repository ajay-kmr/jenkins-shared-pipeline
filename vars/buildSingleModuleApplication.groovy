import com.example.pipeline.model.BuildRequestDetails
import com.example.pipeline.stage.runner.PipeLineStageRunner

def call(Closure buildConfig) {
    echo "******************** Building project using script:- buildSingleModuleApplication ********************"
    BuildRequestDetails buildRequest = BuildRequestDetails.getInstance(buildConfig)
    echo "BuildRequestDetails:- "
    echo buildRequest.toString()
    PipeLineStageRunner.run(this, buildRequest)
}