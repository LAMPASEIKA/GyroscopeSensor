package com.example.gyroscopesensor;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView xy, xz, zy;

    Sensor mAccelerometer, mMagnetic;

    SensorManager mSensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        Log.d("SENSOR1", mSensorManager.getSensorList(Sensor.TYPE_ALL).toString());

        xy = findViewById(R.id.XY);
        xz = findViewById(R.id.XZ);
        zy = findViewById(R.id.ZY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mMagnetic, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    float[] accel = new float[3];
    float[] magnet = new float[3];

    float[] rotationMatrix = new float[16];

    float[] orientation = new float[3];

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accel = sensorEvent.values.clone();
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magnet = sensorEvent.values.clone();
        }
        SensorManager.getRotationMatrix(rotationMatrix, null, accel, magnet);
        SensorManager.getOrientation(rotationMatrix, orientation);

        xy.setText(String.valueOf(Math.round(Math.toDegrees(orientation[0]))));
        xz.setText(String.valueOf(Math.round(Math.toDegrees(orientation[1]))));
        zy.setText(String.valueOf(Math.round(Math.toDegrees(orientation[2]))));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}