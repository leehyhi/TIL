package com.penelope.ketodiet.ui.diet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.penelope.ketodiet.data.diet.Diet;
import com.penelope.ketodiet.databinding.ActivityDietBinding;
import com.penelope.ketodiet.res.StringRes;
import com.penelope.ketodiet.utils.UiUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DietActivity extends AppCompatActivity {

    private ActivityDietBinding binding;
    private DietViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(DietViewModel.class);
        binding = ActivityDietBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 식단 어댑터를 초기화한다
        DietsAdapter adapter = new DietsAdapter();
        binding.recyclerDiet.setAdapter(adapter);
        binding.recyclerDiet.setHasFixedSize(true);

        adapter.setOnItemSelectedListener(new DietsAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
            }

            @Override
            public void onDeleteClick(int position) {
                Diet diet = adapter.getCurrentList().get(position);
                viewModel.onDeleteDietClick(diet);
            }
        });

        // 식단 리사이클러 뷰를 업데이트한다
        viewModel.getAllDiets().observe(this, diets -> {
            if (diets != null) {
                adapter.submitList(diets);
            }
            binding.progressBar.setVisibility(View.INVISIBLE);
        });

        // 새로운 식단 추가 UI 에 리스너를 지정한다
        UiUtils.setEditTextListener(binding.editTextDietName, value -> viewModel.onNameChanged(value));
        UiUtils.setSeekBarListener(binding.seekBarCarbohydrates, value -> viewModel.onCarbohydratesChanged(value));
        UiUtils.setSeekBarListener(binding.seekBarProteins, value -> viewModel.onProteinChanged(value));
        UiUtils.setSeekBarListener(binding.seekBarFats, value -> viewModel.onFatChanged(value));

        // 탄수화물 UI 업데이트
        viewModel.getCarbohydrates().observe(this, carbohydrates -> {
            if (carbohydrates != null) {
                String strCarbohydrates = StringRes.gram(carbohydrates);
                binding.textViewCarbohydrates.setText(strCarbohydrates);
            }
        });

        // 단백질 UI 업데이트
        viewModel.getProtein().observe(this, proteins -> {
            if (proteins != null) {
                String strProteins = StringRes.gram(proteins);
                binding.textViewProteins.setText(strProteins);
            }
        });

        // 지방 UI 업데이트
        viewModel.getFat().observe(this, fats -> {
            if (fats != null) {
                String strFats = StringRes.gram(fats);
                binding.textViewFats.setText(strFats);
            }
        });

        // 버튼 클릭 시 리스너를 지정한다
        binding.fabSubmitDiet.setOnClickListener(v -> {
            viewModel.onSubmitDiet();
            hideKeyboard();
        });

        // 뷰모델에서 발생한 이벤트 처리
        viewModel.getEvent().observe(this, event -> {
            if (event instanceof DietViewModel.Event.ShowGeneralMessage) {
                String message = ((DietViewModel.Event.ShowGeneralMessage) event).message;
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            } else if (event instanceof DietViewModel.Event.EmptyUI) {
                binding.editTextDietName.setText("");
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

}