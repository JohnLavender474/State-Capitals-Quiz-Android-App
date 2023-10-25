package edu.uga.cs.csci4830_project4.database.model;

/**
 * This class represents a row in the state_capitals table. It is a simple POJO
 * (Plain Old Java Object) class used to store data retrieved from the database.
 * <p>
 * It contains fields to store the properties of a state-capital row, including
 * an identifier (id), the name of the state (state), and the choices associated
 * with the state (choices).
 */
public class StateCapitalModel {

    private long id;
    private String state;
    private String[] choices;

    /**
     * Constructs a new StateCapitalModel with default values.
     */
    public StateCapitalModel() {
        id = -1;
        state = null;
        choices = null;
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
     * Gets the choices associated with the state.
     *
     * @return The choices associated with the state.
     */
    public String[] getChoices() {
        return choices;
    }

    /**
     * Sets the choices associated with the state.
     *
     * @param choices The new choices to set.
     */
    public void setChoices(String[] choices) {
        this.choices = choices;
    }
}
