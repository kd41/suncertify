package suncertify.parser;

import suncertify.db.RecordNotFoundException;

/**
 * The Class DBRecord.
 */
public class DBRecord {
  private long position;
  private byte valid;
  private String name;
  private String location;
  private String specialties;
  private String numberOfWorkers;
  private String rate;
  private String owner;
  private long cookie;

  private boolean isLocked = false;

  /**
   * Gets the position.
   * 
   * @return the position
   */
  public long getPosition() {
    return position;
  }

  /**
   * Sets the position.
   * 
   * @param position the new position
   */
  public void setPosition(long position) {
    this.position = position;
  }

  /**
   * Sets the valid.
   * 
   * @param valid the new valid
   */
  public void setValid(byte valid) {
    this.valid = valid;
  }

  /**
   * Checks if is valid.
   * 
   * @return true, if is valid
   */
  public boolean isValid() {
    return valid == 0;
  }

  /**
   * Gets the valid.
   * 
   * @return the valid
   */
  public byte getValid() {
    return valid;
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
   * Sets the cookie.
   * 
   * @param cookie the new cookie
   */
  public void setCookie(long cookie) {
    this.cookie = cookie;
  }

  /**
   * Gets the cookie.
   * 
   * @return the cookie
   */
  public long getCookie() {
    return cookie;
  }

  @Override
  public String toString() {
    return "Data[position=" + position + ", valid=" + valid + ", name=" + name + ", location=" + location
           + ", specialties=" + specialties + ", numberOfWorkers=" + numberOfWorkers + ", rate=" + rate + ", owner="
           + owner + ", cookie=" + cookie + "]";
  }

  @Override
  public boolean equals(Object o) {
    if ((o instanceof DBRecord) && (((DBRecord) o).valid == valid) && (((DBRecord) o).name.equals(name))
        && (((DBRecord) o).location.equals(location)) && (((DBRecord) o).specialties.equals(specialties))
        && (((DBRecord) o).rate.equals(rate))) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 31 * hash + valid;
    hash = 31 * hash + (name == null ? 0 : name.hashCode());
    hash = 31 * hash + (location == null ? 0 : location.hashCode());
    hash = 31 * hash + (specialties == null ? 0 : specialties.hashCode());
    hash = 31 * hash + (rate == null ? 0 : rate.hashCode());
    return hash;
  }

  /**
   * Lock record.
   * 
   * @throws RecordNotFoundException the record not found exception
   */
  public synchronized void lockRecord() throws RecordNotFoundException {
    System.out.println("isValid: " + valid);
    while (isLocked) {
      try {
        wait();
      } catch (InterruptedException e) {
      }
    }
    if (!isValid()) {
      notify();
      throw new RecordNotFoundException();
    }
    isLocked = true;
    System.out.println("isLocked: " + position + " isValid: " + valid);
  }

  /**
   * Unlock record.
   */
  public synchronized void unlockRecord() {
    isLocked = false;
    notify();
  }

}
