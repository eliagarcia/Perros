package edu.ub.pis2324.xoping.presentation.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import androidx.navigation.ui.AppBarConfiguration;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Map;

import edu.ub.pis2324.xoping.R;
import edu.ub.pis2324.xoping.databinding.FragmentCatalegBinding;

public class CatalegActivity extends AppCompatActivity {
    /* Attributes */
    private NavController navController;

    AppBarConfiguration appBarConfiguration;
    private FragmentCatalegBinding binding;


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
        // Obtener referencia a la base de datos Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Obtener una referencia al LinearLayout donde se agregarán los botones
        LinearLayout layout = findViewById(R.id.layout);

        // Obtener los datos de los perros desde Firestore
        db.collection("perros").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    Map<String, Object> perroData = document.getData();
                    if (perroData != null) {
                        // Crear un botón para cada perro
                        Button button = new Button(this);
                        button.setText(perroData.get("nombre").toString());
                        button.setOnClickListener(v -> {
                        });

                        // Agregar el botón al layout
                        layout.addView(button);
                    }
                }
            } else {
                Log.d("CatalegActivity", "Error getting documents: ", task.getException());
            }
        });

    }

    private void showInfo() {
        navController.navigate(R.id.shoppingFragment);
    }
}
