package com.example.expreminder

import Database.GlobarVar
import Database.VolleySingleton
import Model.Product
import Model.User
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_editproduct.*
import kotlinx.android.synthetic.main.activity_editprofile.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_menu2.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class editproduct : AppCompatActivity() {
    private lateinit var product: Product
    private lateinit var image: String
    private var position = -1
    private lateinit var imageArray: ByteArray
    private val GetResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {   // APLIKASI GALLERY SUKSES MENDAPATKAN IMAGE
                val uri = it.data?.data                 // GET PATH TO IMAGE FROM GALLEY
                pictureproduct_edit.setImageURI(uri) // MENAMPILKAN DI IMAGE VIEW
                if (uri != null) {
                    createImageData(uri)
                }
                //   GlobalVar.listDataUser[position].imageUri = uri.toString()
            }
        }

    private fun createImageData(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)
        inputStream?.buffered()?.use {
            imageArray = it.readBytes()
           // GlobarVar.listUser.get(0).profilepic = GlobarVar.ByteArrToString(imageArray!!)
            image = GlobarVar.ByteArrToString(imageArray!!)
        }
    }

    private fun GetIntent() {
        position = intent.getIntExtra("position", -1)

    }

    private fun Display() {

        productname_edit.editText?.setText(GlobarVar.listProductEdit.get(0).productname)
        productcategory_edit.editText?.setText(GlobarVar.listProductEdit.get(0).category)
        expdate_edit.editText?.setText(GlobarVar.listProductEdit.get(0).expdate)
        quantity_edit.editText?.setText(GlobarVar.listProductEdit.get(0).quantity)
        if (GlobarVar.listProductEdit.get(0).productpic != "null") {
            val bArray = GlobarVar.StringToByteArr(GlobarVar.listProductEdit.get(0).productpic)
            //val bArray = GlobalVar.StringToByteArr(user.imageString)
            val options = BitmapFactory.Options()
            options.inSampleSize = 1
            options.inScaled = true
            val bitMap = BitmapFactory.decodeByteArray(
                bArray,
                0,
                bArray.size,
                options
            )
            pictureproduct_edit.setImageBitmap(bitMap)
        }

//
    }

    private fun UpdateData() {

        //Toast.makeText(this, GlobarVar.listUser.get(0).profilepic, Toast.LENGTH_LONG).show()
        val jObj = JSONObject()
        jObj.put("product_id", GlobarVar.listProductEdit.get(0).product_id)
        jObj.put("user_id", GlobarVar.listProductEdit.get(0).user_id)
        jObj.put("productname", productname_edit.editText?.text)
        jObj.put("category", productcategory_edit.editText?.text)
        jObj.put("expdate", expdate_edit.editText?.text)
        jObj.put("quantity", quantity_edit.editText?.text)
        if (image == "") {
            jObj.put("productpic", GlobarVar.listProductEdit.get(0).productpic)
        } else {
            jObj.put("productpic", image)
        }
        val datenow = System.currentTimeMillis()
        val date1 = Date(datenow)

        val format = SimpleDateFormat("dd/MM/yyyy")
        var dait: Date
        val expdate = expdate_edit.editText?.text

        dait = format.parse(expdate.toString());
        if (dait.before(date1)) {
//exp
            UpdateStatus()
            jObj.put("status", 0)

            //if (kolom == 3) {
            //  JOptionPane.showMessageDialog(null, "Barang Kadaluarsa");
            //}
//            mp3.play();
        }else{
            UpdateStatusGood()
            jObj.put("status", 1)
        }


        val request = JsonObjectRequest(
            Request.Method.POST,
            GlobarVar.UpdateProduct,
            jObj,
            {
                val message = it.getString("message")
                // Log.d("messageserver", message)
                if (message == "Success") {
                    Toast.makeText(this, "Data successfully updated", Toast.LENGTH_LONG).show()
                }
                finish()
            },
            {
                Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
                it.printStackTrace()
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(request)

    }
    private fun UpdateStatus() {

        //Toast.makeText(this, GlobarVar.listUser.get(0).profilepic, Toast.LENGTH_LONG).show()
        val jObj = JSONObject()
        jObj.put("product_id", GlobarVar.listProductEdit.get(0).product_id)


        val request = JsonObjectRequest(
            Request.Method.POST,
            GlobarVar.UpdateStatus,
            jObj,
            {
                val message = it.getString("message")
                // Log.d("messageserver", message)
                if (message == "Success") {
                    Toast.makeText(this, "Data successfully Updated", Toast.LENGTH_LONG).show()
                }

                finish()
            },
            {
                Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
                it.printStackTrace()
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(request)

    }
    private fun UpdateStatusGood() {

        //Toast.makeText(this, GlobarVar.listUser.get(0).profilepic, Toast.LENGTH_LONG).show()
        val jObj = JSONObject()
        jObj.put("product_id", GlobarVar.listProductEdit.get(0).product_id)


        val request = JsonObjectRequest(
            Request.Method.POST,
            GlobarVar.UpdateStatusGood,
            jObj,
            {
                val message = it.getString("message")
                // Log.d("messageserver", message)
                if (message == "Success") {
                    Toast.makeText(this, "Data successfully Updated", Toast.LENGTH_LONG).show()
                }
                finish()
            },
            {
                Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
                it.printStackTrace()
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(request)

    }
    override fun onResume() {
        super.onResume()

        val kategori2 = resources.getStringArray(R.array.categoryitem)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, kategori2)
        dropdown.setAdapter(arrayAdapter)
        //material date picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        datepickeredit.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    datepickeredit.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)


                },
                year,
                month,
                day
            )
            dpd.show()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editproduct)
        GetIntent()
        image = ""
        Display()

        backbutton_edit.setOnClickListener {
            onBackPressed()
        }
        pictureproduct_edit.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }
        buttonproductpicture_edit.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }
        buttonready_edit.setOnClickListener {
            UpdateData()
            finish()
        }
    }


}