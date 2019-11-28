package com.sigm.fetchyourpet;
import android.content.Context;
import android.graphics.Bitmap;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dog {
    public static List<Dog> dogList = new ArrayList<>();

    String additionalInfo,name, vaccinationStatus,healthConcerns, breed,size,age,rescueID,sex,image,traits, imageURL, id;
    StorageReference imageStorageReference;
    int zip;
    Bitmap bitmapImage;
    public Rescue r;


    public static Dog currentDog;

    public static Context c;

    private List<Integer> traitsInteger;
    static List<String> traitsTextValue = Arrays.asList(null, null, null,"Size: Large","Size: Medium","Size: Small",null, null, null,"Very well trained","Knows basic commands","Needs training"
            ,null, null, null, "Not the best with kids", "Can tolerate kids", "Loves kids!",null, null, null,"Can be left alone", "Is okay being left alone", "Should probably avoid being left alone"
            ,null, null, null,null, null, null );



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
    public String getTraits(){
        return this.traits;
    }
    public void setTraits(String s){
        this.traits = s;
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

    public String getRescueID(){
        return this.rescueID;
    }
    public void setRescueID(String s){
        this.rescueID = s;
    }
}
