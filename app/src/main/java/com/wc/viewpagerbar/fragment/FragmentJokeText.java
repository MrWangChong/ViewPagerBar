package com.wc.viewpagerbar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wc.viewpagerbar.R;


/**
 * 文本笑话Fragment
 * Created by RushKing on 2017/4/12.
 */

public class FragmentJokeText extends Fragment {
    //private XXXAdapter mAdapter;
    //private List<XXX> infos;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jock_text, container, false);
        RecyclerView recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置适配器
        //infos=new ArrayList<>();
        //mAdapter=new XXXAdapter(getActivity(),infos);
        //recycler_view.setAdapter(xxx);
        getJokeText();
        return view;
    }

    //联网获取JokeText数据
    private void getJokeText() {
        //具体获取数据代码

            //获取到数据之后，解析成List<XXX>
            //使用infos.addAll解析出来的数据
            //刷新显示内容mAdapter.notifyDataSetChanged();
    }
}
