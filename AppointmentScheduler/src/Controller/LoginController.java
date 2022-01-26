package Controller;

import DBAccess.DBAppointment;
import DBAccess.DBUser;
import Model.Appointment;
import Model.Lists;
import Model.User;
import Utility.ButtonNav;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**This class defines Login interface interactions.*/
public class LoginController implements Initializable {

    Stage stage;
    Parent scene;
    String selectedLocation = "/View/MainMenu.fxml";

    //Declare text fields
    public TextField usernameTxt;
    public TextField pwTxt;

    //Declare labels

    public Label errorOutputLbl;
    public Label appTitle;
    public Label logInPrompt;
    public Label usernameLbl;
    public Label pssdLbl;
    public Label timezoneLbl;
    public Label displayZoneID;

    //Declare buttons
    public Button logInBttn;
    public Button clearButton;
    public Button exitBttn;

    //Current Time
    LocalTime timeNow = LocalTime.now();



/**@author Tiffany Rolle */

/**This method clears text fields*/
    @FXML
    void onActionClear(ActionEvent event) {
        usernameTxt.clear();
        pwTxt.clear();
    }
/**This method handles user login, language conversion based on user's OS language default, and appends user login attempts to text file.*/
    @FXML
    void onActionLogIn(ActionEvent event) throws IOException {
        //Filename variable
        String loginActivity = "src/Files/login_activity.txt";
        //Create FileWriter object
        FileWriter userLogin = new FileWriter(loginActivity, true);
        //Create and open file
        PrintWriter loginOutput = new PrintWriter(userLogin);


        boolean loggedIn = false;

        //Declare variable for user login attempt timestamp
        String loginTimestamp = new String(("yyyyMMdd_HHmm").format(String.valueOf(Calendar.getInstance().getTime())));

        //String variables for username/password input
        String usernameAttempt = usernameTxt.getText();
        String passwordAttempt = pwTxt.getText();
        for (User u : DBUser.getAllUsers()) {

            if (u.getUserName().equals(usernameAttempt) && u.getUserPassword().equals(passwordAttempt)) {
                loggedIn = true;
                //Get user input and write to file
                loginOutput.println("User " + u.getUserName() + " (User ID: " + u.getUserID()+ ") logged in successfully on " + loginTimestamp + ".");
                //Closing output file
                loginOutput.close();
                break;
            }
        }

        if (loggedIn) {
            LocalTime aptTime = null;
            LocalDateTime aStartDT = null;
            int aptID = 0;
            boolean upcomingAppointments = false;
            for (Appointment a : DBAppointment.getAllAppts()) {
                aptTime = a.getApptStart().toLocalTime();
                aStartDT = a.getApptStart();
                aptID = a.getApptID();
                long tDifference = ChronoUnit.MINUTES.between(timeNow, aptTime);

                if (tDifference > 0 && tDifference < 15) {
                    upcomingAppointments = true;
                    break;
                }

            }
            if (upcomingAppointments) {
                long tDifference = ChronoUnit.MINUTES.between(timeNow, aptTime);
                String s = "Upcoming appointment at " + aStartDT + ". The appointment ID for this appointment is: " + aptID;
                Lists.apptNotification(s);
            }
            if (upcomingAppointments == false) {
                String notificationType = "There are no upcoming appointments scheduled at this time.";
                Lists.apptNotification(notificationType);
            }
            ButtonNav.navigateTo(stage, event, scene, selectedLocation);
        }
        if (loggedIn == false) {
            //Get user input and write to file
            loginOutput.println("User " + usernameAttempt + " failed to login at " + loginTimestamp + ".");
            //Closing output file
            loginOutput.close();

            //Translates error output to English or French based on the user's default language settings
            ResourceBundle language = ResourceBundle.getBundle("RBLang/Language");
            if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                errorOutputLbl.setText(language.getString("Error"));
            }
        }
    }
/**This method exits the application*/
        @FXML
        void onActionExitApp (ActionEvent event){
            System.exit(0);
        }
        @Override
        public void initialize (URL url, ResourceBundle rb){

            try {
                rb = ResourceBundle.getBundle("RBLang/Language");
                if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en"))
                    //Attaching key-value pairs to labels/buttons for language translation
                    appTitle.setText(rb.getString("AppTitle"));
                logInPrompt.setText(rb.getString("LoginPrompt"));
                usernameLbl.setText(rb.getString("Username"));
                pssdLbl.setText(rb.getString("Password"));
                timezoneLbl.setText(rb.getString("timezone/location"));
                logInBttn.setText(rb.getString("LogIn"));
                clearButton.setText(rb.getString("Clear"));
                exitBttn.setText(rb.getString("Exit"));

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            ZoneId localzoneId = ZoneId.of(TimeZone.getDefault().getID());//User's zone Id
            displayZoneID.setText(String.valueOf(localzoneId)); // Display user's zone Id

        }











}


