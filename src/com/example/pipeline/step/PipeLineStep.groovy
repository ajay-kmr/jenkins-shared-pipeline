package com.example.pipeline.step

import com.example.pipeline.dto.ResponseDTO
import com.example.pipeline.dto.SharedProperties

abstract class PipeLineStep {

    @Delegate
    CommonCommands commonCommands

    @Delegate
    SharedProperties sharedProperties

    String agentName
    String nodeName
    String stageName

    PipeLineStep(SharedProperties sharedProperties, String agentName, String nodeName, String stageName) {
        this.sharedProperties = sharedProperties
        this.commonCommands = new CommonCommands(jenkinsScript: sharedProperties.jenkinsScript)
        this.agentName = agentName
        this.nodeName = nodeName
        this.stageName = stageName
    }

    abstract ResponseDTO run();


    def executeAndReturnStdOutput(String command) {
        execute(command, true)
    }

    def execute(String command, boolean returnStdOut = false) {
        sh(script: command, returnStdout: returnStdOut)
    }
}