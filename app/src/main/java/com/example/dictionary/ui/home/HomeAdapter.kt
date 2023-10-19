package com.example.dictionary.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dictionary.ui.game.GameFragment
import com.example.dictionary.ui.game.LevelFragment
import com.example.dictionary.ui.searchedwords.SearchedWordsFragment
import com.example.dictionary.ui.word.WordFragment

class HomeAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> WordFragment()
            1 -> SearchedWordsFragment()
            2 -> LevelFragment()
            else -> throw IllegalStateException("Invalid position $position")
        }
    }
}