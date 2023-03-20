package com.example.expreminder


import Database.GlobarVar
import Database.VolleySingleton
import Model.Product
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_bottomnavbar.*
import kotlinx.android.synthetic.main.activity_editproduct.*
import kotlinx.android.synthetic.main.fragment_menu2.*
import kotlinx.android.synthetic.main.signup.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class fragmentmenu2 : Fragment() {
    private lateinit var myView: View
    private lateinit var product: Product
    private var image: String = ""

    private lateinit var imageArray: ByteArray
    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){   // APLIKASI GALLERY SUKSES MENDAPATKAN IMAGE
            val uri = it.data?.data                 // GET PATH TO IMAGE FROM GALLEY
            addimageproduct.setImageURI(uri) // MENAMPILKAN DI IMAGE VIEW
            if (uri != null){
                createImageData(uri)
            }
            //   GlobalVar.listDataUser[position].imageUri = uri.toString()
        }
    }
    private fun createImageData(uri: Uri) {
        val resolver = requireActivity().contentResolver
        val inputStream = resolver.openInputStream(uri)
        inputStream?.buffered()?.use {
            imageArray = it.readBytes()
            // GlobarVar.listProduct.get(0).productpic = GlobarVar.ByteArrToString(imageArray!!)
            image = GlobarVar.ByteArrToString(imageArray!!)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {

        myView = inflater.inflate(R.layout.fragment_menu2, container, false)


        return myView

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private fun Cek() {
        //image = GlobarVar.listUser.get(0).profilepic
        var id = 0
        var user_id = GlobarVar.listUser.get(0).user_id
        var productname = productname_addproduct.editText?.text.toString().trim()
        var category = category_addproduct.editText?.text.toString().trim()
        var expdate = expdate_addproduct.editText?.text.toString().trim()
        var quantity = quantity_addproduct.editText?.text.toString().trim()
//var picture = image

        var isCompleted: Boolean = true
        if (image == ""){ //bug
           Toast.makeText(requireContext(),"Please pick picture",Toast.LENGTH_LONG).show()
            isCompleted = false
            //product= Product(id,user_id,productname,category,expdate,quantity,"","")
        }else {
            product = Product(id, user_id, productname, category, expdate, quantity, image, "")




            if (product.expdate!!.equals("Expiry Date")) {
                expdate_addproduct.error = "Please fill your Expiry Date"
                isCompleted = false
            } else {
                val datenow = System.currentTimeMillis()
                val date1 = Date(datenow)

                val format = SimpleDateFormat("dd/MM/yyyy")
                var dait: Date


                dait = format.parse(expdate);
                if (dait.before(date1)) {

                    var status = "0"
                    product =
                        Product(id, user_id, productname, category, expdate, quantity, "", status)
                    // product = Product(id, user_id, productname, category, expdate, quantity, picture, status)

                    //product = Product(id, user_id, productname, category, expdate, quantity, "", status)
                    //if (kolom == 3) {
                    //  JOptionPane.showMessageDialog(null, "Barang Kadaluarsa");
                    //}
//            mp3.play();
                } else {
                    var status = "1"
                    product =
                        Product(id, user_id, productname, category, expdate, quantity, "", status)
                    //    product = Product(id, user_id, productname, category, expdate, quantity, picture, status)

                    // product = Product(id, user_id, productname, category, expdate, quantity, "", status)
                }
                expdate_addproduct.error = ""
            }
            // fullname
            if (product.productname!!.isEmpty()) {
                productname_addproduct.error = "Please fill your product name"
                isCompleted = false
            } else {
                productname_addproduct.error = ""
            }

            // username
            if (product.category!!.equals("Category")) {
                category_addproduct.error = "Please fill your Category"
                isCompleted = false
            } else {
                category_addproduct.error = ""
            }

            //Phone no


            //Email
            if (product.quantity!!.isEmpty()) {
                quantity_addproduct.error = "Please fill your quantity"
                isCompleted = false
            } else {
                quantity_addproduct.error = ""
            }

        }

        if (isCompleted) {

//
            //}
            CheckProductExist()

            //finish()
        }


    }

    private fun CheckProductExist() {
        val jObj = JSONObject()
        jObj.put("productname", product.productname)
        jObj.put("user_id", GlobarVar.listUser.get(0).user_id)


        val request = JsonObjectRequest(
            Request.Method.POST,
            GlobarVar.CheckProductExist,
            jObj,
            {
                val message = it.getString("message")
                if (message == "1") {
                    Toast.makeText(requireContext(), "Product Sudah Ada", Toast.LENGTH_LONG).show()

                } else {
                    if (product.status == "0") {
                        Toast.makeText(
                            requireContext(),
                            "Tanggal sudah Expired!!",
                            Toast.LENGTH_LONG
                        ).show()
                        val mAlertDialog = AlertDialog.Builder(requireContext())
                        //set alertdialog icon
                        mAlertDialog.setTitle("Add Product") //set alertdialog title
                        mAlertDialog.setMessage("Tanggal sudah Expired yakin ingin menambahkan product?") //set alertdialog message
                        mAlertDialog.setPositiveButton("Add") { dialog, id ->

                            CreateData()
                        }
                        mAlertDialog.setNegativeButton("Cancel") { dialog, id ->
                            //perform som tasks here

                        }
                        mAlertDialog.show()

                    } else {
                        CreateData()
                    }

                }
            },
            {
                Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_LONG).show()
                it.printStackTrace()
            }
        )
        VolleySingleton.getInstance(requireContext()).addToRequestQueue(request)
    }

    private fun CreateData() {
        val jObj = JSONObject()
        //error userid
        jObj.put("user_id", GlobarVar.listUser.get(0).user_id)
        jObj.put("productname", product.productname)
        jObj.put("category", product.category)
        jObj.put("expdate", product.expdate)
        jObj.put("quantity", product.quantity)
        if (image==""){
            jObj.put("productpic",product.productpic)
        }else{
            jObj.put("productpic",image)
        }




        jObj.put("status", product.status)

        val request = JsonObjectRequest(
            Request.Method.POST,
            GlobarVar.CreateProduct,
            jObj,
            {
                val message = it.getString("message")
                if (message == "success") {
                    Toast.makeText(requireContext(), "Data successfully created", Toast.LENGTH_LONG)
                        .show()
                    // Toast.makeText(requireContext(), GlobarVar.listUser.get(0).user_id, Toast.LENGTH_LONG).show()
                    val keLoginActivity = Intent(requireContext(), bottomnavbar::class.java)
                    startActivity(keLoginActivity)
                }
            },
            {
                Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_LONG).show()
                it.printStackTrace()
            }
        )
        VolleySingleton.getInstance(requireContext()).addToRequestQueue(request)
    }


    private fun Listener() {
        AddButton.setOnClickListener(){

            Cek()



        }
    }

    override fun onResume() {
        super.onResume()
//        if (image==""){
//            image=""
//        }
        Listener()
        addimageproduct.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }
        addimageproduct.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }

        val kategori = resources.getStringArray(R.array.categoryitem)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, kategori)
        drop.setAdapter(arrayAdapter)
        //material date picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        datepicker.setOnClickListener {
            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    datepicker.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)


                },
                year,
                month,
                day
            )
            dpd.show()

        }

//


    }


}

