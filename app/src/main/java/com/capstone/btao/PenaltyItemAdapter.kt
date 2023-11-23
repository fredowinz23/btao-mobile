package com.capstone.btao

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.btao.models.Driver
import com.capstone.btao.models.PenaltyItem
import com.capstone.btao.models.Violation

class PenaltyItemAdapter(private var context: Context?, private val mList: List<PenaltyItem>) : RecyclerView.Adapter<PenaltyItemAdapter.ViewHolder>() {


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_my_violation, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        holder.violationItem.text = item.violation!!.name
//        intent.putExtra("categoryId", customize.category.id)
//        intent.putExtra("type", type)
//        context?.startActivity(intent)
        holder.item.setOnClickListener {
            (context as ViolationFormActivity).removePenaltyItem(item.id)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val violationItem: TextView = itemView.findViewById(R.id.tvViolationItem)
        val item: LinearLayout = itemView.findViewById(R.id.llItem)
    }

}
