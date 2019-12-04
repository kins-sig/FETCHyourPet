package com.sigm.fetchyourpet;

class similarity {
	
    public String dogID;
    public double score;
    public Dog d;
    
    public similarity(String dogID, double score, Dog d) {
        this.dogID = dogID;
        this.score = score;
        this.d = d;

        d.setSimilarityScore(score);
    }
}  
