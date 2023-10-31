package edu.uga.cs.csci4830_project4.backend.quizzes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.csci4830_project4.backend.states.StateModel;
import edu.uga.cs.csci4830_project4.backend.states.StatesAccess;
import edu.uga.cs.csci4830_project4.common.QuizType;

public class QuizModelFactoryTest {

    private QuizzesAccess quizzesAccess;

    private StatesAccess statesAccess;

    private QuizModelFactory quizModelFactory;

    @Before
    public void setUp() {
        quizzesAccess = Mockito.mock(QuizzesAccess.class);
        statesAccess = Mockito.mock(StatesAccess.class);
        quizModelFactory = new QuizModelFactory(quizzesAccess, statesAccess);
    }

    @Test
    public void testCreateAndStore() {
        // Prepare test data
        int numberOfQuestions = 5;
        List<StateModel> randomStates = new ArrayList<>();
        for (int i = 0; i < numberOfQuestions; i++) {
            StateModel state = new StateModel();
            state.setStateName("TestState" + i);
            state.setCapitalCity("TestCapital" + i);
            state.setSecondCity("TestSecond" + i);
            state.setThirdCity("TestThird" + i);
            randomStates.add(state);
        }

        // Mock behavior for dependencies
        Mockito.when(statesAccess.getRandomStates(numberOfQuestions)).thenReturn(randomStates);
        Mockito.when(quizzesAccess.store(Mockito.any(QuizModel.class))).thenAnswer(invocation -> {
            QuizModel quiz = invocation.getArgument(0);
            quiz.setId(1L);
            return quiz;
        });

        // Call the method to test
        QuizModelFactory.QuizModelFactoryParams params =
                new QuizModelFactory.QuizModelFactoryParams(QuizType.CAPITALS_QUIZ,
                        numberOfQuestions);
        QuizModel quiz = quizModelFactory.createAndStore(params);

        // Verify the behavior
        assertNotNull(quiz);
        assertEquals(QuizType.CAPITALS_QUIZ, quiz.getQuizType());
        assertEquals(numberOfQuestions, quiz.getQuestions().size());

        // Assertions for State Names
        List<String> stateNames = quiz.getStateNames();
        assertNotNull(stateNames);
        assertEquals(numberOfQuestions, stateNames.size());
        for (int i = 0; i < numberOfQuestions; i++) {
            assertEquals("TestState" + i, stateNames.get(i));
        }

        // Assertions for Questions
        List<String> questions = quiz.getQuestions();
        assertNotNull(questions);
        assertEquals(numberOfQuestions, questions.size());
        for (int i = 0; i < numberOfQuestions; i++) {
            assertEquals("What is the capital of TestState" + i + "?", questions.get(i));
        }

        // Assertions for Choices
        List<List<String>> choices = quiz.getChoices();
        assertNotNull(choices);
        assertEquals(numberOfQuestions, choices.size());
        for (int i = 0; i < numberOfQuestions; i++) {
            List<String> stateChoices = choices.get(i);
            assertNotNull(stateChoices);
            assertEquals(3, stateChoices.size());
            assertTrue(stateChoices.contains("TestCapital" + i));
            assertTrue(stateChoices.contains("TestSecond" + i));
            assertTrue(stateChoices.contains("TestThird" + i));
        }

        // Assertions for Responses
        List<String> responses = quiz.getResponses();
        assertNotNull(responses);
        assertEquals(numberOfQuestions, responses.size());
        for (int i = 0; i < numberOfQuestions; i++) {
            assertEquals("", responses.get(i));
        }

        // Assertions for Answers
        List<String> answers = quiz.getAnswers();
        assertNotNull(answers);
        assertEquals(numberOfQuestions, answers.size());
        for (int i = 0; i < numberOfQuestions; i++) {
            assertEquals("TestCapital" + i, answers.get(i));
        }
    }

}
