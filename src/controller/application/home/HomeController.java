/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.application.home;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author rifat
 */
public class HomeController implements Initializable {
    @FXML
    private Label lblTotEmp;
    @FXML
    private Label lblTotHWeek;
    @FXML
    private Label lblTotHToday;
    @FXML
    private Label lblConnectionStatus;
    @FXML
    private Label lblDowntime;
    @FXML
    private Label lblActiveEmp;
    @FXML
    private Text txtTime;
    @FXML
    private Text txtDate;
    
    private Date date;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        date = new Date();
        txtDate.setText(dateFormat.format(date));
    }    
}
