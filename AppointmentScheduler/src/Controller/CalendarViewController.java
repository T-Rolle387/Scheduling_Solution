package Controller;

import DBAccess.DBAppointment;
import Model.Appointment;
import Utility.ButtonNav;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ResourceBundle;
/**This class defines Calendar View interactions.*/
public class CalendarViewController implements Initializable {

    Stage stage;
    Parent scene;
    /**Declares table view and columns*/
    public TableView<Appointment> calendarTableView;
    public TableColumn apptIDCol;
    public TableColumn apptTitleCol;
    public TableColumn descriptionCol;
    public TableColumn locationCol;
    public TableColumn contactCol;
    public TableColumn typeCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn customerIDCol;



/**This method takes the user back to the main menu*/
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        String selectedLocation = "/View/MainMenu.fxml";
        ButtonNav.navigateTo(stage, event, scene, selectedLocation);
    }
/**This method displays appointments by month*/
    @FXML
    void onActionVIewMonth(ActionEvent event) {
        LocalDateTime ltdNow = LocalDateTime.now();
        Month currentMonth = ltdNow.getMonth();
        ObservableList<Appointment> currentMAppointments = FXCollections.observableArrayList();
        for(Appointment a : DBAppointment.getAllAppts()){
            if (a.getApptStart().getMonth() == currentMonth){
                currentMAppointments.add(a);
            }
        }

        calendarTableView.setItems(currentMAppointments);
        apptIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("assocContactName"));
        startCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("apptStart"));
        endCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("apptEnd"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

    }
/**This method displays appointments by week.*/
    @FXML
    void onActionVIewWeek(ActionEvent event) {
        LocalDateTime ltdNow = LocalDateTime.now();
        WeekFields weekFields = WeekFields.of(DayOfWeek.SUNDAY, 1);
        TemporalField weekOfMonth = weekFields.weekOfMonth();
        LocalDate day = ltdNow.toLocalDate();
        int wom = day.get(weekOfMonth);
        YearMonth ym = YearMonth.of(ltdNow.getYear(), ltdNow.getMonth());



        ObservableList<Appointment> currentWAppointments = FXCollections.observableArrayList();
        for(Appointment a : DBAppointment.getAllAppts()){
           LocalDate aDay = a.getApptStart().toLocalDate();
            int wOMA = aDay.get(weekOfMonth);
            YearMonth apptYM = YearMonth.of(a.getApptStart().getYear(), a.getApptStart().getMonth());

           if (wom == wOMA && ym.equals(apptYM)){
               currentWAppointments.add(a);
           }

        }
        calendarTableView.setItems(currentWAppointments);
        apptIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("assocContactName"));
        startCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("apptStart"));
        endCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("apptEnd"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));



    }
/**This method displays all appointments*/
    @FXML
    void onActionViewAll(ActionEvent event) {
        calendarTableView.setItems(DBAppointment.getAllAppts());
        apptIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("assocContactName"));
        startCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("apptStart"));
        endCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("apptEnd"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        calendarTableView.setItems(DBAppointment.getAllAppts());
        apptIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("assocContactName"));
        startCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("apptStart"));
        endCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("apptEnd"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));




    }
}
