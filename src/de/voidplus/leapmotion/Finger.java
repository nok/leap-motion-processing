package de.voidplus.leapmotion;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;


public class Finger extends Pointable implements RawAccess<com.leapmotion.leap.Finger> {

    private com.leapmotion.leap.Finger _finger;

    public Finger(PApplet parent, LeapMotion leap, com.leapmotion.leap.Finger _finger) {
        super(parent, leap, (com.leapmotion.leap.Pointable) _finger);
        this._finger = _finger;
    }

    /**
     * Is it a valid Finger object?
     *
     * @return Is it a valid Finger object?
     */
    @Override
    public boolean isValid() {
        return this._finger.isValid();
    }

    /**
     * Get the raw instance of com.leapmotion.leap.Finger.
     *
     * @return Raw instance of com.leapmotion.leap.Finger
     */
    @Override
    public com.leapmotion.leap.Finger getRaw() {
        return this._finger;
    }

    /**
     * The finger tip position in millimeters.
     *
     * @return Position
     */
    public PVector getPositionOfJointTip() {
        return this.leap.map(this._finger.jointPosition(
                com.leapmotion.leap.Finger.Joint.JOINT_TIP)
        );
    }

    /**
     * The mcp joint position in millimeters.
     *
     * @return Position
     */
    public PVector getPositionOfJointMcp() {
        return this.leap.map(this._finger.jointPosition(
                com.leapmotion.leap.Finger.Joint.JOINT_MCP)
        );
    }

    /**
     * The pip joint position in millimeters.
     *
     * @return Position
     */
    public PVector getPositionOfJointPip() {
        return this.leap.map(this._finger.jointPosition(
                com.leapmotion.leap.Finger.Joint.JOINT_PIP)
        );
    }

    /**
     * The dip joint position in millimeters.
     *
     * @return Position
     */
    public PVector getPositionOfJointDip() {
        return this.leap.map(this._finger.jointPosition(
                com.leapmotion.leap.Finger.Joint.JOINT_DIP)
        );
    }

    /**
     * Raw data of the finger tip position.
     *
     * @return Raw position
     */
    public PVector getRawPositionOfJointTip() {
        return this.leap.convert(this._finger.jointPosition(
                com.leapmotion.leap.Finger.Joint.JOINT_TIP)
        );
    }

    /**
     * Raw data of the mcp joint position.
     *
     * @return Raw position
     */
    public PVector getRawPositionOfJointMcp() {
        return this.leap.convert(this._finger.jointPosition(
                com.leapmotion.leap.Finger.Joint.JOINT_MCP)
        );
    }

    /**
     * Raw data of the pip joint position.
     *
     * @return Raw position
     */
    public PVector getRawPositionOfJointPip() {
        return this.leap.convert(this._finger.jointPosition(
                com.leapmotion.leap.Finger.Joint.JOINT_PIP)
        );
    }

    /**
     * Raw data of the dip joint position.
     *
     * @return Raw position
     */
    public PVector getRawPositionOfJointDip() {
        return this.leap.convert(this._finger.jointPosition(
                com.leapmotion.leap.Finger.Joint.JOINT_DIP)
        );
    }

