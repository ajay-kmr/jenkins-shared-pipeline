import com.example.pipeline.dto.BuildRequestDTO
import com.example.pipeline.dto.SharedProperties
import com.example.pipeline.step.PipeLineStep
import com.example.pipeline.step.PipeLineStepRunner
import com.example.pipeline.step.Prepare

def call(Closure buildConfig) {
    pipeline {
        agent any
        stages {
            stage('Ajay Even Stage') {
                steps {
                    echo "Ajay The build number is even"
                }
            }

            BuildRequestDTO buildRequestDTO = createBuildRequest(buildConfig)
            println("Building project with request:- ${buildRequestDTO}")
            SharedProperties sharedProperties = new SharedProperties(this, buildRequestDTO)

            List<PipeLineStep> pipeLineSteps = [
                    new Prepare(sharedProperties),

            ]
            PipeLineStepRunner stepRunner = new PipeLineStepRunner(this, pipeLineSteps)
            stepRunner.run()
        }
    }


}

private static BuildRequestDTO createBuildRequest(Closure buildConfig) {
    BuildRequestDTO buildRequestDTO = new BuildRequestDTO()
    buildConfig.resolveStrategy = Closure.DELEGATE_FIRST
    buildConfig.delegate = buildRequestDTO
    buildConfig()
    buildRequestDTO
}