package de.voidplus.leapmotion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Vector;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Gesture;

import processing.core.PApplet;
import processing.core.PVector;


/**
 * Leap Motion Processing Library
 * @author Darius Morawiec
 * @version 1.1.3
 */
public class LeapMotion {
	
	public static final String VERSION = "1.1.3.1"; 
	public static final String SDK_VERSION = "1.0.9";
	
	private final PApplet parent;
	
	private PVector world;
	private boolean verbose;
	
	private boolean recognition;
	
	private Frame frame;
	private ArrayList<Hand> hands;
	private ArrayList<Finger> fingers;
	private ArrayList<Tool> tools;
	private ArrayList<Device> devices;
	
	private final Controller controller;
	private final Listener listener;
	
	
	/**
	 * LeapMotion constructor to initialize the controller and listener.
	 * @param parent 	Reference (this) of sketch.
	 * @param verbose	Print debug information to the console.
	 */
	public LeapMotion(PApplet parent, boolean verbose){
		this.parent = parent;
		
		System.out.println("# LeapMotion-Library v"+this.getVersion()+" - LeapMotion-SDK v"+this.getVersionSDK()+" - https://github.com/voidplus/leap-motion-processing");
		
		this.setWorld(200, 500, 200);
		this.setVerbose(verbose);
		this.recognition = false; 
		
		this.hands = new ArrayList<Hand>();
		this.fingers = new ArrayList<Finger>();
		this.tools = new ArrayList<Tool>();
		this.devices = new ArrayList<Device>();
		
		this.controller = new Controller();
		this.listener = new Listener(){
		    public void onInit(Controller controller) {
		    	dispatch("leapOnInit");
		    }
		    public void onConnect(Controller controller) {
		    	dispatch("leapOnConnect");
		    }
		    public void onDisconnect(Controller controller) {
		    	dispatch("leapOnDisconnect");
		    }
		    public void onExit(Controller controller) {
		    	dispatch("leapOnExit");
		    }
			public void onFrame(Controller controller){
				frame = controller.frame();
				dispatch("leapOnFrame");
			}
		};
		this.controller.addListener(this.listener);
		this.runInBackground(true);
		this.parent.registerDispose(this);
	}
	
	/**
	 * LeapMotion constructor to initialize the controller and listener.
	 * @param parent Reference (this) of sketch.
	 */
	public LeapMotion(PApplet parent){
		this(parent, false);
	}
	
	/**
	 * Run the tracking in background, too.
	 * @param silent
	 * @return
	 */
	public LeapMotion runInBackground(boolean active){
		if(active){
			this.controller.setPolicyFlags(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES);
		} else {
			this.controller.setPolicyFlags(Controller.PolicyFlag.POLICY_DEFAULT);
		}
		return this;
	}
	
	/**
	 * The instantaneous framerate.
	 * @return
	 */
	public int getFrameRate(){
		if(this.isConnected()){
			return (int) this.frame.currentFramesPerSecond();
		}
		return 0;
	}
	
	/* ------------------------------------------------------------------------ */
	/* World */
	
	/**
	 * Set the world coordinates.
	 * @param width 	World width dimension in millimeters.
	 * @param height 	World height dimension in millimeters.
	 * @param depth 	World depth dimension in millimeters.
	 * @return
	 */
	public LeapMotion setWorld(int width, int height, int depth){
		this.world = new PVector(width, height, depth);
		return this;
	}
	
	/**
	 * Set the world coordinates.
	 * @param world		World dimensions in millimeters.
	 * @return
	 */
	public LeapMotion setWorld(PVector world){
		this.world = world;
		return this;
	}
	

	/* ------------------------------------------------------------------------ */
	/* Device */
	
	/**
	 * Check if the Leap Motion is connected.
	 * @return
	 */
	public boolean isConnected() {
		return this.controller.isConnected();
	}
	public Controller getController() {
		return this.controller;
	}
	public ArrayList<Device> getDevices() {
		devices.clear();
		if(this.isConnected()){
			if(!this.getController().devices().isEmpty()){
				for(com.leapmotion.leap.Device device : this.getController().devices()){
					devices.add(new Device(this.parent, this, device));
			    }
			}
		}
		return this.devices;
	}
	
	
	/* ------------------------------------------------------------------------ */
	/* Hands */
	
