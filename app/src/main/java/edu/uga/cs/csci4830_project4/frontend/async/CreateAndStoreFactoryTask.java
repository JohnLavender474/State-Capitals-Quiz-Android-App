package edu.uga.cs.csci4830_project4.frontend.async;

import android.os.AsyncTask;

import java.util.function.Consumer;

import edu.uga.cs.csci4830_project4.backend.contracts.IModel;
import edu.uga.cs.csci4830_project4.backend.contracts.IModelFactory;

public class CreateAndStoreFactoryTask<T, M extends IModel> extends AsyncTask<T, Void, M> {

    private final IModelFactory<M, T> factory;
    private final Consumer<M> callback;

    public CreateAndStoreFactoryTask(IModelFactory<M, T> factory) {
        this(factory, null);
    }

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
