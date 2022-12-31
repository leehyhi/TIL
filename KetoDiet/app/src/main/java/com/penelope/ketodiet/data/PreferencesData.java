package com.penelope.ketodiet.data;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.LiveData;

import com.penelope.ketodiet.utils.data.preferences.SharedPreferenceIntegerLiveData;
import com.penelope.ketodiet.utils.data.preferences.SharedPreferenceStringLiveData;

import javax.inject.Inject;

public class PreferencesData {
    // 프로그래머가 SharedPreferences 에 접근할 수 있는 클래스

    private final SharedPreferences preferences;
    private final LiveData<String> uid;


    @Inject
    public PreferencesData(Application application) {
        preferences = PreferenceManager.getDefaultSharedPreferences(application);
        uid = new SharedPreferenceStringLiveData(preferences, "uid", null);
    }

    public LiveData<String> getUidLive() {
        return uid;
    }

    public String getUid() {
        return preferences.getString("uid", null);
    }

    public void setUid(String uid) {
        preferences.edit().putString("uid", uid).apply();
    }

}












