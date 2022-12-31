package com.penelope.ketodiet.ui.history;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.penelope.ketodiet.data.PreferencesData;
import com.penelope.ketodiet.data.statistic.Composition;
import com.penelope.ketodiet.data.statistic.Statistic;
import com.penelope.ketodiet.data.statistic.StatisticRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HistoryViewModel extends ViewModel {

    private final MutableLiveData<Event> event = new MutableLiveData<>();

    private final LiveData<Composition> composition;

    private final LiveData<List<Pair<LocalDate, Composition>>> compositionDetail;

    @Inject
    public HistoryViewModel(PreferencesData preferencesData, StatisticRepository statisticRepository) {

        YearMonth now = YearMonth.now();
        LiveData<List<Statistic>> statistics = statisticRepository.getMonthlyStatistic(
                preferencesData.getUid(),
                now.getYear(),
                now.getMonthValue());

        composition = Transformations.map(statistics, list ->
                list == null ? null : Composition.fromStatistics(list));

        compositionDetail = Transformations.map(statistics, statisticList -> {
            if (statisticList != null) {
                List<Pair<LocalDate, Composition>> list = new ArrayList<>();
                for (Statistic s : statisticList) {
                    LocalDate date = LocalDate.of(s.getYear(), s.getMonth(), s.getDayOfMonth());
                    Composition composition = new Composition(s.getCarbohydrates(), s.getProtein(), s.getFat());
                    list.add(new Pair<>(date, composition));
                }
                return list;
            }
            return null;
        });
    }

    public LiveData<Event> getEvent() {
        event.setValue(null);
        return event;
    }

    public LiveData<Composition> getComposition() {
        return composition;
    }

    public LiveData<List<Pair<LocalDate, Composition>>> getCompositionDetail() {
        return compositionDetail;
    }


    public static class Event {

    }

}