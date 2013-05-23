package de.voidplus.leapmotion;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;


public class Pointable {


	protected PApplet parent;
	protected com.leapmotion.leap.Pointable pointable;
	protected LeapMotion leap;	
	
	
	public Pointable(PApplet parent, LeapMotion leap, com.leapmotion.leap.Pointable pointable){
		this.parent = parent;
		this.leap = leap;
		this.pointable = pointable;
	}
	

	/**
	 * Get the unique ID.
	 * @return
	 */
	public int getId(){
		return this.pointable.id();
	}
	
	/**
	 * The tip position in millimeters. 
	 * @return
	 */
	public PVector getPosition(){
		return this.leap.map(this.pointable.tipPosition());
	}
	
	/**
	 * The rate of change of the tip position in millimeters/second.
	 * @return
	 */
	public PVector getVelocity(){
		return this.leap.map(this.pointable.tipVelocity());
	}

	/**
	 * The direction in which this finger is pointing.
	 * @return
	 */
	public PVector getDirection(){
		return new PVector(
				this.pointable.direction().getX(),
				-this.pointable.direction().getY(),
				this.pointable.direction().getZ()
		);
	}
		
	/**
	 * The estimated length of the finger or tool in millimeters.
	 * @return
	 */
	public float getLength(){
		return this.pointable.length();
	}

	/**
	 * Reports whether this is a valid Pointable object. 
	 * @return
	 */
	public boolean isValid(){
		return this.pointable.isValid();
	}
	
	/**
	 * Draw the finger with all details.
	 * @param radius	The radius of the ellipse (2D) or sphere (3D).
	 */
	public void draw(float radius){
		PVector position = this.getPosition();
		
		if(this.parent.g.is2D()){
			this.parent.ellipseMode(PConstants.CENTER);
			this.parent.ellipse(position.x, position.y, radius, radius);
		} else {
			this.parent.pushMatrix();
			this.parent.translate(position.x, position.y, position.z);
			this.parent.sphereDetail(20);
			this.parent.sphere(5);
			this.parent.popMatrix();
		}
	}
	public void draw(){
		this.draw(5);
	}	
	
}
