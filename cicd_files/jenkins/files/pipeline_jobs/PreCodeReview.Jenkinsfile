#!/usr/bin/env groovy

String bob = "bob/bob -r \${WORKSPACE}/cicd_files/jenkins/rulesets/pipeline_jobs/PreCodeReview.yaml"

pipeline {
    agent {
        node {
            label SLAVE
        }
    }
    options {
        ansiColor('xterm')
    }
    environment {
        GIT_BRANCH = 'temporary'
        GIT_CMD = 'env -i git'
        CHANGED_FILES = sh(returnStdout: true, script: "git diff-tree --diff-filter=ACM --no-commit-id --name-only -r $GIT_COMMIT -- '*'").replaceAll("\\n", ' ')
    }
    stages {
        stage('Cleaning Git Repo') {
            steps {
                sh 'git clean -xdff'
                sh 'git submodule sync'
                sh 'git submodule update --init --recursive'
            }
        }
        stage('Identify changed files') {
            steps {
                script {
                    if (CHANGED_FILES) {
                        sh "echo ${CHANGED_FILES} > diff.txt"
                        echo "Changed Files: ${CHANGED_FILES}"
                    } else {
                        echo "CHANGED_FILES is null or undefined."
                    }
                }
            }
        }
        stage('JSON Validation') {
            when {
                expression {
                    CHANGED_FILES != null && CHANGED_FILES.contains('.json')
                }
            }
            steps {
                script {
                    if (CHANGED_FILES) {
                        sh "${bob} lint-json"
                    }
                }
            }
        }
        stage('CSV Validation') {
            when {
                expression {
                    CHANGED_FILES != null && CHANGED_FILES.contains('.csv')
                }
            }
            steps {
                script {
                    if (CHANGED_FILES) {
                        sh "${bob} lint-csv"
                    }
                }
            }
        }
        stage('MD Validation') {
            when {
                expression {
                    CHANGED_FILES != null && CHANGED_FILES.contains('.md')
                }
            }
            steps {
                script {
                    if (CHANGED_FILES) {
                        sh "${bob} lint-md"
                    }
                }
            }
        }
        stage('TB: Verify Documentation added') {
            when {
                expression {
                    CHANGED_FILES != null && currentBuild.result != 'ERROR'
                }
            }
            steps {
                script {
                    if (CHANGED_FILES) {
                        verifyPipelineDocumentationUpdated()
                    }
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
void verifyPipelineDocumentationUpdated() {
    List<String> thunderbees = [
                        'Alan Conway',
                        'Aloysius Pinto',
                        'Andrew Barwell',
                        'Arshdeep Singh B',
                        'Daniel Onekpe',
                        'John Meehan',
                        'Patrik Svensson',
                        'Philip Walsh',
                        'Richard Collins',
                        'Shane Flynn'
                    ]
    String commiter = "${GERRIT_CHANGE_OWNER_NAME}"
    if ( !thunderbees.contains(commiter) ) {
        return
    }
    if ("${GERRIT_CHANGE_SUBJECT}".contains('SKIP_DOCS')) {
        println 'Skipping Documentation verification'
        return
    }

    List<String> jsonFiles = filterFilesByExtension(CHANGED_FILES, '.json')
    List<String> mdFiles = filterFilesByExtension(CHANGED_FILES, '.md')

    if (jsonFiles.size() > mdFiles.size()) {
        error('Missing Required Documentation')
    }

    List<String> jsonFileNames = extractFileNames(jsonFiles)
    List<String> mdFileNames = extractFileNames(mdFiles)

    if (!compareFileNames(jsonFileNames, mdFileNames)) {
        error('Missing Required Documentation')
    }
}

List<String> filterFilesByExtension(String files, String extension) {
    List<String> filteredFiles = []
    for (String file in files.split()) {
        if (file.endsWith(extension)) {
            filteredFiles.push(file)
        }
    }
    return filteredFiles
}

List<String> extractFileNames(List<String> files) {
    List<String> fileNames = files.collect { file ->
        def fileName = file.tokenize('/').last()
        fileName.substring(0, fileName.lastIndexOf('.'))
    }
    return fileNames
}

boolean compareFileNames(List<String> list1, List<String> list2) {
    return list2.containsAll(list1)
}
