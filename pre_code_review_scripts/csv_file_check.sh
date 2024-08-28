#!/bin/bash

function start_csv_files_check() {
    echo -e "\n"
    echo "************************************************"
    echo "*   CHECKING FOR CSV FILE SYNTAX VIOLATIONS    *"
    echo "************************************************"

    if [[ -z "${changed_files}" ]]; then
        echo "INFO: No CSV files changed"
        exit 0
    fi
}

function run_csv_syntax_violations_check() {
    for script_to_check in ${changed_files}; do
        if [[ "${script_to_check}" == *".csv"* ]]; then
            check_csv_file_script "${script_to_check}"
        fi
    done
}

function check_csv_file_script() {
    script_to_check="${1}"
    echo "Checking CSV file: ${script_to_check}"
    check_if_changed_csv_has_correct_formatting "${script_to_check}"

    if [[ ${exit_code} -ne 0 ]]; then
        is_csv_check_successful=false
        let "number_of_failures++"
        csv_scripts_with_errors+="${script_to_check}"$'\n'
        echo -e "\n"
        echo "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
        echo "ERROR: Please review the above CSV syntax errors in : ${script_to_check}"
        echo "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
        echo -e "\n"
    else
        echo "INFO: ${script_to_check} has passed"
        echo -e "\n"
    fi
}

function check_if_changed_csv_has_correct_formatting() {
    script_to_check="${1}"
    export script_to_check="${script_to_check}"
    python -c '
import sys
import os
import csv
with open(os.getcwd() + "/" + os.environ["script_to_check"]) as csv_file:
  csv_file_reader = csv.reader(csv_file)

  number_of_csv_headers = 0
  for loop_count, row_of_csv in enumerate(csv_file_reader):
    if (loop_count == 0):
      number_of_csv_headers = len(row_of_csv)
    if (len(row_of_csv) != number_of_csv_headers):
      print("Non consistent number of columns in CSV. Exiting...")
      sys.exit(1)
    for csv_column in row_of_csv:
      if (csv_column in ["", None]):
        print("Empty element provided in CSV. Exiting...")
        sys.exit(1)
'
    exit_code=${?}
}

function check_csv_exit_criteria() {
    if [[ ${is_csv_check_successful} = false ]]; then
        echo "============================================================================"
        echo "FAILURE: ${number_of_failures} file(s) CSV csv syntax violations"
        echo "Files with CSV violations:"
        echo "${csv_scripts_with_errors}"
        echo "============================================================================"
        exit 1
    else
        echo "============================================"
        echo "PASS: All CSV file syntax checks have passed"
        echo "============================================"
    fi
}

########################
#     SCRIPT START     #
########################

changed_files=$(cat < diff.txt)

start_csv_files_check
run_csv_syntax_violations_check
check_csv_exit_criteria