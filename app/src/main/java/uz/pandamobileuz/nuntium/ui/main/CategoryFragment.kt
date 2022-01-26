package uz.pandamobileuz.nuntium.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.pandamobileuz.nuntium.R
import uz.pandamobileuz.nuntium.data.models.Category
import uz.pandamobileuz.nuntium.databinding.FragmentCategoryBinding
import uz.pandamobileuz.nuntium.ui.MainActivity
import uz.pandamobileuz.nuntium.ui.adapters.CategoryAdapter
import uz.pandamobileuz.nuntium.ui.custom.BaseFragment

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter()
    }

    private var categoryList: ArrayList<Category>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        categoryAdapter.submitList(categoryList!!)
        binding.recycler.adapter = categoryAdapter

        (activity as MainActivity).bottomNavigationShow()

        categoryAdapter.setOnClickListener {
            findNavController().navigate(R.id.action_categoryFragment_to_newsFragment,
                bundleOf("newsCategory" to it.valueName))
        }

    }

    private fun loadData() {
        categoryList = ArrayList()
        categoryList!!.add(Category(1, "\uD83C\uDFC8   Sports", "Sports", false))
        categoryList!!.add(Category(1, "\uD83C\uDF1E   Life", "Life", false))
        categoryList!!.add(Category(1, "\uD83D\uDC3B   Animals", "Animals", false))
        categoryList!!.add(Category(1, "\uD83C\uDF54   Food", "Food", false))
        categoryList!!.add(Category(1, "\uD83D\uDCDC   History", "History", false))
        categoryList!!.add(Category(1, "\uD83D\uDE37   Covid-19", "Covid-19", false))
        categoryList!!.add(Category(1, "⚖️   Politics", "Politics", false))
        categoryList!!.add(Category(1, "\uD83C\uDFAE   Gaming", "Gaming", false))
        categoryList!!.add(Category(1, "\uD83C\uDF34   Nature", "Nature", false))
        categoryList!!.add(Category(1, "\uD83C\uDFA8   Art", "Art", false))
        categoryList!!.add(Category(1, "\uD83D\uDC57   Fashion", "Fashion", false))
        categoryList!!.add(Category(1, "⚔️   Middle East", "Middle East", false))
    }

}