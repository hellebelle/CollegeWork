class ReviewScreen extends Screen {

  ArrayList<Review> reviews;
  String userName;
  String businessName;
  int stars;
  String date;
  String text;
  int startPos = 50;

  ReviewScreen(PImage img, GUIController controller, ArrayList<Review> reviews) {

    super(img, controller);
    this.reviews = reviews;
  }

  ReviewScreen(color backgroundCol, GUIController controller, ArrayList<Review> reviews) {

    super(backgroundCol, controller);
    this.reviews = reviews;
  }

  void draw() {

    if (backgroundCol!=0) {

      background(backgroundCol);
    } else {

      background(img);
    }

    drawText(startPos);
  }
  void scroll(int change)
  {
    startPos += change;
  }

  void drawText(int startPos) { 
    int ypos = 40;
    for (int i = 0; i < 10; i++) {
      Review review = reviews.get(i);
      String text = review.toString();
      textFont(myFont);
      fill(0);
      textSize(12.5);
      text(text, 100, ypos + startPos, 700, 600);
      ypos += 200;
    }
  }
}
