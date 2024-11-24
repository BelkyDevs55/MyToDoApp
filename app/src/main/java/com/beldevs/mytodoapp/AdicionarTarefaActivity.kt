package com.beldevs.mytodoapp

import android.content.Intent
import android.os.Bundle
import android.os.DropBoxManager
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.beldevs.mytodoapp.databinding.ActivityAdicionarTarefaBinding
import javax.crypto.Mac

class AdicionarTarefaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdicionarTarefaBinding
    private lateinit var db: TarefasDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdicionarTarefaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TarefasDatabaseHelper(this)

        binding.ivSalvarNota.setOnClickListener {
            val titulo = binding.etTitulo.text.toString()
            val descricao = binding.edDescricao.text.toString()

            if (!titulo.isEmpty() && !descricao.isEmpty()) {
                salvarTarefa(titulo, descricao)
            }else{
                Toast.makeText(applicationContext, "Preencha os campos", Toast.LENGTH_SHORT).show()
            }

        }
    }
            private fun salvarTarefa(titulo: String, descricao: String) {
                val tarefa = Tarefa(0, titulo, descricao)
                db.insertTarefa(tarefa)
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finishAffinity()
                Toast.makeText(applicationContext, "A tarefa foi adicionada", Toast.LENGTH_SHORT)
                    .show()
            }

        }


