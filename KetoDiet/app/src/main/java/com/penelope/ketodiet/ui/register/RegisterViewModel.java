package com.penelope.ketodiet.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.penelope.ketodiet.data.user.User;
import com.penelope.ketodiet.data.user.UserRepository;
import com.penelope.ketodiet.utils.AuthUtils;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RegisterViewModel extends ViewModel {

    private final MutableLiveData<Event> event = new MutableLiveData<>();         // 프래그먼트에서 처리할 이벤트

    private String userId = "";
    private String password = "";
    private String passwordConfirm = "";

    private final FirebaseAuth auth;
    private final UserRepository userRepository;


    @Inject
    public RegisterViewModel(FirebaseAuth auth, UserRepository userRepository) {
        this.auth = auth;
        this.userRepository = userRepository;
    }

    public LiveData<Event> getEvent() {
        event.setValue(null);
        return event;
    }


    public void onUserIdChanged(String value) {
        // 아이디 입력값이 변경되었을 때
        userId = value.trim();
    }

    public void onPasswordChanged(String value) {
        // 비밀번호 입력값이 변경되었을 때
        password = value;
    }

    public void onPasswordConfirmChanged(String value) {
        // 비밀번호 확인값이 변경되었을 때
        passwordConfirm = value;
    }

    public void onRegisterClick() {

        // 회원가입 요청했을 때 : 회원가입 시도하기

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
        if (!passwordConfirm.equals(password)) {
            // 에러 : 비밀번호 확인 불일치
            event.setValue(new Event.ShowGeneralMessage("비밀번호를 정확하게 입력해주세요"));
            return;
        }

        auth.createUserWithEmailAndPassword(AuthUtils.emailize(userId), password)
                .addOnSuccessListener(authResult -> {
                    assert authResult.getUser() != null;
                    User user = new User(authResult.getUser().getUid(), userId);
                    userRepository.addUser(user,
                            unused -> event.setValue(new Event.NavigateBackWithResult()),
                            e -> event.postValue(new Event.ShowGeneralMessage("회원정보 생성에 실패했습니다"))
                    );
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    event.setValue(new Event.ShowGeneralMessage("회원가입에 실패했습니다"));
                });
    }

    public void onLoginClick() {
        event.setValue(new Event.NavigateBack());
    }

    // 프래그먼트에 전송될 이벤트

    public static class Event {

        public static class ShowGeneralMessage extends Event {
            public final String message;

            public ShowGeneralMessage(String message) {
                this.message = message;
            }
        }

        public static class NavigateBack extends Event {
        }

        public static class NavigateBackWithResult extends Event {
        }
    }

}
