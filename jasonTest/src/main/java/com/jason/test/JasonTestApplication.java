/**
 * @author zhoushengtao
 * @since 2014年5月21日 下午6:17:00
 */

package com.jason.test;

import android.app.Application;
import android.view.WindowManager;

public class JasonTestApplication extends Application{
    private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();

    public WindowManager.LayoutParams getWindowParams() {
	return windowParams;
    }
}
