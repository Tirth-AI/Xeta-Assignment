package com.example.xetaassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xetaassignment.R
import com.example.xetaassignment.model.workoutPlan

class workoutPlanAdapter(private val workoutPlanList: ArrayList<workoutPlan>) : RecyclerView.Adapter<workoutPlanAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recommended_plan,
            parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPlan = workoutPlanList[position]
        holder.planName.text = currentPlan.planName
        holder.difficultyLevel.text = currentPlan.difficulty
    }


    override fun getItemCount(): Int {
        return workoutPlanList.size
    }



    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val planName: TextView = itemView.findViewById(R.id.tv_planName)
        val difficultyLevel: TextView = itemView.findViewById(R.id.tv_difficulty)
    }
}