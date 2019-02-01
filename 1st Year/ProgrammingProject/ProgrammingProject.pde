import interfascia.*;
import java.util.*;
import java.text.SimpleDateFormat;


GUIController controller, controller1, controller4;
IFTextField textbox;
IFLabel label;
IFButton search, home, next;
PImage MyImage, WTS, BUS, USR;
color grey, white;
Screen homeScreen, screen2, screen3, topBusinessScreen, screen5, currentScreen;
ReviewScreen revSc;
Widget widget1, widget2, widget3, widget4;
String[] dataSet;
ArrayList<Review> reviews;
ArrayList<String>businesses, users;
ArrayList<Widget>widgets;
ArrayList<TextWidget>userWidgets, businessWidgets;
ArrayList<BusinessScreen>bScreens;
ArrayList<UserScreen>uScreens;
boolean isVisible1, isVisible2;
Table dataTable;

PieChart piechart;
int [] array;
PFont myFont, font2;


void setup()
{
  size(959, 542);
  MyImage = loadImage("data/watch-this-space.jpg");
  WTS = loadImage("data/WTS.jpg");
  BUS = loadImage("data/BUS.jpg");
  USR = loadImage("data/USR.jpg");
  myFont = createFont("data/Dubai-Regular", 32);
  textFont(myFont);
  MyImage.resize(width, height);
  dataTable = loadTable("data/reviews_cleaned_no_quotes.csv", "header");
  reviews = new ArrayList<Review>();
  businesses = new ArrayList<String>();
  users =  new ArrayList<String>();
  widgets =  new ArrayList<Widget>();
  userWidgets = new ArrayList<TextWidget>();
  businessWidgets = new ArrayList<TextWidget>();

  addReviewsToList();
  getBusinesses(reviews);
  getUsers(reviews);

  bScreens = loadBusinessScreens(businesses, reviews, controller1, grey);
  uScreens = loadUserScreens(users, reviews, controller1, grey);  


  grey = color(220);
  white = color(255);
  homeScreen = new Screen(MyImage, controller);
  screen2 = new Screen(grey, controller1);
  screen3 = new Screen(grey, controller1);
  topBusinessScreen = new Screen(grey, controller1);
  screen5 = new Screen(grey, controller4);
  currentScreen = homeScreen; 
  revSc = new ReviewScreen(grey, controller1, reviews);

  isVisible1 = true;
  isVisible2 = false;

  controller = new GUIController (this, isVisible1);
  controller1 = new GUIController (this, isVisible2);


  search = new IFButton ("Search", 845, 31, 80, 19);
  home = new IFButton ("Home", 0, 0, 49, 17);

  textbox = new IFTextField("Text Field", 30, 30, 800, "");
  label = new IFLabel("", 55, 102);

  widget1=new Widget(30, 391, width-830, 391, WTS, EVENT_BUTTON1);
  widget3=new Widget(400, 391, width-830, height, USR, EVENT_BUTTON3);
  widget4=new Widget(790, 391, width, height, BUS, EVENT_BUTTON2);

  homeScreen.addWidgets(widget1);
  homeScreen.addWidgets(widget3);
  homeScreen.addWidgets(widget4);
  makeTWidgets();

  controller.add (search);
  controller1.add(home);
  controller.add(textbox);
  controller.add(label);

  textbox.addActionListener(this);
  search.addActionListener(this);
  home.addActionListener(this);

}

void draw() {
  currentScreen.draw();
}
void mousePressed() {  
  currentScreen.mousePressed();
}

