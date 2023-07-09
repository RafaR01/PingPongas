package pt.ipt.dama.pingpongas.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * this class is used to inject fragments on activity
 */
class MyViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    /**
     * specify the number of fragments that you are going to use
     */
    override fun getItemCount(): Int {
        return 3
    }

    /**
     * return a new instance of a fragment
     */
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentOne()
            1 -> FragmentTwo()
            else -> FragmentOne()
        }
        /*
         when (position) {
            0 -> return FragmentOne()
            1 -> return FragmentTwo()
            2 -> return FragmentThree()
            else -> return FragmentOne()
        }
         */
    }

}