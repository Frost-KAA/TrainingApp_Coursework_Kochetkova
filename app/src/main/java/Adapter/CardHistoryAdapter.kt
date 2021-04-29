package Adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataBase.DBCall
import com.example.myapplication.DataBase.DBCallAdapter
import com.example.myapplication.Entity.Efficiency
import com.example.myapplication.R


class CardHistoryAdapter (val context: Context) : RecyclerView.Adapter<CardHistoryAdapter.ViewHolder>() {

    val db_call = DBCall(context)
    private var list : List<Efficiency>? = db_call.getAllEffency()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_history, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = list?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentItem : Efficiency? = null
        currentItem = list?.get(position)

        holder.name.text = db_call.getTrNameByEff(currentItem)
        holder.date.text = db_call.getDateByEff(currentItem)
        holder.time.text = currentItem?.Time.toString() + " минут"
        val st: String = currentItem?.Percenr.toString() + "%"
        holder.percent.text = st


    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.tr_name)
        val date: TextView = itemView.findViewById(R.id.dat)
        val time: TextView = itemView.findViewById(R.id.time)
        val percent : TextView = itemView.findViewById(R.id.percent)
    }


}