package de.voidplus.leapmotion;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;


public class Finger extends Pointable {

	
	private com.leapmotion.leap.Finger finger;
	
	
	public Finger(PApplet parent, LeapMotion leap, com.leapmotion.leap.Finger finger) {
		super(parent, leap, (com.leapmotion.leap.Pointable) finger);
		this.finger = finger;
	}
	public Finger(PApplet parent, LeapMotion leap, com.leapmotion.leap.Pointable pointable) {
		super(parent, leap, pointable);
	}

	
	/**
	 * Draw the direction.
	 */
	public void drawDirection(){
		PVector position = this.getPosition();
		PVector direction = this.getDirection();
		float length = this.getLength();
		
		this.parent.stroke(0);
		this.parent.line(
				position.x, 
				position.y, 
				position.z, 
				position.x+direction.x*length, 
				position.y+direction.y*length, 
				position.z+direction.z*length
		);
	}
	
}