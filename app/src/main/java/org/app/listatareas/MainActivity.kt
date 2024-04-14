package org.app.listatareas

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.app.listatareas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val progressHandler = Handler(Looper.getMainLooper())
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startProgress()

    }

    // startProgress() se define como una función de nivel superior
    private fun startProgress() {
        val totalTime = 3000L // Duración total en milisegundos
        val updateInterval = 50L // Intervalo de actualización en milisegundos
        val totalSteps = totalTime / updateInterval // Total de pasos
        val progressPerStep = 100f / totalSteps // Tiempo del progreso

        var currentProgress = 0f

        //
        progressHandler.postDelayed(object : Runnable {
            override fun run() {
                if (currentProgress >= 100) {
                    // Cuando la barra de progreso llega al 100%, inicia la nueva actividad
                    startActivity(Intent(this@MainActivity, ListaTareasActivity::class.java))
                    finish()
                } else {
                    // Actualiza la barra de progreso
                    currentProgress += progressPerStep
                    binding.progressBar.progress = currentProgress.toInt()
                    progressHandler.postDelayed(this, updateInterval)
                }
            }
        }, updateInterval)
    }
}
