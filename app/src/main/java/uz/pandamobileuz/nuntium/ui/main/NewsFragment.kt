package uz.pandamobileuz.nuntium.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import uz.pandamobileuz.nuntium.R
import uz.pandamobileuz.nuntium.databinding.FragmentNewsBinding
import uz.pandamobileuz.nuntium.ui.MainActivity
import uz.pandamobileuz.nuntium.ui.adapters.NewsCategoryAdapter
import uz.pandamobileuz.nuntium.ui.adapters.SaveSmallNewsAdapter
import uz.pandamobileuz.nuntium.ui.adapters.SmallNewsAdapter
import uz.pandamobileuz.nuntium.ui.custom.BaseFragment
import uz.pandamobileuz.nuntium.ui.main.home.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private val saveViewModel: SaveViewModel by viewModels()
    private val newsCategoryAdapter: NewsCategoryAdapter by lazy {
        NewsCategoryAdapter(saveViewModel)
    }
    private var categoryText: String? = null
    private var time: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryText = arguments?.getString("newsCategory")
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = Calendar.getInstance()
        val data = SimpleDateFormat("yyyy-MM-dd")
        time = data.format(calendar.time)

        (activity as MainActivity).bottomNavigationHide()

        binding.welcomeText.text = categoryText!!

        lifecycleScope.launchWhenCreated {
            viewModel.getFullNews(categoryText!!, time!!).collect {
                newsCategoryAdapter.submitData(it)
            }
        }

        binding.recycler.adapter = newsCategoryAdapter

        newsCategoryAdapter.setOnClickListener {
            findNavController().navigate(R.id.articleFragment, bundleOf("news" to it))
        }

    }


}