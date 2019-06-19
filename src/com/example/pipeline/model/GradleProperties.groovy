package com.example.pipeline.model

class GradleProperties {
    String applicationName
    String version

    String getVersionWithoutSnapShot() {
        if (version?.toUpperCase()?.contains("-SNAPSHOT")) {
            return version.toUpperCase() - '-SNAPSHOT'
        }
        return version
    }

    @Override
    String toString() {
        return "GradleProperties{" +
                "applicationName='" + applicationName + '\'' +
                ", version='" + version + '\'' +
                ", version without SNAPSHOT='" + versionWithoutSnapShot + '\'' +
                '}'
    }
}

