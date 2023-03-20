package com.example.expreminder

import Adapter.ListDataRVAdapter
import Database.GlobarVar
import Interface.CardListener
import Model.Product
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_productgood.*
import java.text.SimpleDateFormat
import java.util.*

class productgood : Fragment(), CardListener {
    private val adapter = ListDataRVAdapter(GlobarVar.listProductGood, this)
    private lateinit var myView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_productgood, container, false)
        // return inflater.inflate(R.layout.fragment_menu3, container, false)
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

//        CheckPermissions()
//
//        setupRecyclerView()
////        addDummyData()
//
//        listener()


        setupRecyclerView()

        GlobarVar.listProductGood.clear()
//        loadFromDB()
        for (i in 0 until GlobarVar.listProduct.size) {
            val datenow = System.currentTimeMillis()
            val date1 = Date(datenow)

            val format = SimpleDateFormat("dd/MM/yyyy")
            var dait: Date

            val expdate = GlobarVar.listProduct.get(i).expdate
            dait = format.parse(expdate);
            val seminggu = 7
            val sebulan = 30
            val sehari = 1
            val mAlertDialog = AlertDialog.Builder(requireContext())
            //set alertdialog icon

            val hitungan =  (((dait.time - date1.time) / (1000 * 60 * 60 * 24))+1).toInt()
            if (seminggu.equals(hitungan)){


                mAlertDialog.setTitle(GlobarVar.listProduct.get(i).productname) //set alertdialog title
                mAlertDialog.setMessage( "Expires in 7 days")
                mAlertDialog.setNegativeButton("Close") { dialog, id ->
                }
                mAlertDialog.show()
              //  Toast.makeText(requireContext(), GlobarVar.listProduct.get(i).productname + "IN 7 DAYS", Toast.LENGTH_LONG).show()
            }else if(sebulan.equals(hitungan)){
                mAlertDialog.setTitle(GlobarVar.listProduct.get(i).productname) //set alertdialog title
                mAlertDialog.setMessage( "Expires in 30 days")
                mAlertDialog.setNegativeButton("Close") { dialog, id ->
                }
                mAlertDialog.show()
            }else if(sehari.equals(hitungan)){
                mAlertDialog.setTitle(GlobarVar.listProduct.get(i).productname) //set alertdialog title
                mAlertDialog.setMessage( "Expires in 1 day")
                mAlertDialog.setNegativeButton("Close") { dialog, id ->
                }
                mAlertDialog.show()
            }
            if (dait.after(date1)) {
//exp


                val newProduct = Product(
                    GlobarVar.listProduct.get(i).product_id,
                    GlobarVar.listProduct.get(i).user_id,
                    GlobarVar.listProduct.get(i).productname,
                    GlobarVar.listProduct.get(i).category,
                    GlobarVar.listProduct.get(i).expdate,
                    GlobarVar.listProduct.get(i).quantity,
                    GlobarVar.listProduct.get(i).productpic,
                    "1"

                )

                GlobarVar.listProductGood.add(newProduct)



            }


        }
        adapter.notifyDataSetChanged()

    }

//    private fun loadFromDB() {
//        val jObj = JSONObject()
//        jObj.put("user_id", GlobarVar.listUser.get(0).user_id)
//        val request = JsonObjectRequest(
//            Request.Method.DEPRECATED_GET_OR_POST,
//            GlobarVar.ReadAllProductByStatusGood,
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
//                    GlobarVar.listProductGood.add(newProduct)
//
//                }
//                adapter.notifyDataSetChanged()
//                // val length = it.getInt("length")
//            },
//            {
//                Toast.makeText(
//                    requireContext(),
//                    GlobarVar.listUser.get(0).username,
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
        listDataRVGood.layoutManager = layoutManager //set layout
        listDataRVGood.adapter = adapter //set adapter

    }

}
