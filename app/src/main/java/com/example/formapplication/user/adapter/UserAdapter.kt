package com.example.formapplication.user.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.formapplication.data.db.entity.UserEntity
import kotlinx.android.synthetic.main.layout_user.view.*
import com.example.formapplication.R
import com.example.formapplication.user.view.AllUserActivity
import com.example.formapplication.user.viewmodel.UserViewModel

/**
 * UserAdapter class is an adapter class for RecyclerView elements
 */
class UserAdapter(private var userList: List<UserEntity>, private val userViewModel: UserViewModel, private val context: Context) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_user, parent, false) as View

        return UserViewHolder(view)
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        if (userList.isNotEmpty()) {
            if (!userList[position].cust_id.equals("")) {
                holder.textFirstLetter.text = userList[position].cust_type[0].toString().toUpperCase()
                holder.textCustId.text = userList[position].cust_id.toString()
                holder.textName.text = userList[position].cust_name
                holder.textAddress.text = userList[position].cust_add
                holder.textPhone.text = userList[position].cust_phn
                holder.textType.text = userList[position].cust_type
            }
        }

        holder.itemView.setOnLongClickListener { itemView ->
            setPopupMenu(itemView, holder, position, context as AllUserActivity)
        }
    }

    // setting popup menu on long pressing each items of the list
    private fun setPopupMenu(
        itemView: View,
        holder: UserViewHolder,
        position: Int,
        context: AllUserActivity
    ): Boolean {

        val popupMenu = PopupMenu(itemView.context, itemView)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_individual -> {
                    holder.textFirstLetter.text = "I"
                    holder.textType.text = itemView.context.resources.getString(R.string.individual)
                    userList[position].cust_type = itemView.context.resources.getString(R.string.individual)

                    //for updating the particular data in database for which the value has been updated
                    userViewModel.updateUserDetails(userList[position])

                    //calling the getUserList() method of AllUserActivity for updating list
                    if (true) {
                        context.getUserList()
                    }
                    true
                }
                R.id.menu_group -> {
                    holder.textFirstLetter.text = "G"
                    holder.textType.text = itemView.context.resources.getString(R.string.group)
                    userList[position].cust_type = itemView.context.resources.getString(R.string.group)
                    userViewModel.updateUserDetails(userList[position])

                    if (true) {
                        context.getUserList()
                    }
                    true
                }
                else -> false
            }
        }

        popupMenu.inflate(R.menu.menu_main)
        popupMenu.show()
        return true
    }

    /**
     * UserViewHolder class is an inner class that acts as a holder for the RecyclerView elements
     */
    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textFirstLetter = view.text_first_letter!!
        val textCustId = view.text_cust_id!!
        val textName = view.text_cust_name!!
        val textAddress = view.text_address!!
        val textPhone = view.text_phn!!
        val textType = view.text_type!!
    }
}