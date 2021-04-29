package Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.*
import com.example.myapplication.DataBase.DBCallAdapter
import com.example.myapplication.DataBase.DBCallCreateTraining
import com.example.myapplication.Entity.Exercise


class CardProcessAdapter( val id: Int, val context: Context) : RecyclerView.Adapter<CardProcessAdapter.ViewHolder>() {

    val db_call_tr = DBCallCreateTraining(context)
    private var list : List<Exercise>? = db_call_tr.getAllExByTraining(id)
    private var color_pos: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_process, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = list?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentItem : Exercise? = null
        currentItem = list?.get(position)
        val db_call_a = DBCallAdapter(context)
        holder.textView.text = db_call_a.getExName(currentItem)
        val kol_appr = db_call_a.getApprKolFromEx(currentItem)
        holder.kol.text = kol_appr.toString()
        if (color_pos == position){
            holder.itemView.setBackgroundResource(R.color.light_blue)
        }
        else{
            holder.itemView.setBackgroundResource(R.color.light)
        }

        holder.itemView.setOnClickListener {
            val info: String? = currentItem?.let { it1 -> db_call_a?.getDescFromEx(it1) }
            val dialog = TrInfoDialogFragment().newInstance(info)
            val activity = holder.itemView.context as? TrainingProcessActivity
            val manager = activity?.supportFragmentManager
            if (manager != null) {
                dialog.show(manager, "info")
            }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textView: TextView = itemView.findViewById(R.id.tr_name)
        val kol: TextView = itemView.findViewById(R.id.kol_appr)
    }

    fun changeColorOnPosition(ex_num: Int){
        color_pos = ex_num
        notifyDataSetChanged()
    }

}