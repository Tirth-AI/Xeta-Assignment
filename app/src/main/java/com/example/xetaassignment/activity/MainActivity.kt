package com.example.xetaassignment.activity

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.xetaassignment.R
import com.example.xetaassignment.adapter.categoryAdapter
import com.example.xetaassignment.adapter.workoutPlanAdapter
import com.example.xetaassignment.model.category
import com.example.xetaassignment.databinding.ActivityMainBinding
import com.example.xetaassignment.model.workoutPlan


class MainActivity : AppCompatActivity() {
    private lateinit var decorView: View
    private lateinit var binding: ActivityMainBinding
    private var customProgressDialog: Dialog? = null

    var workoutPlanList = ArrayList<workoutPlan>()
    var categoryList = ArrayList<category>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showCustomDialog()
        fetchData()
        decorView = window.decorView
        decorView.setOnSystemUiVisibilityChangeListener {
            if (it == 0) {
                decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            )
        }
    }

    private fun fetchData() {
        val url = "http://52.25.229.242:8000/homepage_v2/?format=json"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val data = response.getJSONObject("data")
                val section1 = data.getJSONObject("section_1")
                val section2 = data.getJSONObject("section_2")
                val section3 = data.getJSONObject("section_3")
                val section4 = data.getJSONObject("section_4")



                binding.tvAccuracyValue.text = section2.getString("accuracy")
                binding.tvWorkoutDurationValue.text = section2.getString("workout_duration")
                binding.tvRepsValue.text = section2.getString("reps")
                binding.tvCaloriesBurntValue.text = section2.getString("calories_burned")
                binding.tvCurrentWorkoutPlan.text = section1.getString("plan_name")

                val progress = section1.getString("progress")
                val intProgress = progress.filter { it.isDigit() }
                binding.progressBar.progress = intProgress.toInt()


                val plan1 = section3.getJSONObject("plan_1")
                val plan2 = section3.getJSONObject("plan_2")
                val category1 = section4.getJSONObject("category_1")
                val category2 = section4.getJSONObject("category_2")

                workoutPlanList.add(
                    workoutPlan(
                        plan1.getString("plan_name"),
                        plan1.getString("difficulty")
                    )
                )
                workoutPlanList.add(
                    workoutPlan(
                        plan2.getString("plan_name"),
                        plan2.getString("difficulty")
                    )
                )

                categoryList.add(
                    category(
                        category1.getString("category_name"),
                        category1.getString("no_of_exercises")
                    )
                )
                categoryList.add(
                    category(
                        category2.getString("category_name"),
                        category2.getString("no_of_exercises")
                    )
                )

                val workoutAdapter = workoutPlanAdapter(workoutPlanList)
                binding.rvRecommendedPlan.adapter = workoutAdapter

                val categoryAdapter = categoryAdapter(categoryList)
                binding.rvCategories.adapter = categoryAdapter

                cancelCustomDialog()
            },
            {
                Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
                cancelCustomDialog()
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    private fun showCustomDialog() {
        customProgressDialog = Dialog(this@MainActivity)
        customProgressDialog?.setContentView(R.layout.custom_progress_dialog)
        customProgressDialog?.show()
    }

    private fun cancelCustomDialog() {
        if (customProgressDialog != null) {
            customProgressDialog?.dismiss()
            customProgressDialog = null
        }
    }
}


class MySingleton(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: MySingleton? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MySingleton(context).also {
                    INSTANCE = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}
