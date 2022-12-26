package com.chatpuppy.app.repository;

import com.chatpuppy.app.entity.NetworkInfo;

public interface OnNetworkChangeListener {
	void onNetworkChanged(NetworkInfo networkInfo);
}
