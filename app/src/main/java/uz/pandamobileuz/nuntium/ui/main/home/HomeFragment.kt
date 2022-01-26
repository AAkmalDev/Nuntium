package uz.pandamobileuz.nuntium.ui.main.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import uz.pandamobileuz.nuntium.R
import uz.pandamobileuz.nuntium.data.models.Category
import uz.pandamobileuz.nuntium.data.storage.LocalStorage
import uz.pandamobileuz.nuntium.databinding.FragmentHomeBinding
import uz.pandamobileuz.nuntium.databinding.ItemInnerCatergoryBinding
import uz.pandamobileuz.nuntium.ui.MainActivity
import uz.pandamobileuz.nuntium.ui.adapters.CategoryAdapter
import uz.pandamobileuz.nuntium.ui.adapters.NewsAdapter
import uz.pandamobileuz.nuntium.ui.adapters.SmallNewsAdapter
import uz.pandamobileuz.nuntium.ui.custom.BaseFragment
import uz.pandamobileuz.nuntium.ui.main.SaveViewModel
import uz.pandamobileuz.nuntium.utils.NetworkHelper
import uz.pandamobileuz.nuntium.utils.OnTabSelectedWrapper
import uz.pandamobileuz.nuntium.utils.showToast
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    companion object{
        const val TAG = "HomeFragment"
    }
    private val viewModel: HomeViewModel by viewModels()
    private val saveViewModel: SaveViewModel by viewModels()
    private val adapter: NewsAdapter by lazy {
        NewsAdapter(saveViewModel)
    }
    private val smallNewsAdapter: SmallNewsAdapter by lazy {
        SmallNewsAdapter()
    }
    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter()
    }

    @Inject
    lateinit var localStorage: LocalStorage

    private var categoryList: ArrayList<Category>? = null

    private var time: String? = null

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).bottomNavigationShow()

        loadData()
        categoryAdapter.submitList(categoryList!!)

        binding.recyclerInnerCategory.adapter = categoryAdapter


        val calendar = Calendar.getInstance()
        val data = SimpleDateFormat("yyyy-MM-dd")
        time = data.format(calendar.time)

        if (NetworkHelper(requireContext()).isNetworkConnected()) {
            showToast("net bor ")
            Log.d(TAG, "onViewCreated: NET BOR")
        } else {
            showToast("net yoq")
            Log.d(TAG, "onViewCreated: NET YOQ")
        }

        lifecycleScope.launchWhenCreated {
            viewModel.getNews(localStorage.selectCategory).collect {
                adapter.submitData(it)
            }
        }

        binding.smallNewsRecycler.adapter = smallNewsAdapter

        smallNewsAdapter.setOnClickListener {
            findNavController().navigate(R.id.articleFragment, bundleOf("news" to it))
        }

        binding.recyclerCategory.adapter = adapter

        adapter.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("news", it)
            findNavController().navigate(R.id.articleFragment, bundleOf("news" to it))
        }

        for (category in categoryList!!) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(category.valueName))
        }

        categoryList!!.forEachIndexed { index, category ->
            val tabInnerBinding =
                ItemInnerCatergoryBinding.inflate(layoutInflater)
            tabInnerBinding.categoryName.text = category.valueName
            if (index == 0) {
                tabInnerBinding.categoryCard.setCardBackgroundColor(ContextCompat.getColor(
                    requireContext(), R.color.purple_primary))
                tabInnerBinding.categoryName.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.white))
                loadNews("Random")
            } else {
                tabInnerBinding.categoryCard.setCardBackgroundColor(ContextCompat.getColor(
                    requireContext(), R.color.grey_lighter))
                tabInnerBinding.categoryName.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.grey_darker))
            }
            binding.tabLayout.getTabAt(index)?.customView = tabInnerBinding.root
        }


        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedWrapper() {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                super.onTabSelected(tab)
                val binding = ItemInnerCatergoryBinding.bind(tab!!.customView!!)
                binding.apply {
                    categoryCard.setCardBackgroundColor(
                        ContextCompat.getColor(requireContext(), R.color.purple_primary))
                    categoryName.setTextColor(
                        ContextCompat.getColor(requireContext(), R.color.white))
                    loadNews(categoryList!![tab.position].valueName)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                super.onTabUnselected(tab)
                val binding = ItemInnerCatergoryBinding.bind(tab!!.customView!!)
                binding.apply {
                    categoryCard.setCardBackgroundColor(
                        ContextCompat.getColor(requireContext(), R.color.grey_lighter))
                    categoryName.setTextColor(
                        ContextCompat.getColor(requireContext(), R.color.grey_darker))
                }
            }
        })

    }

    private fun loadNews(query: String) {
        lifecycleScope.launchWhenCreated {
            viewModel.getFullNews(query, time!!).collect {
                smallNewsAdapter.submitData(it)
            }
        }
    }

    private fun loadData() {
        categoryList = ArrayList()
        categoryList!!.add(Category(1, "Random", "Random", false))
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
