package com.example.myapplication.Adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R


class CardApprAdapter(private var list: ArrayList<Pair<Int?, Int?>>?, val context: Context) : RecyclerView.Adapter<CardApprAdapter.ViewHolder>() {

    private var my_weight: Int? = null
    private var my_repeat: Int? = null
    lateinit var one_holder : ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.approach_card, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = list?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) one_holder = holder
        var currentItem: Pair<Int?, Int?>? = null
        currentItem = list?.get(position)
        val st: String = "Подход " + (position+1).toString()
        holder.number.text = st

        my_repeat = null
        my_weight = null

        // для веса
        if (holder.weight.text.length != 0){
            my_weight = holder.weight.text.toString().toInt()
            Log.d("One", "one")
        }
        else if (currentItem != null && currentItem.first != null){
            (holder.weight as TextView).text = currentItem.first.toString()
            my_weight = currentItem.first
            Log.d("Two", "one")
        }
        else{
            if (list?.get(0)?.first == null) (holder.weight as TextView).text = ""
            else (holder.weight as TextView).text = list?.get(0)?.first.toString()
            //holder.weight.text = list?.get(0)?.first.toString()
            Log.d("Three", "one")
        }

        // для повторений
        if (holder.repeat.text.length != 0){
            my_repeat = holder.repeat.text.toString().toInt()

        }
        else if (currentItem != null && currentItem.second != null){
            holder.repeat.text = currentItem.second.toString()
            my_repeat = currentItem.second
        }
        else{
            if (list?.get(0)?.second == null) holder.repeat.text = ""
            else holder.repeat.text = list?.get(0)?.second.toString()
        }
        list?.set(position, Pair(my_weight, my_repeat))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val number: TextView = itemView.findViewById(R.id.appr_number)
        val weight: EditText = itemView.findViewById(R.id.weight)
        val repeat: TextView = itemView.findViewById(R.id.repeat)
    }

    fun getNewList(): ArrayList<Pair<Int?, Int?>>? {
        notifyItemChanged(0)
        if (list?.size == 1){
            list?.set(0, Pair(one_holder.weight.text.toString().toInt(), one_holder.repeat.text.toString().toInt()))
        }
        return list
    }

    fun addNewApp(){
        //val newItem: Pair<Int?, Int?>? = list?.get(0)
        val newItem: Pair<Int?, Int?>? = Pair(null, null)
        if (newItem != null) {
            list?.add(newItem)
        }
        Log.d("Add 1", list?.get(0)?.first.toString() )
        Log.d("Add 2", list?.get(0)?.second.toString() )
        notifyDataSetChanged()
    }

    fun delApp(){
        list?.remove(list!!.last())
        notifyDataSetChanged()
    }
}