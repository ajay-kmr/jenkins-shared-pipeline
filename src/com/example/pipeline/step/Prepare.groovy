package com.example.pipeline.step

import com.example.pipeline.dto.ResponseDTO
import com.example.pipeline.dto.SharedProperties
import com.example.pipeline.enums.BuildStatus

class Prepare extends PipeLineStepImpl {

    Prepare(SharedProperties sharedProperties) {
        super(sharedProperties, 'any', 'docker', 'Prepare')
    }

    @Override
    ResponseDTO run() {
        sharedProperties.jenkinsScript.echo " sharedProperties.jenkinsScript.echo:- Inside Run method of Prepare stage:- com.example.pipeline.step.Prepare.run"
        script.echo "script.echo:- Inside Run method of Prepare stage:- com.example.pipeline.step.Prepare.run"
//        commonCommands.jenkinsScript.echo " commonCommands.jenkinsScript.echo:- Inside Run method of Prepare stage:- com.example.pipeline.step.Prepare.run"

        ResponseDTO<String> responseDTO = new ResponseDTO<>(status: true, message: "Dummy Message")
//        script.node(nodeName) {
        script.stage(stageName) {
            script.echo "Inside Prepare stage"
//                checkout scm
            gitCheckout()
            collectGitProperties()
            collectGradleProperties()
            if (!isEligibleForBuild()) {
                script.echo("This build is not eligible for deployment.. Aborting....")
                buildStatus = BuildStatus.ABORTED
                script.error("Aborting")
            }
        }
//        }
        return responseDTO
    }

    private void gitCheckout() {
        script.echo "Inside gitCheckout method:- com.example.pipeline.step.Prepare.gitCheckout"
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


    }

    private void collectGradleProperties() {
        String applicationName = (script.sh(returnStdout: true, script: "./gradlew -q properties | grep name") as String).split(' ')[1]
        gradleProperties.applicationName = applicationName

        String version = (script.sh(returnStdout: true, script: "./gradlew -q properties | grep version") as String).split(' ')[1]
        if (version && version.toUpperCase() != 'SPECIFIED') {
            gradleProperties.version = applicationName
        } else {
            Map gradlePropertiesAsMap = script.readProperties file: "gradle.properties"
            gradleProperties.version = gradlePropertiesAsMap['version']
        }
    }

    private boolean isEligibleForBuild() {
        //TODO:- Add condition if this commit is eligible for build.
        //Eg:- build should not proceed, If it is triggered by Jenkins job after successful build to update the version
        // Certain branch/user can be excluded from build
        return true
    }
}
