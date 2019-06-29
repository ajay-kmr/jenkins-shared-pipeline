package com.example.pipeline.stage.java.gradle

import com.example.pipeline.enums.Stage
import com.example.pipeline.enums.StageStatus
import com.example.pipeline.model.ResponseDetails
import com.example.pipeline.model.SharedProperties
import com.example.pipeline.stage.PipeLineStageImpl

class GenerateReport extends PipeLineStageImpl<String> {

    GenerateReport(SharedProperties sharedProperties) {
        super(sharedProperties, 'any', 'master', Stage.GENERATE_REPORT)
    }

    @Override
    ResponseDetails<String> run() {
        ResponseDetails<String> responseDTO = new ResponseDetails<>(status: false, message: "Stage ${stage} failed")
        script.node {
            script.stage(stageName) {
                script.echo "Running stage ${stageName}.."
                /**
                 * Generate the various report eg Test Report and add to Jenkins dashboard
                 */
                // Refer @Link {https://www.oreilly.com/library/view/jenkins-2-up/9781491979587/ch04.html}

                generateUnitTestResult()
                generateIntegrationTestResult()

                responseDTO.stashName = stageName
                stageStatus = StageStatus.SUCCESS
            }
        }
        responseDTO.status = true
        responseDTO.message = "Stage ${stage} completed successfully"
        return responseDTO
    }

    /**
     * Refer @Link {https://www.oreilly.com/library/view/jenkins-2-up/9781491979587/ch04.html}*
     *
     * Typically, like a notification, we may want this step to run at the end of the build.
     * And we may want it to run regardless of whether the build succeeded (especially if we have it set up to link to
     * the last successful build).
     * We can add it to a notifications stage in a try-catch-finally section for a Scripted Pipeline or a post stage
     * for a Declarative Pipeline.
     *
     * Note that here we are unstashing content because it was produced on separate nodes running in a parallel step:
     **/
    private void generateUnitTestResult() {
        /*Note that here we are unstashing content because it was produced on separate nodes running in a parallel step:*/
//        script.unstash 'unit-test-reports'
//        script.unstash Stage.BUILD.displayName

        script.publishHTML(target: [
                allowMissing         : false,
                alwaysLinkToLastBuild: false,
                keepAll              : true,
                //TODO:- Add the path of first Module test report directory and files
//                        reportDir            : 'api/build/reports/test',
                reportDir            : 'build/reports/tests/test',
                reportFiles          : 'index.html',
                reportName           : "Unit Testing Results"
        ])
    }

    private void generateIntegrationTestResult() {
        /*Note that here we are unstashing content because it was produced on separate nodes running in a parallel step:
        * For now we are using single node, So commenting it out
        * */
//        script.unstash 'integration-test-reports'
//        script.unstash Stage.BUILD.displayName

        script.publishHTML(target: [
                allowMissing         : false,
                alwaysLinkToLastBuild: false,
                keepAll              : true,
                //TODO:- Add the path of first Module test report directory and files
//                        reportDir            : 'api/build/reports/test',
                reportDir            : 'build/reports/tests/test',
                reportFiles          : 'index.html',
                reportName           : "Integration Testing Results"
        ])
    }
}