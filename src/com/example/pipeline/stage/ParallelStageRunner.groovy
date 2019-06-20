package com.example.pipeline.stage

import com.example.pipeline.enums.Stage
import com.example.pipeline.model.ResponseDetails
import com.example.pipeline.model.SharedProperties

class ParallelStageRunner extends PipeLineStageImpl<List<ResponseDetails>> {

    IPipeLineStage firstStage
    IPipeLineStage secondStage

    ParallelStageRunner(SharedProperties sharedProperties,
                        String agentName, String nodeName,
                        Stage stage,
                        IPipeLineStage firstStage,
                        IPipeLineStage secondStage) {
        super(sharedProperties, agentName, nodeName, stage)
        this.firstStage = firstStage
        this.secondStage = secondStage

    }

    @Override
    ResponseDetails<List<ResponseDetails>> run() {
        List<ResponseDetails> responseDetails = []
        script.parallel firstStage.stage.displayName: {
            responseDetails.add(firstStage.run())
        },
                secondStage.stage.displayName: {
                    responseDetails.add(secondStage.run())
                }
        responseDetails
    }
}
