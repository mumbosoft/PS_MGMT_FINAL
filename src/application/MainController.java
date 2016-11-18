/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import controller.application1.EmployeController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author patri
 */
public class MainController implements Initializable {
    
    
    
    Image home = new Image("/icon/home.png");
    Image homeRed = new Image("/icon/homeRed.png");
    Image employee = new Image("/icon/employe.png");
    Image employeeRed = new Image("/icon/employeRed.png");
    Image setting = new Image("/icon/settings.png");
    Image settingRed = new Image("/icon/settingsRed.png");
    String defultStyle = "-fx-border-width: 0px 0px 0px 5px;"
            + "-fx-border-color:none";

    String activeStyle = "-fx-border-width: 0px 0px 0px 5px;"
            + "-fx-border-color:#FF4E3C";
    
    @FXML
    private AnchorPane acContent;
    @FXML
    private ImageView imgHomeBtn;
    @FXML
    private Button btnHome;
    @FXML
    private ImageView imgEmployeeBtn;
    @FXML
    private Button btnEmployee;
    @FXML
    private ImageView imgSettingsBtn;
    @FXML
    private Button btnSettings;
    
    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnHomeOnClick();
        
    }
    
    @FXML
    public void btnHomeOnClick(){
        homeActive();
        System.out.println("hej");
        FXMLLoader fxmlLoader = new FXMLLoader();
        
        try {
            fxmlLoader.load(getClass().getResource("/view/application/home/Home.fxml").openStream());
        } catch (IOException e) {
            
        }
        
        AnchorPane root = fxmlLoader.getRoot();
        acContent.getChildren().clear();
        acContent.getChildren().add(root);

    }
    
    @FXML
    private void btnEmplopyeeOnClick(ActionEvent event) throws IOException {
        employeeActive();
        EmployeController ec = new EmployeController();
        FXMLLoader fXMLLoader = new FXMLLoader();
        try {
            fXMLLoader.load(getClass().getResource("/view/application/employee/ViewEmploye.fxml").openStream());
        } catch (IOException e) {
            
        }

        AnchorPane acPane = fXMLLoader.getRoot();

        acContent.getChildren().clear();

        acContent.getChildren().add(acPane);

    }
    @FXML
    private void btnSettingsOnClick(ActionEvent event) throws IOException {
        settingsActive();
        FXMLLoader fXMLLoader = new FXMLLoader();
        try {
            fXMLLoader.load(getClass().getResource("/view/application/settings/Settings.fxml").openStream());
        } catch (IOException e) {
            
        }

        AnchorPane acPane = fXMLLoader.getRoot();

        acContent.getChildren().clear();

        acContent.getChildren().add(acPane);

    }
    
    

    private void homeActive() {
        imgHomeBtn.setImage(homeRed);
        imgEmployeeBtn.setImage(employee);
        imgSettingsBtn.setImage(setting);
        btnHome.setStyle(activeStyle);
        btnEmployee.setStyle(defultStyle);
        btnSettings.setStyle(defultStyle);
    }
    private void employeeActive() {
        imgHomeBtn.setImage(home);
        imgEmployeeBtn.setImage(employeeRed);
        imgSettingsBtn.setImage(setting);
        btnHome.setStyle(defultStyle);
        btnEmployee.setStyle(activeStyle);
        btnSettings.setStyle(defultStyle);
    }
    private void settingsActive() {
        imgHomeBtn.setImage(home);
        imgEmployeeBtn.setImage(employee);
        imgSettingsBtn.setImage(settingRed);
        btnHome.setStyle(defultStyle);
        btnEmployee.setStyle(defultStyle);
        btnSettings.setStyle(activeStyle);
    }
    
}
