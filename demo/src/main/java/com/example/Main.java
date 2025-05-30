package com.example;
//main just for tesdting purposes
//im using QuickChatApp.java as the main class to run the application
//ST10477400 Carla Smit
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginSys loginSys = new LoginSys();

        System.out.println("=== Registration ===");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter cell phone number with international code (e.g., +1234567890): ");
        String phoneNumber = scanner.nextLine();

        String registrationResult = loginSys.registerUser(username, password, phoneNumber, firstName, lastName);
        System.out.println(registrationResult);

        if (registrationResult.equals("The two above conditions have been met, and the user has been registered successfully.")) {
            System.out.println("\n=== Login ===");
            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();

            boolean loginSuccess = loginSys.loginUser(loginUsername, loginPassword);
            String loginMessage = loginSys.returnLoginStatus(loginSuccess);
            System.out.println(loginMessage);
        }

        scanner.close();
    }
    //ST10477400 Carla Smit
}
