package com.base.ecomm.volley

import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.base.ecomm.MyApplication
import com.base.ecomm.data.BaseResponse
import com.base.ecomm.data.ConstantAPI
import com.base.ecomm.data.ResourceAPI
import java.util.*


class NetworkManager {

    interface OnRequestListener {
        fun onRequest(html: String, repository: ResourceAPI<Any>)
    }

    fun apiHeaderDefaults(): HashMap<String, String> {
        val header = HashMap<String, String>()

        header["Authorization"] = ConstantAPI.authentication
        header["mobileType"] = "android"
        header["fromApp"] = "Ecomm"
        header["isGooglePlay"] = if(ConstantAPI.GooglePlayBuild) ConstantAPI.GooglePlayBuild.toString() else ConstantAPI.productionBuild.toString()

        return header
    }

    fun apiMasterHeaderDefaults(): HashMap<String, String> {
        val header = HashMap<String, String>()

        header["Authorization"] = ConstantAPI.authentication
        header["mobileType"] = "android"
        header["fromApp"] = "Ecomm"
        header["isGooglePlay"] = if(ConstantAPI.GooglePlayBuild) ConstantAPI.GooglePlayBuild.toString() else ConstantAPI.productionBuild.toString()

        return header
    }

    fun apiMemberHeaderDefaults(): HashMap<String, String> {
        val header = HashMap<String, String>()

        header["Authorization"] = ConstantAPI.authentication
        header["mobileType"] = "android"
        header["fromApp"] = "Ecomm"
        header["isGooglePlay"] = if(ConstantAPI.GooglePlayBuild) ConstantAPI.GooglePlayBuild.toString() else ConstantAPI.productionBuild.toString()

        return header
    }

    fun apiBodyDefaults(): HashMap<String, String> {
        val body = HashMap<String, String>()

        body["mobileType"] = "android"

        return body
    }

    fun callAPI(
        apiUrl: String,
        header: HashMap<String, String>,
        body: HashMap<String, String>,
        listener: OnRequestListener,
        method: Int = Request.Method.POST,
        isToast: Boolean = false
    ) {

        var url = apiUrl

        Log.v("Debug", "xxxxx $url")
        Log.v("Debug", "xxxxx header $header")
        Log.v("Debug", "xxxxx body $body")

        // Get Method
        if (method == Request.Method.GET) {
            url = "$apiUrl?"
            for (item in body) {
                url = "$url${item.key}=${item.value}&"
            }
            url = url.substring(0, url.length - 1)
        }

        val volleyEnrollRequest = object : StringRequest(method, url,

            Response.Listener { response ->
                listener.onRequest(
                    response,
                    repositoryResult(url, header.toString(), body.toString(), response, isToast)
                )
            },

            Response.ErrorListener { error ->
                // listener.onRequest("VolleyError", ResourceAPI.error(null))
                listener.onRequest(
                    "VolleyError",
                    repositoryErrorResult(url, header.toString(), body.toString(), error)
                )
            }

        ) {

            override fun getHeaders(): Map<String, String> {
                return header
            }

            override fun getParams(): Map<String, String> {
                return body
            }

        }

        VolleySingleton.getInstance(MyApplication.contextApp).addToRequestQueue(volleyEnrollRequest)
    }

    fun callAPI(
        url: String,
        header: HashMap<String, String>,
        body: HashMap<String, String>,
        dataPart: HashMap<String, DataPart>,
        listener: OnRequestListener,
        method: Int = Request.Method.POST,
        isToast: Boolean = false
    ) {

        Log.v("Debug", "xxxxx $url")
        Log.v("Debug", "xxxxx header $header")
        Log.v("Debug", "xxxxx body $body")
        Log.v("Debug", "xxxxx dataPart $dataPart")

        val volleyEnrollRequest = object : VolleyMultipartRequest(Method.POST, url,

            Response.Listener { response ->
                listener.onRequest(
                    response,
                    repositoryResult(url, header.toString(), body.toString(), response, isToast)
                )
            },

            Response.ErrorListener { error ->
                // listener.onRequest("VolleyError", ResourceAPI.error(null))
                listener.onRequest(
                    "VolleyError",
                    repositoryErrorResult(url, header.toString(), body.toString(), error)
                )
            }

        ) {

            override fun getHeaders(): Map<String, String> {
                return header
            }

            override fun getParams(): Map<String, String> {
                return body
            }

            override fun getByteData(): Map<String, DataPart> {
                return dataPart
            }

        }

        VolleySingleton.getInstance(MyApplication.contextApp).addToRequestQueue(volleyEnrollRequest)
    }


