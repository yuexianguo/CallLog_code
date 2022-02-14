package com.phone.base.jobservice;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2022/1/26
 */
public class Helpers {
    private static JobService mJob;
    private static JobParameters mJobParams;
    private static final String TAG = T.TAG;
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void schedule(Context context) {
        Log.w(TAG, "Helpers schedule()");

        final JobScheduler scheduler = context.getSystemService(JobScheduler.class);
        final JobInfo.Builder builder = new JobInfo.Builder(BackJobService.ELLISONS_JOB_ID,
                new ComponentName(context, BackJobService.class));

        builder.setOverrideDeadline(BackJobService.ELLISONS_JOB_OVERDIDE_DEADLINE);
        scheduler.schedule(builder.build());
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void cancelJob(Context context) {
        Log.w(TAG, "Helpers cancelJob()");
        final JobScheduler scheduler = context.getSystemService(JobScheduler.class);
        scheduler.cancel(BackJobService.ELLISONS_JOB_ID);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void jobFinished() {
        Log.w(TAG, "Helpers jobFinished()");
        mJob.jobFinished(mJobParams, false);
    }

    public static void enqueueJob() {
        Log.w(TAG, "Helpers enqueueJob()");
    }

    public static void doHardWork(JobService job, JobParameters params) {
        Log.w(TAG, "Helpers doHardWork()");
        mJob = job;
        mJobParams = params;
//        mHandler.postDelayed(mRunnable,5000L);
    }

}
