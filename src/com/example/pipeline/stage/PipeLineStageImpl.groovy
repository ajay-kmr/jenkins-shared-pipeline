package com.example.pipeline.stage

import com.example.pipeline.enums.BuildStatus
import com.example.pipeline.enums.Stage
import com.example.pipeline.model.GitProperties
import com.example.pipeline.model.GradleProperties
import com.example.pipeline.model.SharedProperties

abstract class PipeLineStageImpl<T> implements IPipeLineStage<T> {
    SharedProperties sharedProperties
    def script

    String agentName
    String nodeName
    Stage stage

    PipeLineStageImpl(SharedProperties sharedProperties, String agentName, String nodeName, Stage stage) {
        this.sharedProperties = sharedProperties
        this.script = sharedProperties.jenkinsScript
        this.agentName = agentName
        this.nodeName = nodeName
        this.stage = stage
    }

    def executeAndReturnStdOutput(String command) {
        script.echo "At com.example.pipeline.stage.PipeLineStepImpl.executeAndReturnStdOutput"
        return execute(command, true)
    }

    def execute(String command, boolean returnStdOut = false) {
        script.echo "Executing shell script command at com/example/pipeline/stage/PipeLineStepImpl.groovy:33"
        script.echo command
        script.echo returnStdOut.toString()
//        script.sh(script: command, returnStdout: returnStdOut)
//        script.sh label: '', returnStdout: returnStdOut, script: command
        def commandOutPut = script.sh(encoding: 'UTF-8',
                label: 'Executing-Shell-Script',
                returnStatus: true,
                returnStdout: returnStdOut,
                script: command)

        script.echo commandOutPut
        return commandOutPut
    }

/** Common Properties --STARTS-- **/

    void setBuildStatus(BuildStatus buildStatus) {
        sharedProperties.buildStatus = buildStatus
    }

    GitProperties getGitProperties() {
        return sharedProperties.gitProperties
    }

    GradleProperties getGradleProperties() {
        return sharedProperties.gradleProperties
    }
/** Common Properties --ENDS-- **/
}