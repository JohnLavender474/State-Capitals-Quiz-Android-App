package edu.uga.cs.csci4830_project4.frontend.async;

import android.os.AsyncTask;

import edu.uga.cs.csci4830_project4.backend.contracts.IAccess;
import edu.uga.cs.csci4830_project4.backend.contracts.IModel;

public class UpdateModelAsyncTask<T extends IModel> extends AsyncTask<T, Void, Boolean> {

    private final IAccess<T> access;

    public UpdateModelAsyncTask(IAccess<T> access) {
        this.access = access;
    }

    @SafeVarargs
    @Override
    protected final Boolean doInBackground(T... models) {
        boolean success = false;
        for (T model : models) {
            int updateCount = access.update(model);
            if (!success && updateCount > 0) {
                success = true;
            }
        }
        return success;
    }
}

