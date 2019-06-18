package com.example.pipeline.step

import com.example.pipeline.dto.BuildRequestDTO
import com.example.pipeline.dto.ResponseDTO
import com.example.pipeline.dto.SharedProperties
import com.example.pipeline.enums.BuildStatus

abstract class PipeLineStep {

//    @Delegate
    CommonCommands commonCommands

//    @Delegate
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


    /** Common Commands --STARTS-- **/
    def getJenkinsScript() {
        return commonCommands.jenkinsScript
    }

    void setJenkinsScript(jenkinsScript) {
        commonCommands.jenkinsScript = jenkinsScript
    }

    def getNode() {
        return commonCommands.node
    }

    void setNode(node) {
        commonCommands.node = node
    }

    def getStage() {
        return commonCommands.stage
    }

    void setStage(stage) {
        commonCommands.stage = stage
    }

    def getCheckout() {
        return commonCommands.checkout
    }

    void setCheckout(checkout) {
        commonCommands.checkout = checkout
    }

    def getScm() {
        return commonCommands.scm
    }

    void setScm(scm) {
        commonCommands.scm = scm
    }

    def getEnv() {
        return commonCommands.env
    }

    void setEnv(env) {
        commonCommands.env = env
    }

    def getBranchName() {
        return commonCommands.branchName
    }

    void setBranchName(branchName) {
        commonCommands.branchName = branchName
    }

    def getSh() {
        return commonCommands.sh
    }

    void setSh(sh) {
        commonCommands.sh = sh
    }

    def getEcho() {
        return commonCommands.echo
    }

    void setEcho(echo) {
        commonCommands.echo = echo
    }

    def getCurrentBuild() {
        return commonCommands.currentBuild
    }

    void setCurrentBuild(currentBuild) {
        commonCommands.currentBuild = currentBuild
    }

    def getReadProperties() {
        return commonCommands.readProperties
    }

    void setReadProperties(readProperties) {
        commonCommands.readProperties = readProperties
    }

    def getError() {
        return commonCommands.error
    }

    void setError(error) {
        commonCommands.error = error
    }
/** Common Commands --ENDS-- **/

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