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

![Screenshot 2022-03-07 175438](https://user-images.githubusercontent.com/79055002/157131962-0dc8f43f-ffb9-4bd1-8a5a-1fcb201274f1.png)

Reports:
    Navigate through reports using the labeled tabs provided.
    Customer Schedule:
    Select a customer using the combo box provided to view a summarized customer schedule.
    
    Customer schedule screenshot provided:
    
   ![customer_schedule](https://user-images.githubusercontent.com/79055002/155596084-f1b9786d-dd79-48f3-b2cd-07d5e9d0d66c.png)

    Contact Schedule:
    Select a contact using the combo box provided to view a summarized contact schedule.
    
    Contact schedule screenshot provided:
    
   ![contact_schedule](https://user-images.githubusercontent.com/79055002/155596187-a784a38f-ad65-4f3e-bd3e-6df68800c1c6.png)

    
    Totals by Type/Month:
    Initially totals for all appointments appear. To filter these totals by month select the month for the totals you
    wish to view using the provided combo box. A "View All" button is provided to view all appointments.
    
    Totals by Type/Month screenshot provided:
   ![appointment_totals](https://user-images.githubusercontent.com/79055002/155596319-33a6f5e7-3c1e-497b-9243-56364b961041.png)


Calendar View:
    This view provides a larger, easier to view table of all appointments that allows you to filter the appointments by
    current week and month by selecting one of the labeled radio buttons.
    
    Calendar view screenshot provided:
    
![calendar_view](https://user-images.githubusercontent.com/79055002/155596398-dca5922a-14a4-4376-9ab1-885ca641f9a7.png)

Add/Modify Screens:
    Please fill out all forms, combo boxes, and date selections via date picker provided and click "Save". To view
    modifications made, click the "Main Menu" button and they will display in the tableviews provided.

    Note: The modify screen for customers has a simplified view of associated appointments. Deleting an appointment from
    this view will permanently delete that appointment.
    
 Add customer screen:
 
![add_customer_screen](https://user-images.githubusercontent.com/79055002/155596469-eef4fe4f-3c2c-40d6-be24-894c4ec35938.png)

Modify customer screen:

![modify_customer_screen](https://user-images.githubusercontent.com/79055002/155596538-a3a2fabb-f1c7-424d-9ba2-7c8b701b5ff4.png)

Add appointment screen:

![add_new_appointment](https://user-images.githubusercontent.com/79055002/155596587-a842dfe8-5a2f-4e3f-b0e3-431571e63031.png)

Modify appointment screen:

![modify_appointment](https://user-images.githubusercontent.com/79055002/155596626-60199fe8-b381-4e8a-8dfb-8a00b63eb5a6.png)


