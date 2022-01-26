package uz.pandamobileuz.nuntium.ui.main

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.pandamobileuz.nuntium.R
import uz.pandamobileuz.nuntium.databinding.FragmentBookmarkBinding
import uz.pandamobileuz.nuntium.ui.MainActivity
import uz.pandamobileuz.nuntium.ui.adapters.SaveSmallNewsAdapter
import uz.pandamobileuz.nuntium.ui.custom.BaseFragment

@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>(FragmentBookmarkBinding::inflate) {

    private val viewModel: SaveViewModel by viewModels()
    private val smallNewsAdapter: SaveSmallNewsAdapter by lazy {
        SaveSmallNewsAdapter()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).bottomNavigationShow()

        if (viewModel.getNews().isNotEmpty()){
            binding.emptyLayout.isVisible = false
            binding.smallNewsRecycler.isVisible = true
            smallNewsAdapter.submitList(viewModel.getNews())
            binding.smallNewsRecycler.adapter = smallNewsAdapter

        }else{
            binding.emptyLayout.isVisible = true
            binding.smallNewsRecycler.isVisible = false
        }

        smallNewsAdapter.setOnClickListener {
            findNavController().navigate(R.id.articleFragment, bundleOf("news" to it))
        }

    }

}