	/**
	 * Check if there is any hand.
	 * @return
	 */
	public boolean hasHands(){
		if(this.isConnected()){
			return !this.frame.hands().isEmpty();
		}
		return false;
	}
	
	/**
	 * Get a specific hand.
	 * @param id
	 * @return
	 */
	public Hand getHand(Integer id){
		return new Hand(this.parent, this, this.frame.hand(id));
	}
	
	/**
	 * Get all detected hands.
	 * @return
	 */
	public ArrayList<Hand> getHands(){
		this.hands.clear();
		if(this.hasHands()){
			for(com.leapmotion.leap.Hand hand : this.frame.hands()){
				hands.add(new Hand(this.parent, this, hand));
		    }
		}
		return this.hands;
	}
	
	/**
	 * Get the number of detected hands.
	 * @return
	 */
	public int countHands(){
		if(this.isConnected()){
			return this.frame.hands().count();
		}
		return 0;
	}

	/**
	 * The member of the list that is farthest to the front within the standard Leap Motion frame of reference (i.e has the smallest Z coordinate). 
	 * @return
	 */
	public Hand getFrontHand(){		
		return new Hand(this.parent, this, this.frame.hands().frontmost());
	}
	
	/**
	 * The member of the list that is farthest to the left within the standard Leap Motion frame of reference (i.e has the smallest X coordinate). 
	 * @return
	 */
	public Hand getLeftHand(){		
		return new Hand(this.parent, this, this.frame.hands().leftmost());
	}
	
	/**
	 * The member of the list that is farthest to the right within the standard Leap Motion frame of reference (i.e has the largest X coordinate). 
	 * @return
	 */
	public Hand getRightHand(){		
		return new Hand(this.parent, this, this.frame.hands().rightmost());
	}
	

	/* ------------------------------------------------------------------------ */
	/* Fingers */
	
	/**
	 * Check if there is any finger.
	 * @return
	 */
	public boolean hasFingers(){
		if(this.isConnected()){
			return !this.frame.fingers().isEmpty();
		}
		return false;
	}

	/**
	 * Get a specific finger.
	 * @param id
	 * @return
	 */
	public Finger getFinger(Integer id){
		return new Finger(this.parent, this, this.frame.finger(id));
	}
	
	/**
	 * Get all detected fingers.
	 * @return
	 */
	public ArrayList<Finger> getFingers(){
		this.fingers.clear();
		if(this.hasFingers()){
			for(com.leapmotion.leap.Finger finger : this.frame.fingers()){
				fingers.add(new Finger(this.parent, this, finger));
		    }
		}
		return this.fingers;
	}

	/**
	 * Get the number of detected fingers.
	 * @return
	 */
	public int countFingers(){
		if(this.isConnected()){
			return this.frame.fingers().count();
		}
		return 0;
	}

	/**
	 * The member of the list that is farthest to the front within the standard Leap Motion frame of reference (i.e has the smallest Z coordinate). 
	 * @return
	 */
	public Finger getFrontFinger(){
		return new Finger(this.parent, this, this.frame.fingers().frontmost());
	}
	
	/**
	 * The member of the list that is farthest to the left within the standard Leap Motion frame of reference (i.e has the smallest X coordinate). 
	 * @return
	 */
	public Finger getLeftFinger(){		
		return new Finger(this.parent, this, this.frame.fingers().leftmost());
	}
	
	/**
	 * The member of the list that is farthest to the right within the standard Leap Motion frame of reference (i.e has the largest X coordinate). 
	 * @return
	 */
	public Finger getRightFinger(){		
		return new Finger(this.parent, this, this.frame.fingers().rightmost());
	}
	

	/* ------------------------------------------------------------------------ */
	/* Tools */
	
	/**
	 * Check if there is any tool.
	 * @return
	 */
	public boolean hasTools(){
		if(this.isConnected()){
			return !this.frame.tools().isEmpty();
		}
		return false;
	}

	/**
	 * Get a specific tool.
	 * @param id
	 * @return
	 */
	public Tool getTool(Integer id){
		return new Tool(this.parent, this, this.frame.tool(id));
	}
	
