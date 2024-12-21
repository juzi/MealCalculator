package at.zimmercarral.mealcalc.data.sync

import android.content.Context
import android.net.ConnectivityManager

class NetworkManager(private val context: Context) {

    fun isInHomeNetwork(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                ?: return false

        val network = connectivityManager.activeNetwork ?: return false
        val linkProperties = connectivityManager.getLinkProperties(network) ?: return false

        return linkProperties.linkAddresses.any { linkAddress ->
            linkAddress.address.hostAddress?.contains("192.168.0.") == true
        }
    }
}