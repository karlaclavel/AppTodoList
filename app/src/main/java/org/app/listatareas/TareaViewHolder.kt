package org.app.listatareas

import android.graphics.Paint
import android.view.View
import android.widget.TextView
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import org.app.listatareas.databinding.ItemTareaBinding

// Proporciona las vistas individuales para cada uno de los elementos
class TareaViewHolder(private val binding: ItemTareaBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(tarea: Tarea) {
        // Aplica el tachado al texto si la tarea está seleccionada
        if (tarea.estaSeleccionado) {
            binding.tvTarea.paintFlags = binding.tvTarea.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            // Elimina el tachado si la tarea no está seleccionada
            binding.tvTarea.paintFlags = binding.tvTarea.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        // Establece el texto de la tarea
        binding.tvTarea.text = tarea.nombre

        // Establece el estado del checkbox
        binding.cbCheckbox.isChecked = tarea.estaSeleccionado
    }
}
