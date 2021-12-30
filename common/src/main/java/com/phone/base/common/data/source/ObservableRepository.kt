package com.phone.base.common.data.source

import com.google.gson.JsonObject
import com.phone.base.common.*
import com.phone.base.common.network.ApiFactory
import io.reactivex.Observable
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * description :
 * author :
 * email : @waclighting.com.cn
 * data : 2021/3/19
 */
object ObservableRepository {

    fun getFixtureObservable(
        params: JsonObject,
        forceLocal: Boolean = false,
        timeoutTime: Long = MQTT_REQUEST_TIMEOUT
    ): Observable<JsonObject> {
        val requestBody = params.toString().toRequestBody(BaseDataSource.MEDIA_TYPE_JSON)
        val observable: Observable<JsonObject>
        if (params.has("action") && params.get("action").asString.equals(RequestActions.FIXTURE_DELETE)) {
            observable = ApiFactory.getControllerLongTimeOut()
                .sendActionToFixture(requestBody)
        } else if (params.get("action").asInt == RequestActions.FIXTURE_LIST) {
            observable = ApiFactory.getStrutController()
                .sendActionToFixture(requestBody)
                .retry(4)
        } else {
            observable = ApiFactory.getStrutController()
                .sendActionToFixture(requestBody)
                .retry(2)
        }
        return observable
    }


}