////////////////////////////////////////////////////////////////////////////////
// TabModel.java
// ============
// Model class used for managing tabs and WebViews. Also responsible for creating new tabs
//
// AUTHOR: Vincent Romani (vromani@outlook.com)
// CREATED: 2018-03-19
// UPDATED: 2018-03-30
////////////////////////////////////////////////////////////////////////////////
package rombrowser;

import java.util.ArrayList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.web.WebView;

public class TabModel {

    //private ArrayList declated
    private ArrayList<RomTab> list;

    //Constructor for Tab Model. Initializes declated ArrayList.
    public TabModel() {
        list = new ArrayList<>();
    }

    //returns the TabModel array list
    final public ArrayList<RomTab> getList() {
        return list;
    }

    //gets the Tab of a specific index location in the arrayList
    final public RomTab getTab(int index) {
        return list.get(index);
    }

    //creates a new RomTab and adds to list
    final public void addTab() {
        list.add(new RomTab());
    }

    //deletes tab at specific index location
    public void deleteTab(int index) {
        list.remove(index);
    }

    //loads a specific page given the url and the tab index value
    public void loadPage(int index, String url) {
        WebView web = getList().get(index).getWebPage();
        web.getEngine().load(url);
    }

    //gets url of specific browser
    public String getURL(int index) {
        return getTab(index).getWebPage().getEngine().getLocation();
    }

    //inner class for creating Tab objects. Contains a webpage and an index value
    class RomTab extends Tab {

        private WebView webPage;
        final int index;

        //constructor, sets index value, puts a new WebView in it, and adds an event handler for closing tabs.
        //Also binds the tab title to the engine title
        public RomTab() {
            //sets index value to number of tabs
            index = list.size();

            //initiates webview
            webPage = new WebView();

            //add webview to tab
            this.setContent(webPage);

            //on close method for tab. Terminates program if it is the only tab
            this.setOnClosed(new EventHandler<Event>() {
                @Override
                public void handle(Event arg0) {
                    if (list.size() == 1) {
                        System.exit(0);
                    } else {
                        deleteTab(index);
                    }
                }
            });

            this.textProperty().bind(webPage.getEngine().titleProperty());
        }

        //method for getting the webpage from the tab
        public WebView getWebPage() {
            return webPage;
        }

    }
}
