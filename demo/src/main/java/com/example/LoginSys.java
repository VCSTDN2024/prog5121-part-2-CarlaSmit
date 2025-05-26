package com.example;
//ST10477400 Carla Smit
import java.util.ArrayList;//used arraylist-easier 
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LoginSys {
    private List<User> users;

    public LoginSys() {
        users = new ArrayList<>();
    }

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;//function that checks username

    }

    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) {
            return false;//this checks if the password is less than 8 characters
        }
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            return false;//this checks if the password has a capital letter
        }
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            return false;//this checks if the password has a number
        }
        if (!Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) {
            return false;//this checks if the password has a special character
        }
        return true;
    }

    public boolean checkCellPhoneNumber(String phoneNumber) {
        String regex = "^\\+\\d{1,3}\\d{7,10}$";//this regex checks if the phone number starts with a '+' followed by 1 to 3 digits (international code) and then 7 to 10 digits (local number).
        // The total length should not exceed 13 characters (including the '+')
        // This allows for phone numbers like +12345678901 or +1234567890
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches() && phoneNumber.length() <= 13;
        //if the phone number matches the regex and is not longer than 13 characters, it returns true
    }

    public String registerUser(String username, String password, String phoneNumber, String firstName, String lastName) {
        if (!checkUserName(username)) {
            return "The username is incorrectly formatted.";
        }//this checks if the username is formatted correctly
        if (!checkPasswordComplexity(password)) {
            return "The password does not meet the complexity requirements.";
        }//this checks if the password meets the complexity requirements
        if (!checkCellPhoneNumber(phoneNumber)) {
            return "The cell phone number is incorrectly formatted or does not contain international code.";
        }//this checks if the phone number is formatted correctly and contains the international code
        User newUser = new User(username, password, phoneNumber, firstName, lastName);
        users.add(newUser);
        return "The two above conditions have been met, and the user has been registered successfully.";
    }//this registers the user if all the above conditions are met

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
    }//this returns the login status based on whether the login was successful or not

    //ST10477400 Carla Smit

}
