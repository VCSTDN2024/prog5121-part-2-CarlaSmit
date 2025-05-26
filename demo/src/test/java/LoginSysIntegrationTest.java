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
        assertEquals("The two above conditions have been met, and the user has been registered successfully.", regResult);

        // Successful login
        boolean loginSuccess = loginSys.loginUser("user_", "Password1!");
        String loginMessage = loginSys.returnLoginStatus(loginSuccess);
        assertEquals("A successful login", loginMessage);

        // Failed login with wrong password
        loginSuccess = loginSys.loginUser("user_", "WrongPass1!");
        loginMessage = loginSys.returnLoginStatus(loginSuccess);
        assertEquals("A failed login", loginMessage);

        // Failed login with wrong username
        loginSuccess = loginSys.loginUser("wrong_", "Password1!");
        loginMessage = loginSys.returnLoginStatus(loginSuccess);
        assertEquals("A failed login", loginMessage);
    }

    @Test
    public void testEdgeCaseUsernames() {
        // Username exactly 5 characters with underscore
        String result = loginSys.registerUser("us_er", "Password1!", "+12345678901", "John", "Doe");
        assertEquals("The two above conditions have been met, and the user has been registered successfully.", result);

        // Username 6 characters with underscore (invalid)
        result = loginSys.registerUser("user__6", "Password1!", "+12345678901", "John", "Doe");
        assertEquals("The username is incorrectly formatted.", result);

        // Username without underscore
        result = loginSys.registerUser("user6", "Password1!", "+12345678901", "John", "Doe");
        assertEquals("The username is incorrectly formatted.", result);
    }

    @Test
    public void testEdgeCasePasswords() {
        // Password with exactly 8 characters meeting all criteria
        String result = loginSys.registerUser("user_", "Pass1!A2", "+12345678901", "John", "Doe");
        assertEquals("The two above conditions have been met, and the user has been registered successfully.", result);

        // Password missing capital letter
        result = loginSys.registerUser("user_", "password1!", "+12345678901", "John", "Doe");
        assertEquals("The password does not meet the complexity requirements.", result);

        // Password missing number
        result = loginSys.registerUser("user_", "Password!", "+12345678901", "John", "Doe");
        assertEquals("The password does not meet the complexity requirements.", result);

        // Password missing special character
        result = loginSys.registerUser("user_", "Password1", "+12345678901", "John", "Doe");
        assertEquals("The password does not meet the complexity requirements.", result);
    }

    @Test
    public void testEdgeCasePhoneNumbers() {
        // Valid phone number with international code
        String result = loginSys.registerUser("user_", "Password1!", "+12345678901", "John", "Doe");
        assertEquals("The two above conditions have been met, and the user has been registered successfully.", result);

        // Phone number too short
        result = loginSys.registerUser("user_", "Password1!", "+1234567", "John", "Doe");
        assertEquals("The cell phone number is incorrectly formatted or does not contain international code.", result);

        // Phone number with letters
        result = loginSys.registerUser("user_", "Password1!", "+12a3456789", "John", "Doe");
        assertEquals("The cell phone number is incorrectly formatted or does not contain international code.", result);

        // Phone number missing plus sign
        result = loginSys.registerUser("user_", "Password1!", "12345678901", "John", "Doe");
        assertEquals("The cell phone number is incorrectly formatted or does not contain international code.", result);
    }
}
