package suncertify.db;

/**
 * The Interface DBAccess is the main interface of application.
 */
public interface DBAccess {

  /**
   * Reads a record from the file. Returns an array where each element is a record value.
   * 
   * @param recNo the number of record
   * @return the string[]
   * @throws RecordNotFoundException the record not found exception
   */
  public String[] readRecord(long recNo) throws RecordNotFoundException;

  /**
   * Modifies the fields of a record. The new value for field n appears in data[n]. Throws SecurityException if the
   * record is locked with a cookie other than lockCookie.
   * 
   * @param recNo the number of record
   * @param data the data
   * @param lockCookie the lock cookie
   * @throws RecordNotFoundException the record not found exception
   * @throws SecurityException the security exception
   */
  public void updateRecord(long recNo, String[] data, long lockCookie) throws RecordNotFoundException,
                                                                      SecurityException;

  /**
   * Deletes a record, making the record number and associated disk storage available for reuse. Throws
   * SecurityException if the record is locked with a cookie other than lockCookie.
   * 
   * @param recNo the number of record
   * @param lockCookie the lock cookie
   * @throws RecordNotFoundException the record not found exception
   * @throws SecurityException the security exception
   */
  public void deleteRecord(long recNo, long lockCookie) throws RecordNotFoundException, SecurityException;

  /**
   * Returns an array of record numbers that match the specifiedcriteria. Field n in the database file is described by
   * criteria[n]. A null value in criteria[n] matches any field value. A non-null value in criteria[n] matches any field
   * value that begins with criteria[n]. (For example, "Fred" matches "Fred" or "Freddy".)
   * 
   * @param criteria the criteria
   * @return the long[]
   */
  public long[] findByCriteria(String[] criteria);

  /**
   * Creates a new record in the database (possibly reusing a deleted entry). Inserts the given data, and returns the
   * record number of the new record.
   * 
   * @param data the data
   * @return the long
   * @throws DuplicateKeyException the duplicate key exception
   */
  public long createRecord(String[] data) throws DuplicateKeyException;

  /**
   * Locks a record so that it can only be updated or deleted by this client. Returned value is a cookie that must be
   * used when the record is unlocked, updated, or deleted. If the specified record is already locked by a different
   * client, the current thread gives up the CPU and consumes no CPU cycles until the record is unlocked.
   * 
   * @param recNo the number of record
   * @return the long
   * @throws RecordNotFoundException the record not found exception
   */
  public long lockRecord(long recNo) throws RecordNotFoundException;

  /**
   * Releases the lock on a record. Cookie must be the cookie returned when the record was locked; otherwise throws
   * SecurityException.
   * 
   * @param recNo the number of record
   * @param cookie the cookie
   * @throws SecurityException the security exception
   */
  public void unlock(long recNo, long cookie) throws SecurityException;

}
