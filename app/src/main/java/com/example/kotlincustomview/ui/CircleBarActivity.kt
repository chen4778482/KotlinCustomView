package com.example.kotlincustomview.ui

import com.example.kotlincustomview.R
import com.example.kotlincustomview.act_base.BaseActivity
import kotlinx.android.synthetic.main.act_circle.*

/**
 * Created by 42224 on 2018/5/17.
 */
class CircleBarActivity : BaseActivity() {
    override fun attachLayoutRes(): Int {
        return R.layout.act_circle
    }

    override fun initView() {
        weightCB.update(70, 500)
        stepsCB.update(80, 800)
        caloriesCB.update(90, 1000)
        mCircleBarView
    }
}