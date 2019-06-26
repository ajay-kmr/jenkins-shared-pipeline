package com.example.pipeline.notification

import com.example.pipeline.enums.StageStatus
import com.example.pipeline.model.ResponseDetails

class NotificationService {
    def script

    ResponseDetails<String> sendEmail() {
        // https://www.oreilly.com/library/view/jenkins-2-up/9781491979587/ch04.html
        //https://wiki.jenkins.io/display/JENKINS/Email-ext+plugin

        script.echo "Sending notification"
        ResponseDetails<String> responseDetails = new ResponseDetails<>(status: false, message: "${StageStatus.FAILURE}")
        String messageBody = """
             Check console output at <a href="${script.env.BUILD_URL}">${script.env.JOB_NAME}:${script.env.BUILD_NUMBER}</a>
                      """

        /**
         * The default subject name can be:-
         *      $PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!
         */

        script.emailext(
                attachLog: true,
                compressLog: true,
                recipientProviders: [script.developers()],
                replyTo: 'no-reply@sharedpipeline.com',
//                subject: "${script.BUILD_STATUS}: Job ${script.env.JOB_NAME} ([${script.env.BUILD_NUMBER})",
                subject: "Sample Test Message",
                body: messageBody,
                to: "${script.BUILD_USER_EMAIL}",
                from: 'jenkins@company.com')

        responseDetails.status = true
        responseDetails.message = "${StageStatus.SUCCESS}"
        responseDetails
    }

    /**
     * Send notifications based on build status string
     */
    def sendEmail2(String buildStatus = 'STARTED') {
        // https://www.oreilly.com/library/view/jenkins-2-up/9781491979587/ch04.html

        // build status of null means successful
        buildStatus = buildStatus ?: 'SUCCESS'

        // Default values
        def color = 'RED'
        def colorCode = '#FF0000'
        def subject = "${buildStatus}: Job '${script.env.JOB_NAME} [${script.env.BUILD_NUMBER}]'"
        def summary = "${subject} (${script.env.BUILD_URL})"
        def details = """<p>${buildStatus}: Job '${script.env.JOB_NAME} [${script.env.BUILD_NUMBER}]':</p>
    <p>Check console output at &QUOT;<a href='${script.env.BUILD_URL}'>${script.env.JOB_NAME} [${
            script.env.BUILD_NUMBER
        }]</a>&QUOT;</p>"""


        (colorCode, color) = getColorAndColorCode(buildStatus)

        // Send notifications
// Use email extension plugin
        script.emailext(
                to: 'bitwiseman@bitwiseman.com',
                subject: subject,
                body: details,
                recipientProviders: [[$class: 'DevelopersRecipientProvider']]
        )
    }

    def slackSend(String buildStatus = 'STARTED') {
        //https://www.oreilly.com/library/view/jenkins-2-up/9781491979587/ch04.html
        def subject = "${buildStatus}: Job '${script.env.JOB_NAME} [${script.env.BUILD_NUMBER}]'"
        def summary = "${subject} (${script.env.BUILD_URL})"
        def (colorCode, color) = getColorAndColorCode(buildStatus)
        script.slackSend(color: colorCode, message: summary)
    }

    def hipchatSend(String buildStatus = 'STARTED') {
        def subject = "${buildStatus}: Job '${script.env.JOB_NAME} [${script.env.BUILD_NUMBER}]'"
        def summary = "${subject} (${script.env.BUILD_URL})"
        def (colorCode, color) = getColorAndColorCode(buildStatus)
        script.hipchatSend(color: color, notify: true, message: summary)
    }

    private static List getColorAndColorCode(String buildStatus) {
        def colorCode, color
// Override default values based on build status
        if (buildStatus == 'STARTED') {
            color = 'YELLOW'
            colorCode = '#FFFF00'
        } else if (buildStatus == 'SUCCESS') {
            color = 'GREEN'
            colorCode = '#00FF00'
        } else {
            color = 'RED'
            colorCode = '#FF0000'
        }
        [colorCode, color]
    }
}
