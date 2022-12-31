package com.penelope.ketodiet.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.penelope.ketodiet.data.MealTime;
import com.penelope.ketodiet.data.PreferencesData;
import com.penelope.ketodiet.data.diet.Diet;
import com.penelope.ketodiet.data.diet.DietRepository;
import com.penelope.ketodiet.data.statistic.Composition;
import com.penelope.ketodiet.data.statistic.Statistic;
import com.penelope.ketodiet.data.statistic.StatisticRepository;
import com.penelope.ketodiet.data.user.User;
import com.penelope.ketodiet.data.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private final MutableLiveData<Event> event = new MutableLiveData<>();

    private final LiveData<List<Diet>> allDiets;

    private final MutableLiveData<List<Diet>> breakfastList;
    private final MutableLiveData<List<Diet>> lunchList;
    private final MutableLiveData<List<Diet>> dinnerList;

    private final LiveData<Composition> composition;

    private final LiveData<User> currentUser;

    private final StatisticRepository statisticRepository;
    private final PreferencesData preferencesData;
    private final FirebaseAuth firebaseAuth;


    @Inject
    public MainViewModel(DietRepository dietRepository, StatisticRepository statisticRepository,
                         PreferencesData preferencesData, UserRepository userRepository,
                         FirebaseAuth firebaseAuth) {

        this.statisticRepository = statisticRepository;
        this.preferencesData = preferencesData;
        this.firebaseAuth = firebaseAuth;

        allDiets = dietRepository.getAllDiets();

        breakfastList = new MutableLiveData<>(new ArrayList<>());
        lunchList = new MutableLiveData<>(new ArrayList<>());
        dinnerList = new MutableLiveData<>(new ArrayList<>());

        composition = Transformations.switchMap(breakfastList, breakfast ->
                Transformations.switchMap(lunchList, lunch ->
                        Transformations.map(dinnerList, dinner -> {
                            List<Diet> merged = new ArrayList<>();
                            merged.addAll(breakfast);
                            merged.addAll(lunch);
                            merged.addAll(dinner);
                            return Composition.fromDiets(merged);
                        })));

        LiveData<String> currentUid = preferencesData.getUidLive();
        currentUser = Transformations.switchMap(currentUid, userRepository::getUser);
    }

    public LiveData<Event> getEvent() {
        event.setValue(null);
        return event;
    }

    public LiveData<List<Diet>> getAllDiets() {
        return allDiets;
    }

    public LiveData<List<Diet>> getBreakfastList() {
        return breakfastList;
    }

    public LiveData<List<Diet>> getLunchList() {
        return lunchList;
    }

    public LiveData<List<Diet>> getDinnerList() {
        return dinnerList;
    }

    public LiveData<Composition> getComposition() {
        return composition;
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }


    public void onHistoryClick() {

        Composition compositionValue = composition.getValue();
        assert compositionValue != null;

        if (compositionValue.getCalories() == 0) {
            event.setValue(new Event.NavigateToHistoryScreen());
        } else {
            event.setValue(new Event.ConfirmSave());
        }
    }

    public void onSaveClick() {

        Composition compositionValue = composition.getValue();
        assert compositionValue != null;

        Statistic statistic = new Statistic(
                compositionValue.getCarbohydrates(),
                compositionValue.getProtein(),
                compositionValue.getFat());

        statisticRepository.addStatistic(preferencesData.getUid(), statistic,
                unused -> event.setValue(new Event.NavigateToHistoryScreen()),
                e -> {
                    e.printStackTrace();
                    event.setValue(new Event.ShowGeneralMessage("네트워크를 확인하세요"));
                });
    }

    public void onNotSaveClick() {
        event.setValue(new Event.NavigateToHistoryScreen());
    }

    public void onAddDietClick() {
        event.setValue(new Event.NavigateToDietScreen());
    }

    public void onAddMealClick(MealTime mealTime) {
        event.setValue(new Event.PromptMeal(mealTime));
    }

    public void onDietClick(MealTime mealTime, int position) {

        List<Diet> diets = allDiets.getValue();
        if (diets == null) {
            return;
        }

        MutableLiveData<List<Diet>> mealList;
        switch (mealTime) {
            default:
            case BREAKFAST:
                mealList = breakfastList;
                break;
            case LUNCH:
                mealList = lunchList;
                break;
            case DINNER:
                mealList = dinnerList;
                break;
        }

        List<Diet> oldList = mealList.getValue();
        assert oldList != null;

        List<Diet> newList = new ArrayList<>(oldList);
        newList.add(diets.get(position));
        mealList.setValue(newList);
    }

    public void onMealSwiped(MealTime mealTime, int position) {

        MutableLiveData<List<Diet>> mealList;
        switch (mealTime) {
            default:
            case BREAKFAST:
                mealList = breakfastList;
                break;
            case LUNCH:
                mealList = lunchList;
                break;
            case DINNER:
                mealList = dinnerList;
                break;
        }

        List<Diet> oldList = mealList.getValue();
        assert oldList != null;

        List<Diet> newList = new ArrayList<>(oldList);
        newList.remove(position);
        mealList.setValue(newList);
    }

    public void onLogoutClick() {
        event.setValue(new Event.ConfirmLogout());
    }

    public void onBackClick() {
        event.setValue(new Event.ConfirmLogout());
    }

    public void onLogoutConfirm() {
        firebaseAuth.signOut();
    }

    public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
        // 파이어베이스 로그아웃이 감지되면 앱 로그아웃 처리한다
        if (firebaseAuth.getUid() == null) {
            preferencesData.setUid(null);
        }
    }


    public static class Event {

        public static class ShowGeneralMessage extends Event {
            public final String message;
            public ShowGeneralMessage(String message) {
                this.message = message;
            }
        }

        public static class NavigateToHistoryScreen extends Event {
        }

        public static class NavigateToDietScreen extends Event {
        }

        public static class PromptMeal extends Event {
            public final MealTime mealTime;

            public PromptMeal(MealTime mealTime) {
                this.mealTime = mealTime;
            }
        }

        public static class ConfirmSave extends Event {
        }

        public static class ConfirmLogout extends Event {
        }
    }

}





