package com.javasampleapproach.mysql.validater;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class PasswordValidator {
 
private static Pattern passwordPattern = 
        Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])[^\\s]{8,}$");
     
    public static boolean validatePassword(String userName){
         
        Matcher mtch = passwordPattern.matcher(userName);
        if(mtch.matches()){
            return true;
        }
        return false;
    }
}

