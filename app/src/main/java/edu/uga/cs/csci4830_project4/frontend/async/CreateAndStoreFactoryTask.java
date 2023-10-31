package edu.uga.cs.csci4830_project4.frontend.async;

import android.os.AsyncTask;

import java.util.function.Consumer;

import edu.uga.cs.csci4830_project4.backend.contracts.IModel;
import edu.uga.cs.csci4830_project4.backend.contracts.IModelFactory;

/**
 * An {@link AsyncTask} that creates a new model using a {@link IModelFactory} and stores it in the
 * database. The model is returned in the callback.
 *
 * @param <T> the type of the parameter to the factory.
 * @param <M> the type of the model.
 */
public class CreateAndStoreFactoryTask<T, M extends IModel> extends AsyncTask<T, Void, M> {

    private final IModelFactory<M, T> factory;
    private final Consumer<M> callback;

    /**
     * Constructs a new {@link CreateAndStoreFactoryTask} with the provided factory.
     *
     * @param factory the factory to use for creating the model.
     */
    public CreateAndStoreFactoryTask(IModelFactory<M, T> factory) {
        this(factory, null);
    }

    /**
     * Constructs a new {@link CreateAndStoreFactoryTask} with the provided factory and callback.
     *
     * @param factory the factory to use for creating the model.
     * @param callback the callback to call when the model is created.
     */
    public CreateAndStoreFactoryTask(IModelFactory<M, T> factory, Consumer<M> callback) {
        this.factory = factory;
        this.callback = callback;
    }

    @Override
    protected M doInBackground(T... t) {
        if (t.length == 0) {
            return null;
        }
        return factory.createAndStore(t[0]);
    }

    @Override
    protected void onPostExecute(M m) {
        super.onPostExecute(m);
        if (callback != null) {
            callback.accept(m);
        }
    }
}
