import com.example.pipeline.dto.BuildRequestDTO
import com.example.pipeline.dto.SharedProperties
import com.example.pipeline.step.IPipeLineStep
import com.example.pipeline.step.PipeLineStepRunner
import com.example.pipeline.step.Prepare

def call(Closure buildConfig) {
    echo "******************** Building project using script:- buildSingleModuleApplication ********************"
    BuildRequestDTO buildRequestDTO = createBuildRequest(buildConfig)
    SharedProperties sharedProperties = new SharedProperties(this, buildRequestDTO)

    List<IPipeLineStep> pipeLineSteps = [
            new Prepare(sharedProperties),

    ]
    PipeLineStepRunner stepRunner = new PipeLineStepRunner(this, pipeLineSteps)

    stepRunner.run()
}

private static BuildRequestDTO createBuildRequest(Closure buildConfig) {
    def buildRequestDTO = new BuildRequestDTO()
    buildConfig.resolveStrategy = Closure.DELEGATE_FIRST
    buildConfig.delegate = buildRequestDTO
    buildConfig()

    echo "The config Detail populated"
    echo buildRequestDTO.toString()

    return buildRequestDTO
}