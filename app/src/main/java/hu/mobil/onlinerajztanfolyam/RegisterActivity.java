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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = "RegisterTag";

    EditText name;
    EditText email;
    EditText password;
    EditText passwordAgain;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;


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

        mAuth = FirebaseAuth.getInstance();

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
        Intent intent = new Intent(this,LoginActivity.class);

        intent.putExtra("SECRET_KEY",345);

        startActivity(intent);
        finish();
    }
    public void goAndMain(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

        Log.i(LOG_TAG, "goToMain");
    }
    public void register(View view) {
        String nameStr = name.getText().toString();
        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();
        String passwordAgainStr = passwordAgain.getText().toString();

        Log.i(LOG_TAG, "Registered as: name: '" + nameStr + "' , email: '" + emailStr + "' , password: '" + passwordStr + "' , password again: '" + passwordAgainStr + "'");

        if (passwordStr.equals(passwordAgainStr)) {
            mAuth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(LOG_TAG, "User created successfully");
                        goAndMain(getWindow().getDecorView().findViewById(android.R.id.content));
                        finish();
                    } else {
                        Log.d(LOG_TAG, "User was't created successfully:", task.getException());
                        Toast.makeText(RegisterActivity.this, "User was't created successfully:", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}