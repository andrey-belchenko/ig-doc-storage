
import os

def collect_files_content(folderPath, outputFilePath):
    # Open the output file in write mode
    with open(outputFilePath, 'w', encoding='utf-8') as outputFile:
        # Walk through the directory
        for root, dirs, files in os.walk(folderPath):
            for file in files:
                # Get the relative path of the file
                relative_path = os.path.relpath(os.path.join(root, file), folderPath)
                
                # Write the relative path to the output file
                outputFile.write(f"{relative_path}\n")
                
                # Read and write the content of the file
                with open(os.path.join(root, file), 'r', encoding='utf-8') as inputFile:
                    content = inputFile.read()
                    outputFile.write(content)
                
                # Write the separator
                outputFile.write("\n----\n")

collect_files_content(r"C:\Repos\mygithub\ig-doc-storage\back\data\src\main\kotlin\ig\ds\data",r"C:\Repos\mygithub\ig-doc-storage\utils\output\content.txt" )
    