package com.example.myapplication.DataBase

import android.content.Context
import android.util.Log
import com.example.myapplication.DataBase.AppDatabase
import com.example.myapplication.Entity.Approach
import com.example.myapplication.Entity.Exercise
import com.example.myapplication.Entity.ExerciseList
import com.example.myapplication.Entity.Training

class DBCallCreateTraining(context: Context) {
    private val context: Context = context
    private var db: AppDatabase? = null

    //создание пустой тренировки по умолчанию
    public fun createEmptyTraining() : Int {
        var id: Int = 0
        val t = Thread{
            db = AppDatabase.getDatabase(context);
            val tr: Training? = Training(null, "Новая")
            if (tr != null) {
                db?.TrainingDao()?.insert(tr)
            }
            id = db?.TrainingDao()?.getAll()?.last()?.ID_Training!!
        }
        t.start()
        t.join();
        return id
    }

    //получение имени тренировки
    public fun getTrainingName(id:Int) : String? {
        var st: String ?= null
        val t = Thread{
            db = AppDatabase.getDatabase(context);
            val tr: Training? = db?.TrainingDao()?.getById(id)
            if (tr != null) {
                st = tr.name
            }
        }
        t.start()
        t.join()
        return st
    }

    public fun getExName(id:Int) : String? {
        var st: String ?= null
        val t = Thread{
            db = AppDatabase.getDatabase(context);
            val ex: Exercise? = db?.ExerciseDao()?.getById(id)
            if (ex != null) {
                val exlist = db?.ExerciseListDao()?.getById(ex.ExList_ID)
                if (exlist != null) {
                    st = exlist.name
                }
            }
        }
        t.start()
        t.join()
        return st
    }

    //изменение имени тренировки
    public fun changeTrainingName(id:Int, name:String)  {
        val t = Thread{
            db = AppDatabase.getDatabase(context);
            var tr: Training? = db?.TrainingDao()?.getById(id)
            if (tr != null) {
                tr.name = name
            }
            if (tr != null) {
                db?.TrainingDao()?.update(tr)
            }
        }
        t.start()
    }

    //получение списка упражнений тренировки
    public fun getAllExByTraining(id: Int) : List<Exercise>?{
        var list: List<Exercise>? = null
        val t = Thread{
            db = AppDatabase.getDatabase(context);
            list = db?.ExerciseDao()?.getInTrainingByID(id)
        }
        t.start()
        t.join()
        return list
    }

    //добавление упражнений в тренировку по спику id ExerciseList
    public fun addExToTraining(ex_id_list:ArrayList<Int>?, id: Int?){
        val t = Thread{
            db = AppDatabase.getDatabase(context);
            if (ex_id_list != null) {
                for (ex_id in ex_id_list){
                    Log.d("In In In", ex_id.toString())
                    //проверяем, было ли такое упражнение в тренировке
                    var ex = id?.let { db?.ExerciseDao()?.getByExListAndTrainingId(ex_id, it) }
                    if (ex == null){
                        ex = id?.let { Exercise(null, it, ex_id) }
                        if (ex != null) {
                            db?.ExerciseDao()?.insert(ex)
                        }
                    }
                }
            }
        }
        t.start()
        t.join()
    }

    //получение списка упражнений тренировки
    public fun getAllExListByTraining(id: Int) : List<ExerciseList>{
        val list = ArrayList<ExerciseList>()
        val t = Thread{
            db = AppDatabase.getDatabase(context);
            val exlist = db?.ExerciseDao()?.getInTrainingByID(id)
            if (exlist != null) {
                for (ex in exlist){
                    val exl = db?.ExerciseListDao()?.getById(ex.ExList_ID)
                    if (exl != null) {
                        list.add(exl)
                    }
                }
            }
        }
        t.start()
        t.join()
        return list as List<ExerciseList>
    }

    public fun addApprToEx(list:ArrayList<Pair<Int?, Int?>>, ex_id:Int?){
        val t = Thread {
            db = AppDatabase.getDatabase(context);
            val list_for_del = ex_id?.let { db?.ApproachDao()?.getInExByID(it) }
            if (list_for_del != null) {
                for (del in list_for_del){
                    db?.ApproachDao()?.delete(del)
                }
            }
            for (el in list){
                val appr = ex_id?.let { Approach(null, it, el.second, el.first) }
                if (appr != null) {
                    db?.ApproachDao()?.insert(appr)
                }
            }
        }
        t.start()
    }

    public fun getAllApprFromEx(ex_id:Int?): ArrayList<Pair<Int?, Int?>>{
        val list = ArrayList<Pair<Int?, Int?>>()
        val t = Thread {
            db = AppDatabase.getDatabase(context);
            val appr_list = ex_id?.let { db?.ApproachDao()?.getInExByID(it) }
            if (appr_list != null) {
                for (appr in appr_list){
                    list.add(Pair(appr.weight, appr.repeat))
                }
            }
        }
        t.start()
        t.join()
        return list
    }
}