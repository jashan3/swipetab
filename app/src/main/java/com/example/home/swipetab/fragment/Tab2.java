package com.example.home.swipetab.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.home.swipetab.R;
import com.example.home.swipetab.adapter.Data;
import com.example.home.swipetab.adapter.RecyclerAdapter;

@SuppressLint("ValidFragment")
public class Tab2 extends Fragment {

    private Context mContext;
    private RecyclerAdapter adapter;
    Data data;

    @SuppressLint("ValidFragment")
    public Tab2 (Context mContext){
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_2,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter = new RecyclerAdapter(mContext));


        data =new Data();
        data.setTitle("제목스");
        data.setContent("내용쓰");
        data.setResId(R.drawable.icon1234);
        adapter.addItem(data);

        return view;
    }
}
