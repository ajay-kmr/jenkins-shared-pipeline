package com.example.pipeline.model

/**
 * This class is used to collect the Git related
 * properties required during the pipeline execution
 * This information is generally collected during the Prepare Stage of PipeLine execution.
 */
class GitProperties {
    String remoteOriginUrl
    String committerName
    String committerEmail
    String commitDate
    String commitMessage

    @Override
    String toString() {
        return "GitProperties{" +
                "remoteOriginUrl='" + remoteOriginUrl + '\'' +
                ", committerName='" + committerName + '\'' +
                ", committerEmail='" + committerEmail + '\'' +
                ", commitDate='" + commitDate + '\'' +
                ", commitMessage='" + commitMessage + '\'' +
                '}'
    }
}