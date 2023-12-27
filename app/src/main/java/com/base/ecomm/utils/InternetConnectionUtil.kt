package com.base.ecomm.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import java.net.NetworkInterface
import java.util.*


class InternetConnectionUtil(internal var context: Context) {

    val isConnected: Boolean
        get() {
            return try {
                val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                cm.activeNetworkInfo != null
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

    val networkInfoType: String
        get() {
            return try {
                val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                cm.activeNetworkInfo?.typeName.toString()
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }

    val wifiSSID: String
        get() {
            return try {
                val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                var ssid = wifiManager.connectionInfo.ssid

                if (ssid.startsWith("\"")) {
                    ssid = ssid.substring(1, ssid.length)
                }

                if (ssid.endsWith("\"")) {
                    ssid = ssid.substring(0, ssid.length - 1)
                }

                ssid
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }

    val wifiBSSID: String
        get() {
            return try {
                val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                var bssid = wifiManager.connectionInfo.bssid

                if (bssid.startsWith("\"")) {
                    bssid = bssid.substring(1, bssid.length)
                }

                if (bssid.endsWith("\"")) {
                    bssid = bssid.substring(0, bssid.length - 1)
                }

                // if not location
                // 02:00:00:00:00:00

                bssid
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }

    val macAddress: String
        get() {
            try {
                val all = Collections.list(NetworkInterface.getNetworkInterfaces())
                for (nif in all) {
                    if (!nif.name.equals("wlan0", ignoreCase = true)) continue

                    val macBytes = nif.hardwareAddress ?: return ""

                    val res1 = StringBuilder()
                    for (b in macBytes) {
                        res1.append(String.format("%02X:", b))
                    }

                    if (res1.isNotEmpty()) {
                        res1.deleteCharAt(res1.length - 1)
                    }

                    return res1.toString()
                }
            } catch (ex: Exception) {
                return ""
            }
            return ""
        }

}
