package com.example.pipeline.enums

enum Stage {
    PREPARE("Do the initial setup eg initializing gradle and git properties for further stages."),
    BUILD("Build the application"),
    FORTIFY("FORTIFY"),

    UNIT_TEST("Perform Unit Test"),
    INTEGRATION_TEST("Perform Integration Test"),
    LOAD_TEST("Perform Load Test"),
    ACCEPTANCE_TEST("Perform Business Acceptance Test"),

    SONAR_CHECK("SONAR CHECK", "Run Sonar Check"),
    GENERATE_REPORT("GENERATE REPORT", "Generate Report of Test Result"),
    PUBLISH_ARTIFACT("the artifact to maven repository"),
    RELEASE("Create Docker image and release it to docker hub"),
    DEPLOY("Deploy the application to various environment eg dev, QA, UAT etc"),
    CONCLUDE("Conclude the over all result of pipeline and send/publish the status to required channel"),

    ;
    String displayName
    String description

    Stage(String description) {
        this.displayName = name()
        this.description = description
    }

    Stage(String displayName, String description) {
        this.displayName = displayName
        this.description = description
    }

    static Stage getInstance(def input) {
        if (input && (input instanceof Stage)) {
            return input
        }
        Stage result = values()?.find { stage ->
            stage.name().equalsIgnoreCase(input?.toString()) || stage.displayName.equalsIgnoreCase(input?.toString())
        }
        if ((!result)) {
            throw new IllegalArgumentException("No stage defined for ${input}. Possible values are:- ${values()}")
        }
        return result
    }
}