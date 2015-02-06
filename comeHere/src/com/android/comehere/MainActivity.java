package com.android.comehere;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;

import com.viewpagerindicator.TabPageIndicator;

public class MainActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private TabPageIndicator mTabIndicator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_upload);
		initView();
	}
	

	private void initView()
	{
		ViewPager mViewPager = (ViewPager)findViewById(R.id.id_viewpager);
		TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.id_indicator);
		TabAdapter adapter = new TabAdapter(getSupportFragmentManager());	
		mViewPager.setAdapter(adapter);
		indicator.setViewPager(mViewPager, 0);
	}
}
