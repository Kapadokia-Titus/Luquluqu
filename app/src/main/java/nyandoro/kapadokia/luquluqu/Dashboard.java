package nyandoro.kapadokia.luquluqu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    //firebase listener
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        auth = FirebaseAuth.getInstance();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    //code to be executed on an option selected


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.accSetting:
                Intent intent = new Intent(Dashboard.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.signOut:
                signOutUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
    @Override
    protected void onPause() {
        super.onPause();
        signOutUser();
    }

    private void signOutUser() {
       auth.signOut();
    }

    //on start method that implements our firebase state listener


}
