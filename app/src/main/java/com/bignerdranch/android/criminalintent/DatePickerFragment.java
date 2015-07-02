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
public class DatePickerFragment extends DialogFragment {

    public static String EXTRA_DATE = "date";

    public static DatePickerFragment newInstance(Date date)
    {
        DatePickerFragment dpf = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE,date);
        dpf.setArguments(args);
        return dpf;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
        Date date = (Date)getArguments().getSerializable(EXTRA_DATE);

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePicker dp = (DatePicker)v.findViewById(R.id.date_id);
        dp.init(year,month,day,new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                Date date = (Date)getArguments().getSerializable(EXTRA_DATE);

                Calendar c = Calendar.getInstance();
                c.setTime(date);

                int hour = c.get(Calendar.HOUR);
                int minute = c.get(Calendar.MINUTE);
                int second = c.get(Calendar.SECOND);

                Date dates=new GregorianCalendar(year, month, day,hour,minute,second).getTime();
                getArguments().putSerializable(EXTRA_DATE,dates);
            }
        });
//        TimePicker tp = (TimePicker)v.findViewById(R.id.time_id);
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.date_picker_title).setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
        i.putExtra(EXTRA_DATE,(Date)getArguments().getSerializable(EXTRA_DATE));
        getTargetFragment().onActivityResult(CrimeFragment.REQUEST_DATE,resultCode,i);
    }

}
