import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.LoginSys;

public class LoginSysIntegrationTest {

    private LoginSys loginSys;

    @BeforeEach
    public void setUp() {
        loginSys = new LoginSys();
    }

    @Test
    public void testFullRegistrationAndLoginFlow() {
        // Valid registration
        String regResult = loginSys.registerUser("user_", "Password1!", "+12345678901", "John", "Doe");
        assertEquals("User registered successfully.", regResult);

        // Successful login
        String loginMessage = loginSys.loginUser("user_", "Password1!");
        assertEquals("Welcome John, Doe it is great to see you again.", loginMessage);

        // Failed login with wrong password
        loginMessage = loginSys.loginUser("user_", "WrongPass1!");
        assertEquals("Username or password incorrect, please try again.", loginMessage);

        // Failed login with wrong username
        loginMessage = loginSys.loginUser("wrong_", "Password1!");
        assertEquals("Username or password incorrect, please try again.", loginMessage);
    }

    @Test
    public void testEdgeCaseUsernames() {
        // Username exactly 5 characters with underscore
        String result = loginSys.registerUser("us_er", "Password1!", "+12345678901", "John", "Doe");
        assertEquals("User registered successfully.", result);

        // Username 6 characters with underscore (invalid)
        result = loginSys.registerUser("user__6", "Password1!", "+12345678901", "John", "Doe");
        assertEquals("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.", result);

        // Username without underscore
        result = loginSys.registerUser("user6", "Password1!", "+12345678901", "John", "Doe");
        assertEquals("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.", result);
    }

    @Test
    public void testEdgeCasePasswords() {
        // Password with exactly 8 characters meeting all criteria
        String result = loginSys.registerUser("user_", "Pass1!A2", "+12345678901", "John", "Doe");
        assertEquals("User registered successfully.", result);

        // Password missing capital letter
        result = loginSys.registerUser("user_", "password1!", "+12345678901", "John", "Doe");
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", result);

        // Password missing number
        result = loginSys.registerUser("user_", "Password!", "+12345678901", "John", "Doe");
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", result);

        // Password missing special character
        result = loginSys.registerUser("user_", "Password1", "+12345678901", "John", "Doe");
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", result);
    }

    @Test
    public void testEdgeCasePhoneNumbers() {
        // Valid phone number with international code
        String result = loginSys.registerUser("user_", "Password1!", "+12345678901", "John", "Doe");
        assertEquals("User registered successfully.", result);

        // Phone number too short
        result = loginSys.registerUser("user_", "Password1!", "+1234567", "John", "Doe");
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.", result);

        // Phone number with letters
        result = loginSys.registerUser("user_", "Password1!", "+12a3456789", "John", "Doe");
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.", result);

        // Phone number missing plus sign
        result = loginSys.registerUser("user_", "Password1!", "12345678901", "John", "Doe");
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.", result);
    }
}
