package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskItemAdapter(val ListOfItems:List<String>,val longClickListener: OnLongClickListener,val clickListener:OnClickListener) :RecyclerView.Adapter<TaskItemAdapter.ViewHolder>(){
    interface OnLongClickListener{
        fun onItemLongClicked(position: Int)
    }
    interface OnClickListener{
        fun onItemClicked(position: Int)
    }
    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView: TextView
        init {
            textView = itemView.findViewById(android.R.id.text1)
            itemView.setOnLongClickListener{
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
            itemView.setOnClickListener {
                clickListener.onItemClicked(adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1,parent,false)
        return ViewHolder(contactView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = ListOfItems[position]
        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return ListOfItems.size
    }
}