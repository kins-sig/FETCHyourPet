package com.sigm.fetchyourpet;
// INT Animal ID
// String Name
// Double weight
// Image(s)
// String Vaccination status
// String health conditions/concerns
// Double approx_age
// String breed (if known)
// Double [] [traits vector]
// INT num_times_clicked


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

public class Dog {
    static int counter = 0; //in the future, we can just have the database autoassign an id
    public static List<Dog> dogList = new ArrayList<>();
    String additionalInfo,name, vaccinationStatus,healthConcerns, breed,size = "Unknown",age,rescueID,sex;
    int id;
    Double weight;
    int[] images;
    int image;
    Bitmap bitmapImage;
    int zip;
    public Rescue r;

    Double[] traits;
    ArrayList<String> traitsText;

    int numTimesClicked = 0;
    public static Dog currentDog;

    public static Context c;


    //we will have have an integer array of dog traits and also a string array of dog traits.

    public Dog() {
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVaccinationStatus(String vaccinationStatus) {
        this.vaccinationStatus = vaccinationStatus;
    }

    public void setHealthConcerns(String healthConcerns) {
        this.healthConcerns = healthConcerns;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBitmapImage(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }

    public Dog(String name, Bitmap image, String breed, String vaccinationStatus, String healthConcerns, String sex, String age, String additionalInfo) {
        this.id = counter;
        counter++;
        this.name = name;
        this.bitmapImage = image;
        this.breed = breed;
        this.vaccinationStatus = vaccinationStatus;
        this.healthConcerns = healthConcerns;
        this.dogList.add(this);
        this.sex = sex;
        this.age = age;
        this.additionalInfo = additionalInfo;



    }

    public Dog(String name, Bitmap image, int age, String size, ArrayList<String> traits, int zip, String breed, String vaccinationStatus, String healthConcerns) {
        this.id = counter;
        counter++;
        this.name = name;
        this.age = Integer.toString(age);
        this.size = size;
        this.bitmapImage = image;
        this.traitsText = traits;
        this.zip = zip;
        this.breed = breed;
        this.vaccinationStatus = vaccinationStatus;
        this.healthConcerns = healthConcerns;


        //dogList.add(this);


    }

    public Bitmap getImage() {
        return this.bitmapImage;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<Dog> getDogList() {
        return dogList;
    }

    public String getTraits() {
        String s = "";
        if(traitsText != null) {
            for (String t : traitsText) {
                if (s != "") {
                    s = s + ", " + t;
                } else {
                    s += t;
                }
            }
            return s;
        }
        return "";
    }

    public int getLocation() {
        return this.zip;
    }

    public String getBreed() {
        return this.breed;
    }

    public String getVaccStatus() {
        return this.vaccinationStatus;

    }

    public String getHealthConcerns() {
        return this.healthConcerns;
    }

    public String getAge() {
        return age;
    }

    public String getSize() {
        return this.size;
    }
    public String getAdditionalInfo() {
        return this.additionalInfo;
    }
    public String getSex() {
        return this.sex;
    }
}
