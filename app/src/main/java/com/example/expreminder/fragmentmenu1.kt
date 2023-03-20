package com.example.expreminder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_bottomnavbar.*

import kotlinx.android.synthetic.main.fragment_menu1.*

class fragmentmenu1 : Fragment() {

    private lateinit var myView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_menu1, container, false)
        //return inflater.inflate(R.layout.fragment_menu1, container, false)

        return myView


    }


}