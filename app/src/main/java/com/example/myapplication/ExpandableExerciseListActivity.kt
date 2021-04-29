package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Adapter.CustomExpandableListAdapter
import com.example.myapplication.DataBase.DBCall
import com.example.myapplication.DataBase.DBCallCreateTraining
import com.example.myapplication.Entity.ExerciseList
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExpandableExerciseListActivity : AppCompatActivity() {

    internal var adapter: ExpandableListAdapter? = null
    lateinit var expandableListView: ExpandableListView
    lateinit var add_ex: FloatingActionButton
    var id : Int? = null


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandable_exercise_list)
        setTitle("Список упражнений")

        expandableListView = findViewById(R.id.elv)
        add_ex = findViewById(R.id.add_ex_list)

        id = intent.extras?.get("id") as Int

        //подключение адаптера
        adapter = CustomExpandableListAdapter(this, id!!)
        expandableListView.setAdapter(adapter)

        add_ex.setOnClickListener {
            val i = Intent(this, NewExerciseActivity::class.java)
            startActivity(i)
        }
    }

    // кнопка сохранить в меню
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val p = Intent(this, CreateTrainingActivity::class.java)
        p.putExtra("id", id)
        startActivity(p)
        return super.onOptionsItemSelected(item)
    }

}