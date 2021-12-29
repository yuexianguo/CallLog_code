package com.phone.base.common.data.source

import com.google.gson.JsonObject
import com.phone.base.common.CustomTypes.AxisType
import com.phone.base.common.data.bean.*
import com.phone.base.common.data.source.BaseDataSource.OTAHeaderModule
import com.phone.base.common.lamdaFunction.bean.GetLocationResponse
import com.phone.base.common.lamdaFunction.bean.LocationBody
import com.phone.base.common.lamdaFunction.bean.SearchLocationResponse
import com.phone.base.common.realmObject.AutomationShadow
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.io.File
import java.io.InputStream

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2020/4/23
 */
interface StrutDataSource : BaseDataSource {
    fun getFixtureList(action: Int, success: Consumer<in FixtureAddressesResponse?>, error: Consumer<in Throwable?>): Disposable

}