package com.putianxia.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class MainPagerAdapter extends FragmentPagerAdapter{
	 ArrayList<Fragment> list; 
	 
	public MainPagerAdapter(FragmentManager fm,ArrayList<Fragment> list) {
		super(fm);
		this.list=list;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

}
