package de.voidplus.leapmotion;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;


public class Arm implements RawAccess<com.leapmotion.leap.Arm> {

    protected PApplet parent;
    protected LeapMotion leap;
    private com.leapmotion.leap.Arm _arm;

    public Arm(PApplet parent, LeapMotion leap, com.leapmotion.leap.Arm _arm) {
        this.parent = parent;
        this.leap = leap;
        this._arm = _arm;
    }

    /**
     * Is it a valid Arm object?
     *
     * @return Is it a valid Arm object?
     */
    @Override
    public boolean isValid() {
        return this._arm.isValid();
    }

    /**
     * Get the raw instance of com.leapmotion.leap.Arm.
     *
     * @return Raw instance of Arm
     */
    @Override
    public com.leapmotion.leap.Arm getRaw() {
        return this._arm;
    }

    /**
     * The position of the elbow.
     *
     * @return Position
     */
    public PVector getElbowPosition() {
        return this.leap.map(this._arm.elbowPosition());
    }

    /**
     * The raw position of the elbow.
     *
     * @return Position
     */
    public PVector getWristRawPosition() {
        return this.leap.convert(this._arm.wristPosition());
    }

    /**
     * The position of the wrist.
     *
     * @return Position
     */
    public PVector getWristPosition() {
        return this.leap.map(this._arm.wristPosition());
    }

    /**
     * The raw position of the wrist.
     *
     * @return Position
     */
    public PVector getElbowRawPosition() {
        return this.leap.convert(this._arm.elbowPosition());
    }

    /**
     * The average width of the arm.
     *
     * @return Width
     */
    public float getWidth() {
        return this._arm.width();
    }

    /**
     * The center position.
     *
     * @return Position
     */
    public PVector getPosition() {
        return this.leap.map(this._arm.center());
    }

    /**
     * Raw data of the center position.
     *
     * @return Position
     */
    public PVector getRawPosition() {
        return this.leap.convert(this._arm.center());
    }

    /**
     * Draw arm.
     *
     * @param radius Radius
     */
    public void draw(float radius) {
        PVector wristPos = this.getWristPosition();
        PVector elbowPos = this.getElbowPosition();

        if (this.leap.is2D) {
            this.parent.stroke(0, 35);
            this.parent.noFill();
            this.parent.beginShape(PConstants.LINES);
            this.parent.vertex(wristPos.x, wristPos.y);
            this.parent.vertex(elbowPos.x, elbowPos.y);
            this.parent.endShape(PConstants.OPEN);

            this.parent.noStroke();
            this.parent.fill(0);
            this.parent.ellipseMode(PConstants.CENTER);
            this.parent.ellipse(wristPos.x, wristPos.y, radius, radius);
            this.parent.ellipse(elbowPos.x, elbowPos.y, radius, radius);
        } else {
            this.parent.stroke(0, 35);
            this.parent.noFill();
            this.parent.beginShape(PConstants.LINES);
            this.parent.vertex(wristPos.x, wristPos.y, wristPos.z);
            this.parent.vertex(elbowPos.x, elbowPos.y, elbowPos.z);
            this.parent.endShape(PConstants.OPEN);

            this.parent.noStroke();
            this.parent.fill(0);
            this.parent.sphereDetail(20);
            this.parent.pushMatrix();
                this.parent.translate(wristPos.x, wristPos.y, wristPos.z);
                this.parent.sphere(radius);
            this.parent.popMatrix();
            this.parent.pushMatrix();
                this.parent.translate(elbowPos.x, elbowPos.y, elbowPos.z);
                this.parent.sphere(radius);
            this.parent.popMatrix();
        }
    }

    /**
     * Draw arm.
     */
    public void draw() {
        this.draw(3);
    }

}