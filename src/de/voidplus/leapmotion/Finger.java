package de.voidplus.leapmotion;

import com.leapmotion.leap.Finger.Joint;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;


public class Finger extends Pointable implements RawAccess<com.leapmotion.leap.Finger> {

    protected Joint Joint;
    private com.leapmotion.leap.Finger _finger;

    public Finger(PApplet parent, LeapMotion leap, com.leapmotion.leap.Finger _finger) {
        super(parent, leap, (com.leapmotion.leap.Pointable) _finger);
        this._finger = _finger;
    }

    /**
     * Reports whether it's a valid Finger object.
     *
     * @return
     */
    @Override
    public boolean isValid() {
        return this._finger.isValid();
    }

    /**
     * Get the raw instance of com.leapmotion.leap.Finger.
     *
     * @return
     */
    @Override
    public com.leapmotion.leap.Finger getRaw() {
        return this._finger;
    }

    /**
     * The _finger tip position in millimeters.
     *
     * @return
     */
    public PVector getPositionOfJointTip() {
        return this.leap.map(this._finger.jointPosition(com.leapmotion.leap.Finger.Joint.JOINT_TIP));
    }

    /**
     * The mcp joint position in millimeters.
     *
     * @return
     */
    public PVector getPositionOfJointMcp() {
        return this.leap.map(this._finger.jointPosition(Joint.JOINT_MCP));
    }

    /**
     * The pip joint position in millimeters.
     *
     * @return
     */
    public PVector getPositionOfJointPip() {
        return this.leap.map(this._finger.jointPosition(Joint.JOINT_PIP));
    }

    /**
     * The dip joint position in millimeters.
     *
     * @return
     */
    public PVector getPositionOfJointDip() {
        return this.leap.map(this._finger.jointPosition(Joint.JOINT_DIP));
    }

    /**
     * Raw data of the _finger tip position.
     *
     * @return
     */
    public PVector getRawPositionOfJointTip() {
        return this.leap.convert(this._finger.jointPosition(Joint.JOINT_TIP));
    }

    /**
     * Raw data of the mcp joint position.
     *
     * @return
     */
    public PVector getRawPositionOfJointMcp() {
        return this.leap.convert(this._finger.jointPosition(Joint.JOINT_MCP));
    }

    /**
     * Raw data of the pip joint position.
     *
     * @return
     */
    public PVector getRawPositionOfJointPip() {
        return this.leap.convert(this._finger.jointPosition(Joint.JOINT_PIP));
    }

    /**
     * Raw data of the dip joint position.
     *
     * @return
     */
    public PVector getRawPositionOfJointDip() {
        return this.leap.convert(this._finger.jointPosition(Joint.JOINT_DIP));
    }

    /**
     * Get the index of fingertype (0-4, 0=thumb, 1=index, 2=middle, 3=ring, 4=pinky).
     *
     * @return
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
     * @return
     */
    public Bone getBone(String name) {
        name = name.toLowerCase();
        if (name.equals("distal")) {
            return new Bone(parent, leap, this._finger.bone(com.leapmotion.leap.Bone.Type.TYPE_DISTAL));
        } else if (name.equals("intermediate")) {
            return new Bone(parent, leap, this._finger.bone(com.leapmotion.leap.Bone.Type.TYPE_INTERMEDIATE));
        } else if (name.equals("proximal")) {
            return new Bone(parent, leap, this._finger.bone(com.leapmotion.leap.Bone.Type.TYPE_PROXIMAL));
        } else if (name.equals("metacarpal")) {
            return new Bone(parent, leap, this._finger.bone(com.leapmotion.leap.Bone.Type.TYPE_METACARPAL));
        }
        return null;
    }

    /**
     * Get a specific bone by numeric index.
     *
     * @param type (0-3, 0=distal, 1=intermediate, 2=proximal, 3=metacarpal).
     * @return
     */
    public Bone getBone(int type) {
        switch (type) {
            case 0:
                return new Bone(parent, leap, this._finger.bone(com.leapmotion.leap.Bone.Type.TYPE_DISTAL));
            case 1:
                return new Bone(parent, leap, this._finger.bone(com.leapmotion.leap.Bone.Type.TYPE_INTERMEDIATE));
            case 2:
                return new Bone(parent, leap, this._finger.bone(com.leapmotion.leap.Bone.Type.TYPE_PROXIMAL));
            case 3:
                return new Bone(parent, leap, this._finger.bone(com.leapmotion.leap.Bone.Type.TYPE_METACARPAL));
        }
        return null;
    }

    /**
     * Get the distal bone of the _finger.
     *
     * @return
     */
    public Bone getDistalBone() {
        return new Bone(parent, leap, this._finger.bone(com.leapmotion.leap.Bone.Type.TYPE_DISTAL));
    }

    /**
     * Get the intermediate bone of the _finger.
     *
     * @return
     */
    public Bone getIntermediateBone() {
        return new Bone(parent, leap, this._finger.bone(com.leapmotion.leap.Bone.Type.TYPE_INTERMEDIATE));
    }

    /**
     * Get the metacarpal bone of the _finger.
     *
     * @return
     */
    public Bone getMetacarpalBone() {
        return new Bone(parent, leap, this._finger.bone(com.leapmotion.leap.Bone.Type.TYPE_METACARPAL));
    }

    /**
     * Get the proximal bone of the _finger.
     *
     * @return
     */
    public Bone getProximalBone() {
        return new Bone(parent, leap, this._finger.bone(com.leapmotion.leap.Bone.Type.TYPE_PROXIMAL));
    }

	/* ------------------------------------------------------------------------ */
    /* DRAWING */

    /**
     * Draw the lines between the joints.
     *
     * @param pre
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

    public void drawLines() {
        this.drawLines(true);
    }

    /**
     * Draw all bones of _finger.
     *
     * @param pre
     */
    public void drawBones(boolean pre) {
        this.getBone(0).draw(pre);
        this.getBone(1).draw(pre);
        this.getBone(2).draw(pre);
        if (this.getType() != 0) { // thumb
            this.getBone(3).draw(pre);
        }
    }

    public void drawBones() {
        this.drawBones(true);
    }

    /**
     * Draw all joints of _finger.
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

    public void drawJoints(int radius) {
        this.drawJoints(radius, true);
    }

    public void drawJoints(boolean pre) {
        this.drawJoints(3, pre);
    }

    public void drawJoints() {
        this.drawJoints(3, true);
    }

    /**
     * Draw the joints and bones of the _finger.
     */
    public void draw(float radius, boolean pre) {
        this.drawBones(pre);
        // this.drawLines(pre);
        this.drawJoints(radius, pre);
    }

    public void draw(int radius) {
        this.draw(radius, true);
    }

    public void draw(boolean pre) {
        this.draw(3, pre);
    }

    public void draw() {
        this.draw(3, true);
    }

}