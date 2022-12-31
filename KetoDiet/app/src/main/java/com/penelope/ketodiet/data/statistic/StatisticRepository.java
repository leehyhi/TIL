package com.penelope.ketodiet.data.statistic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

import javax.inject.Inject;

public class StatisticRepository {

    private final CollectionReference userCollection;


    @Inject
    public StatisticRepository(FirebaseFirestore firestore) {
        this.userCollection = firestore.collection("users");
    }

    public void addStatistic(String uid, Statistic statistic, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {

        CollectionReference statisticCollection = userCollection
                .document(uid)
                .collection("statistic");

        statisticCollection.document(statistic.getId()).set(statistic)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public LiveData<List<Statistic>> getMonthlyStatistic(String uid, int year, int month) {

        MutableLiveData<List<Statistic>> data = new MutableLiveData<>();

        CollectionReference statisticCollection = userCollection
                .document(uid)
                .collection("statistic");

        statisticCollection.whereEqualTo("year", year)
                .whereEqualTo("month", month)
                .orderBy("created", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        error.printStackTrace();
                    }
                    if (error != null || value == null) {
                        data.setValue(null);
                        return;
                    }
                    data.setValue(value.toObjects(Statistic.class));
                });

        return data;
    }


}














