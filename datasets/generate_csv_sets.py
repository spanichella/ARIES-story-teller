import math
import os
import random

REPO_PATH = "/repos/python_playground"
FILES_MAIN_PATH = "/repos/SWME_G2_HS20/datasets"

TDM_TRUTH_SET_PATTERN = "/{dataset}/tdm_truth_set_{dataset}_with_oracle.csv"
TXT_TRUTH_SET_PATTERN = "/{dataset}/truth_set_{dataset}-ReqSpec.txt"
RECORDING_TRUTH_SET_TDM = FILES_MAIN_PATH + TDM_TRUTH_SET_PATTERN.format(dataset="recording")
RECORDING_TRUTH_SET_TXT = FILES_MAIN_PATH + TXT_TRUTH_SET_PATTERN.format(dataset="recording")
STORIES_TRUTH_SET_TDM = FILES_MAIN_PATH + TDM_TRUTH_SET_PATTERN.format(dataset="stories")
STORIES_TRUTH_SET_TXT = FILES_MAIN_PATH + TXT_TRUTH_SET_PATTERN.format(dataset="stories")
DATASETS_DICT = {
    "recording": {"TDM": RECORDING_TRUTH_SET_TDM, "TXT": RECORDING_TRUTH_SET_TXT},
    "stories": {"TDM": STORIES_TRUTH_SET_TDM, "TXT": STORIES_TRUTH_SET_TXT}
}
VALUE_SEPARATOR_CHAR = "\t"
SET_CSV_HEADERS = ["id", "req_specification", "class"]

OUTPUT_PATH = REPO_PATH + "/output"
OUTPUT_PATH_CSV = OUTPUT_PATH + "/csv_sets"
OUTPUT_PATH_TDM = OUTPUT_PATH + "/csv_tdm"

TDM_PATTERN = "tdm_{file_name}_with_oracle"
TEST_SET_PATTERN = "test_set-{partition}_{dataset}"
TRAINING_SET_PATTERN = "training_set-{partition}_{dataset}"

SET_PARTITIONS_TRAINING_TO_TEST = ["80-20", "50-50"]
SET_PARTITIONS_SPLIT_CHAR = "-"


def randomly(seq):
    shuffled = list(seq)
    random.shuffle(shuffled)
    return shuffled


def get_truth_set_files_dict():
    truth_set_dict = {}
    for key in DATASETS_DICT:
        file = open(DATASETS_DICT[key]["TXT"], "r")
        lines = file.readlines()
        file.close()
        truth_set_dict[key] = lines

    return truth_set_dict


def get_truth_set_tdm_path(dataset):
    return DATASETS_DICT[dataset]["TDM"]


def write_specific_csv_file(file_path, headers, content_lines, has_split_values_in_lines=False):
    if os.path.exists(file_path):
        os.remove(file_path)
    file = open(file_path, "x")
    file.close()
    file = open(file_path, "a")
    if isinstance(headers, list):
        file.write('"' + '","'.join(headers) + '"\n')
    else:
        file.write(headers.strip() + '\n')

    for line in content_lines:
        if has_split_values_in_lines:
            file.writelines('"' + '","'.join(line) + '"\n')
        else:
            file.writelines(line + '\n')

    file.close()


def write_training_and_test_sets(file_key, separated_content):
    written_files = {}
    for partition in SET_PARTITIONS_TRAINING_TO_TEST:
        shuffled_content = randomly(separated_content)

        split_partitions = partition.split(SET_PARTITIONS_SPLIT_CHAR)
        partitioning = math.floor(shuffled_content.__len__() * (int(split_partitions[0]) / 100))

        training_partition = shuffled_content[:partitioning]
        test_partition = shuffled_content[partitioning:]

        training_file_name = TRAINING_SET_PATTERN.format(dataset=file_key, partition=split_partitions[0])
        training_file_path = OUTPUT_PATH_CSV + "/" + training_file_name + ".csv"
        test_file_name = TEST_SET_PATTERN.format(dataset=file_key, partition=split_partitions[1])
        test_file_path = OUTPUT_PATH_CSV + "/" + test_file_name + ".csv"

        written_files[training_file_name] = training_file_path
        written_files[test_file_name] = test_file_path

        write_specific_csv_file(training_file_path, SET_CSV_HEADERS, training_partition, True)
        write_specific_csv_file(test_file_path, SET_CSV_HEADERS, test_partition, True)

    return written_files


def write_tdm_file(dataset, file_name, file_path):
    tdm_truth_set_path = get_truth_set_tdm_path(dataset)
    tdm_truth_set_file = open(tdm_truth_set_path, "r")
    tdm_truth_set_lines = tdm_truth_set_file.readlines()
    tdm_truth_set_file.close()
    tdm_truth_set_headers = tdm_truth_set_lines[0].strip()
    tdm_truth_set_lines = tdm_truth_set_lines[1:]  # first line is only headers
    tdm_truth_set_content_dict = {}
    for tdm_truth_set_line in tdm_truth_set_lines:
        truth_set_line_number = tdm_truth_set_line.split(",")[0]
        tdm_truth_set_content_dict[truth_set_line_number] = tdm_truth_set_line

    set_csv_file = open(file_path, "r")
    set_csv_lines = set_csv_file.readlines()
    set_csv_file.close()
    set_csv_lines = set_csv_lines[1:]  # first line is only headers

    set_tdm_lines = []
    for set_line in set_csv_lines:
        set_line_number = set_line.split(",")[SET_CSV_HEADERS.index("id")]
        tdm_line = tdm_truth_set_content_dict[set_line_number].strip()
        set_tdm_lines.append(tdm_line)

    set_tdm_file_name = TDM_PATTERN.format(file_name=file_name)
    set_tdm_file_path = OUTPUT_PATH_TDM + "/" + set_tdm_file_name + ".csv"
    write_specific_csv_file(set_tdm_file_path, tdm_truth_set_headers, set_tdm_lines)


def generate_csv_sets():
    truth_set_files_dicts = get_truth_set_files_dict()

    if not os.path.exists(OUTPUT_PATH):
        os.mkdir(path=OUTPUT_PATH)
    if not os.path.exists(OUTPUT_PATH_CSV):
        os.mkdir(path=OUTPUT_PATH_CSV)
    if not os.path.exists(OUTPUT_PATH_TDM):
        os.mkdir(path=OUTPUT_PATH_TDM)

    for dataset_key in truth_set_files_dicts:
        truth_set_content = truth_set_files_dicts[dataset_key]

        separated_content = []
        for line in truth_set_content:
            splits = [item.strip().replace('"', "'") for item in line.split(VALUE_SEPARATOR_CHAR)]
            separated_content.append(splits)

        written_set_files = write_training_and_test_sets(dataset_key, separated_content)

        for set_file_key in written_set_files:
            set_file_path = written_set_files[set_file_key]
            write_tdm_file(dataset_key, set_file_key, set_file_path)


if __name__ == "__main__":
    generate_csv_sets()
