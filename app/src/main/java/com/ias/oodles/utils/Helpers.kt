package com.ias.oodles.utils

import android.R
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleOwner
import androidx.work.*
import com.ias.oodles.downloadfileswithworkmanager.*
import java.io.File
import java.util.*


class Helpers {

    companion object {
        fun downloadPdf(context: Context, url: String?, title: String?) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

            /*  val format = "https://drive.google.com/viewerng/viewer?embedded=true&url=%s"
            val fullPath: String = java.lang.String.format(Locale.ENGLISH, format, Uri.parse(url))
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(fullPath))
            context.startActivity(browserIntent)*/
        }

        fun downloadPdfOld(context: Context, url: String?, title: String?): Long {
            if (url == null || url == "") {
                Toast.makeText(context, "PDF not found!", Toast.LENGTH_SHORT).show()
                return 0
            }
            val direct = File(Environment.getExternalStoragePublicDirectory("Download").toString())

            if (!direct.exists()) {
                direct.mkdirs()
            }
            val extension = url?.substring(url.lastIndexOf("."))
            val downloadReference: Long
            var dm: DownloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val uri = Uri.parse(url)
            val request = DownloadManager.Request(uri)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                title + "pdf" + System.currentTimeMillis() + extension
            )
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            request.setTitle(title)
            Toast.makeText(context, "Downloading..", Toast.LENGTH_SHORT).show()

            downloadReference = dm?.enqueue(request) ?: 0

            return downloadReference

        }

        @SuppressLint("SetJavaScriptEnabled")
        fun setWebViewText(web_view: WebView, url: String) {
            web_view.requestFocus()
            web_view.settings.lightTouchEnabled = true
            web_view.settings.javaScriptEnabled = true
            web_view.run { settings.setGeolocationEnabled(true) }
            web_view.isSoundEffectsEnabled = true
            web_view.loadData(url, "text/html", "UTF-8")
        }


        fun startDownloadingFile(
            context: Context,
            url: String,
            fileName: String,
            type: String,
            workManager: WorkManager,
            lifecycleOwner: LifecycleOwner
        ) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresStorageNotLow(true)
                .setRequiresBatteryNotLow(true)
                .build()
            val data = Data.Builder()

            data.apply {
                putString(KEY_FILE_NAME, fileName)
                putString(KEY_FILE_URL, url)
                putString(KEY_FILE_TYPE, type)
            }

            val oneTimeWorkRequest = OneTimeWorkRequest.Builder(FileDownloadWorker::class.java)
                .setConstraints(constraints)
                .setInputData(data.build())
                .build()
            Toast.makeText(context, "Downloading..", Toast.LENGTH_SHORT).show()

            workManager.enqueue(oneTimeWorkRequest)

            workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
                .observe(lifecycleOwner) { info ->
                    info?.let {
                        when (it.state) {
                            WorkInfo.State.SUCCEEDED -> {
                                val uri = it.outputData.getString(KEY_FILE_URI)
                                uri?.let {
                                    /* val intent = Intent(Intent.ACTION_VIEW)
                                 intent.setDataAndType(uri.toUri(), "application/pdf")
                                 intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                 try {
                                     startActivity(intent)
                                 } catch (e: ActivityNotFoundException) {
                                     Toast.makeText(
                                         requireActivity(),
                                         "Can't open Pdf",
                                         Toast.LENGTH_SHORT
                                     ).show()
                                 }*/
                                }
                            }
                            WorkInfo.State.FAILED -> {
                                //  Toast.makeText(requireActivity(),"Download in failed",Toast.LENGTH_SHORT).show()
                                //  btnOpenFile.text = "Download in failed"
                            }
                            WorkInfo.State.RUNNING -> {
                                //  btnOpenFile.text = "Download in progress.."
                            }
                            else -> {

                            }
                        }
                    }
                }
        }

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val nw = connectivityManager.activeNetwork ?: return false
                val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
                return when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    //for other device how are able to connect with Ethernet
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    //for check internet over Bluetooth
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                    else -> false
                }
            } else {
                return connectivityManager.activeNetworkInfo?.isConnected ?: false
            }
        }
    }



}