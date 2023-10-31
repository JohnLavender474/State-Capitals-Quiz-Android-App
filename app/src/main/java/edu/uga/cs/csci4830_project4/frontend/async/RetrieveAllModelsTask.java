package edu.uga.cs.csci4830_project4.frontend.async;

import android.os.AsyncTask;

import java.util.List;
import java.util.function.Consumer;

import edu.uga.cs.csci4830_project4.backend.contracts.IAccess;
import edu.uga.cs.csci4830_project4.backend.contracts.IModel;

public class RetrieveAllModelsTask<M extends IModel> extends AsyncTask<Void, Void, List<M>> {

    private final IAccess<M> access;
    private final Consumer<List<M>> callback;

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
