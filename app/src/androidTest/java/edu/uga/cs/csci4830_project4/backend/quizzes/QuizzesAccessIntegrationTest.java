package edu.uga.cs.csci4830_project4.backend.quizzes;

/*
@RunWith(AndroidJUnit4.class)
public class QuizzesAccessIntegrationTest {

    private QuizzesAccess quizzesAccess;

    @Before
    public void setup() {
        // Use an in-memory database for testing
        Context context = ApplicationProvider.getApplicationContext();
        IDatabaseHelper databaseHelper = QuizzesDatabaseHelper.getInstance(context);
        quizzesAccess = new QuizzesAccess(databaseHelper);
        // Open the database connection
        quizzesAccess.open();
    }

    @After
    public void cleanup() {
        // Close the database connection
        quizzesAccess.close();
    }

    @Test
    public void testInsertAndRetrieve() {
        // Create a QuizModel to insert
        QuizModel quizModel = createSampleQuizModel();

        // Insert the QuizModel into the database
        QuizModel insertedModel = quizzesAccess.store(quizModel);
        System.out.println("Inserted model: " + insertedModel);

        // Retrieve the QuizModel from the database
        QuizModel retrievedModel = quizzesAccess.getById(insertedModel.getId());
        System.out.println("Retrieved model: " + retrievedModel);

        // Perform assertions to validate the retrieved data
        assertNotNull(retrievedModel);
        assertEquals(insertedModel.getId(), retrievedModel.getId());
        // Add more assertions for other fields
    }

    @Test
    public void testUpdate() {
        // Create a QuizModel and insert it into the database
        QuizModel quizModel = createSampleQuizModel();
        QuizModel insertedModel = quizzesAccess.store(quizModel);

        // Modify the QuizModel
        insertedModel.setQuizType(QuizType.CAPITALS_QUIZ);

        // Update the QuizModel in the database
        int updatedRows = quizzesAccess.update(insertedModel);

        // Assert that the update was successful
        assertEquals(1, updatedRows);

        // Retrieve the updated QuizModel and validate the changes
        QuizModel retrievedModel = quizzesAccess.getById(insertedModel.getId());
        assertNotNull(retrievedModel);
        assertEquals(QuizType.CAPITALS_QUIZ, retrievedModel.getQuizType());
        // Add more assertions for other fields
    }

    @Test
    public void testDelete() {
        // Create a QuizModel and insert it into the database
        QuizModel quizModel = createSampleQuizModel();
        QuizModel insertedModel = quizzesAccess.store(quizModel);

        // Delete the QuizModel from the database
        int deletedRows = quizzesAccess.delete(insertedModel.getId());

        // Assert that the delete was successful
        assertEquals(1, deletedRows);

        // Try to retrieve the deleted QuizModel, which should be null
        QuizModel retrievedModel = quizzesAccess.getById(insertedModel.getId());
        assertNull(retrievedModel);
    }

    // Helper method to create a sample QuizModel for testing
    private QuizModel createSampleQuizModel() {
        QuizModel quizModel = new QuizModel();
        quizModel.setQuizType(QuizType.CAPITALS_QUIZ);
        quizModel.setStateNames(List.of("Georgia", "New York"));
        quizModel.setQuestions(List.of("What is the capital of Georgia?", "What is the capital " +
                "of Florida?"));
        quizModel.setAnswers(List.of("Atlanta", "Miami"));
        quizModel.setResponses(List.of("Atlanta", "Montgomery"));
        quizModel.setChoices(List.of(
                List.of("Atlanta", "New York City", "Chicago"),
                List.of("Miami", "Montgomery", "Tallahassee")
        ));
        return quizModel;
    }
}
 */

