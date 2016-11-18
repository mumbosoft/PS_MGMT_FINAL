package JDBC;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.*;

/**
 * A class for communicating with the passagesystemdb database. For proper
 * functionality, the following prerequisites must be met:
 *
 * There must be a database named passagesystemdb This database must contain 2
 * tables of which each named accordingly: "Employees" - a table that holds a
 * record of employees intended to use the system. "TimeStamps" - a table that
 * holds points in time coupled with a specific RFID from the "Employees"
 * -table.
 *
 * In turn the "Employees" -table must contain the following columns: "RFID" -
 * The Employees personal RFID number. "forename" - the forename of the
 * employee. "surname" - the surname of the employee. "employeeId" - The
 * personal id of the employee.
 *
 * The "TimeStamps" -table must contain the following columns: "RFID" - the RFID
 * coupled to the corresponding point in time. "time" - the date and time.
 * "inOut" - whether or not this point in time corresponds to an entry or exit
 * of the facility.
 *
 * @author Albin
 */
public class PSJDBCTemplate implements PSDBInterface {

    private SingleConnectionDataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    /**
     * Constructor.
     */
    public PSJDBCTemplate() {
        this.setDataSource(this.getSingleConnectionDataSource());
    }

    /**
     * This is the method to be used to initialize database resources ie.
     * connection.
     *
     * @param ds The DataSource object that defines the connection.
     */
    @Override
    public void setDataSource(DataSource ds) {
        try {
            this.dataSource = (SingleConnectionDataSource) ds;
            this.dataSource.getConnection();
            this.jdbcTemplateObject = new JdbcTemplate(dataSource);
        } catch (SQLException ex) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText("Anslutning mot databasen kunde inte etableras!");
            al.setContentText("Vänligen kontrollera IP-addressen");
            al.showAndWait();
        }
    }

    /**
     * Creates a record in the "Employee" table based on the information stored
     * within the argument.
     *
     * @param emp The instance of Employee which to store in the table.
     */
    @Override
    public void createEmployee(Employee emp) throws DataAccessException,
            CannotGetJdbcConnectionException {
        String SQL = "INSERT INTO Employees (RFID, forename, surname, "
                + "employeeId) VALUES (?, ?, ?, ?)";

        jdbcTemplateObject.update(SQL, emp.getRFID(), emp.getForename(),
                emp.getSurname(), emp.getEmployeeId());
        System.out.println("Created Employee = " + emp);
        return;
    }

    /**
     * @param RFID: The RFID corresponding to a Employee in the "Employees"
     * table.
     * @return The employee corresponding to the specified RFID code.
     */
    @Override
    public Employee getEmployee(String RFID) throws CannotGetJdbcConnectionException,
            IncorrectResultSizeDataAccessException, DataAccessException {
        String SQL = "SELECT * FROM Employees WHERE RFID = ?";
        try {
            Employee emp = jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{RFID}, (RowMapper<Employee>) (new EmployeeMapper()));
            return emp;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (CannotGetJdbcConnectionException ex) {
            throw ex;
        }
    }

    /**
     * @return A list of all the Employees in the database.
     */
    @Override
    public List<Employee> listEmployees() throws DataAccessException,
            CannotGetJdbcConnectionException {
        String SQL = "SELECT * FROM Employees";
        List<Employee> employees = jdbcTemplateObject.query(SQL,
                (RowMapper<Employee>) (new EmployeeMapper()));
        return employees;
    }

    /**
     * Deletes the employee coupled to the specified RFID code.
     *
     * @param rfid: The RFID corresponding to the employee to be deleted.
     */
    @Override
    public void deleteEmployee(RFID rfid) throws DataAccessException,
            CannotGetJdbcConnectionException {
        String SQL = "DELETE FROM Employees WHERE RFID = ?";
        jdbcTemplateObject.update(SQL, rfid.toString());
        System.out.println("Deleted Record with RFID = " + rfid.toString());
    }

    /**
     * Updates the employee coupled with the specified RFID. note that it does
     * not update the RFID.
     *
     * @param rfid
     * @param forename
     * @param surname
     * @param employeeId
     * @return true if a record with the specified RFID was encountered in the
     * database else, false.
     */
    @Override
    public boolean updateEmployee(
            RFID rfid, String forename, String surname, String employeeId)
            throws DataAccessException, CannotGetJdbcConnectionException {
        String SQL = "UPDATE Employees SET forename = ?, surname = ?, "
                + "employeeId = ? WHERE RFID = ?";
        int status = jdbcTemplateObject.update(SQL, forename, surname, employeeId, rfid.toString());

        System.out.println("Updated record with RFID = " + rfid.toString() + "\tStatus = " + status);

        return status == 1;
    }

    /**
     * Creates a record in the "Employee" table based on the information stored
     * within the argument.
     *
     * @param RFID The Rfid instance of Employee which to store in the table.
     * @param Forename The forname instance of Employee which to store in the
     * table
     * @param Lastname The Lastname instance of Employee which to store in the
     * table
     * @param EmpId The EmployeeId instance of Employee which to store in the
     * table
     */
    @Override
    public void createEmployeeFromUserGUI(String RFID, String Forename, String Lastname, String EmpId)
            throws DataAccessException, CannotGetJdbcConnectionException {
        String SQL = "INSERT INTO Employees (RFID, forename, surname, "
                + "employeeId) VALUES (?, ?, ?, ?)";

        jdbcTemplateObject.update(SQL, RFID, Forename,
                Lastname, EmpId);
        System.out.println("Created Employee = " + " Rfid: " + RFID + " Forename: " + Forename + " Lastname: " + Lastname + " EmpId: " + EmpId);
        return;
    }

    /**
     * Adds a timestamp to the "Times" table in the database.
     *
     * @param stamp A object containing the current time and associated RFID
     * number.
     * @throws jdbctemplate.SignedInException
     * @throws jdbctemplate.SignedOutException
     */
    @Override
    public void addRFIDTimeStamp(RFIDTimestamp stamp) throws SignedInException,
            SignedOutException, CannotGetJdbcConnectionException,
            IncorrectResultSizeDataAccessException, DataAccessException {
        String SQL = "SELECT * FROM SignedIn WHERE RFID = ?";
        String rfid;
        try {
            rfid = jdbcTemplateObject.queryForObject(
                    SQL, new Object[]{stamp.getRFID().toString()},
                    (RowMapper<String>) (new StringMapper()));
        } catch (EmptyResultDataAccessException ex) {
            rfid = "";
        }

        switch (stamp.getInOut()) {
            case "IN":
                if (rfid.length() == 0) {
                    SQL = "INSERT INTO SignedIn VALUES (?)";
                    jdbcTemplateObject.update(SQL, stamp.getRFID().toString());
                    SQL = "INSERT INTO Times VALUES (?, ?, ?)";
                    jdbcTemplateObject.update(SQL, stamp.getRFID().toString(), stamp.getTime(), stamp.getInOut());
                    System.out.println("Added timestamp = " + stamp);
                } else {
                    throw new SignedInException();
                }
                break;
            case "OUT":
                if (rfid.length() == 0) {
                    throw new SignedOutException();
                } else {
                    SQL = "DELETE FROM SignedIn WHERE RFID = ?";
                    jdbcTemplateObject.update(SQL, stamp.getRFID().toString());
                    SQL = "INSERT INTO Times VALUES (?, ?, ?)";
                    jdbcTemplateObject.update(SQL, stamp.getRFID().toString(), stamp.getTime(), stamp.getInOut());
                    System.out.println("Added timestamp = " + stamp);
                }
                break;
        }
    }

    /**
     * @param rfid
     * @return A list containing all the timestamps corresponding to the
     * specified RFID.
     */
    @Override
    public List<RFIDTimestamp> getTimestamps(RFID rfid)
            throws DataAccessException, CannotGetJdbcConnectionException {
        String SQL = "SELECT * FROM Times WHERE RFID = ?";
        List<RFIDTimestamp> employees = jdbcTemplateObject.query(
                SQL,
                new Object[]{rfid.toString()},
                new RFIDTimestampMapper());
        return employees;
    }

    /**
     * @return A list of all the Employees in the database.
     */
    @Override
    public List<EmployeeStringProp> listEmployeesProp()
            throws DataAccessException, CannotGetJdbcConnectionException {
        String SQL = "SELECT * FROM Employees";
        List<EmployeeStringProp> employees = jdbcTemplateObject.query(SQL,
                (RowMapper<EmployeeStringProp>) (new EmployeePropMapper()));
        return employees;
    }

    /**
     * @return A list of all the Times in the database.
     */
    @Override
    public List<TimeStampProp> listTimeProp()
            throws DataAccessException, CannotGetJdbcConnectionException {
        String SQL = "SELECT * FROM Times";
        List<TimeStampProp> times = jdbcTemplateObject.query(SQL,
                (RowMapper<TimeStampProp>) (new timePropMapper()));
        return times;
    }
