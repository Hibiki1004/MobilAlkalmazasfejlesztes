package hu.mobil.onlinerajztanfolyam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import android.content.SharedPreferences;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    private static final String LOG_TAG = "LoginTag";

    EditText name;
    EditText password;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getIntent().getIntExtra("SECRET_KEY",0) != 345) {
            Log.e(LOG_TAG,"Secret not matching");
            finish();
        }

        name = findViewById(R.id.name);
        password = findViewById(R.id.password);

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (name.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                        return true;
                    } else {
                        Log.i(LOG_TAG, "Login With Done");
                        login(getWindow().getDecorView().findViewById(android.R.id.content));
                        return false;
                    }
                }
                return true;
            }
        });

        preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);

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
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", name.getText().toString());
        editor.putString("password", password.getText().toString());
        editor.apply();

        Log.i(LOG_TAG,"onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(LOG_TAG,"onDestroy");
    }

    public void login(View view) {
        String NameStr = name.getText().toString();
        String passwordStr = password.getText().toString();

        Log.i(LOG_TAG, "Login as: name: '" + NameStr + "' , password: '" + passwordStr + "'");
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);

        intent.putExtra("SECRET_KEY",344);

        startActivity(intent);

        Log.i(LOG_TAG, "goToRegister");
    }

    public void goToMain(View view) {
        finish();

        Log.i(LOG_TAG, "goToMain");
    }
}