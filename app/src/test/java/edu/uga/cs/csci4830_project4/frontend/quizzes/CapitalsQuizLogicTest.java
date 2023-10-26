package edu.uga.cs.csci4830_project4.frontend.quizzes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import edu.uga.cs.csci4830_project4.backend.contracts.IAccess;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizModel;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizType;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizzesAccess;
import edu.uga.cs.csci4830_project4.backend.states.StateModel;
import edu.uga.cs.csci4830_project4.frontend.quizzes.impl.CapitalsQuizLogic;

public class CapitalsQuizLogicTest {

    private CapitalsQuizLogic quizLogic;
    private QuizModel quizModel;
    private IAccess<QuizModel> quizAccess;

    @Before
    public void setUp() {
        quizModel = new QuizModel();
        quizModel.setQuizType(QuizType.CAPITAL_QUIZ);
        quizModel.setResponses(Arrays.asList(null, null, null));
        quizModel.setAnsweredCorrectly(Arrays.asList(false, false, false));

        StateModel stateModel1 = new StateModel();
        stateModel1.setCapitalCity("Atlanta");
        stateModel1.setStateName("Georgia");
        stateModel1.setId(1L);

        StateModel stateModel2 = new StateModel();
        stateModel2.setCapitalCity("Montgomery");
        stateModel2.setStateName("Alabama");
        stateModel2.setId(2L);
        stateModel2.setId(2L);

        StateModel stateModel3 = new StateModel();
        stateModel3.setCapitalCity("Tallahassee");
        stateModel3.setStateName("Florida");
        stateModel3.setId(3L);

        List<StateModel> stateModels = Arrays.asList(stateModel1, stateModel2, stateModel3);

        quizAccess = mock(QuizzesAccess.class);
        quizLogic = new CapitalsQuizLogic(quizModel, stateModels, quizAccess);
    }

    @Test
    public void testGetCurrentQuestion() {
        // Check if the question for the first state is correct
        assertEquals("What is the capital of Georgia?", quizLogic.getCurrentQuestion());
    }

    @Test
    public void testGetCurrentQuestionIndex() {
        // Initial current question index should be 0
        assertEquals(0, quizLogic.getCurrentQuestionIndex());
    }

    @Test
    public void testSetCurrentQuestionIndexValid() {
        // Set the current question index to a valid value (1)
        boolean result = quizLogic.setCurrentQuestionIndex(1);
        // The method should return true when the index is valid
        assertTrue(result);
        // The current question index should be updated to 1
        assertEquals(1, quizLogic.getCurrentQuestionIndex());
    }

    @Test
    public void testSetCurrentQuestionIndexInvalid() {
        // Attempt to set the current question index to an invalid value (-1)
        boolean result = quizLogic.setCurrentQuestionIndex(-1);
        // The method should return false when the index is invalid
        assertFalse(result);
        // The current question index should remain unchanged (0)
        assertEquals(0, quizLogic.getCurrentQuestionIndex());
    }

    @Test
    public void testSetCurrentQuestionIndexAtEnd() {
        // Set the current question index to the last question
        boolean result = quizLogic.setCurrentQuestionIndex(2);
        // The method should return true when the index is valid
        assertTrue(result);
        // The current question index should be updated to 2
        assertEquals(2, quizLogic.getCurrentQuestionIndex());
    }

    @Test
    public void testSetCurrentQuestionIndexBeyondEnd() {
        // Attempt to set the current question index beyond the last question (3)
        boolean result = quizLogic.setCurrentQuestionIndex(3);
        // The method should return false when the index is invalid
        assertFalse(result);
        // The current question index should remain unchanged (0)
        assertEquals(0, quizLogic.getCurrentQuestionIndex());
    }

    @Test
    public void testGetSizeOfQuiz() {
        // The quiz size should match the number of state models
        assertEquals(3, quizLogic.getSizeOfQuiz());
    }

    @Test
    public void testGoToNextQuestion() {
        assertTrue(quizLogic.goToNextQuestion());
        assertTrue(quizLogic.goToNextQuestion());
        assertFalse(quizLogic.goToNextQuestion());
    }

    @Test
    public void testIndexAfterGoToNextQuestion() {
        quizLogic.goToNextQuestion();
        // After moving to the next question, the current question index should be 1
        assertEquals(1, quizLogic.getCurrentQuestionIndex());
    }

    @Test
    public void testGoToNextQuestionAtEnd() {
        // Move to the last question
        quizLogic.goToNextQuestion();
        quizLogic.goToNextQuestion();
        quizLogic.goToNextQuestion();
        // Attempting to move beyond the last question should keep the index at the last question
        assertEquals(2, quizLogic.getCurrentQuestionIndex());
    }

    @Test
    public void testGoToPreviousQuestion() {
        assertFalse(quizLogic.goToPreviousQuestion());
        quizLogic.goToNextQuestion();
        quizLogic.goToNextQuestion();
        assertTrue(quizLogic.goToPreviousQuestion());
        assertTrue(quizLogic.goToPreviousQuestion());
        assertFalse(quizLogic.goToPreviousQuestion());
    }

    @Test
    public void testIndexAfterGoToPreviousQuestion() {
        // Move to the second question
        quizLogic.goToNextQuestion();
        // After moving to the second question, going back to the first question
        quizLogic.goToPreviousQuestion();
        assertEquals(0, quizLogic.getCurrentQuestionIndex());
    }

