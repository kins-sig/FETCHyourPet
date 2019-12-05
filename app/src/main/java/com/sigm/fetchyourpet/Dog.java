package com.sigm.fetchyourpet;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * models a Dog object
 * @author Dylan
 * Need setters for every field that is stored in the database in order to convert the database
 * document into an object.
 * @author Dylan
 */
public class Dog {
    public static ArrayList<Dog> dogList = new ArrayList<>();
    public static Dog currentDog;
    static List<String> traitsTextValue = Arrays.asList(null, null, null, "Size: Large", "Size: Medium", "Size: Small", null, null, null, "Knows basic commands", "Needs training", "Very well trained"
            , null, null, null, "Not the best with kids", "Can tolerate kids", "Loves kids!", null, null, null, "Can be left alone", "Is okay being left alone", "Should probably avoid being left alone"
            , null, null, null, null, null, null);
    public Boolean favorited = false;
    public Boolean disliked = false;
    String additionalInfo, name, vaccinationStatus, healthConcerns, breed, size, age, rescueID, sex, image, traits, imageURL, id;
    StorageReference imageStorageReference;
    Double similarityScore;
    int zip;

    Bitmap bitmapImage = null;
    private Rescue r;
    private List<Integer> traitsInteger;


    public Dog() {


    }

    /**
     *
     * @param name - name of dog
     * @param photo - bitmap of the uploaded photo
     * @param breed - the breed
     * @param vaccinationStatus - status of vaccination
     * @param healthConcerns - any health concerns
     * @param sex - the sex (gender)
     * @param age - the age
     * @param additionalInfo - any additional info
     * @param path - path to the stored photo in the database
     */
    public Dog(String name, Bitmap photo, String breed, String vaccinationStatus, String healthConcerns, String sex, String age, String additionalInfo, String path) {
        this.name = name;
        this.bitmapImage = photo;
        this.breed = breed;
        this.vaccinationStatus = vaccinationStatus;
        this.healthConcerns = healthConcerns;
        this.sex = sex;
        this.age = age;
        this.additionalInfo = additionalInfo;
        this.image = path;


    }

//    public Dog(String name, Bitmap photo, String breed, String vaccinationStatus, String healthConcerns, String sex, String age, String additionalInfo) {
//        this.name = name;
//        this.bitmapImage = photo;
//        this.breed = breed;
//        this.vaccinationStatus = vaccinationStatus;
//        this.healthConcerns = healthConcerns;
//        this.sex = sex;
//        this.age = age;
//        this.additionalInfo = additionalInfo;
//
//
//    }

    /**
     * Resets the dog list. This is necessary when the user logs out, so the next user
     * is not stuck with they favorites and dislikes.
     */
    public static void resetDogList() {
        for (Dog d : dogList) {
            d.setFavorited(false);
            d.setDisliked(false);

        }

    }

    public void setVaccinationStatus(String vaccinationStatus) {
        this.vaccinationStatus = vaccinationStatus;
    }

    public String getTraits() {
        return this.traits;
    }

    public void setTraits(String s) {
        this.traits = s;
    }

    public Bitmap getBitmapImage() {
        return this.bitmapImage;
    }

    public void setBitmapImage(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String s) {
        this.image = s;

    }

    public String getId() {
        return this.id;
    }

    public void setId(String s) {
        this.id = s;

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocation() {
        return this.zip;
    }

    public String getBreed() {
        return this.breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getVaccStatus() {
        return this.vaccinationStatus;

    }

    public String getHealthConcerns() {
        return this.healthConcerns;
    }

    public void setHealthConcerns(String healthConcerns) {
        this.healthConcerns = healthConcerns;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    /**
     * Searches through the traits string and, depending on the value at the indexes
     * 3, 4, and 5, returns the corresponding value
     * @return size string
     */
    public String getSize() {
        String s = "1";
        if (traits.charAt(3) == s.charAt(0)) {
            return "Size: Large";

        } else if (traits.charAt(4) == s.charAt(0)) {
            return "Size: Medium";
        } else if (traits.charAt(5) == s.charAt(0)) {
            return "Size: Small";
        }


        return this.size;

    }

    public String getAdditionalInfo() {
        return this.additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {

        this.additionalInfo = additionalInfo;
    }

    /**
     * Just incase someone took a shortcut manually inputting data in the database, ensure
     * the full sex is returned
     * @return the sex
     */
    public String getSex() {

        if (this.sex.toUpperCase().equals("M")) {
            return "Male";
        } else if (this.sex.toUpperCase().equals("F")) {
            return "Female";
        }
        return this.sex;
    }

    public void setSex(String sex) {


        this.sex = sex;
    }

    public String getRescueID() {
        return this.rescueID;
    }

    public void setRescueID(String s) {
        this.rescueID = s;
    }

    /**
     * For simplicity, all dogs that are listed as "<1" return the age of 0, in order to be
     * able to properly sort all dogs.
     * @return the integer version of the age
     */
    public int getIntAge() {
        int ageNum;
        if (this.age.contains("<")) {
            ageNum = 0;
        } else {
            ageNum = Integer.parseInt(age);
        }
        return ageNum;


    }

    /**
     * Analyze the traits string at indexes 3, 4, and 5 and return the corresponding values
     * @return an integer version of size
     */
    public int getIntSize() {
        int intSize;
        if (Character.toString(traits.charAt(3)).equals("1")) {
            intSize = 3;

        } else if (Character.toString(traits.charAt(4)).equals("1")) {
            intSize = 2;

        } else {
            intSize = 1;

        }


        return intSize;
    }

    /**
     * Converts the traits string to a matrix
     * @return a matrix of the traits(required by the Algo class)
     */
    public Matrix get_dog_traits() {

        double[] data = new double[30];
        char[] chars = traits.trim().toCharArray();
        int i = 0;
        for (char c : chars) {
            data[i] = Character.getNumericValue(c);
            i++;

        }
        return new Matrix(data);


    }

    public void setFavorited(boolean b) {
        this.favorited = b;
    }

    public void setDisliked(boolean b) {
        this.disliked = b;
    }

    public Double getSimilarityScore() {
        return this.similarityScore;
    }

    public void setSimilarityScore(Double s) {
        this.similarityScore = s;
    }
}
