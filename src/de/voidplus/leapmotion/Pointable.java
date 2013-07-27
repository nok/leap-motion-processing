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
	 * Raw data of the tip position.
	 * @return
	 */
	public PVector getRawPosition(){
		return this.leap.convert(this.pointable.tipPosition());
	}
	
	/**
	 * The stabilized tip position in millimeters.
	 * @return
	 */
	public PVector getStabilizedPosition(){
		return this.leap.map(this.pointable.stabilizedTipPosition());
	}
	
	/**
	 * Raw data of the stabilized tip position.
	 * @return
	 */
	public PVector getRawStabilizedPosition(){
		return this.leap.convert(this.pointable.tipPosition());
	}
	
	/**
	 * The rate of change of the tip position in millimeters/second.
	 * @return
	 */
	public PVector getVelocity(){
		return this.leap.map(this.pointable.tipVelocity());
	}

	/**
	 * Raw data of the rate of change of the tip position.
	 * @return
	 */
	public PVector getRawVelocity(){
		return this.leap.convert(this.pointable.tipVelocity());
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
	 * A value proportional to the distance between this Pointable object and the adaptive touch plane.
	 * 
	 * The touch distance is a value in the range [-1, 1]. The value 1.0 indicates the Pointable is at the far edge of the hovering zone. The value 0 indicates the Pointable is just entering the touching zone. A value of -1.0 indicates the Pointable is firmly within the touching zone. Values in between are proportional to the distance from the plane. Thus, the touchDistance of 0.5 indicates that the Pointable is halfway into the hovering zone.
	 * 
	 * You can use the touchDistance value to modulate visual feedback given to the user as their fingers close in on a touch target, such as a button.
	 * 
	 * @return The normalized touch distance of this Pointable object.
	 */
	public float getTouchDistance(){
		return this.pointable.touchDistance();
	}
	
	/**
	 * The current touch zone of this Pointable object.
	 * 
	 * The Leap Motion software computes the touch zone based on a floating touch plane that adapts to the user's finger movement and hand posture. The Leap Motion software interprets purposeful movements toward this plane as potential touch points. When a Pointable moves close to the adaptive touch plane, it enters the "hovering" zone. When a Pointable reaches or passes through the plane, it enters the "touching" zone.
	 * 
	 * The possible states are present in the Zone enum of this class:
	 * 
	 * -1 = Zone.NONE Ð The Pointable is outside the hovering zone.
	 *  0 = Zone.HOVERING Ð The Pointable is close to, but not touching the touch plane.
	 *  1 = Zone.TOUCHING Ð The Pointable has penetrated the touch plane.
	 *  
	 *  The touchDistance value provides a normalized indication of the distance to the touch plane when the Pointable is in the hovering or touching zones.
	 * 
	 * @return The touch zone of this Pointable
	 */
	public int getTouchZone(){
		switch(this.pointable.touchZone()){
			case ZONE_NONE:
				return -1;
			case ZONE_HOVERING:
				return 0;
			case ZONE_TOUCHING:
				return 1;
		}
		return -1;
	}
	
	/**
	 * The estimated length of the finger or tool in millimeters.
	 * @return
	 */
	public float getLength(){
		return this.pointable.length();
	}

	/**
	 * The duration of time this Pointable has been visible to the Leap Motion Controller.
	 * @return
	 */
	public float getTimeVisible(){
		return this.pointable.timeVisible();
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
