package edu.uncc.mad.huduku.core;

public class GooglePlacesReview extends Review {
	
	private double foodRating;
	private double decorRating;
	private double serviceRating;
	
	public GooglePlacesReview(){
		super();
	}

	public double getFoodRating() {
		return foodRating;
	}

	public void setFoodRating(double foodRating) {
		this.foodRating = foodRating;
	}

	public double getDecorRating() {
		return decorRating;
	}

	public void setDecorRating(double decorRating) {
		this.decorRating = decorRating;
	}

	public double getServiceRating() {
		return serviceRating;
	}

	public void setServiceRating(double serviceRating) {
		this.serviceRating = serviceRating;
	}
}
