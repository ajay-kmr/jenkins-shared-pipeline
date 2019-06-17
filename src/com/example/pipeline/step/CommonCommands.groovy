package com.example.pipeline.step


import javax.annotation.PostConstruct

class CommonCommands {
    def jenkinsScript
    def node
    def stage
    def checkout
    def scm
    def env
    def branchName
    def sh
    def echo
    def currentBuild
    def readProperties
    def error

    CommonCommands(jenkinsScript) {
        this.jenkinsScript = jenkinsScript
    }

    @PostConstruct
    void initialize() {
        node = jenkinsScript.node
        stage = jenkinsScript.stage
        checkout = jenkinsScript.checkout
        scm = jenkinsScript.scm
        env = jenkinsScript.env
        branchName = jenkinsScript.BRANCH_CHNAME
        sh = jenkinsScript.sh
        echo = jenkinsScript.echo
        currentBuild = jenkinsScript.currentBuild
        readProperties = jenkinsScript.readProperties
        error = jenkinsScript.error
    }
}