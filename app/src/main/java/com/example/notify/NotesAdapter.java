package com.example.notify;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NotesAdapter  extends RecyclerView.Adapter<NotesAdapter.ViewHolder> implements Filterable {
    @NonNull
     Context context;
    ArrayList<Notes> notes;
    ArrayList<Notes> filterCopy;

    TextView heading;
    EditText titleNotes,contentNotes;
    Button btnSubmit;



    public NotesAdapter(MainActivity context, ArrayList<Notes> items)
    {
        this.context=context;
        this.notes=items;
        filterCopy=new ArrayList<>(items);
    }



    public void deleteItem(int position) {
        if (position < 0 || position >= notes.size()) {
            return;
        }

        notes.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_model,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(notes.get(position).title);
        holder.content.setText(notes.get(position).content);


        holder.llRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog(context);
                d.setContentView(R.layout.update);
                EditText etTitle = d.findViewById(R.id.etTitle);
                EditText etContent = d.findViewById(R.id.etContent);
                Button btn = d.findViewById(R.id.btn);
                TextView tvShow=d.findViewById(R.id.tvShow);
                btn.setText("Update");
                tvShow.setText("Update Contact");

                etTitle.setText(notes.get(position).title);
                etContent.setText(notes.get(position).content);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String titleMain = etTitle.getText().toString().trim();
                        String contentMain = etContent.getText().toString().trim();

                        if (titleMain.isEmpty())
                        {
                            etTitle.setError("Please enter title");
                        }
                        else if (contentMain.isEmpty()) {
                            etContent.setError("Please enter content");
                        }
                        else
                        {
                            notes.set(position, new Notes(titleMain, contentMain));
                            notifyItemChanged(position);
                            d.dismiss();
                        }
                    }
                });

                d.show();
            }
        });

        holder.llRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder b=new AlertDialog.Builder(context)
                        .setTitle("Delete note").setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                notes.remove(position);
                                notifyItemRemoved(position);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        b.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,content;
        LinearLayout llRow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.title);
            content= itemView.findViewById(R.id.content);
            llRow=itemView.findViewById(R.id.llRow);

        }
    }


    @Override
    public Filter getFilter() {
        return filter;
    }
    private Filter filter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Notes> filterList=new ArrayList<>();
            if (constraint == null || constraint.length()==0)
            {
                filterList.addAll(filterCopy);
            }
            else {
                String fp=toString().toLowerCase().trim();
                for (Notes item: filterCopy) {
                    if (item.getTitle().toLowerCase().contains(fp)){
                        filterList.add(item);

                    }
                }

            }

            FilterResults result=new FilterResults();
            result.values=filterList;
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            notes.clear();
            notes.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
