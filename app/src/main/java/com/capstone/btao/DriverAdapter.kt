package com.capstone.btao

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.btao.models.Driver

class DriverAdapter(private var context: Context?, private val mList: List<Driver>) : RecyclerView.Adapter<DriverAdapter.ViewHolder>() {


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_drivers, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        holder.name.text = "${item.firstName} ${item.middleInitial}. ${item.lastName}"
        holder.colorBrandModel.text = "${item.color}/${item.brand}${item.model}"
        holder.licenseNumber.text = item.licenseNumber
        holder.address.text = item.address
        holder.plateNumber.text = item.plateNumber
//        intent.putExtra("categoryId", customize.category.id)
//        intent.putExtra("type", type)
//        context?.startActivity(intent)
        holder.item.setOnClickListener {
            (context as SearchDriverActivity).checkDialog(item.id, holder.name.text.toString())
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView = itemView.findViewById(R.id.tvName)
        val licenseNumber: TextView = itemView.findViewById(R.id.tvLicenseNumber)
        val plateNumber: TextView = itemView.findViewById(R.id.tvPlateNumber)
        val address: TextView = itemView.findViewById(R.id.tvAddress)
        val colorBrandModel: TextView = itemView.findViewById(R.id.tvColorBrandModel)
        val item: LinearLayout = itemView.findViewById(R.id.llItem)
    }

}
