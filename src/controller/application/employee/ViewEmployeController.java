/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.application.employee;

import JDBC.Employee;
import JDBC.EmployeeStringProp;
import JDBC.PSJDBCTemplate;
import JDBC.RFID;
import JDBC.RFIDTimestamp;
import JDBC.TimeStampProp;
import JDBC.ToExcelEmployee;
import JDBC.Users;
import JDBC.timeUsers;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import static java.sql.Timestamp.valueOf;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author rifat
 */
public class ViewEmployeController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField rfidTextField;
    @FXML
    private TextField forNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField employeeIdTextField;
    @FXML
    private TableView<EmployeeStringProp> tableView;
    @FXML
    private TableColumn<EmployeeStringProp, String> ForNameColumn;
    @FXML
    private TableColumn<EmployeeStringProp, String> LastNameColumn;
    @FXML
    private Button update;
    @FXML
    private StackPane spEmployeCont;
    @FXML
    private TableView<TimeStampProp> timeTableView;
    @FXML
    private TableColumn<TimeStampProp, String> RFIDColumn;
    @FXML
    private TableColumn<TimeStampProp, String> dateColumn;
    @FXML
    private TableColumn<TimeStampProp, String> inOutColumn;
    @FXML
    private TextField timeTf;
    @FXML
    private TextField inOutTf;
    @FXML
    private Button updtTime, editTime, remove;

    private List<TimeStampProp> timeList;

    private ObservableList timedata;

    private timeUsers timeUsers;

    private List<EmployeeStringProp> empList;

    private PSJDBCTemplate empTemp;

    private ObservableList empdata;

    private Users users;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Set up connection to database
        empTemp = new PSJDBCTemplate();
        updateTimeTblView();
        updateTblView();
        users = new Users();
        timeUsers = new timeUsers();
    }

    @FXML
    private void btnDltOnAction(ActionEvent event) {
        if (rfidTextField.getText().trim().equals("") || forNameTextField.getText().trim().equals("")
                || lastNameTextField.getText().trim().equals("")
                || employeeIdTextField.getText().trim().equals("")) {
            Alert fail = new Alert(Alert.AlertType.INFORMATION);
            fail.setContentText("Du måste markera en anställd");
            fail.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Ta bort");
            alert.setContentText("är du säker på att du vill ta bort " + forNameTextField.getText() + " " + lastNameTextField.getText() + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                empTemp.deleteEmployee(new RFID(rfidTextField.getText()));
                updateTblView();
                clearAll();
            }

        }

    }

    @FXML
    private void btnAddOnAction(ActionEvent event
    ) {

        Stage stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Lägg till Anställd");

        VBox vbox = new VBox();

        TextField RFID = new TextField();
        TextField forename = new TextField();
        TextField surname = new TextField();
        TextField empId = new TextField();

        RFID.setPromptText("Rfid nummer");
        forename.setPromptText("Förnamn");
        surname.setPromptText("Efternamn");
        empId.setPromptText("Anställnings id");

        Button confirmBtn = new Button();
        confirmBtn.setText("Lägg Till");

        vbox.getChildren().addAll(RFID, forename, surname, empId, confirmBtn);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(30, 30, 30, 30));

        Scene scene = new Scene(vbox);

        stage.setScene(scene);

        confirmBtn.setOnAction(e -> {

            if (RFID.getText().trim().equals("") || forename.getText().trim().equals("")
                    || surname.getText().trim().equals("")
                    || empId.getText().trim().equals("")) {
                Alert fail = new Alert(Alert.AlertType.INFORMATION);
                fail.setContentText("Du måste fylla i alla fält");
                fail.showAndWait();

            } else {
                empTemp.createEmployeeFromUserGUI(RFID.getText(), forename.getText(), surname.getText(), empId.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                updateTblView();
                alert.setHeaderText("Klart!");
                alert.setContentText("Ny Anställd tillagd");
                alert.showAndWait();
                stage.close();

            }

        });
        confirmBtn.requestFocus();
        stage.showAndWait();
    }

    @FXML
    private void btnUpdtOnAction(ActionEvent event
    ) {
        if (rfidTextField.getText().trim().equals("") || forNameTextField.getText().trim().equals("")
                || lastNameTextField.getText().trim().equals("")
                || employeeIdTextField.getText().trim().equals("")) {
            Alert fail = new Alert(Alert.AlertType.INFORMATION);
            fail.setContentText("Du måste fylla i alla fält");
            fail.showAndWait();

        } else {
            empTemp.UpdateEmployee(rfidTextField.getText(), forNameTextField.getText(), lastNameTextField.getText(), employeeIdTextField.getText());
            buttonDisables();
            setTfDisableTrue(); 
            updateTblView();
        }
    }

    @FXML
    public void btnHandleTimesOnAction(ActionEvent event) throws IOException {
        FXMLLoader fXMLLoader = new FXMLLoader();
        FXMLLoader.load(getClass().getResource("/view/application/employee/ViewTimesFXML.fxml"));

        AnchorPane acPane = fXMLLoader.getRoot();

        spEmployeCont.getChildren().clear();

        spEmployeCont.getChildren().add(acPane);

    }

    @FXML
    private void btnChangeOnAction(ActionEvent event
    ) {

        /*if(rfidTextField.getText().trim().equals("") || forNameTextField.getText().trim().equals("") ||
            lastNameTextField.getText().trim().equals("") ||
            employeeIdTextField.getText().trim().equals("")){
                
        }*/
        buttonEnables();
        setTfDisableFalse();
    }

    @FXML
    private void tfSearchOnAction(ActionEvent event
    ) {
    }

    private void clearAll() {
        rfidTextField.clear();
        forNameTextField.clear();
        lastNameTextField.clear();
        employeeIdTextField.clear();
    }

    public void tblEmployeeOnClick(Event event) {
        setselectedView();
    }

    @FXML
    private void tblViewOnClick(KeyEvent event) {
        if (event.getCode().equals(KeyCode.UP)) {
            setselectedView();
        } else if (event.getCode().equals(KeyCode.DOWN)) {
            setselectedView();
        }
    }

    public void setselectedView() {
        clearAll();
        buttonDisables();
        setTfDisableTrue();
        EmployeeStringProp empProp = tableView.getSelectionModel().getSelectedItem();

        if (empProp != null) {
            users.RFID = empProp.getRFID();
            users.forname = empProp.getForename();
            users.surname = empProp.getSurname();
            users.empId = empProp.getEmployeeId();
            rfidTextField.setText(users.RFID);
            forNameTextField.setText(users.forname);
            lastNameTextField.setText(users.surname);
            employeeIdTextField.setText(users.empId);
        }
    }

    public void setTfDisableTrue() {
        //rfidTextField.setDisable(true);
        forNameTextField.setDisable(true);
        lastNameTextField.setDisable(true);
        employeeIdTextField.setDisable(true);
    }

    public void setTfDisableFalse() {
        //rfidTextField.setDisable(false);
        forNameTextField.setDisable(false);
        lastNameTextField.setDisable(false);
        employeeIdTextField.setDisable(false);
    }

    public void buttonDisables() {
        update.setDisable(true);
        remove.setDisable(true);
    }

    public void buttonEnables() {
        update.setDisable(false);
        remove.setDisable(false);
    }

    public void updateTblView() {
        empList = empTemp.listEmployeesProp();
        empdata = FXCollections.observableList(empList);
        ForNameColumn.setCellValueFactory(empdata -> empdata.getValue().Forenameproperty());
        LastNameColumn.setCellValueFactory(empdata -> empdata.getValue().Surnameproperty());
        tableView.setItems(empdata);
    }

    /**
     * ----------------------------------------------------TIME TAB STARTS
     * HERE------------------------------------------------------*
     */
    public void updateTimeTblView() {
        timeList = empTemp.listTimeProp();
        timedata = FXCollections.observableList(timeList);

        RFIDColumn.setCellValueFactory(timedata -> timedata.getValue().RFIDproperty());
        dateColumn.setCellValueFactory(timedata -> timedata.getValue().timeproperty());
        inOutColumn.setCellValueFactory(timedata -> timedata.getValue().inOutproperty());

        timeTableView.setItems(timedata);
        timeTableView.getSortOrder().add(dateColumn);
    }
    private Timestamp curtime;

    public void timeTblEmployeeOnClick(Event event) {
        timeSetselectedView();
        curtime = valueOf(timeTf.getText());
    }

    @FXML
    private void timeTblViewOnClick(KeyEvent event) {
        if (event.getCode().equals(KeyCode.UP)) {
            timeSetselectedView();
            curtime = valueOf(timeTf.getText());
        } else if (event.getCode().equals(KeyCode.DOWN)) {
            timeSetselectedView();
            curtime = valueOf(timeTf.getText());
        }
    }

    public void timeSetselectedView() {
        TimeStampProp timeProp = timeTableView.getSelectionModel().getSelectedItem();
        if (timeProp != null) {
            timeUsers.time = timeProp.getTime();
            timeUsers.inOut = timeProp.getInOut();
            timeTf.setText(timeUsers.time);
            inOutTf.setText(timeUsers.inOut);
        }
    }

    @FXML
    private void btnEditOnAction(ActionEvent event
    ) {

        if (timeTf.getText().trim().equals("") || inOutTf.getText().trim().equals("")) {
            Alert fail = new Alert(Alert.AlertType.INFORMATION);
            fail.setContentText("Du måste markera en anställd.");
            fail.showAndWait();
        } else {
            updtTime.setDisable(false);
            editTime.setDisable(true);
            timeTf.setDisable(false);
        }

    }
    private Timestamp newTime;

    @FXML
    private void btnUpdtTimeOnAction(ActionEvent event) {

        if (!timeTf.getText().matches("(\\p{Digit}){4}(\\-(\\p{Digit}){2}){2}\\s((\\p{Digit}){2}\\:){2}(\\p{Digit}){2}") ) {
            Alert fail = new Alert(Alert.AlertType.WARNING);
            fail.setContentText("Du måste fylla i alla fält korrekt\nKorrekt format: yyyy-mm-dd HH:mm:ss");
            fail.showAndWait();
            timeTf.setText("yyyy-mm-dd HH:mm:ss");
        } else {
            newTime = valueOf(timeTf.getText());
            empTemp.UpdateTime(curtime, newTime);
            updtTime.setDisable(true);
            editTime.setDisable(false);
            timeTf.setDisable(true);
            updateTimeTblView();

        }

    }
    /**
     * ----------------------------------------------------EXPORT TAB STARTS
     * HERE------------------------------------------------------*
     */
    @FXML
    private DatePicker fromDateSum;
    @FXML
    private DatePicker toDateSum;
    @FXML
    private DatePicker fromDatePerson;
    @FXML
    private DatePicker toDatePerson;
    @FXML
    private TextField empIdTF;

    private ArrayList<Employee> empArrList;
    private ArrayList<RFIDTimestamp> timeArrList;
    private ArrayList<String> loggedINNames = new ArrayList<String>();

    private Timestamp fromDate;
    private Timestamp toDate;

    @FXML
    private void btnExportSum(ActionEvent event) throws ParseException, IOException {
        // If one of the dates is not selected.
        if (fromDateSum.getValue() == null || toDateSum.getValue() == null) {
            Alert fail = new Alert(Alert.AlertType.INFORMATION);
            fail.setHeaderText("Fel");
            fail.setContentText("Du måste fylla i alla fält");
            fail.showAndWait();
        } else {

            fromDate = valueOf(fromDateSum.getValue().atStartOfDay());
            toDate = valueOf(toDateSum.getValue().atTime(23, 59, 59));
            empArrList = (ArrayList<Employee>) empTemp.listEmployees();
            ArrayList<ToExcelEmployee> excelList = new ArrayList();
            ToExcelEmployee excEmp;
            String name;
            String surname;
            String id;
            String time;
            long inTime = 0;
            long outTime = 0;
            double totTime = 0;
            
            int nrOfIn = 0, nrOfOut = 0;
            
            for (int i = 0; i < empArrList.size(); i++) {
                name = empArrList.get(i).getForename();
                surname = empArrList.get(i).getSurname();
                id = empArrList.get(i).getEmployeeId();
                timeArrList = (ArrayList<RFIDTimestamp>) empTemp.getTimestamps(new RFID(empArrList.get(i).getRFID()));
                for (int j = 0; j < timeArrList.size(); j++) {
                    if (timeArrList.get(j).getTime().compareTo(toDate.toString()) <= 0 && timeArrList.get(j).getTime().compareTo(fromDate.toString()) >= 0) {
                        if (timeArrList.get(j).getInOut().equals("IN")) {
                            Date in = timeArrList.get(j).getDateTime();
                            inTime += in.getTime();
                            nrOfIn++;
                        } else if (timeArrList.get(j).getInOut().equals("OUT")) {
                            Date out = timeArrList.get(j).getDateTime();
                            outTime += out.getTime();
                            nrOfOut++;
                        }
                    }
                }

                //If there is more logins than logouts i.e. someone is still
                //Logged in!
                if(nrOfIn != nrOfOut) {
                    loggedINNames.add(name + " " + surname);
                    outTime += System.currentTimeMillis();
                }
                
                double hours = (double) (outTime - inTime) / 3600000.0;

                totTime = Math.round(hours * 100) / 100.0;

                excelList.add(excEmp = new ToExcelEmployee(name, surname, id, totTime));
                
                time = "";
                inTime = outTime = nrOfIn = nrOfOut = 0;
                
            }
            
            //If someone/several people is still logged into the system
            //Then raise a warning dialog.
            Alert al = new Alert(AlertType.WARNING);
            for(String n : loggedINNames) {
                al.setContentText(al.getContentText() + n + "\n");
            }
            al.setContentText(al.getContentText()
                + "\nÄr fortfarande inloggad/inloggade i systemet!"
                + "\nTidsberäkningen för dagens datum är ej slutgiltig...");
            al.showAndWait();
            
            ArrayList<RFIDTimestamp> times = (ArrayList<RFIDTimestamp>) empTemp.getAllTimestamps();
            for (int j = 0; j < times.size(); j++) {
                if (times.get(j).getTime().compareTo(toDate.toString()) >= 0 || times.get(j).getTime().compareTo(fromDate.toString()) <= 0) {
                    times.remove(j);
                    j--;
                }
            }

            //Open a dialog to save the file
            FileChooser fs = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
            fs.getExtensionFilters().add(extFilter);
            fs.setInitialFileName("*.xlsx");
            File saveFile = fs.showSaveDialog(anchorPane.getScene().getWindow());

            //Make sure the file was created
            if (saveFile != null) {

                //Make shure the file-extension is .xlsx
                if (!saveFile.getName().endsWith(".xlsx")) {
                    saveFile = new File(saveFile.getAbsolutePath().replaceAll("\\..++", "") + ".xlsx");
                }

                toExcel(excelList, times, saveFile, fromDate, toDate);
                for (int k = 0; k < excelList.size(); k++) {
                    System.out.println(excelList.get(k));
                }
            }
        }
    }

    private void toExcel(ArrayList<ToExcelEmployee> lst, ArrayList<RFIDTimestamp> times,
            File file, Timestamp from, Timestamp to) throws IOException {

        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook
                    .createSheet("Total arbetad tid " + file.getName());
            XSSFRow row = spreadsheet.createRow(0);
            XSSFCell cell;
            cell = row.createCell(0);
            cell.setCellValue("Förnamn:");
            cell = row.createCell(1);
            cell.setCellValue("Efternamn:");
            cell = row.createCell(2);
            cell.setCellValue("Anställningsnummer:");
            cell = row.createCell(3);
            cell.setCellValue("Individuell tid (h) from.: " + (from.toString() + " tom.: " + to.toString()).
                    replaceAll("(\\s)((\\p{Digit}{2}:){2}\\p{Digit}{2})\\..", ""));
            Double totAllTime = 0.0;
            for (int i = 1; i <= lst.size(); i++) {
                row = spreadsheet.createRow(i);
                cell = row.createCell(0);
                cell.setCellValue(lst.get(i - 1).surname);
                cell = row.createCell(1);
                cell.setCellValue(lst.get(i - 1).lastname);
                cell = row.createCell(2);
                cell.setCellValue(lst.get(i - 1).id);
                cell = row.createCell(3);
                cell.setCellValue(lst.get(i - 1).time);
                totAllTime += lst.get(i - 1).time;
                if (i == lst.size()) {
                    row = spreadsheet.createRow(i + 1);
                    cell = row.createCell(3);
                    cell.setCellValue("Sammanställning:");
                    row = spreadsheet.createRow(i + 2);
                    cell = row.createCell(3);
                    cell.setCellValue(totAllTime);
                }

            }

            XSSFSheet spreadsheetTimes = workbook
                    .createSheet("Tider");
            row = spreadsheetTimes.createRow(0);
            cell = row.createCell(0);
            cell.setCellValue("RFID");
            cell = row.createCell(1);
            cell.setCellValue("IN/UT");
            cell = row.createCell(2);
            cell.setCellValue("Datum/Tid");

            for (int i = 1; i <= times.size(); i++) {
                row = spreadsheetTimes.createRow(i);
                cell = row.createCell(0);
                cell.setCellValue(times.get(i - 1).getRFID().toString());
                cell = row.createCell(1);
                cell.setCellValue(times.get(i - 1).getInOut());
                cell = row.createCell(2);
                cell.setCellValue(times.get(i - 1).getTime());
            }
            for (int k = 0; k < spreadsheet.getRow(0).getLastCellNum(); k++) {
                spreadsheet.autoSizeColumn(k);
            }
            for (int j = 0; j < spreadsheetTimes.getRow(0).getLastCellNum(); j++) {
                spreadsheetTimes.autoSizeColumn(j);
            }

            try (FileOutputStream out = new FileOutputStream(file)) {
                workbook.write(out);
            }
            System.out.println(
                    file.getName() + " written successfully");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
