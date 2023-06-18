package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    NotesAdapter adapter;
    LinearLayout llRow;
    final int NOTES_ACTIVITY= 1;
    ImageView imgAddNotes;
    RecyclerView recyclerView;

    ArrayList<Notes> notes= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgAddNotes=findViewById(R.id.imgAddNotes);
        llRow=findViewById(R.id.llRow);
        notes.add(new Notes("Meetings","Client1"));
        notes.add(new Notes("Bank accounts","Client1"));
        notes.add(new Notes("Work to Do","Client1"));
        notes.add(new Notes("Diet","Client1"));
        notes.add(new Notes("Routine","Client1"));
        notes.add(new Notes("Passwords"," affan.313 \n affansalim.313 \n Ragnarok.313 \n Jiggy Jiggy 121"));
        notes.add(new Notes("Friends Birthday","Client1"));

        recyclerView=findViewById(R.id.rvlist);

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new NotesAdapter(this, notes);
        recyclerView.setAdapter(adapter);
        imgAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(MainActivity.this,notesFormData.class);
                startActivityForResult(i,NOTES_ACTIVITY);
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);

        //        llRow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Dialog d = new Dialog(MainActivity.this);
//                d.setContentView(R.layout.update);
//                EditText etTitle = d.findViewById(R.id.etTitle);
//                EditText etContent = d.findViewById(R.id.etContent);
//                Button btn = d.findViewById(R.id.btn);
//
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String titleMain = etTitle.getText().toString().trim();
//                        String contentMain = etContent.getText().toString().trim();
//
//                        if (titleMain.isEmpty()) {
//
//                            etTitle.setError("Please enter title");
//                        }
//                        else if (contentMain.isEmpty())
//                        {
//                            etContent.setError("Please enter content");
//                        }
//                        else
//                        {
//                            notes.add(new Notes(titleMain, contentMain));
//                            adapter.notifyItemInserted(notes.size() - 1);
//                            recyclerView.scrollToPosition(notes.size() - 1);
//                            etTitle.setText("");
//                            etContent.setText("");
//                            d.dismiss();
//                        }
//                    }
//                });
//
//                d.show();
//            }
//        });

// Initialize the adapter and set it to the RecyclerView
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NOTES_ACTIVITY && resultCode == RESULT_OK) {
            String tit = data.getStringExtra("title");
            String con = data.getStringExtra("content");
            notes.add(new Notes(tit,con));
            adapter=new NotesAdapter(this,notes);
            recyclerView.setAdapter(adapter);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
