package suncertify.gui;

/**
 * The Class BSJRow provides the row of record in table.
 */
public class BSJRow implements Comparable<BSJRow> {

  /** The Constant RECORD_POSITION. */
  public static final int RECORD_POSITION = 1;

  /** The Constant VALID. */
  public static final int VALID = 2;

  /** The Constant NAME. */
  public static final int NAME = 3;

  /** The Constant LOCATION. */
  public static final int LOCATION = 4;

  /** The Constant SPECIALTIES. */
  public static final int SPECIALTIES = 5;

  /** The Constant WORKERS_NUMBER. */
  public static final int WORKERS_NUMBER = 6;

  /** The Constant RATE. */
  public static final int RATE = 7;

  /** The Constant OWNER. */
  public static final int OWNER = 8;

  private long number;
  private String position;
  private String valid;
  private String name;
  private String location;
  private String specialties;
  private String numberOfWorkers;
  private String rate;
  private String owner;

  /**
   * Gets the number.
   * 
   * @return the number
   */
  public long getNumber() {
    return number;
  }

  /**
   * Sets the number.
   * 
   * @param number the new number
   */
  public void setNumber(long number) {
    this.number = number;
  }

  /**
   * Gets the position.
   * 
   * @return the position
   */
  public String getPosition() {
    return position;
  }

  /**
   * Sets the position.
   * 
   * @param position the new position
   */
  public void setPosition(String position) {
    this.position = position;
  }

  /**
   * Gets the valid.
   * 
   * @return the valid
   */
  public String getValid() {
    return valid;
  }

  /**
   * Sets the valid.
   * 
   * @param valid the new valid
   */
  public void setValid(String valid) {
    this.valid = valid;
  }

  /**
   * Gets the name.
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   * 
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the location.
   * 
   * @return the location
   */
  public String getLocation() {
    return location;
  }

  /**
   * Sets the location.
   * 
   * @param location the new location
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * Gets the specialties.
   * 
   * @return the specialties
   */
  public String getSpecialties() {
    return specialties;
  }

  /**
   * Sets the specialties.
   * 
   * @param specialties the new specialties
   */
  public void setSpecialties(String specialties) {
    this.specialties = specialties;
  }

  /**
   * Gets the number of workers.
   * 
   * @return the number of workers
   */
  public String getNumberOfWorkers() {
    return numberOfWorkers;
  }

  /**
   * Sets the number of workers.
   * 
   * @param numberOfWorkers the new number of workers
   */
  public void setNumberOfWorkers(String numberOfWorkers) {
    this.numberOfWorkers = numberOfWorkers;
  }

  /**
   * Gets the rate.
   * 
   * @return the rate
   */
  public String getRate() {
    return rate;
  }

  /**
   * Sets the rate.
   * 
   * @param rate the new rate
   */
  public void setRate(String rate) {
    this.rate = rate;
  }

  /**
   * Gets the owner.
   * 
   * @return the owner
   */
  public String getOwner() {
    return owner;
  }

  /**
   * Sets the owner.
   * 
   * @param owner the new owner
   */
  public void setOwner(String owner) {
    this.owner = owner;
  }

  /**
   * To string array.
   * 
   * @return the string[]
   */
  public String[] toStringArray() {
    return new String[] { String.valueOf(number), position, valid, name, location, specialties, numberOfWorkers, rate,
                         owner };
  }

  /**
   * Gets the headers.
   * 
   * @return the headers
   */
  public static String[] getHeaders() {
    return new String[] { "Nr", "Position", "Valid", "Name", "Location", "Specialties", "Nr workers", "Rate", "Owner" };
  }

  @Override
  public int compareTo(BSJRow o) {
    return Integer.parseInt(this.position) - Integer.parseInt(o.position);
  }
}
