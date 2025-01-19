pipeline {
    agent any
    stages {
        stage('scm') {
            steps {
                git url: 'https://github.com/jayanth197/WebServerproject_B2.git',
                    branch: 'main',
                    credentialsId: 'e23c1943-bed0-428c-92c2-cc10dd5a9bc2'
            }
        }
        stage('ArchiveArtifacts') {
            steps {
                archiveArtifacts '**/*.html'
            }
        }
        stage('Build') {
            steps {
                sshPublisher(publishers: [
                    sshPublisherDesc(
                        configName: 'WebServer_B1',
                        transfers: [
                            sshTransfers(
                                excludes: '',
                                exeCommand: '',
                                exeTimeout: 120000,
                                flatten: true,
                                makeEmptyDirs: false,
                                noDefaultExcludes: false,
                                patternSeparator: '[,]+',
                                remoteDirectory: 'www/html',
                                remoteDirectorySDF: false,
                                removePrefix: '',
                                sourceFiles: '**/*.html'
                            )
                        ],
                        usePromotionTimestamp: false,
                        useWorkspaceInPromotion: false,
                        verbose: true
                    )
                ])
            }
        }

    }
}
