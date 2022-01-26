package uz.pandamobileuz.nuntium.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import uz.pandamobileuz.nuntium.R
import uz.pandamobileuz.nuntium.data.models.NewsApi
import uz.pandamobileuz.nuntium.databinding.FragmentArticleBinding
import uz.pandamobileuz.nuntium.ui.MainActivity
import uz.pandamobileuz.nuntium.ui.custom.BaseFragment

@AndroidEntryPoint
class ArticleFragment : BaseFragment<FragmentArticleBinding>(FragmentArticleBinding::inflate) {

    private val saveViewModel: SaveViewModel by viewModels()
    private var newsApi: NewsApi? = null
    private var isLiked = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = requireActivity().window
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        newsApi = arguments?.getSerializable("news") as NewsApi
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).bottomNavigationHide()

        isLiked = saveViewModel.getIsLiked(newsApi!!.publishedAt)

        binding.apply {

            imageBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }

            if (isLiked == 1) {
                imageBookmark.setImageResource(R.drawable.ic_bookmark_active)
                imageBookmark.setAltImageResource(R.drawable.ic_bookmark_active_grey_primary)
            }

            imageBookmark.setOnClickListener {
                isLiked = if (isLiked == 0) {
                    imageBookmark.setImageResource(R.drawable.ic_bookmark_active)
                    imageBookmark.setAltImageResource(R.drawable.ic_bookmark_active_grey_primary)
                    saveViewModel.saveNews(newsApi!!)
                    1
                } else {
                    imageBookmark.setImageResource(R.drawable.ic_bookmark_white)
                    imageBookmark.setAltImageResource(R.drawable.ic_bookmark_grey_primary)
                    saveViewModel.deleteNews(newsApi!!.publishedAt)
                    0
                }
            }

            imageReply.setOnClickListener {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, newsApi!!.url)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

            imageArticle.load(newsApi!!.urlToImage) {
                crossfade(true)
                crossfade(500)
                placeholder(R.drawable.placeholder_image)
                error(R.drawable.placeholder_image)
            }

            title.text = newsApi!!.title

            resultArticle.text = newsApi!!.description

            userName.text = newsApi!!.author

        }


    }

}