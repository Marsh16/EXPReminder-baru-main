package com.example.expreminder

import Adapter.ListDataRVAdapter
import Database.GlobarVar
import Database.VolleySingleton
import Interface.CardListener
import Model.Product
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_editproduct.*
import kotlinx.android.synthetic.main.fragment_productall.*
import kotlinx.android.synthetic.main.fragment_productexpired.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*





class productexpired : Fragment(), CardListener {
    private val adapter = ListDataRVAdapter(GlobarVar.listProductEXP, this)
    private lateinit var myView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_productexpired, container, false)
        // return inflater.inflate(R.layout.fragment_menu3, container, false)
        return myView
    }

    override fun onCardClick(position: Int) {
        val myIntent = Intent(requireContext(), productdetail::class.java).apply {
            putExtra("position", position)
        }
        startActivity(myIntent)
    }
//    private fun playAudio(context: Context) {
//        val mediaPlayer = MediaPlayer.create(context, R.raw.ring)
//        mediaPlayer.start()
//    }


    override fun onResume() {
        super.onResume()

//        CheckPermissions()
//
//        setupRecyclerView()
////        addDummyData()
//
//        listener()


        setupRecyclerView()
        GlobarVar.listProductEXP.clear()
        //loadFromDB()
        for (i in 0 until GlobarVar.listProduct.size) {
            val datenow = System.currentTimeMillis()
            val date1 = Date(datenow)

            val format = SimpleDateFormat("dd/MM/yyyy")
            var dait: Date

            val expdate = GlobarVar.listProduct.get(i).expdate
            dait = format.parse(expdate)
           // val mAlertDialog = AlertDialog.Builder(requireContext())
            if (dait.before(date1)) {
//exp

//                mAlertDialog.setTitle(GlobarVar.listProduct.get(i).productname) //set alertdialog title
//                mAlertDialog.setMessage( "Has Expired")
//                mAlertDialog.setNegativeButton("Close") { dialog, id ->
//
//                }
//                mAlertDialog.show()
//                val mAlertDialog = AlertDialog.Builder(requireContext())
//                //set alertdialog icon
//                mAlertDialog.setTitle("Product Expired") //set alertdialog title
//                mAlertDialog.setMessage(GlobarVar.listProduct.get(i).productname + "is expired")
                val newProduct = Product(
                    GlobarVar.listProduct.get(i).product_id,
                    GlobarVar.listProduct.get(i).user_id,
                    GlobarVar.listProduct.get(i).productname,
                    GlobarVar.listProduct.get(i).category,
                    GlobarVar.listProduct.get(i).expdate,
                    GlobarVar.listProduct.get(i).quantity,
                    GlobarVar.listProduct.get(i).productpic,
                   "0"

                )

                GlobarVar.listProductEXP.add(newProduct)



            }


        }
        adapter.notifyDataSetChanged()
    }

//    private fun loadFromDB() {
//        val jObj = JSONObject()
//        jObj.put("user_id", GlobarVar.listUser.get(0).user_id)
//        val request = JsonObjectRequest(
//            Request.Method.POST,
//            GlobarVar.ReadAllProductByStatusExp,
//            jObj,
//            {
//                val jsonArray = it.getJSONArray("data")
//                for (i in 0 until jsonArray.length()) {
//                    val jsonObj = jsonArray.getJSONObject(i)
//                    val newProduct = Product(
//                        jsonObj.getInt("product_id"),
//                        jsonObj.getInt("user_id"),
//                        jsonObj.getString("productname"),
//                        jsonObj.getString("category"),
//                        jsonObj.getString("expdate"),
//                        jsonObj.getString("quantity"),
//                        jsonObj.getString("productpic"),
//                        jsonObj.getString("status")
//
//                    )
//
//                    GlobarVar.listProductEXP.add(newProduct)
//
//                }
//                adapter.notifyDataSetChanged()
//                // val length = it.getInt("length")
//            },
//            {
//                Toast.makeText(
//                    requireContext(),
//                    GlobarVar.listUser.get(0).user_id,
//                    Toast.LENGTH_LONG
//                ).show()
//                it.printStackTrace()
//            }
//
//
//        )
//
//        VolleySingleton.getInstance(requireContext()).addToRequestQueue(request)
//    }




    private fun setupRecyclerView() {

        val layoutManager = LinearLayoutManager(context)
        listDataRVExp.layoutManager = layoutManager //set layout
        listDataRVExp.adapter = adapter //set adapter

    }
}