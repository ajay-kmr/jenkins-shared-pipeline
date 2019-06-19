package com.example.pipeline.step

import com.example.pipeline.model.ResponseDetails

interface IPipeLineStep {
    ResponseDetails run()

    String getStageName()
}