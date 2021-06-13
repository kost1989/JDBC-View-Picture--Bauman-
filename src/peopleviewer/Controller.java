package peopleviewer;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML private TextField nameTextField;
    @FXML private TextField yearTextField;
    @FXML private ListView<String> listView;



    private Task task = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listView.setItems(FXCollections.observableArrayList(MySQL.loadPeoples()));
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    String[] outputString = MySQL.loadHuman(Integer.parseInt(
                            listView.getSelectionModel().getSelectedIndices().toString().charAt(1) + "") + 1);
                    nameTextField.setText(outputString[0]);
                    yearTextField.setText(outputString[1]);
                }
            }
        });
    }




    @FXML
    public void uploadButton(ActionEvent event) {
        try {
            MySQL.uploadHuman(nameTextField.getText(), Integer.parseInt(yearTextField.getText()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        listView.getItems().clear();
        listView.setItems(FXCollections.observableArrayList(MySQL.loadPeoples()));
    }

    public void loadButton(ActionEvent event) {
        listView.getItems().clear();
        listView.setItems(FXCollections.observableArrayList(MySQL.loadPeoples()));
    }

    public void searchButton(ActionEvent event) {
        listView.getItems().clear();
        listView.setItems(FXCollections.observableArrayList(MySQL.searchHuman(nameTextField.getText(),
                yearTextField.getText())));
    }
}
