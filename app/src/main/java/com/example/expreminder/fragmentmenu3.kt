package com.example.expreminder

import Database.GlobarVar
import Database.VolleySingleton
import Model.User
import android.R
import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.fragment_menu3.*
import org.json.JSONObject


class fragmentmenu3 : Fragment() {
    private lateinit var user: User
    private lateinit var myView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {

        myView = inflater.inflate(com.example.expreminder.R.layout.fragment_menu3, container, false)


        return myView

    }

    private fun ReadUserByUserId() {

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
                    jsonObj.getString("profilepic"),
                )
                if (!GlobarVar.listUser.isEmpty()){
                    GlobarVar.listUser.remove(GlobarVar.listUser.get(0))
                }

                GlobarVar.listUser.add(user)
                if (GlobarVar.listUser.size>0){
                    Display()
                }

            },
            {
                Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_LONG).show()
                it.printStackTrace()
            }


        )

        VolleySingleton.getInstance(requireContext()).addToRequestQueue(request)

    }


    private fun Display() {

        detail_email.text = GlobarVar.listUser.get(0).email
        detail_fullname.text = GlobarVar.listUser.get(0).fullname
        detail_phone_no.text = GlobarVar.listUser.get(0).phone_no
        detail_profile.text = GlobarVar.listUser.get(0).username
        if(GlobarVar.listUser.get(0).profilepic != "null") {
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
            detail_profilepic.setImageBitmap(bitMap)
        }
    }
    override fun onResume() {
        super.onResume()
        ReadUserByUserId()
        Listener()
        //ReadUserByUsername

    }

    private fun Listener() {

        editbutton_profile.setOnClickListener() {
            val kehalamanutama = Intent(requireContext(), editprofile::class.java)
            startActivity(kehalamanutama)
            //Toast.makeText(requireContext(), "Data successfully edite", Toast.LENGTH_LONG).show()
        }
        deletebutton_profile.setOnClickListener() {

        }
logout_detail.setOnClickListener{
    GlobarVar.listUser.remove(GlobarVar.listUser.get(0))
    val kehalamanutama = Intent(requireContext(), MainActivity::class.java)
    startActivity(kehalamanutama)
}
        deletebutton_profile.setOnClickListener {
            //dialog
            val mAlertDialog = AlertDialog.Builder(requireContext())
            //set alertdialog icon
            mAlertDialog.setTitle("Delete User") //set alertdialog title
            mAlertDialog.setMessage("Are you sure you want to delete this User?") //set alertdialog message
            mAlertDialog.setPositiveButton("Delete") { dialog, id ->

                DeleteData()

 }
            mAlertDialog.setNegativeButton("Cancel") { dialog, id ->
                //perform som tasks here

            }
            mAlertDialog.show()
        }
    }


    private fun DeleteData() {
        val jObj = JSONObject()
        jObj.put("user_id", GlobarVar.listUser.get(0).user_id)


        val request = JsonObjectRequest(
            Request.Method.POST,
            GlobarVar.DeleteUser,
            jObj,
            {
                val message = it.getString("message")
                if (message == "success") {
                    Toast.makeText(requireContext(), "Data successfully deleted", Toast.LENGTH_LONG)
                        .show()
                    val kehalamanutama = Intent(requireContext(), MainActivity::class.java)
                    startActivity(kehalamanutama)
                }
            },
            {
                Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_LONG).show()
                it.printStackTrace()
            }


        )

        VolleySingleton.getInstance(requireContext()).addToRequestQueue(request)

    }


}