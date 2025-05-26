import com.example.Message;

public class MessageTest {
    public static void main(String[] args) {
        System.out.println("=== Running Unit Tests ===");
        
        testMessageLength();
        testRecipientValidation();
        testMessageCreation();
        
        System.out.println("=== All Tests Completed ===");
    }
    
    // Test: Message should not exceed 250 characters
    public static void testMessageLength() {
        System.out.println("\n--- Testing Message Length ---");
        
        // Test success case
        Message validMsg = new Message("+1234567890", "Hi Mike, can you join us for dinner tonight");
        if (validMsg.getMessageContent().length() <= 250) {
            System.out.println("SUCCESS: Message ready to send.");
        } else {
            System.out.println("FAILURE: Message exceeds character limit.");
        }
        
        // Test failure case
        String longMessage = "This is a very long message that exceeds the 250 character limit. ".repeat(5);
        if (longMessage.length() > 250) {
            int excess = longMessage.length() - 250;
            System.out.println("SUCCESS: Message exceeds 250 characters by " + excess + ", please reduce size.");
        }
    }
    
    // Test: Recipient number validation
    public static void testRecipientValidation() {
        System.out.println("\n--- Testing Recipient Validation ---");
        
        // Test success case
        Message validRecipient = new Message("+2771863002", "Test message");
        if (validRecipient.checkRecipientCell() == 1) {
            System.out.println("SUCCESS: Cell phone number successfully captured.");
        } else {
            System.out.println("FAILURE: Valid number rejected.");
        }
        
        // Test failure case
        Message invalidRecipient = new Message("1234567890", "Test message");
        if (invalidRecipient.checkRecipientCell() == 0) {
            System.out.println("SUCCESS: Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
        } else {
            System.out.println("FAILURE: Invalid number accepted.");
        }
    }
    
    // Test: Message creation and hash generation
    public static void testMessageCreation() {
        System.out.println("\n--- Testing Message Creation ---");
        
        // Test Data 1
        Message msg1 = new Message("+2771863002", "Hi Mike, can you join us for dinner tonight");
        System.out.println("Message 1 created:");
        System.out.println("ID: " + msg1.getMessageID());
        System.out.println("Hash: " + msg1.getMessageHash());
        System.out.println("Recipient: " + msg1.getRecipient());
        System.out.println("Content: " + msg1.getMessageContent());
        
        // Test Data 2  
        Message msg2 = new Message("0857975889", "Hi Keegan, did you receive the payment?");
        System.out.println("\nMessage 2 created:");
        System.out.println("ID: " + msg2.getMessageID());
        System.out.println("Hash: " + msg2.getMessageHash());
        System.out.println("Recipient: " + msg2.getRecipient());
        System.out.println("Content: " + msg2.getMessageContent());
        
        // Test hash format
        String hash1 = msg1.getMessageHash();
        if (hash1.contains(":") && hash1.length() > 5) {
            System.out.println("SUCCESS: Hash format is correct - " + hash1);
        } else {
            System.out.println("FAILURE: Hash format is incorrect - " + hash1);
        }
        
        // Test total messages
        System.out.println("\nTotal messages created: " + Message.returnTotalMessages());
    }
}

