/**
 * Homework #3
 * @author Ankur Huralikoppi, Vishwas Subramanian
 * 
 * NOTE: Please enable sounds on the emulator while testing the application.
 * 
 */

package edu.uncc.mad.despicablemehunt.images;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.uncc.mad.despicablemehunt.R;

public class ImageHelper {
	
	private List<Integer> focusImages;
	private List<Integer> imagesGrid;
	private Random random;
	
	public ImageHelper(){

		focusImages = new ArrayList<Integer>();
		imagesGrid = new ArrayList<Integer>();
		
		random = new Random();
		
		focusImages.add(R.drawable.icon1);
		focusImages.add(R.drawable.icon2);
		focusImages.add(R.drawable.icon3);
		focusImages.add(R.drawable.icon4);
		
		for(int i = 0 ; i < 16 ;){
			imagesGrid.add(i++, R.drawable.icon1);
			imagesGrid.add(i++, R.drawable.icon2);
			imagesGrid.add(i++, R.drawable.icon3);
			imagesGrid.add(i++, R.drawable.icon4);
		}
		
		shuffleArrays();
		
	}
	
	public void shuffleArrays(){

		//Jumble the arrays. 
		Collections.shuffle(focusImages, random);
		Collections.shuffle(imagesGrid, random);
		
	}
	
	public int getFocusImageIdAtIteration(int iteration){
		return focusImages.get(iteration);
	}
	
	public int getGridImageId(int offset){
		return imagesGrid.get(offset);
	}
	
}