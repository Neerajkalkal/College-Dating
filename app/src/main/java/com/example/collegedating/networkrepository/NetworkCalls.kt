package com.example.collegedating.networkrepository

import android.graphics.Bitmap
import android.util.Log
import com.example.collegedating.model.CollegeList.CollegeList
import com.example.collegedating.model.DataOrException
import com.example.collegedating.model.Interests
import com.example.collegedating.model.Token
import com.example.collegedating.model.adduser.BasicProfileDetails
import com.example.collegedating.model.adduser.UsernameSend
import com.google.gson.Gson
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.ConnectException
import kotlin.math.log

object NetworkCalls {

    private const val URL = "http://3.6.112.15:8081/"


    // Ask For Sending Otp
    suspend fun sendOtp(
        emailId: String,
        otpRequestState: MutableStateFlow<DataOrException<String, Exception>>
    ) {
        otpRequestState.value = DataOrException(loading = true)
        val client = OkHttpClient()


        val requestBody = emailId.toRequestBody("text/plain".toMediaTypeOrNull())

        val request = Request.Builder()
            .post(requestBody)
            .url("${URL}auth/signup")
            .build()
        withContext(Dispatchers.IO) {
            try {

                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    otpRequestState.value = DataOrException(data = responseBody, loading = false)
                } else {
                    otpRequestState.value =
                        DataOrException(e = Exception(response.message), loading = false)
                }

            } catch (error: IOException) {
                otpRequestState.value =
                    DataOrException(loading = false, e = Exception("Internet Error"))
            } catch (error: Exception) {
                otpRequestState.value = DataOrException(loading = false, e = error)

            } catch (error: ConnectException) {

                otpRequestState.value = DataOrException(
                    loading = false,
                    e = Exception("Network error. Please check your connection or try again later.")
                )
            }
        }
    }

    // otp verification
    suspend fun otpVerification(
        emailId: String,
        otp: String,
        otpVerificationState: MutableStateFlow<DataOrException<String, Exception>>
    ) {

        otpVerificationState.value = DataOrException(loading = true)
        val json = """
            {
                "email": "$emailId",
                "otp": "$otp"
            }
        """.trimIndent()

        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        val client = OkHttpClient()

        val request = Request.Builder()
            .post(requestBody)
            .url("${URL}auth/otpVerification")
            .build()

        withContext(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = response.body?.string() ?: ""
                    otpVerificationState.value =
                        DataOrException(data = responseBody, loading = false)

                } else {

                    otpVerificationState.value =
                        DataOrException(loading = false, e = Exception("Something Went Wrong"))
                }

            } catch (_: Exception) {
                otpVerificationState.value =
                    DataOrException(loading = false, e = Exception("Something Went Wrong"))
            }
        }

    }


    // fetch  interest list

    suspend fun getInterestList(
        interestListState: MutableStateFlow<DataOrException<Interests, Exception>>,
    ) {
        interestListState.value = DataOrException(loading = true)


        delay(1000)
        val client = OkHttpClient()

        val request = Request.Builder()
            .get()
            .url("${URL}public/getInterests")
            .build()

        withContext(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    response.body?.use { responseBody ->
                        val quotation =
                            Gson().fromJson(responseBody.string(), Interests::class.java)
                        interestListState.value = DataOrException(data = quotation, loading = false)

                    } ?: run {
                        interestListState.value =
                            DataOrException(e = Exception("Response body is null"), loading = false)
                    }
                }

            } catch (_: Exception) {
                interestListState.value =
                    DataOrException(e = Exception("Something Went Wrong"), loading = false)
            }

        }

    }

    // fetch  college list
    suspend fun fetchCollegeList(
        collegeListState: MutableStateFlow<DataOrException<CollegeList, Exception>>,
        query: String
    ) {
        collegeListState.value = DataOrException(loading = true)
        Log.e("fetchinggg", "deenjenjjjnercnje")
        val client = OkHttpClient()
        val request = Request.Builder()
            .get()
            .url("${URL}public/colleges?name=$query")
            .build()

        withContext(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    response.body?.use { responseBody ->
                        val quotation =
                            Gson().fromJson(responseBody.string(), CollegeList::class.java)
                        collegeListState.value = DataOrException(data = quotation, loading = false)
                    }
                }

            } catch (_: Exception) {
                collegeListState.value =
                    DataOrException(e = Exception("Something Went Wrong"), loading = false)
            }
        }
    }

    //  adding user name
    suspend fun addUsername(
        username: String,
        token: String,
        password: String,
        usernameState: MutableStateFlow<DataOrException<Token, Exception>>
    ) {
        usernameState.value = DataOrException(loading = true)
        val dataToSend = UsernameSend(username, password)

        val client = OkHttpClient()

        val requestBody =
            Gson().toJson(dataToSend).toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .post(requestBody)
            .url("${URL}newUser/createNewUser")
            .addHeader("Authorization", "Bearer $token")
            .build()

        withContext(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    response.body?.use { responseBody ->
                        val quotation =
                            Gson().fromJson(responseBody.string(), Token::class.java)
                        usernameState.value = DataOrException(data = quotation, loading = false)
                    }
                }


            } catch (
                _: Exception
            ) {
                usernameState.value =
                    DataOrException(e = Exception("Something Went Wrong"), loading = false)
            }
        }

    }


    // upload the image and the data
    suspend fun uploadUserBasicDetails(
        basicProfileDetails: BasicProfileDetails,
        token: String,
        uploadState: MutableStateFlow<DataOrException<String, Exception>>
    ) {
        uploadState.value = DataOrException(loading = true)
        val client = OkHttpClient()


        val requestBody = Gson().toJson(basicProfileDetails).toRequestBody("application/json".toMediaTypeOrNull())

        Log.d("bhb",basicProfileDetails.toString())

        val request = Request.Builder()
            .url("${URL}newUser/addBasicDetails")
            .addHeader("Authorization", "Bearer $token")
            .post(requestBody)
            .build()

        withContext(Dispatchers.IO) {
            try {

                Log.d("token",token)
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    uploadState.value =
                        DataOrException(data = response.body?.string(), loading = false)
                } else {

                    // Handle specific error codes
                    val errorMessage = when (response.code) {
                        400 -> "Bad Request: The server could not understand the request."
                        401 -> "Unauthorized: Authentication failed or user does not have permissions."
                        403 -> "Forbidden: Access is denied."
                        404 -> "Not Found: The requested resource could not be found."
                        500 -> "Internal Server Error: The server encountered an error."
                        else -> "Unexpected error: ${response.code}"
                    }
                    uploadState.value =
                        DataOrException(e = Exception(errorMessage), loading = false)
                }
            } catch (error: IOException) {
                uploadState.value =
                    DataOrException(e = Exception("Internet Error"), loading = false)

            } catch (error: Exception) {
                uploadState.value = DataOrException(e = error, loading = false)

            }
        }


    }


    fun bitmapToByteArray(
        bitmap: Bitmap,
        format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG
    ): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(format, 100, stream) // Compress quality is set to 100%
        return stream.toByteArray()
    }

}