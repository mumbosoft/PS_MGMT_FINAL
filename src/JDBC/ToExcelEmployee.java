/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import java.util.*;

/**
 *
 * @author Hejsan
 */
public class ToExcelEmployee extends Object implements Comparable{
    public String surname;
    public String lastname;
    public String id;
    public Double time;
    
    public ToExcelEmployee(String surname,String lastname, String id, Double time){
        this.surname = surname;
        this.id = id;
        this.time = time;
        this.lastname = lastname;
    }

    
    public int compareTo(ToExcelEmployee o) {
        return this.lastname.compareToIgnoreCase(o.lastname); 
   
    }
    @Override
    public int compareTo(Object o) {
        ToExcelEmployee temp = (ToExcelEmployee) o;
        return this.lastname.compareToIgnoreCase(temp.lastname);
    }
    @Override
    public String toString(){
       return  surname + " " + lastname + " " + id + "         Total tid: " + time;
    }

    
}
