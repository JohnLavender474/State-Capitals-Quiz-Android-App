package edu.uga.cs.csci4830_project4.backend.scores;

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
public class ScoresAccessTest {

    private MockDatabase mockDb;
    private ScoresAccess scoresAccess;

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
        scoresAccess = new ScoresAccess(mockHelper);
        scoresAccess.open();
    }

    @After
    public void tearDown() {
        scoresAccess.close();
    }

    @Test
    public void testStore() {
        ScoreModel model = new ScoreModel();
        model.setScore("80");

        long expectedId = 1L;
        mockDb.setMockIdToReturnOnInsert(1L);

        ScoreModel storedModel = scoresAccess.store(model);

        assertNotNull(storedModel);
        assertEquals("80", storedModel.getScore());
        assertEquals(expectedId, storedModel.getId());
    }

    @Test
    public void testGetById() {
        long scoreId = 1L;
        ScoreModel expectedModel = new ScoreModel();
        expectedModel.setId(scoreId);
        expectedModel.setScore("90");

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
                    case ScoreTableValues.COLUMN_ID -> 0;
                    case ScoreTableValues.COLUMN_SCORE -> 1;
                    default -> -1;
                };
            }

            @Override
            public String getString(int columnIndex) {
                if (columnIndex == 1) {
                    return "90";
                }
                return null;
            }

            @Override
            public long getLong(int columnIndex) {
                if (columnIndex == 0) {
                    return scoreId;
                }
                return -1;
            }
        };
        mockDb.setMockCursor(mockCursor);

        ScoreModel retrievedModel = scoresAccess.getById(scoreId);
        assertNotNull(retrievedModel);
        assertEquals(expectedModel, retrievedModel);
    }
}

