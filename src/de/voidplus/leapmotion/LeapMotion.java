package de.voidplus.leapmotion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.leapmotion.leap.Controller.PolicyFlag;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Vector;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Gesture;

import processing.core.PApplet;
import processing.core.PVector;


/**
 * Leap Motion for Processing
 * @author Darius Morawiec
 * @version 2.2.5.1
 */
public class LeapMotion {
	
	public static final String NAME = "Leap Motion";
	public static final String REPO = "https://github.com/nok/leap-motion-processing";
	public static final String VERSION = "2.2.5.1"; 
	public static final String SDK_VERSION = "2.2.5+26752";
	
	// Processing 
	private final PApplet parent;
	
	// Global flags
	private boolean verbose;
	private boolean recognition;
	
	// Data
	private PVector world;
	private PVector origin;
	private ArrayList<Hand> hands;
	private ArrayList<Finger> fingers;
	private ArrayList<Finger> outstretchedFingers;
	private ArrayList<Finger> outstretchedFingersByAngel;
	private ArrayList<Tool> tools;
	private ArrayList<Device> devices;
	private ArrayList<Image> images;
	
	// Internal handler
	private Frame frame;
	private Frame lastFrame;
	private final Controller controller;
	private final Listener listener;
	
	
	/**
	 * LeapMotion constructor to initialize the controller and listener.
	 * @param parent 	Reference (this) of sketch.
	 * @param verbose	Print debug information to the console.
	 */
	public LeapMotion(PApplet parent, boolean verbose){
		this.parent = parent;
		this.setVerbose(verbose);
		this.recognition = false;
		
		this.log("# " + LeapMotion.NAME + " Library v" + LeapMotion.VERSION + " - Leap Motion SDK v" + LeapMotion.SDK_VERSION + " - " + LeapMotion.REPO, true);
		
		// Data
		this.setWorld(200, 500, 200);
		this.hands = new ArrayList<Hand>();
		this.fingers = new ArrayList<Finger>();
		this.outstretchedFingers = new ArrayList<Finger>();
		this.outstretchedFingersByAngel = new ArrayList<Finger>();
		this.tools = new ArrayList<Tool>();
		this.devices = new ArrayList<Device>();
		this.images = new ArrayList<Image>();
		
		// Internal handler
		this.frame = Frame.invalid();
		this.lastFrame = Frame.invalid();
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
		this.withBackgroundFrames();
		
//		this.parent.registerDispose(this);
		this.parent.registerMethod("dispose", this); // support since version 2.0b7 (REV 0215)
	}
	
