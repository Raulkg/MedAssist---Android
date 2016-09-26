package info.theh2o.rahul.medassist.ViewPager.frags;

import android.support.v4.app.Fragment;

import info.theh2o.rahul.medassist.ViewPager.BackPressImpl;
import info.theh2o.rahul.medassist.ViewPager.OnBackPressListener;

/**
 * Created by shahabuddin on 6/6/14.
 */
public class RootFragment extends Fragment implements OnBackPressListener {

    @Override
    public boolean onBackPressed() {
        return new BackPressImpl(this).onBackPressed();
    }
}
