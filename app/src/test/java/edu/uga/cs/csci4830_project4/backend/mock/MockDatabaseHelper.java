package edu.uga.cs.csci4830_project4.backend.mock;

import edu.uga.cs.csci4830_project4.backend.contracts.IDatabase;
import edu.uga.cs.csci4830_project4.backend.contracts.IDatabaseHelper;

/**
 * Simple mock database helper class for testing. Methods are stubbed to return default values.
 * This class is not used in the application. It is only used for testing. Override the
 * methods as needed to return the desired values for testing.
 */
public class MockDatabaseHelper implements IDatabaseHelper {

    @Override
    public void close() {
    }

    @Override
    public IDatabase getViewOnlyDatabase() {
        return new MockDatabase();
    }

    @Override
    public IDatabase getModifiableDatabase() {
        return new MockDatabase();
    }
}
