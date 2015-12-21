package de.voidplus.leapmotion;

import processing.core.PApplet;
import processing.core.PVector;


public class SwipeGesture extends Gesture implements RawAccess<com.leapmotion.leap.SwipeGesture> {

    private com.leapmotion.leap.SwipeGesture _swipe;

    public SwipeGesture(PApplet parent, LeapMotion leap, com.leapmotion.leap.Gesture _gesture) {
        super(parent, leap, _gesture);
        this._swipe = new com.leapmotion.leap.SwipeGesture(_gesture);
    }

    /**
     * Get the raw instance of com.leapmotion.leap.SwipeGesture.
     *
     * @return
     */
    @Override
    public com.leapmotion.leap.SwipeGesture getRaw() {
        return this._swipe;
    }

    /**
     * The current position of the _swipe.
     *
     * @return
     */
    public PVector getPosition() {
        return this.leap.map(this._swipe.position());
    }

    /**
     * Raw data of the current position of the _swipe.
     *
     * @return
     */
    public PVector getRawPosition() {
        return this.leap.convert(this._swipe.position());
    }

    /**
     * The position where the _swipe began.
     *
     * @return
     */
    public PVector getStartPosition() {
        return this.leap.map(this._swipe.startPosition());
    }

    /**
     * Raw data of the position where the _swipe began.
     *
     * @return
     */
    public PVector getRawStartPosition() {
        return this.leap.convert(this._swipe.startPosition());
    }

    /**
     * The unit direction vector parallel to the _swipe motion.
     *
     * @return
     */
    public PVector getDirection() {
        return this.leap.map(this._swipe.direction());
    }

    /**
     * Raw data of the unit direction vector parallel to the _swipe motion.
     *
     * @return
     */
    public PVector getRawDirection() {
        return this.leap.convert(this._swipe.direction());
    }

    /**
     * The _swipe speed in mm/second.
     *
     * @return
     */
    public float getSpeed() {
        return this._swipe.speed();
    }

    /**
     * The finger performing the _swipe gesture.
     *
     * @return
     */
    public Finger getFinger() {
        if (this._swipe.pointable().isFinger()) {
            return new Finger(this.parent, this.leap, new com.leapmotion.leap.Finger(this._swipe.pointable()));
        }
        return null;
    }

}