package uz.pandamobileuz.nuntium.utils

import com.google.android.material.tabs.TabLayout

open class OnTabSelectedWrapper: TabLayout.OnTabSelectedListener {

    override fun onTabSelected(tab: TabLayout.Tab?) {}

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}
}