package de.voidplus.leapmotion;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;


public class Hand implements PConstants, RawAccess<com.leapmotion.leap.Hand> {

    protected PApplet parent;
    protected LeapMotion leap;
    private com.leapmotion.leap.Hand _hand;
    protected ArrayList<Finger> fingers;
    protected ArrayList<Finger> outstretchedFingers;
    protected ArrayList<Finger> outstretchedFingersByAngel;
    protected ArrayList<Tool> tools;

    public Hand(PApplet parent, LeapMotion leap, com.leapmotion.leap.Hand _hand) {
        this.parent = parent;
        this.leap = leap;
        this._hand = _hand;
        this.fingers = new ArrayList<Finger>();
        this.outstretchedFingers = new ArrayList<Finger>();
        this.outstretchedFingersByAngel = new ArrayList<Finger>();

        this.tools = new ArrayList<Tool>();
    }

    /**
     * Is it a valid Hand object?
     *
     * @return Is it a valid Hand object?
     */
    @Override
    public boolean isValid() {
        return this._hand.isValid();
    }

    /**
     * Get the raw instance of com.leapmotion.leap.Hand.
     *
     * @return Raw instance of com.leapmotion.leap.Hand
     */
    @Override
    public com.leapmotion.leap.Hand getRaw() {
        return this._hand;
    }


	/* ------------------------------------------------------------------------ */
    /* HAND */

    /**
     * Get the unique ID.
     *
     * @return ID
     */
    public int getId() {
        return this._hand.id();
    }

    /**
     * The center position of the palm in millimeters.
     *
     * @return Position
     */
    public PVector getPosition() {
        return this.leap.map(this._hand.palmPosition());
    }

    /**
     * Raw data of the center position.
     *
     * @return Raw position
     */
    public PVector getRawPosition() {
        return this.leap.convert(this._hand.palmPosition());
    }

    /**
     * The center position of the palm in millimeters.
     *
     * @return Position
     */
    public PVector getPalmPosition() {
        return this.getPosition();
    }

    /**
     * The stabilized center position of the palm in millimeters.
     *
     * @return Position
     */
    public PVector getStabilizedPosition() {
        return this.leap.map(this._hand.stabilizedPalmPosition());
    }

    /**
     * The stabilized center position of the palm in millimeters.
     *
     * @return Position
     */
    public PVector getStabilizedPalmPosition() {
        return this.getStabilizedPosition();
    }

    /**
     * Raw data of the stabilized center position.
     *
     * @return Position
     */
    public PVector getRawStabilizedPosition() {
        return this.leap.convert(this._hand.stabilizedPalmPosition());
    }

    /**
     * Raw data of the stabilized center position.
     *
     * @return Raw position
     */
    public PVector getRawStabilizedParlmPosition() {
        return this.getRawStabilizedPosition();
    }

    /**
     * The direction from the palm position toward the fingers.
     *
     * @return Direction
     */
    public PVector getDirection() {
        return new PVector(
            PApplet.degrees(this._hand.direction().roll()),
            PApplet.degrees(this._hand.direction().pitch()),
            PApplet.degrees(this._hand.direction().yaw())
        );
    }

    /**
     * Raw data of the direction from the palm position toward the fingers.
     *
     * @return Raw direction
     */
    public PVector getRawDirection() {
        return this.leap.convert(this._hand.direction());
    }

    /**
     * How confident we are with a given hand pose. The confidence level ranges between 0.0 and 1.0 inclusive.
     *
     * @return Confidence level ranges between 0.0 and 1.0
     */
    public float getConfidence() {
        return this._hand.confidence();
    }

    /**
     * Identifies whether this Hand is a left hand.
     *
     * @return True if the hand is identified as a left hand.
     */
    public boolean isLeft() {
        return this._hand.isLeft();
    }

    /**
     * Identifies whether this Hand is a right hand.
     *
     * @return True if the hand is identified as a right hand.
     */
    public boolean isRight() {
        return this._hand.isRight();
    }

    /**
     * The strength of a grab hand pose. The strength is zero for an open hand, and blends to 1.0 when a grabbing hand pose is recognized.
     *
     * @return A float value in the [0..1] range representing the holding strength of the pose.
     */
    public float getGrabStrength() {
        return this._hand.grabStrength();
    }

    /**
     * The holding strength of a pinch hand pose. The strength is zero for an open hand, and blends to 1.0 when a pinching hand pose is recognized. Pinching can be done between the thumb and any other finger of the same hand.
     *
     * @return A float value in the [0..1] range representing the holding strength of the pinch pose.
     */
    public float getPinchStrength() {
        return this._hand.pinchStrength();
    }

