package de.voidplus.leapmotion;

import processing.core.PApplet;
import processing.core.PVector;


public class Pointable {

    protected PApplet parent;
    protected LeapMotion leap;
    protected com.leapmotion.leap.Pointable _pointable;

    public Pointable(PApplet parent, LeapMotion leap, com.leapmotion.leap.Pointable _pointable) {
        this.parent = parent;
        this.leap = leap;
        this._pointable = _pointable;
    }

    /**
     * Get the unique ID.
     *
     * @return ID
     */
    public int getId() {
        return this._pointable.id();
    }

    /**
     * The tip position in millimeters.
     *
     * @return Position
     */
    public PVector getPosition() {
        return this.leap.map(this._pointable.tipPosition());
    }

    /**
     * Raw data of the tip position.
     *
     * @return Raw position
     */
    public PVector getRawPosition() {
        return this.leap.convert(this._pointable.tipPosition());
    }

    /**
     * The stabilized tip position in millimeters.
     *
     * @return Position
     */
    public PVector getStabilizedPosition() {
        return this.leap.map(this._pointable.stabilizedTipPosition());
    }

    /**
     * Raw data of the stabilized tip position.
     *
     * @return Position
     */
    public PVector getRawStabilizedPosition() {
        return this.leap.convert(this._pointable.tipPosition());
    }

    /**
     * The rate of change of the tip position in millimeters/second.
     *
     * @return Position
     */
    public PVector getVelocity() {
        return this.leap.map(this._pointable.tipVelocity());
    }

    /**
     * Raw data of the rate of change of the tip position.
     *
     * @return Velocity
     */
    public PVector getRawVelocity() {
        return this.leap.convert(this._pointable.tipVelocity());
    }

    /**
     * The direction in which this finger is pointing.
     *
     * @return Direction
     */
    public PVector getDirection() {
        return new PVector(
                this._pointable.direction().getX(),
                -this._pointable.direction().getY(),
                this._pointable.direction().getZ()
        );
    }

    /**
     * A value proportional to the distance between this Pointable object and the adaptive touch plane.
     * <p/>
     * The touch distance is a value in the range [-1, 1]. The value 1.0 indicates the Pointable is at the far edge of the hovering zone. The value 0 indicates the Pointable is just entering the touching zone. A value of -1.0 indicates the Pointable is firmly within the touching zone. Values in between are proportional to the distance from the plane. Thus, the touchDistance of 0.5 indicates that the Pointable is halfway into the hovering zone.
     * <p/>
     * You can use the touchDistance value to modulate visual feedback given to the user as their fingers close in on a touch target, such as a button.
     *
     * @return The normalized touch distance of this Pointable object.
     */
    public float getTouchDistance() {
        return this._pointable.touchDistance();
    }

    /**
     * The current touch zone of this Pointable object.
     * <p/>
     * The Leap Motion software computes the touch zone based on a floating touch plane that adapts to the user's finger movement and hand posture. The Leap Motion software interprets purposeful movements toward this plane as potential touch points. When a Pointable moves close to the adaptive touch plane, it enters the "hovering" zone. When a Pointable reaches or passes through the plane, it enters the "touching" zone.
     * <p/>
     * The possible states are present in the Zone enum of this class:
     * <p/>
     * -1 = Zone.NONE � The Pointable is outside the hovering zone.
     * 0 = Zone.HOVERING � The Pointable is close to, but not touching the touch plane.
     * 1 = Zone.TOUCHING � The Pointable has penetrated the touch plane.
     * <p/>
     * The touchDistance value provides a normalized indication of the distance to the touch plane when the Pointable is in the hovering or touching zones.
     *
     * @return The touch zone of this Pointable
     */
    public int getTouchZone() {
        switch (this._pointable.touchZone()) {
            case ZONE_NONE:
                return -1;
            case ZONE_HOVERING:
                return 0;
            case ZONE_TOUCHING:
                return 1;
        }
        return -1;
    }

    /**
     * The estimated length of the finger or tool in millimeters.
     *
     * @return Length
     */
    public float getLength() {
        return this._pointable.length();
    }

    /**
     * The duration of time this Pointable has been visible to the Leap Motion Controller.
     *
     * @return Duration of visibility
     */
    public float getTimeVisible() {
        return this._pointable.timeVisible();
    }

    /**
     * Is this a finger?
     *
     * @return Is this a finger?
     */
    protected boolean isFinger() {
        return this._pointable.isFinger();
    }

    /**
     * Is this a tool?
     *
     * @return Is this a tool?
     */
    protected boolean isTool() {
        return this._pointable.isTool();
    }

}
