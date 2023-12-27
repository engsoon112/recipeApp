package com.base.ecomm.utils

import android.content.Context
import android.os.Build
import android.telephony.PhoneNumberUtils
import android.telephony.TelephonyManager
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.base.ecomm.MyApplication

class PhoneUtil {

    fun phoneValidate(phoneNumber: String): Boolean {
        // return (android.util.Patterns.PHONE.matcher(phoneNumber).matches())
        return phoneNumber.length > 3
    }

    fun phoneClean(number: String): String {
        return number.replace("+", "").replace("-", "").replace(" ", "").replace("(", "").replace(")", "")
    }

    fun e164NumberValidate(countryCode: String, phoneNumber: String): Boolean {
        var phone: String
        var mPhoneNumber: String =
            phoneNumber.replace("+", "").replace("-", "").replace(" ", "").replace("(", "").replace(")", "")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                phone = PhoneNumberUtils.formatNumberToE164(mPhoneNumber, countryCode)
            } catch (e: Exception) {
                try {
                    if (!phoneNumber.startsWith("+")) {
                        phone = PhoneNumberUtils.formatNumberToE164("+$phoneNumber", countryCode)
                    } else {
                        return false
                    }
                } catch (e2: Exception) {
                    return false
                }
            }

        } else {
            // if lower Android version
            return true
        }
        return !phone.isNullOrEmpty()
    }

    fun e164NumberFormat(countryCode: String, dialCode: String, phoneNumber: String): String {

        val mPhoneNumber: String =
            phoneNumber.replace("+", "").replace("-", "").replace(" ", "").replace("(", "").replace(")", "")

        val mDialCode: String = dialCode.replace("+", "").replace("-", "").replace(" ", "").replace("(", "").replace(")", "")

        val mDialCodePhoneNumber = if (!mPhoneNumber.startsWith(mDialCode)) {
            "$mDialCode$mPhoneNumber"
        } else {
            mPhoneNumber
        }

        if (countryCode.isNullOrEmpty() || mPhoneNumber.isNullOrEmpty()) {
            return mPhoneNumber
        }

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                val result = PhoneNumberUtils.formatNumberToE164(mPhoneNumber, countryCode)
                if (result.isNullOrEmpty()) {
                    mDialCodePhoneNumber
                } else {
                    result
                }
            } catch (e: Exception) {
                mDialCodePhoneNumber
            }

        } else {
            // if lower Android version
            return mDialCodePhoneNumber
        }
    }


    fun setPhoneCountry(phoneCountry: String) {
        MemoryUtil().saveString("PhoneCountry", phoneCountry)
    }

    fun getPhoneCountry(): String {
        val phoneCountry = MemoryUtil().loadString("PhoneCountry")
        return if (phoneCountry.isNotEmpty()) phoneCountry else "MY"
    }

    fun setPhoneDialCode(phoneDialCode: String) {
        MemoryUtil().saveString("PhoneDialCode", phoneDialCode)
    }

    fun getPhoneDialCode(): String {
        val phoneDialCode = MemoryUtil().loadString("PhoneDialCode")
        return if (phoneDialCode.isNotEmpty()) phoneDialCode
        else "+${PhoneNumberUtil.getInstance().getCountryCodeForRegion("MY")}"
    }


    private fun getDefaultCountry(): String {
        try {
            val tm = MyApplication.contextApp.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val simCountry = tm.simCountryIso
            if (simCountry != null && simCountry.length == 2) {
                // SIM country code is available
                return simCountry.toUpperCase()
            } else if (tm.phoneType != TelephonyManager.PHONE_TYPE_CDMA) {
                // device is not 3G (would be unreliable)
                val networkCountry = tm.networkCountryIso
                if (networkCountry != null && networkCountry.length == 2) {
                    // network country code is available
                    return networkCountry.toUpperCase()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return "MY"
        }
        return "MY"
    }

}