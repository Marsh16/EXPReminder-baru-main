package com.example.expreminder

import Adapter.ListDataRVAdapter
import Database.GlobarVar
import Database.VolleySingleton
import Interface.CardListener
import Model.Product
import Model.User
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_editproduct.*
import kotlinx.android.synthetic.main.fragment_productall.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class productall : Fragment(), CardListener {
    private lateinit var myView: View
    private lateinit var user: User
   

    private val adapter = ListDataRVAdapter(GlobarVar.listProduct, this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_productall, container, false)
        // return inflater.inflate(R.layout.fragment_menu3, container, false)
//        loadFromDB()
        // GlobarVar.listProduct.clear()
        return myView

    }

    override fun onCardClick(position: Int) {
        val myIntent = Intent(requireContext(), productdetail::class.java).apply {
            putExtra("position", position)
        }
        startActivity(myIntent)
    }


    override fun onResume() {
        super.onResume()
       // GlobarVar.listProduct.clear()
        setupRecyclerView()

        GlobarVar.listProduct.clear()
        loadFromDB()


    }

    private fun loadFromDB() {
        val jObj = JSONObject()
        jObj.put("user_id", GlobarVar.listUser.get(0).user_id)
        val request = JsonObjectRequest(
            Request.Method.POST,
            GlobarVar.ReadAllProductById,
            jObj,
            {
                val jsonArray = it.getJSONArray("data")
                for (i in 0 until jsonArray.length()) {
                    val jsonObj = jsonArray.getJSONObject(i)
                    val newProduct = Product(
                        jsonObj.getInt("product_id"),
                        jsonObj.getInt("user_id"),
                        jsonObj.getString("productname"),
                        jsonObj.getString("category"),
                        jsonObj.getString("expdate"),
                        jsonObj.getString("quantity"),
                        jsonObj.getString("productpic"),
                        jsonObj.getString("status")
                    )
                    GlobarVar.listProduct.add(newProduct)
                }
                adapter.notifyDataSetChanged()
                // val length = it.getInt("length")
            },
            {
                Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_LONG).show()
                it.printStackTrace()
            }
        )
        VolleySingleton.getInstance(requireContext()).addToRequestQueue(request)
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        listData_RV.layoutManager = layoutManager //set layout
        listData_RV.adapter = adapter //set adapter
    }




}