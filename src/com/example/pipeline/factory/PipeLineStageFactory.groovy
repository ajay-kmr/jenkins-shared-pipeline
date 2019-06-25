package com.example.pipeline.factory

import com.example.pipeline.enums.BuildTool
import com.example.pipeline.model.SharedProperties
import com.example.pipeline.stage.IPipeLineStage

class PipeLineStageFactory {
    static List<IPipeLineStage> getPipeLineStages(SharedProperties sharedProperties) {
        BuildTool currentBuildTool = sharedProperties?.buildRequestDetails?.buildTool
        if (!currentBuildTool) {
            throw new IllegalArgumentException("No BuildTool specified. Possible values are ${BuildTool.values()}")
        }
        switch (currentBuildTool) {
            case BuildTool.GRADLE: return GradlePipeLineStageFactory.getPipeLineStages(sharedProperties)
            default:
                throw new IllegalArgumentException("No Build mechanism defined yet for ${currentBuildTool}")
        }
    }
}
