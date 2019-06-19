package com.example.pipeline.enums

enum Stage {
    PREPARE("Do the initial setup eg initializing gradle and git properties for further stages."),
    BUILD("Build the application"),

    UNIT_TEST("Perform Unit Test"),
    INTEGRATION_TEST("Perform Integration Test"),
    LOAD_TEST("Perform Load Test"),
    ACCEPTANCE_TEST("Perform Business Acceptance Test"),

    SONAR_CHECK("SONAR CHECK", "Run Sonar Check")

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
}