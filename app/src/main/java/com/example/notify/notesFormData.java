package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class notesFormData extends AppCompatActivity {

    ImageView backArrow;
    EditText titleNotes,contentNotes;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_form_data);
        init();


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tit=titleNotes.getText().toString().trim();
                String con=contentNotes.getText().toString().trim();

                if (tit.isEmpty())
                {
                    titleNotes.setError("Please enter the title");


                }

                if (con.isEmpty())
                {
                    contentNotes.setError("Please enter the context");

                }
                else
                {
                    Intent i=getIntent();
                    i.putExtra("title",tit);
                    i.putExtra("content",con);
                    setResult(RESULT_OK,i);
                    finish();

                }


            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(notesFormData.this,MainActivity.class);
                startActivity(i);
                finish();
                Toast.makeText(notesFormData.this, "No data was entered", Toast.LENGTH_SHORT).show();
            }
        });





    }

    public void init()
    {
        titleNotes=findViewById(R.id.titleNotes);
        contentNotes=findViewById(R.id.contentNotes);
        btnSubmit=findViewById(R.id.btnSubmit);
        backArrow=findViewById(R.id.backArrow);

    }
}