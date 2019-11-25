package nyandoro.kapadokia.luquluqu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Chat extends AppCompatActivity {


    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //init
        floatingActionButton = findViewById(R.id.floatingBtn);

        //on click option
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Chat.this,"sorry! we cant find your contacts :(", Toast.LENGTH_LONG).show();
            }
        });
    }
}
