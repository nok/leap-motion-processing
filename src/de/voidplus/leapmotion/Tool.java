package de.voidplus.leapmotion;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Tool extends Pointable {

	
	private com.leapmotion.leap.Tool tool;
	
	
	public Tool(PApplet parent, LeapMotion leap, com.leapmotion.leap.Tool tool){
		super(parent, leap, (com.leapmotion.leap.Pointable) tool);
		this.tool = tool;
	}
	public Tool(PApplet parent, LeapMotion leap, com.leapmotion.leap.Pointable pointable){
		super(parent, leap, pointable);
	}

}
