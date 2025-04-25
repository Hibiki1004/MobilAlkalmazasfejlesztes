package hu.mobil.onlinerajztanfolyam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = "RegisterTag";

    EditText name;
    EditText email;
    EditText password;
    EditText passwordAgain;

    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getIntent().getIntExtra("SECRET_KEY",0) != 344) {
            Log.e(LOG_TAG,"Secret not matching");
            finish();
        }

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        passwordAgain = findViewById(R.id.passwordAgain);

        passwordAgain.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || password.getText().toString().isEmpty() || passwordAgain.getText().toString().isEmpty()) {
                        return true;
                    } else {
                        Log.i(LOG_TAG, "Regiser With Done");
                        register(getWindow().getDecorView().findViewById(android.R.id.content));
                        return false;
                    }
                }
                return true;
            }
        });

        preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);


        name.setText(preferences.getString("name", ""));
        password.setText(preferences.getString("password", ""));

        Log.i(LOG_TAG,"onCreate");
    }

    /*

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(LOG_TAG,"onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(LOG_TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(LOG_TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(LOG_TAG,"onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i(LOG_TAG,"onRestart");
    }

*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG,"onDestroy");
    }

    public void goToLogin(View view) {
        getWindow().getDecorView().findViewById(android.R.id.content).findViewById(R.id.name);
        finish();
    }

    public void register(View view) {
        String nameStr = name.getText().toString();
        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();
        String passwordAgainStr = passwordAgain.getText().toString();

        Log.i(LOG_TAG, "Registered as: name: '" + nameStr + "' , email: '" + emailStr + "' , password: '" + passwordStr + "' , password again: '" + passwordAgainStr + "'");

        if (1==1) {
            goToLogin(getWindow().getDecorView().findViewById(android.R.id.content));
        }
    }
}