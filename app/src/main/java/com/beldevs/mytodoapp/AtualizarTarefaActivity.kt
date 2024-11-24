package com.beldevs.mytodoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.beldevs.mytodoapp.databinding.ActivityAtualizarTarefaBinding

class AtualizarTarefaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAtualizarTarefaBinding
    private lateinit var db: TarefasDatabaseHelper
    private var idTarefa : Int =-1

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityAtualizarTarefaBinding.inflate(layoutInflater)
            setContentView(binding.root)

            db = TarefasDatabaseHelper(this)

            idTarefa = intent.getIntExtra("id_tarefa", -1)
            if (idTarefa == -1) {
                finish()
                return

            }

             val tarefa = db.getIdTarefa(idTarefa)
             binding.etTitulo.setText(tarefa.titulo)
             binding.edDescricao.setText(tarefa.descricao)

             binding.ivAtualizarTarefa.setOnClickListener{
                   val tituloNovo = binding.etTitulo.text.toString()
                   val  descricaoNova = binding.edDescricao.text.toString()

              val tarefaAtualizada = Tarefa(idTarefa, tituloNovo, descricaoNova)
              db.updateTarefa(tarefaAtualizada)
              startActivity(Intent( this, MainActivity::class.java ))
              finish()
              Toast.makeText(this, "A tarefa foi atualizada com sucesso", Toast.LENGTH_SHORT).show()


            }

        }

}