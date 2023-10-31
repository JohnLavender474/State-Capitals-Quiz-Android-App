package edu.uga.cs.csci4830_project4.frontend.async;

import android.os.AsyncTask;

import java.util.function.Consumer;

import edu.uga.cs.csci4830_project4.backend.contracts.IAccess;

public class DeleteModelByIdTask extends AsyncTask<Long, Void, Integer> {

    private final IAccess<?> access;
    private final Consumer<Integer> callback;

    public DeleteModelByIdTask(IAccess<?> access) {
        this(access, null);
    }

    public DeleteModelByIdTask(IAccess<?> access, Consumer<Integer> callback) {
        this.access = access;
        this.callback = callback;
    }

    @Override
    protected Integer doInBackground(Long... ids) {
        int deleted = 0;
        access.open();
        for (Long id : ids) {
            deleted += access.deleteById(id);
        }
        access.close();
        return deleted;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (callback != null) {
            callback.accept(integer);
        }
    }
}
