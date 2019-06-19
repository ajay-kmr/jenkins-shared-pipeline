import com.example.pipeline.model.BuildRequestDetails
import com.example.pipeline.model.SharedProperties
import com.example.pipeline.step.IPipeLineStep
import com.example.pipeline.step.PipeLineStepRunner
import com.example.pipeline.step.Prepare
import com.example.pipeline.step.java.gradle.Build

def call(Closure buildConfig) {
    echo "******************** Building project using script:- buildSingleModuleApplication ********************"
    BuildRequestDetails buildRequest = BuildRequestDetails.getInstance(buildConfig)
    SharedProperties sharedProperties = new SharedProperties(this, buildRequest)

    echo "The various properties configured are:- " + sharedProperties.toString()

    List<IPipeLineStep> pipeLineSteps = [
            new Prepare(sharedProperties),
            new Build(sharedProperties),
    ]
    PipeLineStepRunner stepRunner = new PipeLineStepRunner(this, pipeLineSteps)
    stepRunner.run()
}