	/**
	 * Get all detected tools.
	 * @return
	 */
	public ArrayList<Tool> getTools(){
		this.tools.clear();
		if(this.hasTools()){
			for(com.leapmotion.leap.Tool tool : this.frame.tools()){
				tools.add(new Tool(this.parent, this, tool));
		    }
		}
		return this.tools;
	}

	/**
	 * Get the number of detected tools.
	 * @return
	 */
	public int countTools(){
		if(this.isConnected()){
			return this.frame.tools().count();
		}
		return 0;
	}

	/**
	 * The member of the list that is farthest to the front within the standard Leap Motion frame of reference (i.e has the smallest Z coordinate). 
	 * @return
	 */
	public Tool getFrontTool(){
		return new Tool(this.parent, this, this.frame.tools().frontmost());
	}
	
	/**
	 * The member of the list that is farthest to the left within the standard Leap Motion frame of reference (i.e has the smallest X coordinate). 
	 * @return
	 */
	public Tool getLeftTool(){		
		return new Tool(this.parent, this, this.frame.tools().leftmost());
	}
	
	/**
	 * The member of the list that is farthest to the right within the standard Leap Motion frame of reference (i.e has the largest X coordinate). 
	 * @return
	 */
	public Tool getRightTool(){		
		return new Tool(this.parent, this, this.frame.tools().rightmost());
	}
	
	
	/* ------------------------------------------------------------------------ */
	/* Gesture-Recognition */	
	
	/**
	 * Activate the gesture recognition.
	 * @param 	str	
	 * @return
	 */
	public LeapMotion withGestures(String str) {
		str = str.trim().toUpperCase();
		List<String> gestures = Arrays.asList(str.split("\\s*,\\s*"));
		this.recognition = false;
		for(String gesture : gestures){
			gesture = "TYPE_"+gesture;
			switch(com.leapmotion.leap.Gesture.Type.valueOf(gesture)){
				case TYPE_SWIPE:
					controller.enableGesture(Gesture.Type.TYPE_SWIPE);
					this.recognition = true;
					break;
				case TYPE_CIRCLE:
					controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
					this.recognition = true;
					break;
				case TYPE_SCREEN_TAP:
					controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
					this.recognition = true;
					break;
				case TYPE_KEY_TAP:
					controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
					this.recognition = true;
					break;
			}
		}
		if(this.recognition){
			this.parent.registerPre(this);
		} else {
			this.parent.unregisterPre(this);
		}
		return this;
	}
	public LeapMotion withGestures() {
		return this.withGestures("swipe, circle, screen_tap, key_tap");
	}

	/**
	 * Deactivate the gesture recognition.
	 * @return
	 */
	public LeapMotion withoutGestures() {
		this.parent.unregisterPre(this);
		this.recognition = false;
		return this;
	}
	
	/**
	 * Run the recognizer before the drawing.
	 */
	public void pre(){
		this.check();
	}	

