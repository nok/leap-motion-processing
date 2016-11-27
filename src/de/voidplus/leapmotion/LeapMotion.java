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
import com.leapmotion.leap.Gesture.State;
import processing.core.PApplet;
import processing.core.PVector;


/**
 * Leap Motion for Processing
 *
 * @author Darius Morawiec
 * @version 2.3.1.6
 */
public class LeapMotion {

    private static final String NAME = "Leap Motion";
    private static final String REPO = "https://github.com/nok/leap-motion-processing";
    private static final String VERSION = "2.3.1.6";
    private static final String SDK_VERSION = "2.3.1+31549";

    // Processing
    private final PApplet parent;
    protected boolean is2D;

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
     *
     * @param parent  Instance (this) of the used sketch
     * @param verbose Print debug information to the console
     */
    public LeapMotion(PApplet parent, boolean verbose) {
        this.parent = parent;
        this.is2D = parent.g.is2D();
        this.setVerbose(verbose);
        this.recognition = false;
        this.println(
                String.format("# %s Library v%s - Leap Motion SDK v%s - %s",
                        LeapMotion.NAME,
                        this.getVersion(),
                        this.getVersionSDK(),
                        LeapMotion.REPO
                ), false
        );

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
        this.listener = new Listener() {
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

            public void onFrame(Controller controller) {
                frame = controller.frame();
                dispatch("leapOnFrame");
            }
        };
        this.controller.addListener(this.listener);
        this.allowBackgroundApps();

        // support since version 2.0b7 (REV 0215)
        this.parent.registerMethod("dispose", this);
    }

    /**
     * LeapMotion constructor to initialize the controller and listener.
     *
     * @param parent Reference (this) of sketch.
     */
    public LeapMotion(PApplet parent) {
        this(parent, false);
    }

    /**
     * The instantaneous framerate.
     *
     * @return Instantaneous framerwate
     */
    public int getFrameRate() {
        if (this.isConnected()) {
            return (int) this.frame.currentFramesPerSecond();
        }
        return 0;
    }

    /**
     * Get the current timestamp.
     *
     * @return Current timestamp
     */
    public long getTimestamp() {
        if (this.isConnected()) {
            return this.frame.timestamp();
        }
        return 0;
    }

    /**
     * Get the current frame ID.
     *
     * @return Frame ID
     */
    public long getId() {
        if (this.isConnected()) {
            return this.frame.id();
        }
        return Frame.invalid().id();
    }

	
	/* ------------------------------------------------------------------------ */
    /* Policy-Flags */

    /**
     * Print all policy flags.
     */
    public void printPolicyFlags() {
        for (PolicyFlag _flag : PolicyFlag.values()) {
            if (this.controller.isPolicySet(_flag)) {
                this.log(String.format("'%s' is set.", _flag.toString()));
            }
        }
    }

    /**
     * Allow background applications.
     *
     * @return LeapMotion
     */
    public LeapMotion allowBackgroundApps() {
        if (!this.controller.isPolicySet(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES)) {
            this.controller.setPolicy(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES);
        }
        return this;
    }

    /**
     * Allow background applications.
     *
     * @return LeapMotion
     * @deprecated
     */
    public LeapMotion withBackgroundFrames() {
        this.log("'withBackgroundFrames()' is deprecated. Please use 'allowBackgroundApps()'.");
        return this.allowBackgroundApps();
    }

    /**
     * Allow background applications.
     *
     * @return LeapMotion
     * @deprecated
     */
    public LeapMotion runInBackground(boolean active) {
        this.log("'runInBackground()' is deprecated. Please use 'allowBackgroundApps()'.");
        return this.allowBackgroundApps();
    }

    /**
     * Allow background applications.
     *
     * @return LeapMotion
     * @deprecated
     */
    public LeapMotion allowRunInBackground() {
        this.log("'allowRunInBackground()' is deprecated. Please use 'allowBackgroundApps()'.");
        return this.allowBackgroundApps();
    }

    /**
     * Disallow background applications.
     *
     * @return LeapMotion
     * @deprecated
     */
    public LeapMotion withoutBackgroundFrames() {
        this.log("'withoutBackgroundFrames()' is deprecated. Please use 'disallowBackgroundApps()'.");
        return this.disallowBackgroundApps();
    }

