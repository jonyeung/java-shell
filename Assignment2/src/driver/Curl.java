package driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Curl {

  /**
   * Creates a new textfile in the current directory with the contents from the
   * given url
   * 
   * @param fileSys The fileSystem that is being worked with
   * @param website The given URL that is providing the text file contents
   * @throws IOException The error thrown if a file cannot be read
   * @throws CommandException The error thrown if a invalid URL is provided
   */
  public static void curl(FileSystem fileSys, String website)
      throws IOException, CommandException {

    // connect to the URL
    try {
      URL url = new URL(website);
      URLConnection urlConnection = url.openConnection();
      urlConnection.connect();
    } catch (MalformedURLException e) {
      throw new CommandException(website + " is not a valid URL");
    } catch (IOException e) {
      throw new CommandException("Connection to URL failed");
    }

    // read from the URL
    URL url = new URL(website);
    URLConnection urlConnection = url.openConnection();
    BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(urlConnection.getInputStream()));

    // get the contents from the file
    String line;
    String contents = "";
    while ((line = reader.readLine()) != null) {
      contents += line + "\n";
    }



    // create a new textfile that stores the URL contents
    TextFile file =
        new TextFile(website.substring(website.lastIndexOf("/") + 1), contents);

    // store this file in the current directory
    Directory currentDir = fileSys.getCurrentDirectory();
    currentDir.storeFile(file);

  }

}
