package edu.uga.cs.csci4830_project4.frontend.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class StateDTOTest {

    private StateDTO stateDTO;

    @Before
    public void setUp() {
        // Prepare test data
        long id = 1L;
        String stateName = "Test State";
        String capitalCity = "Test Capital";
        String secondCity = "Test Second";
        String thirdCity = "Test Third";
        String statehood = "Test Statehood";
        String capitalSince = "Test Capital Since";
        String sizeRank = "Test Size Rank";

        stateDTO = new StateDTO(id, stateName, capitalCity, secondCity, thirdCity,
                statehood, capitalSince, sizeRank);
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals(1L, stateDTO.getId());
        assertEquals("Test State", stateDTO.getStateName());
        assertEquals("Test Capital", stateDTO.getCapitalCity());
        assertEquals("Test Second", stateDTO.getSecondCity());
        assertEquals("Test Third", stateDTO.getThirdCity());
        assertEquals("Test Statehood", stateDTO.getStatehood());
        assertEquals("Test Capital Since", stateDTO.getCapitalSince());
        assertEquals("Test Size Rank", stateDTO.getSizeRank());

        stateDTO.setId(2L);
        assertEquals(2L, stateDTO.getId());

        stateDTO.setStateName("New State");
        assertEquals("New State", stateDTO.getStateName());

        stateDTO.setCapitalCity("New Capital");
        assertEquals("New Capital", stateDTO.getCapitalCity());

        stateDTO.setSecondCity("New Second");
        assertEquals("New Second", stateDTO.getSecondCity());

        stateDTO.setThirdCity("New Third");
        assertEquals("New Third", stateDTO.getThirdCity());

        stateDTO.setStatehood("New Statehood");
        assertEquals("New Statehood", stateDTO.getStatehood());

        stateDTO.setCapitalSince("New Capital Since");
        assertEquals("New Capital Since", stateDTO.getCapitalSince());

        stateDTO.setSizeRank("New Size Rank");
        assertEquals("New Size Rank", stateDTO.getSizeRank());
    }
}

