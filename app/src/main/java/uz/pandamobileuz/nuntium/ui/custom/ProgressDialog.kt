package uz.pandamobileuz.nuntium.ui.custom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import uz.pandamobileuz.nuntium.R
import uz.pandamobileuz.nuntium.databinding.ItemProgressDialogBinding

class ProgressDialog:DialogFragment(R.layout.item_progress_dialog) {

    private var binding: ItemProgressDialogBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.CustomDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ItemProgressDialogBinding.bind(view)


    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}