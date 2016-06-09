package de.voidplus.leapmotion;

import processing.core.PApplet;
import processing.core.PVector;


public class KeyTapGesture extends Gesture implements RawAccess<com.leapmotion.leap.KeyTapGesture> {

    private com.leapmotion.leap.KeyTapGesture _keyTap;

    public KeyTapGesture(PApplet parent, LeapMotion leap, com.leapmotion.leap.Gesture _gesture) {
        super(parent, leap, _gesture);
        this._keyTap = new com.leapmotion.leap.KeyTapGesture(_gesture);
    }

    /**
     * Get the raw instance of com.leapmotion.leap.KeyTapGesture.
     *
     * @return Raw instance of com.leapmotion.leap.KeyTapGesture
     */
    @Override
    public com.leapmotion.leap.KeyTapGesture getRaw() {
        return this._keyTap;
    }

    /**
     * The position where the key tap is registered.
     *
     * @return Position
     */
    public PVector getPosition() {
        return this.leap.map(this._keyTap.position());
    }

    /**
     * Raw data of the position where the key tap is registered.
     *
     * @return Raw position
     */
    public PVector getRawPosition() {
        return this.leap.convert(this._keyTap.position());
    }

    /**
     * The direction of finger tip motion.
     *
     * @return Direction
     */
    public PVector getDirection() {
        return new PVector(
            PApplet.degrees(this._keyTap.direction().roll()),
            PApplet.degrees(this._keyTap.direction().pitch()),
            PApplet.degrees(this._keyTap.direction().yaw())
        );
    }

    /**
     * Raw data of the position where the key tap is registered.
     *
     * @return Raw direction
     */
    public PVector getRawDirection() {
        return this.leap.convert(this._keyTap.direction());
    }

    /**
     * The finger performing the key tap gesture.
     *
     * @return Single finger or null
     */
    public Finger getFinger() {
        if (this._keyTap.pointable().isFinger()) {
            return new Finger(
                    this.parent, this.leap,
                    new com.leapmotion.leap.Finger(this._keyTap.pointable())
            );
        }
        return null;
    }

}