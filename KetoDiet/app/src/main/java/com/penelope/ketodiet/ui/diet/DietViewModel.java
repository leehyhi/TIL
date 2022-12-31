package com.penelope.ketodiet.ui.diet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.penelope.ketodiet.data.diet.Diet;
import com.penelope.ketodiet.data.diet.DietRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DietViewModel extends ViewModel {

    private final MutableLiveData<Event> event = new MutableLiveData<>();

    // DB 내의 모든 식단 목록
    private final LiveData<List<Diet>> allDiets;

    // 추가할 식단 입력값
    private String name = "";
    private final MutableLiveData<Double> carbohydrates = new MutableLiveData<>();
    private final MutableLiveData<Double> protein = new MutableLiveData<>();
    private final MutableLiveData<Double> fat = new MutableLiveData<>();

    private final DietRepository dietRepository;


    @Inject
    public DietViewModel(DietRepository dietRepository) {

        this.dietRepository = dietRepository;

        allDiets = dietRepository.getAllDiets();
    }

    public LiveData<Event> getEvent() {
        event.setValue(null);
        return event;
    }

    public LiveData<List<Diet>> getAllDiets() {
        return allDiets;
    }

    public LiveData<Double> getCarbohydrates() {
        return carbohydrates;
    }

    public LiveData<Double> getProtein() {
        return protein;
    }

    public LiveData<Double> getFat() {
        return fat;
    }


    public void onDeleteDietClick(Diet diet) {

        dietRepository.deleteDiet(diet.getName(),
                unused -> event.setValue(new Event.ShowGeneralMessage("음식이 삭제되었습니다")),
                e -> {
                    e.printStackTrace();
                    event.setValue(new Event.ShowGeneralMessage("네트워크를 확인하세요"));
                });
    }

    public void onNameChanged(String value) {
        name = value.trim();
    }

    public void onCarbohydratesChanged(int value) {
        carbohydrates.setValue((double) value);
    }

    public void onProteinChanged(int value) {
        protein.setValue((double) value);
    }

    public void onFatChanged(int value) {
        fat.setValue((double) value);
    }

    public void onSubmitDiet() {

        Double carbohydratesValue = carbohydrates.getValue();
        Double proteinValue = protein.getValue();
        Double fatValue = fat.getValue();

        if (name.isEmpty() || carbohydratesValue == null || proteinValue == null || fatValue == null) {
            event.setValue(new Event.ShowGeneralMessage("모두 입력해주세요"));
            return;
        }

        Diet diet = new Diet(name, carbohydratesValue, proteinValue, fatValue);
        dietRepository.addDiet(diet,
                unused -> event.setValue(new Event.ShowGeneralMessage("음식이 추가되었습니다")),
                e -> {
                    e.printStackTrace();
                    event.setValue(new Event.ShowGeneralMessage("네트워크를 확인하세요"));
                });

        event.setValue(new Event.EmptyUI());
    }


    public static class Event {

        public static class ShowGeneralMessage extends Event {
            public final String message;

            public ShowGeneralMessage(String message) {
                this.message = message;
            }
        }

        public static class EmptyUI extends Event {
        }
    }

}