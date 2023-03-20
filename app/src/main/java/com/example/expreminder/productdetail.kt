package com.example.expreminder

import Database.GlobarVar
import Database.VolleySingleton
import Model.Product
import Model.User
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_productdetail.*
import kotlinx.android.synthetic.main.fragment_menu2.*
import org.json.JSONObject

class productdetail : AppCompatActivity() {
    private var position = -1
    private lateinit var product: Product
   // private lateinit var imageArray: ByteArray


    private fun Listener() {
        backbutton_detail.setOnClickListener {
            onBackPressed()
        }
        deleteproductdetail_button.setOnClickListener {
            val mAlertDialog = AlertDialog.Builder(this)
            //set alertdialog icon
            mAlertDialog.setTitle("Delete Product") //set alertdialog title
            mAlertDialog.setMessage("Are you sure you want to delete this Product?") //set alertdialog message
            mAlertDialog.setPositiveButton("Delete") { dialog, id ->

                DeleteData()
              //  finish()
            }
            mAlertDialog.setNegativeButton("Cancel") { dialog, id ->
                //perform som tasks here

            }
            mAlertDialog.show()
//

        }

        editproductdetail_button.setOnClickListener {
            val myIntent = Intent(this, editproduct::class.java).apply {
                putExtra("position", position)
            }
            startActivity(myIntent)
        }


    }

    private fun Display() {
        productname_detail.text = product.productname
        category_detail.text = product.category
        expdate_detail.text = product.expdate
        quantity_detail.text = product.quantity
        if (product.productpic != "") {
            val bArray = GlobarVar.StringToByteArr(product.productpic)
            val options = BitmapFactory.Options()
            options.inSampleSize = 1
            options.inScaled = true
            val bitMap = BitmapFactory.decodeByteArray(
                bArray,
                0,
                bArray.size,
                options
            )
            fotoproduct_detail.setImageBitmap(bitMap)
        }


    }

    private fun ReadFromBD() {
        val jObj = JSONObject()
        jObj.put("product_id", position)


        val request = JsonObjectRequest(
            Request.Method.POST,
            GlobarVar.ReadProductByID,
            jObj,
            {

                val jsonObj = it.getJSONObject("data")
                product = Product(
                    jsonObj.getInt("product_id"),
                    jsonObj.getInt("user_id"),
                    jsonObj.getString("productname"),
                    jsonObj.getString("category"),
                    jsonObj.getString("expdate"),
                    jsonObj.getString("quantity"),
                    jsonObj.getString("productpic"),
                    jsonObj.getString("status")
                )
                if (GlobarVar.listProductEdit.size > 0) {
                    GlobarVar.listProductEdit.clear()
                }

                GlobarVar.listProductEdit.add(product)
                Display()
            },
            {
                Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
                it.printStackTrace()
            }


        )

        VolleySingleton.getInstance(this).addToRequestQueue(request)

    }

    private fun DeleteData() {
        val jObj = JSONObject()
        jObj.put("product_id", position)


        val request = JsonObjectRequest(
            Request.Method.POST,
            GlobarVar.DeleteProduct,
            jObj,
            {
                val myIntent = Intent(this, bottomnavbar::class.java)
                startActivity(myIntent)
                finish()
            },
            {
                Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
                it.printStackTrace()
            }


        )

        VolleySingleton.getInstance(this).addToRequestQueue(request)

    }

    private fun GetIntent() {
        position = intent.getIntExtra("position", -1)

    }

    override fun onResume() {
        super.onResume()
        ReadFromBD()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productdetail)
        GetIntent()
        Listener()
    }
}