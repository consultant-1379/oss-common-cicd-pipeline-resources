package common_classes

class CommonParameters {

    static List slave(String defaultValue='GridEngine') {
        return ['SLAVE', defaultValue, 'Slave']
    }

    static String repo() {
        return 'OSS/com.ericsson.oss.cicd/oss-common-cicd-pipeline-resources'
    }
    static String repoUrl() {
        return '\${GERRIT_MIRROR}/'+repo()
    }

}
