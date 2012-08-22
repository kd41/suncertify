package suncertify.parser;

public class DBRecord {
  private long position;
  private String valid;
  private String name;
  private String location;
  private String specialties;
  private String numberOfWorkers;
  private String rate;
  private String owner;
  private long cookie;

  public long getPosition() {
    return position;
  }

  public void setPosition(long position) {
    this.position = position;
  }

  public void setValid(String valid) {
    this.valid = valid;
  }

  public String getValid() {
    return valid;
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

  public void setCookie(long cookie) {
    this.cookie = cookie;
  }

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
    if ((o instanceof DBRecord) && (((DBRecord) o).valid.equals(valid)) && (((DBRecord) o).name.equals(name))
        && (((DBRecord) o).location.equals(location)) && (((DBRecord) o).specialties.equals(specialties))
        && (((DBRecord) o).rate.equals(rate))) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 31 * hash + (valid == null ? 0 : valid.hashCode());
    hash = 31 * hash + (name == null ? 0 : name.hashCode());
    hash = 31 * hash + (location == null ? 0 : location.hashCode());
    hash = 31 * hash + (specialties == null ? 0 : specialties.hashCode());
    hash = 31 * hash + (rate == null ? 0 : rate.hashCode());
    return hash;
  }

}
