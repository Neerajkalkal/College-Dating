package com.example.collegedating.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collegedating.model.CollegeList.CollegeList
import com.example.collegedating.model.DataOrException
import com.example.collegedating.model.Interests
import com.example.collegedating.model.Token
import com.example.collegedating.model.adduser.BasicProfileDetails
import com.example.collegedating.networkrepository.NetworkCalls
import com.example.collegedating.tokenmanagement.TokenManagement
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val tokenManagement: TokenManagement) : ViewModel() {

    private var emailLocalToken: String? = null

    fun setEmailToken(
        emailToken: String
    ) {
        emailLocalToken = emailToken
    }


    // sending otp
    private val _otpRequestState =
        MutableStateFlow<DataOrException<String, Exception>>(DataOrException())
    val otpRequestState: StateFlow<DataOrException<String, Exception>> = _otpRequestState

    fun sendOtp(
        emailId: String
    ) {
        viewModelScope.launch {
            NetworkCalls.sendOtp(
                emailId = emailId,
                otpRequestState = _otpRequestState
            )
        }
    }

    // otp verification state
    private val _otpVerificationState =
        MutableStateFlow<DataOrException<String, Exception>>(DataOrException())
    val otpVerificationState: StateFlow<DataOrException<String, Exception>> = _otpVerificationState

    fun verifyOtp(
        emailId: String,
        otp: String
    ) {
        viewModelScope.launch {
            NetworkCalls.otpVerification(
                emailId = emailId,
                otp = otp,
                otpVerificationState = _otpVerificationState
            )
        }
    }

    // fetching interest api

    private val _getInterests = MutableStateFlow<DataOrException<Interests, Exception>>(
        DataOrException()
    )
    val getInterests: StateFlow<DataOrException<Interests, Exception>> = _getInterests

    fun fetchInterests() {
        viewModelScope.launch {
            NetworkCalls.getInterestList(
                interestListState = _getInterests
            )
        }
    }

    // college name
    private val _getCollegeList = MutableStateFlow<DataOrException<CollegeList, Exception>>(
        DataOrException()
    )
    val getCollegeList: StateFlow<DataOrException<CollegeList, Exception>> = _getCollegeList


    fun fetchCollegeList(
        query: String
    ) {
        viewModelScope.launch {
            NetworkCalls.fetchCollegeList(
                collegeListState = _getCollegeList,
                query = query
            )

        }
    }

    private val _usernameSendState =
        MutableStateFlow<DataOrException<Token, Exception>>(DataOrException())
    val usernameSendState: StateFlow<DataOrException<Token, Exception>> = _usernameSendState


    // sending userNAme
    fun sendUserNameDetails(
        username: String,
        password: String
    ) {
        viewModelScope.launch {
            emailLocalToken?.let {
                NetworkCalls.addUsername(
                    username = username,
                    usernameState = _usernameSendState,
                    password = password,
                    token = it
                )
            }
        }
    }

    // sending basic user details
    private val _basicUserDetailsState = MutableStateFlow<DataOrException<String, Exception>>(
        DataOrException()
    )
    val basicUserDetailsState: StateFlow<DataOrException<String, Exception>> =
        _basicUserDetailsState

    fun sendBasicUserDetails(
        basicProfileDetails: BasicProfileDetails

    ) {
        viewModelScope.launch {

            tokenManagement.getAccessToken()?.let {
                NetworkCalls.uploadUserBasicDetails(
                    basicProfileDetails = basicProfileDetails,
                    token = it,
                    uploadState = _basicUserDetailsState
                )
            }
        }
    }


    fun reset(

    ) {
        _basicUserDetailsState.value = DataOrException()
        _getCollegeList.value = DataOrException()
        _otpRequestState.value = DataOrException()
        _otpVerificationState.value = DataOrException()
    }


}