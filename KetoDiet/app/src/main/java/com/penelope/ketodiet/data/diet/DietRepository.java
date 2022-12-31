package com.penelope.ketodiet.data.diet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

import javax.inject.Inject;

public class DietRepository {

    private final CollectionReference dietCollection;


    @Inject
    public DietRepository(FirebaseFirestore firestore) {
        dietCollection = firestore.collection("diets");
    }

    public LiveData<List<Diet>> getAllDiets() {

        MutableLiveData<List<Diet>> data = new MutableLiveData<>();

        dietCollection.orderBy("created", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                   if (error != null) {
                       error.printStackTrace();
                   }
                   if (error != null || value == null) {
                       data.setValue(null);
                       return;
                   }
                   data.setValue(value.toObjects(Diet.class));
                });

        return data;
    }

    public void addDiet(Diet diet, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {

        dietCollection.document(diet.getName()).set(diet)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void deleteDiet(String name, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {

        dietCollection.document(name).delete()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

}





