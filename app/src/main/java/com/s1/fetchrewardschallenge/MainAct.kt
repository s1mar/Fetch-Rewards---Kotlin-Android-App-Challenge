package com.s1.fetchrewardschallenge

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.s1.fetchrewardschallenge.databinding.MainBinding
import com.s1.fetchrewardschallenge.interfaces.ViewModelTarget
import com.s1.fetchrewardschallenge.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainAct : Activity() {

    private val TAG = MainAct::class.simpleName
    private lateinit var mBinder : MainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
        setupViewModel()
    }


    private fun setupUI(){
        mBinder = DataBindingUtil.setContentView(this, R.layout.main)
        mBinder.actionBar.btnBack.setOnClickListener { onBackPressed() }
    }

    private fun setupViewModel(){
       val vm = MainViewModel(object : ViewModelTarget{
            override fun processState(state: ViewModelTarget.ActivityStates) {
                if(state == ViewModelTarget.ActivityStates.HAS_DATA){
                    stateHasdata(true)
                }else if(state == ViewModelTarget.ActivityStates.NO_DATA){
                    stateHasdata(false)
                }else if(state == ViewModelTarget.ActivityStates.MAKING_API_CALL){
                    stateLoading(true)
                }
            }
        })

        vm.setAdapter(mBinder)
        vm.startOps()
    }

    private fun stateLoading(isLoading : Boolean) {
        GlobalScope.launch(Dispatchers.Main) {
            mBinder.av2.visibility = if (isLoading) View.VISIBLE else View.GONE

        }
    }

    private fun stateHasdata(hasData: Boolean){
        GlobalScope.launch(Dispatchers.Main)
        {
            stateLoading(false)
            mBinder.rycItems.visibility = if (hasData) View.VISIBLE else View.GONE
            mBinder.animationView.visibility = if (hasData) View.GONE else View.VISIBLE
        }
    }

}