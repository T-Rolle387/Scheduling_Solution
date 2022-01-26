QAM1 Performance Assessment
Author: Tiffany Rolle
E-mail: troll24@wgu.edu

Application Title: Appointment Scheduler
Purpose: To provide a GUI based scheduling desktop application for use in a global consulting organization
Version: v1.01
Date: 9-11-2021

IDE: Intellij IDEA Community Edition 2021.1.1 x64
JDK version: JDK-11.0.1
JAVAFX version: JavaFX-SDK-11.0.2
My SQL Connector Version: mysql-connector-java 8.0.22

Description of additional report:
    A simplified schedule for each customer that provides the following information:
        * customer name
        * appointment title
        * description
        * start time/date
        * end time/date
        * total number of customer related appointments
    The report displays in the user interface via tableview upon customer selection chosen (using a combo box).

Directions to run the program:

Login:
    Upon pressing play the Login Screen will appear. Log in using provided username and password "test" and pressing the
    login button provided. To clear a selection click "Clear" and to exit the program click the "Exit" button.

Main Menu:
    Buttons provided will take you to the different labeled options. To delete a customer or appointment, simply select
    customer in the tableview and click the "Delete" button. Be aware deleting a customer with delete all appointments
    associated with that customer. To delete an appointment simply select that appointment in the tableview and click
    "Delete". To modify or add a customer or appointment select that customer/appointment in the table button and click
    the "Add" or "Modify" button.

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


I really enjoyed making this program and hope you find it suitable. I used information found in the Java 8 API documents
provided by Oracle to implement solutions needed to meet the project requirements.



