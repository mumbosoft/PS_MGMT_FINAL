/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template File, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.application.settings;

import JDBC.PSJDBCTemplate;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 * FXML Controller class
 *
 * @author patrick
 */
public class SettingsController implements Initializable {

    @FXML
    private TextField tfIpAddress;

    private File ipFile;
    private Scanner scan;
    private String ipAddress = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ipFile = new File("database_address.txt");
            scan = new Scanner(ipFile);

            if (scan.hasNext()) {
                tfIpAddress.setText(scan.next());
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void btnAnslutAction(ActionEvent e) {
        btnAction();
    }

    @FXML
    public void btnTestaAnslutningAction(ActionEvent e) {
        btnAction();
    }

    @FXML
    public void tfIpAddressAction(ActionEvent e) {
        ipAddress = getIpAddress();
        if(ipAddress.length() > 0) {
            sparaIp();
        }
    }

    public void sparaIp() {
        PrintWriter writer = null;
        try {
            ipAddress = getIpAddress();
            writer = new PrintWriter(ipFile);
            writer.println(ipAddress);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
    }

    private String getIpAddress() {
        String ip = tfIpAddress.getText();
        if (ip.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$")) {
            return ip;
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText("Fel format på IP-addressen!");
            al.setContentText("IP-addressen bör ha formatet: 0.0.0.0\n"
                    + "Exempel: 192.63.23.123");
            al.showAndWait();
            return "";
        }
    }

    private void btnAction() {
        ipAddress = getIpAddress();
        
        if (ipAddress.length() > 0) {
            sparaIp();
            String url = "jdbc:mysql://" + ipAddress + ":3306/passagesystemdb";
            SingleConnectionDataSource dataSource = new SingleConnectionDataSource(url, false);
            dataSource.setUsername("Albin");
            dataSource.setPassword("APOL1337");
            try {
                dataSource.getConnection();
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setHeaderText("Anlutning mot databasen har etablerats!");
                al.showAndWait();
            } catch (SQLException ex) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setHeaderText("Anslutning mot databasen kunde inte etableras!");
                al.setContentText("Vänligen kontrollera IP-addressen");
                al.showAndWait();
            } finally {
                dataSource.destroy();
            }
        }
    }
}
