sectionedView('Common CICD Pipeline Resources') {
    description("""<div style="padding:1em;border-radius:1em;text-align:center;background:#fbf6e1;box-shadow:0 0.1em 0.3em #525000">
        <b>Common CICD Pipeline Resources</b><br>
       CICD Pipelines and Source Control Jobs. <br><br>
        Team: <b>Thunderbee &#x26A1</b><br>
    </div>""")
    sections {
        listView() {
            name('Common CICD Pipeline Resources Jobs')
            jobs {
                name('oss-common-cicd-pipeline-resources_Pre_Code_Review')
            }
            columns setViewColumns()
        }
        listView() {
            name('Common CICD Pipeline Resources Source Control Jobs')
            jobs {
                name("oss-common-cicd-pipeline-resources_Pipeline_Generator")
                name("oss-common-cicd-pipeline-resources_Pipeline_Updater")
            }
            columns setViewColumns()
        }
    }
}

static Object setViewColumns() {
    return {
        status()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
        buildButton()
    }
}
