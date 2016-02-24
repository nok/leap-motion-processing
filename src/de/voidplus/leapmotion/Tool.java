package de.voidplus.leapmotion;

import processing.core.PApplet;
import processing.core.PVector;


public class Tool extends Pointable implements RawAccess<com.leapmotion.leap.Tool> {

    private com.leapmotion.leap.Tool _tool;

    public Tool(PApplet parent, LeapMotion leap, com.leapmotion.leap.Tool _tool) {
        super(parent, leap, (com.leapmotion.leap.Pointable) _tool);
        this._tool = _tool;
    }

    public Tool(PApplet parent, LeapMotion leap, com.leapmotion.leap.Pointable pointable) {
        super(parent, leap, pointable);
    }

    /**
     * Is it a valid Tool object?
     *
     * @return Is it a valid Tool object?
     */
    @Override
    public boolean isValid() {
        return this._tool.isValid();
    }

    /**
     * Get the raw instance of com.leapmotion.leap.Finger.
     *
     * @return Raw instance of com.leapmotion.leap.Finger
     */
    @Override
    public com.leapmotion.leap.Tool getRaw() {
        return this._tool;
    }

    /**
     * Get the position of tip.
     *
     * @return Position
     */
    public PVector getTipPosition() {
        return this.leap.map(this._tool.tipPosition());
    }

    /**
     * Raw data of tip position.
     *
     * @return Raw position
     */
    public PVector getRawTipPosition() {
        return this.leap.convert(this._tool.tipPosition());
    }


	/* ------------------------------------------------------------------------ */
    /* DRAWING */

    /**
     * Draw the finger with all details.
     *
     * @param radius The radius of the ellipse (2D) or sphere (3D).
     */
    public void draw(float radius) {
        PVector start = this.getPosition();
        PVector end = this.getTipPosition();

        this.parent.noStroke();
        this.parent.fill(0);

        if (this.leap.is2D) {
            this.parent.line(start.x, start.y, end.x, end.y);
        } else {
            this.parent.line(start.x, start.y, start.z, end.x, end.y, end.z);
        }

        this.parent.stroke(radius);
        this.parent.noFill();

        if (this.leap.is2D) {
            this.parent.ellipseMode(processing.core.PConstants.CENTER);
            this.parent.ellipse(start.x, start.y, radius, radius);
            this.parent.ellipse(end.x, end.y, radius, radius);
        } else {
            this.parent.sphereDetail(20);
            this.parent.pushMatrix();
            this.parent.translate(start.x, start.y, start.z);
            this.parent.sphere(radius);
            this.parent.popMatrix();
            this.parent.pushMatrix();
            this.parent.translate(end.x, end.y, end.z);
            this.parent.sphere(radius);
            this.parent.popMatrix();
        }
    }

    /**
     * Draw the finger with all details.
     */
    public void draw() {
        this.draw(5);
    }

}
