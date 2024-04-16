package org.app.listatareas

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.app.listatareas.databinding.ActivityListaTareasBinding

class ListaTareasActivity : AppCompatActivity() {

    // Nombres de las preferencias compartidas
    private val PREF_NAME = "TareasPrefs"
    private val PREF_TAREAS = "Tareas"

    private lateinit var binding: ActivityListaTareasBinding

    private lateinit var rvTarea: RecyclerView
    private lateinit var tareaAdapter: TareaAdapter // Adaptador para el RecyclerView
    private lateinit var fabAddTarea: FloatingActionButton // Botón flotante

    private val tareas = mutableListOf(
        Tarea("Organizar carpeta de descargas"),
        Tarea("Leer documentación de Android Studio")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaTareasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            cargarTareas() // Cargar tareas al iniciar
            initComponent()
            initUI()
            initListeners()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al cargar la actividad", Toast.LENGTH_LONG).show()
        }

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
        tareaAdapter = TareaAdapter(tareas, { position -> tareaSeleccionada(position) }, { position -> deleteTarea(position) })
        rvTarea.layoutManager = LinearLayoutManager(this)
        // Se asigna el adaptador al RecyclerView
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
                guardarTareas()
                dialog.hide()
            } else {
                Toast.makeText(this, "El campo de tarea está vacío", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }

   // Seleccionar tarea cambia el estado de seleccion de la tarea
    private fun tareaSeleccionada(position:Int) {
        tareas[position].estaSeleccionado = ! tareas[position].estaSeleccionado
        actualizarTareas()
        guardarTareas()
    }

    // Actualizar tareas en el RecyclerView
    @SuppressLint("NotifyDataSetChanged")
    private fun actualizarTareas () {
        tareaAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteTarea(position: Int) {
        tareas.removeAt(position)
        tareaAdapter.notifyDataSetChanged()
        guardarTareas()
    }

    // Guarda las tareas en las preferencias compartidas
    private fun guardarTareas() {
        val prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val editor = prefs.edit()
        val tareaStrings = tareas.map { tarea -> "${tarea.nombre},${tarea.estaSeleccionado}" }
        editor.putStringSet(PREF_TAREAS, tareaStrings.toSet())
        editor.apply()
    }

    // Carga las tareas guardadas desde las preferencias comparticas y las guarda en la lista de tareas
    private fun cargarTareas() {
        val prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val tareaStrings = prefs.getStringSet(PREF_TAREAS, setOf()) ?: setOf()

        tareas.clear()

        for (tareaString in tareaStrings) {
            val partes = tareaString.split(",")
            if (partes.size == 2) {
                val nombre = partes[0]
                val estaSeleccionado = partes[1].toBoolean()
                tareas.add(Tarea(nombre, estaSeleccionado))
            }
        }
    }

}