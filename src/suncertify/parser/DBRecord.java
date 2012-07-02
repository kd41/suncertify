package suncertify.parser;

public class DBRecord {
  private long position;
  private boolean isValid;
  private String name;
  private String location;
  private String specialties;
  private long size;
  private String rate;
  private long owner;

  public long getPosition() {
    return position;
  }

  public void setPosition(long position) {
    this.position = position;
  }

  public void setValid(boolean isValid) {
    this.isValid = isValid;
  }

  public boolean isValid() {
    return isValid;
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

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public String getRate() {
    return rate;
  }

  public void setRate(String rate) {
    this.rate = rate;
  }

  public long getOwner() {
    return owner;
  }

  public void setOwner(long owner) {
    this.owner = owner;
  }

  @Override
  public String toString() {
    return "Data[position=" + position + ", isValid=" + isValid + ", name=" + name + ", location=" + location + ", specialties=" + specialties + ", size=" + size + ", rate="
           + rate + ", owner=" + owner + "]";
  }

}
