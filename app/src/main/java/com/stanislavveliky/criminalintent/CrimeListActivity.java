package com.stanislavveliky.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by stan_ on 1/7/2018.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment()
    {
        return new CrimeListFragment();
    }
}
