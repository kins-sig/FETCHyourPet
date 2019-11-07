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


public class Dog{
    static int counter = 0; //in the future, we can just have the database autoassign an id
    int id;
    String name;
    Double weight;
    int[] images;
    int image;
    String vaccinationStatus;
    String healthConcerns;
    Double approx_age; //have the rescue enter "-1" if unknown
    String breed;
    Double[] traits;
    int numTimesClicked = 0;

    public Dog(String name, int image){
        this.id = counter; counter++;
        this.name = name;
        this.image = image;


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





}