    /**
     * Get the index of fingertype (0-4, 0=thumb, 1=index, 2=middle, 3=ring, 4=pinky).
     *
     * @return Type of index
     */
    public int getType() {
        com.leapmotion.leap.Finger.Type type = this._finger.type();
        switch (type) {
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

    /**
     * Get a specific bone by name.
     *
     * @param name "distal", "intermediate", "proximal", "metacarpal"
     * @return Single bone or null
     */
    public Bone getBone(String name) {
        name = name.toLowerCase();
        if (name.equals("distal")) {
            return new Bone(parent, leap, this._finger.bone(
                    com.leapmotion.leap.Bone.Type.TYPE_DISTAL));
        } else if (name.equals("intermediate")) {
            return new Bone(parent, leap, this._finger.bone(
                    com.leapmotion.leap.Bone.Type.TYPE_INTERMEDIATE));
        } else if (name.equals("proximal")) {
            return new Bone(parent, leap, this._finger.bone(
                    com.leapmotion.leap.Bone.Type.TYPE_PROXIMAL));
        } else if (name.equals("metacarpal")) {
            return new Bone(parent, leap, this._finger.bone(
                    com.leapmotion.leap.Bone.Type.TYPE_METACARPAL));
        }
        return null;
    }

    /**
     * Get a specific bone by numeric index.
     *
     * @param type (0-3, 0=distal, 1=intermediate, 2=proximal, 3=metacarpal).
     * @return Single bone or null
     */
    public Bone getBone(int type) {
        switch (type) {
            case 0:
                return new Bone(parent, leap, this._finger.bone(
                        com.leapmotion.leap.Bone.Type.TYPE_DISTAL));
            case 1:
                return new Bone(parent, leap, this._finger.bone(
                        com.leapmotion.leap.Bone.Type.TYPE_INTERMEDIATE));
            case 2:
                return new Bone(parent, leap, this._finger.bone(
                        com.leapmotion.leap.Bone.Type.TYPE_PROXIMAL));
            case 3:
                return new Bone(parent, leap, this._finger.bone(
                        com.leapmotion.leap.Bone.Type.TYPE_METACARPAL));
        }
        return null;
    }

    /**
     * Get the distal bone of the finger.
     *
     * @return Single bone
     */
    public Bone getDistalBone() {
        return new Bone(parent, leap, this._finger.bone(
                com.leapmotion.leap.Bone.Type.TYPE_DISTAL));
    }

    /**
     * Get the intermediate bone of the finger.
     *
     * @return Single bone
     */
    public Bone getIntermediateBone() {
        return new Bone(parent, leap, this._finger.bone(
                com.leapmotion.leap.Bone.Type.TYPE_INTERMEDIATE));
    }

    /**
     * Get the metacarpal bone of the finger.
     *
     * @return Single bone
     */
    public Bone getMetacarpalBone() {
        return new Bone(parent, leap, this._finger.bone(
                com.leapmotion.leap.Bone.Type.TYPE_METACARPAL));
    }

    /**
     * Get the proximal bone of the finger.
     *
     * @return Single bone
     */
    public Bone getProximalBone() {
        return new Bone(parent, leap, this._finger.bone(
                com.leapmotion.leap.Bone.Type.TYPE_PROXIMAL));
    }


	/* ------------------------------------------------------------------------ */
    /* DRAWING */

    /**
     * Draw the lines between joints.
     *
     * @param pre Activate or deactivate predefined colors
     */
    public void drawLines(boolean pre) {
        if (pre) {
            this.parent.stroke(0);
            this.parent.noFill();
        }

        PVector tip = this.getPositionOfJointTip();
        PVector mcp = this.getPositionOfJointMcp();
        PVector pip = this.getPositionOfJointPip();
        PVector dip = this.getPositionOfJointDip();

        this.parent.beginShape();
        if (this.parent.g.is2D()) {
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
        this.parent.endShape(PConstants.OPEN);
    }

    /**
     * Draw the lines between joints.
     */
    public void drawLines() {
        this.drawLines(true);
    }

    /**
     * Draw all bones of a finger.
     *
     * @param pre Activate or deactivate predefined colors
     */
    public void drawBones(boolean pre) {
        this.getBone(0).draw(pre);
        this.getBone(1).draw(pre);
        this.getBone(2).draw(pre);
        if (this.getType() != 0) { // !thumb
            this.getBone(3).draw(pre);
        }
    }

    /**
     * Draw all bones of a finger.
     */
    public void drawBones() {
        this.drawBones(true);
    }

    /**
     * Draw all joints and bones of a finger.
     *
     * @param radius Radius
     * @param pre    Activate or deactivate predefined colors
     */
    public void drawJoints(float radius, boolean pre) {
        if (pre) {
            this.parent.noStroke();
            this.parent.fill(0);
        }

        PVector tip = this.getPositionOfJointTip();
        PVector mcp = this.getPositionOfJointMcp();
        PVector pip = this.getPositionOfJointPip();
        PVector dip = this.getPositionOfJointDip();

        if (this.parent.g.is2D()) {
            this.parent.ellipseMode(PConstants.CENTER);
            this.parent.ellipse(tip.x, tip.y, radius, radius);
            this.parent.ellipse(pip.x, pip.y, radius, radius);
            this.parent.ellipse(dip.x, dip.y, radius, radius);
            if (this.getType() != 0) {
                this.parent.ellipse(mcp.x, mcp.y, radius, radius);
            }
        } else {
            this.parent.sphereDetail(20);
            this.parent.pushMatrix();
                this.parent.translate(tip.x, tip.y, tip.z);
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
            if (this.getType() != 0) {
                this.parent.pushMatrix();
                    this.parent.translate(mcp.x, mcp.y, mcp.z);
                    this.parent.sphere(radius);
                this.parent.popMatrix();
            }
        }
    }

    /**
     * Draw all joints of finger.
     *
     * @param radius Radius
     */
    public void drawJoints(int radius) {
        this.drawJoints(radius, true);
    }

    /**
     * Draw all joints of a finger.
     *
     * @param pre Activate or deactivate predefined colors
     */
    public void drawJoints(boolean pre) {
        this.drawJoints(3, pre);
    }

    /**
     * Draw all joints of a finger.
     */
    public void drawJoints() {
        this.drawJoints(3, true);
    }

    /**
     * Draw all joints and bones of a finger.
     *
     * @param radius Radius
     * @param pre    Activate or deactivate predefined colors
     */
    public void draw(float radius, boolean pre) {
        this.drawBones(pre);
        this.drawJoints(radius, pre);
    }

    /**
     * Draw all joints and bones of a finger.
     *
     * @param radius Radius
     */
    public void draw(int radius) {
        this.draw(radius, true);
    }

    /**
     * Draw all joints and bones of a finger.
     *
     * @param pre Activate or deactivate predefined colors
     */
    public void draw(boolean pre) {
        this.draw(3, pre);
    }

    /**
     * Draw all joints and bones of a finger.
     */
    public void draw() {
        this.draw(3, true);
    }

}