package edu.uga.cs.csci4830_project4.backend.mock;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

public class MockCursor implements Cursor {

    private int position = -1; // Current cursor position
    private String[] columnNames; // Column names
    private Object[][] data; // Data stored as a 2D array

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public boolean move(int offset) {
        int newPosition = position + offset;
        if (newPosition < -1 || newPosition >= data.length) {
            return false;
        }
        position = newPosition;
        return true;
    }

    @Override
    public boolean moveToPosition(int position) {
        if (position < -1 || position >= data.length) {
            return false;
        }
        this.position = position;
        return true;
    }

    @Override
    public boolean moveToFirst() {
        if (data.length > 0) {
            position = 0;
            return true;
        }
        return false;
    }

    @Override
    public boolean moveToLast() {
        if (data.length > 0) {
            position = data.length - 1;
            return true;
        }
        return false;
    }

    @Override
    public boolean moveToNext() {
        if (position < data.length - 1) {
            position++;
            return true;
        }
        return false;
    }

    @Override
    public boolean moveToPrevious() {
        if (position > 0) {
            position--;
            return true;
        }
        return false;
    }

    @Override
    public boolean isFirst() {
        return position == 0;
    }

    @Override
    public boolean isLast() {
        return position == data.length - 1;
    }

    @Override
    public boolean isBeforeFirst() {
        return position < 0;
    }

    @Override
    public boolean isAfterLast() {
        return position >= data.length;
    }

    // Implement other methods as needed

    @Override
    public int getColumnIndex(String columnName) {
        for (int i = 0; i < columnNames.length; i++) {
            if (columnNames[i].equals(columnName)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
        for (int i = 0; i < columnNames.length; i++) {
            if (columnNames[i].equals(columnName)) {
                return i;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex >= 0 && columnIndex < columnNames.length) {
            return columnNames[columnIndex];
        }
        return null;
    }

    @Override
    public String[] getColumnNames() {
        return columnNames;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public byte[] getBlob(int columnIndex) {
        if (isValidCursorPosition() && columnIndex >= 0 && columnIndex < columnNames.length) {
            Object value = data[position][columnIndex];
            if (value instanceof byte[]) {
                return (byte[]) value;
            }
        }
        return null;
    }

    @Override
    public String getString(int columnIndex) {
        if (isValidCursorPosition() && columnIndex >= 0 && columnIndex < columnNames.length) {
            Object value = data[position][columnIndex];
            if (value instanceof String) {
                return (String) value;
            }
        }
        return null;
    }

    @Override
    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
        // Implement if needed
    }

    @Override
    public short getShort(int columnIndex) {
        return 0;
    }

    @Override
    public int getInt(int columnIndex) {
        return 0;
    }

    @Override
    public long getLong(int columnIndex) {
        return 0;
    }

    @Override
    public float getFloat(int columnIndex) {
        return 0;
    }

    @Override
    public double getDouble(int columnIndex) {
        return 0;
    }

    // Implement other data retrieval methods

    @Override
    public int getType(int columnIndex) {
        // You can implement this based on the actual type of the data
        return FIELD_TYPE_STRING; // Default to string
    }

    @Override
    public boolean isNull(int columnIndex) {
        if (isValidCursorPosition() && columnIndex >= 0 && columnIndex < columnNames.length) {
            Object value = data[position][columnIndex];
            return (value == null);
        }
        return true;
    }

    @Override
    public void deactivate() {
        // Implement if needed
    }

    @Override
    public boolean requery() {
        // Implement if needed
        return false;
    }

    @Override
    public void close() {
        // Implement if needed
    }

    @Override
    public boolean isClosed() {
        // Implement if needed
        return false;
    }

    @Override
    public void registerContentObserver(ContentObserver observer) {
        // Implement if needed
    }

    @Override
    public void unregisterContentObserver(ContentObserver observer) {
        // Implement if needed
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        // Implement if needed
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        // Implement if needed
    }

    @Override
    public void setNotificationUri(android.content.ContentResolver cr, Uri uri) {
        // Implement if needed
    }

    @Override
    public Uri getNotificationUri() {
        // Implement if needed
        return null;
    }

    @Override
    public boolean getWantsAllOnMoveCalls() {
        // Implement if needed
        return false;
    }

    @Override
    public void setExtras(Bundle extras) {
        // Implement if needed
    }

    @Override
    public Bundle getExtras() {
        // Implement if needed
        return Bundle.EMPTY;
    }

    @Override
    public Bundle respond(Bundle extras) {
        // Implement if needed
        return Bundle.EMPTY;
    }

    // Helper method to check if the cursor is at a valid position
    private boolean isValidCursorPosition() {
        return position >= 0 && position < data.length;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }
}
