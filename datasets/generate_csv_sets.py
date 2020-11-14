import math
import os
import random

REPO_PATH = "/repos/python_playground"
FILES_MAIN_PATH = "/repos/SWME_G2_HS20/datasets"

COMBINED_TRUTH_SET = FILES_MAIN_PATH + "/combined/truth_set_Combined-ReqSpec.txt"
RECORDING_TRUTH_SET = FILES_MAIN_PATH + "/recording/truth_set_Recording-ReqSpec.txt"
VALUE_SEPARATOR_CHAR = "\t"
CSV_HEADERS = ["id", "req_specification", "class"]

OUTPUT_PATH = REPO_PATH + "/output"
OUTPUT_PATH_CSV = OUTPUT_PATH + "/csv_sets"

SET_PARTITIONS_TRAINING_TO_TEST = ["80-20", "50-50"]
SET_PARTITIONS_SPLIT_CHAR = "-"


def randomly(seq):
    shuffled = list(seq)
    random.shuffle(shuffled)
    return shuffled


def get_files_dicts():
    combined_file = open(COMBINED_TRUTH_SET, "r")
    combined_lines = combined_file.readlines()
    combined_file.close()

    recording_file = open(RECORDING_TRUTH_SET, "r")
    recording_lines = recording_file.readlines()
    recording_file.close()

    return {"recording": recording_lines, "combined": combined_lines}


def write_specific_csv_file(file_path, split_content_lines):
    if os.path.exists(file_path):
        os.remove(file_path)
    file = open(file_path, "x")
    file.close()
    file = open(file_path, "a")
    file.write('"' + '","'.join(CSV_HEADERS) + '"\n')
    for line in split_content_lines:
        file.writelines('"' + '","'.join(line) + '"\n')
    file.close()


def write_csv_sets(file_key, separated_content):
    for partition in SET_PARTITIONS_TRAINING_TO_TEST:
        shuffled_content = randomly(separated_content)

        split_partitions = partition.split(SET_PARTITIONS_SPLIT_CHAR)
        partitioning = math.floor(shuffled_content.__len__() * (int(split_partitions[0]) / 100))

        training_partition = shuffled_content[:partitioning]
        test_partition = shuffled_content[partitioning:]

        training_file_path = OUTPUT_PATH_CSV + "/training_set-" + file_key + "-" + split_partitions[0] + ".csv"
        test_file_path = OUTPUT_PATH_CSV + "/test_set-" + file_key + "-" + split_partitions[1] + ".csv"

        write_specific_csv_file(training_file_path, training_partition)
        write_specific_csv_file(test_file_path, test_partition)


def generate_csv_sets():
    files_dicts = get_files_dicts()

    if not os.path.exists(OUTPUT_PATH):
        os.mkdir(path=OUTPUT_PATH)
    if not os.path.exists(OUTPUT_PATH_CSV):
        os.mkdir(path=OUTPUT_PATH_CSV)

    for file_key in files_dicts:
        file_content = files_dicts[file_key]

        separated_content = []
        for line in file_content:
            splits = [item.strip() for item in line.split(VALUE_SEPARATOR_CHAR)]
            separated_content.append(splits)

        write_csv_sets(file_key, separated_content)


if __name__ == "__main__":
    generate_csv_sets()
