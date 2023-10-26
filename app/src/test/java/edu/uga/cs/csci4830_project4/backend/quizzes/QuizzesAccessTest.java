package edu.uga.cs.csci4830_project4.backend.quizzes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.database.Cursor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        QuizModel model = new QuizModel(2);
        model.setQuizType(QuizType.CAPITAL_QUIZ);
        model.setStateIds(Arrays.asList(1L, 2L));
        model.setResponses(Arrays.asList("Atlanta", null));
        model.setAnsweredCorrectly(Arrays.asList(true, false));
        model.setFinished(true);
        model.setScore(100);

        long expectedId = 1L;
        mockDb.setMockIdToReturnOnInsert(1L);

        QuizModel storedModel = quizzesAccess.store(model);

        assertNotNull(storedModel);
        assertEquals(expectedId, storedModel.getId());
        assertEquals(2, storedModel.getNumberOfQuestions());
        assertEquals(QuizType.CAPITAL_QUIZ, storedModel.getQuizType());
        assertEquals(storedModel.getStateIds(), Arrays.asList(1L, 2L));
        assertEquals(storedModel.getResponses(), Arrays.asList("Atlanta", null));
        assertEquals(storedModel.getAnsweredCorrectly(), Arrays.asList(true, false));
        assertTrue(storedModel.isFinished());
        assertEquals(100, storedModel.getScore());
    }

    @Test
    public void testGetById() {
        long quizId = 1L;
        List<Long> stateIds = new ArrayList<>() {{
            add(1L);
            add(2L);
        }};
        List<String> responses = new ArrayList<>() {{
            add("response1");
            add("response2");
        }};
        List<Boolean> answeredCorrectly = new ArrayList<>() {{
            add(true);
            add(false);
        }};
        QuizModel expectedModel = new QuizModel(2);
        expectedModel.setId(quizId);
        expectedModel.setQuizType(QuizType.CAPITAL_QUIZ);
        expectedModel.setStateIds(stateIds);
        expectedModel.setResponses(responses);
        expectedModel.setAnsweredCorrectly(answeredCorrectly);
        expectedModel.setFinished(true);
        expectedModel.setScore(100);

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
                    case QuizTableValues.COLUMN_QUIZ_TYPE -> 1;
                    case QuizTableValues.COLUMN_STATE_IDS -> 2;
                    case QuizTableValues.COLUMN_RESPONSES -> 3;
                    case QuizTableValues.COLUMN_ANSWERED_CORRECTLY -> 4;
                    case QuizTableValues.COLUMN_FINISHED -> 5;
                    case QuizTableValues.COLUMN_SCORE -> 6;
                    case QuizTableValues.COLUMN_NUMBER_QUESTIONS -> 7;
                    default -> throw new IllegalStateException("Unexpected value: " + column);
                };
            }

            @Override
            public String getString(int columnIndex) {
                return switch (columnIndex) {
                    case 1 -> QuizType.CAPITAL_QUIZ.name();
                    case 2 -> "[1,2]";
                    case 3 -> "[response1,response2]";
                    case 4 -> "[true,false]";
                    default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
                };
            }

            @Override
            public long getLong(int columnIndex) {
                if (columnIndex == 0) {
                    return quizId;
                }
                throw new IllegalStateException("Unexpected value: " + columnIndex);
            }

            @Override
            public int getInt(int columnIndex) {
                return switch (columnIndex) {
                    case 5 -> 1;
                    case 6 -> 100;
                    case 7 -> 2;
                    default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
                };
            }
        };
        mockDb.setMockCursor(mockCursor);

        QuizModel retrievedModel = quizzesAccess.getById(quizId);
        assertNotNull(retrievedModel);
        assertEquals(expectedModel, retrievedModel);
    }
}
