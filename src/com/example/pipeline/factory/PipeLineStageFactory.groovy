package com.example.pipeline.factory

import com.example.pipeline.enums.BuildTool
import com.example.pipeline.model.SharedProperties
import com.example.pipeline.stage.IPipeLineStage

class PipeLineStageFactory {
    List<IPipeLineStage> createPipeLineStages(SharedProperties sharedProperties) {
        sharedProperties.jenkinsScript.echo "Creating pipeline stages"
        List<IPipeLineStage> pipeLineStageList = []

        BuildTool currentBuildTool = sharedProperties?.buildRequestDetails?.buildTool
        if (!currentBuildTool) {
            sharedProperties.jenkinsScript.echo "No BuildTool specified. Possible values are ${BuildTool.values()}"
//            throw new IllegalArgumentException("No BuildTool specified. Possible values are ${BuildTool.values()}")
        }
        switch (currentBuildTool) {
            case BuildTool.GRADLE: pipeLineStageList = GradlePipeLineStageFactory.getPipeLineStages(sharedProperties)
                break
            default:
                sharedProperties.jenkinsScript.echo "No Build mechanism defined yet for ${currentBuildTool}"
//                throw new IllegalArgumentException("No Build mechanism defined yet for ${currentBuildTool}")
        }
        pipeLineStageList
    }
}
