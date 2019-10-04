package com.example.recipesfornewbies.UtilsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipesfornewbies.R

class SettingsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)

        TODO("Listen to settings change, store them in a RoomDatabase and adjust display of both defaultList and settings tab accordingly")


    }

}