package sg.edu.rp.c346.id22022260.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    EditText etEnterTask, etEnterDate;
    Button btnInsert, btnGetTasks;
    TextView tvResults;
    ListView lv;
    ArrayList<String> tasks = new ArrayList<String>();
    Boolean isDescending = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEnterTask = findViewById(R.id.etEnterTask);
        etEnterDate = findViewById(R.id.etEnterDate);
        btnInsert = findViewById(R.id.btnInsert);
        btnGetTasks = findViewById(R.id.btnGetTasks);
        tvResults = findViewById(R.id.tvResults);
        lv = findViewById(R.id.lv);

        ArrayAdapter aaTasks = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        lv.setAdapter(aaTasks);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                db.insertTask(etEnterTask.getText().toString(), etEnterDate.getText().toString());
            }
        });

        btnGetTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);

                ArrayList<String> data = db.getTaskContent();
                ArrayList<Task> taskData = db.getTasks();

                db.close();

                String txt = "";
                for (int i = 0; i < data.size(); i++) {
                    Log.d("Database Content", i + ". " + data.get(i));
                    txt += i + ". " + data.get(i) + "\n";
                }

                tvResults.setText(txt);
                tasks.clear();

                for (int i = 0; i < taskData.size(); i++) {
                    tasks.add(taskData.get(i).toString());
                }

                if (isDescending) {
                    tasks.sort(Comparator.reverseOrder());
                } else {
                    tasks.sort(Comparator.naturalOrder());
                }

                isDescending = !isDescending;

                aaTasks.notifyDataSetChanged();
            }
        });
    }
}