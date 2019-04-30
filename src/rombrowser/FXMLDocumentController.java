////////////////////////////////////////////////////////////////////////////////
// FXMLDocumentController.java
// ============
// Controller class for RomBrowser JavaFX GUI
//
// AUTHOR: Vincent Romani (vromani@outlook.com)
// CREATED: 2018-03-19
// UPDATED: 2018-03-30
////////////////////////////////////////////////////////////////////////////////

package rombrowser;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private Button forwardButton;
    @FXML
    private TextField urlBox;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Tab addTab;
    @FXML
    private TabPane tabPane;
    @FXML
    private MenuItem bookmarkMenu;
    @FXML
    private AnchorPane toolbarAnchor;
    @FXML
    private ImageView image;
    @FXML
    private MenuItem goToBookmark;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private AnchorPane AnchorPane;

    TabModel tabs = new TabModel();
    private URLConverter convert;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //creates new tab to begin
        makeNewTab();
        
        //binds the background image to the size of the window
        image.fitWidthProperty().bind(AnchorPane.widthProperty());
        image.fitHeightProperty().bind(AnchorPane.heightProperty());

        //initializes URL Converter
        convert = new URLConverter();

        updateNavigationButtons();

        //creates a ChangeListener to change the URL box and buttons depending on the selected tab
        tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if ((int) newValue < tabs.getList().size()) {
                    urlBox.setText(tabs.getURL((int) newValue));
                    updateNavigationButtons();
                }
            }
        }
        );
    }

    @FXML
    private void goBack(ActionEvent event) {
        //saves index of current selected tab
        int tabIndex = tabPane.getSelectionModel().getSelectedIndex();

        //gets webpage from tabmodel
        WebView webPage = tabs.getTab(tabIndex).getWebPage();

        //gets history from tabmodel
        final WebHistory history = getCurrentEngine().getHistory();

        //moves the webpage 1 page forward
        history.go(-1);

        //sets URL to new location
        urlBox.setText(getCurrentEngine().getLocation());

        updateNavigationButtons();
    }

    @FXML
    private void goForward(ActionEvent event) {
        //saves index of current selected tab
        int tabIndex = tabPane.getSelectionModel().getSelectedIndex();

        //gets webpage from tabmodel
        WebView webPage = tabs.getTab(tabIndex).getWebPage();

        //gets history from tabmodel
        final WebHistory history = getCurrentEngine().getHistory();

        //moves the webpage 1 page forward
        history.go(+1);

        //sets URL to new location
        urlBox.setText(getCurrentEngine().getLocation());

        updateNavigationButtons();
    }

    //refresh button which reloads the current engine
    private void refreshWebsite(MouseEvent event) {
        getCurrentEngine().reload();
    }

    @FXML
    private void handleGo(ActionEvent event) {
        //sets the webview opacity to full
        getCurrentWebview().setOpacity(1);
        //sets URL in the URL box to String variable
        String url = urlBox.getText();

        //runs only if search bar is not empty
        if (!url.equals("")) {

            //convert string to URL
            url = convert.convertURL(url);

            //gets index of selected tab
            int index = tabPane.getSelectionModel().getSelectedIndex();
            
            //loads page of the current
            tabs.loadPage(index, url);

            urlBox.setText(url);

            updateNavigationButtons();

        }
    }

    //selects all when the url box is clicked.
    @FXML
    private void selectAll(MouseEvent event) {
        urlBox.selectAll();
    }

    //Bookmarks current page when the bookmark menu button is pressed
    @FXML
    private void handleBookmarkCurrent(ActionEvent event) {
        Bookmarker b = new Bookmarker();
        b.bookmarkPage(getCurrentWebview().getEngine().getLocation());
    }

    //Loads Bookmark page
    @FXML
    private void loadBookmarkPage(ActionEvent event) {
        URL u = getClass().getResource("/html/newtab.html");
        getCurrentEngine().load(u.toExternalForm());

    }

    //creates a new tab when the new tab button is selected if there is more than 1 tab open (itself included)
    @FXML
    private void handleNewTab(Event event) {
        if (tabPane.getTabs().size() > 1) {
            makeNewTab();
        }
    }

    //method used for updating the back and forward buttons. Issue with webview engine history causing it to only work after 3 history entries.
    public void updateNavigationButtons() {

        //get the current web history of the currently selected tab
        final WebHistory currentTabHistory = getCurrentEngine().getHistory();

        //if statements to determine which tabs to enable
        if (currentTabHistory.getCurrentIndex() <= 0) {
            backButton.setDisable(true);
        } else {
            backButton.setDisable(false);
        }
        if ((currentTabHistory.getCurrentIndex() + 1 >= currentTabHistory.getEntries().size())) {
            forwardButton.setDisable(true);
        } else {
            forwardButton.setDisable(false);
        }
    }

    //this method links the controller and the Tab Model. Is used to simplify code
    //Gets the selected tab index and returns the Webview assiciated to it.
    public WebView getCurrentWebview() {
        int currentTabIndex = tabPane.getSelectionModel().getSelectedIndex();
        return tabs.getTab(currentTabIndex).getWebPage();
    }

    //this method is to improve readability of code. uses above method to reduce code
    public WebEngine getCurrentEngine() {
        return getCurrentWebview().getEngine();
    }

    //makeNewTab method calls upon the TabModel.java object and uses the addTab method to create a new tab within the model
    private void makeNewTab() {
        
        //create new tab in tabs model
        tabs.addTab();
        
        //gets index position of the last tab before the + button
        int position = tabPane.getTabs().size() - 1;

        //adds tab to tab pane given the correct location
        tabPane.getTabs().add(position, tabs.getList().get(position));

        //sets the new tab as the selected tab
        tabPane.getSelectionModel().select(position);
        
        //sets opacity to .5
        getCurrentWebview().setOpacity(.5);
        
        //binds the tab to the size of the tabPane
        getCurrentWebview().prefHeightProperty().bind(tabPane.heightProperty());
        getCurrentWebview().prefWidthProperty().bind(tabPane.widthProperty());
    }

}
