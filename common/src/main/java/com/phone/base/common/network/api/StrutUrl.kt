package com.phone.base.common.network.api

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2020/10/15
 */

const val URL_CODE_FORMAT = "https://strut-waclighting.auth.us-east-1.amazoncognito.com/oauth2/authorize?identity_provider=%s&scope=openid&client_id=4u9v7h7i6a87q9q4ajls82drgk&response_type=code&scope=email+openid+profile&redirect_uri=https://api.smartthings.com/oauth/callback"
const val URL_OBTAIN_TOKEN_FORMAT = "https://strut-waclighting.auth.us-east-1.amazoncognito.com/oauth2/token?grant_type=authorization_code&client_id=4u9v7h7i6a87q9q4ajls82drgk&redirect_uri=https://api.smartthings.com/oauth/callback&code=%s"
const val URL_REFRESH_TOKEN_FORMAT = "https://strut-waclighting.auth.us-east-1.amazoncognito.com/oauth2/token?grant_type=refresh_token&client_id=4u9v7h7i6a87q9q4ajls82drgk&redirect_uri=https://api.smartthings.com/oauth/callback&refresh_token=%s"

const val TOKEN_URL_AUTHORIZATION = "Basic NHU5djdoN2k2YTg3cTlxNGFqbHM4MmRyZ2s6dnZxZDNrYTViMTBkcWpub2x2b3RmOWppcG9hZzY5OTVzdGd1OTYwajQybjVpY3ZpODhn"
const val TOKEN_URL_CONTENT_TYPE = "application/x-www-form-urlencoded"