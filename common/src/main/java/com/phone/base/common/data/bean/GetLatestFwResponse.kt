package com.phone.base.common.data.bean

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2020/12/30
 */

data class GetLatestFwResponse (
        val iotm:StrutComVersBean,
        val scm:StrutComVersBean,
        val dcm:StrutDcmBean,
        val sensor:StrutComVersBean,
        val dmx:StrutComVersBean,
        val motor:StrutComVersBean?
)