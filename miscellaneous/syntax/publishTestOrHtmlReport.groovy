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


node {
    stage("generate-report") {
        sh './gradlew clean build'

        /*Note that here we may need for unstashing content because it was produced on separate nodes running in a parallel step:*/
//        script.unstash 'unit-test-reports'


        publishHTML(target: [
                allowMissing         : false,
                alwaysLinkToLastBuild: false,
                keepAll              : true,
                //TODO:- Add the path of first Module test report directory and files
                //                        reportDir            : 'api/build/reports/test',
                reportDir            : 'build/reports/tests/test',
                reportFiles          : 'index.html',
                reportName           : "Unit Testing Results"
        ])

        publishHTML(target: [
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