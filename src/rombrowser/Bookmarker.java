////////////////////////////////////////////////////////////////////////////////
// Bookmarker.java
// ============
// Class used for creating a bookmarking object.
// Object writes to a local html file by appending the inputted URL into the webpage
//
// AUTHOR: Vincent Romani (vromani@outlook.com)
// CREATED: 2018-03-19
// UPDATED: 2018-03-30
////////////////////////////////////////////////////////////////////////////////

package rombrowser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Bookmarker {
    
    public void bookmarkPage(String url){
        //Declare new arraylist for the lines in the document
    ArrayList<String> lines = new ArrayList<>();
    
    //declare varibale for line that will be used to store document lines after being read.
        String line;
        
        // use try-with-resources
        try (FileReader fr = new FileReader("./src/html/newtab.html");
                //declare and initiate buffered reader for given file reader.
                BufferedReader br = new BufferedReader(fr)) {
            // read line by line and store to lines
            while ((line = br.readLine()) != null) {
                //adds line that was read to lines array
                lines.add(line);
            }
        } catch (IOException e) {
            //prints error message
            System.err.println("[ERROR] " + e.getMessage());
        }

        //writes all lines while appending bookmarked line in the middle
        try(BufferedWriter writer= new BufferedWriter(new FileWriter(new File("./src/html/~newtab.html"),false))) {
            //declate new file to write to
            File file = new File("./src/html/~newtab.html");
            //create new file
            file.createNewFile();
            //loop for the length of the arraylist
            for (String z : lines) {

                //check for the bookmark div, which is where the added bookmark will be placed.
                if (z.contains("<div id='bookmarks'>")) {
                    
                    //writes the line, and the next line which contains the header
                    writer.write(z);
                    writer.newLine();
                    writer.write(z);
                    writer.newLine();
                    
                    //Writes the new bookmark
                    writer.write("<a href=\"" + url + "\"><img src=\"http://www.google.com/s2/favicons?domain=" + url + "\" alt='link' style='width:75px; height: 75px;'/></a> <br>");
                    writer.newLine();
                } else {
                    writer.write(z);
                    writer.newLine();
                }
            }
        //Deletes the old file
        Files.delete(Paths.get("./src/html/newtab.html"));

        //renames new file to replace the old file
        file.renameTo(new File("./src/html/newtab.html"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
