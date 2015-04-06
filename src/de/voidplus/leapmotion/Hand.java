package de.voidplus.leapmotion;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Hand implements PConstants {	

	private PApplet parent;
	private com.leapmotion.leap.Hand hand;
	private LeapMotion leap;
	private ArrayList<Finger> fingers;
	private ArrayList<Finger> outstretchedFingers;
	private ArrayList<Finger> outstretchedFingersByAngel;
	private ArrayList<Tool> tools;
	
	public Hand(PApplet parent, LeapMotion leap, com.leapmotion.leap.Hand hand){
		this.parent = parent;
		this.leap = leap;
		this.hand = hand;
		this.fingers = new ArrayList<Finger>();
		this.outstretchedFingers = new ArrayList<Finger>();
		this.outstretchedFingersByAngel = new ArrayList<Finger>();
		
		this.tools = new ArrayList<Tool>();
	}

	/**
	 * Reports whether this is a valid Hand object. 
	 * @return
	 */
	public boolean isValid(){
		return this.hand.isValid();
	}
	protected static boolean isValid(com.leapmotion.leap.Hand hand){
		return hand.isValid();
	}
	
	/* ------------------------------------------------------------------------ */
	/* HAND */
	
	/**
	 * Get the unique ID.
	 * @return
	 */
	public int getId(){
		return this.hand.id();
	}
	
	/**
	 * The center position of the palm in millimeters.
	 * @return
	 */
	public PVector getPosition(){
		return this.leap.map(this.hand.palmPosition());
	}
	
	/**
	 * Raw data of the center position.
	 * @return
	 */
	public PVector getRawPosition(){
		return this.leap.convert(this.hand.palmPosition());
	}
	
	/**
	 * The center position of the palm in millimeters.
	 * @return
	 */
	public PVector getPalmPosition(){
		return this.getPosition();
	}
	
	/**
	 * The stabilized center position of the palm in millimeters.
	 * @return
	 */
	public PVector getStabilizedPosition(){
		return this.leap.map(this.hand.stabilizedPalmPosition());
	}
	
	/**
	 * The stabilized center position of the palm in millimeters.
	 * @return
	 */
	public PVector getStabilizedPalmPosition(){
		return this.getStabilizedPosition();
	}
	
	/**
	 * Raw data of the stabilized center position.
	 * @return
	 */
	public PVector getRawStabilizedPosition(){
		return this.leap.convert(this.hand.stabilizedPalmPosition());
	}
	
	/**
	 * Raw data of the stabilized center position.
	 * @return
	 */
	public PVector getRawStabilizedParlmPosition(){
		return this.getRawStabilizedPosition();
	}
	
	/**
	 * The direction from the palm position toward the fingers.
	 * @return
	 */
	public PVector getDirection(){
		return this.leap.map(this.hand.direction());
	}
	
	/**
	 * Raw data of the direction from the palm position toward the fingers.
	 * @return
	 */
	public PVector getRawDirection(){
		return this.leap.convert(this.hand.direction());
	}
	
	/**
	 * How confident we are with a given hand pose. The confidence level ranges between 0.0 and 1.0 inclusive.
	 * @return 
	 */
	public float getConfidence(){
		return this.hand.confidence();
	}
	
	/**
	 * Identifies whether this Hand is a left hand.
	 * @return True if the hand is identified as a left hand.
	 */
	public boolean isLeft(){
		return this.hand.isLeft();
	}

	/**
	 * Identifies whether this Hand is a right hand.
	 * @return True if the hand is identified as a right hand.
	 */
	public boolean isRight(){
		return this.hand.isRight();
	}
	
	/**
	 * The strength of a grab hand pose. The strength is zero for an open hand, and blends to 1.0 when a grabbing hand pose is recognized.
	 * @return A float value in the [0..1] range representing the holding strength of the pose.
	 */
	public float getGrabStrength(){
		return this.hand.grabStrength();
	}
	
	/**
	 * The holding strength of a pinch hand pose. The strength is zero for an open hand, and blends to 1.0 when a pinching hand pose is recognized. Pinching can be done between the thumb and any other finger of the same hand.
	 * @return A float value in the [0..1] range representing the holding strength of the pinch pose.
	 */
	public float getPinchStrength(){
		return this.hand.pinchStrength();
	}	
	
	/**
	 * The duration of time this Hand has been visible to the Leap Motion Controller.
	 * @return
	 */
	public float getTimeVisible(){
		return this.hand.timeVisible();
	}
	
	/**
	 * The member of the list that is farthest to the front within the standard Leap Motion frame of reference (i.e has the smallest Z coordinate). 
	 * @return
	 */
	public Finger getFrontFinger(){
		return new Finger(this.parent, this.leap, this.hand.fingers().frontmost());
	}
	
	/**
	 * The member of the list that is farthest to the left within the standard Leap Motion frame of reference (i.e has the smallest X coordinate). 
	 * @return
	 */
	public Finger getLeftFinger(){		
		return new Finger(this.parent, this.leap, this.hand.fingers().leftmost());
	}
	
	/**
	 * The member of the list that is farthest to the right within the standard Leap Motion frame of reference (i.e has the largest X coordinate). 
	 * @return
	 */
	public Finger getRightFinger(){		
		return new Finger(this.parent, this.leap, this.hand.fingers().rightmost());
	}
	
	/**
	 * The member of the list that is farthest to the front within the standard Leap Motion frame of reference (i.e has the smallest Z coordinate). 
	 * @return
	 */
	public Tool getFrontTool(){		
		return new Tool(this.parent, this.leap, this.hand.tools().frontmost());
	}
	
	/**
	 * The member of the list that is farthest to the left within the standard Leap Motion frame of reference (i.e has the smallest X coordinate). 
	 * @return
	 */
	public Tool getLeftTool(){		
		return new Tool(this.parent, this.leap, this.hand.tools().leftmost());
	}
	
	/**
	 * The member of the list that is farthest to the right within the standard Leap Motion frame of reference (i.e has the largest X coordinate). 
	 * @return
	 */
	public Tool getRightTool(){		
		return new Tool(this.parent, this.leap, this.hand.tools().rightmost());
	}
	
	
	/* ------------------------------------------------------------------------ */
	/* FLIGHT-DYNAMICS */
	
	/**
	 * Get the angles of the hand (x=roll, y=pich, z=yaw).
	 * @return
	 */
	public PVector getDynamics(){
		return new PVector(this.getRoll(), this.getPitch(), this.getYaw());
	}
	
	/**
	 * Get the roll angle (x).
	 * @return
	 */
	public float getRoll(){
		return PApplet.degrees(this.hand.direction().pitch());
	}
	
	/**
	 * Get the pitch angle (y).
	 * @return
	 */
	public float getPitch(){
		return -PApplet.degrees(this.hand.palmNormal().roll());
	}
	
	/**
	 * Get the yaw angle (z).
	 * @return
	 */
	public float getYaw(){
		return PApplet.degrees(this.hand.direction().yaw());
	}
	
	
	/* ------------------------------------------------------------------------ */
	/* SPHERE */
	
	/**
	 * The center of a sphere fit to the curvature of this hand. 
	 * @return
	 */
	public PVector getSpherePosition(){
		return this.leap.map(this.hand.sphereCenter());
	}

	/**
	 * Raw data of the center of a sphere fit to the curvature of this hand. 
	 * @return
	 */
	public PVector getRawSpherePosition(){
		return this.leap.convert(this.hand.sphereCenter());
	}
	
	/**
	 * The radius of a sphere fit to the curvature of this hand. 
	 * @return
	 */
	public float getSphereRadius(){
		return this.hand.sphereRadius();
	}
	

	/* ------------------------------------------------------------------------ */
	/* FINGERS */
	
	/**
	 * Get all detected fingers of the hand.
	 * @return
	 */
	public ArrayList<Finger> getFingers(){
		fingers.clear();
		if(this.hasFingers()){
			for(com.leapmotion.leap.Finger finger : this.hand.fingers()){
				this.fingers.add(new Finger(this.parent, this.leap, finger));
		    }
		}
		return fingers;
	}
	
	/**
	 * Get all outstrechted fingers.
	 * @return
	 */
	public ArrayList<Finger> getOutstrechtedFingers() {
		this.outstretchedFingers.clear();
		if(!this.hand.fingers().extended().isEmpty()){
			for(com.leapmotion.leap.Finger finger : this.hand.fingers().extended()){
				this.outstretchedFingers.add(new Finger(this.parent, this.leap, finger));
			}
		}
		return this.outstretchedFingers;
	}
	
	/**
	 * Get all outstrechted fingers.
	 * @return
	 */
	public ArrayList<Finger> getOutstrechtedFingers(int ignoreForBackwardsCompatibility) {
		return this.getOutstrechtedFingers();
	}
	
	/**
	 * Get all outstrechted fingers by angle.
	 * @param similarity Minimum value of similarity.
	 * @return
	 */
	public ArrayList<Finger> getOutstrechtedFingersByAngel(int similarity) {
		this.outstretchedFingersByAngel.clear();
		if (this.hasFingers()) {
			for (com.leapmotion.leap.Finger finger : this.hand.fingers()) {
				if (Finger.isValid(finger)) {
					Finger candidate = new Finger(this.parent, this.leap, finger);
					// calculate total distance
					float distance = 0.0f;
					for (int b = 0; b < 4; b++) {
						distance += PVector.dist(
							candidate.getBone(b).getNextJoint(),
							candidate.getBone(b).getPrevJoint()
						);
					}
					// calculate shortest distance
					float direct = PVector.dist(
						candidate.getBone(0).getNextJoint(),
							candidate.getBone(((candidate.getType() != 0) ? 3 : 2)).getPrevJoint()
					);
					// calculate ratio
					if ((direct / distance * 100) >= similarity) {
						outstretchedFingersByAngel.add(candidate);
					}
				}
		    }
		}
		return this.outstretchedFingersByAngel;
	}
	
	/**
	 * Get all outstrechted fingers with 75% likelihood by angel.
	 * @return
	 */
	public ArrayList<Finger> getOutstrechtedFingersByAngel() {
		return this.getOutstrechtedFingersByAngel(75);
	}
	
	/**
	 * Get all raw outstrechted fingers.
	 * @return
	 */
	public ArrayList<Finger> getRawOutstrechtedFingers() {
		this.outstretchedFingers.clear();
		
		return this.outstretchedFingers;
	}
	
	/**
	 * Check if there is any finger.
	 * @return
	 */
	public boolean hasFingers(){
		if(this.leap.isConnected()){
			return !this.hand.fingers().isEmpty();
		}
		return false;
	}
	
	/**
	 * Get the number of fingers.
	 * @return
	 */
	public int countFingers(){
		if(this.leap.isConnected()){
			return this.hand.fingers().count();
		}
		return 0;
	}
	
	/**
	 * Get a specific finger by id.
	 * @param type	0:TYPE_THUMB, 1:TYPE_INDEX, 2:TYPE_MIDDLE, 3:TYPE_RING, 4:TYPE_PINKY
	 * @return
	 */
	public Finger getFinger(int type){
		for(Finger finger : this.getFingers()){
			if(finger.getType()==type){
				return finger;
			}
		}
		return null;
	}

	/**
	 * Get a specific finger by name.
	 * @param name	"thumb", "index", "middle", "ring", "pinky"
	 * @return
	 */
	public Finger getFinger(String name){
		name = name.toLowerCase();
		if(name.equals("thumb")){
			return this.getFinger(0);
		} else if(name.equals("index")){
			return this.getFinger(1);
		} else if(name.equals("middle")){
			return this.getFinger(2);
		} else if(name.equals("ring")){
			return this.getFinger(3);
		} else if (name.equals("pinky")){
			return this.getFinger(4);
		}
		return null;
	}
	
	/**
	 * Get the thumb finger.
	 * @return
	 */
	public Finger getThumb(){
		return this.getFinger(0);
	}

	/**
	 * Get the index finger.
	 * @return
	 */
	public Finger getIndexFinger(){
		return this.getFinger(1);
	}
	
	/**
	 * Get the middle finger.
	 * @return
	 */
	public Finger getMiddleFinger(){
		return this.getFinger(2);
	}
	
	/**
	 * Get the ring finger.
	 * @return
	 */
	public Finger getRingFinger(){
		return this.getFinger(3);
	}
	
	/**
	 * Get the pinky/little finger.
	 * @return
	 */
	public Finger getPinkyFinger(){
		return this.getFinger(4);
	}

	
	/* ------------------------------------------------------------------------ */
	/* TOOLS */
	
	/**
	 * Get all detected tools of the hand.
	 * @return
	 */
	public ArrayList<Tool> getTools(){
		tools.clear();
		if(this.hasTools()){
			for(com.leapmotion.leap.Tool tool : this.hand.tools()){
				tools.add(new Tool(this.parent, this.leap, tool));
		    }
		}
		return tools;
	}
	
	/**
	 * Check if there is any tool.
	 * @return
	 */
	public boolean hasTools(){
		if(this.leap.isConnected()){
			return !this.hand.tools().isEmpty();
		}
		return false;
	}
	
	/**
	 * Get the number of tools.
	 * @return
	 */
	public int countTools(){
		if(this.leap.isConnected()){
			return this.hand.tools().count();
		}
		return 0;
	}
	
	
	/* ------------------------------------------------------------------------ */
	/* ARM */
	
	/**
	 * Get arm of the hand.
	 * @return
	 */
	public Arm getArm(){
		return new Arm(this.parent, this.leap, this.hand.arm());
	}
	
	/**
	 * Check if hand has valid arm.
	 * @return
	 */
	public boolean hasArm(){
		return this.hand.arm().isValid();
	}
	
	
	/* ------------------------------------------------------------------------ */
	/* DRAWING */
	
	/**
	 * Draw the hand with all details.
	 * @param radius	The radius of the ellipse (2D) or sphere (3D).
	 */
	public void draw(float radius, boolean pre){
		if(pre){
			this.parent.noStroke();
			this.parent.fill(0);	
		}
		
		PVector position = this.getPosition();
		if(this.parent.g.is2D()){
			this.parent.ellipseMode(PConstants.CENTER);
			this.parent.ellipse(position.x, position.y, radius, radius);
		} else {
			
			// position
			this.parent.pushMatrix();
				this.parent.translate(position.x, position.y, position.z);
				this.parent.sphereDetail(20);
				this.parent.sphere(radius);
			this.parent.popMatrix();
			
			// sphere
//			this.parent.stroke(0, 30);
//			
//			this.parent.noFill();
//			this.parent.pushMatrix();
//				this.parent.translate(position.x, position.y, position.z);
//				
//				float pitch = this.hand.direction().pitch();
//				PVector rotation = this.leap.map(new com.leapmotion.leap.Vector(pitch, 0, 0));
//				this.parent.rotateX(rotation.x);
//				this.parent.ellipse(0, 0, 100, 100);
//			this.parent.popMatrix();
			
		}
		// fingers
		if(this.hasFingers()){
			for(Finger finger : this.getFingers()){
				finger.draw(pre);
			}
		}
	}
	public void draw(int radius){
		this.draw(radius, true);
	}
	public void draw(boolean pre){
		this.draw(5, pre);
	}
	public void draw(){
		this.draw(5, true);
	}

	/**
	 * Draw all fingers of the hand.
	 * @param radius	The radius of the ellipse (2D) or sphere (3D).
	 */
	public void drawFingers(int radius, boolean pre){
		if(this.hasFingers()){
			for(Finger finger : this.fingers){
				finger.draw(radius, pre);
			}
		}
	}
	public void drawFingers(int radius){
		this.drawFingers(radius, true);
	}
	public void drawFingers(boolean pre){
		this.drawFingers(3, pre);
	}
	public void drawFingers(){
		this.drawFingers(3, true);
	}
	
	/**
	 * Draw the sphere of the hand.
	 * @param pre
	 */
	public void drawSphere(boolean pre){
		if(pre){
			this.parent.stroke(0, 10);
			this.parent.noFill();
		}
		
		PVector position = this.getSpherePosition();
		float radius = this.getSphereRadius();
		
		if(this.parent.g.is2D()){
			this.parent.ellipseMode(PConstants.CENTER);
			this.parent.ellipse(position.x, position.y, radius, radius);
		} else {
			this.parent.pushMatrix();
				this.parent.translate(position.x, position.y, position.z);
				this.parent.sphereDetail(12);
				this.parent.sphere(radius);
			this.parent.popMatrix();
		}
	}
	public void drawSphere(){
		this.drawSphere(true);
	}
	
}