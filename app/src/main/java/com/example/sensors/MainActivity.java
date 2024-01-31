package com.example.sensors;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorListener;
    private GestureDetector gestureDetector;

    private TextView XAxis, YAxis, ZAxis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XAxis = findViewById(R.id.XAxis);
        YAxis = findViewById(R.id.YAxis);
        ZAxis = findViewById(R.id.ZAxis);

        sensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float xAcc = sensorEvent.values[0];
                float yAcc = sensorEvent.values[1];
                float zAcc = sensorEvent.values[2];

                XAxis.setText("X: " + xAcc);
                YAxis.setText("Y: " + yAcc);
                ZAxis.setText("Z: " + zAcc);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor != null) {
            sensorManager.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        gestureDetector = new GestureDetector(this, new DoubleTapListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensor != null) {
            sensorManager.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private class DoubleTapListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Toast.makeText(MainActivity.this, "Double Tap Detected", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}

