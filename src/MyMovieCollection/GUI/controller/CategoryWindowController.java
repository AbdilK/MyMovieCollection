package MyMovieCollection.GUI.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import MyMovieCollection.BE.Category;
import MyMovieCollection.GUI.model.MovieModel;

/*/*
 * @author Abdil-K, Bjarne666, Hassuni8, KerimTopci
 */

public class CategoryWindowController implements Initializable
{
    
    private MainWindowController MainWController;
    private boolean isEditing = false;
    private int CategoryNewID;
    private MovieModel cm;
    private Category category;
    @FXML
    private TextField txtNameCategory;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
/**
 * denne metode Initialiserer controller klassen
 * @param url
 * @param rb 
 */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        MainWController = new MainWindowController();
        cm = MovieModel.getInstance();
        category = cm.getCategory();
        if (category != null)
        {
            txtNameCategory.setText(category.getCategoryName());
        }
    }
/** 
 * denne metode vil lukke category vinduet.
 * @param event 
 */
    @FXML
    private void clickCancelCategory (ActionEvent event) 
    {
        isEditing = false;
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
/** 
 * denne metode gemmer de nye data du har puttet ind i category vinduet
 * @param event 
 */
    @FXML
    private void clickSaveCategory(ActionEvent event) 
    {
        if (!isEditing)
        {
            if (!txtNameCategory.getText().isEmpty())
            {
                Category category = new Category(cm.nextAvailableCategoryID(), txtNameCategory.getText());
                cm.createCategory(category);
                MainWController.refreshTableCategory();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        } else
        {
            if (!"".equals(txtNameCategory.getText()))
            {
                Category category = new Category(CategoryNewID, txtNameCategory.getText());
                cm.updateCategory(category);
                MainWController.refreshTableCategory();
                ((Node) (event.getSource())).getScene().getWindow().hide();
                isEditing = false;
            }
        }
    }

    /**
     * denne metode tillader os at få forbindelse med vores MainWindowController og vil checke om vi tilføjer eller redigere en film
     * @param controller
     * @param isEditing
     * @param categoryID
     */
    public void setController(MainWindowController controller, boolean isEditing, int categoryID)
    {
        this.MainWController = controller;
        this.isEditing = isEditing;
        this.CategoryNewID = categoryID;
    }
}