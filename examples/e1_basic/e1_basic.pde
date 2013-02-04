import com.leapmotion.leap.*;
import de.voidplus.leapmotion.*;

LeapMotion leap;
Frame frame;

void setup(){
  size(800, 800, PApplet.P3D);
  background(0);
  noFill();
  sphereDetail(20);
  smooth();
  leap = new LeapMotion(this);
}

void draw(){
  background(0);
  
  if (frame != null) {
    System.out.println("Frame id: " + frame.id() + ", timestamp: "
        + frame.timestamp() + ", hands: " + frame.hands().count()
        + ", fingers: " + frame.fingers().count() + ", tools: "
        + frame.tools().count());

    if (!frame.hands().empty()) {
      
      // Get the first hand
      Hand hand = frame.hands().get(0);
      PVector posHand = leap.map(hand.palmPosition());
      
      // Draw the sphere of the hand
      if(hand.fingers().count()>=3){
        PVector posSphere = leap.map(hand.sphereCenter());
        stroke(255,20);
        pushMatrix();
          translate(posSphere.x, posSphere.y, posSphere.z);
          sphereDetail(50);
          sphere(hand.sphereRadius());
        popMatrix();
      }
      
      // Draw the position of the hand
      stroke(255,100);
      pushMatrix();
        translate(posHand.x, posHand.y, posHand.z);
        sphereDetail(20);
        sphere(5);
      popMatrix();
      
      // Check if the hand has any fingers
      FingerList fingers = hand.fingers();
      if (!fingers.empty()) {
        
        Vector avgPos = Vector.zero();
        for (Finger finger : fingers) {
          
          // Calculate the hand's average finger tip position
          PVector posFinger = leap.map(finger.tipPosition());
          avgPos = avgPos.plus(finger.tipPosition());
          
          // Draw all finger tips
          stroke(255);
          pushMatrix();
            translate(posFinger.x, posFinger.y, posFinger.z);
            sphere(6);
          popMatrix();
        }
        avgPos = avgPos.divide(fingers.count());
        PVector posAvgFinger = leap.map(avgPos);
        
        // Draw the average finger tip position
        pushMatrix();
          translate( posAvgFinger.x, posAvgFinger.y, posAvgFinger.z);
          sphereDetail( 5 );
          sphere( 3 );
        popMatrix();
        
        System.out.println("Hand has " + fingers.count()
            + " fingers, average finger tip position: "
            + avgPos);
      }

      // Get the hand's sphere radius and palm position
      System.out.println("Hand sphere radius: " + hand.sphereRadius()
          + " mm, palm position: " + hand.palmPosition());

      // Get the hand's normal vector and direction
      Vector normal = hand.palmNormal();
      Vector direction = hand.direction();

      // Calculate the hand's pitch, roll, and yaw angles
      System.out.println("Hand pitch: "
          + Math.toDegrees(direction.pitch()) + " degrees, "
          + "roll: " + Math.toDegrees(normal.roll())
          + " degrees, " + "yaw: "
          + Math.toDegrees(direction.yaw()) + " degrees\n");
    }
  }
  
}

void leapOnInit(Controller controller) {
    System.out.println("Initialized");
}

void leapOnConnect(Controller controller) {
    System.out.println("Connected");
}

void leapOnDisconnect(Controller controller) {
    System.out.println("Disconnected");
}

void leapOnExit(Controller controller) {
    System.out.println("Exited");
}

void leapOnFrame(Controller controller){
    // Get the most recent frame and report some basic information
    frame = controller.frame();    
}
