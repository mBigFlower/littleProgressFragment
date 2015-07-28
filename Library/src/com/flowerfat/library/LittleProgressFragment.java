/*
 * Copyright (C) 2013 Evgeny Shishkin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flowerfat.library;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * 
 * I just make a change from Evgeny Shishkin
 * thank you for him 
 * 
 * @author bigFlower
 *
 */
public abstract class LittleProgressFragment extends Fragment {

	public final static int STATE_LOADING = 0;
	public final static int STATE_FAILED = 1;
	public final static int STATE_SUCCESS = 2;
	
	// 载体
	private View mContentParent;
	// 加载中  的显示
	private View mProgressView;
	// 加载成功的显示
	private View mContentView;
	// 加载失败的显示
	private View mFailedView;
	private int mIsContentState;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.little_fragment_progress, container,
				false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initContent();
	}
	
	protected abstract void findViews(View view);	  //初始化控件
	
	/**
	 * Initialization views.
	 */
	private void initContent() {
		if (mContentParent != null && mProgressView != null) {
			return;
		}
		View root = getView();
		if (root == null) {
			throw new IllegalStateException("Content view not yet created");
		}
		mProgressView = root.findViewById(R.id.FragmentProgress);
		if (mProgressView == null) {
			throw new RuntimeException(
					"Your content must have a ViewGroup whose id attribute is 'R.id.progress_container'");
		}
		mContentParent = root.findViewById(R.id.FragmentParent);
		if (mContentParent == null) {
			throw new RuntimeException(
					"Your content must have a ViewGroup whose id attribute is 'R.id.content_container'");
		}
		mFailedView = root.findViewById(R.id.FragmentFailed);
		if (mFailedView != null) {
			mFailedView.setVisibility(View.GONE);
		}
	}
	/**
	 * Detach from view.
	 */
	@Override
	public void onDestroyView() {
		mIsContentState = STATE_LOADING;
		mProgressView = mContentParent = mContentView = mFailedView = null;
		super.onDestroyView();
	}

	/**
	 * 设置布局
	 * 
	 * @param layoutResId
	 */
	protected void setContentView(int layoutResId) {
		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		View contentView = layoutInflater.inflate(layoutResId, null);
		setContentView(contentView);
	}

	/**
	 * 将我们的布局添加进视图
	 */
	public void setContentView(View view) {
		initContent();
		findViews(view);
		if (view == null) {
			throw new IllegalArgumentException("Content view can't be null");
		}
		if (mContentParent instanceof ViewGroup) {
			ViewGroup contentContainer = (ViewGroup) mContentParent;
			if (mContentView == null) {
				contentContainer.addView(view);
			} else {
				int index = contentContainer.indexOfChild(mContentView);
				// replace content view
				contentContainer.removeView(mContentView);
				contentContainer.addView(view, index);
			}
			mContentView = view;
		} else {
			throw new IllegalStateException(
					"Can't be used with a custom content view");
		}
		// 加载完毕，显示
    	setContentShow();
	}

	/**
	 * 设置加载失败的文字
	 * @param resId
	 */
	public void setEmptyText(int resId) {
		setEmptyText(getString(resId));
	}

	public void setEmptyText(CharSequence text) {
		initContent();
		if (mFailedView != null && mFailedView instanceof TextView) {
			((TextView) mFailedView).setText(text);
		} else {
			throw new IllegalStateException(
					"Can't be used with a custom content view");
		}
	}

	//////////////////////////////////////////////  三种状态：加载中，成功，失败
	public void setContentLoading() {
		mProgressView.setVisibility(View.VISIBLE);
		mContentView.setVisibility(View.GONE);
		mFailedView.setVisibility(View.GONE);
		mIsContentState = STATE_LOADING;
	}

	public void setContentShow() {
		mProgressView.setVisibility(View.GONE);
		mContentView.setVisibility(View.VISIBLE);
		mFailedView.setVisibility(View.GONE);
		mIsContentState = STATE_SUCCESS;
	}

	public void setContentFailed() {
		mProgressView.setVisibility(View.GONE);
		mContentView.setVisibility(View.GONE);
		mFailedView.setVisibility(View.VISIBLE);
		mIsContentState = STATE_FAILED;
	}

}
