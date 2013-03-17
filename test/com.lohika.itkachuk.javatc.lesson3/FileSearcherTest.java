package com.lohika.itkachuk.javatc.lesson3;

import com.lohika.itkachuk.javatc.lesson3.exceptions.FileSearcherException;
import org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: itkachuk
 * Date: 3/17/13 9:41 PM
 */
public class FileSearcherTest {

    private static String currentDirectory;

    @BeforeClass
    public static void preparation(){
        currentDirectory = System.getProperty("user.dir");
        System.out.println("Current directory: " + currentDirectory);
    }

    // Negative cases
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() throws FileSearcherException {
        SearchFileInFolder fileSearcher = new SearchFileInFolder(null, ""); // null or empty arguments are not allowed
        fileSearcher.searchFileAndPrint();
    }

    @Test
    public void testErrorMessageForIncorrectDirectoryName() throws FileSearcherException {
        String targetDirectoryName = "\\unexisting_directory";
        SearchFileInFolder fileSearcher = new SearchFileInFolder(targetDirectoryName, "somefile.txt"); // check whether the directory exists
        try{
            fileSearcher.searchFileAndPrint();
        } catch (IllegalArgumentException e) {
            assertEquals("Validate error message in case of incorrect directory name",
                    "Input error: \"" + targetDirectoryName + "\" is not a directory",
                    e.getMessage());
        }
    }

    // Positive cases
    @Test
    public void testFileFound() {
        String fileName = "installed.txt";
        SearchFileInFolder fileSearcher = new SearchFileInFolder(currentDirectory, fileName);
        try{
            fileSearcher.searchFileAndPrint(); // here we specified all correct arguments, so there are no any Exceptions expected
        } catch (FileSearcherException e) {
        }
    }

    // TODO: more tests..

    public static junit.framework.Test suite(){
        return new JUnit4TestAdapter(FileSearcherTest.class);
    }
}
