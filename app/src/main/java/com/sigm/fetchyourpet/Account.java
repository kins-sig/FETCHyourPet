package com.sigm.fetchyourpet;


public class Account {

    private String username;

    private String password;
    private Boolean isAdopter;
    static Account currentAccount;

    public Account(String username, String password, boolean isAdopter){
        this.username = username;
        this.password = password;
        this.isAdopter = isAdopter;
    }


    public static String getMD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
    public String getUsername() {
        return username;
    }

    public Account(){

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
        this.isAdopter=b;
    }
}
