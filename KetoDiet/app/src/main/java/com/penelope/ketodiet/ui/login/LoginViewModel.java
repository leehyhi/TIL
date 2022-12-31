package com.penelope.ketodiet.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.penelope.ketodiet.data.PreferencesData;
import com.penelope.ketodiet.utils.AuthUtils;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {


    private final MutableLiveData<Event> event = new MutableLiveData<>();         // 프래그먼트에서 처리할 이벤트

    private String userId = "";                         // 입력된 유저 아이디 값
    private String password = "";                       // 입력된 비밀번호 값

    private final LiveData<String> currentUid;          // 현재 로그인 된 계정의 uid

    private final FirebaseAuth auth;
    private final PreferencesData preferencesData;


    @Inject
    public LoginViewModel(FirebaseAuth auth, PreferencesData preferencesData) {
        this.auth = auth;
        this.preferencesData = preferencesData;

        currentUid = preferencesData.getUidLive();
    }

    public LiveData<Event> getEvent() {
        event.setValue(null);
        return event;
    }

    public LiveData<String> getCurrentUid() {
        return currentUid;
    }


    public void onUserIdChanged(String value) {
        // 아이디 입력값이 변경되었을 때
        userId = value.trim();
    }

    public void onPasswordChanged(String value) {
        // 비밀번호 입력값이 변경되었을 때
        password = value;
    }

    public void onLoginClicked() {

        // 로그인을 요청했을 때 : 로그인 시도하기

        if (userId.length() < 4) {
            // 에러 : 짧은 아이디
            event.setValue(new Event.ShowGeneralMessage("아이디를 4글자 이상 입력해주세요"));
            return;
        }
        if (password.length() < 6) {
            // 에러 : 짧은 패스워드
            event.setValue(new Event.ShowGeneralMessage("비밀번호를 6글자 이상 입력해주세요"));
            return;
        }

        auth.signInWithEmailAndPassword(AuthUtils.emailize(userId), password)
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    event.setValue(new Event.ShowGeneralMessage("회원정보를 확인해주세요"));
                });
    }

    public void onRegisterClicked() {
        event.setValue(new Event.NavigateToRegisterScreen());
    }

    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        String uid = firebaseAuth.getUid();
        if (uid != null) {
            preferencesData.setUid(uid);
        }
    }

    // 프래그먼트에 전송될 이벤트

    static class Event {

        public static class ShowGeneralMessage extends Event {
            public final String message;

            public ShowGeneralMessage(String message) {
                this.message = message;
            }
        }

        public static class NavigateToRegisterScreen extends Event {
        }
    }
}
