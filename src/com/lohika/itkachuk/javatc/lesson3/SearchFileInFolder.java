package com.lohika.itkachuk.javatc.lesson3;

import com.lohika.itkachuk.javatc.lesson3.exceptions.FileSearcherException;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: itkachuk
 * Date: 3/11/13 5:00 PM
 */
public class SearchFileInFolder {

    private File targetDirectory;

    private String fileName;

    private String outputMessage;

    private long scannedDirectoriesCounter;

    public SearchFileInFolder(String targetDirectoryName, String fileName) throws IllegalArgumentException {
        if (validateInputs(targetDirectoryName, fileName)) {
            this.targetDirectory = new File(targetDirectoryName);
            this.fileName = fileName;
        } else
            throw new IllegalArgumentException(outputMessage);
    }

    private boolean validateString(String inputString){
        return (inputString != null && !inputString.isEmpty());
    }

    private boolean validateInputs(String targetDirectoryName, String fileName){
        if (!validateString(targetDirectoryName)) {
            outputMessage = "Input error: targetDirectoryName can not be empty";
            return false;
        }
        if (!validateString(fileName)) {
            outputMessage = "Input error: fileName can not be empty";
            return false;
        }
        if (fileName.length() > 255) {
            outputMessage = "Input error: the fileName length can not be more than 255 characters";
            return false;
        }
        if ((targetDirectoryName.length() + fileName.length()) > 260) {
            outputMessage = "Input error: the total path length can not be more than 260 characters";
            return false;
        }

        File directory = new File(targetDirectoryName);
        if (!directory.isDirectory()) {
            outputMessage = "Input error: \"" + targetDirectoryName + "\" is not a directory";
            return false;
        }
        if (!directory.canRead()) {
            outputMessage = "Directory \"" + targetDirectoryName + "\" can not be read";
            return false;
        }
        return true;
    }

    public void searchFileAndPrint() throws FileSearcherException {

        File file = processDirectoryRecursively(targetDirectory, fileName);
        if (file == null) {
            throw new FileSearcherException("The FileSearcher was unable to find the file \""
                    + fileName + "\" in the directory \"" + targetDirectory + "\" and all its subdirectories");
        }

        try {
            System.out.println("Found file at: " + file.getAbsolutePath());
            System.out.println("Scanned directories count: " + scannedDirectoriesCounter);
            System.out.println("Printing file content:\n");

            // print file content to console
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line = in.readLine();
            while (line != null) {
                System.out.println(line);
                line = in.readLine();
            }
            in.close();
        } catch(FileNotFoundException e) {
            throw new FileSearcherException("The FileSearcher internal error occurred, caused by FileNotFoundException: " + e.getMessage(), e.getCause());
        } catch (IOException e) {
            throw new FileSearcherException("The FileSearcher internal error occurred, caused by IOException: " + e.getMessage(), e.getCause());
        }
    }

    private File processDirectoryRecursively(File directory, String fileName) {
        scannedDirectoriesCounter++;
        // check, if we have target file in current directory
        File file = new File(directory + File.separator + fileName);
        if (file.isFile()) return file;
        else { // if not, iterate over all subdirectories and do the same job
            File[] files = directory.listFiles();
            if (files != null) {
                for (File subdirectory : files) {
                    if (subdirectory.isDirectory()) {
                        File returnedFile = processDirectoryRecursively(subdirectory, fileName);
                        if (returnedFile != null) return returnedFile;
                    }
                }
            }
            return null;
        }
    }

    public static void main(String[] args){
        if (args.length != 2) {
            System.out.println("Usage: SearchFileInFolder <targetDirectoryName> <fileName>");
            System.exit(1);
        }

        try {
            SearchFileInFolder searcher = new SearchFileInFolder(args[0], args[1]);
            searcher.searchFileAndPrint();
        } catch (IllegalArgumentException iae) {
            System.out.println("One or more input arguments are not valid:\n" + iae.getMessage());
        } catch (FileSearcherException fse) {
            System.out.println(fse.getMessage());
        }
    }
}
