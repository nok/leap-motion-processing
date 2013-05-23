package de.voidplus.leapmotion;


import processing.core.PApplet;
import processing.core.PVector;


public class ScreenTapGesture extends Gesture {

	
	private com.leapmotion.leap.ScreenTapGesture screenTap;
	
	
	public ScreenTapGesture(PApplet parent, LeapMotion leap, com.leapmotion.leap.Gesture gesture){
		super(parent, leap, gesture);
		this.screenTap = new com.leapmotion.leap.ScreenTapGesture(gesture);
	}

	
	/**
	 * The position where the screen tap is registered. 
	 * @return
	 */
	public PVector getPosition() {
		return this.leap.map(this.screenTap.position());
	}
	
	/**
	 * The direction of finger tip motion.
	 * @return
	 */
	public PVector getDirection() {
		return this.leap.map(this.screenTap.direction());
	}
	
	/**
	 * The finger performing the screen tap gesture.
	 * @return
	 */
	public Finger getFinger() {
		return new Finger(this.parent, this.leap, this.screenTap.pointable());
	}
	
}