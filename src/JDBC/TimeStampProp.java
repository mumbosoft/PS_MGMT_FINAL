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
public class TimeStampProp {
    
    private SimpleStringProperty RFID, time, inOut;
    
    
    /**
     * Constructor. 
     * @param RFID The RFID number corresponding to this employee.
     * @param Time 
     * @param inout
     */
    public TimeStampProp(String RFID, String Time, String inout) {
        this.RFID = new SimpleStringProperty(RFID);
        Time = Time.replaceAll("\\.0", "");
        this.time = new SimpleStringProperty(Time);
        this.inOut = new SimpleStringProperty(inout);
    }
    
    public StringProperty RFIDproperty(){
        return RFID;
    }
    public String getRFID(){
        return RFID.get();
    }
    public void setRFID(String rfid) {
        RFID.set(rfid);
    }
    
    
    
    
    public StringProperty timeproperty(){
        return time;
    }
    public String getTime(){
        return time.get();
    }
    public void setTime(String timey) {
        time.set(timey);
    }
    
    
    
    
    
    public StringProperty inOutproperty(){
        return inOut;
    }
    public String getInOut(){
        return inOut.get();
    }
    public void setInOut(String InOUT) {
        inOut.set(InOUT);
    }
    
}
