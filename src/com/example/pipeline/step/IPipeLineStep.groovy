package com.example.pipeline.step

import com.example.pipeline.dto.ResponseDTO

interface IPipeLineStep {
    ResponseDTO run()

    String getStageName()
}