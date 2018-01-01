package cn.kingsleychung.final_project.Other;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by weimumu on 2018-1-1.
 */

public class GetRecordsPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentLists;

    public GetRecordsPagerAdapter(FragmentManager fm, List<Fragment> fragmentLists) {
        super(fm);
        this.fragmentLists = fragmentLists;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentLists.get(position);
    }

    @Override
    public int getCount() {
        return fragmentLists.size();
    }
}
