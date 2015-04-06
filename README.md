# Leap Motion for Processing

Simple library to use the complete [Leap Motion](https://leapmotion.com/) [SDK](https://developer.leapmotion.com/documentation/java/index.html) in [Processing](http://processing.org/).


## Table of Contents

- [About](#about)
- [Download](#download)
- [Installation](#installation)
- [Dependencies](#dependencies)
- [Tested](#tested)
- [Examples](#examples)
- [Usage](#usage)
- [Changelog](#changelog)
- [Questions?](#questions)
- [License](#license)


## About

The Leap detects and tracks hands, fingers and finger-like tools. The device operates in an intimate proximity with high precision and tracking frame rate.

The Leap software analyzes the objects observed in the device field of view. It recognizes hands, fingers, and tools, reporting both discrete positions and motion. The Leap field of view is an inverted pyramid centered on the device. The effective range of the Leap extends from approximately 25 to 600 millimeters above the device (1 inch to 2 feet).


## Download

- [Leap Motion for Processing v2.2.4.1](download/LeapMotionForProcessing.zip?raw=true)

Note: If you are interested in the newest **development** implementation, so have a look at the [dev branch](https://github.com/nok/leap-motion-processing/tree/dev).


## Installation

Unzip and put the extracted *LeapMotionForProcessing* folder into the libraries folder of your Processing sketches. Reference and examples are included in the *LeapMotionForProcessing* folder. For further help read the [tutorial](http://www.learningprocessing.com/tutorials/libraries/) by [Daniel Shiffman](https://github.com/shiffman).


## Dependencies

- [Leap Motion Software](https://developer.leapmotion.com/) **2.2.4+26750**


## Tested

System:

- **OSX** (*Mac OS 10.7 and higher*)
- **Linux** (*not tested yet, but it should work*) (*Ubuntu Linux 12.04 LTS and Ubuntu 13.04 Raring Ringtail*)
- **Windows** (*not tested yet, but x86 and x64 should work*) (*Windows 7 and 8*)

Processing version:

- **3.0a5**
- **2.2.1**
- 2.1.2
- 2.1.1
- 2.1.0
- 2.0.1
- 2.0b9
- 2.0b8
- 2.0b7

Leap Motion Software version:

* **2.2.4+26750**
* 2.2.3+25971
* 2.2.1+24116
* 2.2.0+23475
* 2.1.6+23110
* 2.1.5+22699
* 2.0.5+18024 BETA
* 2.0.4+17546 BETA
* 2.0.3+17004 BETA
* 2.0.2+16391 BETA
* 2.0.1+15831 BETA
* 2.0.0+13819 BETA
* ...


## Examples

* [Basic Data-Access](#basic-data-access) → [e1_basic.pde](examples/e1_basic/e1_basic.pde)
* [Gesture-Recognition](#gesture-recognition) → [e2_gestures.pde](examples/e2_gestures/e2_gestures.pde)
* [Camera-Images](#camera-images) → [e3_camera_images.pde](examples/e3_camera_images/e3_camera_images.pde)


## Usage

### Basic Data-Access

Before you start to code I recommend you to read the [official basic introduction](https://developer.leapmotion.com/documentation/java/devguide/Leap_Overview.html).

```java
import de.voidplus.leapmotion.*;

LeapMotion leap;

void setup(){
    size(800, 500, OPENGL);
    background(255);
    // ...

    leap = new LeapMotion(this);
}

void draw(){
    background(255);
    // ...
    int fps = leap.getFrameRate();


    // ========= HANDS =========

    for(Hand hand : leap.getHands()){


        // ----- BASICS -----

        int     hand_id          = hand.getId();
        PVector hand_position    = hand.getPosition();
        PVector hand_stabilized  = hand.getStabilizedPosition();
        PVector hand_direction   = hand.getDirection();
        PVector hand_dynamics    = hand.getDynamics();
        float   hand_roll        = hand.getRoll();
        float   hand_pitch       = hand.getPitch();
        float   hand_yaw         = hand.getYaw();
        boolean hand_is_left     = hand.isLeft();
        boolean hand_is_right    = hand.isRight();
        float   hand_grab        = hand.getGrabStrength();
        float   hand_pinch       = hand.getPinchStrength();
        float   hand_time        = hand.getTimeVisible();
        PVector sphere_position  = hand.getSpherePosition();
        float   sphere_radius    = hand.getSphereRadius();


        // ----- SPECIFIC FINGER -----

        Finger  finger_thumb     = hand.getThumb();
        // or                      hand.getFinger("thumb");
        // or                      hand.getFinger(0);

        Finger  finger_index     = hand.getIndexFinger();
        // or                      hand.getFinger("index");
        // or                      hand.getFinger(1);

        Finger  finger_middle    = hand.getMiddleFinger();
        // or                      hand.getFinger("middle");
        // or                      hand.getFinger(2);

        Finger  finger_ring      = hand.getRingFinger();
        // or                      hand.getFinger("ring");
        // or                      hand.getFinger(3);

        Finger  finger_pink      = hand.getPinkyFinger();
        // or                      hand.getFinger("pinky");
        // or                      hand.getFinger(4);


        // ----- DRAWING -----

        hand.draw();
        // hand.drawSphere();


        // ========= ARM =========

        if(hand.hasArm()){
            Arm     arm               = hand.getArm();
            float   arm_width         = arm.getWidth();
            PVector arm_wrist_pos     = arm.getWristPosition();
            PVector arm_elbow_pos     = arm.getElbowPosition();
        }


        // ========= FINGERS =========

        for(Finger finger : hand.getFingers()){


            // ----- BASICS -----

            int     finger_id         = finger.getId();
            PVector finger_position   = finger.getPosition();
            PVector finger_stabilized = finger.getStabilizedPosition();
            PVector finger_velocity   = finger.getVelocity();
            PVector finger_direction  = finger.getDirection();
            float   finger_time       = finger.getTimeVisible();


            // ----- SPECIFIC FINGER -----

            switch(finger.getType()){
                case 0:
                    // System.out.println("thumb");
                    break;
                case 1:
                    // System.out.println("index");
                    break;
                case 2:
                    // System.out.println("middle");
                    break;
                case 3:
                    // System.out.println("ring");
                    break;
                case 4:
                    // System.out.println("pinky");
                    break;
            }


            // ----- SPECIFIC BONE -----

            Bone    bone_distal       = finger.getDistalBone();
            // or                       finger.get("distal");
            // or                       finger.getBone(0);

            Bone    bone_intermediate = finger.getIntermediateBone();
            // or                       finger.get("intermediate");
            // or                       finger.getBone(1);

            Bone    bone_proximal     = finger.getProximalBone();
            // or                       finger.get("proximal");
            // or                       finger.getBone(2);

            Bone    bone_metacarpal   = finger.getMetacarpalBone();
            // or                       finger.get("metacarpal");
            // or                       finger.getBone(3);


            // ----- DRAWING -----

            // finger.draw(); // = drawLines()+drawJoints()
            // finger.drawLines();
            // finger.drawJoints();


            // ----- TOUCH EMULATION -----

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


        // ========= TOOLS =========

        for(Tool tool : hand.getTools()){


            // ----- BASICS -----

            int     tool_id           = tool.getId();
            PVector tool_position     = tool.getPosition();
            PVector tool_stabilized   = tool.getStabilizedPosition();
            PVector tool_velocity     = tool.getVelocity();
            PVector tool_direction    = tool.getDirection();
            float   tool_time         = tool.getTimeVisible();


            // ----- DRAWING -----

            // tool.draw();


            // ----- TOUCH EMULATION -----

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


    // ========= DEVICES =========

    for(Device device : leap.getDevices()){
        float device_horizontal_view_angle = device.getHorizontalViewAngle();
        float device_verical_view_angle = device.getVerticalViewAngle();
        float device_range = device.getRange();
    }

}

// ========= CALLBACKS =========

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

![Snapshot](reference/leap_gestures.jpg)

> Source: [Leap Motion](https://developer.leapmotion.com/documentation/skeletal/java/devguide/Leap_Overview.html#gestures)

```java
import de.voidplus.leapmotion.*;

LeapMotion leap;

void setup(){
    size(800, 500, OPENGL);
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


// ----- SWIPE GESTURE -----

void leapOnSwipeGesture(SwipeGesture g, int state){
	int 	id 					= g.getId();
	Finger	finger 				= g.getFinger();
	PVector	position 			= g.getPosition();
	PVector position_start 		= g.getStartPosition();
	PVector direction 			= g.getDirection();
	float 	speed 				= g.getSpeed();
	long 	duration 			= g.getDuration();
    float   duration_seconds 	= g.getDurationInSeconds();

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


// ----- CIRCLE GESTURE -----

void leapOnCircleGesture(CircleGesture g, int state){
	int 	id 					= g.getId();
	Finger	finger 				= g.getFinger();
	PVector	position_center 	= g.getCenter();
	float	radius 				= g.getRadius();
	float 	progress 			= g.getProgress();
	long 	duration 			= g.getDuration();
	float 	duration_seconds 	= g.getDurationInSeconds();
    int     direction          = g.getDirection();

	switch(state){
		case 1:	// Start
			break;
		case 2: // Update
			break;
		case 3: // Stop
			println("CircleGesture: "+id);
			break;
	}

    switch(direction){
        case 0: // Anticlockwise/Left gesture
            break;
        case 1: // Clockwise/Right gesture
            break;
    }
}


// ----- SCREEN TAP GESTURE -----

void leapOnScreenTapGesture(ScreenTapGesture g){
	int 	id 					= g.getId();
	Finger	finger 				= g.getFinger();
	PVector	position			= g.getPosition();
	PVector direction			= g.getDirection();
	long 	duration 			= g.getDuration();
	float 	duration_seconds 	= g.getDurationInSeconds();

	println("ScreenTapGesture: "+id);
}


// ----- KEY TAP GESTURE -----

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

### Camera-Images

```java
import de.voidplus.leapmotion.*;

LeapMotion leap;

void setup(){
	size(640, 480, OPENGL);
	background(255);  
	leap = new LeapMotion(this);
}

void draw(){
	background(255);

	// ========= CAMERA IMAGES =========

	if (leap.hasImages()) {
		for (Image camera : leap.getImages()) {
			if (camera.isLeft()) {
				// left camera
				image(camera, 0, 0);
			} else {
				// right camera
				image(camera, 0, camera.getHeight());
			}
		}
	}

}
```


## Changelog

You can find the changes in the [release](https://github.com/nok/leap-motion-processing/releases) section.


## Questions?

Don't be shy and feel free to contact me on Twitter: [@darius_morawiec](https://twitter.com/darius_morawiec)


## License

The library is Open Source Software released under the [License](LICENSE.txt).
