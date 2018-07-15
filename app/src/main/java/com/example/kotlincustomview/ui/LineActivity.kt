package com.example.kotlincustomview.ui

import com.example.kotlincustomview.R
import com.example.kotlincustomview.act_base.BaseActivity
import kotlinx.android.synthetic.main.act_line.*

/**
 * Created by 42224 on 2018/6/28.
 */

class LineActivity : BaseActivity() {

    override fun attachLayoutRes(): Int {
        return R.layout.act_line
    }

    override fun initView() {
        mYuanJiaoHeng.setColor(R.color.color_ff23252d)
        mYuanJiaoHeng.setText("11")
        mYuanJiaoHeng.setChangDu("32".toFloat(), "32".toFloat())
        mYuanJiaoHeng.update(1000)


        mYuanJiaoHeng2.setColor(R.color.color_ffd0423d)
        mYuanJiaoHeng2.setText("22")
        mYuanJiaoHeng2.setChangDu("2".toFloat(), "31".toFloat())
        mYuanJiaoHeng2.update(1000)

        mYuanJiaoHeng3.setColor(R.color.colorAccent)
        mYuanJiaoHeng3.setText("33")
        mYuanJiaoHeng3.setChangDu("1".toFloat(), "31".toFloat())
        mYuanJiaoHeng3.update(1000)
    }

}
