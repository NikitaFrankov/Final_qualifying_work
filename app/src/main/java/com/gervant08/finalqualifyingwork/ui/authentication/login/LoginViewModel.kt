package com.gervant08.finalqualifyingwork.ui.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gervant08.finalqualifyingwork.model.data.User
import com.gervant08.finalqualifyingwork.model.tools.DataStoreManager
import com.gervant08.finalqualifyingwork.model.data.UserLiveData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel (
    private val dataStoreManager: DataStoreManager
) : ViewModel() {


    fun login(userEmail: String, userPassword: String) {
        var email = ""
        var password = ""

        viewModelScope.launch {
            dataStoreManager.readEmail().collect {
                email = it
            }
            dataStoreManager.readPassword().collect {
                password = it
            }

            if (userEmail == email && userPassword == password) {
                UserLiveData.loggedUserLiveData.postValue(User(userEmail, userPassword))
            }
        }


    }

}