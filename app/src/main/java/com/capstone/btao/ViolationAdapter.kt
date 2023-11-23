package com.capstone.btao

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.btao.models.Driver
import com.capstone.btao.models.Violation

class ViolationAdapter(private var context: Context?, private val mList: List<Violation>) : RecyclerView.Adapter<ViolationAdapter.ViewHolder>() {


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_violation_option, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        holder.name.text = item.name
//        intent.putExtra("categoryId", customize.category.id)
//        intent.putExtra("type", type)
//        context?.startActivity(intent)
        holder.item.setOnClickListener {
            (context as ViolationFormActivity).addToPenaltyItem(item.id)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView = itemView.findViewById(R.id.tvName)
        val item: CardView = itemView.findViewById(R.id.cvItem)
    }

}
