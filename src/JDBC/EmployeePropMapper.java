/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author patri
 */
class EmployeePropMapper implements RowMapper{

    

    @Override
    public EmployeeStringProp mapRow(ResultSet rs, int i) throws SQLException {
       EmployeeStringProp empProp = new EmployeeStringProp(rs.getString("RFID"), rs.getString("forename"), rs.getString("surname"), rs.getString("employeeId"));
       return empProp;
    }
    
}