    /**
     * Disallow background applications.
     *
     * @return LeapMotion
     */
    public LeapMotion disallowBackgroundApps() {
        if (this.controller.isPolicySet(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES)) {
            this.controller.clearPolicy(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES);
        }
        return this;
    }

	
	/* ------------------------------------------------------------------------ */
    /* World */

    /**
     * Set the world coordinates.
     *
     * @param width  World width dimension in millimeters (default: 200).
     * @param height World height dimension in millimeters (default: 500).
     * @param depth  World depth dimension in millimeters (default: 200).
     * @return LeapMotion
     */
    public LeapMotion setWorld(int width, int height, int depth) {
        return this.setWorld(new PVector(width, height, depth));
    }

    /**
     * Set the world coordinates.
     *
     * @param world World dimensions in millimeters.
     * @return LeapMotion
     */
    public LeapMotion setWorld(PVector world) {
        this.world = world;
        return this;
    }

    /**
     * Move the world origin.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @return LeapMotion
     */
    public LeapMotion moveWorld(int x, int y, int z) {
        return this.moveWorld(new PVector(x, y, z));
    }

    /**
     * Move the world origin.
     *
     * @param origin PVector of the origin
     * @return LeapMotion
     */
    public LeapMotion moveWorld(PVector origin) {
        this.origin = origin;
        return this;
    }


	/* ------------------------------------------------------------------------ */
    /* Device */

    /**
     * Is the device connected successfully?
     *
     * @return Is the device connected successfully?
     */
    public boolean isConnected() {
        return this.controller.isConnected();
    }

    /**
     * Get the raw instance of com.leapmotion.leap.Controller
     *
     * @return used com.leapmotion.leap.Controller instance
     */
    public Controller getController() {
        return this.controller;
    }

    /**
     * Get all devices (for future updates)
     *
     * @return List of devices
     */
    public ArrayList<Device> getDevices() {
        devices.clear();
        if (this.isConnected()) {
            if (!this.getController().devices().isEmpty()) {
                for (com.leapmotion.leap.Device _device : this.getController().devices()) {
                    devices.add(new Device(this.parent, this, _device));
                }
            }
        }
        return this.devices;
    }

	
	/* ------------------------------------------------------------------------ */
    /* Hands */

    /**
     * Is it a valid hand?
     *
     * @param _hand Instance of com.leapmotion.leap.Hand
     * @return Is it a valid hand?
     */
    private static boolean isValid(com.leapmotion.leap.Hand _hand) {
        return _hand != null && _hand.isValid();
    }

    /**
     * Are there any hands?
     *
     * @return Are there any hands?
     */
    public boolean hasHands() {
        return this.isConnected() && !this.frame.hands().isEmpty();
    }

    /**
     * Get the number of detected hands.
     *
     * @return Number of detected hands
     */
    public int countHands() {
        return this.isConnected() ? this.frame.hands().count() : 0;
    }

    /**
     * Get a specific hand.
     *
     * @param id ID of a specific hand
     * @return Single hand or null
     */
    public Hand getHand(Integer id) {
        if (!this.hasHands()) {
            com.leapmotion.leap.Hand _hand = this.frame.hand(id);
            if (LeapMotion.isValid(_hand)) {
                return new Hand(this.parent, this, _hand);
            }
        }
        return null;
    }

    /**
     * Get all detected hands.
     *
     * @return List of all detected hands
     */
    public ArrayList<Hand> getHands() {
        this.hands.clear();
        if (this.hasHands()) {
            for (com.leapmotion.leap.Hand _hand : this.frame.hands()) {
                if (LeapMotion.isValid(_hand)) {
                    hands.add(new Hand(this.parent, this, _hand));
                }
            }
        }
        return this.hands;
    }

    /**
     * The member of the list that is farthest to the front within the standard Leap Motion frame of reference (i.e has the smallest Z coordinate).
     *
     * @return Single hand or null
     */
    public Hand getFrontHand() {
        if (!this.hasHands()) {
            com.leapmotion.leap.Hand _hand = this.frame.hands().frontmost();
            if (LeapMotion.isValid(_hand)) {
                return new Hand(this.parent, this, _hand);
            }
        }
        return null;
    }

