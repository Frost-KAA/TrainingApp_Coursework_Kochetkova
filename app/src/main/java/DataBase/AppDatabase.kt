package com.example.myapplication.DataBase
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.Dao.*
import com.example.myapplication.Entity.*

@Database (entities = [Approach::class , Exercise::class, ExDate::class,
                    ExerciseList::class, Muscle::class,
                    Training::class, Efficiency::class ], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ApproachDao() : ApproachDao
    abstract fun ExerciseDao(): ExerciseDao
    abstract fun ExerciseListDao(): ExerciseListDao
    abstract fun TrainingDao(): TrainingDao
    abstract fun MuscleDao(): MuscleDao
    abstract fun ExDateDao(): ExDateDao
    abstract fun EfficiencyDao(): EfficiencyDao

    companion object{
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase? {
            if (instance == null){
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "training_db.db").createFromAsset("my_training.db").build()
                val db = getDatabase(context)
//                val m1: Muscle = Muscle(1, "Мышцы шеи")
//                val m2: Muscle = Muscle(2, "Мышцы рук")
//                val m3: Muscle = Muscle(3, "Мышцы плечевого пояса")
//                val m4: Muscle = Muscle(4, "Мышцы спины")
//                val m5: Muscle = Muscle(5, "Мышцы груди")
//                val m6: Muscle = Muscle(6, "Мышцы живота")
//                val m7: Muscle = Muscle(7, "Мышцы ног")
//                val m8: Muscle = Muscle(8, "Мышцы ягодиц")
//                val m9: Muscle = Muscle(9, "Остальное")
//
//                db?.MuscleDao()?.insert(m1)
//                db?.MuscleDao()?.insert(m2)
//                db?.MuscleDao()?.insert(m3)
//                db?.MuscleDao()?.insert(m4)
//                db?.MuscleDao()?.insert(m5)
//                db?.MuscleDao()?.insert(m6)
//                db?.MuscleDao()?.insert(m7)
//                db?.MuscleDao()?.insert(m8)
//                db?.MuscleDao()?.insert(m9)
//
//                val e1: ExerciseList = ExerciseList(1, 1, "Подъемы головы, лежа спиной на скамье", "")
//                val e2: ExerciseList = ExerciseList(2, 2, "Армейский жим", "Подъемы рук вверх с гантелями")
//                val e3: ExerciseList = ExerciseList(3, 2, "Сгибание рук с грифом штанги", "Сгибание руки по уровня груди и полное выпремление руки")
//                val e4: ExerciseList = ExerciseList(4, 2, "Разгибание рук с гантелями лежа", "Руки находятся на уровне груди, опускаются до уровня скамьи")
//                val e5: ExerciseList = ExerciseList(5, 3, "Подъёмы гантелей в стороны в наклоне", "Руки немного согруты")
//                val e6: ExerciseList = ExerciseList(6, 3, "Жим гантелей сидя", "Спина прямая")
//                val e7: ExerciseList = ExerciseList(7, 4, "Тяга Т-образного грифа", "Лопаки сводятся")
//                val e8: ExerciseList = ExerciseList(8, 4, "Становая тяга со штангой", "Пресс напряжён")
//                val e9: ExerciseList = ExerciseList(9, 4, "Вертикальная тяга", "До сведения лопаток")
//                val e10: ExerciseList = ExerciseList(10, 5, "Разведение гантелей, лежа на наклонной скамье", "Руки опускаютя до уроня скамьи")
//                val e11: ExerciseList = ExerciseList(11, 5, "Жим штанги на горизонтальной скамье", "Широкий хват")
//                val e12: ExerciseList = ExerciseList(12, 5, "Отжимания от пола", "Узкий хват")
//                val e13: ExerciseList = ExerciseList(13, 6, "Подъемы туловища на наклонной скамье", "По подъёме выдох")
//                val e14: ExerciseList = ExerciseList(14, 6, "Боковые наклоны стоя", "С большим мячом")
//                val e15: ExerciseList = ExerciseList(15, 6, "Подъемы ног на наклонной скамье", "Ноги прямые")
//                val e16: ExerciseList = ExerciseList(16, 7, "Приседания со штангой в Смите", "")
//                val e17: ExerciseList = ExerciseList(17, 7, "Приседания со свободной штангой ", "")
//                val e18: ExerciseList = ExerciseList(18, 7, "Сгибания ног лёжа", "На заднюю поверхность")
//                val e19: ExerciseList = ExerciseList(19, 7, "Разгибание ног сидя", "На квадрицепс")
//                val e20: ExerciseList = ExerciseList(20, 7, "Сведение ног сидя", "На внутреннюю поверхность бедра")
//                val e21: ExerciseList = ExerciseList(21, 8, "Выпады со свободной штангой", "")
//                val e22: ExerciseList = ExerciseList(22, 8, "Выпады со штангой в Смите", "")
//                val e23: ExerciseList = ExerciseList(23, 8, "Проходка выпадов", "Штанка на плечи или гантели в руках")
//                val e24: ExerciseList = ExerciseList(24, 8, "Ягодичный мост", "В Смите")
//                val e25: ExerciseList = ExerciseList(25, 8, "Махи ногой", "")
//                val e26: ExerciseList = ExerciseList(26, 8, "Разведение ног сидя", "Корпус наклонен вперёд")
//
//                db?.ExerciseListDao()?.insert(e1)
//                db?.ExerciseListDao()?.insert(e2)
//                db?.ExerciseListDao()?.insert(e3)
//                db?.ExerciseListDao()?.insert(e4)
//                db?.ExerciseListDao()?.insert(e5)
//                db?.ExerciseListDao()?.insert(e6)
//                db?.ExerciseListDao()?.insert(e7)
//                db?.ExerciseListDao()?.insert(e8)
//                db?.ExerciseListDao()?.insert(e9)
//                db?.ExerciseListDao()?.insert(e10)
//                db?.ExerciseListDao()?.insert(e11)
//                db?.ExerciseListDao()?.insert(e12)
//                db?.ExerciseListDao()?.insert(e13)
//                db?.ExerciseListDao()?.insert(e14)
//                db?.ExerciseListDao()?.insert(e15)
//                db?.ExerciseListDao()?.insert(e16)
//                db?.ExerciseListDao()?.insert(e17)
//                db?.ExerciseListDao()?.insert(e18)
//                db?.ExerciseListDao()?.insert(e19)
//                db?.ExerciseListDao()?.insert(e20)
//                db?.ExerciseListDao()?.insert(e21)
//                db?.ExerciseListDao()?.insert(e22)
//                db?.ExerciseListDao()?.insert(e23)
//                db?.ExerciseListDao()?.insert(e24)
//                db?.ExerciseListDao()?.insert(e25)
//                db?.ExerciseListDao()?.insert(e26)
//
//                val t = Training(1, "Силовая на ноги")
//                db?.TrainingDao()?.insert(t)

            }
            return instance
        }
    }
}