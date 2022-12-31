package com.penelope.ketodiet.ui.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.penelope.ketodiet.databinding.ActivityRegisterBinding;
import com.penelope.ketodiet.utils.UiUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private RegisterViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // UI 에 리스너 부여
        UiUtils.setEditTextListener(binding.editTextUserId, s -> viewModel.onUserIdChanged(s));
        UiUtils.setEditTextListener(binding.editTextPassword, s -> viewModel.onPasswordChanged(s));
        UiUtils.setEditTextListener(binding.editTextPasswordConfirm, s -> viewModel.onPasswordConfirmChanged(s));

        binding.buttonSignUp.setOnClickListener(v -> viewModel.onRegisterClick());
        binding.textViewSignIn.setOnClickListener(v -> viewModel.onLoginClick());

        // 뷰모델에서 전송한 이벤트 처리
        viewModel.getEvent().observe(this, event -> {
            if (event instanceof RegisterViewModel.Event.ShowGeneralMessage) {
                hideKeyboard(binding.getRoot());
                String message = ((RegisterViewModel.Event.ShowGeneralMessage) event).message;
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            } else if (event instanceof RegisterViewModel.Event.NavigateBack) {
                finish();
            } else if (event instanceof RegisterViewModel.Event.NavigateBackWithResult) {
                Toast.makeText(this, "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}