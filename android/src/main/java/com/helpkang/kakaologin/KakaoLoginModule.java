package com.helpkang.kakaologin;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.helpkang.kakaologin.mo.ReactKakaoLogin;
import com.kakao.auth.Session;

public class KakaoLoginModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    private static final String LOG_TAG = "KakaoTalk";
    private ReactKakaoLogin rkl;

    public KakaoLoginModule(ReactApplicationContext reactContext) {
        super(reactContext);
        initKakao();
    }

    @ReactMethod
    public void login(Promise promise){
//        initKakao();
        rkl.login(promise);
    }

    @ReactMethod
    public void logout(Promise promise){
//        initKakao();
        rkl.logout(promise);
    }

    public void initKakao(){
        if( this.rkl  != null) return;
        ReactApplicationContext reactContext = getReactApplicationContext();
        reactContext.addActivityEventListener(this);

//        Log.d("current context", reactContext.toString());
//
//        Log.d("current Activity", reactContext.getCurrentActivity().toString());

        this.rkl = new ReactKakaoLogin(reactContext);
    }


    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        // if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)){
        //    return;
        // }
        try {
            Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data);
        } catch (IllegalStateException ise) {
            // 초기화 안했을때 로그아웃 시도시 IllegalStateException 떨어져서 앱크래시되는거 막음...
            Log.w(LOG_TAG, "kakao session is not initialized.");
        }
    }


    @Override
    public void onNewIntent(Intent intent) {
    }

    @Override
    public String getName() {
        return "KakaoLoginModule";
    }
}
