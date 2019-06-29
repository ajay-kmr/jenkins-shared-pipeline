package com.example.pipeline.stage

import com.example.pipeline.enums.Stage
import com.example.pipeline.enums.StageStatus
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

    def stash = { String stashName ->
        script.stash name: stashName, useDefaultExcludes: false
    }

    def unStash = { String stashName ->
        script.unstash stashName
    }

    def echo = { def outputToEcho ->
        script.echo outputToEcho
    }

    def sh = { def commandToExecute ->
        script.sh commandToExecute
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

    String getStageName() {
        this.stage.displayName
    }

    void setStageStatus(StageStatus status) {
        sharedProperties.stageStatus = status
    }

    GitProperties getGitProperties() {
        return sharedProperties.gitProperties
    }

    GradleProperties getGradleProperties() {
        return sharedProperties.gradleProperties
    }
/** Common Properties --ENDS-- **/
}