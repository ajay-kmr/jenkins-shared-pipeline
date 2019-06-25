package com.example.pipeline.model

import com.example.pipeline.enums.StageStatus

class EmailNotificationRequest {
    def script
    StageStatus buildStatus

    String color = "RED"
    String colorCode = "#FF0000"

    String subject
    String summary
    String message

    String setBuildStatus() {
        this.buildStatus = buildStatus
        // Override default values based on build status
        if (buildStatus == StageStatus.STARTED) {
            color = 'YELLOW'
            colorCode = '#FFFF00'
        } else if (buildStatus == StageStatus.SUCCESS) {
            color = 'GREEN'
            colorCode = '#00FF00'
        } else {
            color = 'RED'
            colorCode = '#FF0000'
        }
    }
}
