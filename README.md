# Aggie Find-It  
(*cs371-fa2024-teamproject-lost-and-found*)

Aggie Find-It is a centralized lost-and-found system designed to simplify the recovery and management of lost items at New Mexico State University (NMSU). This application connects all campus buildings through a unified database, enabling students, faculty, and administrators to track, report, and manage lost-and-found items seamlessly.

---

## Project Description

Lost items at NMSU are typically stored at individual building lost-and-found locations, creating challenges for those who are unsure where an item was misplaced. The current system relies on trial and error, as students must physically visit multiple buildings to locate their belongings.  

Aggie Find-It eliminates these inefficiencies by providing:  
- A university-wide database that integrates lost-and-found records from all buildings.  
- A platform for both primary users (administrators) and secondary users (students and staff) to interact with the system efficiently and securely.  

This application makes the process of finding and returning items faster, more secure, and accessible from anywhere, improving the overall experience for everyone involved.  

---

## Features

### Core Features

1. **Centralized Lost-and-Found Database**  
   - A unified platform where all campus buildings can store and manage lost-and-found records.  

2. **User Role Separation**  
   - **Primary Users**: Lost-and-Found administrators who can view, update, and manage detailed records.  
   - **Secondary Users**: Students, faculty, and staff who can report and search for items with limited access to sensitive data.  

3. **Online Reporting System**  
   - Secondary users can file detailed reports for lost items, specifying descriptions, locations, and contact details.  
   - Administrators can review these reports and take appropriate action.  

4. **Item Search and Matching**  
   - Secondary users can search for found items using filters like date, location, and general item descriptions.  
   - Automatic matching of lost and found reports based on keywords and metadata.  

5. **Request Management**  
   - Secondary users can submit item recovery requests.  
   - Administrators can process requests, notify users of matches, and provide directions to the item's location.  

6. **Secure Data Handling**  
   - Sensitive information is hidden from secondary users to ensure privacy and prevent misuse.  
   - Only administrators can access detailed item descriptions and remove entries from the system.  

---

## Benefits

### For Administrators:  
- Gain a consolidated view of all lost-and-found records across campus.  
- Streamline processes to return items to their rightful owners.  
- Reduce reliance on physical records and manual effort.

### For Students and Staff:  
- Save time by searching for items online instead of visiting multiple buildings.  
- Receive notifications when a lost item matches a report.  
- File and track reports conveniently from any device.  

---

## Installation

1. Download the repository from GitHub and extract the contents to a folder.  
2. Follow the instructions below to configure and run the application.  

---

## Usage

### Prerequisites
Ensure the following software and tools are installed:  
- **JavaFX**: For GUI components.  
- **MySQL**: To manage the lost-and-found database.  

### Compile and Run

1. Open a terminal and navigate to the `src` directory.  
2. Compile the Java files with the following command:  
   ```bash
   javac --module-path (JFX PATH) --add-modules javafx.controls,javafx.graphics,javafx.media,javafx.fxml -cp "lib/mongodb-driver-sync-5.2.0.jar;lib/mongodb-driver-core-5.2.0.jar;lib/bson-5.2.0.jar;lib/bson-record-codec-5.2.0.jar" src/com/Aggie_FindIt/*.java
   ```
3. Run the application:  
   ```bash
   java --module-path (JFX PATH) --add-modules javafx.controls,javafx.graphics,javafx.media,javafx.fxml -cp "src;lib/mongodb-driver-sync-5.2.0.jar;lib/mongodb-driver-core-5.2.0.jar;lib/bson-5.2.0.jar;lib/bson-record-codec-5.2.0.jar" com.Aggie_FindIt.Aggie_FindIt
   ```

---

## End User Profiles

### Primary Users:  
Lost-and-Found administrators responsible for managing campus-wide lost-and-found records. They use the system to:  
- Add new entries for found items.  
- Search the database to assist secondary users.  
- Remove entries when items are returned.  

### Secondary Users:  
Students, faculty, staff, or community members who report and search for lost or found items. They use the system to:  
- Submit online reports for lost items.  
- View limited details about found items.  
- Request updates on their lost item reports.  

---

## Contributors

- Rupak Dey  
- Mario Saenz  
- Benjamin Widner III  
- Demetrius Billey  
