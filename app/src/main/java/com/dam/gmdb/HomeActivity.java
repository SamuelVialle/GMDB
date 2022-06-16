package com.dam.gmdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static com.dam.gmdb.commons.NodesNames.*;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    /* Vars Globales*/
    private Context context;
    private RecyclerView rvFilms;
    private AdapterFilms adapterFilms;
    private FirebaseFirestore db;

    /* Initialisation */
    private void init(){
        rvFilms = findViewById(R.id.rvFilms);
        rvFilms.setHasFixedSize(true);
        rvFilms.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,
                false));

        db = FirebaseFirestore.getInstance();
    }

    /* Récupérer les datas */
    private void getDataFromFirestore(){
        Query query = db.collection(TABLE_FILM).orderBy(KEY_TITRE);
        Log.i(TAG, "getDataFromFirestore: " + query);
        FirestoreRecyclerOptions<ModelFilms> films =
            new FirestoreRecyclerOptions.Builder<ModelFilms>()
                    .setQuery(query, ModelFilms.class)
                    .build();

        adapterFilms = new AdapterFilms(films);

        rvFilms.setAdapter(adapterFilms);
    }

    /* Vérifier si les datas sont présentes dans la db */
    private void addSampleData(){
        SharedPreferences sharedPreferences = getSharedPreferences(R.class.getPackage().getName()
                     + ".prefs", Context.MODE_PRIVATE);

        if(!sharedPreferences.getBoolean(UPLOAD_PREFS, false)) {
            AddSampleDatasToFirebase.addDatasToFireBase(getApplicationContext());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        addSampleData();
        getDataFromFirestore();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser curentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(curentUser == null){
            startActivity(new Intent(HomeActivity.this, SignInActivity.class));
        } else {
            adapterFilms.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterFilms.stopListening();
    }
}