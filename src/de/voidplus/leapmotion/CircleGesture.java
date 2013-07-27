package de.voidplus.leapmotion;


import processing.core.PApplet;
import processing.core.PVector;


public class CircleGesture extends Gesture {

	
	private com.leapmotion.leap.CircleGesture circle;
	
	
	public CircleGesture(PApplet parent, LeapMotion leap, com.leapmotion.leap.Gesture gesture){
		super(parent, leap, gesture);
		this.circle = new com.leapmotion.leap.CircleGesture(gesture);
	}

	
	/**
	 * The center point of the circle within the Leap frame of reference. 
	 * @return
	 */
	public PVector getCenter() {
		return this.leap.map(this.circle.center());
	}
	
	/**
	 * Raw data of the center point of the circle within the Leap frame of reference. 
	 * @return
	 */
	public PVector getRawCenter() {
		return this.leap.convert(this.circle.center());
	}
	
	/**
	 * Returns the normal vector for the circle being traced.
	 * @return
	 */
	public PVector getNormal() {
		return this.leap.map(this.circle.normal());
	}
	
	/**
	 * Raw data the normal vector for the circle being traced.
	 * @return
	 */
	public PVector getRawNormal() {
		return this.leap.convert(this.circle.normal());
	}
	
	/**
	 * The number of times the finger tip has traversed the circle.
	 * @return
	 */
	public float getProgress() {
		return this.circle.progress();
	}
	
	/**
	 * The radius of the circle.
	 * @return
	 */
	public float getRadius() {
		return this.circle.radius();
	}
	
	/**
	 * The finger performing the circle gesture. 
	 * @return
	 */
	public Finger getFinger() {
		return new Finger(this.parent, this.leap, this.circle.pointable());
	}
	
}