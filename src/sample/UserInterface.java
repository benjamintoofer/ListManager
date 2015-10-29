package sample;

/**
 * Created by benjamintoofer on 10/29/15.
 */

import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class UserInterface {

    private int winWidth;
    private int winHeight;

    //Scene
    private Scene scene;

    //Menu Bar
    private MenuBar menuBar;
    private Menu fileMenu;
    private MenuItem saveMenuItem;
    private MenuItem saveAsMenuItem;
    private MenuItem openMenuItem;


    public UserInterface(int width,int height,Scene scene){

        this.winWidth = width;
        this.winHeight = height;
        this.scene = scene;
    }

    private void init(){

        //Instantiate Menu Items
        saveMenuItem = new MenuItem("Save");
        saveAsMenuItem = new MenuItem("Save As...");
        openMenuItem = new MenuItem("Open...");

        //Instantiate Menu
        fileMenu = new Menu("File");

        //Instantiate Menu Bar
        menuBar = new MenuBar();

        fileMenu.getItems().addAll(openMenuItem,saveMenuItem,saveAsMenuItem);
        menuBar.getMenus().add(fileMenu);

        



    }
    public int getWinWidth()
    {
        return winWidth;
    }

    public int getWinHeight()
    {
        return winHeight;
    }
}
