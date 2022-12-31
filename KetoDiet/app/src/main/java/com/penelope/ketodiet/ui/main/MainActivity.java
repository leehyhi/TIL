package com.penelope.ketodiet.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.penelope.ketodiet.data.MealTime;
import com.penelope.ketodiet.data.diet.Diet;
import com.penelope.ketodiet.databinding.ActivityMainBinding;
import com.penelope.ketodiet.databinding.DialogPromptMealBinding;
import com.penelope.ketodiet.res.StringRes;
import com.penelope.ketodiet.ui.diet.DietActivity;
import com.penelope.ketodiet.ui.history.HistoryActivity;
import com.penelope.ketodiet.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    private DialogPromptMealBinding dialogPromptMealBinding;
    private AlertDialog dialogPromptMeal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 버튼/뷰 클릭시 리스너를 지정한다
        binding.fabHistory.setOnClickListener(v -> viewModel.onHistoryClick());
        binding.fabAddDiet.setOnClickListener(v -> viewModel.onAddDietClick());
        binding.imageViewAddBreakfast.setOnClickListener(v -> viewModel.onAddMealClick(MealTime.BREAKFAST));
        binding.imageViewAddLunch.setOnClickListener(v -> viewModel.onAddMealClick(MealTime.LUNCH));
        binding.imageViewAddDinner.setOnClickListener(v -> viewModel.onAddMealClick(MealTime.DINNER));
        binding.imageViewLogout.setOnClickListener(v -> viewModel.onLogoutClick());

        // 식사 추가 대화상자 초기화
        dialogPromptMealBinding = DialogPromptMealBinding.inflate(getLayoutInflater());
        dialogPromptMeal = new AlertDialog.Builder(this)
                .setView(dialogPromptMealBinding.getRoot())
                .setNegativeButton("취소", null)
                .create();

        List<String> namesOfDiets = new ArrayList<>();
        ArrayAdapter<String> dietAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, namesOfDiets);
        dialogPromptMealBinding.listViewDiets.setAdapter(dietAdapter);

        // 식사 추가 대화상자의 식단 목록 업데이트
        viewModel.getAllDiets().observe(this, diets -> {
            if (diets != null) {
                List<String> names = diets.stream().map(Diet::getName).collect(Collectors.toList());
                namesOfDiets.clear();
                namesOfDiets.addAll(names);
                dietAdapter.notifyDataSetChanged();
            }
        });

        // 아침, 점심, 저녁 식사 리사이클러 뷰 초기화
        CardsAdapter breakfastAdapter = new CardsAdapter(true);
        CardsAdapter lunchAdapter = new CardsAdapter(true);
        CardsAdapter dinnerAdapter = new CardsAdapter(true);
        binding.recyclerBreakfast.setAdapter(breakfastAdapter);
        binding.recyclerLunch.setAdapter(lunchAdapter);
        binding.recyclerDinner.setAdapter(dinnerAdapter);

        UiUtils.setSwipeListener(binding.recyclerBreakfast, position -> viewModel.onMealSwiped(MealTime.BREAKFAST, position));
        UiUtils.setSwipeListener(binding.recyclerLunch, position -> viewModel.onMealSwiped(MealTime.LUNCH, position));
        UiUtils.setSwipeListener(binding.recyclerDinner, position -> viewModel.onMealSwiped(MealTime.DINNER, position));

        // 아침, 점심, 저녁 식사 리사이클러 뷰 업데이트
        viewModel.getBreakfastList().observe(this, diets -> {
            List<String> names = diets.stream().map(Diet::getName).collect(Collectors.toList());
            breakfastAdapter.submitList(names);
        });

        viewModel.getLunchList().observe(this, diets -> {
            List<String> names = diets.stream().map(Diet::getName).collect(Collectors.toList());
            lunchAdapter.submitList(names);
        });

        viewModel.getDinnerList().observe(this, diets -> {
            List<String> names = diets.stream().map(Diet::getName).collect(Collectors.toList());
            dinnerAdapter.submitList(names);
        });

        // 구성비 UI 업데이트
        viewModel.getComposition().observe(this, composition -> {

            // 칼로리 텍스트뷰 업데이트
            String strCalories = StringRes.calories(composition.getCalories());
            binding.textViewCalories.setText(strCalories);

            // 파이 차트 업데이트
            List<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry((float) composition.getRateOfCarbohydrate(), "탄수화물"));
            entries.add(new PieEntry((float) composition.getRateOfProtein(), "단백질"));
            entries.add(new PieEntry((float) composition.getRateOfFats(), "지방"));
            PieDataSet pds = new PieDataSet(entries, "");
            pds.setColors(0xFF555555, 0xFF777777, 0xFF999999);
            pds.setValueTextColor(0xFFEEEEEE);
            pds.setValueTextSize(16f);
            pds.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return Math.round(value * 100) + "%";
                }
            });
            PieData pieData = new PieData(pds);
            binding.pieChart.setData(pieData);
            binding.pieChart.getDescription().setEnabled(false);
            binding.pieChart.invalidate();
        });

        // 회원정보를 표시한다
        viewModel.getCurrentUser().observe(this, user -> {
            // 로그아웃 상태이면 로그인 화면으로 되돌아간다
            if (user == null) {
                finish();
                return;
            }
            String strWelcome = StringRes.welcome(user.getId());
            binding.textViewUserId.setText(strWelcome);
        });

        // 뷰모델에서 발생한 이벤트를 처리한다
        viewModel.getEvent().observe(this, event -> {
            if (event instanceof MainViewModel.Event.NavigateToHistoryScreen) {
                startHistoryActivity();
            } else if (event instanceof MainViewModel.Event.NavigateToDietScreen) {
                startDietActivity();
            } else if (event instanceof MainViewModel.Event.PromptMeal) {
                MealTime mealTime = ((MainViewModel.Event.PromptMeal) event).mealTime;
                showPromptMealDialog(mealTime);
            } else if (event instanceof MainViewModel.Event.ConfirmSave) {
                showConfirmSaveDialog();
            } else if (event instanceof MainViewModel.Event.ConfirmLogout) {
                showConfirmLogoutDialog();
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
    public void onBackPressed() {
        viewModel.onBackClick();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        viewModel.onAuthStateChanged(firebaseAuth);
    }

    private void startHistoryActivity() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    private void startDietActivity() {
        Intent intent = new Intent(this, DietActivity.class);
        startActivity(intent);
    }

    private void showPromptMealDialog(MealTime mealTime) {

        String title = StringRes.mealTime(mealTime) + " 식단 추가";
        dialogPromptMeal.setTitle(title);

        // 식단 클릭 시 뷰모델에 통보, 대화상자 종료
        dialogPromptMealBinding.listViewDiets.setOnItemClickListener((parent, view, position, id) -> {
            if (position != -1) {
                viewModel.onDietClick(mealTime, position);
                dialogPromptMeal.dismiss();
            }
        });

        dialogPromptMeal.show();
    }

    private void showConfirmSaveDialog() {

        new AlertDialog.Builder(this)
                .setTitle("현재 기록 저장")
                .setMessage("현재 작성된 기록을 저장하시겠습니까?")
                .setPositiveButton("저장", (dialog, which) -> viewModel.onSaveClick())
                .setNegativeButton("저장 안 함", (dialog, which) -> viewModel.onNotSaveClick())
                .show();
    }

    private void showConfirmLogoutDialog() {

        new AlertDialog.Builder(this)
                .setTitle("로그아웃")
                .setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", (dialog, which) -> viewModel.onLogoutConfirm())
                .setNegativeButton("취소", null)
                .show();
    }

}





