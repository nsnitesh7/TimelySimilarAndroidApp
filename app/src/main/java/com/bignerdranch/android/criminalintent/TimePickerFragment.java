package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by nitesi on 03-07-2015.
 */
public class TimePickerFragment extends DialogFragment {

    public static String EXTRA_TIME = "time";

    public static TimePickerFragment newInstance(Date date)
    {
        TimePickerFragment dpf = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TIME,date);
        dpf.setArguments(args);
        return dpf;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);
        Date date = (Date)getArguments().getSerializable(EXTRA_TIME);

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        TimePicker dp = (TimePicker)v.findViewById(R.id.time_id);
        dp.setCurrentHour(hour);
        dp.setCurrentMinute(minute);
        dp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                Date date = (Date)getArguments().getSerializable(EXTRA_TIME);

                Calendar c = Calendar.getInstance();
                c.setTime(date);

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                Date dates=new GregorianCalendar(year, month, day,hour,minute,0).getTime();
                getArguments().putSerializable(EXTRA_TIME,dates);
            }
        });
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.time_picker_title).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                SendResult(Activity.RESULT_OK);
            }
        }).create();
    }

    private void SendResult(int resultCode)
    {
        if(getTargetFragment()==null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_TIME,(Date)getArguments().getSerializable(EXTRA_TIME));
        getTargetFragment().onActivityResult(CrimeFragment.REQUEST_TIME, resultCode, i);
    }

}
