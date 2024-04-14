package org.app.listatareas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.app.listatareas.databinding.ItemTareaBinding

class TareaAdapter(
    private val tarea: List<Tarea>,
    private val tareaSeleccionada: (Int) -> Unit,
    private val deleteTarea: (Int) -> Unit
) : RecyclerView.Adapter<TareaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val binding = ItemTareaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TareaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        holder.render(tarea[position])
        holder.itemView.setOnClickListener { tareaSeleccionada(position) }

        holder.itemView.setOnLongClickListener {
            deleteTarea(position)
            true // Indica que se ha manejado el evento de clic largo
        }
    }

    override fun getItemCount() = tarea.size
}

