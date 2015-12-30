package de.voidplus.leapmotion;

import processing.core.PApplet;
import processing.core.PVector;


public class CircleGesture extends Gesture implements RawAccess<com.leapmotion.leap.CircleGesture> {

    private com.leapmotion.leap.CircleGesture _circle;

    public CircleGesture(PApplet parent, LeapMotion leap, com.leapmotion.leap.Gesture _gesture) {
        super(parent, leap, _gesture);
        this._circle = new com.leapmotion.leap.CircleGesture(_gesture);
    }

    /**
     * Get the raw instance of com.leapmotion.leap.CircleGesture.
     *
     * @return Raw instance of CircleGesture
     */
    @Override
    public com.leapmotion.leap.CircleGesture getRaw() {
        return this._circle;
    }

    /**
     * The center point of the gesture within the Leap frame of reference.
     *
     * @return Position
     */
    public PVector getCenter() {
        return this.leap.map(this._circle.center());
    }

    /**
     * Raw data of the center point of the gesture within the Leap frame of reference.
     *
     * @return Raw position
     */
    public PVector getRawCenter() {
        return this.leap.convert(this._circle.center());
    }

    /**
     * Returns the normal vector for the gesture being traced.
     *
     * @return Position
     */
    public PVector getNormal() {
        return this.leap.map(this._circle.normal());
    }

    /**
     * Raw data the normal vector for the gesture being traced.
     *
     * @return Raw position
     */
    public PVector getRawNormal() {
        return this.leap.convert(this._circle.normal());
    }

    /**
     * The number of times the finger tip has traversed the gesture.
     *
     * @return Number of counter
     */
    public float getProgress() {
        return this._circle.progress();
    }

    /**
     * The radius of the gesture.
     *
     * @return Radius
     */
    public float getRadius() {
        return this._circle.radius();
    }

    /**
     * The finger performing the circle gesture.
     *
     * @return Finger
     */
    public Finger getFinger() {
        if (this._circle.pointable().isFinger()) {
            return new Finger(this.parent, this.leap, new com.leapmotion.leap.Finger(this._circle.pointable()));
        }
        return null;
    }

    /**
     * Get the direction of the _circle gestures.
     *
     * @return Direction (0 = anticlockwise/left, 1 = clockwise/right)
     */
    public int getDirection() {
        if (this.isValid()) {
            if (this._circle.pointable().direction().angleTo(this._circle.normal()) <= Math.PI / 2) {
                return 1;
            } else {
                return 0;
            }
        }
        return -1;
    }

}