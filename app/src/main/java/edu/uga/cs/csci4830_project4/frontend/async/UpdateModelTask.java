package edu.uga.cs.csci4830_project4.frontend.async;

import android.os.AsyncTask;

import edu.uga.cs.csci4830_project4.backend.contracts.IAccess;
import edu.uga.cs.csci4830_project4.backend.contracts.IModel;

public class UpdateModelTask<M extends IModel> extends AsyncTask<M, Void, Void> {

    private final IAccess<M> access;

    public UpdateModelTask(IAccess<M> access) {
        this.access = access;
    }

    @Override
    protected Void doInBackground(M... models) {
        access.open();
        for (M model : models) {
            access.update(model);
        }
        access.close();
        return null;
    }
}
