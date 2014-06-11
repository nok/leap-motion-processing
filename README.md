# Leap Motion for Processing

Simple library to use the complete [Leap Motion](https://leapmotion.com/) [API](https://developer.leapmotion.com/documentation/api/annotated) in [Processing](http://processing.org/).


## BETA

If you are interested in the **Leap Motion 2.0.2 BETA** implementation, so have a look at the [BETA branch](https://github.com/voidplus/leap-motion-processing/tree/beta).


## About

The Leap detects and tracks hands, fingers and finger-like tools. The device operates in an intimate proximity with high precision and tracking frame rate.

The Leap software analyzes the objects observed in the device field of view. It recognizes hands, fingers, and tools, reporting both discrete positions and motion. The Leap field of view is an inverted pyramid centered on the device. The effective range of the Leap extends from approximately 25 to 600 millimeters above the device (1 inch to 2 feet).


## Download

- [Leap Motion for Processing v.1.1.3.1](https://raw.github.com/voidplus/leap-motion-processing/master/download/LeapMotionForProcessing.zip)


## Installation

Unzip and put the extracted *LeapMotionForProcessing* folder into the libraries folder of your Processing sketches. Reference and examples are included in the *LeapMotionForProcessing* folder.


## Dependencies

- [Leap Motion Software](http://www.leapmotion.com/setup)


## Usage


### Basic Data-Access

```java
import de.voidplus.leapmotion.*;

LeapMotion leap;

void setup(){
    size(800, 500, P3D);
    background(255);
    noStroke(); fill(50);
    // ...
    
    leap = new LeapMotion(this);
}

void draw(){
    background(255);
    // ...
	int fps = leap.getFrameRate();

	// HANDS
	for(Hand hand : leap.getHands()){

		hand.draw();
		int     hand_id          = hand.getId();
		PVector hand_position    = hand.getPosition();
		PVector hand_stabilized  = hand.getStabilizedPosition();
		PVector hand_direction   = hand.getDirection();
		PVector hand_dynamics    = hand.getDynamics();
		float   hand_roll        = hand.getRoll();
		float   hand_pitch       = hand.getPitch();
		float   hand_yaw         = hand.getYaw();
		PVector sphere_position  = hand.getSpherePosition();
		float   sphere_radius    = hand.getSphereRadius();
		
		// FINGERS
		for(Finger finger : hand.getFingers()){
		
			// Basics
			finger.draw();
			int     finger_id         = finger.getId();
			PVector finger_position   = finger.getPosition();
			PVector finger_stabilized = finger.getStabilizedPosition();
			PVector finger_velocity   = finger.getVelocity();
			PVector finger_direction  = finger.getDirection();
			
			// Touch Emulation
			int     touch_zone        = finger.getTouchZone();
			float   touch_distance    = finger.getTouchDistance();

			switch(touch_zone){
				case -1: // None
				break;
				case 0: // Hovering
					// println("Hovering (#"+finger_id+"): "+touch_distance);
				break;
				case 1: // Touching
					// println("Touching (#"+finger_id+")");
				break;
			}
		}
		
		// TOOLS
		for(Tool tool : hand.getTools()){
		
			// Basics
			tool.draw();
			int     tool_id           = tool.getId();
			PVector tool_position     = tool.getPosition();
			PVector tool_stabilized   = tool.getStabilizedPosition();
			PVector tool_velocity     = tool.getVelocity();
			PVector tool_direction    = tool.getDirection();

			// Touch Emulation
			int     touch_zone        = tool.getTouchZone();
			float   touch_distance    = tool.getTouchDistance();

			switch(touch_zone){
				case -1: // None
				break;
				case 0: // Hovering
					// println("Hovering (#"+tool_id+"): "+touch_distance);
				break;
				case 1: // Touching
					// println("Touching (#"+tool_id+")");
				break;
			}
		}
		
	}
	
	// DEVICES
	for(Device device : leap.getDevices()){
		float device_horizontal_view_angle = device.getHorizontalViewAngle();
		float device_verical_view_angle = device.getVerticalViewAngle();
		float device_range = device.getRange();
	}
}

void leapOnInit(){
	// println("Leap Motion Init");
}
void leapOnConnect(){
	// println("Leap Motion Connect");
}
void leapOnFrame(){
	// println("Leap Motion Frame");
}
void leapOnDisconnect(){
	// println("Leap Motion Disconnect");
}
void leapOnExit(){
	// println("Leap Motion Exit");
}
```


### Gesture-Recognition

![Snapshot](https://raw.github.com/voidplus/leap-motion-processing/master/reference/leap_gestures.jpg)

```java
import de.voidplus.leapmotion.*;

LeapMotion leap;

void setup(){
    size(800, 500, P3D);
    background(255);
    // ...
    
    leap = new LeapMotion(this).withGestures();
    // leap = new LeapMotion(this).withGestures("circle, swipe, screen_tap, key_tap");
    // leap = new LeapMotion(this).withGestures("swipe"); // Leap detects only swipe gestures.
}

void draw(){
    background(255);
    // ...
}

// SWIPE GESTURE
void leapOnSwipeGesture(SwipeGesture g, int state){
	int 	id 					= g.getId();
	Finger	finger 				= g.getFinger();
	PVector	position 			= g.getPosition();
	PVector position_start 		= g.getStartPosition();
	PVector direction 			= g.getDirection();
	float 	speed 				= g.getSpeed();
	long 	duration 			= g.getDuration();
	float 	duration_seconds 	= g.getDurationInSeconds();

	switch(state){
		case 1:	// Start
			break;
		case 2: // Update
			break;
		case 3: // Stop
			println("SwipeGesture: "+id);
			break;
	}
}

// CIRCLE GESTURE
void leapOnCircleGesture(CircleGesture g, int state){
	int 	id 					= g.getId();
	Finger	finger 				= g.getFinger();
	PVector	position_center 	= g.getCenter();
	float	radius 				= g.getRadius();
	float 	progress 			= g.getProgress();
	long 	duration 			= g.getDuration();
	float 	duration_seconds 	= g.getDurationInSeconds();

	switch(state){
		case 1:	// Start
			break;
		case 2: // Update
			break;
		case 3: // Stop
			println("CircleGesture: "+id);
			break;
	}
}

// SCREEN TAP GESTURE
void leapOnScreenTapGesture(ScreenTapGesture g){
	int 	id 					= g.getId();
	Finger	finger 				= g.getFinger();
	PVector	position			= g.getPosition();
	PVector direction			= g.getDirection();
	long 	duration 			= g.getDuration();
	float 	duration_seconds 	= g.getDurationInSeconds();

	println("ScreenTapGesture: "+id);
}

// KEY TAP GESTURE
void leapOnKeyTapGesture(KeyTapGesture g){
	int 	id 					= g.getId();
	Finger	finger 				= g.getFinger();
	PVector	position			= g.getPosition();
	PVector direction			= g.getDirection();
	long 	duration 			= g.getDuration();
	float 	duration_seconds 	= g.getDurationInSeconds();
	
	println("KeyTapGesture: "+id);
}
```


## Examples

* [Basic Data](https://github.com/voidplus/leap-motion-processing/blob/master/examples/e1_basic/e1_basic.pde)
* [Gesture Recognition](https://github.com/voidplus/leap-motion-processing/blob/master/examples/e2_gestures/e2_gestures.pde)

Note: Or try the [OneDollarUnistrokeRecognizer](https://github.com/voidplus/onedollar-unistroke-recognizer) library, a two-dimensional template based gesture recognizer.


## Tested

System:

- **OSX** (*Mac OS 10.7 and higher*)
- **Linux** (*not tested yet, but it should work*) (*Ubuntu Linux 12.04 LTS and Ubuntu 13.04 Raring Ringtail*)
- **Windows** (*not tested yet, but x86 and x64 should work*) (*Windows 7 and 8*)

Processing Version:

- **2.1.2**
- 2.1.1
- 2.1.0
- 2.0.1
- 2.0b9
- 2.0b8
- 2.0b7

Leap Motion Software Version:

* **1.2.0+10933**
* 1.1.3+9188
* 1.1.0+9145
* 1.0.4+7346
* 1.0.2+7287


## Changelog

### ~~v.1.2.0~~

> So far Leap Motion didn't release the developer libraries for SDK v1.2.0 to support the Leap Motion Software v1.2.0+10933.

- ~~Added support for [SDK v.1.2.0](https://developer.leapmotion.com/documentation/Leap_SDK_Release_Notes.html#version-1-2-0)~~


### v.1.1.3.1

- Added methods:
	- ```runInBackground()``` to ```LeapMotion```

### v.1.1.3

- Added support for [SDK v.1.0.9](https://developer.leapmotion.com/documentation/Common/Leap_SDK_Release_Notes#version-1-0-9)

### v.1.1.2

- Added methods:
	-  ```getPosition()``` → ```getRawPosition()``` (to get raw data without mapping)
	- ```hasTools()```, ```getTool(id)```, ```getTools()```, ```countTools()```, ```getFrontHand()```, ```getLeftHand()```, ```getRightHand()```, ```getFrontFinger()```, ```getLeftFinger()```, ```getRightFinger()```, ```getFrontTool()```, ```getLeftTool()``` and ```getRightTool()``` to ```LeapMotion``` 
	- ```getFrontFinger()```, ```getLeftFinger()```, ```getRightFinger()```, ```getFrontTool()```, ```getLeftTool()```and ```getRightTool()``` to ```Hand```

### v.1.1.0

- Added support for [SDK v.0.8.1](https://developer.leapmotion.com/documentation/Common/Leap_SDK_Release_Notes#versnoS-0-8-1)
- Added support for [SDK v.0.8.0](https://developer.leapmotion.com/documentation/Common/Leap_SDK_Release_Notes#version-0-8-0)


## Links

Useful links for developers:

- [Documentation](https://developer.leapmotion.com/documentation)
- [SDK Release Notes](https://developer.leapmotion.com/documentation/Common/Leap_SDK_Release_Notes.html)
- [Knowledge Base](https://developer.leapmotion.com/articles)

## Projects

- [Leap Motion: Ball Maze](http://www.youtube.com/watch?v=I_-UpOYULxw) by David Thomasson
- [Leap Motion: Tagging and capturing physical objects](https://vimeo.com/85337378) by Anouk Hoffmeister and Tom Brewe

## Questions?

Don't be shy and feel free to contact me via [Twitter](http://twitter.voidplus.de).


## License

The library is Open Source Software released under the [License](https://raw.github.com/voidplus/leap-motion-processing/master/LICENSE.txt). It's developed by [Darius Morawiec](http://voidplus.de).
