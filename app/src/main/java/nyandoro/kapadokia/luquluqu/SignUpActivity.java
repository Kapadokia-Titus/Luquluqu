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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputName, inputPassword,confPassword;
    private Button btnSignIn, btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;


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
        confPassword = findViewById(R.id.reg_conf_password);
        progressBar = findViewById(R.id.progressbar);



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
              String email = inputName.getText().toString();
              String password = inputPassword.getText().toString();
              String pwdConfirm = confPassword.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(pwdConfirm) ){


                    //check if the password and confirm password march
                    if(doStringMatch(password, pwdConfirm)){
                        // we'll call a function that registers our user
                        registerNewUser(inputPassword.getText().toString(), inputPassword.getText().toString());

                    }else{
                        Toast.makeText(SignUpActivity.this, "password mismatch", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(SignUpActivity.this, "missing fields!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //a private method to register our new user
    //the method accepts two parameters i.e email and password
    private void registerNewUser(String email, String password){
        email = inputName.getText().toString().trim();
        password = inputPassword.getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //listening if the task was successful
                if(!task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "authentication error"+ task.getException(), Toast.LENGTH_SHORT).show();
                }else {
                    progressBar.setVisibility(View.GONE);
                    //toasting a success message
                    Toast.makeText(SignUpActivity.this, "Registered successfully ", Toast.LENGTH_SHORT).show();

                    //send a verification email method
                    sendVerificationEmail();
                    //get user data
                        User luquUser = new User();
                        luquUser.setName("kapadokia Titus");
                        luquUser.setPhone("0743378884");
                        luquUser.setPhoto_url("") ;
                        luquUser.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());

                        //database creation
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(luquUser);
                    //take the user to the login screen
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    //a private method that sends email verification
    private void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user !=null){
                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, "check your email for verification", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //report network issues
                    }
                });
            }
    }

    //a boolean method that will be used to check if the values match
    private boolean doStringMatch(String value1, String value2){
            return value1.equals(value2);
    }
}
