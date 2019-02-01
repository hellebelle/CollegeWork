class Screen { //<>// //<>//

  color backgroundCol;
  PImage img;
  GUIController controller;
  ArrayList<Widget>widgets;
  ArrayList<TextWidget>tWidgets;
  int event;
  int startPos = 50;
  Screen(PImage img, GUIController controller) {

    this.img = img;
    this.controller = controller;
    this.widgets = new ArrayList<Widget>();
    this.tWidgets = new ArrayList<TextWidget>();
  }

  Screen(color backgroundCol, GUIController controller) {

    this.backgroundCol = backgroundCol;
    this.controller = controller;
    this.widgets = new ArrayList<Widget>();
    this.tWidgets = new ArrayList<TextWidget>();
  }

  void addWidgets(Widget widget) {  
    widgets.add(widget);
  }

  void addTextWidget(TextWidget tw) {
    tWidgets.add(tw);
  }

  void draw() {
    if (backgroundCol!=0) {
      background(backgroundCol);
    } else {
      background(img);
    }

    for (int i =0; i<widgets.size(); i++) {
      Widget widget = widgets.get(i);
      widget.draw();
    }



    if (currentScreen==homeScreen) {
      fill(white);
      noStroke();
      rect(width/2-180, 9, 280, 20);
      rect(0, 350, 959, 25);
      fill(0);
      text("Type in a user name or a business name", width/2-179, 25);
      textSize(15);
      text("Reviews", 60, 365);
      text("Users to Explore", width/2-60, 365);
      text("Businesses to Explore", width-180, 365);
    }

    if (currentScreen == screen2) {
    }

    if (currentScreen == screen3)
    {
      background(grey);
      fill(0);
      stroke(1);
      text("Users", width/2-60, 25);
      for (int i =0; i<tWidgets.size(); i++) { 
        TextWidget tWidget =tWidgets.get(i);
        tWidget.draw();
      }
    }

    if (currentScreen == topBusinessScreen)
    {
      background(grey);
      fill(0);
      stroke(1);
      text("Businesses", width/2-60, 25);
      for (int i =0; i<tWidgets.size(); i++) { 
        TextWidget tWidget =tWidgets.get(i);
        tWidget.draw();
      }
    }

    if (currentScreen == screen5)
    {
      currentScreen = revSc;
    }
  }

  void mousePressed() {
    if (currentScreen == homeScreen) {
      for (int i = 0; i<widgets.size(); i++)
      {
        Widget widget = widgets.get(i);
        widget.mousePressed();
      }
    }
    if (currentScreen == screen3||currentScreen == topBusinessScreen) {
      for (int i = 0; i<tWidgets.size(); i++) {
        TextWidget tWidget = tWidgets.get(i);
            //as getEvent(x,y) does not work properly this method could not be implemented.
        //event = tWidget.getEvent(mouseX, mouseY);
        //switch(event) {
        //case EVENT_BUTTON5:

        /*boolean done = false;
         while (!done)
         {
         for (int j = 0; j<uScreens.size(); j++)
         {        
         UserScreen screen = uScreens.get(j);
         if (tWidget.getLabel().equalsIgnoreCase(screen.getUserName()))
         {
         println("found userScreen");
         currentScreen = screen;
         visible1 = false;
         visible2 = true; 
         c.setVisible(visible1);
         c1.setVisible(visible2);
         println("gone to screen 2");
         screen.setupUScreen();
         done = true;
         break;
         }
         }
         }*/
      }
    }
  }
}
