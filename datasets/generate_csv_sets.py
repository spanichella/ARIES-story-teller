import math
import os
import random

REPO_PATH = "/repos/python_playground"
FILES_MAIN_PATH = "/repos/SWME_G2_HS20/datasets"

STORIES_TRUTH_SET_TXT = FILES_MAIN_PATH + "/stories/truth_set_stories-ReqSpec.txt"
RECORDING_TRUTH_SET_TXT = FILES_MAIN_PATH + "/recording/truth_set_recording-ReqSpec.txt"
VALUE_SEPARATOR_CHAR = "\t"
SET_CSV_HEADERS = ["id", "req_specification", "class"]

STORIES_TRUTH_SET_TDM = FILES_MAIN_PATH + "/stories/tdm_truth_set_stories_with_oracle.csv"
RECORDING_TRUTH_SET_TDM = FILES_MAIN_PATH + "/recording/tdm_truth_set_recording_with_oracle.csv"

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


def get_truthset_files_dicts():
    combined_file = open(STORIES_TRUTH_SET_TXT, "r")
    combined_lines = combined_file.readlines()
    combined_file.close()

    recording_file = open(RECORDING_TRUTH_SET_TXT, "r")
    recording_lines = recording_file.readlines()
    recording_file.close()

    return {"recording": recording_lines, "stories": combined_lines}


def get_truthset_tdm_path(dataset):
    if dataset == "recording":
        return RECORDING_TRUTH_SET_TDM
    elif dataset == "stories":
        return STORIES_TRUTH_SET_TDM
    else:
        return ""


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
    # print(dataset)
    tdm_truthset_path = get_truthset_tdm_path(dataset)
    # print(tdm_truthset_path)
    # print(file_path)
    tdm_truthset_file = open(tdm_truthset_path, "r")
    tdm_truthset_lines = tdm_truthset_file.readlines()
    tdm_truthset_file.close()
    tdm_truthset_headers = tdm_truthset_lines[0].strip()
    tdm_truthset_lines = tdm_truthset_lines[1:]  # first line is only headers
    tdm_truthset_content_dict = {}
    for tdm_truthset_line in tdm_truthset_lines:
        truthset_line_number = tdm_truthset_line.split(",")[0]
        tdm_truthset_content_dict[truthset_line_number] = tdm_truthset_line

    # print(*tdm_truthset_content_dict)

    set_csv_file = open(file_path, "r")
    set_csv_lines = set_csv_file.readlines()
    set_csv_file.close()
    set_csv_lines = set_csv_lines[1:]  # first line is only headers

    set_tdm_lines = []
    for set_line in set_csv_lines:
        # print("set line", set_line)
        set_line_number = set_line.split(",")[SET_CSV_HEADERS.index("id")]
        # print("line number", set_line_number)
        tdm_line = tdm_truthset_content_dict[set_line_number].strip()
        # print(tdm_line)
        # if tdm_line[tdm_line.__len__() - 1] == "\n":
        #     print("has newline at end")
        set_tdm_lines.append(tdm_line)

    set_tdm_file_name = TDM_PATTERN.format(file_name=file_name)
    set_tdm_file_path = OUTPUT_PATH_TDM + "/" + set_tdm_file_name + ".csv"
    write_specific_csv_file(set_tdm_file_path, tdm_truthset_headers, set_tdm_lines)


def generate_csv_sets():
    truthset_files_dicts = get_truthset_files_dicts()

    if not os.path.exists(OUTPUT_PATH):
        os.mkdir(path=OUTPUT_PATH)
    if not os.path.exists(OUTPUT_PATH_CSV):
        os.mkdir(path=OUTPUT_PATH_CSV)
    if not os.path.exists(OUTPUT_PATH_TDM):
        os.mkdir(path=OUTPUT_PATH_TDM)

    for dataset_key in truthset_files_dicts:
        truthset_content = truthset_files_dicts[dataset_key]

        separated_content = []
        for line in truthset_content:
            splits = [item.strip().replace('"', "'") for item in line.split(VALUE_SEPARATOR_CHAR)]
            separated_content.append(splits)

        written_set_files = write_training_and_test_sets(dataset_key, separated_content)

        for set_file_key in written_set_files:
            set_file_path = written_set_files[set_file_key]
            write_tdm_file(dataset_key, set_file_key, set_file_path)


if __name__ == "__main__":
    generate_csv_sets()
