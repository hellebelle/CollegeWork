class BusinessScreen extends Screen {
  String businessName;
  String businessID;
  ArrayList<Review>listOfReviews;
  ArrayList<Widget>widgets;
  int averageStars;
  PieChart pChart;
  LineGraph lineGraph;


  BusinessScreen(PImage img, GUIController controller, String businessName, String businessID ) {

    super(img, controller);
    this.businessName = businessName;
    this.businessID = businessID;
    listOfReviews = new ArrayList<Review>();    
    this.widgets = new ArrayList<Widget>();
  }

  BusinessScreen(color backgroundCol, GUIController controller, String businessName, String businessID) {

    super(backgroundCol, controller);
    this.businessName = businessName;
    this.businessID = businessID; 
    listOfReviews = new ArrayList<Review>();    
    this.widgets = new ArrayList<Widget>();
  }

  void setupBScreen() {
    averageStars = getAverageStars(reviews);
    listOfReviews = getReviews(reviews);
    int[]starsArray = addIntoArray(listOfReviews);
    pChart = new PieChart(200,starsArray,105, 430); 
    draw();
  }

  void draw() {
    background (255);
    textSize(15);
    text("REVIEWS", width/2-10, 30);
    fill(grey);
    rect(0, 30, 220, 80);
    fill(255, 0, 0);
    rect(0, 120, 35, 35); 
    text("Funny", 70, 150);
    fill(0, 255, 0);
    rect(0, 170, 35, 35); 
    text("Useful", 70, 200);
    fill(0, 0, 255);
    rect(0, 220, 35, 35);
    text("Cool", 70, 250); 
    fill(220,150,0);
    rect(0, 285, 20, 20);
    fill(66, 185, 244);
    rect(30, 285, 20, 20);
    fill(141, 20, 168);
    rect(60, 285, 20, 20); 
    fill(244, 65, 211);
    rect(90, 285, 20, 20);    
    fill(244, 241, 65);
    rect(120, 285, 20, 20);
    fill(0);
    text("Stars received", 50, 270);
    text("1",5, 300);
    text("2", 35, 300);
    text("3", 65, 300);
    text("4", 95, 300);
    text("5", 125, 300);
    pChart.draw();
    printReviews(listOfReviews, 230, 40);
  }
  
  int getAverageStars(ArrayList<Review>reviews) {
    int count = 0;    
    int sum = 0;
    for (int i = 0; i<reviews.size(); i++) {
      Review review = reviews.get(i);
      if (businessName.equalsIgnoreCase(review.getBusinessName())) {
        sum = sum + review.getStars();
        count++;
      }
    }
    int average = sum/count;
    return average;
  }

  ArrayList<Review> getReviews(ArrayList<Review>reviews) {
    for (int i = 0; i<reviews.size(); i++) {

      Review review = reviews.get(i);
      if (businessName.equalsIgnoreCase(review.getBusinessName())) {   
        listOfReviews.add(review);
      }
    }
    return listOfReviews;
  }

  void printReviews(ArrayList<Review> listOfReviews, int xPos, int yPos) {
    fill(0);
    textSize(14);
    text("Business Name:  "+businessName, 0, 50);
    text("Average Stars:"+averageStars, 0, 90);
    for (int i = 0; i <listOfReviews.size(); i++) {
      textSize(12.5);
      Review review = listOfReviews.get(i);
      text(toString(review), xPos, yPos, 700, 600);
      fill(255, 0, 0);
      rect(xPos+400, yPos, 35, 35);      
      fill(0, 255, 0);
      rect(xPos+500, yPos, 35, 35);     
      fill(0, 0, 255);
      rect(xPos+600, yPos, 35, 35);    
      fill(0);
      text(review.getFunny(), xPos+415, yPos+22);
      text(review.getUseful(), xPos+515, yPos+22);
      text(review.getCool(), xPos+615, yPos+22);
      yPos += 190;
    }
  }

  public String getBusinessId() {
    return businessID;
  }

  public String getBusinessName() {

    return businessName;
  }

  public int getAverageStars() {
    return averageStars;
  }

  public String toString(Review review) {

    String result = "User Name: " + review.getUserName()  + "\nDate of Posting: " + review.getDate() + "\nStars given: " + review.getStars() + "\nReview: " + review.getText();
    return result;
  }
  
  int[] addIntoArray(ArrayList<Review>reviews) {  

  int oneStar = 0;
  int twoStar = 0;
  int threeStar = 0;
  int fourStar = 0;
  int fiveStar = 0;
  for (int i =0; i < reviews.size(); i++) {

    int x = reviews.get(i).getStars();

    switch(x)
    {
    case 1:
      oneStar++;
      break;
    case 2:
      twoStar++;
      break;
    case 3:
      threeStar++;
      break;
    case 4:
      fourStar++;
      break;
    case 5:
      fiveStar++;
      break;
    }
  }

  int[] valueArray = { (oneStar*360)/reviews.size(), (twoStar*360)/reviews.size(), (threeStar*360)/reviews.size(), (fourStar*360)/reviews.size(), (fiveStar*360)/reviews.size() };
  return valueArray;
}

}
