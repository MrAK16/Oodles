package com.ias.gsscore.ui.activity

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import butterknife.ButterKnife
import com.ias.gsscore.R
import com.ias.gsscore.ui.fragment.CourseTestFragment
import com.ias.gsscore.ui.fragment.CourseDetailsFragment
import com.ias.gsscore.utils.SingletonClass

class ContainerActivity : BaseActivity(), Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        ButterKnife.bind(this)
        hideFooter(true)
        if (intent.getStringExtra("whereFrom") == "viewAllBatches") {
            addFragment(
                CourseTestFragment(this, "Upcoming Batches", false, "", this), this
            )
            setToolBar("All Batches")
            SingletonClass.instance.setContainerManager(supportFragmentManager)
        } else {
            setToolBar("IAS Foundation 2023")
            addFragment(
                CourseDetailsFragment(), this
            )
        }

    }

    companion object {
        fun addFragment(fragment: Fragment?, requireActivity: FragmentActivity) {
            if (fragment == null) return
            requireActivity.supportFragmentManager.beginTransaction()
                .replace(R.id.rootContainer, fragment)
                .commitAllowingStateLoss()
        }
    }

    override fun selectPDF() {
        selectPdf()
    }

    private fun selectPdf() {
        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "application/pdf"
        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(pdfIntent, 12)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // For loading PDF
        when (requestCode) {
            12 -> if (resultCode == RESULT_OK) {

                //         pdfUri = data?.data!!
                val uri: Uri = data?.data!!
                val uriString: String = uri.toString()
                var pdfName: String? = null
                if (uriString.startsWith("content://")) {
                    var myCursor: Cursor? = null
                    try {
                        myCursor =
                            applicationContext!!.contentResolver.query(uri, null, null, null, null)
                        if (myCursor != null && myCursor.moveToFirst()) {
                            pdfName =
                                myCursor.getString(myCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            Log.d("***** ContainerActivity >>>", "" + pdfName)
                            //         pdfTextView.text = pdfName
                        }
                    } finally {
                        myCursor?.close()
                    }
                }
            }
        }
    }
}

interface Listener {
    fun selectPDF()
}