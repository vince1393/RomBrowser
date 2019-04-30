////////////////////////////////////////////////////////////////////////////////
// URLConcerter.java
// ============
// Object class used for creating a URLConverter object.
// Object can take a URL and determine if valid, and if not it will convert to a google search
//
// AUTHOR: Vincent Romani (vromani@outlook.com)
// CREATED: 2018-03-19
// UPDATED: 2018-03-30
////////////////////////////////////////////////////////////////////////////////

package rombrowser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLConverter {

    String url;

    public String convertURL(String url) {
        if (checkValidURL(url)) {
            return this.url;
        } else {
            //If check cannot find website, redirects to google search
            return ("http://www.google.ca/search?q=" + url.replaceAll(" ", "+") + "&");
        }
    }

    private boolean checkValidURL(String url) {
        //checks for spaces, if theres spaces, it is not a URL. Returns false.
        if (url.contains(" ")) {
            return false;
        }
        if (!(url.toLowerCase().startsWith("http://") && !url.toLowerCase().startsWith("https://"))) {
            //adds http:// to the extension if it is not in the URL
            String url2 = "http://" + url;
            if (!testConnectionURL(url2)) {
                return false;
            } else {
                this.url = url2;
                return true;
            }
        }
        if (!testConnectionURL(url)) {
            return false;
        }
        return true;
    }

    private boolean testConnectionURL(String url) {
        try {
            //create URL object to begin testing connection
            URL myurl = new URL(url);
            //opens connection with URL object
            HttpURLConnection connection = (HttpURLConnection) myurl.openConnection();
            //The following code changes the request type to head in order to reduce transfer load 
            connection.setRequestMethod("HEAD");
            //tries to get response from URL
            connection.getResponseCode();
            //returns true if website responds
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
