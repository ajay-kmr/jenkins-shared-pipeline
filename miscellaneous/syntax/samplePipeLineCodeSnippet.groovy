/* ----few snippets of pipeline code that will send the e-mail.---- STARTS ----*/
// https://www.oreilly.com/library/view/jenkins-2-up/9781491979587/ch04.html
// https://code-maven.com/jenkins-send-email-notifications
// https://jenkins.io/blog/2017/02/15/declarative-notifications/
emailext(
        subject: "Job '${env.JOB_NAME} ${env.BUILD_NUMBER}'",
        body: """<p>Check console output at <a href="${env.BUILD_URL}">${env.JOB_NAME}</a></p>""",
        to: "report@code-maven.com",
        from: "jenkins@code-maven.com"
)

// BuildUser plugin and e-mail
def notify(status) {
    wrap([$class: 'BuildUser']) {
        emailext(
                subject: "${status}: Job ${env.JOB_NAME} ([${env.BUILD_NUMBER})",
                body: """
       Check console output at <a href="${env.BUILD_URL}">${env.JOB_NAME} (${env.BUILD_NUMBER})</a>""",
                to: "${BUILD_USER_EMAIL}",
                from: 'jenkins@company.com')
    }
}
/* ----few snippets of pipeline code that will send the e-mail.---- ENDS ----*/

/* ---- Build User Vars Plugin also known as build user vars plugin.---- STARTS ----*/
// https://wiki.jenkins.io/display/JENKINS/Build+User+Vars+Plugin
// https://plugins.jenkins.io/build-user-vars-plugin
// https://code-maven.com/jenkins-pipeline-builduser

pipeline {
    agent none
    stages {
        stage('example') {
            agent { label 'master' }
            steps {
                script {
                    wrap([$class: 'BuildUser']) {
                        echo "BUILD_USER=${BUILD_USER}"
                        echo "BUILD_USER_FIRST_NAME=${BUILD_USER_FIRST_NAME}"
                        echo "BUILD_USER_LAST_NAME=${BUILD_USER_LAST_NAME}"
                        echo "BUILD_USER_ID=${BUILD_USER_ID}"
                        echo "BUILD_USER_EMAIL=${BUILD_USER_EMAIL}"
                        echo "---"
                        echo "env.BUILD_USER=${env.BUILD_USER}"
                        echo "env.BUILD_USER_FIRST_NAME=${env.BUILD_USER_FIRST_NAME}"
                        echo "env.BUILD_USER_LAST_NAME=${env.BUILD_USER_LAST_NAME}"
                        echo "env.BUILD_USER_ID=${env.BUILD_USER_ID}"
                        echo "env.BUILD_USER_EMAIL=${env.BUILD_USER_EMAIL}"
                    }
                }
            }
        }
    }
}
/* ---- Build User Vars Plugin also known as build user vars plugin.---- ENDS ----*/
/* ---- How to list the environment variables available to Jenkins Pipeline.---- STARTS ----*/
// https://code-maven.com/jenkins-pipeline-environment-variables
/* ---- How to list the environment variables available to Jenkins Pipeline.---- ENDS ----*/

