/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author patri
 */
public class EmployeeStringProp {
    
    private final SimpleStringProperty RFID, forename, surname, employeeId;

    /**
     * Constructor. 
     * @param RFID The RFID number corresponding to this employee.
     * @param forename 
     * @param surname
     * @param employeeId 
     */
    public EmployeeStringProp(String RFID, String forename, String surname, String employeeId) {
        this.RFID = new SimpleStringProperty(RFID);
        this.forename = new SimpleStringProperty(forename);
        this.surname = new SimpleStringProperty(surname);
        this.employeeId = new SimpleStringProperty(employeeId);
    }

    /**
     * Returns the RFID code of this employee.
     * @return RFID
     */
    public StringProperty RFIDproperty(){
        return RFID;
    }
    public String getRFID() {
        return RFID.get();
    }
    public void setRFID(String rfid) {
        RFID.set(rfid);
    }

    /**
     * Returns the forename of this employee.
     * @return forename
     */
    public StringProperty Forenameproperty(){
        return forename;
    }
    public String getForename() {
        return forename.get();
    }
    public void setForename(String forn) {
        forename.set(forn);
    }

    /**
     * Returns the surname of this employee. 
     * @return surname
     */
    public StringProperty Surnameproperty(){
        return surname;
    }
    public String getSurname() {
        return surname.get();
    }
    public void setSurname(String sur) {
        surname.set(sur);
    }

    /**
     * Returns the employee id of this employee.
     * @return employeeId.
     */
    public StringProperty EmpIdproperty(){
        return employeeId;
    }
    public String getEmployeeId() {
        return employeeId.get();
    }
    public void setEmployeeId(String empid) {
        employeeId.set(empid);
    }
}
