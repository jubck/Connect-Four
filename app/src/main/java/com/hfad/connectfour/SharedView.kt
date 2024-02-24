/**
 * Shared View
 * Allows for the user's name to be passed to the Connect Four Fragment
 * @author jubck
 * @date 2/24/2024
 */

package com.hfad.connectfour

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    fun setText(input: String) {
        _text.value = input
    }
}
