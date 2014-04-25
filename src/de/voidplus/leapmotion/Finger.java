package de.voidplus.leapmotion;

import com.leapmotion.leap.Finger.Joint;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;


public class Finger extends Pointable {

	
	private com.leapmotion.leap.Finger finger;
	private Joint Joint;
	
	
	public Finger(PApplet parent, LeapMotion leap, com.leapmotion.leap.Finger finger) {
		super(parent, leap, (com.leapmotion.leap.Pointable) finger);
		this.finger = finger;
	}
	public Finger(PApplet parent, LeapMotion leap, com.leapmotion.leap.Pointable pointable) {
		super(parent, leap, pointable);
	}
	

	/**
	 * The finger tip position in millimeters. 
	 * @return
	 */
	public PVector getPositionOfJointTip(){
		return this.leap.map(this.finger.jointPosition(com.leapmotion.leap.Finger.Joint.JOINT_TIP));
	}
	
	/**
	 * The mcp joint position in millimeters. 
	 * @return
	 */
	public PVector getPositionOfJointMcp(){
		return this.leap.map(this.finger.jointPosition(Joint.JOINT_MCP));
	}
	
	/**
	 * The pip joint position in millimeters. 
	 * @return
	 */
	public PVector getPositionOfJointPip(){
		return this.leap.map(this.finger.jointPosition(Joint.JOINT_PIP));
	}
	
	/**
	 * The dip joint position in millimeters. 
	 * @return
	 */
	public PVector getPositionOfJointDip(){
		return this.leap.map(this.finger.jointPosition(Joint.JOINT_DIP));
	}

	/**
	 * Raw data of the finger tip position. 
	 * @return
	 */
	public PVector getRawPositionOfJointTip(){
		return this.leap.convert(this.finger.jointPosition(Joint.JOINT_TIP));
	}
	
	/**
	 * Raw data of the mcp joint position. 
	 * @return
	 */
	public PVector getRawPositionOfJointMcp(){
		return this.leap.convert(this.finger.jointPosition(Joint.JOINT_MCP));
	}
	
	/**
	 * Raw data of the pip joint position. 
	 * @return
	 */
	public PVector getRawPositionOfJointPip(){
		return this.leap.convert(this.finger.jointPosition(Joint.JOINT_PIP));
	}
	
	/**
	 * Raw data of the dip joint position. 
	 * @return
	 */
	public PVector getRawPositionOfJointDip(){
		return this.leap.convert(this.finger.jointPosition(Joint.JOINT_DIP));
	}
	
	/**
	 * Get the index of fingertype (0-4, 0=thumb, 1=index, 2=middle, 3=ring, 4=pinky).  
	 * @return
	 */
	public int getType(){
		com.leapmotion.leap.Finger.Type type = this.finger.type();
		switch(type){
			case TYPE_THUMB:
				return 0;
			case TYPE_INDEX:
				return 1;
			case TYPE_MIDDLE:
				return 2;
			case TYPE_RING:
				return 3;
			case TYPE_PINKY:
				return 4;
		}
		return -1;
	}
	
	
	/* ------------------------------------------------------------------------ */
	/* DRAWING */
	
	/**
	 * Draw the lines between the joints.  
	 * @return
	 */
	public void drawLines(){
		this.parent.stroke(0);
		this.parent.noFill();
		
		PVector tip = this.getPositionOfJointTip();
		PVector mcp = this.getPositionOfJointMcp();
		PVector pip = this.getPositionOfJointPip();
		PVector dip = this.getPositionOfJointDip();
		
		this.parent.beginShape();
		if(this.parent.g.is2D()){
			this.parent.vertex(mcp.x, mcp.y);
			this.parent.vertex(pip.x, pip.y);
			this.parent.vertex(dip.x, dip.y);
			this.parent.vertex(tip.x, tip.y);		
		} else {
			this.parent.vertex(mcp.x, mcp.y, mcp.z);
			this.parent.vertex(pip.x, pip.y, pip.z);
			this.parent.vertex(dip.x, dip.y, dip.z);
			this.parent.vertex(tip.x, tip.y, tip.z);
		}
		this.parent.endShape(this.parent.OPEN);
	}

	/**
	 * Draw the joints of the finger.  
	 * @return
	 */
	public void drawJoints(float radius){
		this.parent.noStroke();
		this.parent.fill(0);
		
		PVector tip = this.getPositionOfJointTip();
		PVector mcp = this.getPositionOfJointMcp();
		PVector pip = this.getPositionOfJointPip();
		PVector dip = this.getPositionOfJointDip();
		
		if(this.parent.g.is2D()){
			this.parent.ellipseMode(PConstants.CENTER);
			this.parent.ellipse(tip.x, tip.y, radius, radius);
			this.parent.ellipse(mcp.x, mcp.y, radius, radius);
			this.parent.ellipse(pip.x, pip.y, radius, radius);
			this.parent.ellipse(dip.x, dip.y, radius, radius);
		} else {
			this.parent.sphereDetail(20);
			
			this.parent.pushMatrix();
				this.parent.translate(tip.x, tip.y, tip.z);
				this.parent.sphere(radius);
			this.parent.popMatrix();
			this.parent.pushMatrix();
				this.parent.translate(mcp.x, mcp.y, mcp.z);
				this.parent.sphere(radius);
			this.parent.popMatrix();
			this.parent.pushMatrix();
				this.parent.translate(pip.x, pip.y, pip.z);
				this.parent.sphere(radius);
			this.parent.popMatrix();
			this.parent.pushMatrix();
				this.parent.translate(dip.x, dip.y, dip.z);
				this.parent.sphere(radius);
			this.parent.popMatrix();
		}
	}
	public void drawJoints(){
		this.drawJoints(3);
	}
	
	/**
	 * Draw the joints and the lines of the finger.  
	 * @return
	 */
	public void draw(float radius){
		this.drawLines();
		this.drawJoints(radius);
	}
	public void draw(){
		this.draw(3);
	}	
	
}