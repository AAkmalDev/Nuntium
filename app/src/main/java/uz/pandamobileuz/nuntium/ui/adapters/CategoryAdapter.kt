package uz.pandamobileuz.nuntium.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.pandamobileuz.nuntium.data.models.Category
import uz.pandamobileuz.nuntium.databinding.ItemCategoryBinding
import uz.pandamobileuz.nuntium.databinding.ItemInnerCatergoryBinding
import uz.pandamobileuz.nuntium.utils.SingleBlock

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.VHolderMain>() {

    private val categoryList = ArrayList<Category>()
    private var listener: SingleBlock<Category>? = null

    inner class VHolderMain(val itemInnerBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemInnerBinding.root) {
        fun bind(category: Category) {
            itemInnerBinding.apply {
                categoryName.text = category.categoryName

                itemView.setOnClickListener {
                    listener!!.invoke(category)
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