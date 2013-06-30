# Leap Motion Processing

Simple library to use the complete [Leap Motion](https://leapmotion.com/) [API](https://developer.leapmotion.com/documentation/api/annotated) in [Processing](http://processing.org/).

## About

The Leap detects and tracks hands, fingers and finger-like tools. The device operates in an intimate proximity with high precision and tracking frame rate.

The Leap software analyzes the objects observed in the device field of view. It recognizes hands, fingers, and tools, reporting both discrete positions and motion. The Leap field of view is an inverted pyramid centered on the device. The effective range of the Leap extends from approximately 25 to 600 millimeters above the device (1 inch to 2 feet).

## Download

* [LeapMotion-Processing v1.0.0](https://raw.github.com/voidplus/leap-motion-processing/master/download/LeapMotion-Processing.zip)

## Installation

Unzip and put the extracted *LeapMotion* folder into the libraries folder of your Processing sketches. Reference and examples are included in the *LeapMotion* folder.

## Dependencies

* [LeapMotion SDK v0.7.9](https://developer.leapmotion.com/downloads/leap-motion/sdk)

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

	// HANDS
	for(Hand hand : leap.getHands()){

		hand.draw();
		int     hand_id          = hand.getId();
		PVector hand_position    = hand.getPosition();
		PVector hand_direction   = hand.getDirection();
		PVector hand_dynamics    = hand.getDynamics();
		float   hand_roll        = hand.getRoll();
		float   hand_pitch       = hand.getPitch();
		float   hand_yaw         = hand.getYaw();
		PVector sphere_position  = hand.getSpherePosition();
		float   sphere_radius    = hand.getSphereRadius();
		
		// FINGERS
		for(Finger finger : hand.getFingers()){
			finger.draw();
			int     finger_id         = finger.getId();
			PVector finger_position   = finger.getPosition();
			PVector finger_velocity   = finger.getVelocity();
			PVector finger_direction  = finger.getDirection();
		}
		
		// TOOLS
		for(Tool tool : hand.getTools()){
			tool.draw();
			int     tool_id           = tool.getId();
			PVector tool_position     = tool.getPosition();
			PVector tool_velocity     = tool.getVelocity();
			PVector tool_direction    = tool.getDirection();
		}
		
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

* [Basic](https://github.com/voidplus/leap-motion-processing/blob/master/examples/e1_basic/e1_basic.pde)
* [Gestures](https://github.com/voidplus/leap-motion-processing/blob/master/examples/e2_gestures/e2_gestures.pde)
* …

<!--
With other libraries/dependencies:

* Gestures-Recognition with the [OneDollarUnistrokeRecognizer Processing Library](https://github.com/voidplus/onedollar-unistroke-recognizer).
-->

## Tested

System:

* OSX
* Windows (*not tested yet, but x86 and x64 should work*)

Processing Version:

* 2.0b7
* 2.0b8
* 2.0b9

## Links
Useful links for developers:

* [Getting started](https://developer.leapmotion.com/documentation)
* [Documentation](https://developer.leapmotion.com/documentation/guide/Leap_Overview)
* [Knowledge Base](https://developer.leapmotion.com/articles)

## Questions?

Don't be shy and feel free to contact me via [Twitter](http://twitter.voidplus.de).

## License

The library is Open Source Software released under the [MIT License](https://raw.github.com/voidplus/leap-motion-processing/master/LICENSE.txt). It's developed by [Darius Morawiec](http://voidplus.de).