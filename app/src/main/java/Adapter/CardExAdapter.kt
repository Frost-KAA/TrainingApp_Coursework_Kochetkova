package com.example.myapplication.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ApproachListActivity
import com.example.myapplication.DataBase.DBCall
import com.example.myapplication.DataBase.DBCallAdapter
import com.example.myapplication.DataBase.DBCallCreateTraining
import com.example.myapplication.Entity.Exercise
import com.example.myapplication.Entity.Training
import com.example.myapplication.R

class CardExAdapter( val id: Int, val context: Context) : RecyclerView.Adapter<CardExAdapter.ViewHolder>() {

    val db_call = DBCall(context)
    private var list : List<Exercise>? = db_call.getAllExByTraining(id)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_ex, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = list?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentItem : Exercise? = null
        currentItem = list?.get(position)
        holder.textView.text = db_call.getExName(currentItem)

        val i = Intent(context, ApproachListActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        holder.textView.setOnClickListener{
            i.putExtra("id", id)
            if (currentItem != null) {
                i.putExtra("ex_id", currentItem.ID_Ex)
            }
            context.startActivity(i)
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textView: TextView = itemView.findViewById(R.id.tr_name)
    }

    fun delete(position: Int){
        var currentItem : Exercise? = null
        if (list != null) {
            currentItem = list!![position]
        }
        val db_call_a = DBCallAdapter(context)
        db_call_a.deleteExFromTraining(currentItem)
        list = db_call.getAllExByTraining(id)
        notifyDataSetChanged()
    }
}