package com.example.messenger.util;

import android.widget.EditText;

public class Util {

    public static boolean dataCheck(String email,String password){
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`" +
                "{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}" +
                "\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)" +
                "+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return (m.matches()&&password.length()>5);
    }

    public static String getTrimText(EditText editText){
        return editText.getText().toString().trim();
    }

    public static boolean checkEmail(String email){
        return dataCheck(email,"aaaaaa");
    }

}
