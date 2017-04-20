package com.wc.viewpagerbar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 图片笑话页
 * Created by RushKing on 2017/4/12.
 */

public class FragmentJokeImage extends Fragment {
    private int flag;//flag标记，如果为0，是搞笑图，如果为1，是动态搞笑图;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public FragmentJokeImage setFlag(int flag) {
        this.flag = flag;
        return this;
    }
}
