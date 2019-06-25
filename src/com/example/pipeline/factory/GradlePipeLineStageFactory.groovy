package com.example.pipeline.factory

import com.example.pipeline.enums.Stage
import com.example.pipeline.model.SharedProperties
import com.example.pipeline.stage.IPipeLineStage
import com.example.pipeline.stage.Prepare
import com.example.pipeline.stage.java.gradle.*

class GradlePipeLineStageFactory {

    static List<IPipeLineStage> getPipeLineStages(SharedProperties sharedProperties) {
        setDefaultPipeLineStagesIfNotDefined(sharedProperties)
        List<IPipeLineStage> pipeLineSteps = sharedProperties.buildRequestDetails.stages.collect { stage ->
            getPipeLineStage(stage, sharedProperties)
        }
        Collections.unmodifiableList(pipeLineSteps)
    }

    private static IPipeLineStage getPipeLineStage(Stage stage, SharedProperties sharedProperties) {
        switch (stage) {
            case Stage.PREPARE: return new Prepare(sharedProperties)
            case Stage.BUILD: return new Build(sharedProperties)
            case Stage.FORTIFY: return new Fortify(sharedProperties)
            case Stage.ACCEPTANCE_TEST: return new AcceptanceTest(sharedProperties)
            case Stage.SONAR_CHECK: return new Sonar(sharedProperties)
            case Stage.GENERATE_REPORT: return new GenerateReport(sharedProperties)
            case Stage.PUBLISH_ARTIFACT: return new PublishArtifact(sharedProperties)
            case Stage.RELEASE: return new Release(sharedProperties)
            case Stage.DEPLOY: return new Deploy(sharedProperties)
            case Stage.CONCLUDE: return new Conclude(sharedProperties)
            default:
                throw new IllegalArgumentException("No action defined for ${stage}")
        }
    }

    private static void setDefaultPipeLineStagesIfNotDefined(SharedProperties sharedProperties) {
        if (!sharedProperties?.buildRequestDetails?.stages) {
            //If Application has not defined any Stage, then define the default one
            sharedProperties.buildRequestDetails.stages = [
                    Stage.PREPARE,
                    Stage.BUILD,
                    Stage.FORTIFY,
//                Stage.ACCEPTANCE_TEST,
//                Stage.SONAR_CHECK,
                    Stage.GENERATE_REPORT,
                    Stage.PUBLISH_ARTIFACT,
                    Stage.RELEASE,
                    Stage.DEPLOY,
                    Stage.CONCLUDE,
            ]
            echo "No stages ware defined. So using defaults:-"
            echo "${sharedProperties.buildRequestDetails.stages}"
        }
    }
}
