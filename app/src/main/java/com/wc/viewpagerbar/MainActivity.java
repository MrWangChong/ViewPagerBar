package com.wc.viewpagerbar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.wc.pagerbar.SelectView;
import com.wc.viewpagerbar.adapter.FragmentAdapter;
import com.wc.viewpagerbar.fragment.FragmentJoke;
import com.wc.viewpagerbar.fragment.FragmentNews;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager装Fragment demo，我复制的SelectView做导航，只能固定在ViewPager装两个Fragment
 */
public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private SelectView selectView;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        selectView = (SelectView) findViewById(R.id.selectView);

        //设置ViewPager要加入的Fragment
        fragments = new ArrayList<>();
        fragments.add(new FragmentNews());//添加新闻页
        fragments.add(new FragmentJoke());//添加笑话页
        //设置ViewPager适配器
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments));
        //设置SelectView左边和右边显示的文字
        selectView.setText("新闻", "笑话");
        //设置ViewPager和SelectView的事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    selectView.setSelect(true);
                } else {
                    selectView.setSelect(false);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        selectView.setOnSelectListener(new SelectView.OnSelectListener() {

            @Override
            public void onSelect(SelectView view, boolean isLeft) {
                if (isLeft) {
//					Log.v("onSelect", "点左边");
                    viewPager.setCurrentItem(0);
                } else {
//					Log.v("onSelect", "点右边");
                    viewPager.setCurrentItem(1);
                }
            }
        });

        //-------设置selectView的属性，按需设置就行------------//
        //设置字体大小,默认14sp
        selectView.setTextSize(14);
        //设置圆角大小，默认6
        selectView.setRadius(6);
        //设置未选中的字体颜色 Color.WHITE
        selectView.setColor(Color.WHITE);
        //设置选中字体颜色 默认#212121
        selectView.setSelectedColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }
}
