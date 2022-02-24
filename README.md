# Scheduling_Solution
GUI based international appointment scheduling solution designed to cross-check time zones and deliver individualized reports for a hypothetical consulting agency.

Version: v1.01
Date: 9-11-2021

IDE: Intellij IDEA Community Edition 2021.1.1 x64
JDK version: JDK-11.0.1
JAVAFX version: JavaFX-SDK-11.0.2
My SQL Connector Version: mysql-connector-java 8.0.22


Directions to run the program:

Login:
    Upon pressing play the Login Screen will appear. The Login screen will automatically translate to English or French based on the user's OS language preference.
    Log in using provided username and password "test" and pressing the
    login button provided. To clear a selection click "Clear" and to exit the program click the "Exit" button.
    
    Screenshot of Login Screen in English:
    
  ![login](https://user-images.githubusercontent.com/79055002/155594868-ad205961-7f60-4c9a-ac2a-70622ed54fed.png)
  

    Screenshot of Login Screen in French:
    
![Capture d’écran (13)](https://user-images.githubusercontent.com/79055002/155594460-661afda3-0659-485b-bbc8-b6ccb32305a3.png)

    All login attempts are recorded in a .txt file:


![login_attempt_log](https://user-images.githubusercontent.com/79055002/155594988-bef6b843-93a6-41d2-b71e-20d88b568d83.png)

Main Menu:
    Buttons provided will take you to the different labeled options. To delete a customer or appointment, simply select
    customer in the tableview and click the "Delete" button. Be aware deleting a customer with delete all appointments
    associated with that customer. To delete an appointment simply select that appointment in the tableview and click
    "Delete". To modify or add a customer or appointment select that customer/appointment in the table button and click
    the "Add" or "Modify" button.

Main Menu screenshot provided:


Reports:
    Navigate through reports using the labeled tabs provided.
    Customer Schedule:
    Select a customer using the combo box provided to view a summarized customer schedule.
    Contact Schedule:
    Select a contact using the combo box provided to view a summarized contact schedule.
    Totals by Type/Month:
    Initially totals for all appointments appear. To filter these totals by month select the month for the totals you
    wish to view using the provided combo box. A "View All" button is provided to view all appointments.

Calendar View:
    This view provides a larger, easier to view table of all appointments that allows you to filter the appointments by
    current week and month by selecting one of the labeled radio buttons.

Add/Modify Screens:
    Please fill out all forms, combo boxes, and date selections via date picker provided and click "Save". To view
    modifications made, click the "Main Menu" button and they will display in the tableviews provided.

    Note: The modify screen for customers has a simplified view of associated appointments. Deleting an appointment from
    this view will permanently delete that appointment.
