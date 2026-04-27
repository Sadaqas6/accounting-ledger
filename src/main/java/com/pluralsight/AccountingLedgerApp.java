package com.pluralsight;

import java.util.ArrayList;
import java.util.Scanner;

public class AccountingLedgerApp {
    public static void main(String[] args) {

        ArrayList<Transactions> ledger = new ArrayList<>(); // ARRAYLIST OF TRANSACTION OBJECTS

        Scanner sc = new Scanner(System.in);  // ALLOWS US TO READ THE INPUT FROM THE KEYBOARD

        System.out.println("WELCOME TO THE ACCOUNTING LEDGER PAGE");  // GREETING THE USER
        System.out.println("-------------------------------------");

//        homeScreen();
//        ledger();
    }
        public static void homeScreen(Scanner sc){  // DISPLAYS THE MAIN MENU

            System.out.println("""          
                    \tD) Add Deposit
                    \tP) Make Payment(Debit)
                    \tL) Ledger
                    \tX) Exit
                    """);
            System.out.print("Select from the OPTIONS above: "); // PROMPTS THE USER TO SELECT OPTIONS
            String userOption = sc.nextLine().toUpperCase();

            switch (userOption){
                case "D":                // GOES TO THE ADD DEPOSIT SUBMENU
                    // addDeposit(sc);
                    break;
                case "P":                // GOES TO THE MAKE DEPOSIT SUBMENU
                    // makeDeposit(sc);
                    break;
                case "L":                // GOES TO THE LEDGER SUBMENU
                    //  ledger(sc);
                    break;
                case "X":               // SENDS A GOODBYE MESSAGE
                    System.out.println("Thank you for visiting your account. See you soon!!");
                    sc.close();         // CLOSES THE SCANNER
                default:                // HANDLES ANY INPUT THAT AREN'T THE ONES LISTED ABOVE
                    System.out.println("Invalid option. Try again!(Press ENTER to continue");
                    sc.nextLine();
            }

        }
}
