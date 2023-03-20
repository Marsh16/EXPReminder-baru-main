package com.example.expreminder


import Database.GlobarVar
import Database.VolleySingleton
import Model.User
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_productdetail.*
import kotlinx.android.synthetic.main.fragment_menu3.*
import kotlinx.android.synthetic.main.fragment_menu3.view.*
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.signup.*
import org.json.JSONObject

class loginActivity : AppCompatActivity() {
    private lateinit var user: User
    // private var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        Listener()

//        usernm.addTextChangedListener {
//            var username = usernameTextInputLayout.editText?.text.toString().trim()
//            var password = passwordTextInputLayout.editText?.text.toString().trim()
//            if (username.isEmpty() || password.isEmpty()) {
//                button.isEnabled = false
//            } else {
//                button.isEnabled = true
//            }
//        }
//        pass.addTextChangedListener {
//            var username = usernameTextInputLayout.editText?.text.toString().trim()
//            var password = passwordTextInputLayout.editText?.text.toString().trim()
//            if (username.isEmpty() || password.isEmpty()) {
//                button.isEnabled = false
//            } else {
//                button.isEnabled = true
//            }
//        }
    }

    private fun Listener() {
        newuser1.setOnClickListener() {

            intent = Intent(this, signupActivity::class.java)
            startActivity(intent)
        }
        button.setOnClickListener() {
//var id =0
//            var email =""
//            var status = ""
            var username = usernameTextInputLayout.editText?.text.toString().trim()

            var password = passwordTextInputLayout.editText?.text.toString().trim()
            val jObj = JSONObject()
            jObj.put("username", username)
            jObj.put("password", password)


            val request = JsonObjectRequest(
                Request.Method.POST,
                GlobarVar.CheckUser,
                jObj,
                {
                    val message = it.getString("message")
                    if (message == "1") {
                        // UserCheckIdByUsername()
                        val jObj = JSONObject()
                        jObj.put("username", username)


                        val request = JsonObjectRequest(
                            Request.Method.POST,
                            GlobarVar.ReadUserByUsername,
                            jObj,
                            {


                                val message = it.getString("message")
                                if (message == "success") {
                                    val jsonObj = it.getJSONObject("data")
                                    user = User(
                                        jsonObj.getInt("user_id"),
                                        jsonObj.getString("fullname"),
                                        jsonObj.getString("username"),
                                        jsonObj.getString("email"),
                                        jsonObj.getString("phone_no"),
                                        jsonObj.getString("password"),
                                        jsonObj.getString("profilepic")
                                    )
                                    var isCompleted: Boolean = true

                                    // fullname
                                    if (user.username!!.isEmpty()) {
                                        usernameTextInputLayout.error = "Please fill your username"
                                        isCompleted = false
                                    } else {
                                        usernameTextInputLayout.error = ""
                                    }

                                    if (user.password!!.isEmpty()) {

                                        passwordTextInputLayout.error = "Please fill your password"
                                        isCompleted = false
                                    } else {
                                        if (user.password!!.length < 8) {

                                            passwordTextInputLayout.error = "Jumlah password min 8 karakter"
                                            isCompleted = false
                                        } else if (!user.password!!.matches(".*[a-z].*".toRegex())) {

                                            passwordTextInputLayout.error = "Password tidak memiliki huruf kecil"
                                            isCompleted = false
                                        } else if (!user.password!!.matches(".*[A-Z].*".toRegex())) {

                                            passwordTextInputLayout.error = "Password tidak memiliki huruf kapital"
                                            isCompleted = false
                                        } else {

                                            passwordTextInputLayout.error = ""
                                        }
                                    }
                                    if (isCompleted) {

//
                                        //}
                                        if (GlobarVar.listUser.size > 0) {
                                            GlobarVar.listUser.remove(GlobarVar.listUser.get(0))
                                        }

                                        GlobarVar.listUser.add(user)
                                        //Cek()




                                    }
                                    Toast.makeText(this, "Login Berhasil", Toast.LENGTH_LONG).show()
                                    val kemenuutama = Intent(this, bottomnavbar::class.java)
                                    startActivity(kemenuutama)
                                    finish()

                                    if (!GlobarVar.listUser.isEmpty()) {
                                        GlobarVar.listUser.clear()
                                    }

                                    GlobarVar.listUser.add(user)

                                } else {
                                    Toast.makeText(this, "Username salah", Toast.LENGTH_LONG).show()
                                }

                            },
                            {
                                Toast.makeText(this, "Username Salah", Toast.LENGTH_LONG).show()
                                it.printStackTrace()
                            }
                        )
                        VolleySingleton.getInstance(this).addToRequestQueue(request)

                    } else if (message == "0") {
                        Toast.makeText(this, "Username/password salah!!", Toast.LENGTH_LONG).show()
                    }
                },
                {
                    Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
                    it.printStackTrace()
                }
            )
            VolleySingleton.getInstance(this).addToRequestQueue(request)


        }

         //   user = User(id, fullname, username, email, phone_no, password, "")
           // UserCheckIdByUsername()


        }
    }

//    private fun LoginCheck() {
//
//    }
//
//    private fun UserCheckIdByUsername() {
//
//        val jObj = JSONObject()
//        jObj.put("username", user.username)
//
//
//        val request = JsonObjectRequest(
//            Request.Method.POST,
//            GlobarVar.ReadUserByUsername,
//            jObj,
//            {
//
//
//                val message = it.getString("message")
//                if (message == "success") {
//                    val jsonObj = it.getJSONObject("data")
//                    user = User(
//                        jsonObj.getInt("user_id"),
//                        jsonObj.getString("fullname"),
//                        jsonObj.getString("username"),
//                        jsonObj.getString("email"),
//                        jsonObj.getString("phone_no"),
//                        jsonObj.getString("password"),
//                        jsonObj.getString("profilepic")
//                    )
//                    if (!GlobarVar.listUser.isEmpty()) {
//                        GlobarVar.listUser.clear()
//                    }
//
//                    GlobarVar.listUser.add(user)
//
//                } else {
//                    Toast.makeText(this, "Username salah", Toast.LENGTH_LONG).show()
//                }
//
//            },
//            {
//                Toast.makeText(this, "Username Salah", Toast.LENGTH_LONG).show()
//                it.printStackTrace()
//            }
//        )
//        VolleySingleton.getInstance(this).addToRequestQueue(request)
//    }










