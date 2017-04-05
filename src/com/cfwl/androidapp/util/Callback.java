package com.cfwl.androidapp.util;

public interface Callback {
	void onBefore();

	boolean onRun();

	void onAfter(boolean b);
}
