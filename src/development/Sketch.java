package development;


import de.voidplus.leapmotion.*;
import processing.core.*;


public class Sketch extends PApplet {

	LeapMotion leap;

	public void setup() {
		size(500, 500, P3D);
		background(250);
		
		leap = new LeapMotion(this).withGestures();
	}

	public void draw() {
		background(250);
		noStroke(); fill(50);
		
		// Hands
		for(Hand hand : leap.getHands()){
			hand.draw();
			
			int     hand_id          = hand.getId();
			PVector hand_position    = hand.getPosition();
			PVector hand_direction   = hand.getDirection();
			
			PVector hand_dynamics    = hand.getDynamics();
			float   hand_roll        = hand.getRoll();
			float   hand_pitch       = hand.getPitch();
			float   hand_yaw         = hand.getYaw();
			
			PVector sphere_position  = hand.getSpherePosition();
			float   sphere_radius    = hand.getSphereRadius();
			
			// Fingers
			for(Finger finger : hand.getFingers()){
				finger.draw();
				int     finger_id         = finger.getId();
				PVector finger_position   = finger.getPosition();
				PVector finger_velocity   = finger.getVelocity();
				PVector finger_direction  = finger.getDirection();	
			}
			
			// Tools
			for(Tool tool : hand.getTools()){
				tool.draw();
				int     tool_id           = tool.getId();
				PVector tool_position     = tool.getPosition();
				PVector tool_velocity     = tool.getVelocity();
				PVector tool_direction    = tool.getDirection();
			}
			
		}
		
		// Fingers
		for(Finger finger : leap.getFingers()){
			finger.draw();
			int     finger_id         = finger.getId();
			PVector finger_position   = finger.getPosition();
			PVector finger_velocity   = finger.getVelocity();
			PVector finger_direction  = finger.getDirection();	
		}
		
	}

	/*
	 * Leap Motion Runtime Callbacks
	 */
	public void leapOnInit(){
		// println("Leap Motion Init");
	}
	public void leapOnConnect(){
		// println("Leap Motion Connect");
	}
	public void leapOnFrame(){
		// println("Leap Motion Frame");
	}
	public void leapOnDisconnect(){
		// println("Leap Motion Disconnect");
	}
	public void leapOnExit(){
		// println("Leap Motion Exit");
	}
	
	/*
	 * Leap Motion Gesture-Recognition Callbacks
	 */
	public void leapOnSwipeGesture(SwipeGesture g, int state){
		
		// Properties
		int 	id 					= g.getId();
		Finger	finger 				= g.getFinger();
		PVector	position 			= g.getPosition();
		PVector position_start 		= g.getStartPosition();
		PVector direction 			= g.getDirection();
		float 	speed 				= g.getSpeed();
		long 	duration 			= g.getDuration();
		float 	duration_seconds 	= g.getDurationInSeconds();
		
		// States
		switch(state){
			case 1:	// Start
				break;
			case 2: // Update
				break;
			case 3: // Stop
				println("SwipeGesture: "+id);
				break;
		}
	}
	public void leapOnCircleGesture(CircleGesture g, int state){
		
		// Properties
		int 	id 					= g.getId();
		Finger	finger 				= g.getFinger();
		PVector	position_center 	= g.getCenter();
		float	radius 				= g.getRadius();
		float 	progress 			= g.getProgress();
		long 	duration 			= g.getDuration();
		float 	duration_seconds 	= g.getDurationInSeconds();
		
		// States
		switch(state){
			case 1:	// Start
				break;
			case 2: // Update
				break;
			case 3: // Stop
				println("CircleGesture: "+id);
				break;
		}
	}
	public void leapOnScreenTapGesture(ScreenTapGesture g){
		
		// Properties
		int 	id 					= g.getId();
		Finger	finger 				= g.getFinger();
		PVector	position			= g.getPosition();
		PVector direction			= g.getDirection();
		long 	duration 			= g.getDuration();
		float 	duration_seconds 	= g.getDurationInSeconds();

		println("ScreenTapGesture: "+id);
	}
	public void leapOnKeyTapGesture(KeyTapGesture g){
		
		// Properties
		int 	id 					= g.getId();
		Finger	finger 				= g.getFinger();
		PVector	position			= g.getPosition();
		PVector direction			= g.getDirection();
		long 	duration 			= g.getDuration();
		float 	duration_seconds 	= g.getDurationInSeconds();
		
		println("KeyTapGesture: "+id);
	}

	
	public static void main(String args[]) {
		PApplet.main(new String[] { "development.Sketch" });

		// Or run the sketch in present-mode:
		// PApplet.main(new String[] { "--present", "development.Sketch" });
	}
	

}
