package com.example.kotlincustomview.ui

import android.view.View
import com.example.kotlincustomview.R
import com.example.kotlincustomview.act_base.BaseActivity
import kotlinx.android.synthetic.main.avt_ripple.*

/**
 * Created by 42224 on 2018/5/16.
 */
class RippleViewActivity : BaseActivity() {
    override fun attachLayoutRes(): Int {
        return R.layout.avt_ripple
    }

    override fun initView() {
        mRippleView.startAnimation()
    }
}