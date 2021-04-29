package DataBase

import android.content.Context
import android.util.Log
import com.example.myapplication.DataBase.AppDatabase
import com.example.myapplication.Entity.Efficiency
import com.example.myapplication.Entity.ExDate
import com.example.myapplication.Entity.Training
import java.util.*

class DBCallProcess(context: Context) {

    private val context: Context = context
    private var db: AppDatabase? = null

    //заполнение даты в базе
    public fun addDate(): Int{
        var id: Int = 0
        val calendar: Calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val t = Thread{
            db = AppDatabase.getDatabase(context);
            var today = db?.ExDateDao()?.getByDate(day, month, year)
            if (today == null){
                today = ExDate(null, day, month, year)
                db?.ExDateDao()?.insert(today)
                today = db?.ExDateDao()?.getAll()?.last()
            }
            if (today != null) {
                id = today.ID_Date!!
            }
        }
        t.start()
        t.join()
        return id
    }

    public fun setEfficiency(tr_id: Int, date_id: Int, time: Int, per: Int){
        val t = Thread{
            db = AppDatabase.getDatabase(context);
            var eff = Efficiency(null, tr_id, date_id, time, per)
            db?.EfficiencyDao()?.insert(eff)
        }
        t.start()
    }

    public fun getMaxFilling(tr_id: Int): Int{
        var kol : Int = 0
        val t = Thread{
            db = AppDatabase.getDatabase(context);
            val ex_list = db?.ExerciseDao()?.getInTrainingByID(tr_id)
            if (ex_list != null) {
                for (ex in ex_list){
                    val id = ex.ID_Ex
                    val appr_list = id?.let { db?.ApproachDao()?.getInExByID(it) }
                    if (appr_list != null) {
                        for (appr in appr_list){
                            if (appr.weight != 0){
                                kol = kol + appr.repeat!! * appr.weight!!
                            } else{
                                kol += appr.repeat!!
                            }
                        }
                    }
                }
            }

        }
        t.start()
        t.join()
        return kol
    }

}