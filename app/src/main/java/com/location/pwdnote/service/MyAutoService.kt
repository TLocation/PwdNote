package com.location.pwdnote.service

import android.os.Build
import android.os.CancellationSignal
import android.service.autofill.*
import androidx.annotation.RequiresApi

/**
 *
 * @author tianxiaolong
 * time：2020/3/28 17:11
 * description：
 */
@RequiresApi(Build.VERSION_CODES.O)
class MyAutoService : AutofillService() {
    override fun onFillRequest(request: FillRequest,
        cancellationSignal: CancellationSignal, callback: FillCallback
    ) {
    }

    override fun onSaveRequest(request: SaveRequest, callback: SaveCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}