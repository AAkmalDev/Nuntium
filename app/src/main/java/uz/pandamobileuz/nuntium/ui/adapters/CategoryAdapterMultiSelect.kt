package uz.pandamobileuz.nuntium.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.pandamobileuz.nuntium.R
import uz.pandamobileuz.nuntium.data.models.Category
import uz.pandamobileuz.nuntium.databinding.ItemCategoryBinding
import uz.pandamobileuz.nuntium.utils.SingleBlock

class CategoryAdapterMultiSelect : RecyclerView.Adapter<CategoryAdapterMultiSelect.VHolderMain>() {

    private val categoryList = ArrayList<Category>()
    private var listener: SingleBlock<Category>? = null
    private var lastPosition = -1


    inner class VHolderMain(val itemInnerBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemInnerBinding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(category: Category) {
            itemInnerBinding.apply {
                categoryName.text = category.categoryName
                if (category.isSelected){
                    categoryName.setTextColor(Color.parseColor("#FFFFFF"))
                    layoutCategory.setBackgroundResource(R.drawable.category_select_back)
                }else{
                    categoryName.setTextColor(Color.parseColor("#666C8E"))
                    layoutCategory.setBackgroundResource(R.drawable.category_unselect_back)
                }
                itemView.setOnClickListener {
                    category.isSelected = !category.isSelected
                    lastPosition = bindingAdapterPosition
                    listener!!.invoke(category)
                    notifyDataSetChanged()
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VHolderMain(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))

    override fun onBindViewHolder(holder: VHolderMain, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int = categoryList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Category>) {
        categoryList.clear()
        categoryList.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnClickListener(block: SingleBlock<Category>){
        listener = block
    }


}