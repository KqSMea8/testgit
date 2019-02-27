package com.hanweb.interceptor;

import com.hanweb.common.permission.AbsPermissionAdapter;
import com.hanweb.common.permission.PermissionStructure;

public class PermissionAdapter extends AbsPermissionAdapter {

	@Override
	public boolean checkTag(PermissionStructure structure) {
		return false;
	}

	@Override
	public boolean checkController(PermissionStructure structure) {
		return false;
	}

	@Override
	public boolean checkControllerWithURL(PermissionStructure structure, String url) {
		return true;
	}
}
