package com.penelope.ketodiet.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.penelope.ketodiet.databinding.ActivityLoginBinding;
import com.penelope.ketodiet.ui.main.MainActivity;
import com.penelope.ketodiet.ui.register.RegisterActivity;
import com.penelope.ketodiet.utils.UiUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // UI 에 리스너 부여
        UiUtils.setEditTextListener(binding.editTextUserId, s -> viewModel.onUserIdChanged(s));
        UiUtils.setEditTextListener(binding.editTextPassword, s -> viewModel.onPasswordChanged(s));

        binding.buttonLogin.setOnClickListener(v -> viewModel.onLoginClicked());
        binding.textViewRegister.setOnClickListener(v -> viewModel.onRegisterClicked());

        // 앱 로그인 감지 시 메인 화면으로 이동
        viewModel.getCurrentUid().observe(this, uid -> {
            if (uid != null) {
                startMainActivity();
            }
        });

        // 뷰모델에서 전송한 이벤트 처리
        viewModel.getEvent().observe(this, event -> {
            if (event instanceof LoginViewModel.Event.ShowGeneralMessage) {
                hideKeyboard(binding.getRoot());
                String message = ((LoginViewModel.Event.ShowGeneralMessage) event).message;
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            } else if (event instanceof LoginViewModel.Event.NavigateToRegisterScreen) {
                startRegisterActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    protected void onPause() {
        FirebaseAuth.getInstance().removeAuthStateListener(this);
        super.onPause();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        viewModel.onAuthStateChanged(firebaseAuth);
    }

    private void startRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}




