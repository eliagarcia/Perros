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
import edu.ub.pis2324.xoping.databinding.FragmentCatalegBinding;
import edu.ub.pis2324.perros.presentation.viewmodels.fragments.LogInViewModel;

public class CatalegActivity extends AppCompatActivity {
    /* Attributes */
    private NavController navController;

    AppBarConfiguration appBarConfiguration;
    private FragmentCatalegBinding binding;
    private LogInViewModel logInViewModel;

    /**
     * Called when the activity is being created.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentCatalegBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* Initializations */
    }

    private void initCataleg() {
        /* Set up the navigation controller */
        navController = ( (NavHostFragment) getSupportFragmentManager()
            .findFragmentById(R.id.nav_host_fragment_main) )
            .getNavController();

        /*
          Set up the bottom navigation, indicating the fragments
          that are part of the bottom navigation.
        */
        appBarConfiguration = new AppBarConfiguration.Builder(
            R.id.shoppingFragment,
            R.id.cartFragment,
            R.id.profileFragment
        ).build();

        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.perrete1.setOnClickListener(v -> showHome());

    }

    private void showHome() {
        navController.navigate(R.id.shoppingFragment);
    }
}
