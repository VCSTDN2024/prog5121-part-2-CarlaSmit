package com.example;
import java.util.*;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Message {
   private String messageID;
    private String messageHash;
    private String recipient;
    private String messageContent;
    private static int totalMessagesSent = 0;
    private static List<Message> allMessages = new ArrayList<>();
    
    // Constructor
    public Message(String recipient, String messageContent) {
        this.recipient = recipient;
        this.messageContent = messageContent;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
        totalMessagesSent++;
        allMessages.add(this);
    }
    
    // Generate random 10-digit message ID
    private String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }
    
    // Method: checkMessageID() - ensures message ID is not more than 10 characters
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }
    
    // Method: checkRecipientCell() - ensures recipient cell number is no more than 10 characters and starts with international code
    // Method: checkRecipientCell() - ensures recipient cell number is no more than 10 characters and starts with international code
public int checkRecipientCell() {
    if (recipient == null || recipient.isEmpty()) {
        return 0; // Invalid
    }
    
    // Check if starts with + (international code)
    if (!recipient.startsWith("+")) {
        return 0; // Invalid
    }
    
    // Get the number part (without the +)
    String numberPart = recipient.substring(1);
    
    // Check if number part is too long (max 10 digits after +)
    if (numberPart.length() > 10) {
        return 0; // Invalid
    }
    
    // Check if the number part contains only digits
    if (!numberPart.matches("\\d+")) {
        return 0; // Invalid
    }
    
    return 1; // Valid
}
    
    // Method: createMessageHash() - creates and returns the message hash
    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2 || messageContent == null || messageContent.trim().isEmpty()) {
            return "00:0:INVALID";
        }
        
        String[] words = messageContent.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0].toUpperCase() : "";
        String lastWord = words.length > 1 ? words[words.length - 1].toUpperCase() : firstWord.toUpperCase();
        
        String firstTwoDigits = messageID.substring(0, 2);
        int messageLength = messageContent.length();
        
        return firstTwoDigits + ":" + messageLength + ":" + firstWord + lastWord;
    }
    
    // Method: SentMessage() - allows user to choose send, store, or disregard
    public String SentMessage() {
        String[] options = {"Send Message", "Disregard Message", "Store Message"};
        int choice = JOptionPane.showOptionDialog(
            null,
            "What would you like to do with this message?",
            "Message Options",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        switch (choice) {
            case 0:
                return "Message successfully sent.";
            case 1:
                return "Press 0 to delete message.";
            case 2:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Message successfully sent.";
        }
    }
    
    // Method: printMessages() - returns list of all messages sent
    public static String printMessages() {
        if (allMessages.isEmpty()) {
            return "No messages have been sent.";
        }
        
        StringBuilder result = new StringBuilder();
        result.append("=== All Messages Sent ===\n");
        for (int i = 0; i < allMessages.size(); i++) {
            Message msg = allMessages.get(i);
            result.append("Message ").append(i + 1).append(":\n");
            result.append("Message ID: ").append(msg.messageID).append("\n");
            result.append("Message Hash: ").append(msg.messageHash).append("\n");
            result.append("Recipient: ").append(msg.recipient).append("\n");
            result.append("Message: ").append(msg.messageContent).append("\n");
            result.append("---\n");
        }
        return result.toString();
    }
    
    // Method: returnTotalMessages() - returns total number of messages sent
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
    public static void resetCounters() {
    totalMessagesSent = 0;
    allMessages.clear();
}
    
    // Method: storeMessage() - stores messages in JSON format
    public void storeMessage() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = sdf.format(new Date());
            
            // Create JSON string manually
            StringBuilder json = new StringBuilder();
            json.append("{\n");
            json.append("  \"messageID\": \"").append(escapeJson(this.messageID)).append("\",\n");
            json.append("  \"messageHash\": \"").append(escapeJson(this.messageHash)).append("\",\n");
            json.append("  \"recipient\": \"").append(escapeJson(this.recipient)).append("\",\n");
            json.append("  \"messageContent\": \"").append(escapeJson(this.messageContent)).append("\",\n");
            json.append("  \"timestamp\": \"").append(timestamp).append("\"\n");
            json.append("}\n");
            
            FileWriter writer = new FileWriter("stored_messages.json", true);
            writer.write(json.toString());
            writer.close();
            
            System.out.println("Message stored successfully in JSON format.");
        } catch (IOException e) {
            System.err.println("Error storing message: " + e.getMessage());
        }
    }
    
    // Helper method to escape JSON strings
    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
    public String getMessageID() { 
        return messageID; 
    }
    public String getMessageHash() {
         return messageHash; 
        }
    public String getRecipient() {
         return recipient; 
        }
    public String getMessageContent() {
         return messageContent; 
        }
    // Method: resetCounters() - resets static counters for testing

}
