package com.theoretics.mobilepos.util;

import android.os.AsyncTask;

public class ServerTaskHelper extends AsyncTask {

    private TaskDelegate delegate;

    //here is the task protocol to can delegate on other object
    public interface TaskDelegate {
        //define you method headers to override
        void onTaskEndWithResult(int success);
        //void onTaskFinishGettingData(Data result);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

}
