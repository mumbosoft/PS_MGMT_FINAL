/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.application1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author rifat
 */
public class EmployeController implements Initializable {
    @FXML
    private MenuItem btnViewEmployee;
    @FXML
    private MenuItem btnAddEmployee;
    
    private String userId;
    
    @FXML
    private StackPane spEmployeCont;
    @FXML
    public BorderPane bpContent;
    @FXML
    private Label lblView;
    @FXML
    private ImageView ivEmployeIcon;
    
    Image image = new Image("/icon/d.png");

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ivEmployeIcon.setImage(image);
        lblView.setText("Anställda");
    }    

    @FXML
    public void btnViewEmployeeOnAction(ActionEvent event) throws IOException {
        lblView.setText("Översikt");
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.load(getClass().getResource("/view/application/employee/ViewEmploye.fxml").openStream());
        
        AnchorPane acPane = fXMLLoader.getRoot();
        
        spEmployeCont.getChildren().clear();
        
        spEmployeCont.getChildren().add(acPane);
        
    }

    @FXML
    private void btnAddEmployeeOnClick(ActionEvent event) throws IOException {
        lblView.setText("Lägg till ny anställd");
        
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.load(getClass().getResource("/view/application/employee/AddEmploye.fxml").openStream());
        
        
        AnchorPane acPane = fXMLLoader.getRoot();
        
        spEmployeCont.getChildren().clear();
        
        spEmployeCont.getChildren().add(acPane);
        
    }

    
}
