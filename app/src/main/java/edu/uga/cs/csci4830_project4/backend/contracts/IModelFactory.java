package edu.uga.cs.csci4830_project4.backend.contracts;

/**
 * Interface for a model factory.
 *
 * @param <M> The type of model to create.
 * @param <T> The type of object to use to create the model.
 */
public interface IModelFactory<M extends IModel, T> {

    /**
     * Creates a model from the provided object.
     *
     * @param t The object to use to create the model.
     * @return The created model.
     */
    M createAndStore(T t);
}
