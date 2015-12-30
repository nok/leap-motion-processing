package de.voidplus.leapmotion;

import java.util.ArrayList;

import processing.core.PApplet;


public abstract class Gesture {

    protected PApplet parent;
    protected LeapMotion leap;
    private com.leapmotion.leap.Gesture _gesture;

    public Gesture(PApplet parent, LeapMotion leap, com.leapmotion.leap.Gesture _gesture) {
        this.parent = parent;
        this.leap = leap;
        this._gesture = _gesture;
    }

    /**
     * Is it a valid Gesture object?
     *
     * @return Is it a valid Gesture object?
     */
    public boolean isValid() {
        return this._gesture.isValid();
    }

    /**
     * Get the unique ID.
     *
     * @return ID
     */
    public int getId() {
        return this._gesture.id();
    }

    /**
     * Get the type index of a gesture.
     *
     * @return Type of gesture
     */
    public int getType() {
        if (this.isValid()) {
            switch (this._gesture.type()) {
                case TYPE_CIRCLE:
                    return 0;
                case TYPE_KEY_TAP:
                    return 1;
                case TYPE_SCREEN_TAP:
                    return 2;
                case TYPE_SWIPE:
                    return 3;
                default:
                    return -1;
            }
        }
        return -1;
    }


	/* ------------------------------------------------------------------------ */
    /* DURATION */

    /**
     * The elapsed duration of the recognized movement up to the frame containing this Gesture object, in microseconds.
     *
     * @return Microseconds
     */
    public long getDuration() {
        return this._gesture.duration();
    }

    /**
     * The elapsed duration in seconds.
     *
     * @return Seconds
     */
    public float getDurationInSeconds() {
        return this._gesture.durationSeconds();
    }


	/* ------------------------------------------------------------------------ */
    /* HAND */

    /**
     * Is there any hand?
     *
     * @return Is there any hand?
     */
    public boolean hasHands() {
        return !this._gesture.hands().isEmpty();
    }

    /**
     * Get the number of detected hands.
     *
     * @return Number of hands
     */
    public int countHands() {
        if (this.hasHands()) {
            return this._gesture.hands().count();
        }
        return 0;
    }

    /**
     * Get all detected hands.
     *
     * @return List of hands
     */
    public ArrayList<Hand> getHands() {
        ArrayList<Hand> hands = new ArrayList<Hand>();
        if (this.hasHands()) {
            for (com.leapmotion.leap.Hand hand : this._gesture.hands()) {
                hands.add(new Hand(this.parent, this.leap, hand));
            }
        }
        return hands;
    }

}
