package com.hfad.connectfour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText

class Home : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val nameInput = view.findViewById<TextInputEditText>(R.id.enterName)

        view.findViewById<Button>(R.id.startbutton).setOnClickListener {
            val userName = nameInput.text.toString()
            sharedViewModel.setText(userName) // Update the ViewModel with the user's name

            navController.navigate(R.id.action_home2_to_connectFour)
        }
    }
}
