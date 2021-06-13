package pictureviewer;

import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML private TextField uploadTextField;
    @FXML private ImageView imageViewer;
    @FXML private ListView<String> listView;

    private Task task = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listView.setItems(FXCollections.observableArrayList(MySQL.loadInfo()));
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    imageViewer.setImage(MySQL.loadPicture(Integer.parseInt(
                            listView.getSelectionModel().getSelectedIndices().toString().charAt(1) + "") + 1));
                }
            }
        });
    }

    @FXML
    public void uploadButton(ActionEvent event) {
        MySQL.uploadPicture(uploadTextField.getText());
        listView.getItems().clear();
        listView.setItems(FXCollections.observableArrayList(MySQL.loadInfo()));
    }

}
