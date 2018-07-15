package com.example.kotlincustomview.ui

import android.content.Intent
import android.view.View
import com.example.kotlincustomview.R

import com.example.kotlincustomview.act_base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun attachLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        miam_RippleView.setOnClickListener {
            var intent = Intent(this@MainActivity, RippleViewActivity::class.java)
            startActivity(intent)
        }
        miam_CircleBar.setOnClickListener {
            var intent = Intent(this@MainActivity, CircleBarActivity::class.java)
            startActivity(intent)
        }
        miam_CircleBar1.setOnClickListener{
            var intent = Intent(this@MainActivity, LineActivity::class.java)
            startActivity(intent)
        }
    }

}
