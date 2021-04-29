package com.example.myapplication.Adapter

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.*
import com.example.myapplication.DataBase.DBCall
import com.example.myapplication.Entity.Training

class CardAdapter(private var list: List<Training>?, val context: Context) : RecyclerView.Adapter<CardAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = list?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentItem : Training? = null
        if (list != null) {
            currentItem = list!![position]
            holder.textView.text = currentItem.name
        }

        val db_call = DBCall(context)

        val i = Intent(context, CreateTrainingActivity::class.java)
        i.addFlags(FLAG_ACTIVITY_NEW_TASK);
        holder.edit.setOnClickListener{
            if (currentItem != null) {
                i.putExtra("id", currentItem.ID_Training)
                Log.d("ID Adapter", currentItem.ID_Training.toString())
            }
            context.startActivity(i)
        }

        val j = Intent(context, TrainingProcessActivity::class.java)
        j.addFlags(FLAG_ACTIVITY_NEW_TASK);
        holder.itemView.setOnClickListener{
            if (currentItem != null) {
                j.putExtra("id", currentItem.ID_Training)
            }
            val no_appr : Boolean = db_call.isThereNoAppr(currentItem?.ID_Training!!)
            Log.d("No_appr", no_appr.toString())
            if (no_appr){
                //вызываем диалоговое окно
                val dialog = WarningDialogFragment()
                val activity = holder.itemView.context as? TrainingListActivity
                val manager = activity?.supportFragmentManager
                if (manager != null) {
                    dialog.show(manager, "warning")
                }
            } else{
                context.startActivity(j)
            }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textView: TextView = itemView.findViewById(R.id.tr_name)
        val edit: ImageButton = itemView.findViewById(R.id.add_appr_button)

    }

    fun delete(position: Int){
        var currentItem : Training? = null
        if (list != null) {
            currentItem = list!![position]
        }
        val db_call = DBCall(context)
        list = db_call.deleteTraining(currentItem)
        notifyDataSetChanged()
    }
}