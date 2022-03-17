package com.example.imageview;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.M)
public class ActivityTp extends AppCompatActivity implements BottomSheet.BottomSheetListener {

    TimePicker timeP;
    TextView txtTp;
    Button btn_set, btn_cancel, btnn;
    EditText edt_req;

    int year1, month1, day1, hour, min;

    int req = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp);

        timeP = findViewById(R.id.timeP);
        txtTp = findViewById(R.id.txtTp);
        btn_set = findViewById(R.id.btn_set);
        btn_cancel = findViewById(R.id.btn_cancel);
        btnn = findViewById(R.id.btnn);
        edt_req = findViewById(R.id.edt_req);
        txtTp.setText("No Alarm");
        // init();

        btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheet bottomSheet = new BottomSheet();
                bottomSheet.show(getSupportFragmentManager(), "bottomSheet");
            }
        });


        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                req = Integer.parseInt(edt_req.getText().toString());
                hour = timeP.getHour();
                min = timeP.getMinute();
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year1);
                c.set(Calendar.MONTH, month1);
                c.set(Calendar.DAY_OF_MONTH, day1);
                c.set(Calendar.HOUR_OF_DAY, hour);
                c.set(Calendar.MINUTE, min);
                c.set(Calendar.SECOND, 0);
                setAlarm(c);
                init();
            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
                txtTp.setText("No Alarm");
            }
        });

        timeP.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int min) {

                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hour);
                c.set(Calendar.MINUTE, min);
                c.set(Calendar.SECOND, 0);
                //setAlarm(c);
                //Toast.makeText(ActivityTp.this, hour+"."+min+c, Toast.LENGTH_SHORT).show();
            }
        });
    }


    void setAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ActivityTp.this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, req, intent, 0);


        //it checks the passed time.
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }

    void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ActivityTp.this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, req, intent, 0);

        alarmManager.cancel(pendingIntent);
    }

    @SuppressLint("SetTextI18n")
    private void init() {

        String format = "";

        if (hour >= 12) {

            format = "PM";
            if (hour != 12)
                hour -= 12;

        } else format = "AM";

        txtTp.setText("Alarm set on: " + addZero(hour) + " : " + addZero(min) + format + req);

    }

    String addZero(int num) {
        if (num <= 9)
            return "0" + num;
        else
            return num + "";

    }


    @Override
    public void onButtonClicked(int year, int month, int day) {
        year1 = year;
        month1 = month;
        day1 = day;
        btnn.setText(year1 + "/" + month1 + "/" + day1);
    }
}
