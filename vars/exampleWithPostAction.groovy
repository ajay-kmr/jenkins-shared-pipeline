import com.example.pipeline.model.BuildRequestDetails
import com.example.pipeline.stage.runner.PipeLineStageRunner

def call(Closure buildConfig) {
    /**
     * @link refer{https://jenkins.io/blog/2017/10/02/pipeline-templates-with-shared-libraries/}
     */
    echo "******************** Building project using script:- buildSingleModuleApplication ********************"
    BuildRequestDetails buildRequest = BuildRequestDetails.getInstance(buildConfig)
//    SharedProperties sharedDetails = new SharedProperties(this, buildRequest)
    echo "BuildRequestDetails:- "
    echo buildRequest.toString()

    pipeline {
        agent any
        stages {
            PipeLineStageRunner.run(this, buildRequest)
        }
        post {
            failure {
                mail to: buildConfig.email, subject: 'Pipeline failed', body: "${env.BUILD_URL}"
            }
        }
    }
}