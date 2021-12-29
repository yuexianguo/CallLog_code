package com.phone.base.common

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2020/8/19
 */
class InputTypes {
    companion object {
        val map = mapOf(
                1 to "Built In 0-10V",
                2 to "Built TRIAC/ELV",
                3 to "0-10V 1",
                4 to "0-10V 2",
                5 to "0-10V 3"
        )

        fun getInputName(input: Int): String? {
            return if (map.containsKey(input)) map[input] else ""
        }
    }
}