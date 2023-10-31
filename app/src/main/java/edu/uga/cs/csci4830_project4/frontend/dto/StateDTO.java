package edu.uga.cs.csci4830_project4.frontend.dto;

import java.io.Serializable;

import edu.uga.cs.csci4830_project4.backend.states.StateModel;

/**
 * DTO for a state.
 */
public class StateDTO implements Serializable {

    private long id;
    private String stateName;
    private String capitalCity;
    private String secondCity;
    private String thirdCity;
    private String statehood;
    private String capitalSince;
    private String sizeRank;

    /**
     * Default constructor.
     */
    public StateDTO() {
        id = -1L;
    }

    /**
     * Constructor for this DTO.
     *
     * @param id           the ID of the state.
     * @param stateName    the state name.
     * @param capitalCity  the capital city.
     * @param secondCity   the second city.
     * @param thirdCity    the third city.
     * @param statehood    the statehood.
     * @param capitalSince the capital since.
     * @param sizeRank     the size rank.
     */
    public StateDTO(long id, String stateName, String capitalCity, String secondCity,
                    String thirdCity, String statehood, String capitalSince, String sizeRank) {
        this.id = id;
        this.stateName = stateName;
        this.capitalCity = capitalCity;
        this.secondCity = secondCity;
        this.thirdCity = thirdCity;
        this.statehood = statehood;
        this.capitalSince = capitalSince;
        this.sizeRank = sizeRank;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    public String getSecondCity() {
        return secondCity;
    }

    public void setSecondCity(String secondCity) {
        this.secondCity = secondCity;
    }

    public String getThirdCity() {
        return thirdCity;
    }

    public void setThirdCity(String thirdCity) {
        this.thirdCity = thirdCity;
    }

    public String getStatehood() {
        return statehood;
    }

    public void setStatehood(String statehood) {
        this.statehood = statehood;
    }

    public String getCapitalSince() {
        return capitalSince;
    }

    public void setCapitalSince(String capitalSince) {
        this.capitalSince = capitalSince;
    }

    public String getSizeRank() {
        return sizeRank;
    }

    public void setSizeRank(String sizeRank) {
        this.sizeRank = sizeRank;
    }

    /**
     * Converts a state model to a state DTO.
     *
     * @param stateModel the state model to convert.
     * @return the state DTO.
     */
    public static StateDTO fromModel(StateModel stateModel) {
        return new StateDTO(stateModel.getId(), stateModel.getStateName(),
                stateModel.getCapitalCity(), stateModel.getSecondCity(),
                stateModel.getThirdCity(), stateModel.getStatehood(),
                stateModel.getCapitalSince(), stateModel.getSizeRank());
    }
}

