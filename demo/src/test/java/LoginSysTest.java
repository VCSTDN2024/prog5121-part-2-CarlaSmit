import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.LoginSys;

public class LoginSysTest {

    private LoginSys loginSys;

    @BeforeEach
    public void setUp() {
        loginSys = new LoginSys();
    }

    @Test
    public void testValidUsername() {
        String result = loginSys.registerUser("user_", "Password1!", "+12345678901", "John", "Doe");
        assertTrue(result.contains("Username successfully captured.") || result.equals("User registered successfully."));
    }

    @Test
    public void testInvalidUsername() {
        String result = loginSys.registerUser("user", "Password1!", "+12345678901", "John", "Doe");
        assertEquals("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.", result);
    }

    @Test
    public void testValidPassword() {
        String result = loginSys.registerUser("user_", "Password1!", "+12345678901", "John", "Doe");
        assertTrue(result.contains("Password successfully captured.") || result.equals("User registered successfully."));
    }

    @Test
    public void testInvalidPassword() {
        String result = loginSys.registerUser("user_", "password", "+12345678901", "John", "Doe");
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", result);
    }

    @Test
    public void testValidPhoneNumber() {
        String result = loginSys.registerUser("user_", "Password1!", "+12345678901", "John", "Doe");
        assertTrue(result.contains("Cell phone number successfully added.") || result.equals("User registered successfully."));
    }

    @Test
    public void testInvalidPhoneNumber() {
        String result = loginSys.registerUser("user_", "Password1!", "1234567890", "John", "Doe");
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.", result);
    }

    @Test
    public void testSuccessfulLogin() {
        loginSys.registerUser("user_", "Password1!", "+12345678901", "John", "Doe");
        String loginMessage = loginSys.loginUser("user_", "Password1!");
        assertEquals("Welcome John, Doe it is great to see you again.", loginMessage);
    }

    @Test
    public void testFailedLogin() {
        loginSys.registerUser("user_", "Password1!", "+12345678901", "John", "Doe");
        String loginMessage = loginSys.loginUser("user_", "WrongPass1!");
        assertEquals("Username or password incorrect, please try again.", loginMessage);
    }

    // Additional tests for thorough coverage

    @Test
    public void testEmptyUsername() {
        String result = loginSys.registerUser("", "Password1!", "+12345678901", "John", "Doe");
        assertEquals("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.", result);
    }

    @Test
    public void testEmptyPassword() {
        String result = loginSys.registerUser("user_", "", "+12345678901", "John", "Doe");
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", result);
    }

    @Test
    public void testEmptyPhoneNumber() {
        String result = loginSys.registerUser("user_", "Password1!", "", "John", "Doe");
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.", result);
    }

    @Test
    public void testEmptyFirstName() {
        String result = loginSys.registerUser("user_", "Password1!", "+12345678901", "", "Doe");
        assertEquals("User registered successfully.", result);
    }

    @Test
    public void testEmptyLastName() {
        String result = loginSys.registerUser("user_", "Password1!", "+12345678901", "John", "");
        assertEquals("User registered successfully.", result);
    }

    @Test
    public void testLoginWithEmptyUsername() {
        loginSys.registerUser("user_", "Password1!", "+12345678901", "John", "Doe");
        String loginMessage = loginSys.loginUser("", "Password1!");
        assertEquals("Username or password incorrect, please try again.", loginMessage);
    }

    @Test
    public void testLoginWithEmptyPassword() {
        loginSys.registerUser("user_", "Password1!", "+12345678901", "John", "Doe");
        String loginMessage = loginSys.loginUser("user_", "");
        assertEquals("Username or password incorrect, please try again.", loginMessage);
    }
}
