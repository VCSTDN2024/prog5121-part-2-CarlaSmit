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

    public String registerUser(String username, String password, String phoneNumber, String firstName, String lastName) {
        String usernameValidation = validateUsername(username);
        if (!usernameValidation.equals("Username successfully captured.")) {
            return usernameValidation;
        }

        String passwordValidation = validatePassword(password);
        if (!passwordValidation.equals("Password successfully captured.")) {
            return passwordValidation;
        }

        String phoneValidation = validatePhoneNumber(phoneNumber);
        if (!phoneValidation.equals("Cell phone number successfully added.")) {
            return phoneValidation;
        }

        User newUser = new User(username, password, phoneNumber, firstName, lastName);
        users.add(newUser);
        return "User registered successfully.";
    }

    private String validateUsername(String username) {
        if (username.contains("_") && username.length() <= 5) {
            return "Username successfully captured.";
        } else {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
    }

    private String validatePassword(String password) {
        if (password.length() < 8) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        return "Password successfully captured.";
    }

    private String validatePhoneNumber(String phoneNumber) {
        // Regular expression to check international country code followed by number, max 10 characters
        // Example: +1234567890 or +12 34567890 (spaces optional)
        String regex = "^\\+\\d{1,3}\\d{7,10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.matches() && phoneNumber.length() <= 13) {
            return "Cell phone number successfully added.";
        } else {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
    }

    public String loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return "Welcome " + user.getFirstName() + ", " + user.getLastName() + " it is great to see you again.";
            }
        }
        return "Username or password incorrect, please try again.";
    }
}
