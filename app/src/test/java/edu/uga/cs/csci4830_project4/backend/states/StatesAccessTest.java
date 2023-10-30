package edu.uga.cs.csci4830_project4.backend.states;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.database.Cursor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import edu.uga.cs.csci4830_project4.backend.contracts.IDatabase;
import edu.uga.cs.csci4830_project4.backend.mock.MockCursor;
import edu.uga.cs.csci4830_project4.backend.mock.MockDatabase;
import edu.uga.cs.csci4830_project4.backend.mock.MockDatabaseHelper;

@RunWith(MockitoJUnitRunner.class)
public class StatesAccessTest {

    private MockDatabase mockDb;
    private StatesAccess statesAccess;

    @Before
    public void setUp() {
        mockDb = new MockDatabase();
        MockDatabaseHelper mockHelper = new MockDatabaseHelper() {
            @Override
            public IDatabase getViewOnlyDatabase() {
                return mockDb;
            }

            @Override
            public IDatabase getModifiableDatabase() {
                return mockDb;
            }
        };
        statesAccess = new StatesAccess(mockHelper);
        statesAccess.open();
    }

    @After
    public void tearDown() {
        statesAccess.close();
    }

    @Test
    public void testStore() {
        StateModel model = new StateModel();
        model.setStateName("Georgia");
        model.setCapitalCity("Atlanta");
        model.setSecondCity("Savannah");
        model.setThirdCity("Macon");
        model.setStatehood("January 2, 1788");
        model.setCapitalSince("1868");
        model.setSizeRank("24");

        long expectedId = 1L;
        mockDb.setMockIdToReturnOnInsert(1L);

        StateModel storedModel = statesAccess.store(model);

        assertNotNull(storedModel);
        assertEquals(expectedId, storedModel.getId());
        assertEquals("Georgia", storedModel.getStateName());
        assertEquals("Atlanta", storedModel.getCapitalCity());
        assertEquals("Savannah", storedModel.getSecondCity());
        assertEquals("Macon", storedModel.getThirdCity());
        assertEquals("January 2, 1788", storedModel.getStatehood());
        assertEquals("1868", storedModel.getCapitalSince());
        assertEquals("24", storedModel.getSizeRank());
    }

    @Test
    public void testGetByStateName() {
        long stateId = 1L;
        StateModel expectedModel = new StateModel();
        expectedModel.setId(stateId);
        expectedModel.setStateName("Georgia");
        expectedModel.setCapitalCity("Atlanta");
        expectedModel.setSecondCity("Savannah");
        expectedModel.setThirdCity("Macon");
        expectedModel.setStatehood("January 2, 1788");
        expectedModel.setCapitalSince("1868");
        expectedModel.setSizeRank("24");

        Cursor mockCursor = new MockCursor() {

            int position = 0;

            @Override
            public boolean moveToFirst() {
                position = 0;
                return true;
            }

            @Override
            public boolean moveToNext() {
                position++;
                return position < 2;
            }

            @Override
            public int getColumnIndex(String column) {
                return switch (column) {
                    case StateTableValues.COLUMN_ID -> 0;
                    case StateTableValues.COLUMN_STATE_NAME -> 1;
                    case StateTableValues.COLUMN_CAPITAL_CITY -> 2;
                    case StateTableValues.COLUMN_SECOND_CITY -> 3;
                    case StateTableValues.COLUMN_THIRD_CITY -> 4;
                    case StateTableValues.COLUMN_STATEHOOD -> 5;
                    case StateTableValues.COLUMN_CAPITAL_SINCE -> 6;
                    case StateTableValues.COLUMN_SIZE_RANK -> 7;
                    default -> -1;
                };
            }

            @Override
            public String getString(int columnIndex) {
                return switch (columnIndex) {
                    case 1 -> "Georgia";
                    case 2 -> "Atlanta";
                    case 3 -> "Savannah";
                    case 4 -> "Macon";
                    case 5 -> "January 2, 1788";
                    case 6 -> "1868";
                    case 7 -> "24";
                    default -> null;
                };
            }

            @Override
            public long getLong(int columnIndex) {
                if (columnIndex == 0) {
                    return stateId;
                }
                return -1;
            }
        };
        mockDb.setMockCursor(mockCursor);

        StateModel retrievedModel = statesAccess.getByStateName("Georgia");
        assertNotNull(retrievedModel);
        assertEquals(expectedModel, retrievedModel);
    }

    @Test
    public void testGetById() {
        long stateId = 1L;
        StateModel expectedModel = new StateModel();
        expectedModel.setId(stateId);
        expectedModel.setStateName("Georgia");
        expectedModel.setCapitalCity("Atlanta");
        expectedModel.setSecondCity("Savannah");
        expectedModel.setThirdCity("Macon");
        expectedModel.setStatehood("January 2, 1788");
        expectedModel.setCapitalSince("1868");
        expectedModel.setSizeRank("24");

        Cursor mockCursor = new MockCursor() {

            int position = 0;

            @Override
            public boolean moveToFirst() {
                position = 0;
                return true;
            }

            @Override
            public boolean moveToNext() {
                position++;
                return position < 2;
            }

            @Override
            public int getColumnIndex(String column) {
                return switch (column) {
                    case StateTableValues.COLUMN_ID -> 0;
                    case StateTableValues.COLUMN_STATE_NAME -> 1;
                    case StateTableValues.COLUMN_CAPITAL_CITY -> 2;
                    case StateTableValues.COLUMN_SECOND_CITY -> 3;
                    case StateTableValues.COLUMN_THIRD_CITY -> 4;
                    case StateTableValues.COLUMN_STATEHOOD -> 5;
                    case StateTableValues.COLUMN_CAPITAL_SINCE -> 6;
                    case StateTableValues.COLUMN_SIZE_RANK -> 7;
                    default -> -1;
                };
            }

            @Override
            public String getString(int columnIndex) {
                return switch (columnIndex) {
                    case 1 -> "Georgia";
                    case 2 -> "Atlanta";
                    case 3 -> "Savannah";
                    case 4 -> "Macon";
                    case 5 -> "January 2, 1788";
                    case 6 -> "1868";
                    case 7 -> "24";
                    default -> null;
                };
            }

            @Override
            public long getLong(int columnIndex) {
                if (columnIndex == 0) {
                    return stateId;
                }
                return -1;
            }
        };
        mockDb.setMockCursor(mockCursor);

        StateModel retrievedModel = statesAccess.getById(stateId);
        assertNotNull(retrievedModel);
        assertEquals(expectedModel, retrievedModel);
    }
}
