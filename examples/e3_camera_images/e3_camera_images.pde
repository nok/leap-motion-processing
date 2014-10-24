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
