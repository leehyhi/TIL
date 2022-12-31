package com.penelope.ketodiet.data.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class UserRepository {

    private final CollectionReference usersCollection;

    @Inject
    public UserRepository(FirebaseFirestore firestore) {

        usersCollection = firestore.collection("users");
    }

    // DB에 회원정보를 추가하는 메소드

    public void addUser(User user, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {

        usersCollection.document(user.getUid())
                .set(user)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    // DB 에서 특정 회원정보를 가져오는 메소드

    public void getUser(String uid, OnSuccessListener<User> onSuccessListener, OnFailureListener onFailureListener) {

        usersCollection.document(uid).get()
                .addOnSuccessListener(documentSnapshot -> onSuccessListener.onSuccess(documentSnapshot.toObject(User.class)))
                .addOnFailureListener(onFailureListener);
    }

    public LiveData<User> getUser(String uid) {

        MutableLiveData<User> data = new MutableLiveData<>();
        if (uid == null) {
            data.setValue(null);
            return data;
        }

        usersCollection.document(uid).addSnapshotListener((value, error) -> {
           if (error != null) {
               error.printStackTrace();
           }
           if (error != null || value == null) {
               data.setValue(null);
               return;
           }
           data.setValue(value.toObject(User.class));
        });

        return data;
    }

    // DB 에서 모든 회원정보를 (유저아이디 : 회원정보) 형식의 맵으로 가져오는 메소드

    public LiveData<Map<String, User>> getUserMap() {

        MutableLiveData<Map<String, User>> data = new MutableLiveData<>();

        usersCollection.addSnapshotListener((value, error) -> {
            if (error != null) {
                error.printStackTrace();
            }
            if (error != null || value == null) {
                data.setValue(null);
                return;
            }
            Map<String, User> userMap = new HashMap<>();
            for (DocumentSnapshot snapshot : value) {
                User user = snapshot.toObject(User.class);
                if (user != null) {
                    userMap.put(user.getUid(), user);
                }
            }
            data.setValue(userMap);
        });

        return data;
    }

}