    /**
     * The duration of time this Hand has been visible to the Leap Motion Controller.
     *
     * @return Duration
     */
    public float getTimeVisible() {
        return this._hand.timeVisible();
    }

    /**
     * The member of the list that is farthest to the front within the standard Leap Motion frame of reference (i.e has the smallest Z coordinate).
     *
     * @return Single finger
     */
    public Finger getFrontFinger() {
        return new Finger(this.parent, this.leap, this._hand.fingers().frontmost());
    }

    /**
     * The member of the list that is farthest to the left within the standard Leap Motion frame of reference (i.e has the smallest X coordinate).
     *
     * @return Single finger
     */
    public Finger getLeftFinger() {
        return new Finger(this.parent, this.leap, this._hand.fingers().leftmost());
    }

    /**
     * The member of the list that is farthest to the right within the standard Leap Motion frame of reference (i.e has the largest X coordinate).
     *
     * @return Single finger
     */
    public Finger getRightFinger() {
        return new Finger(this.parent, this.leap, this._hand.fingers().rightmost());
    }

    /**
     * The member of the list that is farthest to the front within the standard Leap Motion frame of reference (i.e has the smallest Z coordinate).
     *
     * @return Single tool
     */
    public Tool getFrontTool() {
        return new Tool(this.parent, this.leap, this._hand.tools().frontmost());
    }

    /**
     * The member of the list that is farthest to the left within the standard Leap Motion frame of reference (i.e has the smallest X coordinate).
     *
     * @return Single tool
     */
    public Tool getLeftTool() {
        return new Tool(this.parent, this.leap, this._hand.tools().leftmost());
    }

    /**
     * The member of the list that is farthest to the right within the standard Leap Motion frame of reference (i.e has the largest X coordinate).
     *
     * @return Single tool
     */
    public Tool getRightTool() {
        return new Tool(this.parent, this.leap, this._hand.tools().rightmost());
    }

	
	/* ------------------------------------------------------------------------ */
    /* FLIGHT-DYNAMICS */

    /**
     * Get the angles of the hand (x=roll, y=pich, z=yaw).
     *
     * @return Angles
     */
    public PVector getDynamics() {
        return new PVector(this.getRoll(), this.getPitch(), this.getYaw());
    }

    /**
     * Get the roll angle (x).
     *
     * @return Single angle
     */
    public float getRoll() {
        return -PApplet.degrees(this._hand.palmNormal().roll());
    }

    /**
     * Get the pitch angle (y).
     *
     * @return Single angle
     */
    public float getPitch() {
        return PApplet.degrees(this._hand.direction().pitch());
    }

    /**
     * Get the yaw angle (z).
     *
     * @return Single angle
     */
    public float getYaw() {
        return PApplet.degrees(this._hand.direction().yaw());
    }

	
	/* ------------------------------------------------------------------------ */
    /* SPHERE */

    /**
     * The center of a sphere fit to the curvature of this hand.
     *
     * @return Position
     */
    public PVector getSpherePosition() {
        return this.leap.map(this._hand.sphereCenter());
    }

    /**
     * Raw data of the center of a sphere fit to the curvature of this _hand.
     *
     * @return Raw position
     */
    public PVector getRawSpherePosition() {
        return this.leap.convert(this._hand.sphereCenter());
    }

    /**
     * The radius of a sphere fit to the curvature of this _hand.
     *
     * @return Radius
     */
    public float getSphereRadius() {
        return this._hand.sphereRadius();
    }


	/* ------------------------------------------------------------------------ */
	/* FINGERS */

    /**
     * Get all detected fingers of the _hand.
     *
     * @return List of fingers
     */
    public ArrayList<Finger> getFingers() {
        fingers.clear();
        if (this.hasFingers()) {
            for (com.leapmotion.leap.Finger finger : this._hand.fingers()) {
                this.fingers.add(new Finger(this.parent, this.leap, finger));
            }
        }
        return fingers;
    }

    /**
     * Get all outstretched fingers.
     *
     * @return List of fingers
     */
    public ArrayList<Finger> getOutstretchedFingers() {
        this.outstretchedFingers.clear();
        if (!this._hand.fingers().extended().isEmpty()) {
            for (com.leapmotion.leap.Finger finger : this._hand.fingers().extended()) {
                this.outstretchedFingers.add(new Finger(this.parent, this.leap, finger));
            }
        }
        return this.outstretchedFingers;
    }

