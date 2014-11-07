package de.voidplus.leapmotion;

import processing.core.PApplet;
import processing.core.PVector;

public class Arm {

	protected PApplet parent;
	protected com.leapmotion.leap.Arm arm;
	protected LeapMotion leap;

	public Arm(PApplet parent, LeapMotion leap, com.leapmotion.leap.Arm arm){
		this.parent = parent;
		this.leap = leap;
		this.arm = arm;
	}
	
	/**
	 * Reports whether this is a valid Arm object. 
	 * @return
	 */
	public boolean isValid(){
		return this.arm.isValid();
	}
	protected static boolean isValid(com.leapmotion.leap.Arm arm){
		return arm.isValid();
	}
	
	/**
	 * The position of the elbow.
	 * @return
	 */
	public PVector getElbowPosition(){
		return this.leap.map(this.arm.elbowPosition());
	}
	
	/**
	 * The raw position of the elbow.
	 * @return
	 */
	public PVector getWristRawPosition(){
		return this.leap.convert(this.arm.wristPosition());
	}
	
	/**
	 * The position of the wrist.
	 * @return
	 */
	public PVector getWristPosition(){
		return this.leap.map(this.arm.wristPosition());
	}
	
	/**
	 * The raw position of the wrist.
	 * @return
	 */
	public PVector getElbowRawPosition(){
		return this.leap.convert(this.arm.elbowPosition());
	}
	
	/**
	 * The average width of the arm.
	 * @return
	 */
	public float getWidth(){
		return this.arm.width();
	}
	
	/**
	 * The center position.
	 * @return
	 */
	public PVector getPosition(){
		return this.leap.map(this.arm.center());
	}
	
	/**
	 * Raw data of the center position.
	 * @return
	 */
	public PVector getRawPosition(){
		return this.leap.convert(this.arm.center());
	}
	
}