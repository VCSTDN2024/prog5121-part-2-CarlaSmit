package com.example;
//ST10477400 Carla Smit
import java.util.Scanner;

import javax.swing.JOptionPane;

public class QuickChatApp {
    private static Scanner scanner = new Scanner(System.in);
    private static String currentUser;
    private static int maxMessages;
    
    
    public static void main(String[] args) {
        //Force GUI to run in non-headless mode
    System.setProperty("java.awt.headless", "false");

        System.out.println("=== QuickChat Application ===");
        testGUI();//test the GUI so just say ok
        //Step 1: User login
        if (!login()) {
            System.out.println("Login failed. Exiting application.");
            return;
        }
        
        //Step 2: Get number of messages user wants to send
        maxMessages = getMessageCount();
        
        //Step 3: Main application loop
        runApplication();
        
        scanner.close();
    }
    
    private static boolean login() {
        System.out.println("=== Login Required ===");
        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Enter password: ");
            String password = scanner.nextLine().trim();
            
            //Simple authentication (you can enhance this)
            if (!username.isEmpty() && !password.isEmpty()) {
                currentUser = username;
                System.out.println("Login successful! Welcome, " + username + "!");
                return true;
            } else {
                attempts++;
                System.out.println("Invalid credentials. Please try again. (" + (3-attempts) + " attempts remaining)");
            }
        }
        return false;
    }
    
    private static int getMessageCount() {
        while (true) {//this loop will continue until a valid number is entered
            try {
                System.out.print("How many messages would you like to send? ");
                int count = Integer.parseInt(scanner.nextLine().trim());
                if (count > 0) {
                    return count;
                } else {
                    System.out.println("Please enter a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private static void runApplication() {
        while (true) {
            displayMenu();
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    sendMessages();
                    break;
                case "2":
                    showRecentMessages();
                    break;
                case "3":
                    System.out.println("Thank you for using QuickChat. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }
    
    private static void displayMenu() {//this displays the main menu
        System.out.println("\n=== Welcome to QuickChat ===");
        System.out.println("Please choose an option:");
        System.out.println("1. Send Messages");
        System.out.println("2. Show recently sent messages");
        System.out.println("3. Quit");
        System.out.print("Enter your choice (1-3): ");
    }
    
    private static void sendMessages() {
        System.out.println("\n=== Send Messages ===");
        System.out.println("You can send up to " + maxMessages + " messages.");
        
        for (int i = 0; i < maxMessages; i++) {
            System.out.println("\n--- Message " + (i + 1) + " of " + maxMessages + " ---");
            
            //Get recipient with validation
            String recipient = getValidRecipient();
            
            //Get message content with validation
            String messageContent = getValidMessage();
            
            //Create message object
            Message message = new Message(recipient, messageContent);
            
            //Display message details
            displayMessageDetails(message);
            
            //Handle message sending/storing/discarding
            String result = message.SentMessage();
            System.out.println(result);
            
            //Ask if user wants to continue
            if (i < maxMessages - 1) {
                System.out.print("Continue sending messages? (y/n): ");
                String continueChoice = scanner.nextLine().trim().toLowerCase();
                if (!continueChoice.equals("y") && !continueChoice.equals("yes")) {
                    break;
                }
            }
        }
        
        //Display total messages sent
        System.out.println("\nTotal messages processed: " + Message.returnTotalMessages());
    }
    
    private static String getValidRecipient() {
        while (true) {
            System.out.print("Enter recipient number (with international code, max 10 chars): ");
            String recipient = scanner.nextLine().trim();
            
            //Create temporary message to test validation
            Message tempMessage = new Message(recipient, "test");
            if (tempMessage.checkRecipientCell() == 1) {
                System.out.println("Cell phone number successfully captured.");
                return recipient;
            } else {
                System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
            }
        }
    }
    
    private static String getValidMessage() {
        while (true) {//this loop will continue until a valid message is entered
            System.out.print("Enter your message (max 250 characters): ");
            String message = scanner.nextLine().trim();
            
            if (message.length() > 250) {
                int excess = message.length() - 250;
                System.out.println("Message exceeds 250 characters by " + excess + ", please reduce size.");
            } else if (message.length() < 50) {
                System.out.println("Please enter a message of less than 50 characters.");
                return message;
            } else {
                System.out.println("Message ready to send.");
                return message;
            }
        }
    }
    
    private static void displayMessageDetails(Message message) {//this displays the message details
        System.out.println("\n=== Message Details ===");
        System.out.println("Message ID: " + message.getMessageID());
        System.out.println("Message Hash: " + message.getMessageHash());
        System.out.println("Recipient: " + message.getRecipient());
        System.out.println("Message: " + message.getMessageContent());
    }
    
    private static void showRecentMessages() {//this shows the recent messages
        System.out.println("\n=== Recent Messages ===");
        System.out.println("Coming Soon.");
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
    /*OpenAI, ChatGPT,Help with JSON*/
    public static void testGUI() {
    JOptionPane.showMessageDialog(null, "GUI Test - Can you see this?");//this tests the GUI to see if it works
}
}//ST10477400 Carla Smit


