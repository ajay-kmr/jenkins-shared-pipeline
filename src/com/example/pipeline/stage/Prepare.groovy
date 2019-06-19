package com.example.pipeline.stage

import com.example.pipeline.enums.Stage
import com.example.pipeline.enums.StageStatus
import com.example.pipeline.model.ResponseDetails
import com.example.pipeline.model.SharedProperties

class Prepare extends PipeLineStageImpl<String> {

    Prepare(SharedProperties sharedProperties) {
        super(sharedProperties, 'any', 'master', Stage.PREPARE)
    }

    @Override
    ResponseDetails<String> run() {
        ResponseDetails<String> responseDTO = new ResponseDetails<>(status: true, message: "Dummy Message")
//        script.node(nodeName) {
        script.node {
            script.stage(stage) {
                script.echo "Running stage ${stage}..".toString()
                gitCheckout()
                collectGitProperties()
                collectGradleProperties()
                if (!isEligibleForBuild()) {
                    script.echo("This build is not eligible for deployment.. Aborting....")
                    stageStatus = StageStatus.ABORTED
                    script.error("Aborting")
                }
            }
        }
        return responseDTO
    }

    private void gitCheckout() {
        script.echo "Inside gitCheckout method:- com.example.pipeline.stage.Prepare.gitCheckout"

        script.checkout script.scm

        String currentBranchName = script.sh(returnStdout: true, script: "git branch")
        script.echo "Current branch name from git checkout command:-"
        script.echo currentBranchName

        script.echo "script.BRANCH_NAME:- "
        script.echo script.BRANCH_NAME

        if (currentBranchName != script.BRANCH_NAME) {
            script.sh "git checkout ${script.BRANCH_NAME}"
        }
    }

    private void collectGitProperties() {
        gitProperties.remoteOriginUrl = script.sh(returnStdout: true, script: "git config --get remote.origin.url")
        //Refer:- @link{https://git-scm.com/docs/pretty-formats}
        gitProperties.committerName = script.sh(returnStdout: true, script: "git log -1 --pretty=format:'%an'")
        gitProperties.committerEmail = script.sh(returnStdout: true, script: "git log -1 --pretty=format:'%ae'")
        gitProperties.commitDate = script.sh(returnStdout: true, script: "git log -1 --pretty=format:'%ad'")
        gitProperties.commitMessage = script.sh(returnStdout: true, script: "git log -1 --format='%B'")

        script.echo "The collected Git Properties are:-"
        script.echo gitProperties.toString()
    }

    private void collectGradleProperties() {
        String applicationName = (script.sh(returnStdout: true, script: "./gradlew -q properties | grep name") as String).split(' ')[1]
        gradleProperties.applicationName = applicationName

        String version = (script.sh(returnStdout: true, script: "./gradlew -q properties | grep version") as String).split(' ')[1]
        if (version && version.toUpperCase() != 'SPECIFIED') {
            gradleProperties.version = version
        } else {
            Map gradlePropertiesAsMap = script.readProperties file: "gradle.properties"
            gradleProperties.version = gradlePropertiesAsMap['version']
        }

        script.echo "The collected Gradle Properties are:-"
        script.echo gradleProperties.toString()
    }

    private boolean isEligibleForBuild() {
        boolean isEligible = true
        //TODO:- Add condition if this commit is eligible for build.
        //Eg:- build should not proceed, If it is triggered by Jenkins job after successful build to update the version
        // Certain branch/user can be excluded from build
        script.echo "Is eligible to continue further with build process:- "
        script.echo isEligible.toString()
        return isEligible
    }
}
