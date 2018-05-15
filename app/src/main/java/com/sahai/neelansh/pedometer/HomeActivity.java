package com.sahai.neelansh.pedometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity implements SensorEventListener{

    private String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
            "Saturday"};
    private String[] months = {"January", "February", "March", "April", "May", "June", "July",
            "September", "October", "November", "December"};

    private TextView mDate;
    private TextView mDay;
    private TextView mSteps;
    private TextView mDistance;
    private TextView mAvgSpeed;
    private TextView mDuration;
    private ImageView mTime;

    private SensorManager sensorManager;
    boolean runing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDate = (TextView) findViewById(R.id.tv_date);
        mDay = (TextView) findViewById(R.id.tv_day);
        mSteps = (TextView) findViewById(R.id.tv_steps);
        mDistance = (TextView) findViewById(R.id.tv_distance);
        mAvgSpeed = (TextView) findViewById(R.id.tv_speed);
        mDuration = (TextView) findViewById(R.id.tv_time);
        mTime = (ImageView) findViewById(R.id.iv_time);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Date time = Calendar.getInstance().getTime();

        mDay.setText(days[time.getDay()]);
        mDay.setAllCaps(true);

        mDate.setText(String.valueOf(time.getDate())+"-"+months[time.getMonth()]+"-"
                +String.valueOf(time.getYear()+1900));
    }

    @Override
    protected void onResume() {
        super.onResume();
        runing = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "Step Counter Sensors Unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        runing = false;
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(runing) {
            mSteps.setText(String.valueOf(sensorEvent.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.d("Acc-Changed", String.valueOf(i)+String.valueOf(sensor.getType()));
    }
}
