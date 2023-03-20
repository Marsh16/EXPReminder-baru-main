package com.example.expreminder


import Database.VolleySingleton
import Database.GlobarVar
import Model.User
import Model.Userconfirmcheck
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.signup.*
import org.json.JSONObject

class signupActivity : AppCompatActivity() {
    private lateinit var user: User
    private lateinit var userconfirm: Userconfirmcheck

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)
        haveacc.setOnClickListener() {
            intent = Intent(this, loginActivity::class.java)
            startActivity(intent)

        }
        save_edit.setOnClickListener() {

            Cek()


        }
    }

    private fun CheckUserExist() {
        val jObj = JSONObject()
        jObj.put("username", user.username)


        val request = JsonObjectRequest(
            Request.Method.POST,
            GlobarVar.CheckUserExist,
            jObj,
            {
                val message = it.getString("message")
                if (message == "1") {
                    Toast.makeText(this, "Username Sudah Ada", Toast.LENGTH_LONG).show()

                } else {
                    CreateData()
                }
            },
            {
                Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
                it.printStackTrace()
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(request)
    }


    private fun Cek() {
        var id = 0
        var fullname = fullname_edit.editText?.text.toString().trim()
        var username = username_edit.editText?.text.toString().trim()
        var email = email_edit.editText?.text.toString().trim()
        var phone_no = phoneno_edit.editText?.text.toString().trim()
        var password = password_edit.editText?.text.toString().trim()
        var confirmcheck = confirmpassword_edit.editText?.text.toString().trim()
        userconfirm = Userconfirmcheck(confirmcheck)
        user = User(id, fullname, username, email, phone_no, password, "")
        var isCompleted: Boolean = true

        // fullname
        if (user.fullname!!.isEmpty()) {
            fullname_edit.error = "Please fill your fullname"
            isCompleted = false
        } else {
            fullname_edit.error = ""
        }

        // username
        if (user.username!!.isEmpty()) {
            username_edit.error = "Please fill your username"
            isCompleted = false
        } else {
            username_edit.error = ""
        }

        //Phone no
        if (user.phone_no!!.isEmpty()) {
            phoneno_edit.error = "Please fill your phone number"
            isCompleted = false
        } else {
            phoneno_edit.error = ""
        }

        //Email
        if (user.email!!.isEmpty()) {
            email_edit.error = "Please fill your email"
            isCompleted = false
        } else {
            // Berguna untuk cek apakah input merupakan email
            if (!Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
                email_edit.error = "Please fill your email correctly"
                isCompleted = false
            } else {
                email_edit.error = ""
            }
        }

        if (!userconfirm.confirmpassword.equals(user.password)) {
            confirmpassword_edit.error = "Please fill in the same as your password"
            isCompleted = false
        } else {
            confirmpassword_edit.error = ""
        }
//         Password
        if (user.password!!.isEmpty()) {
            password_edit.error = "Please fill your password"
            confirmpassword_edit.error = "Please fill your password"
            isCompleted = false
        } else {

            if (user.password!!.length < 8) {
                password_edit.error = "Jumlah password min 8 karakter"
                confirmpassword_edit.error = "Jumlah password min 8 karakter"
                isCompleted = false
            } else if (!user.password!!.matches(".*[a-z].*".toRegex())) {
                password_edit.error = "Password tidak memiliki huruf kecil"
                confirmpassword_edit.error = "Password tidak memiliki huruf kecil"
                isCompleted = false
            } else if (!user.password!!.matches(".*[A-Z].*".toRegex())) {
                password_edit.error = "Password tidak memiliki huruf kapital"
                confirmpassword_edit.error = "Password tidak memiliki huruf kapital"
                isCompleted = false
            } else {
                password_edit.error = ""
                confirmpassword_edit.error = ""
            }
        }

        if (isCompleted) {

//
            //}
            CheckUserExist()

            //finish()
        }
    }

    private fun CreateData() {
        val jObj = JSONObject()
        jObj.put("fullname", user.fullname)
        jObj.put("username", user.username)
        jObj.put("phone_no", user.phone_no)
        jObj.put("email", user.email)
        jObj.put("password", user.password)
        jObj.put("profilepic", "")

        val request = JsonObjectRequest(
            Request.Method.POST,
            GlobarVar.CreateUser,
            jObj,
            {
                val message = it.getString("message")
                if (message == "success") {
                    Toast.makeText(this, "Data successfully created", Toast.LENGTH_LONG).show()
                    val keLoginActivity = Intent(this, loginActivity::class.java)
                    startActivity(keLoginActivity)
                }
            },
            {
                Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
                it.printStackTrace()
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(request)
    }
}