    /**
     * The member of the list that is farthest to the left within the standard Leap Motion frame of reference (i.e has the smallest X coordinate).
     *
     * @return Single hand or null
     */
    public Hand getLeftHand() {
        if (this.hasHands()) {
            com.leapmotion.leap.Hand _hand = this.frame.hands().leftmost();
            if (LeapMotion.isValid(_hand)) {
                return new Hand(this.parent, this, _hand);
            }
        }
        return null;
    }

    /**
     * The member of the list that is farthest to the right within the standard Leap Motion frame of reference (i.e has the largest X coordinate).
     *
     * @return Right hand or null
     */
    public Hand getRightHand() {
        if (this.hasHands()) {
            com.leapmotion.leap.Hand _hand = this.frame.hands().rightmost();
            if (LeapMotion.isValid(_hand)) {
                return new Hand(this.parent, this, _hand);
            }
        }
        return null;
    }


	/* ------------------------------------------------------------------------ */
    /* Fingers */

    /**
     * Is it a valid finger?
     *
     * @param _finger Instance of com.leapmotion.leap.Finger
     * @return Is it a valid finger?
     */
    private static boolean isValid(com.leapmotion.leap.Finger _finger) {
        return _finger != null && _finger.isValid();
    }

    /**
     * Are there any fingers?
     *
     * @return Are there any fingers?
     */
    public boolean hasFingers() {
        return this.isConnected() && !this.frame.fingers().isEmpty();
    }

    /**
     * Get a specific finger.
     *
     * @param id ID of a specific finger
     * @return Single finger or null
     */
    public Finger getFinger(Integer id) {
        if (this.hasFingers()) {
            com.leapmotion.leap.Finger _finger = this.frame.finger(id);
            if (!LeapMotion.isValid(_finger)) {
                return new Finger(this.parent, this, _finger);
            }
        }
        return null;
    }

    /**
     * Get all detected fingers.
     *
     * @return List of fingers
     */
    public ArrayList<Finger> getFingers() {
        this.fingers.clear();
        if (this.hasFingers()) {
            for (com.leapmotion.leap.Finger _finger : this.frame.fingers()) {
                if (_finger.isValid()) {
                    fingers.add(new Finger(this.parent, this, _finger));
                }
            }
        }
        return this.fingers;
    }

    /**
     * Get all outstretched fingers.
     *
     * @return List of fingers
     */
    public ArrayList<Finger> getOutstretchedFingers() {
        this.outstretchedFingers.clear();
        if (!this.frame.fingers().extended().isEmpty()) {
            for (com.leapmotion.leap.Finger _finger : this.frame.fingers().extended()) {
                this.outstretchedFingers.add(new Finger(this.parent, this, _finger));
            }
        }
        return this.outstretchedFingers;
    }

