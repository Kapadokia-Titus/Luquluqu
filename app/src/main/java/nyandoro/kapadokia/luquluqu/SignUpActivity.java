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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputName, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //get firebase with auth instance
        auth = FirebaseAuth.getInstance();

        inputName = findViewById(R.id.reg_email);
        inputPassword = findViewById(R.id.reg_password);
        btnSignIn = findViewById(R.id.sign_in_button);
        btnSignUp = findViewById(R.id.sign_up_button);
        btnResetPassword = findViewById(R.id.btn_reset_password);
        progressBar = findViewById(R.id.progressbar);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(SignUpActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
    }
});

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputName.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(SignUpActivity.this, "email empty", Toast.LENGTH_SHORT).show();
                }
                if( TextUtils.isEmpty(password)){
                    Toast.makeText(SignUpActivity.this, "password required", Toast.LENGTH_SHORT).show();
                }
                if(password.length()<6){
                    Toast.makeText(SignUpActivity.this, "password too short, enter a minimum of 6 characters", Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignUpActivity.this, "registration set successfully"+ task.isSuccessful(), Toast.LENGTH_SHORT).show();
                       progressBar.setVisibility(View.GONE);

                       //listening if the task was successful
                        if(!task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, "authentication error"+ task.getException(), Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
}
