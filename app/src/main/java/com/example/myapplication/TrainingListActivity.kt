package com.example.myapplication

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.CardAdapter
import com.example.myapplication.DataBase.AppDatabase
import com.example.myapplication.Entity.Training
import com.google.android.material.floatingactionbutton.FloatingActionButton


class TrainingListActivity : AppCompatActivity() {

    lateinit var add_b : FloatingActionButton
    lateinit var recycler : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_list)
        setTitle("Тренировки")

        MyAsyncTask().execute()

        add_b = findViewById(R.id.add_button)
        recycler =  findViewById(R.id.recycler_view)

        val i = Intent(this, CreateTrainingActivity::class.java)
        add_b.setOnClickListener{
            startActivity(i)
        }


        // обработка свапа вправо на удаление
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (recycler.adapter as CardAdapter).delete(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recycler)

    }


    inner  class MyAsyncTask() : AsyncTask<Void, Void, List<Training>?>(){

        override fun doInBackground(vararg params: Void): List<Training>? {
            val db = AppDatabase.getDatabase(this@TrainingListActivity.applicationContext)
            val list : List <Training>? = db?.TrainingDao()?.getAll()
            return list
        }

        override fun onPostExecute(l: List<Training>?) {
            super.onPreExecute()
            recycler.adapter = CardAdapter(l, this@TrainingListActivity.applicationContext)
            recycler.layoutManager = LinearLayoutManager(this@TrainingListActivity)
            recycler.setHasFixedSize(true)
        }
  }

    // кнопка истории тренировок в меню
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.activity_training_list_menu, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val j = Intent(this, HistoryActivity::class.java)
        startActivity(j)
        return super.onOptionsItemSelected(item)
    }


}

