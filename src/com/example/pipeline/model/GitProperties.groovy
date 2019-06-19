package com.example.pipeline.model

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