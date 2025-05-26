import com.example.Message;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

//ST10477400 Carla Smit
public class MessageJUnitTests {
    
    private Message testMessage1;
    private Message testMessage2;
    
    @BeforeEach
    void setUp() {
        //Reset static counters before each test
        Message.resetCounters(); 
        
        //Test data from assignment
        testMessage1 = new Message("+2771863002", "Hi Mike, can you join us for dinner tonight");
        testMessage2 = new Message("0857975889", "Hi Keegan, did you receive the payment?");
    }
    
    @Test
    @DisplayName("Test Message ID Creation and Validation")
    void testMessageIDCreation() {
        //Test that message ID is created
        assertNotNull(testMessage1.getMessageID(), "Message ID should not be null");
        
        //Test that message ID is correct length (10 digits)
        assertEquals(10, testMessage1.getMessageID().length(), 
            "Message ID should be exactly 10 characters");
        
        //Test that message ID contains only digits
        assertTrue(testMessage1.getMessageID().matches("\\d{10}"), 
            "Message ID should contain only digits");
        
        //Test checkMessageID method
        assertTrue(testMessage1.checkMessageID(), 
            "checkMessageID should return true for valid 10-digit ID");
    }
    
    @Test
    @DisplayName("Test Recipient Cell Number Validation")
    void testRecipientValidation() {
        //Test valid recipient (with international code)
        assertEquals(1, testMessage1.checkRecipientCell(), 
            "Valid recipient with + should return 1");
        
        //Test invalid recipient (without international code)
        assertEquals(0, testMessage2.checkRecipientCell(), 
            "Invalid recipient without + should return 0");
        
        //Test recipient too long
        Message longRecipient = new Message("+12345678901", "Test message");
        assertEquals(0, longRecipient.checkRecipientCell(), 
            "Recipient longer than 10 characters should return 0");
    }
    
    @Test
    @DisplayName("Test Message Hash Creation")
    void testMessageHashCreation() {
        String hash1 = testMessage1.createMessageHash();
        
        //Test hash format: XX:YY:WORDWORD
        assertTrue(hash1.matches("\\d{2}:\\d+:[A-Z]+"), 
            "Hash should match format XX:YY:WORDWORD");
        
        //Test hash contains first two digits of message ID
        String messageID = testMessage1.getMessageID();
        String expectedPrefix = messageID.substring(0, 2);
        assertTrue(hash1.startsWith(expectedPrefix), 
            "Hash should start with first two digits of message ID");
        
        //Test hash contains message length
        int messageLength = testMessage1.getMessageContent().length();
        assertTrue(hash1.contains(":" + messageLength + ":"), 
            "Hash should contain message length");
        
        //Test hash contains first and last words in uppercase
        assertTrue(hash1.contains("HITONIGHT"), 
            "Hash should contain first and last words: HITONIGHT");
    }
    
    @Test
    @DisplayName("Test Message Length Validation")
    void testMessageLengthValidation() {
        //Test valid message length
        assertTrue(testMessage1.getMessageContent().length() <= 250, 
            "Message should not exceed 250 characters");
        
        //Test message content is not empty
        assertFalse(testMessage1.getMessageContent().trim().isEmpty(), 
            "Message content should not be empty");
        
        //Create long message for testing
        String longMessage = "A".repeat(251);
        Message longMsg = new Message("+1234567890", longMessage);
        assertTrue(longMsg.getMessageContent().length() > 250, 
            "Long message test should exceed 250 characters");
    }
    
    @Test
    @DisplayName("Test Message Counter Functionality")
    void testMessageCounter() {
        int initialCount = Message.returnTotalMessages();
        
        //Create new message
        Message newMsg = new Message("+1111111111", "Test counter");
        
        //Verify counter incremented
        assertEquals(initialCount + 1, Message.returnTotalMessages(), 
            "Message counter should increment when new message is created");
    }
    
    @Test
    @DisplayName("Test Print Messages Functionality")
    void testPrintMessages() {
        String messageList = Message.printMessages();
        
        //Test that messages are listed
        assertNotNull(messageList, "Print messages should not return null");
        assertTrue(messageList.contains("Message ID"), 
            "Message list should contain Message ID");
        assertTrue(messageList.contains("Message Hash"), 
            "Message list should contain Message Hash");
        assertTrue(messageList.contains("Recipient"), 
            "Message list should contain Recipient");
    }
    
    @Test
    @DisplayName("Test JSON Storage Functionality")
    void testJSONStorage() {
        //Test store message method
        testMessage1.storeMessage();
        
        //Check if JSON file was created
        File jsonFile = new File("stored_messages.json");
        assertTrue(jsonFile.exists(), 
            "JSON file should be created when storing messages");
        
        //Clean up test file
        if (jsonFile.exists()) {
            jsonFile.delete();
        }
    }
    
    @Test
    @DisplayName("Test Assignment Specific Test Data")
    void testAssignmentTestData() {
        //Test Case 1: +2771863002, "Hi Mike, can you join us for dinner tonight"
        assertEquals("+2771863002", testMessage1.getRecipient(), 
            "Test data 1 recipient should match");
        assertEquals("Hi Mike, can you join us for dinner tonight", testMessage1.getMessageContent(), 
            "Test data 1 message should match");
        
        //Test Case 2: 0857975889, "Hi Keegan, did you receive the payment?"
        assertEquals("0857975889", testMessage2.getRecipient(), 
            "Test data 2 recipient should match");
        assertEquals("Hi Keegan, did you receive the payment?", testMessage2.getMessageContent(), 
            "Test data 2 message should match");
        
        //Test that hash contains expected words
        String hash2 = testMessage2.createMessageHash();
        assertTrue(hash2.contains("HIPAYMENT"), 
            "Hash should contain HIPAYMENT for test data 2");
    }//ST10477400 Carla Smit
}
