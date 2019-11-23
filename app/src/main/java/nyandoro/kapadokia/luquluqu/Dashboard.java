package nyandoro.kapadokia.luquluqu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    //firebase listener
    private FirebaseAuth auth;

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

            case R.id.chat:
                Intent chatIntent = new Intent(Dashboard.this, Chat.class);
                startActivity(chatIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
    @Override
    protected void onPause() {
        super.onPause();

    }

    private void signOutUser(){

      auth.getCurrentUser();
      Intent intent = new Intent(this, LoginActivity.class);
      auth.signOut();
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
    }

    //on start method that implements our firebase state listener


}
