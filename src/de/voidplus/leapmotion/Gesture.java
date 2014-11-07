package de.voidplus.leapmotion;

import java.util.ArrayList;

import processing.core.PApplet;

public abstract class Gesture {
	
	protected PApplet parent;
	protected LeapMotion leap;
	private com.leapmotion.leap.Gesture gesture;
	
	public Gesture(PApplet parent, LeapMotion leap, com.leapmotion.leap.Gesture gesture){
		this.parent = parent;
		this.leap = leap;
		this.gesture = gesture;
	}

	/**
	 * Reports whether this is a valid Gesture object. 
	 * @return
	 */
	public boolean isValid(){
		return this.gesture.isValid();
	}
	public static boolean isValid(com.leapmotion.leap.Gesture gesture){
		return gesture.isValid();
	}
	
	/**
	 * Get the unique ID.
	 * @return
	 */
	public int getId(){
		return this.gesture.id();
	}

	/**
	 * Get the type index of a gesture.
	 * @return 
	 */
	public int getType(){
		if(this.isValid()){
			switch (this.gesture.type()) {
			case TYPE_CIRCLE:
				return 0;
			case TYPE_KEY_TAP:
				return 1;
			case TYPE_SCREEN_TAP:
				return 2;
			case TYPE_SWIPE:
				return 3;
			default:
				return -1;
			}
		}
		return -1;
	}
	
	/* ------------------------------------------------------------------------ */
	/* DURATION */
	
	/**
	 * The elapsed duration of the recognized movement up to the frame containing this Gesture object, in microseconds.
	 * @return
	 */
	public long getDuration(){
		return this.gesture.duration();
	}
	
	/**
	 * The elapsed duration in seconds. 
	 * @return
	 */
	public float getDurationInSeconds(){
		return this.gesture.durationSeconds();
	}
	

	/* ------------------------------------------------------------------------ */
	/* HAND */
	
	/**
	 * Check if there is any hand.
	 * @return
	 */
	public boolean hasHands(){
		return !this.gesture.hands().isEmpty();
	}
	
	/**
	 * Get all detected hands.
	 * @return
	 */
	public ArrayList<Hand> getHands(){
		ArrayList<Hand> hands = new ArrayList<Hand>();
		if(this.hasHands()){
			for(com.leapmotion.leap.Hand hand : this.gesture.hands()){
				hands.add(new Hand(this.parent, this.leap, hand));
		    }
		}
		return hands;
	}
	
	/**
	 * Get the number of detected hands.
	 * @return
	 */
	public int countHands(){
		if(this.hasHands()){
			return this.gesture.hands().count();
		}
		return 0;
	}
	
}
