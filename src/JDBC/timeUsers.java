/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author patri
 */
public class timeUsers {
    public ObservableList<String> allUser = FXCollections.observableArrayList();

    public String RFID;
    public String time;
    public String inOut;

    public ObservableList<EmployeeStringProp> timeLists = FXCollections.observableArrayList();


}

