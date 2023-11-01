package edu.uga.cs.csci4830_project4.backend.quizzes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static edu.uga.cs.csci4830_project4.common.CommonUtilMethods.listToString;
import static edu.uga.cs.csci4830_project4.common.CommonUtilMethods.stringToDate;
import static edu.uga.cs.csci4830_project4.common.CommonUtilMethods.stringToList;

import android.database.Cursor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import edu.uga.cs.csci4830_project4.backend.contracts.IDatabase;
import edu.uga.cs.csci4830_project4.backend.mock.MockCursor;
import edu.uga.cs.csci4830_project4.backend.mock.MockDatabase;
import edu.uga.cs.csci4830_project4.backend.mock.MockDatabaseHelper;
import edu.uga.cs.csci4830_project4.common.QuizType;

@RunWith(MockitoJUnitRunner.class)
public class QuizzesAccessTest {

    private static final String TIME_CREATED = "2023-10-30 12:00";
    private static final String TIME_UPDATED = "2023-10-31 05:00";

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
        model.setQuizType(QuizType.CAPITALS_QUIZ);
        model.setQuestions(stringToList("Question 1;Question 2", s -> s));
        model.setResponses(stringToList("Response 1;Response 2", s -> s));
        model.setChoices(stringToList("Choice a1,Choice a2,Choice a3;Choice b1,Choice b2," +
                "Choice b3", s -> {
            String[] split = s.split(",");
            return List.of(split);
        }));
        model.setAnswers(stringToList("Answer1;Answer2", s -> s));
        model.setStateNames(stringToList("Georgia;New York", s -> s));
        model.setTimeCreated(stringToDate(TIME_CREATED));
        model.setTimeUpdated(stringToDate(TIME_UPDATED));

        long expectedId = 1L;
        mockDb.setMockIdToReturnOnInsert(1L);

        QuizModel storedModel = quizzesAccess.store(model);

        assertNotNull(storedModel);
        assertEquals(QuizType.CAPITALS_QUIZ, storedModel.getQuizType());
        assertEquals(expectedId, storedModel.getId());
        assertEquals(stringToList("Question 1;Question 2", s -> s), storedModel.getQuestions());
        assertEquals(stringToList("Response 1;Response 2", s -> s), storedModel.getResponses());
        assertEquals(stringToList("Choice a1,Choice a2,Choice a3;Choice b1,Choice b2,Choice b3",
                s -> {
            String[] split = s.split(",");
            return List.of(split);
        }), storedModel.getChoices());
        assertEquals(stringToList("Answer1;Answer2", s -> s), storedModel.getAnswers());
        assertEquals(stringToList("Georgia;New York", s -> s), storedModel.getStateNames());
        assertEquals(stringToDate(TIME_CREATED), storedModel.getTimeCreated());
        assertEquals(stringToDate(TIME_UPDATED), storedModel.getTimeUpdated());

        System.out.println("Test store: model = " + model);
    }

    @Test
    public void testGetById() {
        long quizId = 1L;
        QuizModel expectedModel = new QuizModel();
        expectedModel.setId(quizId);
        expectedModel.setQuizType(QuizType.CAPITALS_QUIZ);
        expectedModel.setQuestions(stringToList("Question 1;Question 2", s -> s));
        expectedModel.setResponses(stringToList("Response 1;Response 2", s -> s));
        expectedModel.setChoices(stringToList("Choice a1,Choice a2,Choice a3;Choice b1,Choice b2,"
                + "Choice b3", s -> {
            String[] split = s.split(",");
            return List.of(split);
        }));
        expectedModel.setAnswers(stringToList("Answer1;Answer2", s -> s));
        expectedModel.setStateNames(stringToList("1;2", s -> s));
        expectedModel.setTimeCreated(stringToDate(TIME_CREATED));
        expectedModel.setTimeUpdated(stringToDate(TIME_UPDATED));

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
                    case QuizTableValues.COLUMN_STATE_NAMES -> 5;
                    case QuizTableValues.COLUMN_QUIZ_TYPE -> 6;
                    case QuizTableValues.COLUMN_TIME_CREATED -> 7;
                    case QuizTableValues.COLUMN_TIME_UPDATED -> 8;
                    default -> -1;
                };
            }

            @Override
            public String getString(int columnIndex) {
                return switch (columnIndex) {
                    case 1 -> listToString(List.of("Question 1", "Question 2"));
                    case 2 -> listToString(List.of("Response 1", "Response 2"));
                    case 3 ->
                            listToString(List.of("Choice a1,Choice a2,Choice a3", "Choice " +
                                    "b1,Choice b2,Choice b3"));
                    case 4 -> listToString(List.of("Answer1", "Answer2"));
                    case 5 -> listToString(List.of("Georgia", "New York"));
                    case 6 -> "CAPITALS_QUIZ";
                    case 7 -> TIME_CREATED;
                    case 8 -> TIME_UPDATED;
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

        System.out.println("Test get by id: expected model = " + expectedModel);
        System.out.println("Test get by id: retrieved model = " + retrievedModel);
    }
}

