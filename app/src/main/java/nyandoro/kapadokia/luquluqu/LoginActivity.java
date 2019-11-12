package nyandoro.kapadokia.luquluqu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

  private EditText inputEmail, inputPassword;
  private FirebaseAuth auth;
  private ProgressBar progressBar;
  private FirebaseAuth.AuthStateListener authStateListener;
  private Button btn_signup, btn_login, btn_resetPassword;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);



//        initializations
    inputEmail = findViewById(R.id.log_email);
    inputPassword = findViewById(R.id.log_password);
    // firebase auth
    auth = FirebaseAuth.getInstance();

    progressBar = findViewById(R.id.progressbar);
    btn_login = findViewById(R.id.log_btnLogin);
    btn_signup = findViewById(R.id.log_btnRegister);
    btn_resetPassword = findViewById(R.id.log_btnResetPassword);


    //using the auth state listener

    authStateListener = new FirebaseAuth.AuthStateListener() {
      @Override
      public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        //redirecting intents
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
          Intent intent = new Intent(LoginActivity.this, Dashboard.class);
          startActivity(intent);
        }

      }
    };

    //execute this when the signup button is clicked
    btn_signup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent  = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
      }
    });

    //execute this code when the reset button is clicked
    btn_resetPassword.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent  = new Intent(LoginActivity.this, ResetPasswordActivity.class);
        startActivity(intent);
      }
    });

    //login functionalities
    btn_login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        // initialization
        String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();

        //checking if the fields are empty
        if(TextUtils.isEmpty(email)){
          Toast.makeText(LoginActivity.this, "email required", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
          Toast.makeText(LoginActivity.this, "password required", Toast.LENGTH_SHORT).show();
        }

        //code to be performed if the fields are empty
        //set progressbar visibility
        progressBar.setVisibility(View.VISIBLE);

        //authenticating the user
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            //catching if sign up was sucessful
            if(!task.isSuccessful()){
              if(password.length()<6){
                inputPassword.setError(getString(R.string.minimum_password));
              }
              else{
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this,"authentication failed", Toast.LENGTH_SHORT).show();
              }
            }
            else{
              Intent intent = new Intent(LoginActivity.this, Dashboard.class);
              startActivity(intent);
              finish();
            }
          }
        });
      }
    });
  }

  //creating on start method

  @Override
  protected void onStart() {
    super.onStart();
    FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
  }


  //on destroy

  @Override
  protected void onDestroy() {
    super.onDestroy();

    if (authStateListener !=null){
      FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
    }

  }


}