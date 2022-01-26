package uz.pandamobileuz.nuntium.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import uz.pandamobileuz.nuntium.R
import uz.pandamobileuz.nuntium.data.models.Category
import uz.pandamobileuz.nuntium.data.storage.LocalStorage
import uz.pandamobileuz.nuntium.databinding.FragmentCategoryAuthBinding
import uz.pandamobileuz.nuntium.ui.MainActivity
import uz.pandamobileuz.nuntium.ui.adapters.CategoryAdapter
import uz.pandamobileuz.nuntium.ui.adapters.CategoryAdapterMultiSelect
import uz.pandamobileuz.nuntium.ui.custom.BaseFragment
import uz.pandamobileuz.nuntium.utils.showToast
import java.lang.StringBuilder
import javax.inject.Inject

@AndroidEntryPoint
class CategoryAuthFragment :
    BaseFragment<FragmentCategoryAuthBinding>(FragmentCategoryAuthBinding::inflate) {

    private val categoryAdapter: CategoryAdapterMultiSelect by lazy {
        CategoryAdapterMultiSelect()
    }
    private var categoryList: ArrayList<Category>? = null
    var dataStr:ArrayList<String>? = null

    @Inject
    lateinit var localStorage: LocalStorage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()
        categoryAdapter.submitList(categoryList!!)
        binding.recycler.adapter = categoryAdapter
        dataStr = ArrayList()
        categoryAdapter.setOnClickListener {
            if (it.isSelected){
                dataStr!!.add(it.valueName)
            }else{
                dataStr!!.remove(it.valueName)
            }
        }
        binding.btnNext.setOnClickListener {
            if (dataStr!!.size>0){
                localStorage.selectCategory = dataStr!!.joinToString("+")
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }else{
                showToast("Select 1 Category")
            }

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