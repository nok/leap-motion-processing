package de.voidplus.leapmotion;

import processing.core.PApplet;
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

}