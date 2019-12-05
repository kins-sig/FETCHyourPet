package com.sigm.fetchyourpet;

import android.graphics.Bitmap;

import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Models the Rescue object
 * @author Dylan
 */
public class Rescue {


    public static Rescue currentRescue;

    private static ArrayList<Rescue> rescues = new ArrayList<>();
    public List<Dog> dogs = new ArrayList<>();
    private String organization, street, city, state, email, username, image, zip, rescueID;
    private Bitmap photo = null;
    private StorageReference imageStorageReference;

    /**
     *
     * @param b - the bitmap photo that was uploaded
     * @param name - the name of the rescue rescue
     * @param street - the street address of the rescue
     * @param city - the city of the rescue
     * @param state - the state of the rescue
     * @param zip - the zip of the rescue
     * @param email - the email of the rescue
     * @param path - the path to the image stored in the database uploaded by rescue
     */
    public Rescue(Bitmap b, String name, String street, String city, String state, String zip, String email, String path) {
        this.photo = b;
        this.organization = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.email = email;
        this.image = path;

        rescues.add(this);
    }

    public Rescue() {

    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String s) {
        this.image = s;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getOrganization() {
        return this.organization;
    }

    public void setOrganization(String name) {
        this.organization = name;
    }


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return (this.zip);
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public StorageReference getImageStorageReference() {
        return imageStorageReference;
    }

    public void setImageStorageReference(StorageReference imageStorageReference) {
        this.imageStorageReference = imageStorageReference;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRescueID() {
        return rescueID;
    }

    public void setRescueID(String rescueID) {
        this.rescueID = rescueID;
    }
}
