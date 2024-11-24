package com.beldevs.mytodoapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TarefasAdaptador (
    private var tarefas : List<Tarefa>,
    context : Context) : RecyclerView.Adapter<TarefasAdaptador.TarefaViewHolder>() {

        private val db: TarefasDatabaseHelper = TarefasDatabaseHelper(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tarefa, parent, false)
        return TarefaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tarefas.size
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = tarefas[position]
        holder.itemTitulo.text = tarefa.titulo
        holder.itemDescricao.text = tarefa.descricao

        /*Event para atualizar uma tarefa*/
        holder.ivAtualizar.setOnClickListener {
            val intent =
                Intent(holder.itemView.context, AtualizarTarefaActivity::class.java).apply {
                    putExtra("id_tarefa", tarefa.id)
                }
            holder.itemView.context.startActivity(intent)
            Toast.makeText(
                holder.itemView.context,
                "O id da tarefa selecionada é ${tarefa.id}",
                Toast.LENGTH_SHORT
            ).show()
        }

        /*Event para eliminar uma tarefa*/
        holder.ivEliminar.setOnClickListener{
            db.deleteTarefa(tarefa.id)
            atualizarLista(db.getAllTarefas())
            Toast.makeText(holder.itemView.context, "Tarefa excluida", Toast.LENGTH_SHORT).show()
        }
    }

    class TarefaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitulo: TextView = itemView.findViewById(R.id.item_titulo)
        val itemDescricao: TextView = itemView.findViewById(R.id.item_descricao)
        val ivAtualizar: ImageView = itemView.findViewById(R.id.ivAtualizar)
        val ivEliminar : ImageView = itemView.findViewById(R.id.ivEliminar)
    }

    fun atualizarLista(novaTarefa: List<Tarefa>) {
        tarefas = novaTarefa
        notifyDataSetChanged()
    }
}

