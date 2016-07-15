package driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Curl {
  
  /**
   * Creates a new textfile in the current directory with the contents from
   * the given url
   * 
   * @param fileSys The fileSystem that is being worked with
   * @param website The given URL that is providing the text file contents
   * @throws IOException The error thrown if the URL is not valid
   */
  public static void curl(FileSystem fileSys, String website) 
      throws IOException {
    
    // read the contents from the URL
    URL url = new URL(website);
    BufferedReader reader = new BufferedReader(
        new InputStreamReader(url.openStream()));
    
    // store the contents from the URL
    String line;
    String contents = "";
    while ((line = reader.readLine()) != null) {
      contents += line + "\n";
    }
    reader.close();
    
    
    
    // create a new textfile that stores the URL contents
    TextFile file = new TextFile(
        website.substring(website.lastIndexOf("/") + 1), contents);
    
    // store this file in the current directory
    Directory currentDir = fileSys.getCurrentDirectory();
    currentDir.storeFile(file);
    
  }
  
  public static void main(String[] args) throws IOException {
    FileSystem fileSys = new FileSystem();
    curl(fileSys, "www.textfiles.com/100/easymoney.ana");
  }
}
