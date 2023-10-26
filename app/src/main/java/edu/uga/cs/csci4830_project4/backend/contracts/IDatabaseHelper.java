package edu.uga.cs.csci4830_project4.backend.contracts;

/**
 * This interface provides a contract for database helper classes.
 */
public interface IDatabaseHelper {

    /**
     * Closes the database connection.
     */
    void close();

    /**
     * Returns a readable database.
     *
     * @return a readable database
     */
    IDatabase getViewOnlyDatabase();

    /**
     * Returns a writable database.
     *
     * @return a writable database
     */
    IDatabase getModifiableDatabase();
}
