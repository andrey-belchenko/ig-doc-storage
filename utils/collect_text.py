import os


def collect_files_content(paths, outputFilePath):
    # Open the output file in write mode
    with open(outputFilePath, "w", encoding="utf-8") as outputFile:
        # Iterate over each path in the list
        for path in paths:
            # Check if the path is a file
            if os.path.isfile(path):
                # Write the absolute path to the output file
                outputFile.write(f"{os.path.abspath(path)}\n")

                # Read and write the content of the file
                outputFile.write("\n```\n")
                with open(path, "r", encoding="utf-8") as inputFile:
                    content = inputFile.read()
                    outputFile.write(content)

                # Write the separator
                outputFile.write("\n```\n")

            # Check if the path is a directory
            elif os.path.isdir(path):
                # Walk through the directory
                for root, dirs, files in os.walk(path):
                    for file in files:
                        # Get the absolute path of the file
                        absolute_path = os.path.join(root, file)

                        # Write the absolute path to the output file
                        outputFile.write(f"{os.path.abspath(absolute_path)}\n")
                        outputFile.write("\n```\n")
                        # Read and write the content of the file
                        with open(absolute_path, "r", encoding="utf-8") as inputFile:
                            content = inputFile.read()
                            outputFile.write(content)

                        # Write the separator
                        outputFile.write("\n```\n")

            # Handle invalid paths (neither file nor directory)
            else:
                print(f"Warning: '{path}' is not a valid file or directory. Skipping.")


collect_files_content(
    [
        r"C:\Repos\mygithub\ig-doc-storage\back\data\src\main\kotlin\ig\ds\data\model", r"C:\Repos\mygithub\ig-doc-storage\back\data\migrations" ,
        r"C:\Repos\mygithub\ig-doc-storage\back\data\src\main\kotlin\ig\ds\data\service\AttachmentService.kt"
        ],
    r"C:\Repos\mygithub\ig-doc-storage\utils\output\content.md",
)
