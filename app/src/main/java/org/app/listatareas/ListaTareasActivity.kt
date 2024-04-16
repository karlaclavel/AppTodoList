package org.app.listatareas

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.app.listatareas.databinding.ActivityListaTareasBinding

class ListaTareasActivity : AppCompatActivity() {

    private val PREF_NAME = "TareasPrefs"
    private val PREF_TAREAS = "Tareas"

    private lateinit var binding: ActivityListaTareasBinding

    private lateinit var rvTarea: RecyclerView
    private lateinit var tareaAdapter: TareaAdapter
    private lateinit var fabAddTarea: FloatingActionButton

    private val tareas = mutableListOf(
        Tarea("Organizar carpeta de descargas"),
        Tarea("Leer documentación de Android Studio")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaTareasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cargarTareas() // Cargar tareas al iniciar

        initComponent()
        initUI()
        initListeners()

    }

    // Iniciar los escuchadores
    private fun initListeners() {
        fabAddTarea.setOnClickListener { dialogoTarea() }
    }

    // Iniciar los componentes
    private fun initComponent() {
        rvTarea = binding.rvTarea
        fabAddTarea = binding.aAdirTarea
    }

    // Iniciar la vista
    private fun initUI() {
        tareaAdapter = TareaAdapter(tareas, { position -> tareaSeleccionada(position) },
            { position -> deleteTarea(position) })
        rvTarea.layoutManager = LinearLayoutManager(this)
        // Se asigna el adaptador
        rvTarea.adapter = tareaAdapter

    }

    // Dialogo para agregar una nueva tarea
    private fun dialogoTarea() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_tarea)

        val btnTarea: Button = dialog.findViewById(R.id.btnTarea)
        val etTarea: EditText = dialog.findViewById(R.id.etTarea)

        btnTarea.setOnClickListener {
            val tareaText = etTarea.text.toString()
            if (tareaText.isNotEmpty()) {
                tareas.add(Tarea(tareaText))
                actualizarTareas()
                guardarTareas() // Guardar tareas después de agregar
                dialog.hide()
            } else {
                // Mostrar alerta
                Toast.makeText(this, "El campo de tarea está vacío", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }

   // Seleccionar tarea cambia el estado de seleccion de la tarea
    private fun tareaSeleccionada(position:Int) {
        tareas[position].estaSeleccionado = ! tareas[position].estaSeleccionado
        actualizarTareas()
        guardarTareas() // Guardar tareas después de agregar
    }

    // Actualizar tareas
    @SuppressLint("NotifyDataSetChanged")
    private fun actualizarTareas () {
        tareaAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteTarea(position: Int) {
        tareas.removeAt(position)
        tareaAdapter.notifyDataSetChanged()
        guardarTareas() // Guardar tareas después de agregar
    }

    private fun guardarTareas() {
        val prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val editor = prefs.edit()
        val tareaSet = HashSet<String>()

        for (tarea in tareas) {
            tareaSet.add(tarea.nombre)
        }

        editor.putStringSet(PREF_TAREAS, tareaSet)
        editor.apply()
    }

    private fun cargarTareas() {
        val prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val tareaSet = prefs.getStringSet(PREF_TAREAS, HashSet<String>()) ?: HashSet<String>()

        tareas.clear()

        for (tareaNombre in tareaSet) {
            tareas.add(Tarea(tareaNombre))
        }
    }


}