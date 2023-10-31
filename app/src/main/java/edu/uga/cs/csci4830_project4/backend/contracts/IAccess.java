package edu.uga.cs.csci4830_project4.backend.contracts;

import java.util.List;

/**
 * This interface represents a model in the database.
 *
 * @param <T> The type of model.
 */
public interface IAccess<T extends IModel> {

    /**
     * Opens the database.
     */
    void open();

    /**
     * Closes the database.
     */
    void close();

    /**
     * Stores the model in the database.
     *
     * @param model The model to store.
     * @return The model with its assigned ID.
     */
    T store(T model);

    /**
     * Retrieves the model with the given id from the database.
     *
     * @param id The id of the model to retrieve.
     * @return The model with the given id.
     */
    T getById(long id);

    /**
     * Retrieves the models matching the given parameters from the database.
     *
     * @param columns       The columns to retrieve.
     * @param selection     The selection criteria.
     * @param selectionArgs The selection arguments.
     * @param groupBy       The grouping criteria.
     * @param having        The having criteria.
     * @param orderBy       The ordering criteria.
     * @param limit         The limit criteria.
     * @return The model with the given id.
     */
    List<T> retrieve(String[] columns, String selection, String[] selectionArgs,
                     String groupBy, String having, String orderBy, String limit);

    /**
     * Retrieves all models from the database.
     *
     * @return A list of all models in the database.
     */
    List<T> retrieveAll();

    /**
     * Updates the model in the database.
     *
     * @param model The model to update.
     * @return The number of rows affected.
     */
    int update(T model);

    /**
     * Deletes the models with the given id from the database.
     *
     * @return The number of rows affected.
     */
    int deleteById(long id);

    /**
     * Deletes all models from the database.
     */
    void deleteAll();
}
