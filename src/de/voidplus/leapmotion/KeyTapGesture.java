package de.voidplus.leapmotion;


import processing.core.PApplet;
import processing.core.PVector;


public class KeyTapGesture extends Gesture {

	
	private com.leapmotion.leap.KeyTapGesture keyTap;
	
	
	public KeyTapGesture(PApplet parent, LeapMotion leap, com.leapmotion.leap.Gesture gesture){
		super(parent, leap, gesture);
		this.keyTap = new com.leapmotion.leap.KeyTapGesture(gesture);
	}

	
	/**
	 * The position where the key tap is registered.
	 * @return
	 */
	public PVector getPosition() {
		return this.leap.map(this.keyTap.position());
	}
	
	/**
	 * Raw data of the position where the key tap is registered.
	 * @return
	 */
	public PVector getRawPosition() {
		return this.leap.convert(this.keyTap.position());
	}
	
	/**
	 * The direction of finger tip motion.
	 * @return
	 */
	public PVector getDirection() {
		return this.leap.map(this.keyTap.direction());
	}

	/**
	 * Raw data of the position where the key tap is registered.
	 * @return
	 */
	public PVector getRawDirection() {
		return this.leap.convert(this.keyTap.direction());
	}
	
	/**
	 * The finger performing the key tap gesture.
	 * @return
	 */
	public Finger getFinger() {
		return new Finger(this.parent, this.leap, this.keyTap.pointable());
	}
	
}