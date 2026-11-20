# Insurance Management System

**Name:** Tlhohonolofatso Qoane
**Student number:** 2437485

This is my insurance management system. It is a Java Swing desktop app that connects to a MySQL database. I built it in NetBeans. The app lets staff register customers, create policies, submit claims and assess claims, depending on the role they log in with.

## What the system can do

- Log in as one of three users: Insurance Officer, Claims Officer or Administrator
- Register customers (it stops you from registering the same national ID twice)
- Create Motor, Property, Life and Health policies
- Calculate the premium for each policy type
- Submit claims against a policy
- Assess claims (approve, reject or set under review)
- Send an email to the customer when a claim status changes
- Export reports to CSV files
- Add and delete system users (Administrator only)

## What each user can do

| User | What they can do |
|---|---|
| Insurance Officer | Manage customers, create and manage policies, submit claims, generate reports |
| Claims Officer | Assess claims only (approve or reject) |
| Administrator | Manage system users, view all policies, customers and claims, see system information, generate reports |

The role is saved in the `role` column of the `users` table. It must be written exactly as `INSURANCE_OFFICER`, `CLAIMS_OFFICER` or `ADMINISTRATOR`, otherwise the dashboard will say the role is not recognised.

## What you need before running it

- NetBeans (with a JDK installed)
- MySQL 8 (the server) and MySQL Workbench or any other way to run SQL
- The three jar files in the `lib` folder, which are already in the project:
  - `mysql-connector-j`
  - `javax.mail-1.6.2.jar`
  - `activation-1.1.1.jar`

## How to use the project

### Step 1: Open the database first

The program cannot do anything without the database, so this must be done before running it.

1. Start the MySQL server (on Windows, open Services and start **MySQL80**, or start it from MySQL Workbench).
2. Open MySQL Workbench and connect to your local server.
3. Make sure the database `TlhohonolofatsoQoane2437485` exists. If it does not, create it:

```sql
CREATE DATABASE TlhohonolofatsoQoane2437485;
USE TlhohonolofatsoQoane2437485;
```

4. Make sure these tables exist in that database: `users`, `customers`, `policies`, `motor_policies`, `property_policies`, `life_policies`, `health_policies` and `claims`.
5. Make sure there is at least one user for each role so you can log in. For example:

```sql
INSERT INTO users (full_name, email, password_hash, role) VALUES
  ('Insurance Officer', 'officer@example.com', 'password123', 'INSURANCE_OFFICER'),
  ('Claims Officer',    'claims@example.com',  'password123', 'CLAIMS_OFFICER'),
  ('Admin',             'admin@example.com',   'password123', 'ADMINISTRATOR');
```

(Use whatever names and passwords you want. When logging in you type the full name and the password.)

### Step 2: Check the database connection in the code

Open `DBConnection.java` and make sure the details match your MySQL:

```java
private static final String URL =
    "jdbc:mysql://localhost:3306/TlhohonolofatsoQoane2437485?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "your MySQL password here";
```

Change `PASSWORD` to your own MySQL root password. The part `allowPublicKeyRetrieval=true` is needed, otherwise MySQL 8 gives the error "Public Key Retrieval is not allowed".

### Step 3: Open and run the project in NetBeans

1. In NetBeans go to **File > Open Project** and select the project folder.
2. Right-click the project and choose **Clean and Build**.
3. Press **Run** (F6). The login window should appear. The main class is `LoginFrame`.

### Step 4: Log in and use the system

**As an Insurance Officer**

1. Log in with an Insurance Officer account.
2. Click **Manage Customers** to register a new customer.
3. Click **Create & Manage Policies**, pick the policy type, fill in the details and save. The policy number is created for you.
4. Click **Submit Claims**. Enter the policy number and the claim amount, then click **Submit Claim**. The claim is saved with the status `PENDING`.
5. Click **Generate Reports** to export the CSV files.

**As a Claims Officer**

1. Log in with a Claims Officer account.
2. Click **Assess Claims**.
3. Click a claim in the table. Its details fill into the boxes.
4. Type the status (for example `APPROVED` or `REJECTED`), the approved amount, and a rejection reason if it is rejected.
5. Click save. The customer gets an email about the new status.

**As an Administrator**

1. Log in with an Administrator account.
2. **Manage System Users** is where you add or delete users.
3. The other buttons show all policies, customers and claims, and the system information.
4. **Generate Reports** exports the CSV files.

Use the **Back** or **Logout** buttons to go back to the dashboard or to the login screen.

## Reports

When you click **Generate Reports** the system saves these files in the project folder:

- `Customers_Report.csv`
- `Policy_Report.csv`
- `Claims_Report.csv`

The export runs on a separate thread so the window does not freeze.

## Email setup

The emails are sent through Gmail in `EmailService.java`. To make it work, put the sender Gmail address and a Gmail **app password** in that file. Your normal Gmail password will not work.

## Project files

```
src/tlhohonolofatsoqoane2437485/
  LoginFrame                  login screen (main class)
  DashboardFrame              main menu, buttons change per role
  CustomerRegistrationFrame   register and list customers
  PolicyCreationFrame         create the four policy types
  ClaimSubmissionFrame        Insurance Officer submits claims
  ClaimsAssessmentFrame       Claims Officer assesses claims
  UserManagementFrame         Administrator manages users
  DataViewerFrame             shows tables for the admin views
  Policy                      abstract parent class
  MotorPolicy, PropertyPolicy, LifePolicy, HealthPolicy   child classes
  CustomerDAO, PolicyDAO      database code for customers and policies
  DBConnection                connects to MySQL
  EmailService                sends claim status emails
  ReportTask                  writes the CSV reports
```

## Problems I ran into and how to fix them

| Problem | Fix |
|---|---|
| "Public Key Retrieval is not allowed" | Add `allowPublicKeyRetrieval=true` to the URL in `DBConnection.java` |
| "Database connection failed" or "Access denied" | Check that MySQL is running and the password in `DBConnection.java` is correct |
| NullPointerException when I click login | This happens when the database connection failed, so fix the connection first |
| "Unrecognized user role" | The role in the `users` table is not spelled exactly like the three roles above |
| "Policy number not found" when submitting a claim | The policy must be created first |
| Emails are not sending | Check the Gmail app password and the internet connection |
| Java version error when building | In the project properties set the Source/Binary format to the JDK you have |

## Things I would improve

- Passwords are stored and checked as plain text, so they should be hashed
- The database and email passwords are written inside the code
- The claim description box is on the form but it is not saved to the database yet