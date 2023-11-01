package edu.uga.cs.csci4830_project4.backend.scores;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static edu.uga.cs.csci4830_project4.common.CommonUtilMethods.stringToDate;

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
import edu.uga.cs.csci4830_project4.common.QuizType;

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
        model.setQuizType(QuizType.CAPITALS_QUIZ);
        model.setTimeCompleted(stringToDate("2023-11-01 12:00"));

        long expectedId = 1L;
        mockDb.setMockIdToReturnOnInsert(1L);

        ScoreModel storedModel = scoresAccess.store(model);

        assertNotNull(storedModel);
        assertEquals("80", storedModel.getScore());
        assertEquals(expectedId, storedModel.getId());
        assertEquals(QuizType.CAPITALS_QUIZ, storedModel.getQuizType());
        assertEquals(stringToDate("2023-11-01 12:00"), storedModel.getTimeCompleted());
    }

    @Test
    public void testGetById() {
        long scoreId = 1L;
        ScoreModel expectedModel = new ScoreModel();
        expectedModel.setId(scoreId);
        expectedModel.setQuizType(QuizType.CAPITALS_QUIZ);
        expectedModel.setScore("90");
        expectedModel.setTimeCompleted(stringToDate("2023-11-01 12:00"));

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
                    case ScoreTableValues.COLUMN_QUIZ_TYPE -> 2;
                    case ScoreTableValues.COLUMN_TIME_COMPLETED -> 3;
                    default -> -1;
                };
            }

            @Override
            public String getString(int columnIndex) {
                return switch (columnIndex) {
                    case 1 -> "90";
                    case 2 -> "CAPITALS_QUIZ";
                    case 3 -> "2023-11-01 12:00";
                    default -> null;
                };
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

