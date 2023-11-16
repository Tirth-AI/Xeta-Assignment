package com.example.xetaassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xetaassignment.R
import com.example.xetaassignment.model.category

class categoryAdapter (private val categoryList: ArrayList<category>) : RecyclerView.Adapter<categoryAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.categories,
            parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentCategory = categoryList[position]
        holder.planName.text = currentCategory.categoryName
        holder.difficultyLevel.text = currentCategory.numberOfExercise
    }


    override fun getItemCount(): Int {
        return categoryList.size
    }



    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val planName: TextView = itemView.findViewById(R.id.tv_categoryname)
        val difficultyLevel: TextView = itemView.findViewById(R.id.tv_exerciseCount)
    }
}