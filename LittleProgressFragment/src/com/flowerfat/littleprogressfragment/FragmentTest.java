package com.flowerfat.littleprogressfragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.flowerfat.library.LittleProgressFragment;

public class FragmentTest extends LittleProgressFragment {

	private TextView mTextView;

	// 仅仅做一个延时
	private Handler mHandler;
	private Runnable mShowContentRunnable = new Runnable() {
		@Override
		public void run() {
			setContentView(R.layout.test_view_content);
		}
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setEmptyText(R.string.empty);

		delayForSuccess();
	}

	@Override
	protected void findViews(View view) {
		mTextView = (TextView) view.findViewById(R.id.test_title);
	}

	// //////////////////////////////////////////测试用
	// 延时~
	private void delayForSuccess() {
		mHandler = new Handler();
		mHandler.postDelayed(mShowContentRunnable, 2500);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mHandler.removeCallbacks(mShowContentRunnable);
	}

	// 三个状态
	public void controlSuccess() {
		setContentShow();
	}

	public void controlFailed() {
		setContentFailed();
	}

	public void controlLoading() {
		try {
			setContentLoading();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 操作控件
	public void controlWidget() {
		if (mTextView != null)
			mTextView.setText("这是什么鬼？");
	}
}
