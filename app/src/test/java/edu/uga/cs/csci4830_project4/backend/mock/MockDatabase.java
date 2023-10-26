package edu.uga.cs.csci4830_project4.backend.mock;

import android.database.Cursor;

import org.mockito.Mockito;

import java.util.Map;

import edu.uga.cs.csci4830_project4.backend.contracts.IDatabase;

/**
 * Simple mock database class for testing. Methods are stubbed to return default values.
 * This class is not used in the application. It is only used for testing. Override the
 * methods as needed to return the desired values for testing.
 */
public class MockDatabase implements IDatabase {

    private long mockIdToReturnOnInsert = 1L;
    private int mockReturnValueOnUpdate = 1;
    private int mockReturnValueOnDelete = 1;
    private Cursor mockCursor = Mockito.mock(Cursor.class);

    public long getMockIdToReturnOnInsert() {
        return mockIdToReturnOnInsert;
    }

    public void setMockIdToReturnOnInsert(long mockIdToReturnOnInsert) {
        this.mockIdToReturnOnInsert = mockIdToReturnOnInsert;
    }

    public int getMockReturnValueOnUpdate() {
        return mockReturnValueOnUpdate;
    }

    public void setMockReturnValueOnUpdate(int mockReturnValueOnUpdate) {
        this.mockReturnValueOnUpdate = mockReturnValueOnUpdate;
    }

    public int getMockReturnValueOnDelete() {
        return mockReturnValueOnDelete;
    }

    public void setMockReturnValueOnDelete(int mockReturnValueOnDelete) {
        this.mockReturnValueOnDelete = mockReturnValueOnDelete;
    }

    public Cursor getMockCursor() {
        return mockCursor;
    }

    public void setMockCursor(Cursor mockCursor) {
        this.mockCursor = mockCursor;
    }

    @Override
    public long insert(String table, String nullColumnHack, Map<String, Object> values) {
        return mockIdToReturnOnInsert;
    }

    @Override
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs,
                        String groupBy, String having, String orderBy, String limit) {
        return mockCursor;
    }

    @Override
    public int update(String table, Map<String, Object> values, String whereClause,
                      String[] whereArgs) {
        return mockReturnValueOnUpdate;
    }

    @Override
    public int delete(String table, String whereClause, String[] whereArgs) {
        return mockReturnValueOnDelete;
    }
}
