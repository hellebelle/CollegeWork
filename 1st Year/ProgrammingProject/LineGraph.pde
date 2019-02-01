class LineGraph {
  
  int xPos;
  int yPos;
  float height;
  float width;
  float maxValue;
  float minValue;
  String xLabel;
  String yLabel;
  int[] xValues;
  int[] yValues;
  float buffer;
  float scale;
  int bufferCount;
  PVector[] points;

  LineGraph(int xPos, int yPos, float height, float width, String xLabel, String yLabel, int[] xValues, int[] yValues) {
    
    this.xPos = xPos;
    this.yPos = yPos;
    this.height = height;
    this.width = width;
    this.maxValue = max(yValues);
    this.minValue = min(yValues);
    this.xLabel = xLabel;
    this.yLabel = yLabel;
    this.xValues = xValues;
    this.yValues = yValues;
    buffer = width/ xValues.length;
    bufferCount = 0;
    points = new PVector[yValues.length];
    for(int i = 0; i < yValues.length; i++) {
      
      float mappedHeight = map(yValues[i], minValue, maxValue, 0, height);
      float vectorY = yPos + (height - mappedHeight);
      float vectorX =  xPos + (buffer * bufferCount);
      points[i] = new PVector(vectorX, vectorY);
      bufferCount++;
      
    }
    
  }
  
  void draw() {
    
    line(xPos, yPos, xPos, yPos + height);
    line(xPos, yPos + height, xPos + width, yPos + height);
    text(yLabel, xPos - 50, yPos + height/2);
    text(xLabel, xPos + width/2, yPos + height + 30);
    text(maxValue, yPos, xPos - 50);
    text(minValue, yPos+height, xPos - 50);
    for (int i = 0; i < xValues.length -1; i++) {
      
      line(points[i].x, points[i].y, points[i+1].x, points[i+1].y);
      text(xValues[i], points[i].x, yPos + height);
      
    }
    
  }
  
}