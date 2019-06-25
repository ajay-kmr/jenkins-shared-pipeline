package com.example.pipeline.notification

import com.example.pipeline.model.EmailNotificationRequest
import com.example.pipeline.model.ResponseDetails

class NotificationService {
    ResponseDetails<String> sendEmail(EmailNotificationRequest request) {
        // https://www.oreilly.com/library/view/jenkins-2-up/9781491979587/ch04.html
        def script = request.script
        script.emailext(
                subject: "${request.buildStatus}: Job ${script.env.JOB_NAME} ([${script.env.BUILD_NUMBER})",
                body: """
       Check console output at <a href="${script.env.BUILD_URL}">
${script.env.JOB_NAME} (${script.env.BUILD_NUMBER})</a>
""",
                to: "${script.BUILD_USER_EMAIL}",
                from: 'jenkins@company.com')
    }

    /**
     * Send notifications based on build status string
     */
    def sendEmail2(script, String buildStatus = 'STARTED') {
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

    def slackSend(script, String buildStatus = 'STARTED') {
        //https://www.oreilly.com/library/view/jenkins-2-up/9781491979587/ch04.html
        def subject = "${buildStatus}: Job '${script.env.JOB_NAME} [${script.env.BUILD_NUMBER}]'"
        def summary = "${subject} (${script.env.BUILD_URL})"
        def (colorCode, color) = getColorAndColorCode(buildStatus)
        script.slackSend(color: colorCode, message: summary)
    }

    def hipchatSend(script, String buildStatus = 'STARTED') {
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