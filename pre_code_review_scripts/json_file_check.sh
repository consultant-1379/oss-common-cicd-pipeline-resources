#!/bin/bash

function start_json_files_check() {
  echo -e "\n"
  echo "************************************************"
  echo "*  CHECKING FOR JSON FILE SYNTAX VIOLATIONS    *"
  echo "************************************************"

  if [[ -z "${changed_files}" ]]; then
    echo "INFO: No JSON files changed"
    exit 0
  fi
}

function run_json_syntax_violations_check() {
  for script_to_check in ${changed_files}; do
    if [[ "${script_to_check}" == *".json"* ]]; then
      check_json_file_script "${script_to_check}"
    fi
  done
}

function check_json_file_script() {
  script_to_check="${1}"

  echo "Checking JSON file: ${script_to_check}"
  cat "${script_to_check}" | python -m json.tool
  exit_code=${?}
  if [[ ${exit_code} -ne 0 ]]; then
    is_json_check_successful=false
    let "number_of_failures++"
    json_scripts_with_errors+="${script_to_check}"$'\n'
    echo -e "\n"
    echo "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
    echo "ERROR: Please review the above JSON file syntax errors in : ${script_to_check}"
    echo "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
    echo -e "\n"
  else
    echo "INFO: ${script_to_check} has passed"
    echo -e "\n"
  fi
}

function check_json_exit_criteria() {
  if [[ ${is_json_check_successful} = false ]]; then
    echo "============================================================================"
    echo "FAILURE: ${number_of_failures} file(s) have JSON syntax violations"
    echo "Files with JSON violations:"
    echo "${json_scripts_with_errors}"
    echo "============================================================================"
    exit 1
  else
    echo "==============================================="
    echo "PASS: All JSON file syntax checks have passed"
    echo "==============================================="
  fi
}

########################
#     SCRIPT START     #
########################

changed_files=$(cat < diff.txt)

start_json_files_check
run_json_syntax_violations_check
check_json_exit_criteria
