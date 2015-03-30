package com.example.rlee.webviewdemo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by rlee on 3/30/15.
 */
public class JSI {
    private static final String TAG = "Web_JSI";

    private static final int FLAG_CANCELED = -1;
    private static final int FLAG_FAILED = 0;
    private static final int FLAG_SUCCEEDED = 1;

    private Context context;
    private WebView webView;
    private String rootUrl;
    private boolean activityStopped;
    private JsiCallback jsiCallbackOrNull;
    private boolean isDestroyed = false;

    public JSI(Context context, WebView webView) {
        this(context, webView, null);
    }

    public JSI(Context context, WebView webView, JsiCallback jsiCallbackOrNull) {
        this.context = context;
        this.webView = webView;
//        initDb();
//        initDownloader();
        initActivityLifecycleListener();
        this.jsiCallbackOrNull = jsiCallbackOrNull;
    }

    public interface JsiCallback {
        public void showGetConnected();
        void setUpEnabled(boolean enabled);
    }

    private void initActivityLifecycleListener() {
        ((Application) context.getApplicationContext()).registerActivityLifecycleCallbacks(
                new AbstractActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityStarted(Activity activity) {
                        if (activity == context) {
                            activityStopped = false;
                            callbackWebView("onShellEvent", "'activate'");
                        }
                    }

                    @Override
                    public void onActivityStopped(Activity activity) {
                        if (activity == context) {
                            activityStopped = true;
                            callbackWebView("onShellEvent", "'deactivate'");
                        }
                    }
                });
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public void callShowChannelMenu() {
        callbackWebView("toggleChannelMenu", null);
    }

    public void callbackWebView(String func, String params) {
        if (TextUtils.isEmpty(func)) {
            //D.logcat(TAG, "Invalid callback function");
            return;
        }
        StringBuilder callback = new StringBuilder(16 * 1024);
        callback.append("javascript:if (typeof " + func + " != 'undefined') {")
                .append("(function() { ").append(func).append('(');
        if (!TextUtils.isEmpty(params)) {
            callback.append(params);
        }
        callback.append("); } )() } else {console.log('UNDEFINED ' + '" + func + "');}");
        final String script = callback.toString();
        //D.logcat(TAG, "Invoking callback " + script);
        webView.post(new Runnable() {
            @Override
            public void run() {
                if( webView != null && !isDestroyed ) {
                    webView.loadUrl(script);
                }
            }
        });
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }
}
