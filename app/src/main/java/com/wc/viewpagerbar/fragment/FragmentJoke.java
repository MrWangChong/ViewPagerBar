package com.wc.viewpagerbar.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wc.pagerbar.PagerNavigationBar;
import com.wc.viewpagerbar.R;
import com.wc.viewpagerbar.adapter.FragmentAdapter;

import java.util.ArrayList;


/**
 * 笑话页
 * Created by RushKing on 2017/4/12.
 */

public class FragmentJoke extends Fragment {
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private ViewPager viewPager;
    private PagerNavigationBar pagerBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joke, container, false);
        //初始化控件
        pagerBar = (PagerNavigationBar) view.findViewById(R.id.pagerBar);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        //设置需要显示的标题
        titles = new ArrayList<>();
        titles.add("文本笑话");
        titles.add("搞笑图");
        titles.add("动态搞笑图");
        fragments = new ArrayList<>();
        //添加和标题对应的Fragment
        fragments.add(new FragmentJokeText());
        fragments.add(new FragmentJokeImage().setFlag(0));
        fragments.add(new FragmentJokeImage().setFlag(1));

        viewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragments, titles));
        // 关联
        pagerBar.setViewPager(viewPager);
        setTabsValue();
        return view;
    }

    // 设置标题的详细属性
    private void setTabsValue() {
        // 自动填充满屏幕(默认不填满)
        pagerBar.setShouldExpand(true);
        // 需要分割线(默认不需要)
        pagerBar.setDivider(true);
        // 设置字体大小 单位sp，默认15sp
        pagerBar.setTextSize(15);
        // 设置未选中字体颜色，默认 Color.BLACK
        pagerBar.setTextColor(Color.BLACK);
        // 设置选中字体颜色，默认 Color.RED
        pagerBar.setSelectedTextColor(Color.RED);
        // 设置游标颜色，默认 Color.RED
        pagerBar.setIndicatorColor(Color.RED);
        // 设置游标高度 单位px，默认4dp
        pagerBar.setIndicatorHeight(10);
        // 设置游标滑动方式是否为默认的，默认为true
        pagerBar.setIndicatorScrollModel(false);
    }
}
