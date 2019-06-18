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
        echo "Inside Run method of Prepare stage:- com.example.pipeline.step.Prepare.run"

        ResponseDTO<String> responseDTO = new ResponseDTO<>(status: true, message: "Dummy Message")
        node(nodeName) {
            stage(stageName) {
                echo "Inside Prepare stage"
//                checkout scm
                gitCheckout()
                collectGitProperties()
                collectGradleProperties()
                if (!isEligibleForBuild()) {
                    echo("This build is not eligible for deployment.. Aborting....")
                    buildStatus = BuildStatus.ABORTED
                    error("Aborting")
                }
            }
        }
        return responseDTO
    }

    private void gitCheckout() {
        execute("git checkout ${branchName}")
    }

    private void collectGitProperties() {
        gitProperties.remoteOriginUrl = executeAndReturnStdOutput("git config --get remote.origin.url")
        //Refer:- @link{https://git-scm.com/docs/pretty-formats}
        gitProperties.committerName = executeAndReturnStdOutput("git log -1 --pretty=format:'%an'")
        gitProperties.committerEmail = executeAndReturnStdOutput("git log -1 --pretty=format:'%ae'")
        gitProperties.commitDate = executeAndReturnStdOutput("git log -1 --pretty=format:'%ad'")
        gitProperties.commitMessage = executeAndReturnStdOutput("git log -1 --format='%B'")


    }

    private void collectGradleProperties() {
        String applicationName = (executeAndReturnStdOutput("./gradlew -q properties | grep name") as String).split(' ')[1]
        gradleProperties.applicationName = applicationName

        String version = (executeAndReturnStdOutput("./gradlew -q properties | grep version") as String).split(' ')[1]
        if (version && version.toUpperCase() != 'SPECIFIED') {
            gradleProperties.version = applicationName
        } else {
            Map gradlePropertiesAsMap = readProperties file: "gradle.properties"
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
