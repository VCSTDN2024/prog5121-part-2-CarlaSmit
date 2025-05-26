package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LoginSys {
    private List<User> users;

    public LoginSys() {
        users = new ArrayList<>();
    }

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) {
            return false;
        }
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            return false;
        }
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            return false;
        }
        if (!Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) {
            return false;
        }
        return true;
    }

    public boolean checkCellPhoneNumber(String phoneNumber) {
        String regex = "^\\+\\d{1,3}\\d{7,10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches() && phoneNumber.length() <= 13;
    }

    public String registerUser(String username, String password, String phoneNumber, String firstName, String lastName) {
        if (!checkUserName(username)) {
            return "The username is incorrectly formatted.";
        }
        if (!checkPasswordComplexity(password)) {
            return "The password does not meet the complexity requirements.";
        }
        if (!checkCellPhoneNumber(phoneNumber)) {
            return "The cell phone number is incorrectly formatted or does not contain international code.";
        }
        User newUser = new User(username, password, phoneNumber, firstName, lastName);
        users.add(newUser);
        return "The two above conditions have been met, and the user has been registered successfully.";
    }

    public boolean loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "A successful login";
        } else {
            return "A failed login";
        }
    }
}
