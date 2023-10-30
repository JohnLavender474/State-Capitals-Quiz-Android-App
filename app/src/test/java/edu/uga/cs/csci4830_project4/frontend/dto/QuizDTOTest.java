package edu.uga.cs.csci4830_project4.frontend.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.csci4830_project4.common.QuizType;

public class QuizDTOTest {

    private QuizDTO quizDTO;

    @Before
    public void setUp() {
        // Prepare test data
        long quizId = 1L;
        List<String> questions = new ArrayList<>();
        questions.add("Question 1");
        questions.add("Question 2");

        List<List<String>> choices = new ArrayList<>();
        choices.add(new ArrayList<>());
        choices.add(new ArrayList<>());

        List<String> responses = new ArrayList<>();
        responses.add("");
        responses.add("");

        List<String> answers = new ArrayList<>();
        answers.add("Answer1");
        answers.add("Answer2");

        List<String> stateNames = new ArrayList<>();
        stateNames.add("State1");
        stateNames.add("State2");

        quizDTO = new QuizDTO(quizId, QuizType.CAPITALS_QUIZ, questions, choices, responses,
                answers, stateNames);
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals(1L, quizDTO.getQuizId());
        assertNotNull(quizDTO.getQuestions());
        assertNotNull(quizDTO.getChoices());
        assertNotNull(quizDTO.getResponses());
        assertNotNull(quizDTO.getAnswers());
        assertNotNull(quizDTO.getStateNames());

        quizDTO.setQuizId(2L);
        assertEquals(2L, quizDTO.getQuizId());

        List<String> newQuestions = new ArrayList<>();
        newQuestions.add("New Question");
        quizDTO.setQuestions(newQuestions);
        assertEquals(newQuestions, quizDTO.getQuestions());

        List<List<String>> newChoices = new ArrayList<>();
        newChoices.add(new ArrayList<>());
        quizDTO.setChoices(newChoices);
        assertEquals(newChoices, quizDTO.getChoices());

        List<String> newResponses = new ArrayList<>();
        newResponses.add("New Response");
        quizDTO.setResponses(newResponses);
        assertEquals(newResponses, quizDTO.getResponses());

        List<String> newAnswers = new ArrayList<>();
        newAnswers.add("New Answer");
        quizDTO.setAnswers(newAnswers);
        assertEquals(newAnswers, quizDTO.getAnswers());

        List<String> newStateNames = new ArrayList<>();
        newStateNames.add("New State");
        quizDTO.setStateNames(newStateNames);
        assertEquals(newStateNames, quizDTO.getStateNames());
    }

    @Test
    public void testSetResponse() {
        quizDTO.setResponse(0, "New Response");
        assertEquals("New Response", quizDTO.getResponses().get(0));
    }
}

