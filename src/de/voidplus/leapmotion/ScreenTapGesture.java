package de.voidplus.leapmotion;

import processing.core.PApplet;
import processing.core.PVector;


public class ScreenTapGesture extends Gesture implements RawAccess<com.leapmotion.leap.ScreenTapGesture> {

    private com.leapmotion.leap.ScreenTapGesture _screenTap;

    public ScreenTapGesture(PApplet parent, LeapMotion leap, com.leapmotion.leap.Gesture _gesture) {
        super(parent, leap, _gesture);
        this._screenTap = new com.leapmotion.leap.ScreenTapGesture(_gesture);
    }

    /**
     * Get the raw instance of com.leapmotion.leap.ScreenTapGesture.
     *
     * @return
     */
    @Override
    public com.leapmotion.leap.ScreenTapGesture getRaw() {
        return this._screenTap;
    }

    /**
     * The position where the screen tap is registered.
     *
     * @return
     */
    public PVector getPosition() {
        return this.leap.map(this._screenTap.position());
    }

    /**
     * Raw data of the position where the screen tap is registered.
     *
     * @return
     */
    public PVector getRawPosition() {
        return this.leap.convert(this._screenTap.position());
    }

    /**
     * The direction of finger tip motion.
     *
     * @return
     */
    public PVector getDirection() {
        return this.leap.map(this._screenTap.direction());
    }

    /**
     * Raw data of the direction of finger tip motion.
     *
     * @return
     */
    public PVector getRawDirection() {
        return this.leap.convert(this._screenTap.direction());
    }

    /**
     * The finger performing the screen tap gesture.
     *
     * @return
     */
    public Finger getFinger() {
        if (this._screenTap.pointable().isFinger()) {
            return new Finger(this.parent, this.leap, new com.leapmotion.leap.Finger(this._screenTap.pointable()));
        }
        return null;
    }

}