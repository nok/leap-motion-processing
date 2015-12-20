package de.voidplus.leapmotion;

import processing.core.PApplet;
import processing.core.PVector;

public class KeyTapGesture extends Gesture {

    private com.leapmotion.leap.KeyTapGesture _keyTap;

    public KeyTapGesture(PApplet parent, LeapMotion leap, com.leapmotion.leap.Gesture _gesture) {
        super(parent, leap, _gesture);
        this._keyTap = new com.leapmotion.leap.KeyTapGesture(_gesture);
    }

    /**
     * The position where the key tap is registered.
     *
     * @return
     */
    public PVector getPosition() {
        return this.leap.map(this._keyTap.position());
    }

    /**
     * Raw data of the position where the key tap is registered.
     *
     * @return
     */
    public PVector getRawPosition() {
        return this.leap.convert(this._keyTap.position());
    }

    /**
     * The direction of finger tip motion.
     *
     * @return
     */
    public PVector getDirection() {
        return this.leap.map(this._keyTap.direction());
    }

    /**
     * Raw data of the position where the key tap is registered.
     *
     * @return
     */
    public PVector getRawDirection() {
        return this.leap.convert(this._keyTap.direction());
    }

    /**
     * The finger performing the key tap gesture.
     *
     * @return
     */
    public Finger getFinger() {
        if (this._keyTap.pointable().isFinger()) {
            return new Finger(this.parent, this.leap, new com.leapmotion.leap.Finger(this._keyTap.pointable()));
        }
        return null;
    }

}