package com.sigm.fetchyourpet;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Models the PotentialAdopter object
 * @author Dylan
 */
public class PotentialAdopter {


    public static PotentialAdopter currentAdopter;
    private static ArrayList<PotentialAdopter> adopters = new ArrayList<>();
    ArrayList<Dog> dislikedDogsArray = new ArrayList<>();
    ArrayList<Dog> favoritedDogsArray = new ArrayList<>();
    String dislikedDogs = "";
    String favoritedDogs = "";
    boolean alreadySet = false;
    private int phone;
    private Bitmap photo;
    private String firstName, email, password, image, zip, username, adopterID, traits = "";

    /**
     *
     * @param b - the uploaded bitmap of the adopter profile
     * @param fname - the adopters first name
     * @param zip - the adopters zipcode
     * @param email - the adopters email
     * @param path - the path to the image stored in the database
     */
    public PotentialAdopter(Bitmap b, String fname, String zip, String email, String path) {
        this.photo = b;
        this.firstName = fname;
        this.email = email;
        this.zip = zip;
        this.image = path;

        adopters.add(this);
    }

    public PotentialAdopter() {
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdopterID() {
        return adopterID;
    }

    public void setAdopterID(String adopterID) {
        this.adopterID = adopterID;
    }

    public String getTraits() {
        return this.traits;
    }

    public void setTraits(String s) {
        this.traits = s;
    }

    /**
     *
     * @return a matrix version of the traits string (required by Algo class)
     */
    public Matrix get_user_traits() {

        double[] data = new double[30];
        char[] chars = traits.trim().toCharArray();
        int i = 0;
        for (char c : chars) {
            data[i] = Character.getNumericValue(c);
            i++;

        }
        return new Matrix(data);


    }

    public String getDislikedDogs() {
        return dislikedDogs;
    }

    public void setDislikedDogs(String s) {
        this.dislikedDogs = s;
    }

    public void setFavoritedDogs(String s) {
        this.favoritedDogs = s;
    }

    /**
     * Sets the array of disliked dogs for the current adopter.
     * Initially, dislikedDogs is a string that is grabbed from the database, containing
     * dog IDs separated by a space. We must convert this string into an array of Dog objects
     * in order to properly portray the disliked dogs
     */
    public void setDislikedDogsArray() {
        //we only want to set the array once
        if (!alreadySet) {
            alreadySet = true;
            String s = dislikedDogs.trim();
            String s2 = favoritedDogs.trim();
            String[] disliked = s.split(" ");
            String[] favorited = s2.split(" ");


            for (String id : disliked) {
                for (Dog d : Dog.dogList) {
                    if (id.trim().equals(d.getId())) {
                        dislikedDogsArray.add(d);
                        Log.d("test", d.getName());

                    }

                }

            }
            for (String id : favorited) {
                for (Dog d : Dog.dogList) {
                    if (id.trim().equals(d.getId())) {
                        favoritedDogsArray.add(d);
                        d.favorited = true;
                        Log.d("test", "favorite " + d.getName());

                    }
                }


            }

        }
    }

    /**
     * Clears the disliked dogs for the current user and updates the value in the database
     */
    public void clearDislikes() {
        dislikedDogsArray.clear();

        Map<String, Object> update = new HashMap<>();
        String s = "";

        update.put("dislikedDogs", s);

        MainActivity.firestore
                .collection("adopter")
                .document(PotentialAdopter.currentAdopter.getAdopterID())
                .update(update);
    }
}
