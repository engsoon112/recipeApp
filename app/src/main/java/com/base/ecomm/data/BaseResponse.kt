package com.base.ecomm.data


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


open class BaseResponse : Serializable {

    @SerializedName("code")
    @Expose
    var code: Int = -1

    @SerializedName("msg")
    @Expose
    var msg: String = ""

    @SerializedName("status")
    @Expose
    var status: Boolean = false


}