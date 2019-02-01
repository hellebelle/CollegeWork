class Review {

  private String userId;
  private String userName;
  private String businessId;
  private String businessName;
  private int stars;
  private Date date;
  private String text;
  private int useful;
  private int funny;
  private int cool;


  Review(String userId, String userName, String businessId, String businessName, int stars, Date date, String text, int useful, int funny, int cool) {
    
    this.userId = userId;
    this.userName = userName;
    this.businessId = businessId;
    this.businessName = businessName;
    this.stars = stars;
    this.date = date;
    this.text = text;
    this.useful = useful;
    this.funny = funny;
    this.cool = cool;
    
  }

  public String getUserId() {
    
    return userId;
    
  }
  
  public String getUserName() {
    
    return userName;
    
  }

  public String getBusinessId() {
    
    return businessId;
    
  }

  public String getBusinessName() {
    
    return businessName;
    
  }

  public int getStars() {
    
    return stars;
    
  }

  public Date getDate() {
    
    return date;
    
  }

  public String getText() {
    
    return text;
   
  }

  public int getUseful() {
    
    return useful;
    
  }

  public int getFunny() {
    
    return funny;
    
  }

  public int getCool() {
    
    return cool;
    
  }

  public String toString() {
    
    String result = "Business Name: " + businessName +"\nUser Name: " + userName + "\nStars given: " + stars + "\nReview: " + text;
    return result;
    
  }
  
}