	/**
	 * Run the recognizer.
	 */	
	private void check() {
		if (this.isConnected() && this.recognition) {
			for (com.leapmotion.leap.Gesture g : this.frame.gestures()) {
				int state = 2;
				switch (g.type()) {
					case TYPE_CIRCLE:
						state = 2;
						if(g.state()==com.leapmotion.leap.Gesture.State.STATE_START){
							state = 1;
						} else if(g.state()==com.leapmotion.leap.Gesture.State.STATE_STOP){
							state = 3;
						}
						try {
							this.parent.getClass().getMethod(
								"leapOnCircleGesture",
								de.voidplus.leapmotion.CircleGesture.class,
								int.class
							).invoke(
								this.parent,
								new de.voidplus.leapmotion.CircleGesture(
									this.parent,
									this, g
								),
								state
							);
						} catch (Exception e) {
							// e.printStackTrace();
						}
						break;
					case TYPE_SWIPE:
						state = 2;
						if(g.state()==com.leapmotion.leap.Gesture.State.STATE_START){
							state = 1;
						} else if(g.state()==com.leapmotion.leap.Gesture.State.STATE_STOP){
							state = 3;
						}
						try {
							this.parent.getClass().getMethod(
								"leapOnSwipeGesture",
								de.voidplus.leapmotion.SwipeGesture.class,
								int.class
							).invoke(
								this.parent,
								new de.voidplus.leapmotion.SwipeGesture(
									this.parent,
									this, g
								),
								state
							);
						} catch (Exception e) {
							// e.printStackTrace();
						}
						break;
					case TYPE_SCREEN_TAP:
						if(g.state()==com.leapmotion.leap.Gesture.State.STATE_STOP){
							try {
								this.parent.getClass().getMethod(
									"leapOnScreenTapGesture",
									de.voidplus.leapmotion.ScreenTapGesture.class
								).invoke(
									this.parent,
									new de.voidplus.leapmotion.ScreenTapGesture(
										this.parent,
										this, g
									)
								);
							} catch (Exception e) {
								// e.printStackTrace();
							}
						}
						break;
					case TYPE_KEY_TAP:
						if(g.state()==com.leapmotion.leap.Gesture.State.STATE_STOP){
							try {
								this.parent.getClass().getMethod(
									"leapOnKeyTapGesture",
									de.voidplus.leapmotion.KeyTapGesture.class
								).invoke(
									this.parent,
									new de.voidplus.leapmotion.KeyTapGesture(
										this.parent,
										this, g
									)
								);
							} catch (Exception e) {
								// e.printStackTrace();
							}
						}
						break;
					default:
			            System.out.println("Unknown gesture type.");
			            break;
				}
				
			}
		}
	}
	
	
	/**
	 * Set Gesture.Circle.MinRadius (Default: 5.0 mm)
	 * @param mm
	 * @return 
	 */
	public LeapMotion setGestureCircleMinRadius(float mm) {
		return this.setConfig("Gesture.Circle.MinRadius", mm);
	}
	
	/**
	 * Set Gesture.Circle.MinArc (Default: 270 degrees)
	 * @param degrees
	 * @return
	 */
	public LeapMotion setGestureCircleMinArc(float degrees) {
		return this.setConfig("Gesture.Circle.MinArc", this.parent.radians(degrees));
	}

	/**
	 * Set Gesture.Swipe.MinLength (Default: 150 mm)
	 * @param mm
	 * @return
	 */
	public LeapMotion setGestureSwipeMinLength(float mm) {
		return this.setConfig("Gesture.Swipe.MinLength", mm);
	}
	
	/**
	 * Set Gesture.Swipe.MinVelocity (Default: 1000 mm/s)
	 * @param mms
	 * @return
	 */
	public LeapMotion setGestureSwipeMinVelocity(float mms) {
		return this.setConfig("Gesture.Swipe.MinVelocity", mms);
	}

	/**
	 * Set Gesture.KeyTap.MinDownVelocity (Default: 50 mm/s)
	 * @param mms
	 * @return
	 */
	public LeapMotion setGestureKeyTapMinDownVelocity(float mms) {
		return this.setConfig("Gesture.KeyTap.MinDownVelocity", mms);
	}
	
	/**
	 * Set Gesture.KeyTap.HistorySeconds (Default: 0.1 s)
	 * @param s
	 * @return
	 */
	public LeapMotion setGestureKeyTapHistorySeconds(float s) {
		return this.setConfig("Gesture.KeyTap.HistorySeconds", s);
	}
	
	/**
	 * Set Gesture.KeyTap.MinDistance (Default: 3.0 mm)
	 * @param mm
	 * @return
	 */
	public LeapMotion setGestureKeyTapMinDistance(float mm) {
		return this.setConfig("Gesture.KeyTap.MinDistance", mm);
	}
	
	/**
	 * Set Gesture.ScreenTap.MinForwardVelocity (Default: 50 mm/s)
	 * @param mms
	 * @return
	 */
	public LeapMotion setGestureScreenTapMinForwardVelocity(float mms) {
		return this.setConfig("Gesture.ScreenTap.MinForwardVelocity", mms);
	}

	/**
	 * Set Gesture.ScreenTap.HistorySeconds (Default: 0.1 s)
	 * @param s
	 * @return
	 */
	public LeapMotion setGestureScreenTapHistorySeconds(float s) {
		return this.setConfig("Gesture.ScreenTap.HistorySeconds", s);
	}
	
