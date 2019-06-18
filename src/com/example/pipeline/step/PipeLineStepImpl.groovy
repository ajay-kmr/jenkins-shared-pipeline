package com.example.pipeline.step

import com.example.pipeline.dto.BuildRequestDTO
import com.example.pipeline.dto.SharedProperties
import com.example.pipeline.enums.BuildStatus

abstract class PipeLineStepImpl implements IPipeLineStep {

//    @Delegate
    CommonCommands commonCommands

//    @Delegate
    SharedProperties sharedProperties
    def script

    String agentName
    String nodeName
    String stageName

    PipeLineStepImpl(SharedProperties sharedProperties, String agentName, String nodeName, String stageName) {
        this.sharedProperties = sharedProperties
        this.script = sharedProperties.jenkinsScript
        this.commonCommands = new CommonCommands(jenkinsScript: script)
        this.agentName = agentName
        this.nodeName = nodeName
        this.stageName = stageName
    }

    def executeAndReturnStdOutput(String command) {
        script.echo "At com.example.pipeline.step.PipeLineStepImpl.executeAndReturnStdOutput"
        execute(command, true)
    }

    def execute(String command, boolean returnStdOut = false) {
        script.echo "Executing shell script command at com/example/pipeline/step/PipeLineStepImpl.groovy:33"
        script.echo command
        script.echo returnStdOut.toString()
        script.sh(script: command, returnStdout: returnStdOut)
    }

/** Common Properties --STARTS-- **/

    BuildRequestDTO getBuildRequestDTO() {
        return sharedProperties.buildRequestDTO
    }

    void setBuildRequestDTO(BuildRequestDTO buildRequestDTO) {
        sharedProperties.buildRequestDTO = buildRequestDTO
    }

    BuildStatus getBuildStatus() {
        return sharedProperties.buildStatus
    }

    void setBuildStatus(BuildStatus buildStatus) {
        sharedProperties.buildStatus = buildStatus
    }

    SharedProperties.GitProperties getGitProperties() {
        return sharedProperties.gitProperties
    }

    void setGitProperties(SharedProperties.GitProperties gitProperties) {
        sharedProperties.gitProperties = gitProperties
    }

    SharedProperties.GradleProperties getGradleProperties() {
        return sharedProperties.gradleProperties
    }

    void setGradleProperties(SharedProperties.GradleProperties gradleProperties) {
        sharedProperties.gradleProperties = gradleProperties
    }
/** Common Properties --ENDS-- **/
}