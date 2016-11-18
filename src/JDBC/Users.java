/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import java.sql.Blob;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 *
 * @author patri
 */
public class Users {
    public ObservableList<String> allUser = FXCollections.observableArrayList();

    public String RFID;
    public String forname;
    public String surname;
    public String empId;

    public ObservableList<EmployeeStringProp> employeeLists = FXCollections.observableArrayList();


}
