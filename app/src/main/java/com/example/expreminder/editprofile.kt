package com.example.expreminder

import Database.GlobarVar
import Database.VolleySingleton
import Model.User
import Model.Userconfirmcheck
import android.R
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_bottomnavbar.*
import kotlinx.android.synthetic.main.activity_editprofile.*
import kotlinx.android.synthetic.main.fragment_menu2.*
import kotlinx.android.synthetic.main.fragment_menu3.*
import kotlinx.android.synthetic.main.signup.*
import org.json.JSONObject
import java.util.*


class editprofile : AppCompatActivity() {
    private var position = -1
    private lateinit var user: User
    private lateinit var userconfirm: Userconfirmcheck
    private lateinit var imageArray: ByteArray
    private lateinit var image: String
    private val GetResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {   // APLIKASI GALLERY SUKSES MENDAPATKAN IMAGE
                val uri = it.data?.data

                // GET PATH TO IMAGE FROM GALLEY
                profile_editprofile.setImageURI(uri)
                if (uri != null) {
                    createImageData(uri)
                }

                // MENAMPILKAN DI IMAGE VIEW
//            GlobalVar.listDataUser[position].imageUri = uri.toString()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.expreminder.R.layout.activity_editprofile)
        //GetIntent()
        image = ""
        if (GlobarVar.listUser.size > 0) {
            Display()
        }

        back_editprofile.setOnClickListener {
            onBackPressed()
        }
        profile_editprofile.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }
        action_editprofile.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)

        }
        save_editprofile.setOnClickListener {

            Cek()
        }

    }



    private fun createImageData(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)
        inputStream?.buffered()?.use {
            imageArray = it.readBytes()
            image = GlobarVar.ByteArrToString(imageArray!!)
        }
    }

//    private fun GetIntent() {
//        position = intent.getIntExtra("position", -1)
//
//    }

    private fun Cek() {
        var id = GlobarVar.listUser.get(0).user_id
        //Log.d("userid", id.toString())
        var fullname = fullname_editprofile.editText?.text.toString().trim()
        var username = username_editprofile.editText?.text.toString().trim()
        var email = email_editprofile.editText?.text.toString().trim()
        var phone_no = phoneno_editprofile.editText?.text.toString().trim()
        var password = password_editprofile.editText?.text.toString().trim()


        var confirmcheck = confirmpassword_editprofile.editText?.text.toString().trim()

        userconfirm = Userconfirmcheck(confirmcheck)
        if (image == "") {
            user = User(id, fullname, username, email, phone_no, password, GlobarVar.listUser.get(0).profilepic)
        } else {
            user = User(id, fullname, username, email, phone_no, password, image)
        }

        var isCompleted: Boolean = true

        // fullname
        if (user.fullname!!.isEmpty()) {
            fullname_editprofile.error = "Please fill your fullname"
            isCompleted = false
        } else {
            fullname_editprofile.error = ""
        }
        if (!userconfirm.confirmpassword.equals(user.password)) {
            confirmpassword_editprofile.error = "Please fill in the same as your password"
            isCompleted = false
        } else {
            confirmpassword_editprofile.error = ""
        }

        // username
        if (user.username!!.isEmpty()) {
            username_editprofile.error = "Please fill your username"
            isCompleted = false
        } else {
            username_editprofile.error = ""
        }

        //Phone no
        if (user.phone_no!!.isEmpty()) {
            phoneno_editprofile.error = "Please fill your phone number"
            isCompleted = false
        } else {
            phoneno_editprofile.error = ""
        }

        //Email
        if (user.email!!.isEmpty()) {
            email_editprofile.error = "Please fill your email"
            isCompleted = false
        } else {
            // Berguna untuk cek apakah input merupakan email
            if (!Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
                email_editprofile.error = "Please fill your email correctly"
                isCompleted = false
            } else {
                email_editprofile.error = ""
            }
        }


//         Password
        if (user.password!!.isEmpty()) {
            password_editprofile.error = "Please fill your password"
            confirmpassword_editprofile.error = "Please fill your password"
            isCompleted = false
        } else {
            if (user.password!!.length < 8) {
                password_editprofile.error = "Jumlah password min 8 karakter"
                confirmpassword_editprofile.error = "Jumlah password min 8 karakter"
                isCompleted = false
            } else if (!user.password!!.matches(".*[a-z].*".toRegex())) {
                password_editprofile.error = "Password tidak memiliki huruf kecil"
                confirmpassword_editprofile.error = "Password tidak memiliki huruf kecil"
                isCompleted = false
            } else if (!user.password!!.matches(".*[A-Z].*".toRegex())) {
                password_editprofile.error = "Password tidak memiliki huruf kapital"
                confirmpassword_editprofile.error = "Password tidak memiliki huruf kapital"
                isCompleted = false
            } else {
                password_editprofile.error = ""
                confirmpassword_editprofile.error = ""
            }
        }

        if (isCompleted) {
            GlobarVar.listUser.remove(GlobarVar.listUser.get(0))
            GlobarVar.listUser.add(user)
            if (GlobarVar.listUserc.size > 0) {
                GlobarVar.listUserc.remove(GlobarVar.listUserc.get(0))

            }
            GlobarVar.listUserc.add(userconfirm)
            UpdateData()
            finish()
        }
    }


    private fun Display() {
        fullname_editprofile.editText?.setText(GlobarVar.listUser.get(0).fullname)
        username_editprofile.editText?.setText(GlobarVar.listUser.get(0).username)
        email_editprofile.editText?.setText(GlobarVar.listUser.get(0).email)
        phoneno_editprofile.editText?.setText(GlobarVar.listUser.get(0).phone_no)
        password_editprofile.editText?.setText(GlobarVar.listUser.get(0).password)
        confirmpassword_editprofile.editText?.setText(GlobarVar.listUser.get(0).password)
        if (GlobarVar.listUser.get(0).profilepic != "null") {
            val bArray = GlobarVar.StringToByteArr(GlobarVar.listUser.get(0).profilepic)
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
            profile_editprofile.setImageBitmap(bitMap)
        }
    }

    private fun UpdateData() {
        //Toast.makeText(this, GlobarVar.listUser.get(0).profilepic, Toast.LENGTH_LONG).show()
        val jObj = JSONObject()
        jObj.put("user_id", GlobarVar.listUser.get(0).user_id)
        jObj.put("fullname", GlobarVar.listUser.get(0).fullname)
        jObj.put("username", GlobarVar.listUser.get(0).username)
        jObj.put("email", GlobarVar.listUser.get(0).email)
        jObj.put("phone_no", GlobarVar.listUser.get(0).phone_no)
        jObj.put("password", GlobarVar.listUser.get(0).password)
        if (image==""){
            jObj.put("profilepic", GlobarVar.listUser.get(0).profilepic)
        }else{
            jObj.put("profilepic", image)
        }


        val request = JsonObjectRequest(
            Request.Method.POST,
            GlobarVar.UpdateUser,
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


}