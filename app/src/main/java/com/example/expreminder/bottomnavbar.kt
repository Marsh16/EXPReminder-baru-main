package com.example.expreminder

import Database.GlobarVar
import Database.VolleySingleton
import Model.User
import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_bottomnavbar.*
import kotlinx.android.synthetic.main.activity_productdetail.*
import kotlinx.android.synthetic.main.fragment_menu3.*
import kotlinx.android.synthetic.main.fragment_productexpired.*
import kotlinx.android.synthetic.main.login.*
import org.json.JSONObject


class bottomnavbar : AppCompatActivity() {
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottomnavbar)


        SetCurrentFragment(productall())
        bottomNavigationView_bottomnavbar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu1_menu -> {
                    SetCurrentFragment(productall())
                    cardproduct.visibility = View.VISIBLE

                }

                R.id.menu2_menu -> {
                    SetCurrentFragment(fragmentmenu2())
                    cardproduct.visibility = View.GONE
                }
//
//                    R.id.menu2_menu -> cardproduct.visibility=View.GONE
                R.id.menu3_menu -> {
                    SetCurrentFragment(fragmentmenu3())
                    cardproduct.visibility = View.GONE

                    ReadUserByID()

                }
            }

            true
        }
        upperNavBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.all -> SetCurrentFragment(productall())
                R.id.good -> SetCurrentFragment(productgood())
                R.id.expired -> {
                    SetCurrentFragment(productexpired())

                }


            }

            true
        }


    }

    override fun onResume() {
        super.onResume()
//        SetCurrentFragment(fragmentmenu3())
//        cardproduct.visibility = View.GONE
//
//        ReadUserByID()
//        //ReadUserByUsername

    }

    private fun ReadUserByID() {

        val jObj = JSONObject()
        jObj.put("user_id", GlobarVar.listUser.get(0).user_id)


        val request = JsonObjectRequest(
            Request.Method.POST,
            GlobarVar.ReadUserByID,
            jObj,
            {

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
                if (!GlobarVar.listUser.isEmpty()) {
                    GlobarVar.listUser.remove(GlobarVar.listUser.get(0))
                }

                GlobarVar.listUser.add(user)
                Display()
            },
            {
                Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
                it.printStackTrace()
            }


        )

        VolleySingleton.getInstance(this).addToRequestQueue(request)

    }


    private fun Display() {
        detail_email.text = GlobarVar.listUser.get(0).email
        detail_fullname.text = GlobarVar.listUser.get(0).fullname
        detail_phone_no.text = GlobarVar.listUser.get(0).phone_no
        detail_profile.text = GlobarVar.listUser.get(0).username
    }

    private fun SetCurrentFragment(fragment: Fragment) {
        this.supportFragmentManager.beginTransaction().apply {
            replace(framelayout_bottomnavbar.id, fragment)
            commit()


        }

    }


}