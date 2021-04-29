package com.example.myapplication.DataBase

import android.content.Context
import com.example.myapplication.DataBase.AppDatabase
import com.example.myapplication.Entity.Efficiency
import com.example.myapplication.Entity.Exercise
import com.example.myapplication.Entity.ExerciseList
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask

class DBCallAdapter(context: Context) {

    private val context: Context = context
    private var db: AppDatabase? = null

    //получение имени упражнения
    public fun getExName(ex: Exercise?) : String? {
        var name:String? = null
        val r = Callable<String> {
            db = AppDatabase.getDatabase(context);
            val trl : ExerciseList? = ex?.ExList_ID?.let { db?.ExerciseListDao()?.getById(it) }
            if (trl != null) {
                name = trl.name
            }
            return@Callable name
        }
        val task = FutureTask<String>(r)
        Executors.newSingleThreadExecutor().execute(task)
        while (!task.isDone()){}
        return name
    }

    public fun deleteExFromTraining(ex: Exercise?){
        val t = Thread{
            db = AppDatabase.getDatabase(context);
            if (ex != null) {
                db?.ExerciseDao()?.delete(ex)
            }
        }
        t.start()
    }

    //получение количества подходов упражнения
    public fun getApprKolFromEx(currentItem: Exercise?) : Int? {
        var kol:Int? = null
        val r = Callable<Int> {
            db = AppDatabase.getDatabase(context);
            val list_appr = currentItem?.ID_Ex?.let { db?.ApproachDao()?.getInExByID(it) }
            if (list_appr != null) {
                kol = list_appr.size
            }
            return@Callable kol
        }
        val task = FutureTask<Int>(r)
        Executors.newSingleThreadExecutor().execute(task)
        while (!task.isDone()){}
        return kol
    }

    //получение списка эффективности всех тренировок
    public fun getAllEffency() : List<Efficiency>? {
        var list: List<Efficiency>? = null
        val r = Callable<List<Efficiency>?> {
            db = AppDatabase.getDatabase(context);
            list = db?.EfficiencyDao()?.getAll()
            return@Callable list
        }
        val task = FutureTask<List<Efficiency>?>(r)
        Executors.newSingleThreadExecutor().execute(task)
        while (!task.isDone()){}
        return list
    }

    //получение названия тренировки по её эффективности
    public fun getTrNameByEff(eff: Efficiency?) : String? {
        var name: String? = null
        val r = Callable<String? > {
            db = AppDatabase.getDatabase(context);
            val tr = eff?.Training_ID?.let { db?.TrainingDao()?.getById(it) }
            if (tr != null) {
                name = tr.name
            }
            return@Callable name
        }
        val task = FutureTask<String?>(r)
        Executors.newSingleThreadExecutor().execute(task)
        while (!task.isDone()){}
        return name
    }

    public fun getDateByEff(eff: Efficiency?) : String? {
        var date: String? = null
        val r = Callable<String? > {
            db = AppDatabase.getDatabase(context);
            val d = eff?.Date_ID?.let { db?.ExDateDao()?.getById(it) }
            var m: String ?= null
            if (d?.month!! < 10) m ="0" + (d?.month?.plus(1)).toString()
            date = d?.day.toString() + "." + m + "." + d?.year.toString()
            return@Callable date
        }
        val task = FutureTask<String?>(r)
        Executors.newSingleThreadExecutor().execute(task)
        while (!task.isDone()){}
        return date
    }


    public fun getDescFromEx(ex: Exercise): String?{
        var desc : String? = null
        val t = Thread {
            db = AppDatabase.getDatabase(context);
            val exlist = db?.ExerciseListDao()?.getById(ex.ExList_ID)
            if (exlist != null) {
                desc = exlist.desc
            }
        }
        t.start()
        t.join()
        return desc
    }



}