# Leap Motion for Processing

Simple library to use the complete [Leap Motion](https://leapmotion.com/) [API](https://developer.leapmotion.com/documentation/api/annotated) in [Processing](http://processing.org/).


## About

The Leap detects and tracks hands, fingers and finger-like tools. The device operates in an intimate proximity with high precision and tracking frame rate.

The Leap software analyzes the objects observed in the device field of view. It recognizes hands, fingers, and tools, reporting both discrete positions and motion. The Leap field of view is an inverted pyramid centered on the device. The effective range of the Leap extends from approximately 25 to 600 millimeters above the device (1 inch to 2 feet).


## Download

- [Leap Motion for Processing v2.0.3 BETA](https://raw.github.com/voidplus/leap-motion-processing/beta/download/LeapMotionForProcessing.zip)


## Installation

Unzip and put the extracted *LeapMotionForProcessing* folder into the libraries folder of your Processing sketches. Reference and examples are included in the *LeapMotionForProcessing* folder.


## Dependencies

- [Leap Motion Software](https://developer.leapmotion.com/) **2.0.3+17004 BETA**


## Usage


### Basic Data-Access

![Snapshot](https://raw.github.com/voidplus/leap-motion-processing/beta/reference/hand.jpg)

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

    // === HANDS
    for(Hand hand : leap.getHands()){

        // --- Basics
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

        // --- Specific fingers
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

        // --- Drawing
        // hand.drawSphere();
        hand.draw();

        // === ARM
        if(hand.hasArm()){
            Arm     arm               = hand.getArm();
            float   arm_width         = arm.getWidth();
            PVector arm_wrist_pos     = arm.getWristPosition();
            PVector arm_elbow_pos     = arm.getElbowPosition();
        }

        // === FINGERS (all)
        for(Finger finger : hand.getFingers()){
      
            // --- Basics
            int     finger_id         = finger.getId();
            PVector finger_position   = finger.getPosition();
            PVector finger_stabilized = finger.getStabilizedPosition();
            PVector finger_velocity   = finger.getVelocity();
            PVector finger_direction  = finger.getDirection();
            float   finger_time       = finger.getTimeVisible();

            // --- Find specific finger
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
      
            // --- Drawing
            // finger.draw(); // = drawLines()+drawJoints()
            // finger.drawLines();
            // finger.drawJoints();

            // --- Touch emulation
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
    
        // === TOOLS
        for(Tool tool : hand.getTools()){
      
            // --- Basics
            int     tool_id           = tool.getId();
            PVector tool_position     = tool.getPosition();
            PVector tool_stabilized   = tool.getStabilizedPosition();
            PVector tool_velocity     = tool.getVelocity();
            PVector tool_direction    = tool.getDirection();
            float   tool_time         = tool.getTimeVisible();
      
            // --- Drawing
            // tool.draw();
      
            // --- Touch emulation
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
  
    // === DEVICES
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

- **2.2.1**
- 2.1.2
- 2.1.1
- 2.1.0
- 2.0.1
- 2.0b9
- 2.0b8
- 2.0b7

Leap Motion Software Version:

* **2.0.3+17004 BETA**
* 2.0.2+16391 BETA
* 2.0.1+15831 BETA
* 2.0.0+13819 BETA
* ...


## Changelog

### v2.0.3 BETA

- Added support for SDK 2.0.3+17004 BETA
	- Updated libraries
- Added new class:
	- Class ```Arm```
- Added public methods:
	- Class ```Arm```
		- ```boolean isValid()```
		- ```PVector getWristPosition()```
		- ```PVector getWristRawPosition()```
		- ```PVector getElbowPosition()```
		- ```PVector getElbowRawPosition()```
		- ```float getWidth()```
	- Class ```Hand```
		- ```Arm getArm()```
		- ```boolean hasArm()```
	- Class ```LeapMotion```
		- ```LeapMotion moveWorld(Integer x, Integer y, Integer z)```
		- ```LeapMotion moveWorld(PVector origin)```

### v2.0.2.2 BETA

- Added public methods:
	- Class ```Hand```
		- ```boolean isValid()```
	- Class ```Bone```
		- ```boolean isValid()```
	- Class ```Finger```
		- ```boolean isValid()```
	- Class ```Tool```
		- ```boolean isValid()```
- Changed public methods:
	- Class ```LeapMotion```
		- ```Hand getHand(Integer id)```
		- ```ArrayList<Hand> getHands()```	
		- ```Hand getFrontHand()```
		- ```Hand getLeftHand()```
		- ```Hand getRightHand()```
		- ```Finger getFinger(Integer id)```
		- ```ArrayList<Finger> getFingers()```	
		- ```Tool getTool(Integer id)```
		- ```ArrayList<Tool> getTools()```

### v2.0.2 BETA

- Added support for SDK 2.0.2+16391 BETA
	- Updated libraries

### v2.0.1 BETA

- Added support for SDK v2.0.1+15831 BETA
	- Updated libraries
	- Added new Bone API
- Added new class:
	- Class ```Bone```
- Added public methods:
	- Class ```Bone```
		- ```float getLength()```
		- ```float getWidth()```
		- ```PVector getNextJoint()``` and ```PVector getRawNextJoint()```
		- ```PVector getPrevJoint()``` and ```PVector getRawPrevJoint()```
		- ```int getType()``` (0-3, 0=distal, 1=intermediate, 2=proximal, 3=metacarpal)
		- ```PVector getDirection()``` and ```PVector getRawDirection()```
		- ```void draw()``` and ```void draw(boolean pre)```
	- Class ```Hand```
		- ```float getWidth()```
	- Class ```Finger```
		- ```Bone getBone(int index)``` (0-3, 0=distal, 1=intermediate, 2=proximal, 3=metacarpal)
		- ```Bone getBone(int name)``` ("distal", "intermediate", "proximal", "metacarpal")
		- ```Bone getDistalBone()```
		- ```Bone getIntermediateBone()```
		- ```Bone getMetacarpalBone()```
		- ```Bone getProximalBone()```
		- ```void drawBones()``` and ```void drawBones(boolean pre)```
	- Class ```LeapMotion```
		- ```long getId()```
		- ```long getTimestamp()```
- Changed public methods:
	- Class ```Finger```
		- ```void draw()```
		- ```void draw(int radius)```
		- ```void draw(boolean pre)```
		- ```void draw(int radius, boolean pre)```
		- ```void drawJoints()```
		- ```void drawJoints(boolean pre)```
		- ```void drawLines()```
		- ```void drawLines(int radius)```
		- ```void drawLines(boolean pre)```
		- ```void drawLines(int radius, boolean pre)```
	- Class ```Hand```
		- ```void draw()```
		- ```void draw(int radius)```
		- ```void draw(boolean pre)```
		- ```void draw(int radius, boolean pre)```
		- ```void drawSphere()```
		- ```void drawSphere(boolean pre)```
		- ```void drawFingers()```
		- ```void drawFingers(int radius)```
		- ```void drawFingers(boolean pre)```
		- ```void drawFingers(int radius, boolean pre)```
- Fixed [Gestures in Processing](https://community.leapmotion.com/t/gestures-in-processing/873/11)
			
### v2.0.0 BETA

- Added support for SDK v2.0.0+13819 BETA
- Added public methods:
	- Class ```Hand```
		- ```Finger getFinger(int index)``` (0-4, 0=thumb, 1=index, 2=middle, 3=ring, 4=pinky)
		- ```Finger getFinger(String name)``` ("thumb", "index", "middle", "ring", "pinky")
		- ```Finger getThumb()```
		- ```Finger getIndexFinger()```
		- ```Finger getMiddleFinger()```
		- ```Finger getRingFinger()```
		- ```Finger getPinkyFinger()```
		- ```float getConfidence()```
		- ```boolean isLeft()```
		- ```boolean isRight()```
		- ```float getGrabStrength()``` ([0..1])
		- ```float getPinchStrength()``` ([0..1])
		- ```void drawSphere()```
		- ```void drawFingers()```
	- Class ```Finger```
		- ```PVector getPositionOfJointTip()``` and ```PVector getRawPositionOfJointTip()```
		- ```PVector getPositionOfJointMcp()``` and ```PVector getRawPositionOfJointMcp()```
		- ```PVector getPositionOfJointPip()``` and ```PVector getRawPositionOfJointPip()```
		- ```PVector getPositionOfJointDip()``` and ```PVector getRawPositionOfJointDip()```
		- ```void getType()``` (0-4, 0=thumb, 1=index, 2=middle, 3=ring, 4=pinky)
		- ```void drawLines()```
		- ```void drawJoints()```
	- Class ```Tool```
		- ```PVector getTipPosition()```
		- ```void draw()```
- Changed public methods:
	- Class ```Hand```
		- ```void draw()```
	- Class ```Finger```
		- ```void draw()```
	- Class ```Tool```
		- ```void draw()```
- Removed public methods:
	- Class ```Finger```
		- ```void drawDirection()```

### ~~v1.2.0~~

> So far Leap Motion didn't release the developer libraries for SDK v.1.2.0 to support the Leap Motion Software v.1.2.0+10933.

- ~~Added support for [SDK v.1.2.0](https://developer.leapmotion.com/documentation/Leap_SDK_Release_Notes.html#version-1-2-0)~~


### v1.1.3.1

- Added public method:
	- ```LeapMotion runInBackground(boolean active)``` to class ```LeapMotion```

### v1.1.3

- Added support for [SDK v.1.0.9](https://developer.leapmotion.com/documentation/Common/Leap_SDK_Release_Notes#version-1-0-9)

### v1.1.2

- Added public methods:
	-  ```PVector getPosition()``` → ```PVector getRawPosition()``` (to get raw data without mapping)
	- ```boolean hasTools()```, ```Tool getTool(id)```, ```ArrayList<Tool> getTools()```, ```int countTools()```, ```Hand getFrontHand()```, ```Hand getLeftHand()```, ```Hand getRightHand()```, ```Finger getFrontFinger()```, ```Finger getLeftFinger()```, ```Finger getRightFinger()```, ```Tool getFrontTool()```, ```Tool getLeftTool()``` and ```Tool getRightTool()``` to class ```LeapMotion``` 
	- ```Finger getFrontFinger()```, ```Finger getLeftFinger()```, ```Finger getRightFinger()```, ```Tool getFrontTool()```, ```Tool getLeftTool()```and ```Tool getRightTool()``` to class ```Hand```

### v1.1.0

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