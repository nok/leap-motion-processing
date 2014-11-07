package de.voidplus.leapmotion;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Tool extends Pointable {

	private com.leapmotion.leap.Tool tool;
		
	public Tool(PApplet parent, LeapMotion leap, com.leapmotion.leap.Tool tool){
		super(parent, leap, (com.leapmotion.leap.Pointable) tool);
		this.tool = tool;
	}
	public Tool(PApplet parent, LeapMotion leap, com.leapmotion.leap.Pointable pointable){
		super(parent, leap, pointable);
	}

	/**
	 * Reports whether this is a valid Tool object. 
	 * @return
	 */
	public boolean isValid(){
		return this.tool.isValid();
	}
	protected static boolean isValid(com.leapmotion.leap.Tool tool){
		return tool.isValid();
	}
	
	/**
	 * Get the position of tip.
	 * @return
	 */
	public PVector getTipPosition(){
		return this.leap.map(this.tool.tipPosition());
	}
	
	/**
	 * Raw data of tip position.
	 * @return
	 */
	public PVector getRawTipPosition(){
		return this.leap.convert(this.tool.tipPosition());
	}
	

	/* ------------------------------------------------------------------------ */
	/* DRAWING */
	
	/**
	 * Draw the finger with all details.
	 * @param radius	The radius of the ellipse (2D) or sphere (3D).
	 */
	public void draw(float radius){
		PVector start = this.getPosition();
		PVector end = this.getTipPosition();
		
		this.parent.noStroke();
		this.parent.fill(0);
		
		if(this.parent.g.is2D()){
			this.parent.line(start.x, start.y, end.x, end.y);
		} else {
			this.parent.line(start.x, start.y, start.z, end.x, end.y, end.z);
		}

		this.parent.stroke(radius);
		this.parent.noFill();
		
		if(this.parent.g.is2D()){
			this.parent.ellipseMode(PConstants.CENTER);
			this.parent.ellipse(start.x, start.y, radius, radius);
			this.parent.ellipse(end.x, end.y, radius, radius);
		} else {
			this.parent.sphereDetail(20);
			this.parent.pushMatrix();
				this.parent.translate(start.x, start.y, start.z);
				this.parent.sphere(radius);
			this.parent.popMatrix();
			this.parent.pushMatrix();
				this.parent.translate(end.x, end.y, end.z);
				this.parent.sphere(radius);
			this.parent.popMatrix();
		}
	}
	public void draw(){
		this.draw(5);
	}
	
}
