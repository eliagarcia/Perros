package edu.ub.pis2324.xoping.presentation.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;

import androidx.navigation.fragment.NavHostFragment;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import edu.ub.pis2324.xoping.R;
import edu.ub.pis2324.xoping.databinding.FragmentLogInBinding;
import edu.ub.pis2324.xoping.presentation.viewmodels.fragments.LogInViewModel;

public class LogInActivity extends AppCompatActivity {
    /* Attributes */
    private NavController navController;

    AppBarConfiguration appBarConfiguration;
    private FragmentLogInBinding binding;
    private LogInViewModel logInViewModel;

    /**
     * Called when the activity is being created.
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentLogInBinding.inflate(getLayoutInflater());
        setContentView(R.layout.fragment_log_in);
        setSupportActionBar(binding.btnLogIn);

        /* Initializations */
        initLogIn();

    }

    private void setSupportActionBar(Button btnLogIn) {
        if(btnLogIn != null){
            btnLogIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("LogInActivity", "Button clicked");
                }
            });
        }
    }

    /**
     * Initialize the log in.
     */

    private void initLogIn() {
        binding.btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(binding.etLoginUsername.getText().toString().isEmpty() && binding.etLoginPassword.getText().toString().isEmpty())) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(
                            String.valueOf(binding.etLoginUsername.getText()),
                            String.valueOf(binding.etLoginPassword.getText())
                    );
                    showHome();
                } else {
                    Toast.makeText(LogInActivity.this, "Please fill in the fields", Toast.LENGTH_SHORT).show();

                }
            }

        });


    }

    private void showHome() {
        navController.navigate(R.id.action_logInFragment_to_shoppingFragment);
    }
    @Override
    public boolean onSupportNavigateUp() {
        /* Enable the up navigation: the button shown in the left of the action bar */
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
