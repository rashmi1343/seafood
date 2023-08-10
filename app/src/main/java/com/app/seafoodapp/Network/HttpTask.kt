package com.app.seafoodapp.Network

import android.os.AsyncTask
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class HttpTask(callback: (String?) -> Unit) : AsyncTask<String, Unit, String>()  {

    var callback = callback
    val TIMEOUT = 30*1000




    override fun doInBackground(vararg params: String): String? {
        val url = URL(params[1])
        val httpClient = url.openConnection() as HttpURLConnection
        httpClient.setReadTimeout(TIMEOUT)
        httpClient.setConnectTimeout(TIMEOUT)
        httpClient.requestMethod = params[0]




           // httpClient.setRequestProperty("Authorization", "Bearer " + Constant.accesstoken)

        if (params[0] == "POST") {
            httpClient.instanceFollowRedirects = false
            httpClient.doOutput = true
            httpClient.doInput = true
            httpClient.useCaches = false
            httpClient.setRequestProperty("Content-Type", "application/json")
          //  httpClient.setRequestProperty("Accept", "application/json");
           // httpClient.setRequestProperty("charset", "utf-8")
           // httpClient.setRequestProperty("Content-length", params[2].size.toString())
            httpClient.setRequestProperty("authorizationkey", "20A0751C-9FEE-47F8-A6A9-335BE39")
            httpClient.setRequestProperty("keypassword", "aW5kaWFuc2VhZm9vZEAyMDIwIQ")

        }
        try {
            if (params[0] == "POST") {
                httpClient.connect()
                val os = httpClient.getOutputStream()
                val writer = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
                writer.write(params[2])
                writer.flush()
                writer.close()
                os.close()
            }

             if (httpClient.responseCode == HttpURLConnection.HTTP_OK || httpClient.responseCode ==HttpURLConnection.HTTP_CREATED) {
                val stream = BufferedInputStream(httpClient.inputStream)
                val data: String = readStream(inputStream = stream)
                 val map = httpClient.getHeaderFields()


                return data
            }

             else {
                println("ERROR ${httpClient.responseCode}")
            }



        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            httpClient.disconnect()
        }

        return null
    }

    fun readStream(inputStream: BufferedInputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.forEachLine { stringBuilder.append(it) }
        return stringBuilder.toString()
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        callback(result)
    }
}
