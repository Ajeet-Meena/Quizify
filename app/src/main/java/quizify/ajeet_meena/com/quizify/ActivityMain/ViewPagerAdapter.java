package quizify.ajeet_meena.com.quizify.ActivityMain;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
 
    CharSequence Titles[];
    int NumbOfTabs;
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }
 
    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            return new quizify.ajeet_meena.com.quizify.ActivityMain.FragmentOne();
        }
        else
        {
            return new quizify.ajeet_meena.com.quizify.ActivityMain.FragmentTwo();
        }
    }
 
    // This method return the titles for the Tabs in the Tab Strip
 
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }
 
    // This method return the Number of slidingTabLayout for the slidingTabLayout Strip
 
    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}