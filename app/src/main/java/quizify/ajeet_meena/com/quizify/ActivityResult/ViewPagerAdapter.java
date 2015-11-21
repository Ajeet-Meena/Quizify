package quizify.ajeet_meena.com.quizify.ActivityResult;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
 
    CharSequence Titles[];
    int NumbOfTabs;
    int eventId;
    String from;
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb, int eventId, String from) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.eventId = eventId;
        this.from = from;
    }
 
    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("eventId",eventId);
        bundle.putString("from",from);
        if(position == 0) // if the position is 0 we are returning the First tab
        {
            FragmentOne fragmentOne= new FragmentOne();
            fragmentOne.setArguments(bundle);
            return fragmentOne;
        }
        else
        {
            FragmentTwo fragmentTwo = new FragmentTwo();
            fragmentTwo.setArguments(bundle);
            return fragmentTwo;
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