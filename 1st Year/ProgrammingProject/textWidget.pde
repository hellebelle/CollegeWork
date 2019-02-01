class TextWidget {

  int x, y, width, height, event;
  String label; 
  color widgetColor, labelColor;                                                       
  PFont widgetFont;
  boolean white = false;
  PImage widgetImage;
  PFont font1;
  int mX; 
  int mY;

  TextWidget(int x, int y, int width, int height, String label, int event, color widgetColor) {
    this.x=x;
    this.y=y;
    this.width = width;
    this.height= height;
    this.widgetColor=widgetColor;
    this.label = label;
    this.event = event;
    this.font1  = loadFont("Dubai-Regular-26.vlw");
  }

  void draw() {
    fill(widgetColor);
    rect(x, y, width, height);
    textFont (font1);
    fill(0);
    text(label, x+15, y+24);
  }

  int getEvent(int mX, int mY) {
    //program always returns EVENT_NULL
    if (mX>x && mX < x+width && mY >y && mY <y+height) 
    {
      return event;
    } 
    return EVENT_NULL;
  }
  
  public String getLabel() {
    
    return label;
    
  }
}
