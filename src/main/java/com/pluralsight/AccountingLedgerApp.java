package com.pluralsight;

import java.io.*;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;




public class AccountingLedgerApp {


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);  // ALLOWS US TO READ THE INPUT FROM THE KEYBOARD

       printDivider();
       System.out.println("\uD83D\uDCB0 WELCOME TO THE ACCOUNTING LEDGER PAGE \uD83D\uDCB0");  // GREETING
       printDivider();

        homeScreen(sc);

    }
        public static void homeScreen(Scanner sc){  // DISPLAYS THE MAIN MENU


        while (true) {// KEEPS LOOPING BACK TO HOME SCREEN AFTER EVERY ACTION
            formatSpaces();
            System.out.println("""          
                    \tD) Add Deposit
                    \tP) Make Payment(Debit)
                    \tL) Ledger
                    \tX) Exit
                    """);
            System.out.print("Select from the OPTIONS above: ");// PROMPTS THE USER TO SELECT OPTIONS
            String userOption = sc.nextLine().toUpperCase();

            switch (userOption) {
                case "D":                // GOES TO THE ADD DEPOSIT SUBMENU
                    addDeposit(sc);
                    break;
                case "P":                // GOES TO THE MAKE DEPOSIT SUBMENU
                    makeDeposit(sc);
                    break;
                case "L":                // GOES TO THE LEDGER SUBMENU
                     displayLedgerScreen(sc);
                    break;
                case "X":               // SENDS A GOODBYE MESSAGE
                    System.out.println("Thank you for visiting your account. See you soon!!");
                    sc.close();  // CLOSES THE SCANNER
                    System.exit(0);
                    break;
                default:                // HANDLES ANY INPUT THAT AREN'T THE ONES LISTED ABOVE
                    System.out.print("Invalid option. Try again!(Press ENTER to continue)");
                    sc.nextLine();
            }
        }
    }

        public static void addDeposit(Scanner sc) {
            ArrayList<Transactions> transaction = loadInventory(); // LOADS ALL THE TRANSACTIONS FROM THE CSV FILE


                formatSpaces();
                System.out.println("=-=-=-=-=-=ADD DEPOSIT=-=-=-=-=-=");
                formatSpaces();

                String date;
                String time;

                // ASKS THE USER IF THE TRANSACTION IS HAPPENING NOW OR IN THE PAST
                System.out.print("Is this transaction from today? (Y/N): ");
                String userInput = sc.nextLine().trim().toUpperCase();

                if (userInput.equalsIgnoreCase("Y")) {
                    // AUTO-GENERATES THE CURRENT DATE/TIME
                    date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));  // PRINTS "2026-04-27"
                    time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));  // PRINTS "15:37:46"
                    System.out.printf("""
                            Date set to: %s
                            Time set to: %s%n""", date, time);

                } else {

                    // ASK USER TO INPUT DATE AND TIME MANUALLY
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    date = sc.nextLine().trim();

                    System.out.print("Enter time (HH:MM:SS): ");
                    time = sc.nextLine().trim();

                }

                System.out.print("Enter Description: ");  // PROMPTS USER FOR DESCRIPTION
                String description = sc.nextLine().trim();

                System.out.print("Enter Vendor: ");  // PROMPTS USER FOR VENDOR
                String vendor = sc.nextLine().trim();

                System.out.print("Enter Amount: ");  // PROMPTS USER FOR AMOUNT
                double amount = Double.parseDouble(sc.nextLine());

                // CREATES A NEW TRANSACTION OBJECT WITH COLLECTED DATA ABOVE
                Transactions t = new Transactions(date, time, description, vendor, amount);
                transaction.add(t);  // ADDS IT TO THE LIST

                // SAVES THE NEW TRANSACTION OBJECT TO FILE
                saveToFile(t);
                formatSpaces();
                System.out.println("\uD83D\uDCB2Deposit of $" + amount + " added successfully!");

        }

        public static void makeDeposit(Scanner sc){
            ArrayList<Transactions> transaction = loadInventory(); // LOADS ALL THE TRANSACTIONS FROM THE CSV FILE

            formatSpaces();
            System.out.println("=-=-=-=MAKE PAYMENT=-=-=-=");
            formatSpaces();

            // ASKS THE USER IF THE PAYMENT IS HAPPENING NOW OR IN THE PAST
            System.out.print("Is this payment from today? (Y/N): ");
            String answer = sc.nextLine().trim().toUpperCase();

            String date;
            String time;


            if (answer.equals("Y")) {
                // AUTO-GENERATES THE CURRENT DATE AND TIME
                date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));  // PRINTS "2026-04-27"
                time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));  // PRINTS "15:37:46"
                System.out.printf("""
                        Date set to: %s
                        Time set to: %s%n""", date, time);
            } else {
                // PROMPTS THE USER TO MANUALLY ENTER THE DATE AND TIME
                System.out.print("Enter date (YYYY-MM-DD): ");
                date = sc.nextLine().trim();

                System.out.print("Enter time (HH:MM:SS): ");
                time = sc.nextLine().trim();
            }

            System.out.print("Enter Description: ");  // PROMPTS USER FOR DESCRIPTION
            String description = sc.nextLine().trim();

            System.out.print("Enter Vendor: ");  // PROMPTS USER FOR VENDOR
            String vendor = sc.nextLine().trim();

            System.out.print("Enter Amount: ");  // PROMPTS USER FOR AMOUNT
            double amount = Double.parseDouble(sc.nextLine());

            if (amount > 0){
                amount = -amount;  // FLIPS THE AMOUNT NEGATIVE FOR THE PAYMENT
            }

            // CREATES A NEW TRANSACTION OBJECT WITH COLLECTED DATA ABOVE
            Transactions t = new Transactions(date, time, description, vendor, amount);
            transaction.add(t);  // ADDS IT TO THE LIST

            // SAVES THE NEW TRANSACTION OBJECT TO FILE
            saveToFile(t);
            formatSpaces();
            System.out.println("\uD83D\uDCB5 Payment of $" + Math.abs(amount) + " saved successfully!");
        }

        public static void displayLedgerScreen(Scanner sc) {

            ArrayList<Transactions> transaction = loadInventory();

            while (true) {
                formatSpaces();
                System.out.println("-=-=-=-=-=LEDGER SCREEN=-=-=-=-=-");
                System.out.println("""
                        \tA) All
                        \tD) Deposits
                        \tP) Payments
                        \tR) Reports
                        \tH) Home""");
                System.out.print("Choose from these OPTIONS above: ");
                String userChoice = sc.nextLine().trim().toUpperCase();

                switch (userChoice) {
                    case "A":
                        formatSpaces();
                        for (int i = transaction.size() - 1; i >= 0; i--) {
                            System.out.println(transaction.get(i));
                        }
                        break;

                    case "D":
                        formatSpaces();
                        // DEPOSITS - amount is POSITIVE (greater than 0)
                        for (int i = transaction.size() - 1; i >= 0; i--) {
                            if (transaction.get(i).getAmount() > 0) {
                                System.out.println(transaction.get(i));
                            }
                        }
                        break;

                    case "P":
                        formatSpaces();
                        // PAYMENTS - amount is NEGATIVE (less than 0)
                        for (int i = transaction.size() - 1; i >= 0; i--) {
                            if (transaction.get(i).getAmount() < 0) {
                                System.out.println(transaction.get(i));
                            }
                        }
                        break;

                    case "R":
                        formatSpaces();
                        displayReports(sc);
                        break;

                    case "H":
                        return;
                    default:
                        System.out.println("Invalid option. Please try again!");



                }

            }

        }

        public static void displayReports(Scanner sc) {

            ArrayList<Transactions> transaction = loadInventory();

            while(true){
            formatSpaces();
            System.out.println("-=-=-=-=-=-=REPORTS=-=-=-=-=-=-");
            System.out.println("""
                    \t1) Month To Date
                    \t2) Previous Month
                    \t3) Year To Date
                    \t4) Previous Year
                    \t5) Search by Vendor
                    \t6) Custom Search
                    \t0) Back""");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    formatSpaces();
                    System.out.println("-=-=-=-=MONTH TO DATE=-=-=-=-");

                    for (int i = transaction.size() - 1; i >= 0; i--) {

                        try{
                        // PARSES THE TRANSACTION DATE STRING INTO A LocalDate OBJECT
                        LocalDate transactionDate = LocalDate.parse(transaction.get(i).getDate());
                        // CHECKS IF THE TRANSACTION MONTH AND YEAR MATCH THE CURRENT MONTH AND YEAR
                        if (transactionDate.getMonthValue() == LocalDate.now().getMonthValue() && transactionDate.getYear() == LocalDate.now().getYear()) {
                            System.out.println(transaction.get(i));
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                            System.out.println("Invalid date format for transaction: " + transaction.get(i));}
                    }
                    break;
                case 2:
                    formatSpaces();
                    System.out.println("-=-=-=-=PREVIOUS MONTH=-=-=-=-");

                    for (int i = transaction.size() - 1; i >= 0; i--) {

                        try{
                        // PARSES THE TRANSACTION DATE STRING INTO A LocalDate OBJECT
                        LocalDate transactionDate = LocalDate.parse(transaction.get(i).getDate());

                        if (transactionDate.getMonthValue() == LocalDate.now().getMonthValue() - 1 && transactionDate.getYear() == LocalDate.now().getYear()) {
                            System.out.println(transaction.get(i));
                        }
                    }catch(Exception e){
                            e.printStackTrace();
                            System.out.println("Invalid date format for transaction: " + transaction.get(i));
                        }
                    }
                    break;
                case 3:
                    formatSpaces();
                    System.out.println("-=-=-=-=YEAR TO DATE=-=-=-=-");

                    for (int i = transaction.size() - 1; i >= 0; i--) {

                        try{
                        LocalDate transactionDate = LocalDate.parse(transaction.get(i).getDate());

                        if (transactionDate.getYear() == LocalDate.now().getYear()) {
                            System.out.println(transaction.get(i));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                            System.out.println("Invalid date format for transaction: " + transaction.get(i));
                        }
                    }
                    break;
                case 4:
                    formatSpaces();
                    System.out.println("-=-=-=-=PREVIOUS YEAR=-=-=-=-");

                        for (int i = transaction.size() - 1; i >= 0; i--) {
                            try{

                            LocalDate transactionDate = LocalDate.parse(transaction.get(i).getDate());

                            if (transactionDate.getYear() == LocalDate.now().getYear() - 1) {
                                System.out.println(transaction.get(i));
                            }

                    } catch (Exception e){
                           e.printStackTrace();
                            System.out.println("Invalid date format for transaction: " + transaction.get(i));
                }
       }

                    break;
                case 5:
                    formatSpaces();
                    System.out.println("-=-=-=-=SEARCH BY VENDOR=-=-=-=-");

                    System.out.print("Enter Vendor: ");
                    String vendor = sc.nextLine().trim();

                    for (int i = transaction.size() - 1; i >= 0; i--) {

                        if (transaction.get(i).getVendor().equalsIgnoreCase(vendor)) {
                            System.out.println(transaction.get(i));
                        }
                    }
                    break;
                case 6:
                    formatSpaces();
                    System.out.println("-=-=-=-=CUSTOM SEARCH=-=-=-=-");

                    System.out.print("Start Date (YYYY-MM-DD or press ENTER to skip): ");
                    String startDate = sc.nextLine().trim();
                    System.out.print("End Date (YYYY-MM-DD or press ENTER to skip): ");
                    String endDate = sc.nextLine().trim();
                    System.out.print("Description (or press ENTER to skip): ");
                    String description = sc.nextLine().trim();
                    System.out.print("Vendor (or press ENTER to skip): ");
                    String inputVendor = sc.nextLine().trim();
                    System.out.print("Amount (or press ENTER to skip): ");
                    String inputAmount = sc.nextLine();

                    for (int i = transaction.size() - 1; i >= 0; i--) {
                        if (
                                (startDate.isEmpty() || transaction.get(i).getDate().compareTo(startDate) >= 0 ) &&  // is the transaction date AFTER OR EQUAL to startDate?
                                (endDate.isEmpty() || transaction.get(i).getDate().compareTo(endDate) <= 0) &&  // is the transaction date BEFORE OR EQUAL to endDate?
                                (description.isEmpty() || transaction.get(i).getDescription().equalsIgnoreCase(description)) &&
                                (inputVendor.isEmpty() || transaction.get(i).getVendor().equalsIgnoreCase(inputVendor)) &&
                                (inputAmount.isEmpty() || transaction.get(i).getAmount() == Double.parseDouble(inputAmount)))
                        {
                                System.out.println(transaction.get(i));
                        }
                    }
                    break;

                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again!");
            }
        }
    }


        public static ArrayList<Transactions> loadInventory(){

            ArrayList<Transactions> transactions = new ArrayList<>(); // ARRAYLIST OF TRANSACTION OBJECTS

            try{
                // OPENS AND READS THE FILE USING BUFFEREDREADER FOR EFFICIENCY
                BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"));

                String line;   // line VARIABLE HOLDS EACH LINE FROM THE FILE AS IT READS
                bufferedReader.readLine();  // SKIPS THE FIRST HEADER LINE

                // READS EACH LINE FROM THE CSV FILE UNTIL IT RETURNS NULL
                while((line = bufferedReader.readLine()) != null){

                    String[] splitTransaction = line.split("\\|");
                    String date = splitTransaction[0]; // FIRST FIELD: DATE
                    String time = splitTransaction[1]; // SECOND FIELD: TIME
                    String description = splitTransaction[2]; // THIRD FIELD: DESCRIPTION
                    String vendor = splitTransaction[3];      // FORTH FIELD: VENDOR
                    double amount = Double.parseDouble(splitTransaction[4]);  // FIFTH FIELD: AMOUNT

                    Transactions t = new Transactions(date, time, description, vendor, amount);  // CREATES A NEW TRANSACTION OBJECT
                    transactions.add(t);   // ADDS THE TRANSACTION TO THE ARRAYLIST
                }
                bufferedReader.close();  // CLOSES THE FILE AND RELEASES TO RESOURCES

            }catch(IOException e){

                e.printStackTrace();  // PRINTS THE ERROR IN THE SEQUENCES OF PROBLEMS CAUSING THE ERROR
            }

            return transactions; // RETURNS THE FULL LIST
        }

        public static void saveToFile(Transactions t){

            try{
                // USING APPEND == TRUE TO PREVENT FROM OVERRIDING THE EXISTING TRANSACTIONS
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true));

                // WRITES THE TRANSACTION INTO THE CSV FILE
                bufferedWriter.write(t.toString());  // CALLS THE toString FROM TRANSACTION CLASS
                bufferedWriter.newLine();
                bufferedWriter.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void formatSpaces() {
        System.out.println();
    }
    public static void printDivider(){

        System.out.println(" ========================================");
    }
}
