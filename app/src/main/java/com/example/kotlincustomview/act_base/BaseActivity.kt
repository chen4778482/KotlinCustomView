package com.example.kotlincustomview.act_base

import android.os.Bundle
import android.support.v4.app.FragmentActivity

/**
 * Created by 42224 on 2018/5/16.
 */
abstract class BaseActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var layout: Int = attachLayoutRes()
        if (layout != 0) {
            setContentView(layout)
            initView()
        }
    }

    protected abstract fun attachLayoutRes(): Int
    protected open fun initView() {
    }
}