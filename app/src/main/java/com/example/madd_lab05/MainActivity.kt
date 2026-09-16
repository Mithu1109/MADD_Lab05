package com.example.madd_lab05

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.madd_lab05.R

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var ivImage: ImageView

    // Variables to track image position
    private var posX = 0f
    private var posY = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivImage = findViewById(R.id.ivImage)

        // Initialize SensorManager and Accelerometer
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        // Register the sensor listener to start receiving data
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onPause() {
        super.onPause()
        // Unregister the listener to stop tracking and save battery life
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            // event.values[0]: acceleration along the X axis (tilt left/right)
            // event.values[1]: acceleration along the Y axis (tilt forward/backward)

            val deltaX = -event.values[0] // Negative to match natural tilt direction
            val deltaY = event.values[1]

            // Update positions (multiply by a factor to control speed)
            posX += deltaX * 2
            posY += deltaY * 2

            // Apply translation to move the ImageView smoothly
            ivImage.translationX = posX
            ivImage.translationY = posY
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Required method for SensorEventListener, can remain empty for this lab
    }
}