package com.example.myapplication

import Adapter.CardHistoryAdapter
import Adapter.CardProcessAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity : AppCompatActivity() {

    lateinit var recycler : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setTitle("История тренировок")

        //подключение адаптера
        recycler =  findViewById(R.id.recycler_view)
        recycler.adapter = CardHistoryAdapter(this@HistoryActivity.applicationContext)
        recycler.layoutManager = LinearLayoutManager(this@HistoryActivity)
        recycler.setHasFixedSize(true)
    }
}