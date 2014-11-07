package de.voidplus.leapmotion;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Image extends PImage {

	protected PApplet parent;
	protected com.leapmotion.leap.Image image;
	protected LeapMotion leap;
	protected int id;
	protected PImage pimage;

	public Image(PApplet parent, LeapMotion leap, com.leapmotion.leap.Image image){
		super(image.width(), image.height(), PConstants.RGB);
		
		this.parent = parent;
		this.leap = leap;
		
		this.image = image;
		this.id = image.id();
		
		// https://developer.leapmotion.com/documentation/skeletal/java/devguide/Leap_Images.html
		this.loadPixels();
		byte[] imageData = this.image.data();
		int r, g, b;
		for (int i = 0; i < image.width() * image.height(); i++) {
			r = (imageData[i] & 0xFF) << 16;
			g = (imageData[i] & 0xFF) << 8;
			b = imageData[i] & 0xFF;
			this.pixels[i] = r | g | b;
		}
		this.updatePixels();
	}
	
	/**
	 * Is it a valid instance of class Image?
	 * @param image
	 * @return
	 */
	protected static boolean isValid(com.leapmotion.leap.Image image){
		return image.isValid();
	}

	/**
	 * Get image width.
	 * @return
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Get image height.
	 * @return
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * Get the id of image.
	 * @return
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Is that the left camera?
	 * @return
	 */
	public boolean isLeft() {
		if (this.getId() == 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Is that the right camera?
	 * @return
	 */
	public boolean isRight() {
		if (this.getId() == 0) {
			return true;
		}
		return false;
	}
	
}
