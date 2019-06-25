package com.example.pipeline.enums

enum BuildTool {
    ANT,
    MAVEN,
    GRADLE,

    ;

    static BuildTool getInstance(def input) {
        if (input && (input instanceof BuildTool)) {
            return input
        }
        BuildTool result = values().find({ buildTool -> buildTool.name().equalsIgnoreCase(input?.toString()) })
        if (!result) {
            throw new IllegalArgumentException("BuildTool ${input} not supported. Supported values are:- ${values()}")
        }
    }
}