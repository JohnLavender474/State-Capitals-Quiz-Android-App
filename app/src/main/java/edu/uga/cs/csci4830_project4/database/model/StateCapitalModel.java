package edu.uga.cs.csci4830_project4.database.model;

/**
 * This class represents a row in the state_capitals table.
 */
public class StateCapitalModel {

    private long id;
    private String state;
    private String capitalCity;
    private String secondCity;
    private String thirdCity;
    private String statehood;
    private String capitalSince;
    private int sizeRank;

    /**
     * Constructs a new StateCapitalModel with default values.
     */
    public StateCapitalModel() {
        id = -1;
        state = null;
        capitalCity = null;
        secondCity = null;
        thirdCity = null;
        statehood = null;
        capitalSince = null;
        sizeRank = -1;
    }

    /**
     * Gets the identifier of the state-capital row.
     *
     * @return The identifier (id) of the state-capital row.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the identifier of the state-capital row.
     *
     * @param id The new identifier (id) to set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the name of the state associated with the row.
     *
     * @return The name of the state.
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the name of the state associated with the row.
     *
     * @param state The new state name to set.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the capital city of the state.
     *
     * @return The capital city of the state.
     */
    public String getCapitalCity() {
        return capitalCity;
    }

    /**
     * Sets the capital city of the state.
     *
     * @param capitalCity The new capital city to set.
     */
    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    /**
     * Gets the second-largest city in the state.
     *
     * @return The second-largest city in the state.
     */
    public String getSecondCity() {
        return secondCity;
    }

    /**
     * Sets the second-largest city in the state.
     *
     * @param secondCity The new second-largest city to set.
     */
    public void setSecondCity(String secondCity) {
        this.secondCity = secondCity;
    }

    /**
     * Gets the third-largest city in the state.
     *
     * @return The third-largest city in the state.
     */
    public String getThirdCity() {
        return thirdCity;
    }

    /**
     * Sets the third-largest city in the state.
     *
     * @param thirdCity The new third-largest city to set.
     */
    public void setThirdCity(String thirdCity) {
        this.thirdCity = thirdCity;
    }

    /**
     * Gets the statehood date of the state.
     *
     * @return The statehood date of the state.
     */
    public String getStatehood() {
        return statehood;
    }

    /**
     * Sets the statehood date of the state.
     *
     * @param statehood The new statehood date to set.
     */
    public void setStatehood(String statehood) {
        this.statehood = statehood;
    }

    /**
     * Gets the year in which the state's capital was established.
     *
     * @return The year in which the state's capital was established.
     */
    public String getCapitalSince() {
        return capitalSince;
    }

    /**
     * Sets the year in which the state's capital was established.
     *
     * @param capitalSince The new year of the state's capital establishment to set.
     */
    public void setCapitalSince(String capitalSince) {
        this.capitalSince = capitalSince;
    }

    /**
     * Gets the size rank of the state among all U.S. states.
     *
     * @return The size rank of the state.
     */
    public int getSizeRank() {
        return sizeRank;
    }

    /**
     * Sets the size rank of the state among all U.S. states.
     *
     * @param sizeRank The new size rank of the state to set.
     */
    public void setSizeRank(int sizeRank) {
        this.sizeRank = sizeRank;
    }
}
