package nyandoro.kapadokia.luquluqu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    //declarations
    private CardView initiative, schedule, culture,comm, statistics;

    //firebase listener
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        auth = FirebaseAuth.getInstance();

        //initializations
        initiative = findViewById(R.id.start_initiative);
        schedule = findViewById(R.id.employee_schedule);
        culture = findViewById(R.id.company_culture);
        comm = findViewById(R.id.join_comm);
        statistics = findViewById(R.id.statistics);

        //onClicks
        initiative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(Dashboard.this, WeeksInitiative.class);
                startActivity(intent);
            }
        });


        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, EmployeeSchedule.class);
                startActivity(intent);
            }
        });

        culture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, CompanyCulture.class);
                startActivity(intent);
            }
        });

        comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Chat.class);
                startActivity(intent);
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this, "we have no statistics to display for now", Toast.LENGTH_SHORT).show();
            }
        });



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
