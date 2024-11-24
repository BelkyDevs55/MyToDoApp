package com.beldevs.mytodoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.beldevs.mytodoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: TarefasDatabaseHelper
    private lateinit var tarefasAdapter: TarefasAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Usando o binding gerada automaticamente
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TarefasDatabaseHelper(this)

        tarefasAdapter = TarefasAdaptador(db.getAllTarefas(), this)

        binding.tarefasRv.layoutManager = LinearLayoutManager(this)
        binding.tarefasRv.adapter = tarefasAdapter

        // Configurar o botón FAB para mostrar um Toast quando é presionado
        binding.FABAdicionarTarefa.setOnClickListener {
            startActivity(Intent(applicationContext, AdicionarTarefaActivity::class.java))

        }
    }

    override fun onResume(){
        super.onResume()
        tarefasAdapter.atualizarLista(db.getAllTarefas())
    }
}



