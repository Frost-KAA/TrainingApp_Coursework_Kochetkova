package com.example.myapplication

import Adapter.CardProcessAdapter
import DataBase.DBCallProcess
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.CardApprAdapter
import com.example.myapplication.DataBase.DBCall
import com.example.myapplication.DataBase.DBCallCreateTraining
import com.example.myapplication.Entity.Exercise
import java.util.*

class TrainingProcessActivity : AppCompatActivity() {

    lateinit var recycler : RecyclerView
    lateinit var chronometer: Chronometer
    lateinit var ok: ImageButton
    lateinit var weight: TextView
    lateinit var repeat: TextView
    lateinit var appr_name: TextView
    var tr_time : Int = 0
    var percent: Int = 0
    var current_filling: Int = 0
    var max_filling: Int? = null
    var id: Int? = null
    var day_id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_process)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        id = intent.extras?.get("id") as Int?
        val db_call = DBCall(this.applicationContext)
        ok = findViewById(R.id.ok)
        weight = findViewById(R.id.weight)
        repeat = findViewById(R.id.repeat)
        appr_name = findViewById(R.id.appr_number)

        var tr_name : String? = "Тренировка"
        tr_name = id?.let { db_call.getTrainingName(it) }
        setTitle(tr_name)

        //запуск секундомера
        chronometer = findViewById(R.id.simpleChronometer)
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()

        day_id = db_call.addDate()

        max_filling = id?.let { db_call.getMaxFilling(it) }


        val ex_list: List<Exercise>? = id?.let { db_call.getAllExByTraining(it) }
        var ex_num: Int = 0
        var appr_num: Int = 0
        var appr_list = db_call.getAllApprFromEx(ex_list?.get(ex_num)?.ID_Ex)

        fun setApprOnView(){
            val st: String = "Подход " + (appr_num+1).toString()
            appr_name.text = st
            weight.text = appr_list?.get(appr_num)?.weight.toString()
            repeat.text = appr_list?.get(appr_num)?.repeat.toString()
        }

        fun saveDateFromView(){
            val w : Int = weight.text.toString().toInt()
            val r : Int = repeat.text.toString().toInt()
            if (w != 0){
                current_filling += w*r
            } else{
                current_filling += r
            }

        }

        setApprOnView()

        //подключение адаптера
        recycler =  findViewById(R.id.recycler_view)
        recycler.adapter = id?.let { CardProcessAdapter(it, this@TrainingProcessActivity.applicationContext) }
        recycler.layoutManager = LinearLayoutManager(this@TrainingProcessActivity)
        recycler.setHasFixedSize(true)

        ok.setOnClickListener {
            if (appr_name.text == "Тренировка завершена"){
                endTraining()
            }
            if (appr_num+1 < appr_list?.size ?: 0) {
                saveDateFromView()
                appr_num += 1
                setApprOnView()
            } else if (ex_list != null) {
                if (ex_num + 1 < ex_list.size){
                    saveDateFromView()
                    appr_num = 0
                    ex_num += 1
                    appr_list = db_call.getAllApprFromEx(ex_list?.get(ex_num)?.ID_Ex)
                    setApprOnView()
                    (recycler.adapter as CardProcessAdapter).changeColorOnPosition(ex_num)
                }
                else{
                    val st: String = "Тренировка завершена"
                    appr_name.text = st
                    saveDateFromView()
                }
            }
        }
    }

    // кнопка сохранить в меню
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {
                endTraining()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun endTraining(){
        val elapsedMillis: Long = (SystemClock.elapsedRealtime() - chronometer.base)
        tr_time = (elapsedMillis / 1000 / 60).toInt()
        chronometer.stop()
        if (max_filling!= 0){
            percent = current_filling * 100 / max_filling!!
        }
        else{
            percent = 0
        }
        val db_call = DBCall(this.applicationContext)
        db_call.setEfficiency(id!!, day_id!!, tr_time, percent)
        val i = Intent(this, TrainingListActivity::class.java)
        startActivity(i)
    }
}