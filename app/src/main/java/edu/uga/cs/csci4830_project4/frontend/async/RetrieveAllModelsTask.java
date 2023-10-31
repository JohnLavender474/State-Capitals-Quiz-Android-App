package edu.uga.cs.csci4830_project4.frontend.async;

import android.os.AsyncTask;

import java.util.List;
import java.util.function.Consumer;

import edu.uga.cs.csci4830_project4.backend.contracts.IAccess;
import edu.uga.cs.csci4830_project4.backend.contracts.IModel;

/**
 * This class provides an asynchronous task for retrieving all models.
 *
 * @param <M> the type of the model.
 */
public class RetrieveAllModelsTask<M extends IModel> extends AsyncTask<Void, Void, List<M>> {

    private final IAccess<M> access;
    private final Consumer<List<M>> callback;

    /**
     * Constructs a new {@link RetrieveAllModelsTask} instance.
     *
     * @param access   the access object to use for retrieving the models.
     * @param callback the callback to run after the task completes.
     */
    public RetrieveAllModelsTask(IAccess<M> access, Consumer<List<M>> callback) {
        this.access = access;
        this.callback = callback;
    }

    @Override
    protected List<M> doInBackground(Void... voids) {
        access.open();
        List<M> models = access.retrieveAll();
        access.close();
        return models;
    }

    @Override
    protected void onPostExecute(List<M> ms) {
        super.onPostExecute(ms);
        callback.accept(ms);
    }
}
