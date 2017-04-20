### ViewPager跟滑导航栏以及仿QQ首页切换栏

[![](https://jitpack.io/v/MrWangChong/ViewPagerBar.svg)](https://jitpack.io/#MrWangChong/ViewPagerBar)

### 引入
Step 1. Add the JitPack repository to your build file Add it in your root build.gradle at the end of repositories:
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```gradle
	dependencies {
	       compile 'com.github.MrWangChong:ViewPagerBar:1.0.0'
	}
```

以前一直使用的[PagerSlidingTabStrip](https://github.com/astuetz/PagerSlidingTabStrip) 后来使用的时候发现一个BUG，以为是PagerSlidingTabStrip的BUG，然后就自己仿照着写了一个，结果发现并不是PagerSlidingTabStrip的BUG，自此之后，就用的自己写的这个了

自己写的时候新加了一个滑动效果，计算方法参考了[SmartTabLayout](https://github.com/ogaclejapan/SmartTabLayout)

总共就两个类，可以自行拷贝来使用，嫌麻烦就直接引入就行。
* 仿QQ顶部导航栏  SelectView  
有一个缺点，当时写的时候图快，没有写计算控件宽度和高度的方法，需要固定好宽高使用

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="48dp"
        android:background="@color/colorPrimaryDark">

        <com.wc.pagerbar.SelectView
            android:id="@+id/selectView"
            android:layout_width="114dp"
            android:layout_height="26dp"
            android:layout_centerInParent="true"
            android:clickable="true" />
    </RelativeLayout>

    <android.support.v4.view.ViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</LinearLayout>
```
demo是在ViewPager里面加入Fragment使用的
```java
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

        //-------设置SelectView的属性，按需设置就行------------//
        //设置字体大小,默认14sp
        selectView.setTextSize(14);
        //设置圆角大小，默认6
        selectView.setRadius(6);
        //设置未选中的字体颜色 Color.WHITE
        selectView.setColor(Color.WHITE);
        //设置选中字体颜色 默认#212121
        selectView.setSelectedColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

```
FragmentAdapter 是我写的一个Fragment通用适配器

```java
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * 通用Fragment适配器
 * Created by RushKing on 2017/2/16.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments; // Fragemnt列表
    private List<String> titles; // Fragemnt列表

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null) {
            return titles.get(position);
        }
        return String.valueOf(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
    }
}

```
* 跟滑导航栏 PagerNavigationBar
```xml
    <?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    <!-- 标题 -->
    <com.wc.pagerbar.PagerNavigationBar
        android:id="@+id/pagerBar"
        android:layout_width="match_parent"
        android:layout_height="32dp" />

    <android.support.v4.view.ViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</LinearLayout>

```

```java

viewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragments, titles));
// 关联
pagerBar.setViewPager(viewPager);


//-------设置selectView的属性，按需设置就行------------//
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
        
```

