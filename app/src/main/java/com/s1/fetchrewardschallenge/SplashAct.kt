package com.s1.fetchrewardschallenge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.s1.fetchrewardschallenge.databinding.SplashBinding


class SplashAct : Activity() {


    private val TAG = SplashAct::class.simpleName
    private lateinit var mBinder :SplashBinding
    private val TIME_TO_NEXT_ACT : Long = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI();
        nextAct();
    }

    private fun setupUI(): Unit {
        mBinder = DataBindingUtil.setContentView(this, R.layout.splash)
    }

    private fun nextAct(){
       object: CountDownTimer(TIME_TO_NEXT_ACT,TIME_TO_NEXT_ACT){
           override fun onTick(p0: Long) {
           }

           override fun onFinish() {
               Log.v(TAG,getString(R.string.debug_next_act));
               val launchIntent =  Intent(this@SplashAct, MainAct::class.java)
               startActivity(launchIntent)
               finish()
           }

       }.start()
    }


}