package com.sigm.fetchyourpet;

/**
 * Simple class used to store dogs with their respective scores.
 * @author Garrett Neilson
 */
class similarity {

    public String dogID;
    public double score;
    public Dog d;

    /**
     *
     * @param dogID - the dog's ID
     * @param score - the similarity score
     * @param d - the dog
     */
    public similarity(String dogID, double score, Dog d) {
        this.dogID = dogID;
        this.score = score;
        this.d = d;

        d.setSimilarityScore(score);
    }
}  
