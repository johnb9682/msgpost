package core.web;

import com.msgpost.model.sys.User;
 
public class SessionThreadLocal {

	private static ThreadLocal<User> ADMINUSERTHREADLOCAL = new ThreadLocal<User>();

	public static User getThreadSysUser() {
		return ADMINUSERTHREADLOCAL.get();
	}

	public static void setThreadSysUser(User user) {
		ADMINUSERTHREADLOCAL.set(user);
	}

}
