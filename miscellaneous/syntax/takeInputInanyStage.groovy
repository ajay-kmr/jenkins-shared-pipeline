/**
 * Refer @link {https://support.cloudbees.com/hc/en-us/articles/204986450-Pipeline-How-to-manage-user-inputs}*
 *
 * */
stage('Promotion') {
    //Note:- The output of method:- input() will contains the value of parameters inside a Map
    // **If you have only one parameter, **its value will be directly returned instead of a Map
    // Tip: It is best to avoid inserting input steps inside a node{} block, so as not occupy a node/agent while waiting for user input.

    String buildApprovalUrl = "${BUILD_URL}input/"
    echo "The URL for providing Input will be-> ${buildApprovalUrl}"
    def approvalInput = input(
            message: "Should we continue with the deployment?",
            ok: "Yes, we should.",
            id: 'approvalInputId',
            submitterParameter: 'approvedBy',
            //submitter "alice,bob"
            parameters: [
                    string(name: 'Environment', defaultValue: 'Dev', description: 'Deploying to environment- Dev', trim: true),
                    string(name: 'Branch', defaultValue: 'master', description: 'Deploying branch- master', trim: true),
                    string(name: 'CommitId', defaultValue: '1234asdsd45545', description: 'Commit Id', trim: true),
                    string(name: 'Version', defaultValue: '1.0.2', description: 'Version', trim: true),
            ]
    )
    //The captured input can be accessed as follow:-
    echo "Environment:- ${approvalInput['Environment']}"
    echo "Branch:- ${approvalInput.Branch}"
    echo "CommitId:- ${approvalInput.CommitId}"
    echo "Version:- ${approvalInput.Version}"

    echo "Deployment approved by ${env.approvedBy} for:- "

}
