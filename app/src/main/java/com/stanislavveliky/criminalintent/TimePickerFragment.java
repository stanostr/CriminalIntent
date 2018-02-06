package com.stanislavveliky.criminalintent;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by stan_ on 1/23/2018.
 */

public class TimePickerFragment extends DialogFragment {

    public static final String TAG = "TimePickerFragment";
    public static final String EXTRA_TIME = "com.stanislavveliky.criminalintent.time";
    private static final String ARG_DATE = "time";
    private TimePicker mTimePicker;
    private Date mDate;

    public static TimePickerFragment newInstance(Date date)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState)
    {

        mDate = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);
        mTimePicker = view.findViewById(R.id.dialog_time_time_picker);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker.setHour(hour);
            mTimePicker.setMinute(minute);
        }
        else {
            mTimePicker.setCurrentHour(hour);
            mTimePicker.setCurrentMinute(minute);
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(R.string.time_picker_title);
        dialog.setView(view);
        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int hour;
                int minute;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hour = mTimePicker.getHour();
                    minute = mTimePicker.getMinute();
                }
                else {
                    hour = mTimePicker.getCurrentHour();
                    minute = mTimePicker.getCurrentMinute();
                }
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(mDate);
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                mDate = calendar.getTime();
                Log.d(TAG, mDate.toString());
                sendResult(Activity.RESULT_OK, mDate);
            }
        });

        return dialog.create();
    }

    public void sendResult(int resultCode, Date date)
    {
        if(getTargetFragment() == null) return;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
