package edu.uga.cs.csci4830_project4.backend.contracts;

/**
 * This interface represents a model in the database.
 */
public interface IModel {

    /**
     * Returns the model's id.
     *
     * @return the model's id.
     */
    long getId();

    /**
     * Sets the model's id.
     *
     * @param id the model's id.
     */
    void setId(long id);
}
