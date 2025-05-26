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
        assertTrue(loginSys.checkUserName("user_"));
    }

    @Test
    public void testInvalidUsername() {
        assertFalse(loginSys.checkUserName("user"));
    }

    @Test
    public void testValidPassword() {
        assertTrue(loginSys.checkPasswordComplexity("Password1!"));
    }

    @Test
    public void testInvalidPassword() {
        assertFalse(loginSys.checkPasswordComplexity("password"));
    }

    @Test
    public void testValidPhoneNumber() {
        assertTrue(loginSys.checkCellPhoneNumber("+12345678901"));
    }

    @Test
    public void testInvalidPhoneNumber() {
        assertFalse(loginSys.checkCellPhoneNumber("1234567890"));
    }

    @Test
    public void testSuccessfulLogin() {
        loginSys.registerUser("user_", "Password1!", "+12345678901", "John", "Doe");
        assertTrue(loginSys.loginUser("user_", "Password1!"));
    }

    @Test
    public void testFailedLogin() {
        loginSys.registerUser("user_", "Password1!", "+12345678901", "John", "Doe");
        assertFalse(loginSys.loginUser("user_", "WrongPass1!"));
    }

    // Additional tests for thorough coverage

    @Test
    public void testEmptyUsername() {
        assertFalse(loginSys.checkUserName(""));
    }

    @Test
    public void testEmptyPassword() {
        assertFalse(loginSys.checkPasswordComplexity(""));
    }

    @Test
    public void testEmptyPhoneNumber() {
        assertFalse(loginSys.checkCellPhoneNumber(""));
    }

    @Test
    public void testEmptyFirstName() {
        String result = loginSys.registerUser("user_", "Password1!", "+12345678901", "", "Doe");
        assertEquals("The two above conditions have been met, and the user has been registered successfully.", result);
    }

    @Test
    public void testEmptyLastName() {
        String result = loginSys.registerUser("user_", "Password1!", "+12345678901", "John", "");
        assertEquals("The two above conditions have been met, and the user has been registered successfully.", result);
    }

    @Test
    public void testLoginWithEmptyUsername() {
        loginSys.registerUser("user_", "Password1!", "+12345678901", "John", "Doe");
        assertFalse(loginSys.loginUser("", "Password1!"));
    }

    @Test
    public void testLoginWithEmptyPassword() {
        loginSys.registerUser("user_", "Password1!", "+12345678901", "John", "Doe");
        assertFalse(loginSys.loginUser("user_", ""));
    }
}
