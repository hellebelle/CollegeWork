class PieChart {

  int[] valueArray;
  float diameter;
  int xPos;
  int yPos;
  color colour;

  PieChart(float diameter, int[]array, int xPos, int yPos) {

    this.diameter = diameter;
    valueArray = array;  
    this.xPos = xPos;
    this.yPos = yPos;
  }

  void draw() {

    float previousAngle = 0;
     
      for (int i = 0; i < valueArray.length; i++) {
      switch(i)
      {
        case 0:
          colour = color(220,150,0);
          break;
        case 1:
          colour = color(66, 185, 244);
          break;
        case 2:
          colour = color(141, 20, 168);
          break;
        case 3:
          colour = color(244, 65, 211);
          break;
        case 4:
          colour = color(244, 241, 65);
          break;
      }
      fill(colour);
      arc(xPos, yPos, diameter, diameter, previousAngle, previousAngle + radians(valueArray[i]));
      previousAngle += radians(valueArray[i]);
    }
  }
}
