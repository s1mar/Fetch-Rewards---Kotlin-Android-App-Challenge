package com.s1.fetchrewardschallenge.viewmodels

import com.s1.fetchrewardschallenge.adapter.RecyclerAdapterMain
import com.s1.fetchrewardschallenge.data.Item
import com.s1.fetchrewardschallenge.databinding.MainBinding
import com.s1.fetchrewardschallenge.interfaces.ViewModelTarget
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



class MainViewModel(val target : ViewModelTarget?){

    private var adapter : RecyclerAdapterMain = RecyclerAdapterMain()
    private var adapterBound = false

    fun startOps() {

        //check to see if the list adapter is bound or not
        if(!adapterBound){
            throw Exception("Please bind the adapter to the recycler before continuing")
        }
        target?.processState(state = ViewModelTarget.ActivityStates.MAKING_API_CALL)
        GlobalScope.launch(Dispatchers.IO)
        {
            fetchData()
        }

    }

    fun setAdapter(binding: MainBinding){
        adapterBound = true
        binding.rycItems.adapter = adapter
    }

    private fun fetchData(){
         val listItems = Item.getListItems() ?: listOf<Item>()
         adapter.updateDataSet(listItems)

        if(listItems.isEmpty()){
             target?.processState(ViewModelTarget.ActivityStates.NO_DATA)
         }else{
             target?.processState(ViewModelTarget.ActivityStates.HAS_DATA)
         }
    }

}