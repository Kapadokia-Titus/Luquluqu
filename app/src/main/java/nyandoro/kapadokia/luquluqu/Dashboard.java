package nyandoro.kapadokia.luquluqu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {

    private TextView tvWeeekInitiative, tvEmployeeSchedule, tvCompanyCulture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvWeeekInitiative = findViewById(R.id.week_initiative);
        tvEmployeeSchedule = findViewById(R.id.employee_schedule);
        tvCompanyCulture =  findViewById(R.id.company_culture);

        onPreview();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private  void onPreview(){

        //when user clicks week's initiative
     tvWeeekInitiative.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             //Toast.makeText(Dashboard.this, "initiative is clicked", Toast.LENGTH_LONG).show();
             Intent intent = new Intent(Dashboard.this, WeeksInitiative.class);
             startActivity(intent);
         }
     });

     //when employee schedule is clicked
        tvEmployeeSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Dashboard.this, "employee is clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Dashboard.this, EmployeeSchedule.class);
                startActivity(intent);
            }
        });

        //when company culture is clicked
        tvCompanyCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Dashboard.this, "company is clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Dashboard.this, CompanyCulture.class);
                   startActivity(intent);
            }
        });
    }
}
