package uz.pandamobileuz.nuntium.ui.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB: ViewBinding>(
    private val bindingInflater:(inflater:LayoutInflater) -> VB
) :Fragment() {

    private var _bn: VB? = null
    val binding get() = _bn!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bn = bindingInflater.invoke(inflater)
        if (_bn == null)
            throw IllegalArgumentException("Binding null")

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }
}