    /**
     * Get all outstretched fingers.
     *
     * @return List of fingers
     */
    public ArrayList<Finger> getOutstretchedFingers(int ignoreForBackwardsCompatibility) {
        return this.getOutstretchedFingers();
    }

    /**
     * Get all outstretched fingers by angle.
     *
     * @param similarity Minimum value of similarity
     * @return List of fingers
     */
    public ArrayList<Finger> getOutstretchedFingersByAngel(int similarity) {
        this.outstretchedFingersByAngel.clear();
        if (this.hasFingers()) {
            for (com.leapmotion.leap.Finger finger : this._hand.fingers()) {
                if (finger.isValid()) {
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
     *
     * @return List of fingers
     */
    public ArrayList<Finger> getOutstretchedFingersByAngel() {
        return this.getOutstretchedFingersByAngel(75);
    }

    /**
     * Get all raw outstrechted fingers.
     *
     * @return List of fingers
     */
    public ArrayList<Finger> getRawOutstrechtedFingers() {
        this.outstretchedFingers.clear();

        return this.outstretchedFingers;
    }

    /**
     * Is there any finger?
     *
     * @return Is there any finger?
     */
    public boolean hasFingers() {
        return this.leap.isConnected() && !this._hand.fingers().isEmpty();
    }

    /**
     * Get the number of fingers.
     *
     * @return Number of fingers
     */
    public int countFingers() {
        if (this.leap.isConnected()) {
            return this._hand.fingers().count();
        }
        return 0;
    }

    /**
     * Get a specific finger by id.
     *
     * @param type 0:TYPE_THUMB, 1:TYPE_INDEX, 2:TYPE_MIDDLE, 3:TYPE_RING, 4:TYPE_PINKY
     * @return Single finger or null
     */
    public Finger getFinger(int type) {
        for (Finger finger : this.getFingers()) {
            if (finger.getType() == type) {
                return finger;
            }
        }
        return null;
    }

    /**
     * Get a specific finger by name.
     *
     * @param name "thumb", "index", "middle", "ring", "pinky"
     * @return Single finger or null
     */
    public Finger getFinger(String name) {
        name = name.toLowerCase();
        if (name.equals("thumb")) {
            return this.getFinger(0);
        } else if (name.equals("index")) {
            return this.getFinger(1);
        } else if (name.equals("middle")) {
            return this.getFinger(2);
        } else if (name.equals("ring")) {
            return this.getFinger(3);
        } else if (name.equals("pinky")) {
            return this.getFinger(4);
        }
        return null;
    }

    /**
     * Get the thumb finger.
     *
     * @return Single finger or null
     */
    public Finger getThumb() {
        return this.getFinger(0);
    }

    /**
     * Get the index finger.
     *
     * @return Single finger or null
     */
    public Finger getIndexFinger() {
        return this.getFinger(1);
    }

    /**
     * Get the middle finger.
     *
     * @return Single finger or null
     */
    public Finger getMiddleFinger() {
        return this.getFinger(2);
    }

    /**
     * Get the ring finger.
     *
     * @return Single finger or null
     */
    public Finger getRingFinger() {
        return this.getFinger(3);
    }

    /**
     * Get the pinky/little finger.
     *
     * @return Single finger or null
     */
    public Finger getPinkyFinger() {
        return this.getFinger(4);
    }

	
	/* ------------------------------------------------------------------------ */
	/* TOOLS */

    /**
     * Get all detected tools of the hand.
     *
     * @return List of tools
     */
    public ArrayList<Tool> getTools() {
        tools.clear();
        if (this.hasTools()) {
            for (com.leapmotion.leap.Tool tool : this._hand.tools()) {
                tools.add(new Tool(this.parent, this.leap, tool));
            }
        }
        return tools;
    }

    /**
     * Is there any tool?
     *
     * @return Is there any tool?
     */
    public boolean hasTools() {
        if (this.leap.isConnected()) {
            return !this._hand.tools().isEmpty();
        }
        return false;
    }

    /**
     * Get the number of tools.
     *
     * @return Number of tools
     */
    public int countTools() {
        if (this.leap.isConnected()) {
            return this._hand.tools().count();
        }
        return 0;
    }
	
	
	/* ------------------------------------------------------------------------ */
	/* ARM */

    /**
     * Get arm of the hand.
     *
     * @return Single arm
     */
    public Arm getArm() {
        return new Arm(this.parent, this.leap, this._hand.arm());
    }

    /**
     * Is there any arm?
     *
     * @return Is there any arm?
     */
    public boolean hasArm() {
        return this._hand.arm().isValid();
    }
	
	
	/* ------------------------------------------------------------------------ */
	/* DRAWING */

    /**
     * Draw the hand with all details.
     *
     * @param radius The radius of the ellipse (2D) or sphere (3D).
     */
    public void draw(float radius) {
        this.parent.noStroke();
        this.parent.fill(0);

        PVector position = this.getPosition();
        if (this.leap.is2D) {
            this.parent.ellipseMode(PConstants.CENTER);
            this.parent.ellipse(position.x, position.y, radius, radius);
        } else {
            this.parent.pushMatrix();
            this.parent.translate(position.x, position.y, position.z);
            this.parent.sphereDetail(20);
            this.parent.sphere(radius);
            this.parent.popMatrix();
        }

        // Is there any arm?
        if (this.hasArm()) {
            this.getArm().draw();
        }

        // Are there any fingers?
        if (this.hasFingers()) {
            if (this.countFingers() == 5) {
                this.parent.stroke(0, 35);
                this.parent.noFill();
                PVector lastJointOfThumb = this.getThumb().getProximalBone().getPrevJoint();
                PVector lastJointOfIndex = this.getIndexFinger().getMetacarpalBone().getPrevJoint();
                PVector lastJointOfMiddle = this.getMiddleFinger().getMetacarpalBone().getPrevJoint();
                PVector lastJointOfRing = this.getRingFinger().getMetacarpalBone().getPrevJoint();
                PVector lastJointOfPinky = this.getPinkyFinger().getMetacarpalBone().getPrevJoint();

                this.parent.beginShape();
                if (this.leap.is2D) {
                    this.parent.vertex(lastJointOfThumb.x, lastJointOfThumb.y);
                    this.parent.vertex(lastJointOfIndex.x, lastJointOfIndex.y);
                    this.parent.vertex(lastJointOfMiddle.x, lastJointOfMiddle.y);
                    this.parent.vertex(lastJointOfRing.x, lastJointOfRing.y);
                    this.parent.vertex(lastJointOfPinky.x, lastJointOfPinky.y);
                } else {
                    this.parent.vertex(lastJointOfThumb.x, lastJointOfThumb.y, lastJointOfThumb.z);
                    this.parent.vertex(lastJointOfIndex.x, lastJointOfIndex.y, lastJointOfIndex.z);
                    this.parent.vertex(lastJointOfMiddle.x, lastJointOfMiddle.y, lastJointOfMiddle.z);
                    this.parent.vertex(lastJointOfRing.x, lastJointOfRing.y, lastJointOfRing.z);
                    this.parent.vertex(lastJointOfPinky.x, lastJointOfPinky.y, lastJointOfPinky.z);
                }
                this.parent.endShape(PConstants.OPEN);
            }
            for (Finger finger : this.getFingers()) {
                finger.draw();
            }
        }

        // Basic information
        this.parent.stroke(0);
        String information = String.format(
            "id: %d" +
            "\nconfidence: %.2f" +
            "\nhand side: %s" +
            "\noutstretched fingers: %d" +
            "\ntime visible: %.2f s" +
            "\npinch strength: %.2f" +
            "\ngrab strength: %.2f",
            this.getId(),
            this.getConfidence(),
            this.isLeft() ? "left" : "right",
            this.getOutstretchedFingers().size(),
            this.getTimeVisible(),
            this.getPinchStrength(),
            this.getGrabStrength());
        if (this.leap.is2D) {
            this.parent.text(information, position.x - 30, position.y - 150);
        } else {
            this.parent.text(information, position.x - 30, position.y - 150, position.z);
        }
    }

    public void draw() {
        this.draw(5);
    }

    /**
     * Draw all fingers of the hand.
     *
     * @param radius The radius of the ellipse (2D) or sphere (3D).
     */
    public void drawFingers(int radius) {
        if (this.hasFingers()) {
            for (Finger finger : this.fingers) {
                finger.draw(radius);
            }
        }
    }

    public void drawFingers() {
        this.drawFingers(3);
    }

    /**
     * Draw the sphere of the hand.
     */
    public void drawSphere() {
        this.parent.stroke(0, 10);
        this.parent.noFill();

        PVector position = this.getSpherePosition();
        float radius = this.getSphereRadius();

        if (this.leap.is2D) {
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

}