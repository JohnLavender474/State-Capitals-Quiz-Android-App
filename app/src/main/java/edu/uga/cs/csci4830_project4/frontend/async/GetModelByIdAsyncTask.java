package edu.uga.cs.csci4830_project4.frontend.async;

import android.os.AsyncTask;

import edu.uga.cs.csci4830_project4.backend.contracts.IAccess;
import edu.uga.cs.csci4830_project4.backend.contracts.IModel;

public class GetModelByIdAsyncTask<T extends IModel> extends AsyncTask<Long, Void, T> {
    private final IAccess<T> access;

    public GetModelByIdAsyncTask(IAccess<T> access) {
        this.access = access;
    }

    @Override
    protected T doInBackground(Long... ids) {
        if (ids.length > 0) {
            long id = ids[0];
            return access.getById(id);
        }
        return null;
    }
}