    /**
     * Get all outstretched fingers by angel.
     *
     * @param similarity Minimum value of similarity
     * @return List of fingers
     */
    public ArrayList<Finger> getOutstretchedFingersByAngel(int similarity) {
        this.outstretchedFingersByAngel.clear();
        if (this.hasFingers()) {
            for (com.leapmotion.leap.Finger _finger : this.frame.fingers()) {
                if (_finger.isValid()) {
                    Finger candidate = new Finger(this.parent, this, _finger);
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
     *
     * @return List of fingers
     */
    public ArrayList<Finger> getOutstrechtedFingersByAngel() {
        return this.getOutstretchedFingersByAngel(75);
    }

    /**
     * Get the number of overall detected fingers.
     *
     * @return Number of fingers
     */
    public int countFingers() {
        if (this.isConnected()) {
            return this.frame.fingers().count();
        }
        return 0;
    }

    /**
     * The member of the list that is farthest to the front within the standard Leap Motion frame of reference (i.e has the smallest Z coordinate).
     *
     * @return Single finger or null
     */
    public Finger getFrontFinger() {
        if (this.hasFingers()) {
            return new Finger(this.parent, this, this.frame.fingers().frontmost());
        }
        return null;
    }

    /**
     * The member of the list that is farthest to the left within the standard Leap Motion frame of reference (i.e has the smallest X coordinate).
     *
     * @return Single finger or null
     */
    public Finger getLeftFinger() {
        if (this.hasFingers()) {
            return new Finger(this.parent, this, this.frame.fingers().leftmost());
        }
        return null;
    }

    /**
     * The member of the list that is farthest to the right within the standard Leap Motion frame of reference (i.e has the largest X coordinate).
     *
     * @return Single finger or null
     */
    public Finger getRightFinger() {
        if (this.hasFingers()) {
            return new Finger(this.parent, this, this.frame.fingers().rightmost());
        }
        return null;
    }


	/* ------------------------------------------------------------------------ */
    /* Tools */

    /**
     * Is it a valid tool?
     *
     * @param _tool Instance of com.leapmotion.leap.Tool
     * @return Is it a valid tool?
     */
    private static boolean isValid(com.leapmotion.leap.Tool _tool) {
        return _tool != null && _tool.isValid();
    }

    /**
     * Are there any tools?
     *
     * @return Are there any tools?
     */
    public boolean hasTools() {
        return this.isConnected() && !this.frame.tools().isEmpty();
    }

    /**
     * Get a specific tool.
     *
     * @param id ID of a specific tool
     * @return Single tool or null
     */
    public Tool getTool(Integer id) {
        if (this.hasTools()) {
            com.leapmotion.leap.Tool _tool = this.frame.tool(id);
            if (!LeapMotion.isValid(_tool)) {
                return new Tool(this.parent, this, _tool);
            }
        }
        return null;
    }

    /**
     * Get all detected tools.
     *
     * @return List of tools
     */
    public ArrayList<Tool> getTools() {
        this.tools.clear();
        if (this.hasTools()) {
            for (com.leapmotion.leap.Tool _tool : this.frame.tools()) {
                if (LeapMotion.isValid(_tool)) {
                    tools.add(new Tool(this.parent, this, _tool));
                }
            }
        }
        return this.tools;
    }

    /**
     * Get the number of detected tools.
     *
     * @return List of tools
     */
    public int countTools() {
        if (this.isConnected()) {
            return this.frame.tools().count();
        }
        return 0;
    }

    /**
     * The member of the list that is farthest to the front within the standard Leap Motion frame of reference (i.e has the smallest Z coordinate).
     *
     * @return Single tool or null
     */
    public Tool getFrontTool() {
        if (this.hasTools()) {
            return new Tool(this.parent, this, this.frame.tools().frontmost());
        }
        return null;
    }

    /**
     * The member of the list that is farthest to the left within the standard Leap Motion frame of reference (i.e has the smallest X coordinate).
     *
     * @return Single tool or null
     */
    public Tool getLeftTool() {
        if (this.hasTools()) {
            return new Tool(this.parent, this, this.frame.tools().leftmost());
        }
        return null;
    }

    /**
     * The member of the list that is farthest to the right within the standard Leap Motion frame of reference (i.e has the largest X coordinate).
     *
     * @return Single tool or null
     */
    public Tool getRightTool() {
        if (this.hasTools()) {
            return new Tool(this.parent, this, this.frame.tools().rightmost());
        }
        return null;
    }


	/* ------------------------------------------------------------------------ */
    /* Camera-Images */

    /**
     * Allow camera images.
     *
     * @return LeapMotion
     */
    public LeapMotion allowImages() {
        if (!this.controller.isPolicySet(Controller.PolicyFlag.POLICY_IMAGES)) {
            this.controller.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);
        }
        return this;
    }

    /**
     * Allow camera images.
     *
     * @return LeapMotion
     * @deprecated
     */
    public LeapMotion withCameraImages() {
        this.log("'withCameraImages()' is deprecated. Please use 'allowImages()'.");
        return this.allowImages();
    }

    /**
     * Disallow camera images.
     *
     * @return LeapMotion
     */
    public LeapMotion disallowImages() {
        if (this.controller.isPolicySet(Controller.PolicyFlag.POLICY_IMAGES)) {
            this.controller.clearPolicy(Controller.PolicyFlag.POLICY_IMAGES);
        }
        return this;
    }

    /**
     * Disallow camera images.
     *
     * @return LeapMotion
     * @deprecated
     */
    public LeapMotion withoutCameraImages() {
        this.log("'withoutCameraImages()' is deprecated. Please use 'disallowImages()'.");
        return this.disallowImages();
    }

    /**
     * Are there any raw images available?
     *
     * @return Are there any raw images available?
     */
    public boolean hasImages() {
        if (this.controller.isPolicySet(Controller.PolicyFlag.POLICY_IMAGES)) {
            if (!this.controller.frame().images().isEmpty()) {
                return true;
            }
        } else {
            this.allowImages();
        }
        return false;
    }

    /**
     * Get all raw camera images.
     *
     * @return List of images
     */
    public ArrayList<Image> getImages() {
        this.images.clear();
        if (this.hasImages()) {
            for (com.leapmotion.leap.Image _image : this.controller.frame().images()) {
                if (_image.isValid()) {
                    images.add(new Image(this.parent, this, _image));
                }
            }
        }
        return this.images;
    }

	
	/* ------------------------------------------------------------------------ */
    /* Optimized HMD */

    /**
     * Allow optimized tracking for head mounted displays.
     *
     * @return LeapMotion
     */
    public LeapMotion allowHdm() {
        if (!this.controller.isPolicySet(Controller.PolicyFlag.POLICY_OPTIMIZE_HMD)) {
            this.controller.setPolicy(Controller.PolicyFlag.POLICY_OPTIMIZE_HMD);
        }
        return this;
    }

    /**
     * Allow optimized tracking for head mounted displays.
     *
     * @return LeapMotion
     * @deprecated
     */
    public LeapMotion withOptimizedHdm() {
        this.log("'withOptimizedHdm()' is deprecated. Please use 'allowHdm()'.");
        return this.allowHdm();
    }

    /**
     * Disallow optimized tracking for head mounted displays.
     *
     * @return LeapMotion
     */
    public LeapMotion disallowHdm() {
        if (this.controller.isPolicySet(Controller.PolicyFlag.POLICY_OPTIMIZE_HMD)) {
            this.controller.clearPolicy(Controller.PolicyFlag.POLICY_OPTIMIZE_HMD);
        }
        return this;
    }

    /**
     * Disallow optimized tracking for head mounted displays.
     *
     * @return LeapMotion
     * @deprecated
     */
    public LeapMotion withoutOptimizedHdm() {
        this.log("'withoutOptimizedHdm()' is deprecated. Please use 'disallowHdm()'.");
        return this.disallowHdm();
    }

	
	/* ------------------------------------------------------------------------ */
    /* Gesture-Recognition */

    /**
     * Activate gesture recognition.
     *
     * @param names List of gestures (e.g. "swipe, circle, screen_tap, key_tap")
     * @return LeapMotion
     */
    public LeapMotion withGestures(String names) {
        names = names.trim().toUpperCase();
        List<String> gestures = Arrays.asList(names.split("\\s*,\\s*"));
        this.recognition = false;
        for (String gesture : gestures) {
            gesture = "TYPE_" + gesture;
            this.recognition = true;
            switch (com.leapmotion.leap.Gesture.Type.valueOf(gesture)) {
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
        if (this.recognition) {
            this.parent.registerMethod("pre", this);
        } else {
            this.parent.unregisterMethod("pre", this);
        }
        return this;
    }

    public LeapMotion allowGestures(String names) {
        return this.withGestures(names);
    }

    public LeapMotion withGestures() {
        return this.withGestures("swipe, circle, screen_tap, key_tap");
    }

    public LeapMotion allowGestures() {
        return this.withGestures("swipe, circle, screen_tap, key_tap");
    }

    /**
     * Deactivate gesture recognition.
     *
     * @return LeapMotion
     */
    public LeapMotion withoutGestures() {
        this.parent.unregisterMethod("pre", this);
        this.recognition = false;
        return this;
    }

    /**
     * Run the recognizer before sketch drawing.
     */
    public void pre() {
        this.check();
    }

    /**
     * Remove listener before destroying sketch.
     */
    public void dispose() {
        this.controller.removeListener(this.listener);
    }

    /**
     * Check available gestures.
     */
    private void check() {
        if (this.isConnected() && this.recognition) {
            for (com.leapmotion.leap.Gesture _g : this.frame.gestures(this.lastFrame)) {
                if (_g.isValid()) {
                    int state = 2;
                    switch (_g.type()) {
                        case TYPE_CIRCLE:
                            if (_g.state() == State.STATE_START) {
                                state = 1;
                            } else if (_g.state() == State.STATE_STOP) {
                                state = 3;
                            }
                            dispatch("leapOnCircleGesture",
                                    de.voidplus.leapmotion.CircleGesture.class, int.class,
                                    new de.voidplus.leapmotion.CircleGesture(
                                            this.parent,
                                            this, _g
                                    ),
                                    state
                            );

//                            try {
//                                this.parent.getClass().getMethod(
//                                        "leapOnCircleGesture",
//                                        de.voidplus.leapmotion.CircleGesture.class,
//                                        int.class
//                                ).invoke(
//                                        this.parent,
//                                        new de.voidplus.leapmotion.CircleGesture(
//                                                this.parent,
//                                                this, _g
//                                        ),
//                                        state
//                                );
//                            } catch (Exception e) {
//                                // e.printStackTrace();
//                            }
                            break;
                        case TYPE_SWIPE:
                            if (_g.state() == State.STATE_START) {
                                state = 1;
                            } else if (_g.state() == State.STATE_STOP) {
                                state = 3;
                            }
                            dispatch("leapOnSwipeGesture",
                                    de.voidplus.leapmotion.SwipeGesture.class, int.class,
                                    new de.voidplus.leapmotion.SwipeGesture(
                                            this.parent,
                                            this, _g
                                    ),
                                    state
                            );
//                            try {
//                                this.parent.getClass().getMethod(
//                                        "leapOnSwipeGesture",
//                                        de.voidplus.leapmotion.SwipeGesture.class,
//                                        int.class
//                                ).invoke(
//                                        this.parent,
//                                        new de.voidplus.leapmotion.SwipeGesture(
//                                                this.parent,
//                                                this, _g
//                                        ),
//                                        state
//                                );
//                            } catch (Exception e) {
//                                // e.printStackTrace();
//                            }
                            break;
                        case TYPE_SCREEN_TAP:
                            if (_g.state() == State.STATE_STOP) {
                                dispatch("leapOnScreenTapGesture",
                                        de.voidplus.leapmotion.ScreenTapGesture.class,
                                        new de.voidplus.leapmotion.ScreenTapGesture(
                                                this.parent,
                                                this, _g
                                        )
                                );
//                                try {
//                                    this.parent.getClass().getMethod(
//                                            "leapOnScreenTapGesture",
//                                            de.voidplus.leapmotion.ScreenTapGesture.class
//                                    ).invoke(
//                                            this.parent,
//                                            new de.voidplus.leapmotion.ScreenTapGesture(
//                                                    this.parent,
//                                                    this, _g
//                                            )
//                                    );
//                                } catch (Exception e) {
//                                    // e.printStackTrace();
//                                }
                            }
                            break;
                        case TYPE_KEY_TAP:
                            if (_g.state() == State.STATE_STOP) {
                                dispatch("leapOnKeyTapGesture",
                                        de.voidplus.leapmotion.KeyTapGesture.class,
                                        new de.voidplus.leapmotion.KeyTapGesture(
                                                this.parent,
                                                this, _g
                                        )
                                );
//                                try {
//                                    this.parent.getClass().getMethod(
//                                            "leapOnKeyTapGesture",
//                                            de.voidplus.leapmotion.KeyTapGesture.class
//                                    ).invoke(
//                                            this.parent,
//                                            new de.voidplus.leapmotion.KeyTapGesture(
//                                                    this.parent,
//                                                    this, _g
//                                            )
//                                    );
//                                } catch (Exception e) {
//                                    // e.printStackTrace();
//                                }
                            }
                            break;
                        default:
                            this.log("Unknown gesture type.");
                            break;
                    }
                }
            }
            this.lastFrame = this.frame;
        }
    }

    /**
     * Set Gesture.Circle.MinRadius (Default: 5.0 mm)
     *
     * @param mm Millimeters
     * @return LeapMotion
     */
    public LeapMotion setGestureCircleMinRadius(float mm) {
        return this.setConfig("Gesture.Circle.MinRadius", mm);
    }

    /**
     * Set Gesture.Circle.MinArc (Default: 270 degrees)
     *
     * @param degrees Degrees
     * @return LeapMotion
     */
    public LeapMotion setGestureCircleMinArc(float degrees) {
        return this.setConfig("Gesture.Circle.MinArc", PApplet.radians(degrees));
    }

    /**
     * Set Gesture.Swipe.MinLength (Default: 150 mm)
     *
     * @param mm Millimeters
     * @return LeapMotion
     */
    public LeapMotion setGestureSwipeMinLength(float mm) {
        return this.setConfig("Gesture.Swipe.MinLength", mm);
    }

    /**
     * Set Gesture.Swipe.MinVelocity (Default: 1000 mm/s)
     *
     * @param mms Millimeters per second
     * @return LeapMotion
     */
    public LeapMotion setGestureSwipeMinVelocity(float mms) {
        return this.setConfig("Gesture.Swipe.MinVelocity", mms);
    }

    /**
     * Set Gesture.KeyTap.MinDownVelocity (Default: 50 mm/s)
     *
     * @param mms Millimeters per second
     * @return LeapMotion
     */
    public LeapMotion setGestureKeyTapMinDownVelocity(float mms) {
        return this.setConfig("Gesture.KeyTap.MinDownVelocity", mms);
    }

    /**
     * Set Gesture.KeyTap.HistorySeconds (Default: 0.1 s)
     *
     * @param s Seconds
     * @return LeapMotion
     */
    public LeapMotion setGestureKeyTapHistorySeconds(float s) {
        return this.setConfig("Gesture.KeyTap.HistorySeconds", s);
    }

    /**
     * Set Gesture.KeyTap.MinDistance (Default: 3.0 mm)
     *
     * @param mm Millimeters
     * @return LeapMotion
     */
    public LeapMotion setGestureKeyTapMinDistance(float mm) {
        return this.setConfig("Gesture.KeyTap.MinDistance", mm);
    }

    /**
     * Set Gesture.ScreenTap.MinForwardVelocity (Default: 50 mm/s)
     *
     * @param mms Millimeters per second
     * @return LeapMotion
     */
    public LeapMotion setGestureScreenTapMinForwardVelocity(float mms) {
        return this.setConfig("Gesture.ScreenTap.MinForwardVelocity", mms);
    }

    /**
     * Set Gesture.ScreenTap.HistorySeconds (Default: 0.1 s)
     *
     * @param s Seconds
     * @return LeapMotion
     */
    public LeapMotion setGestureScreenTapHistorySeconds(float s) {
        return this.setConfig("Gesture.ScreenTap.HistorySeconds", s);
    }

    /**
     * Set Gesture.ScreenTap.MinDistance (Default: 5.0 mm)
     *
     * @param mm Millimeters
     * @return LeapMotion
     */
    public LeapMotion setGestureScreenTapMinDistance(float mm) {
        return this.setConfig("Gesture.ScreenTap.MinDistance", mm);
    }

	
	/* ------------------------------------------------------------------------ */
    /* Configuration */

    /**
     * Set configuration parameters.
     *
     * @param keyString Key
     * @param value     Value
     * @return LeapMotion
     */
    public LeapMotion setConfig(String keyString, int value) {
        if (this.controller.isConnected()) {
            if (this.controller.config().setInt32(keyString, value)) {
                this.controller.config().save();
            }
        }
        this.controller.config().save();
        return this;
    }

    /**
     * Set configuration parameters.
     *
     * @param keyString Key
     * @param value     Value
     * @return LeapMotion
     */
    public LeapMotion setConfig(String keyString, float value) {
        if (this.controller.isConnected()) {
            if (this.controller.config().setFloat(keyString, value)) {
                this.controller.config().save();
            }
        }
        return this;
    }

    /**
     * Set configuration parameters.
     *
     * @param keyString Key
     * @param value     Value
     * @return LeapMotion
     */
    public LeapMotion setConfig(String keyString, boolean value) {
        if (this.controller.isConnected()) {
            if (this.controller.config().setBool(keyString, value)) {
                this.controller.config().save();
            }
        }
        return this;
    }

    /**
     * Set configuration parameters.
     *
     * @param keyString Key
     * @param value     Value
     * @return LeapMotion
     */
    public LeapMotion setConfig(String keyString, String value) {
        if (this.controller.isConnected()) {
            if (this.controller.config().setString(keyString, value)) {
                this.controller.config().save();
            }
        }
        return this;
    }

	
	/* ------------------------------------------------------------------------ */
    /* Geometry */

    /**
     * Convert/map the Vector data to sketch PVector data with world settings.
     *
     * @param position Instance of class Vector
     * @return PVector  version of instance Vector
     */
    protected PVector map(Vector position) {

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
     *
     * @param position Instance of class Vector
     * @return PVector  version of instance Vector
     */
    protected PVector convert(Vector position) {
        return this.move(new PVector(
                position.getX(),
                position.getY(),
                position.getZ()
        ));
    }

    /**
     * Move all elements.
     *
     * @param position Shift vector
     * @return New position
     */
    private PVector move(PVector position) {
        if (this.origin != null) {
            position.x += origin.x;
            position.y += origin.y;
            position.z += origin.z;
        }
        return position;
    }

	
	/* ------------------------------------------------------------------------ */
    /* Library */

    /**
     * Method to invoke the callbacks.
     *
     * @param method Name of the callback
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
            if (success) {
                this.log(String.format("Callback %s();", method));
            }
        }
    }

    /**
     * Method to invoke callbacks with one argument.
     *
     * @param method Name of the callback
     * @param clazz  Class of argument
     * @param obj    Content of argument
     */
    private void dispatch(final String method, Class clazz, Object obj) {
        boolean success = false;
        try {
            this.parent.getClass().getMethod(
                    method,
                    clazz
            ).invoke(
                    this.parent,
                    obj
            );
            success = true;
        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            if (success) {
                this.log(String.format("Callback %s();", method));
            }
        }
    }

    /**
     * Method to invoke callbacks with two arguments.
     *
     * @param method  Name of the callback
     * @param clazz_1 Class of first argument
     * @param clazz_2 Class of second argument
     * @param obj_1   Content of first argument
     * @param obj_2   Content of second argument
     */
    private void dispatch(final String method,
                          Class clazz_1, Class clazz_2,
                          Object obj_1, Object obj_2
    ) {
        boolean success = false;
        try {
            this.parent.getClass().getMethod(
                    method,
                    clazz_1,
                    clazz_2
            ).invoke(
                    this.parent,
                    obj_1,
                    obj_2
            );
            success = true;
        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            if (success) {
                this.log(String.format("Callback %s();", method));
            }
        }
    }

    /**
     * Get the version of the library.
     *
     * @return Version number of the Processing library
     */
    public String getVersion() {
        return LeapMotion.VERSION;
    }

    /**
     * Get the used version of the SDK.
     *
     * @return Version number of the dependent SDK libraries
     */
    public String getVersionSDK() {
        return LeapMotion.SDK_VERSION;
    }

	
	/* ------------------------------------------------------------------------ */
    /* Helpers */

    /**
     * Print debug information to the console.
     *
     * @param verbose Activate or deactivate the mode
     * @return LeapMotion
     */
    public LeapMotion setVerbose(boolean verbose) {
        this.verbose = verbose;
        return this;
    }

    /**
     * Log message to console.
     *
     * @param msg Message
     * @param ns  Namespace
     */
    private void println(String msg, boolean ns) {
        if (ns) {
            PApplet.println(String.format("# %s: %s", LeapMotion.NAME, msg));
        } else {
            PApplet.println(msg);
        }
    }

    /**
     * Print message to console.
     *
     * @param msg Message
     */
    private void println(String msg) {
        this.println(msg, true);
    }

    /**
     * Log message to console.
     *
     * @param msg Message
     */
    private void log(String msg) {
        if (this.verbose) {
            this.println(msg);
        }
    }

}