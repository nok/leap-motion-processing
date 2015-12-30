package de.voidplus.leapmotion;

import processing.core.PApplet;


public class Device implements RawAccess<com.leapmotion.leap.Device> {

    protected PApplet parent;
    protected LeapMotion leap;
    private com.leapmotion.leap.Device _device;

    public Device(PApplet parent, LeapMotion leap, com.leapmotion.leap.Device _device) {
        this.parent = parent;
        this.leap = leap;
        this._device = _device;
    }

    /**
     * Is it a valid Device object?
     *
     * @return Is it a valid Device object?
     */
    @Override
    public boolean isValid() {
        return this._device.isValid();
    }

    /**
     * Get the raw instance of com.leapmotion.leap.Device.
     *
     * @return Raw instance of com.leapmotion.leap.Device
     */
    @Override
    public com.leapmotion.leap.Device getRaw() {
        return this._device;
    }

    /**
     * The angle of view along the x axis of this device.
     * <p/>
     * The Leap Motion controller scans a region in the shape of an inverted pyramid centered at the device's center and extending upwards. The horizontalViewAngle reports the view angle along the long dimension of the device.
     *
     * @return The horizontal angle of view in radians.
     */
    public float getHorizontalViewAngle() {
        return this._device.horizontalViewAngle();
    }

    /**
     * The angle of view along the z axis of this device.
     * <p/>
     * The Leap Motion controller scans a region in the shape of an inverted pyramid centered at the device's center and extending upwards. The verticalViewAngle reports the view angle along the short dimension of the device.
     *
     * @return The vertical angle of view in radians.
     */
    public float getVerticalViewAngle() {
        return this._device.verticalViewAngle();
    }

    /**
     * The maximum reliable tracking range.
     * <p/>
     * The range reports the maximum recommended distance from the device center for which tracking is expected to be reliable. This distance is not a hard limit. Tracking may be still be functional above this distance or begin to degrade slightly before this distance depending on calibration and extreme environmental conditions.
     *
     * @return The recommended maximum range of the device in mm.
     */
    public float getRange() {
        return this._device.range();
    }

    /**
     * A string containing a brief, human readable description of the Device object.
     */
    public String toString() {
        return this._device.toString();
    }
}