	/**
	 * Set Gesture.ScreenTap.MinDistance (Default: 5.0 mm)
	 * @param mm
	 * @return
	 */
	public LeapMotion setGestureScreenTapMinDistance(float mm) {
		return this.setConfig("Gesture.ScreenTap.MinDistance", mm);
	}

	
	/* ------------------------------------------------------------------------ */
	/* Configuration */
	
	/**
	 * Set configuration parameters.
	 * @param keyString
	 * @param value
	 * @return
	 */
	public LeapMotion setConfig(String keyString, int value){
		if(this.controller.isConnected()){
			if(this.controller.config().setInt32(keyString, value)){
				this.controller.config().save();
			}
		}
		this.controller.config().save();
		return this;
	}
	
	/**
	 * Set configuration parameters.
	 * @param keyString
	 * @param value
	 * @return
	 */
	public LeapMotion setConfig(String keyString, float value){
		if(this.controller.isConnected()){
			if(this.controller.config().setFloat(keyString, value)){
				this.controller.config().save();
			}
		}
		return this;
	}
	
	/**
	 * Set configuration parameters.
	 * @param keyString
	 * @param value
	 * @return
	 */
	public LeapMotion setConfig(String keyString, boolean value){
		if(this.controller.isConnected()){
			if(this.controller.config().setBool(keyString, value)){
				this.controller.config().save();
			}
		}
		return this;
	}
	
	/**
	 * Set configuration parameters.
	 * @param keyString
	 * @param value
	 * @return
	 */
	public LeapMotion setConfig(String keyString, String value){
		if(this.controller.isConnected()){
			if(this.controller.config().setString(keyString, value)){
				this.controller.config().save();
			}
		}
		return this;
	}

	
	/* ------------------------------------------------------------------------ */
	/* Geometry */
	
	/**
	 * Convert/map the Vector data to sketch PVector data with world settings.
	 * @param 	position
	 * @return
	 */
	public PVector map(Vector position){
		
//		InteractionBox box = this.frame.interactionBox();
//		Vector normalized = box.normalizePoint(position);
//		PVector response = new PVector(
//			(normalized.getX()*this.parent.width),
//			(this.parent.height-normalized.getY()*this.parent.height)
//		);
		
		PVector response = new PVector();
		
		// WIDTH
		if (position.getX() > 0) {
			response.x = PApplet.lerp(
				(this.parent.width * 0.5f),
				this.parent.width,
				(position.getX() / this.world.x)
			);
		} else {
			response.x = PApplet.lerp(
				(this.parent.width * 0.5f),
				0.0f,
				(-position.getX() / this.world.x)
			);
		}
		
		// HEIGHT
		response.y = PApplet.lerp(
			this.parent.height,
			0.0f,
			(position.getY() / world.y)
		);
		
		// DEPTH
		response.z = PApplet.lerp(
			50.0f,
			0.0f,
			(position.getZ() / world.z)
		);
		
		return response;
	}
	
	/**
	 * Convert Vector to PVector without modifications
	 * @param position
	 * @return
	 */
	public PVector convert(Vector position) {
		return new PVector(
			position.getX(),
			position.getY(),
			position.getZ()
		);
	}

	/* ------------------------------------------------------------------------ */
	/* Library */
	
	/**
	 * Method to route the callbacks.
	 * @param method
	 * @param controller
	 */
	private void dispatch(final String method) {
		boolean success = false;
		try {
			this.parent.getClass().getMethod(
				method
			).invoke(
				this.parent
			);
			success = true;
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			if(success && this.verbose){
				System.out.println("# LeapMotion: Callback "+method+"();");
			}
		}
	}
	
	/**
	 * Print debug information to the console.
	 * @param 	verbose
	 * @return
	 */
	public LeapMotion setVerbose(boolean verbose){
		this.verbose = verbose;
		return this;
	}
	
	/**
	 * Get the version of the library. 
	 * @return
	 */
	public String getVersion(){
		return this.VERSION;
	}
	
	/**
	 * Get the used version of the SDK.
	 * @return
	 */
	public String getVersionSDK(){
		return this.SDK_VERSION;
	}	
	
	public void dispose(){
		this.controller.removeListener(this.listener);
	}

}
