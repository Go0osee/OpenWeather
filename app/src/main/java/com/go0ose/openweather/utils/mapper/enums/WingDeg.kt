package com.go0ose.openweather.utils.mapper.enums

import androidx.annotation.StringRes
import com.go0ose.openweather.R

enum class WingDeg(
    @StringRes
    val directionId: Int
) {
    NE(R.string.ne),
    E(R.string.e),
    SE(R.string.se),
    S(R.string.s),
    SW(R.string.sw),
    W(R.string.w),
    NW(R.string.nw),
    N(R.string.n)
}