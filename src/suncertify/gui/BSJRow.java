package suncertify.gui;

public class BSJRow {
  public static final int RECORD_NUMBER = 1;
  public static final int NAME = 3;
  public static final int LOCATION = 4;
  public static final int SPECIALTIES = 5;
  public static final int WORKERS_NUMBER = 6;
  public static final int RATE = 7;
  public static final int OWNER = 8;
  public static final int COOKIE = 9;

  private long number;
  private String position;
  private String valid;
  private String name;
  private String location;
  private String specialties;
  private String numberOfWorkers;
  private String rate;
  private String owner;
  private String cookie;

  public long getNumber() {
    return number;
  }

  public void setNumber(long number) {
    this.number = number;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getValid() {
    return valid;
  }

  public void setValid(String valid) {
    this.valid = valid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getSpecialties() {
    return specialties;
  }

  public void setSpecialties(String specialties) {
    this.specialties = specialties;
  }

  public String getNumberOfWorkers() {
    return numberOfWorkers;
  }

  public void setNumberOfWorkers(String numberOfWorkers) {
    this.numberOfWorkers = numberOfWorkers;
  }

  public String getRate() {
    return rate;
  }

  public void setRate(String rate) {
    this.rate = rate;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public void setCookie(String cookie) {
    this.cookie = cookie;
  }

  public String getCookie() {
    return cookie;
  }

  public String[] toStringArray() {
    return new String[] { String.valueOf(number), position, valid, name, location, specialties, numberOfWorkers, rate,
                         owner, cookie };
  }

  public static String[] getHeaders() {
    return new String[] { "Nr", "Position", "Valid", "Name", "Location", "Specialties", "Nr workers", "Rate", "Owner",
                         "Cookie" };
  }
}
