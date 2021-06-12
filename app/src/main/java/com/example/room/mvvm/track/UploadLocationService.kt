package com.example.room.mvvm.track

import android.app.IntentService
import android.content.Intent
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.room.mvvm.viewmodel.LoginViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.net.MalformedURLException
import java.net.URL
import java.util.*

/**
 * Created by Marty on 12/20/2017.
 */
class UploadLocationService : IntentService {
    var points: ArrayList<Location>? = null

    constructor() : super("UploadLocationService") {}
    constructor(name: String?) : super(name) {}

    override fun onHandleIntent(intent: Intent?) {

        if (intent != null) {
            val b = intent.extras
            if (b != null) {
                points = intent.getParcelableArrayListExtra("points")
                val url: URL
                try {
                    url = URL("http://localhost:8000/trackdrivertrip/update") // set your server url
                    sendLocation(url, "")
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun sendLocation(url: URL, token: String) {
        try {
            val jsonResp: String?
            var code = 0
            val jsonObject = JSONObject()
            jsonObject.put("id", "userid")
            val pointsArray = JSONArray()
            for (i in points!!.indices) {
                pointsArray.put(JSONArray().put(points!![i].longitude).put(points!![i].latitude))
            }
            jsonObject.put("coordinates", pointsArray)
            Log.d("data sent", jsonObject.toString())
            if (pointsArray.length() != 0) {
                jsonResp = ServiceCall.doServerCall("POST", url, pointsArray.toString(), token)
                if (jsonResp != null && jsonResp == ServiceCall.TIME_OUT) {
                    code = 500
                    return
                } else if (jsonResp == null) {
                    code = 500
                    return
                }
                //                Log.d("resp", jsonResp);
//                JSONObject json = new JSONObject(jsonResp);
//                code = Integer.parseInt(json.getString("code"));
            } else {
                code = 100
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}