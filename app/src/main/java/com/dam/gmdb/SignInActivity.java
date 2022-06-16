package com.dam.gmdb;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dam.gmdb.commons.Utils;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;

import java.util.Arrays;
import java.util.List;

import com.dam.gmdb.commons.Utils.*;

public class SignInActivity extends AppCompatActivity {

    /** Vars Globales **/
    private View baseview;
    private String message;


    /** Méthode initUI pour les composants du design */
    private void initUI(){
        baseview = findViewById(R.id.mainLayoutSignIn);
    }

    /** Méthode de gestion du bouton **/
    public void startSignUpActivity(View view){
        signUpActivity();
    }

    /** La variable du callback avec le retour du type de signLauncher */
    private final ActivityResultLauncher<Intent> signLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignResult(result);
                }
            }
    );

    /** La méthode pour récupérer le callback de la connexion */
    private void onSignResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if(result.getResultCode() == RESULT_OK) {
            // Connecté !!
            message = getString(R.string.connected);
        } else {
            // Pas connecté
            if(response == null) {
                message = getString(R.string.error);
            } else if(response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                message = getString(R.string.no_internet);
            } else if(response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                message = getString(R.string.unknow_error);
            }
        }
        Utils.showSnackBar(baseview, message);
    }

    /** Méthode de gestion du signUp **/
    private void signUpActivity(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
          new AuthUI.IdpConfig.EmailBuilder().build(),
          new AuthUI.IdpConfig.PhoneBuilder().build(),
          new AuthUI.IdpConfig.GoogleBuilder().build(),
          new AuthUI.IdpConfig.GitHubBuilder().build()
        );

        Intent signUpIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                // Ajoute le théme et le logo
                .setLogo(R.drawable.popcorn) // logo
                .setTheme(R.style.LoginTheme)
                // RGPD
                .setTosAndPrivacyPolicyUrls("https://google.com", "https://yahoo.com")
                // Enregistre l'état de l'utilisateur (connected or not, that is the question?)
                .setIsSmartLockEnabled(true)
                .build();

        signLauncher.launch(signUpIntent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initUI();

    }
}