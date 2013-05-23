package de.voidplus.leapmotion;


import processing.core.PApplet;
import processing.core.PVector;


public class SwipeGesture extends Gesture {

	
	private com.leapmotion.leap.SwipeGesture swipe;
	
	
	public SwipeGesture(PApplet parent, LeapMotion leap, com.leapmotion.leap.Gesture gesture){
		super(parent, leap, gesture);
		this.swipe = new com.leapmotion.leap.SwipeGesture(gesture);
	}
	
	
	/**
	 * The current position of the swipe.
	 * @return
	 */
	public PVector getPosition() {
		return this.leap.map(this.swipe.position());
	}

	/**
	 * The position where the swipe began. 
	 * @return
	 */
	public PVector getStartPosition() {
		return this.leap.map(this.swipe.startPosition());
	}
	
	/**
	 * The unit direction vector parallel to the swipe motion. 
	 * @return
	 */
	public PVector getDirection() {
		return this.leap.map(this.swipe.direction());
	}
	
	/**
	 * The swipe speed in mm/second. 
	 * @return
	 */
	public float getSpeed() {
		return this.swipe.speed();
	}
	
	/**
	 * The finger performing the swipe gesture.
	 * @return
	 */
	public Finger getFinger() {
		return new Finger(this.parent, this.leap, this.swipe.pointable());
	}
	
}