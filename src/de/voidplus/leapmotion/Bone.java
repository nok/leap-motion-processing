package de.voidplus.leapmotion;

import processing.core.PApplet;
import processing.core.PVector;

public class Bone {

	protected PApplet parent;
	protected com.leapmotion.leap.Bone bone;
	protected LeapMotion leap;

	public Bone(PApplet parent, LeapMotion leap, com.leapmotion.leap.Bone bone){
		this.parent = parent;
		this.leap = leap;
		this.bone = bone;
	}
	
	/**
	 * Reports whether this is a valid Bone object. 
	 * @return
	 */
	public boolean isValid(){
		return this.bone.isValid();
	}
	protected static boolean isValid(com.leapmotion.leap.Bone bone){
		return bone.isValid();
	}
	
	/**
	 * Get the index of bone (0-3, 0=distal, 1=intermediate, 2=proximal, 3=metacarpal).  
	 * @return
	 */
	public int getType(){
		com.leapmotion.leap.Bone.Type type = this.bone.type();
		switch(type){
			case TYPE_DISTAL:
				return 0;
			case TYPE_INTERMEDIATE:
				return 1;
			case TYPE_PROXIMAL:
				return 2;
			case TYPE_METACARPAL:
				return 3;
		}
		return -1;
	}
	
	/**
	 * Get the length of this bone.
	 * @return
	 */
	public float getBoneLength(){
		return this.bone.length();
	}

	/**
	 * Get the width of this bone.
	 * @return
	 */
	public float getBoneWidth(){
		return this.bone.width();
	}
	
	/**
	 * Get the next joint position.
	 * @return
	 */
	public PVector getNextJoint(){
		return this.leap.map(this.bone.nextJoint());
	}

	/**
	 * Get the raw next joint position.
	 * @return
	 */
	public PVector getRawNextJoint(){
		return this.leap.convert(this.bone.nextJoint());
	}
	
	/**
	 * Get the previous joint position.
	 * @return
	 */
	public PVector getPrevJoint(){
		return this.leap.map(this.bone.prevJoint());
	}
	
	/**
	 * Get the raw previous joint position.
	 * @return
	 */
	public PVector getRawPrevJoint(){
		return this.leap.convert(this.bone.prevJoint());
	}
	
	/**
	 * The direction.
	 * @return
	 */
	public PVector getDirection(){
		return this.leap.map(this.bone.direction());
	}
	
	/**
	 * Raw data of the direction.
	 * @return
	 */
	public PVector getRawDirection(){
		return this.leap.convert(this.bone.direction());
	}
	
	/**
	 * Draw all bones.
	 * @param pre
	 */
	public void draw(boolean pre){
		if(pre){
			this.parent.stroke(0, 35);
			this.parent.noFill();
		}
		
		PVector next = this.getNextJoint();
		PVector prev = this.getPrevJoint();
		
		this.parent.beginShape(this.parent.LINES);
		if(this.parent.g.is2D()){
			this.parent.vertex(next.x, next.y);
			this.parent.vertex(prev.x, prev.y);
		} else {
			this.parent.vertex(next.x, next.y, next.z);
			this.parent.vertex(prev.x, prev.y, prev.z);
		}
		this.parent.endShape(this.parent.OPEN);
	}
	public void draw(){
		this.draw(true);
	}
	
}
