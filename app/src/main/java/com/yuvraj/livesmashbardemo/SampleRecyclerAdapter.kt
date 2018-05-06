package com.yuvraj.livesmashbardemo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class SampleRecyclerAdapter(private val context: Context, private val list: List<String>,
                            private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<SampleRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler_list, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.textView?.text = item;
        holder.bind(item, onItemClickListener)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView? = null;

        fun bind(model: String, listener: OnItemClickListener) {
            itemView.setOnClickListener { listener.onItemClick(layoutPosition) }
        }

        init {
            textView = itemView.findViewById(R.id.text_title)
        }
    }

    companion object {
        private val TAG = SampleRecyclerAdapter::class.java.simpleName
    }
}