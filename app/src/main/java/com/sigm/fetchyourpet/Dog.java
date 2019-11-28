package com.sigm.fetchyourpet;
import android.content.Context;
import android.graphics.Bitmap;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.List;

public class Dog {
    public static List<Dog> dogList = new ArrayList<>();

    String additionalInfo,name, vaccinationStatus,healthConcerns, breed,size = "Unknown",age,rescueID,sex,image,traits, imageURL, id;
    StorageReference imageStorageReference;
    int zip;
    Bitmap bitmapImage;
    public Rescue r;


    ArrayList<String> traitsText;
    public static Dog currentDog;

    public static Context c;



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

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
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
