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


import java.util.ArrayList;
import java.util.List;

public class Dog{
    static int counter = 0; //in the future, we can just have the database autoassign an id
    int id;
    String name;
    Double weight;
    int[] images;
    int image;
    int zip;
    String vaccinationStatus;
    String healthConcerns;
    Double approx_age; //have the rescue enter "-1" if unknown
    String breed;
    String size;
    int age;
    Double[] traits;
    ArrayList<String> traitsText;
    int numTimesClicked = 0;
    static List<Dog> dogList;
    public Dog(){

    }

    public Dog(String name, int image, int age, String size, ArrayList<String> traits, int zip, String breed, String vaccinationStatus, String healthConcerns ){
        this.id = counter; counter++;
        this.name = name;
        this.age = age;
        this.size = size;
        this.image = image;
        this.traitsText = traits;
        this.zip = zip;
        this.breed = breed;
        this.vaccinationStatus = vaccinationStatus;
        this.healthConcerns = healthConcerns;
        //dogList.add(this);


    }

    public int getImage(){
        return this.image;
    }

    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }

    public List<Dog> getDogList(){
        return dogList;
    }
    public String getTraits(){
        String s = "";
        for(String t: traitsText){
            if(s!=""){
                s = s + ", " + t;
            }
            else{
                s+=t;
            }
        }
        return s;
    }
    public int getLocation(){
        return this.zip;
    }
    public String getBreed(){
        return this.breed;
    }
    public String getVaccStatus(){
        return this.vaccinationStatus;

    }
    public String getHealthConcerns(){
        return this.healthConcerns;
    }
    public String getAgeString(){
        return Integer.toString(age);
    }
    public String getSize(){
        return this.size;
    }





}
