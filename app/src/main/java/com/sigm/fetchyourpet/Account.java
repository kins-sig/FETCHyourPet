package com.sigm.fetchyourpet;

//Account consists of a username, password, and a boolean value.
//The username acts as a foreign key, the boolean is determines what database table to query.
public class Account {


    static Account currentAccount;
    private String username;
    private String password;
    private Boolean isAdopter;

    public Account(String username, String password, boolean isAdopter) {
        this.username = username;
        this.password = password;
        this.isAdopter = isAdopter;
    }


    public Account() {

    }

    //Takes in a password string and returns a hashed password
    public static String getMD5(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(password.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAdopter() {
        return isAdopter;
    }

    public void setIsAdopter(Boolean b) {
        this.isAdopter = b;
    }
}
