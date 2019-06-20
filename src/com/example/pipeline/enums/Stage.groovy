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
    RELEASE("Generate Report of Test Result"),
    DEPLOY("Generate Report of Test Result"),
    CONCLUDE("Generate Report of Test Result"),

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
        values()?.find { stage ->
            stage.name().equalsIgnoreCase(input?.toString()) || stage.displayName.equalsIgnoreCase(input?.toString())
        }
    }
}