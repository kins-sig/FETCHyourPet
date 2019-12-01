package com.sigm.fetchyourpet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.lang.*;


/**
 * This algorithm class controls an arraylist of dog objects and compares the users preferences to the given dog traits and outputs a list of dogs in 
 * order of how similar they are to the user's desired preferences.
 * 
 * current_user: current user that is searching for dogs
 * all_dogs: a list of dogs in the "database" that the user will search through
 * @author Garrett Neilson
 *
 */
public class Algo {

	public PotentialAdopter current_user;
	public ArrayList<Dog> all_dogs = new ArrayList();
	
	/**
	 * Accessed directly only to run the algorithm for the first time, all subsequent calls of this function are through the "refresh" function
	 * which updates the list based on what the user liked / disliked
	 * @return a list of the dog IDs in order 
	 */
	public String[] run_recommender_system() {
		double [] similarity_scores = new double[all_dogs.size()];
		String [] dog_IDs = new String [all_dogs.size()];
		Dog [] dogs = new Dog [all_dogs.size()];

		double score = 0;
		Matrix curr_dog_traits = new Matrix(0);
		Dog curr_dog = new Dog();
		
		for(int i = 0; i < all_dogs.size(); i++) {
//			curr_dog_traits = all_dogs.get(i).get_dog_traits();
			curr_dog = all_dogs.get(i);
			curr_dog_traits = curr_dog.get_dog_traits();
			score = current_user.get_user_traits().normalize().cos_sim(curr_dog_traits.normalize());
			score = score*100;
			score = Math.round(score);
			score = score /100;
//			System.out.println(curr_dog_traits);
//			System.out.println(current_user.user_traits);
//			System.out.println(score);
			dog_IDs[i] = curr_dog.getId();
			similarity_scores[i] = score;
		}
		
		similarity [] zip = new similarity[Math.min(dog_IDs.length,similarity_scores.length)];
		
		for(int i = 0; i < zip.length; i++)
		{
		    zip[i] = new similarity(dog_IDs[i], similarity_scores[i]);
		}

		Arrays.sort(zip, new Comparator<similarity>() {

		    @Override
		    public int compare(similarity o1, similarity o2) {
		        return Double.compare(o1.score, o2.score);
		    }
		});
		
		String [] sorted_dog_IDs = new String[all_dogs.size()];
		for(int i = 0; i < all_dogs.size(); i++) {
			sorted_dog_IDs[i] = zip[i].dogID;
		}
		
		// print algo results
//		System.out.print("[");
//		for(int i = 0; i < all_dogs.size(); i++) {
//			System.out.print("(" + zip[i].dogID + ", " + zip[i].score + ")" + ", ");
//			
//		}
//		System.out.print("]");
//		System.out.println("");


		//Sorting the dogs from highest to lowest rather than lowest to highest to make life easier
//		int i = all_dogs.size()-1;
//		int j = 0;
//		for(Dog d: all_dogs){
//			if(d.getId().equals(sorted_dog_IDs[j])){
//				dogs[i] = d;
//				i-=1;
//				j+=1;
//			}
//
//
//		}


		
		return sorted_dog_IDs;
	}
	
	/**
	 * Updates the user vector based on their preferences 
	 * @return	an array of all of the dog ID's in order based on the updated user vector
	 */
	public String[] refresh() {
		//current_user.update_traits_vector();
		return run_recommender_system();
	}
	
	/**
	 * Updates the current user information based on the dog that was liked
	 * @param curr_dog: the dog that the user liked
	 */
	public void user_likes(Dog curr_dog) {		
		//this.current_user.user_likes(curr_dog.get_dog_traits());
		//this.current_user.num_dogs_liked++;
	}
	
	/**
	 * Updates the current user information based on the dog that was dislikes
	 * @param curr_dog: the dog that the user disliked
	 */
	public void user_dislikes(Dog curr_dog) {
		//this.current_user.user_dislikes(curr_dog.get_dog_traits());
		//this.current_user.num_dogs_disliked++;

	}
	
	/**
	 * Updates the current user information based on the dog that was favorited
	 * @param curr_dog: the dog that the user favorited
	 */
	public void user_favorites(Dog curr_dog) {
		//this.current_user.favorite_dogs.add(curr_dog);
	}
	
	/**
	 * Change the user settings to a new one
	 * @param new_user: a given new user
	 */
	public void change_user(PotentialAdopter new_user) {
		System.out.println("User " + this.current_user.getAdopterID()+ " has changed to " + new_user.getAdopterID());
		this.current_user = new_user;
	}
	
	/**
	 * Print a list of all dog ID's in the system
	 */
	public void print_all_dogs_in_system() {
		System.out.print("Dog IDs in the system: ");
		for(int i = 0; i < all_dogs.size(); i++) {
			Dog d = this.all_dogs.get(i);
			System.out.print(d.getId() + " ");
		}
	}
}
