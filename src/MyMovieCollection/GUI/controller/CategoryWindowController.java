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
// This will close the category window
    @FXML
    private void clickCancelCategory (ActionEvent event) 
    {
        isEditing = false;
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
// This saves the new data you put in the category window
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

    public void setController(MainWindowController controller, boolean isEditing, int categoryID) // This method allows us to get connection with our MainWindowController and will check whether we are creating or editing
    {
        this.MainWController = controller;
        this.isEditing = isEditing;
        this.CategoryNewID = categoryID;
    }
}