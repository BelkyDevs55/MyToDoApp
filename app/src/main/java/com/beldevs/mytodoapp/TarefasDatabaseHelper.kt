package com.beldevs.mytodoapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TarefasDatabaseHelper (context: Context): SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {

    companion object {
        private const val DATABASE_NAME = "tarefas.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "tarefas"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "titulo"
        private const val COLUMN_DESCRIPTION = "descricao"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_DESCRIPTION TEXT)"
        db?.execSQL(createTableQuery)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery =
            "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)

    }

    fun insertTarefa(tarefa: Tarefa) {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_TITLE, tarefa.titulo)
            put(COLUMN_DESCRIPTION, tarefa.descricao)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()

    }

    fun getAllTarefas(): List<Tarefa> {
        val listaTarefas = mutableListOf<Tarefa>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val descricao = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))

            val tarefa = Tarefa(id, titulo, descricao)
            listaTarefas.add(tarefa)

        }

        cursor.close()
        db.close()
        return listaTarefas
    }

    fun getIdTarefa(idTarefa: Int): Tarefa {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $idTarefa"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val descricao = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
        cursor.close() // Fechar o cursor
        db.close() // Fechar Base de dados
        return Tarefa(id, titulo, descricao)

    }


    fun updateTarefa(tarefa: Tarefa) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, tarefa.titulo)
            put(COLUMN_DESCRIPTION, tarefa.descricao)

        }

        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(tarefa.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()

    }

    fun deleteTarefa(idTarefa: Int){
        val db =writableDatabase
        val whereClaus = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(idTarefa.toString())
        db.delete(TABLE_NAME, whereClaus, whereArgs)
        db.close()
    }






}