	/**
	 * LeapMotion constructor to initialize the controller and listener.
	 * @param parent Reference (this) of sketch.
	 */
	public LeapMotion(PApplet parent){
		this(parent, false);
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
	
	/**
	 * Get the current timestamp.
	 * @return
	 */
	public long getTimestamp(){
		if(this.isConnected()){
			return this.frame.timestamp();
		}
		return 0;
	}
	
	/**
	 * Get the current ID.
	 * @return
	 */
	public long getId(){
		if(this.isConnected()){
			return this.frame.id();
		}
		return Frame.invalid().id();
	}

	
	/* ------------------------------------------------------------------------ */
	/* Policy-Flags */
	
	/**
	 * Print policy flags.
	 */
	public void printPolicyFlags(){
		for (PolicyFlag flag : PolicyFlag.values()) {
			if(this.controller.isPolicySet(flag)){
				this.log("'"+flag.toString()+"' is set.");
			}
		}
	}
	
	/**
	 * Enable the tracking in background.
	 * @return
	 */	
	public LeapMotion withBackgroundFrames(){
		if(!this.controller.isPolicySet(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES)){
			this.controller.setPolicy(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES);
		}
		return this;
	}
	
	/**
	 * Disable the tracking in background.
	 * @return
	 */	
	public LeapMotion withoutBackgroundFrames(){
		if(this.controller.isPolicySet(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES)){
			this.controller.clearPolicy(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES);
		}		
		return this;
	}

	/**
	 * Run tracking in background too.
	 * @return
	 * @deprecated
	 */
	public LeapMotion runInBackground(boolean active){
		this.log("'runInBackground("+Boolean.toString(active)+")' is deprecated. Please use 'withBackgroundFrames()'.");
		return this.withBackgroundFrames();
	}
	
	/**
	 * Run tracking in background too.
	 * @return
	 */
	public LeapMotion allowRunInBackground(){
		this.log("'allowRunInBackground()' is deprecated. Please use 'withBackgroundFrames()'.");
		return this.withBackgroundFrames();
	}
	
	
	/* ------------------------------------------------------------------------ */
	/* World */
	
	/**
	 * Set the world coordinates.
	 * @param width 	World width dimension in millimeters (default: 200).
	 * @param height 	World height dimension in millimeters (default: 500).
	 * @param depth 	World depth dimension in millimeters (default: 200).
	 * @return
	 */
	public LeapMotion setWorld(int width, int height, int depth){
		return this.setWorld(new PVector(width, height, depth));
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
	
	/**
	 * Move the world origin.
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public LeapMotion moveWorld(int x, int y, int z){
		return this.moveWorld(new PVector(x, y, z));
	}
	
	/**
	 * Move the world origin.
	 * @param origin
	 * @return
	 */
	public LeapMotion moveWorld(PVector origin){
		this.origin = origin;
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
		com.leapmotion.leap.Hand hand = this.frame.hand(id);
		if(!LeapMotion.isNull(hand) && Hand.isValid(hand)){
			return new Hand(this.parent, this, hand);
		}
		return null;
	}
	
	/**
	 * Get all detected hands.
	 * @return
	 */
	public ArrayList<Hand> getHands(){
		this.hands.clear();
		if(this.hasHands()){
			for(com.leapmotion.leap.Hand hand : this.frame.hands()){
				if(Hand.isValid(hand)){
					hands.add(new Hand(this.parent, this, hand));
				}
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
		if (!this.frame.hands().isEmpty()) {
			com.leapmotion.leap.Hand hand = this.frame.hands().frontmost();
			if (!LeapMotion.isNull(hand) && Hand.isValid(hand)) {
				return new Hand(this.parent, this, hand);
			}
		}
		return null;
	}
	
	/**
	 * The member of the list that is farthest to the left within the standard Leap Motion frame of reference (i.e has the smallest X coordinate). 
	 * @return
	 */
	public Hand getLeftHand(){
		if (!this.frame.hands().isEmpty()) {
			com.leapmotion.leap.Hand hand = this.frame.hands().leftmost();
			if (!LeapMotion.isNull(hand) && Hand.isValid(hand)) {
				return new Hand(this.parent, this, hand);
			}
		}
		return null;
	}
	
	/**
	 * The member of the list that is farthest to the right within the standard Leap Motion frame of reference (i.e has the largest X coordinate). 
	 * @return
	 */
	public Hand getRightHand(){		
		if (!this.frame.hands().isEmpty()) {
			com.leapmotion.leap.Hand hand = this.frame.hands().rightmost();
			if (!LeapMotion.isNull(hand) && Hand.isValid(hand)) {
				return new Hand(this.parent, this, hand);
			}
		}
		return null;
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
		com.leapmotion.leap.Finger finger = this.frame.finger(id);
		if(!LeapMotion.isNull(finger) && Finger.isValid(finger)){
			return new Finger(this.parent, this, finger);
		}
		return null;
	}
	
	/**
	 * Get all detected fingers.
	 * @return
	 */
	public ArrayList<Finger> getFingers() {
		this.fingers.clear();
		if (this.hasFingers()) {
			for (com.leapmotion.leap.Finger finger : this.frame.fingers()) {
				if (Finger.isValid(finger)) {
					fingers.add(new Finger(this.parent, this, finger));
				}
		    }
		}
		return this.fingers;
	}
	
	/**
	 * Get all outstrechted fingers.
	 * @return
	 */
	public ArrayList<Finger> getOutstrechtedFingers() {
		this.outstretchedFingers.clear();
		if(!this.frame.fingers().extended().isEmpty()){
			for(com.leapmotion.leap.Finger finger : this.frame.fingers().extended()){
				this.outstretchedFingers.add(new Finger(this.parent, this, finger));
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
	 * Get all outstrechted fingers by angel.
	 * @param similarity Minimum value of similarity.
	 * @return
	 */
	public ArrayList<Finger> getOutstrechtedFingersByAngel(int similarity) {
		this.outstretchedFingersByAngel.clear();
		if (this.hasFingers()) {
			for (com.leapmotion.leap.Finger finger : this.frame.fingers()) {
				if (Finger.isValid(finger)) {
					Finger candidate = new Finger(this.parent, this, finger);
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
	 * Get all outstrechted fingers with 75% likelihood.
	 * @return
	 */
	public ArrayList<Finger> getOutstrechtedFingersByAngel() {
		return this.getOutstrechtedFingersByAngel(75);
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
		com.leapmotion.leap.Tool tool = this.frame.tool(id);
		if(!LeapMotion.isNull(tool) && Tool.isValid(tool)){
			return new Tool(this.parent, this, tool);
		}
		return null;
	}
	
	/**
	 * Get all detected tools.
	 * @return
	 */
	public ArrayList<Tool> getTools(){
		this.tools.clear();
		if(this.hasTools()){
			for(com.leapmotion.leap.Tool tool : this.frame.tools()){
				if(Tool.isValid(tool)){
					tools.add(new Tool(this.parent, this, tool));
				}
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
	/* Camera-Images */	
	
	/**
	 * Enable camera images.
	 * @return
	 */
	public LeapMotion withCameraImages() {
		if(!this.controller.isPolicySet(Controller.PolicyFlag.POLICY_IMAGES)){
			this.controller.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);
		}
		return this;
	}

	/**
	 * Disable camera images.
	 * @return
	 */
	public LeapMotion withoutCameraImages() {
		if(this.controller.isPolicySet(Controller.PolicyFlag.POLICY_IMAGES)){
			this.controller.clearPolicy(Controller.PolicyFlag.POLICY_IMAGES);
		}
		return this;
	}
	
	/**
	 * Any raw images available?
	 * @return
	 */
	public boolean hasImages(){
		if(this.controller.isPolicySet(Controller.PolicyFlag.POLICY_IMAGES)){
			if(this.controller.frame().images().count()>0){
				return true;
			}	
		} else {
			this.withCameraImages();
		}
		return false;
	}
	
	/**
	 * Get all raw camera images.
	 * @return
	 */
	public ArrayList<Image> getImages(){
		this.images.clear();
		if(this.hasImages()){
			for(com.leapmotion.leap.Image image : this.controller.frame().images()){
				if(Image.isValid(image)){
					images.add(new Image(this.parent, this, image));
				}
		    }
		}
		return this.images;
	}
	
	
	/* ------------------------------------------------------------------------ */
	/* Optimized HMD */	
	
	/**
	 * Enable optimized tracking for head mounted displays.
	 * @return
	 */
	public LeapMotion withOptimizedHdm() {
		if(!this.controller.isPolicySet(Controller.PolicyFlag.POLICY_OPTIMIZE_HMD)){
			this.controller.setPolicy(Controller.PolicyFlag.POLICY_OPTIMIZE_HMD);
		}
		return this;
	}

	/**
	 * Disable optimized tracking for head mounted displays.
	 * @return
	 */
	public LeapMotion withoutOptimizedHdm() {
		if(this.controller.isPolicySet(Controller.PolicyFlag.POLICY_OPTIMIZE_HMD)){
			this.controller.clearPolicy(Controller.PolicyFlag.POLICY_OPTIMIZE_HMD);
		}
		return this;
	}
	
	
	/* ------------------------------------------------------------------------ */
	/* Gesture-Recognition */	
	
	/**
	 * Enable gesture recognition.
	 * @param 	str	
	 * @return
	 */
	public LeapMotion withGestures(String str) {
		str = str.trim().toUpperCase();
		List<String> gestures = Arrays.asList(str.split("\\s*,\\s*"));
		this.recognition = false;
		for(String gesture : gestures){
			gesture = "TYPE_"+gesture;
			this.recognition = true;
			switch(com.leapmotion.leap.Gesture.Type.valueOf(gesture)){
				case TYPE_SWIPE:
					controller.enableGesture(Gesture.Type.TYPE_SWIPE);
					break;
				case TYPE_CIRCLE:
					controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
					break;
				case TYPE_SCREEN_TAP:
					controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
					break;
				case TYPE_KEY_TAP:
					controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
					break;
				default:
					this.recognition = false;
					break;
			}
		}
		if(this.recognition){
//			this.parent.registerPre(this);
			this.parent.registerMethod("pre", this);
		} else {
//			this.parent.unregisterPre(this);
			this.parent.unregisterMethod("pre", this);
		}
		return this;
	}
	public LeapMotion withGestures() {
		return this.withGestures("swipe, circle, screen_tap, key_tap");
	}

	/**
	 * Disable gesture recognition.
	 * @return
	 */
	public LeapMotion withoutGestures() {
//		this.parent.unregisterPre(this);
		this.parent.unregisterMethod("pre", this);
		this.recognition = false;
		return this;
	}
	
	/**
	 * Run the recognizer before sketch drawing.
	 */
	public void pre(){
		this.check();
	}	

	/**
	 * Check available gestures.
	 */	
	private void check() {
		if (this.isConnected() && this.recognition) {
			for (com.leapmotion.leap.Gesture g : this.frame.gestures(this.lastFrame)) {
				if (g.isValid()) {
					int state = 2;
					switch (g.type()) {
						case TYPE_CIRCLE:
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
			this.lastFrame = this.frame;
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
		return this.setConfig("Gesture.Circle.MinArc", PApplet.radians(degrees));
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
	protected PVector map(Vector position){
		
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
		
		return this.move(response);
	}
	
	/**
	 * Convert Vector to PVector without modifications
	 * @param position
	 * @return
	 */
	protected PVector convert(Vector position) {
		return this.move(new PVector(
			position.getX(),
			position.getY(),
			position.getZ()
		));
	}

	/**
	 * Replace all elements.
	 * @param position
	 * @return
	 */
	private PVector move(PVector position){
		if(this.origin!=null){
			position.x += origin.x;
			position.y += origin.y;
			position.z += origin.z;	
		}
		return position;
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
				this.log("Callback "+method+"();");
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
		return LeapMotion.VERSION;
	}
	
	/**
	 * Get the used version of the SDK.
	 * @return
	 */
	public String getVersionSDK(){
		return LeapMotion.SDK_VERSION;
	}	
	
	public void dispose(){
		this.controller.removeListener(this.listener);
	}

	
	/* ------------------------------------------------------------------------ */
	/* Helpers */
	
	/**
	 * Log message to console.
	 * @param message
	 * @param skip
	 */
	private void log(String message, boolean skip){
		if(!skip){
			PApplet.println("# " + LeapMotion.NAME + ": " + message);
		} else {
			PApplet.println(message);
		}
	}
	private void log(String message){
		this.log(message, false);
	}

	private static boolean isNull(Object object){
		return object==null;
	}
	
}