    fun repositoryResult(
        url: String,
        header: String,
        body: String,
        html: String,
        isToast: Boolean
    ): ResourceAPI<Any> {

        Log.v("Debug", "xxxxx $html")


        var result: ResourceAPI<Any> = ResourceAPI.loading(null)

        try {

            result = if (html.isNotEmpty()) {

                val response = Gson().fromJson(html, BaseResponse::class.java)

                if (response.status) { // Success

                    // Success and code 200 do Toast
                    if (response.code == 200) {
                        if (response.msg.isNotEmpty() && isToast) {
                            Toast.makeText(
                                MyApplication.contextApp,
                                response.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    // return success
                    ResourceAPI.success(response)

                } else { // Failed

                    // Token Expired
                    when (response.code) {

                        1001, 1003, 1008 -> {
                            ResourceAPI.tokenExpired(response)
                        }

                        1002, 1006 -> {
                            ResourceAPI.unauthorized(response)
                        }

                        else -> {
//                            MyFirebaseCrashlytics().apiNetworkError(url, header, body, html)
                            ResourceAPI.failed(response)
                        }
                    }

                }

            } else {
//                MyFirebaseCrashlytics().apiNetworkError(url, header, body, html)
                ResourceAPI.error(null)
            }

        } catch (e: Exception) {
//            MyFirebaseCrashlytics().apiNetworkError(url, header, body, html, e)
            result = ResourceAPI.error(null)
            e.printStackTrace()
        }

        // if still anim_loading or do ntg, then return error
        if (result == ResourceAPI.loading(null)) {
//            MyFirebaseCrashlytics().apiNetworkError(url, header, body, html)
            result = ResourceAPI.error(null)
        }

        return result
    }


    fun repositoryErrorResult(
        url: String,
        header: String,
        body: String,
        error: VolleyError
    ): ResourceAPI<Any> {

        var result: ResourceAPI<Any> = ResourceAPI.loading(null)

        try {

            val data = java.lang.String(
                error.networkResponse.data,
                HttpHeaderParser.parseCharset(error.networkResponse.headers)
            )
                .toString()

//            if (!MyApplication.isProduction) {
                Log.v(
                    "Debug",
                    "xxxxx statusCode ${error.networkResponse.statusCode}"
                )
                Log.v("Debug", "xxxxx $data")
//            }

            try {
                result = if (data.isNotEmpty()) {

                    val response = Gson().fromJson(data, BaseResponse::class.java)

                    when (response.code) {
                        401, 1001, 1008 -> {
                            ResourceAPI.tokenExpired(response)
                        }

                        1002, 1006 -> {
                            ResourceAPI.unauthorized(response)
                        }

                        else -> {
//                            MyFirebaseCrashlytics().apiNetworkError(url, header, body, data)
                            ResourceAPI.failed(response)
                        }
                    }

                } else {
//                    MyFirebaseCrashlytics().apiNetworkError(url, header, body, data)
                    ResourceAPI.error(null)
                }

            } catch (e: Exception) {
//                MyFirebaseCrashlytics().apiNetworkError(url, header, body, data, e)
                result = ResourceAPI.networkError(null)
                e.printStackTrace()
            }

        } catch (e: Exception) {

            val message = error.message ?: ""

//            MyFirebaseCrashlytics().apiNetworkError(url, header, body, message, e)
            e.printStackTrace()
            result = ResourceAPI.networkError(null)
        }

        // if still anim_loading or do ntg, then return error
        if (result == ResourceAPI.loading(null)) {
//            MyFirebaseCrashlytics().apiNetworkError(
//                url,
//                header,
//                body,
//                "NetworkManager.kt:repositoryErrorResult().loading"
//            )
            result = ResourceAPI.networkError(null)
        }

        return result
    }

    companion object {
        val instance: NetworkManager by lazy {
            NetworkManager()
        }
    }

}