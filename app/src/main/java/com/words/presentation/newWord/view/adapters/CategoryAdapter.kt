package com.words.presentation.newWord.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.words.R
import com.words.domain.category.model.CategoryEntity
import com.words.presentation.newWord.view.customview.CircleView


class CategoriesDropDownAdapter(
    context: Context,
    var dataSource: List<CategoryEntity>,
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_categories_dropdown, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }

        vh.label.text = dataSource[position].title
        vh.colorView.setImageDrawable(CircleView(dataSource[position].colorHex.toInt(), 10.0f));
        return view
    }

    override fun getItem(position: Int): CategoryEntity {
        return dataSource[position];

    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val label: TextView
        val colorView: ImageView

        init {
            colorView = row?.findViewById(R.id.viewColor) as ImageView
            label = row.findViewById(R.id.textViewTitle) as TextView
        }
    }
}