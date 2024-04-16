package org.app.listatareas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.app.listatareas.databinding.ItemTareaBinding

/*** TareaAdapter decide que datos se muestran y cómo ***/

// TareaAdapter hereda de RecyclerView
class TareaAdapter(
    private val tarea: MutableList<Tarea>,
    private val tareaSeleccionada: (Int) -> Unit,
    private val deleteTarea: (Int) -> Unit
) : RecyclerView.Adapter<TareaViewHolder>() {

    // Crea una instancia de TareaViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {

        // Infla la vista de tarea desde item_tarea
        val binding = ItemTareaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TareaViewHolder(binding)

    }

    // position es el parametro que da la posicion del rv con el que se interactua
    // Actualiza y ssocia los datos de una tarea especifíca a una vista de TareaViewHolder
    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {

        // Actualiza la vista con los datos de la tarea
        holder.render(tarea[position])

        // Indica que se ha manejado el evento de un clic
        holder.itemView.setOnClickListener { tareaSeleccionada(position) }

        // Indica que se ha manejado el evento de clic largo
        holder.itemView.setOnLongClickListener {
            deleteTarea(position)
            true
        }

    }

    // Número total de tareas de la lista tareas
    override fun getItemCount() = tarea.size

}

