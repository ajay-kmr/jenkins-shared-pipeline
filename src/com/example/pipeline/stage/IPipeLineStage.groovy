package com.example.pipeline.stage

import com.example.pipeline.enums.Stage
import com.example.pipeline.model.ResponseDetails

interface IPipeLineStage<T> {
    ResponseDetails<T> run()

    Stage getStage()
}