void actionPerformed(GUIEvent e) {  
  if (e.getSource() == search) {
    boolean finished = false;
    while (!finished) {
      for (int i = 0; i<businesses.size(); i++)
      {
        String business = businesses.get(i);
        if (textbox.getValue().equalsIgnoreCase(business))
        {
          boolean done = false;
          while (!done)
          {
            for (int j = 0; j<bScreens.size(); j++)
            {
              BusinessScreen screen = bScreens.get(j);
              if (business.equalsIgnoreCase(screen.getBusinessName()))
              {
                currentScreen = screen;
                isVisible1 = false;
                isVisible2 = true; 
                controller.setVisible(isVisible1);
                controller1.setVisible(isVisible2);
                println("gone to screen 2");
                screen.setupBScreen();
                done = true;
              }
            }
          }
          finished = true;
        }
        if (!finished)
        {
          for (int k = 0; k<users.size(); k++)
          {
            String user = users.get(k);
            if (textbox.getValue().equalsIgnoreCase(user))
            {
              boolean done = false;
              while (!done)
              {
                for (int l = 0; l<uScreens.size(); l++)
                {
                  UserScreen screen = uScreens.get(l);
                  if (user.equalsIgnoreCase(screen.getUserName()))
                  {
                    currentScreen = screen;
                    isVisible1 = false;
                    isVisible2 = true; 
                    controller.setVisible(isVisible1);
                    controller1.setVisible(isVisible2);
                    println("gone to screen 2");
                    screen.setupUScreen();
                    done = true;
                  }
                }
              }
              finished = true;
            } else {
              finished = true;
            }
          }
        }
      }
    }
  }

  if (e.getSource() == home) {
    currentScreen = homeScreen;
    isVisible1 = true;
    isVisible2 = false; 
    controller.setVisible(isVisible1);
    controller1.setVisible(isVisible2);
    println("gone to screen 1");
  }

  if (e.getMessage().equals("Completed")) {
    boolean finished = false;
    while (!finished) {
      for (int i = 0; i<businesses.size(); i++)
      {
        String business = businesses.get(i);
        if (textbox.getValue().equalsIgnoreCase(business))
        {
          boolean done = false;
          while (!done)
          {
            for (int j = 0; j<bScreens.size(); j++)
            {
              BusinessScreen screen = bScreens.get(j);
              if (business.equalsIgnoreCase(screen.getBusinessName()))
              {
                currentScreen = screen;
                isVisible1 = false;
                isVisible2 = true; 
                controller.setVisible(isVisible1);
                controller1.setVisible(isVisible2);
                println("gone to screen 2");
                screen.setupBScreen();
                done = true;
              }
            }
          }
          finished = true;
        }
        if (!finished)
        {
          for (int k = 0; k<users.size(); k++)
          {
            String user = users.get(k);
            if (textbox.getValue().equalsIgnoreCase(user))
            {
              boolean done = false;
              while (!done)
              {
                for (int l = 0; l<uScreens.size(); l++)
                {
                  UserScreen screen = uScreens.get(l);
                  if (user.equalsIgnoreCase(screen.getUserName()))
                  {
                    currentScreen = screen;
                    isVisible1 = false;
                    isVisible2 = true; 
                    controller.setVisible(isVisible1);
                    controller1.setVisible(isVisible2);
                    println("gone to screen 2");
                    screen.setupUScreen();
                    done = true;
                  }
                }
              }
              finished = true;
            } else {
              println("name not found");
              finished = true;
            }
          }
        }
      }
    }
  }
}


void addReviewsToList() {


  for (TableRow row : dataTable.rows()) {

    String user_id = row.getString("user_id");
    String user_name = row.getString("user_name");
    String business_id = row.getString("business_id");
    String business_name = row.getString("business_name");
    int stars = row.getInt("stars");
    Date date = convertToDate(row.getString("date"));//row.getString("date");
    String text = row.getString("text");
    int useful = row.getInt("useful");
    int funny = row.getInt("funny");
    int cool = row.getInt("cool");
    reviews.add(new Review (user_id, user_name, business_id, business_name, stars, date, text, useful, funny, cool));
  }
}

