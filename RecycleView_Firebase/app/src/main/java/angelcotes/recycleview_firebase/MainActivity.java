package angelcotes.recycleview_firebase;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import angelcotes.recycleview_firebase.Data.Person;
import angelcotes.recycleview_firebase.Data.Recycler.MyAdapter;

public class MainActivity extends AppCompatActivity {

    private String url = "smsmovil2";
    EditText nameEditText, descEditText;
    Button saveBtn;
    ArrayList<Person> persons = new ArrayList<>();
    RecyclerView rv;
    MyAdapter adapter;
    Firebase firebase;
    private Context information;
    private int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        rv = (RecyclerView) findViewById(R.id.mRecyclerID);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Firebase.setAndroidContext(this);

        firebase = new Firebase("https://" + url +".firebaseio.com/");

        this.refreshData();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Title");

// Set up the input
            final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    url = input.getText().toString();
                    firebase = new Firebase("https://" + url + ".firebaseio.com/");
                    refreshData();
                    Toast.makeText(MainActivity.this, "DataBase was changed", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
        refreshData();
        return super.onOptionsItemSelected(item);
    }


    private void displayDialog(){
        Dialog  d = new Dialog(this);
        d.setTitle("Save in DataBase");
        d.setContentView(R.layout.dialoglayout);

        nameEditText = (EditText) d.findViewById(R.id.nameEditText);
        descEditText = (EditText) d.findViewById(R.id.descEditText);
        saveBtn = (Button) d.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataBase(nameEditText.getText().toString(), descEditText.getText().toString());

                nameEditText.setText("");
                descEditText.setText("");
            }
        });

        d.show();
    }

    private void saveDataBase(String name, String id){
        Person per = new Person();
        per.setNamePerson(name);
        per.setIdPerson(id);

        firebase.child("Person").push().setValue(per);
        Toast.makeText(MainActivity.this, "Data was saved", Toast.LENGTH_SHORT).show();
    }

    private void getUpdate(DataSnapshot dataSnapshot){
        persons.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren()){
            Person per = new Person();
            per.setNamePerson(ds.getValue(Person.class).getNamePerson());
            per.setIdPerson(ds.getValue(Person.class).getIdPerson());

            persons.add(per);
        }
        if (persons.size() > 0){
            adapter = new MyAdapter(MainActivity.this,persons);
            rv.setAdapter(adapter);
            Toast.makeText(MainActivity.this, "Data was Updated", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "Data no is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshData(){
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getUpdate(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getUpdate(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
