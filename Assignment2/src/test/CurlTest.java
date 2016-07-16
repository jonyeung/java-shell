package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import driver.CommandException;
import driver.Curl;
import driver.File;
import driver.FileSystem;

/**
 * Class to test the Curl class
 */
public class CurlTest {

  FileSystem fileSystem;

  @Before
  public void setUp() {

    fileSystem = new FileSystem();
  }

  /**
   * Test if curl works correctly
   * 
   * @throws IOException The error thrown if a file cannot be read
   * @throws CommandException The error thrown if a invalid URL is provided
   */
  @Test
  public void testCurl() throws IOException, CommandException {

    Curl.curl(fileSystem, "http://www.cs.cmu.edu/~spok/grimmtmp/073.txt");
    File result = fileSystem.getRootDirectory().getStoredFiles().get(0);
    assertEquals(result.getName(), "073.txt");
  }

  /**
   * Test if getting a fake url will throw an exception
   * 
   * @throws IOException The error thrown if a file cannot be read
   */
  @Test
  public void testGetFakeURL() throws IOException {

    try {
      Curl.curl(fileSystem, "fakeURL");
      fail("URL does not exist.");
    } catch (CommandException e) {
    }
  }

}