    @Test
    public void testGoToPreviousQuestionAtStart() {
        // Attempting to move back from the first question should keep the index at 0
        quizLogic.goToPreviousQuestion();
        assertEquals(0, quizLogic.getCurrentQuestionIndex());
    }

    @Test
    public void testAtEndOfQuiz() {
        // The end of the quiz should be reached when the current question is the last one
        assertFalse(quizLogic.atEndOfQuiz()); // Initially, not at the end
        quizLogic.goToNextQuestion(); // Move to the second question
        assertFalse(quizLogic.atEndOfQuiz()); // Not at the end
        quizLogic.goToNextQuestion(); // Move to the last question
        assertTrue(quizLogic.atEndOfQuiz()); // At the end
    }

    @Test
    public void testAtStartOfQuiz() {
        // At the start when the current question is the first one
        assertTrue(quizLogic.atStartOfQuiz()); // Initially at the start
        quizLogic.goToNextQuestion(); // Move to the second question
        assertFalse(quizLogic.atStartOfQuiz()); // Not at the start
    }

    @Test
    public void testSubmitResponseCorrect() {
        quizLogic.submitResponse("Atlanta"); // Correct response
        // After submitting a correct response, the score should be updated
        assertEquals(1, quizLogic.getScore());
        // Verify that the quiz model was updated through the access interface
        verify(quizAccess).update(quizModel);
    }

    @Test
    public void testSubmitResponseIncorrect() {
        quizLogic.submitResponse("Montgomery"); // Incorrect response
        // After submitting an incorrect response, the score should remain the same
        assertEquals(0, quizLogic.getScore());
        // Verify that the quiz model was updated through the access interface
        verify(quizAccess).update(quizModel);
    }

    @Test
    public void testSubmitSameResponseTwice() {
        // Set the current question index to the second question (index 1)
        quizLogic.setCurrentQuestionIndex(1);
        // Submit the correct answer for the second question
        quizLogic.submitResponse("Montgomery");
        // The score should be incremented
        assertEquals(1, quizLogic.getScore());
        // The answeredCorrectly value for the second question should be true
        assertTrue(quizModel.getAnsweredCorrectly().get(1));
        // Submit the correct answer again for the second question
        quizLogic.submitResponse("Montgomery");
        // The score should remain the same
        assertEquals(1, quizLogic.getScore());
        // THe answeredCorrectly value for the second question should be unchanged
        assertTrue(quizModel.getAnsweredCorrectly().get(1));
    }

    @Test
    public void testChangeResponseFromIncorrectToCorrect() {
        // Set the current question index to the second question (index 1)
        quizLogic.setCurrentQuestionIndex(1);
        // Submit an incorrect answer for the second question
        quizLogic.submitResponse("Los Angeles");
        // The score should be decremented
        assertEquals(0, quizLogic.getScore());
        // The answeredCorrectly value for the second question should be false
        assertFalse(quizModel.getAnsweredCorrectly().get(1));
        // Submit the correct answer again for the second question
        quizLogic.submitResponse("Montgomery");
        // The score should remain the same
        assertEquals(1, quizLogic.getScore());
        // THe answeredCorrectly value for the second question should be unchanged
        assertTrue(quizModel.getAnsweredCorrectly().get(1));
    }

    @Test
    public void testChangeResponseFromCorrectToIncorrect() {
        // Set the current question index to the first question (index 0)
        quizLogic.setCurrentQuestionIndex(0);
        // Submit the correct answer for the first question
        quizLogic.submitResponse("Atlanta");
        // The score should remain the same
        assertEquals(1, quizLogic.getScore());
        // The answeredCorrectly value for the first question should be true
        assertTrue(quizModel.getAnsweredCorrectly().get(0));
        // Submit the incorrect answer again for the first question
        quizLogic.submitResponse("Montgomery");
        // The score should be decremented
        assertEquals(0, quizLogic.getScore());
        // Tee answeredCorrectly value for the first question should be changed to false
        assertFalse(quizModel.getAnsweredCorrectly().get(0));
    }

    @Test
    public void testChangeResponseFromIncorrectToIncorrect() {
        // Set the current question index to the first question (index 0)
        quizLogic.setCurrentQuestionIndex(0);
        // Submit an incorrect answer for the first question
        quizLogic.submitResponse("Los Angeles");
        // The score should remain at zero
        assertEquals(0, quizLogic.getScore());
        // The answeredCorrectly value for the first question should be false
        assertFalse(quizModel.getAnsweredCorrectly().get(0));
        // Submit an incorrect answer again for the first question
        quizLogic.submitResponse("New York City");
        // The score should remain the same
        assertEquals(0, quizLogic.getScore());
        // The answeredCorrectly value for the first question should still be false
        assertFalse(quizModel.getAnsweredCorrectly().get(0));
    }

    @Test
    public void testFinishQuiz() {
        quizLogic.finishQuiz();
        // After finishing the quiz, the quiz model's finished flag should be set to true
        assertTrue(quizModel.isFinished());
        // Verify that the quiz model was updated through the access interface
        verify(quizAccess).update(quizModel);
    }
}

