package org.zoobie.pomd.quizz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.zoobie.pomd.quizz.fragment.MultipleChoiceQuestionFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;

        fragment = new MultipleChoiceQuestionFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
