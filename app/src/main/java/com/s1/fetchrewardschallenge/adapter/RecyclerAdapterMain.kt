package com.s1.fetchrewardschallenge.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s1.fetchrewardschallenge.R
import com.s1.fetchrewardschallenge.data.Item
import com.s1.fetchrewardschallenge.databinding.ItemBinding
import com.s1.fetchrewardschallenge.databinding.ItemListIdBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecyclerAdapterMain : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val dataSet : ArrayList<Item> = mutableListOf<Item>() as ArrayList<Item>

    private var lastProcessedMap : MutableMap<Int,Int> = mutableMapOf()

    companion object VIEW_TYPES{
        const val HEADER_TYPE = 1
        const val BODY_TYPE = 2
    }


    @SuppressLint("NotifyDataSetChanged")
    public fun updateDataSet(dataSet: List<Item>?){
        if(dataSet!=null){
            var dataSetSorted = dataSet.filter { item: Item ->  !item.name.isNullOrBlank()}
            dataSetSorted = dataSetSorted.sortedWith(compareBy(Item::listId,Item::name))
            this.dataSet.clear()
            this.dataSet.addAll(dataSetSorted)

            GlobalScope.launch(Dispatchers.Main) {
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val listID = dataSet[position].listId
        if(lastProcessedMap.containsKey(listID)){
            val pos = lastProcessedMap[listID]
            return if (pos==position) HEADER_TYPE else BODY_TYPE
        }
        lastProcessedMap[listID] = position
        return BODY_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater  = LayoutInflater.from(parent.context)
        val vh = when(viewType){
            HEADER_TYPE-> ViewHolder_ListItem(layoutInflater.inflate(R.layout.item_list_id,parent,false))
            else -> ViewHolder_Item(layoutInflater.inflate(R.layout.item,parent,false))
        }
        return vh
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dataItem = dataSet[position]
        if(holder is ViewHolder_ListItem){
            holder.binder.txtLstId.setText("""List ID: ${dataItem.listId}""")
        }else if(holder is ViewHolder_Item){
            holder.binder.txtId.setText(dataItem.id.toString())
            holder.binder.txtName.setText(dataItem.name.toString())
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class ViewHolder_ListItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binder : ItemListIdBinding = ItemListIdBinding.bind(itemView)
    }

    inner class ViewHolder_Item (itemView: View) : RecyclerView.ViewHolder(itemView){
        var binder : ItemBinding = ItemBinding.bind(itemView)

    }
}