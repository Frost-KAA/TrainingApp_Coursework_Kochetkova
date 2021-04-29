package com.example.myapplication.DataBase

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.myapplication.Entity.*
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask
import kotlin.collections.ArrayList

class DBCall (context: Context) {

    private val context: Context = context
    private var db: AppDatabase? = null

    public fun add() {
        val t = Thread{
            db = AppDatabase.getDatabase(context);
            val ex = Exercise(null, 1, 1)
            val d = ExDate(null, 1, 4, 2020)
            db?.ExDateDao()?.insert(d)
            db?.ExerciseDao()?.insert(ex)
        }
        t.start()
        t.join()
    }

    // удаление тренировки из списка
    public fun deleteTraining(tr: Training?) : List<Training>? {
        var list : List<Training>? = null
        val t = Thread{
                    db = AppDatabase.getDatabase(context);
                    if (tr != null) {
                        db?.TrainingDao()?.delete(tr)
                        list = db?.TrainingDao()?.getAll()
                    }
        }
        t.start()
        t.join()
        return list
    }


    // получение списка мышц
    public fun getMuscle() : List<Muscle>? {
        var list : List<Muscle>? = null
        val r = Callable<List<Muscle>> {
            db = AppDatabase.getDatabase(context);
            list =  db?.MuscleDao()?.getAll()
            return@Callable list
        }
        val task = FutureTask<List<Muscle>>(r)
        Executors.newSingleThreadExecutor().execute(task)
        while (!task.isDone){}
        return list
    }


    //получение списка id по списку упражнений
    public fun getIdsExList(list: List<ExerciseList>) : ArrayList<Int>? {
        var ids = ArrayList<Int>()
        val r = Callable<ArrayList<Int>> {
            db = AppDatabase.getDatabase(context);
            for (ex in list){
                ex.ID_ExList?.let { ids.add(it) }
            }
            return@Callable ids
        }
        val task = FutureTask<ArrayList<Int>>(r)
        Executors.newSingleThreadExecutor().execute(task)
        while (!task.isDone){}
        return ids
    }


    // получение полного списка упражнений с мышцами для адаптера
    public fun getListData() : List<List<ExerciseList>>{
        val listData = ArrayList<List<ExerciseList>>()
        val r = Callable<List<List<ExerciseList>>>() {
            db = AppDatabase.getDatabase(context);
            val muscle: List<Muscle>? = db?.MuscleDao()?.getAll()
            if (muscle != null) {
                for (mus in muscle) {
                    var ex: List<ExerciseList>? = null
                    if (mus.ID_Muscle != null) {
                        ex = db?.ExerciseListDao()?.getInMuscle(mus.ID_Muscle!!)
                    }
                    if (ex != null) listData.add(ex)
                }
            }
            return@Callable listData
        }
        val task = FutureTask<List<List<ExerciseList>>>(r)
        Executors.newSingleThreadExecutor().execute(task)
        while (!task.isDone){}
        return listData
    }

    public fun isThereNoAppr(tr_id: Int):Boolean {
        var f: Boolean = false
        val t = Thread{
            db = AppDatabase.getDatabase(context);
            val ex_list = db?.ExerciseDao()?.getInTrainingByID(tr_id)
            if (ex_list != null && ex_list.size != 0) {
                Log.d("Ex", "Ex_list")
                for (ex in ex_list){
                    val appr_list = ex.ID_Ex?.let { db?.ApproachDao()?.getInExByID(it) }
                    if (appr_list == null || appr_list.size == 0){
                        f = true
                        break
                    }
                }
            } else{
                f = true
            }
        }
        t.start()
        t.join()
        return  f
    }

    public fun addNewExList(name: String, desc: String?){
        val t = Thread{
            db = AppDatabase.getDatabase(context);
            val ex = ExerciseList(null, 9, name, desc)
            db?.ExerciseListDao()?.insert(ex)
        }
        t.start()
    }

}