void getBusinesses(ArrayList <Review> reviews) {

  for (int i = 0; i<reviews.size(); i++) {

    Review review = reviews.get(i);
    String name = review.getBusinessName();
    if (businesses.contains(name)||name.equals("#N/A")) {
    } else {
      businesses.add(name);
    }
  }
}

void getUsers(ArrayList <Review> reviews) {

  for (int i = 0; i<reviews.size(); i++) {    
    Review review = reviews.get(i);
    String name = review.getUserName();
    if (users.contains(name)||name.equals("#N/A")) {
    } else {
      users.add(name);
    }
  }
}

ArrayList<BusinessScreen> loadBusinessScreens(ArrayList<String> businesses, ArrayList<Review>reviews, GUIController controller, color backgroundColour) {

  ArrayList<BusinessScreen> businessScreenList = new ArrayList<BusinessScreen>();
  for (int i = 0; i < businesses.size(); i++) {    
    String businessID;
    boolean finished = false;
    String business = businesses.get(i);
    while (!finished) {
      for (int j = 0; j < reviews.size(); j++) {
        Review review = reviews.get(j);
        if (business.equalsIgnoreCase(review.getBusinessName())) {
          businessID = review.getBusinessId();
          BusinessScreen screen = new BusinessScreen(backgroundColour, controller, business, businessID);
          businessScreenList.add(screen);
          finished = true;
        }
      }
    }
  }
  return businessScreenList;
}

ArrayList<UserScreen> loadUserScreens(ArrayList<String>users, ArrayList<Review>reviews, GUIController controller, color backgroundColour) {

  ArrayList<UserScreen> userScreenList = new ArrayList<UserScreen>();
  for (int i = 0; i < users.size(); i++) {   
    String userID;
    boolean finished = false;
    String user = users.get(i);
    while (!finished) {      
      for (int j = 0; j < reviews.size(); j++) {        
        Review review = reviews.get(j);
        if (user.equalsIgnoreCase(review.getUserName())) {          
          userID = review.getUserId();
          UserScreen screen = new UserScreen(backgroundColour, controller, user, userID);
          userScreenList.add(screen);
          finished = true;
        }
      }
    }
  }
  return userScreenList;
}

void mouseWheel(MouseEvent event)
{
  float x = event.getCount();
  if (x < 0 && currentScreen == revSc)
  {
    revSc.scroll(30);
  } else if (x > 0 && currentScreen == revSc)
  {
    revSc.scroll(-30);
  }
}

Date convertToDate(String  sDate ) {

  Date date = null;
  try {
    SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");  
    date=formatter.parse(sDate);
  }
  catch(Exception e) {
  }
  return date;
}

void makeTWidgets() {
  int y = 30;
  Random rn = new Random();
  for (int i=0; i<=16; i++)
  {
    boolean nameFound = false;
    int random = rn.nextInt(users.size());
    for (int j = 0; j<screen3.tWidgets.size(); j++) {
      TextWidget tw =screen3.tWidgets.get(j);
      if (users.get(random).equalsIgnoreCase(tw.getLabel())) {
        nameFound = true;
      }
    }
    if (!nameFound) {
      String name = users.get(random);
      TextWidget tw = new TextWidget(50, y+20, 859, 50, name, EVENT_BUTTON5, white);
      screen3.addTextWidget(tw);
      y = y+30;
    }
  }

  y= 30;
  for (int i = 0; i < 50; i++)
  {
    boolean nameFound = false;
    int random = rn.nextInt(businesses.size());
    for (int j = 0; j<topBusinessScreen.tWidgets.size(); j++) {
      TextWidget tw =topBusinessScreen.tWidgets.get(j);
      if (businesses.get(random).equalsIgnoreCase(tw.getLabel())) {
        nameFound = true;
      }
    }
    if (!nameFound) {
      String name = businesses.get(random);
      TextWidget tw = new TextWidget(50, y+20, 859, 30, name, EVENT_BUTTON5, white);
      topBusinessScreen.addTextWidget(tw);
      y = y+30;
    }
  }
}
