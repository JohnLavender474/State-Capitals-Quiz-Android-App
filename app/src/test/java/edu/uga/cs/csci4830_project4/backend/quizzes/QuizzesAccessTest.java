package edu.uga.cs.csci4830_project4.backend.quizzes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static edu.uga.cs.csci4830_project4.utils.CommonUtilMethods.stringToList;

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
public class QuizzesAccessTest {

    private MockDatabase mockDb;
    private QuizzesAccess quizzesAccess;

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
        quizzesAccess = new QuizzesAccess(mockHelper);
        quizzesAccess.open();
    }

    @After
    public void tearDown() {
        quizzesAccess.close();
    }

    @Test
    public void testStore() {
        QuizModel model = new QuizModel();
        model.setQuestions(stringToList("Question 1;Question 2", s -> s));
        model.setResponses(stringToList("Response 1;Response 2", s -> s));
        model.setChoices(stringToList("Choice a1,Choice a2,Choice a3;Choice b1,Choice b2," +
                "Choice b3", s -> s));
        model.setAnswers(stringToList("Answer1,Answer2", s -> s));

        long expectedId = 1L;
        mockDb.setMockIdToReturnOnInsert(1L);

        QuizModel storedModel = quizzesAccess.store(model);

        assertNotNull(storedModel);
        assertEquals(expectedId, storedModel.getId());
        assertEquals(stringToList("Question 1;Question 2", s -> s), storedModel.getQuestions());
        assertEquals(stringToList("Response 1;Response 2", s -> s), storedModel.getResponses());
        assertEquals(stringToList("Choice a1,Choice a2,Choice a3;Choice b1,Choice b2,Choice b3",
                s -> s), storedModel.getChoices());
        assertEquals(stringToList("Answer1,Answer2", s -> s), storedModel.getAnswers());
    }

    @Test
    public void testGetById() {
        long quizId = 1L;
        QuizModel expectedModel = new QuizModel();
        expectedModel.setId(quizId);
        expectedModel.setQuestions(stringToList("Question 1;Question 2", s -> s));
        expectedModel.setResponses(stringToList("Response 1;Response 2", s -> s));
        expectedModel.setChoices(stringToList("Choice a1,Choice a2,Choice a3;Choice b1,Choice b2,"
                + "Choice b3", s -> s));
        expectedModel.setAnswers(stringToList("Answer1,Answer2", s -> s));

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
                    case QuizTableValues.COLUMN_ID -> 0;
                    case QuizTableValues.COLUMN_QUESTIONS -> 1;
                    case QuizTableValues.COLUMN_RESPONSES -> 2;
                    case QuizTableValues.COLUMN_CHOICES -> 3;
                    case QuizTableValues.COLUMN_ANSWERS -> 4;
                    default -> -1;
                };
            }

            @Override
            public String getString(int columnIndex) {
                return switch (columnIndex) {
                    case 1 -> "Question 1;Question 2";
                    case 2 -> "Response 1;Response 2";
                    case 3 -> "Choice a1,Choice a2,Choice a3;Choice b1,Choice b2,Choice b3";
                    case 4 -> "Answer1,Answer2";
                    default -> null;
                };
            }

            @Override
            public long getLong(int columnIndex) {
                if (columnIndex == 0) {
                    return quizId;
                }
                return -1;
            }
        };
        mockDb.setMockCursor(mockCursor);

        QuizModel retrievedModel = quizzesAccess.getById(quizId);
        assertNotNull(retrievedModel);
        assertEquals(expectedModel, retrievedModel);
    }
}

