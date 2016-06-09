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
     * @return Raw instance of com.leapmotion.leap.SwipeGesture
     */
    @Override
    public com.leapmotion.leap.SwipeGesture getRaw() {
        return this._swipe;
    }

    /**
     * The current position of the swipe.
     *
     * @return Position
     */
    public PVector getPosition() {
        return this.leap.map(this._swipe.position());
    }

    /**
     * Raw data of the current position of the swipe.
     *
     * @return Raw position
     */
    public PVector getRawPosition() {
        return this.leap.convert(this._swipe.position());
    }

    /**
     * The position where the swipe began.
     *
     * @return Position
     */
    public PVector getStartPosition() {
        return this.leap.map(this._swipe.startPosition());
    }

    /**
     * Raw data of the position where the swipe began.
     *
     * @return Raw position
     */
    public PVector getRawStartPosition() {
        return this.leap.convert(this._swipe.startPosition());
    }

    /**
     * The unit direction vector parallel to the swipe motion.
     *
     * @return Direction
     */
    public PVector getDirection() {
        return new PVector(
            PApplet.degrees(this._swipe.direction().roll()),
            PApplet.degrees(this._swipe.direction().pitch()),
            PApplet.degrees(this._swipe.direction().yaw())
        );
    }

    /**
     * Raw data of the unit direction vector parallel to the swipe motion.
     *
     * @return Raw direction
     */
    public PVector getRawDirection() {
        return this.leap.convert(this._swipe.direction());
    }

    /**
     * The swipe speed in mm/second.
     *
     * @return Speed
     */
    public float getSpeed() {
        return this._swipe.speed();
    }

    /**
     * The finger performing the swipe gesture.
     *
     * @return Single finger or null
     */
    public Finger getFinger() {
        if (this._swipe.pointable().isFinger()) {
            return new Finger(
                    this.parent, this.leap,
                    new com.leapmotion.leap.Finger(this._swipe.pointable())
            );
        }
        return null;
    }

}