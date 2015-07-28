package com.flowerfat.littleprogressfragment;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

	private FragmentTest mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initFragment();
		
	}
	
	// 初始化fragment
	private void initFragment(){
		mFragment = new FragmentTest();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.main_fragment, mFragment).commit();
	}

	// 点击事件
	public void buttonClick(View v) {
		if (v.getId() == R.id.main_loading) {
			mFragment.controlLoading();
		} else if (v.getId() == R.id.main_success) {
			mFragment.controlSuccess();
		} else if (v.getId() == R.id.main_failed) {
			mFragment.controlFailed();
		} else if (v.getId() == R.id.main_widget) { 
			mFragment.controlWidget();
		}
	}
}