//    /**
//     * Updates the time coupled with the specified Time.
//     *
//     * @param timedate
//     * @param RFID
//     * @return true if a record with the specified RFID and Time was encountered in the
//     * database else, false.
//     */
//    @Override
//    public boolean updateTime(RFID rfid, Timestamp curtime, Timestamp newtime)
//            throws DataAccessException, CannotGetJdbcConnectionException {
//        String SQL = "UPDATE Times SET time = ?,  WHERE RFID = ?";
//        int status = jdbcTemplateObject.update(SQL, forename, surname, employeeId, rfid.toString());
//
//        System.out.println("Updated record with RFID = " + rfid.toString() + "\tStatus = " + status);
//
//        return status == 1;
//    }

    /**
     * Updates the employee coupled with the specified RFID. note that it does
     * not update the RFID.
     *
     * @param RFID
     * @param forename
     * @param surname
     * @param employeeId
     * @return true if a record with the specified RFID was encountered in the
     * database else, false.
     */
    public boolean UpdateEmployee(
            String RFID, String forename, String surname, String employeeId)
            throws DataAccessException, CannotGetJdbcConnectionException {
        String SQL = "UPDATE Employees SET forename = ?, surname = ?, "
                + "employeeId = ? WHERE RFID = ?";
        int status = jdbcTemplateObject.update(SQL, forename, surname, employeeId, RFID);

        System.out.println("Updated record with RFID = " + RFID + "\tStatus = " + status);

        return status == 1;
    }

    /**
     * Updates the employee coupled with the specified RFID. note that it does
     * not update the RFID.
     *
     * @param curtime
     * @param newtime
     * @return true if a record with the specified curTime was encountered in
     * the database else, false.
     */
    public boolean UpdateTime(Timestamp curtime, Timestamp newtime)
            throws DataAccessException, CannotGetJdbcConnectionException {
        String SQL = "UPDATE Times SET time = ? WHERE time = ?";
        int status = jdbcTemplateObject.update(SQL, newtime, curtime);

        System.out.println("Updated record with current Time = " + curtime + " to " + newtime + " \tStatus = " + status);

        return status == 1;
    }

    /**
     * @return A list containing all the timestamps
     */
    @Override
    public List<RFIDTimestamp> getAllTimestamps()
            throws DataAccessException, CannotGetJdbcConnectionException {
        String SQL = "SELECT * FROM Times";
        List<RFIDTimestamp> list = jdbcTemplateObject.query(SQL,
                (RowMapper<RFIDTimestamp>) (new RFIDTimestampMapper()));
        return list;
    }

    /**
     * Returns a list containing all the signed in rfid's.
     *
     * @return
     */
    @Override
    public List<RFID> getAllSignedIn()
            throws DataAccessException, CannotGetJdbcConnectionException {
        String SQL = "SELECT * FROM SignedIn";
        List<RFID> list = jdbcTemplateObject.query(SQL,
                (RowMapper<RFID>) (new RFIDMapper()));
        return list;
    }

    /**
     * Creates a connection to the database.
     *
     * @return The instantiated DataSource object that defines the connection to
     * the database.
     */
    private SingleConnectionDataSource getSingleConnectionDataSource() {
        String ip = "";
        try {
            Scanner scan = new Scanner(new File("database_address.txt"));
            if (scan.hasNextLine()) {
                ip = scan.nextLine();
            }
        } catch (FileNotFoundException ex) {
            System.err.println("You have to add a file in root directory named: "
                    + "\"database_address.txt\" containing the ip-adress to the "
                    + "server.");
        }

        String url = "jdbc:mysql://" + ip + ":3306/passagesystemdb";
        SingleConnectionDataSource dataSource = new SingleConnectionDataSource(url, false);
        dataSource.setUsername("Albin");
        dataSource.setPassword("APOL1337");

        return dataSource;
    }
}
