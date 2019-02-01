class Widget {

  int x, y, width, height;
  String label; 
  int event;
  color widgetColor, labelColor;
  PFont widgetFont;
  boolean white = false;
  PImage widgetImage;
  PFont font1;
  int mX; 
  int mY;

  Widget(int x, int y, int width, int height, PImage widgetImage, int event) {

    this.x=x;
    this.y=y;
    this.width = width;
    this.height= height;
    this.widgetImage=widgetImage;
    this.event=event; 
  }

  Widget(int x, int y, int width, int height, String label, int event, color widgetColor) {

    this.x=x;
    this.y=y;
    this.width = width;
    this.height= height;
    this.widgetColor=widgetColor;
    this.label = label;
    this.event = event;
  }


  void draw() {

    if (widgetImage!=null) {
      image(widgetImage, x, y);
    }

     else {
     fill(widgetColor);
     rect(x, y, width, height);
     //textFont (font1);
     fill(0);
     textSize(12.5);
     text(label, x+15, y+15);
     }
  }

  int getEvent(int mX, int mY) {

    if (mX>x && mX < x+width && mY >y && mY <y+height) {
      return event;
    }
    else{
       return EVENT_NULL;
    }
   
  }

  void mousePressed() {

    int event;  
    if (currentScreen==homeScreen) {
      event = getEvent(mouseX, mouseY);
      if (event == EVENT_BUTTON1) {

        println("button 1 pressed");
        currentScreen = screen5;
        isVisible1 = false;		
        isVisible2 = true; 		
        controller.setVisible(isVisible1);		
        controller1.setVisible(isVisible2);        
        println("gone to screen reviews");
      }
     
      else if (event == EVENT_BUTTON2) {

        println("button 2 pressed");
        currentScreen = topBusinessScreen;
        isVisible1 = false;		
        isVisible2 = true; 		
        controller.setVisible(isVisible1);		
        controller1.setVisible(isVisible2);        
        println("gone to screen 2");
      }

      else if (event== EVENT_BUTTON3) {

        currentScreen = screen3;
        println("button 3 pressed");
        isVisible1 = false;
        isVisible2 = true; 
        controller.setVisible(isVisible1);
        controller1.setVisible(isVisible2);
        println("gone to screen 3");
      }
    } 
  }
}
