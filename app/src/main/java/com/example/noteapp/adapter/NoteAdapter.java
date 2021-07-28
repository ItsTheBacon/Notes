package com.example.noteapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.R;
import com.example.noteapp.interfaces.ItemClickList;
import com.example.noteapp.model.NoteModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    public List<NoteModel> list = new ArrayList<>();
    private ItemClickList onitemClickList;

    public void setItemClickList(ItemClickList itemClickList) {
        this.onitemClickList = itemClickList;
    }

    public void addText(NoteModel model, int index) {
        list.add(0, model);
        notifyDataSetChanged();

    }

    public void setlist(List<NoteModel> modelList, int index) {
        list.clear();
        this.list.addAll(0, modelList);
        notifyDataSetChanged();
    }

    public void delete(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }



    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);

        return new MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        holder.bind(list.get(position));

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else return 0;

    }

public void filterList(ArrayList<NoteModel> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txttitle, txt_time;

        public MyViewHolder(View itemView) {
            super(itemView);

            txttitle = itemView.findViewById(R.id.item_title);
            txt_time = itemView.findViewById(R.id.item_time);
        }

        public void bind(NoteModel model) {
            txttitle.setText(model.getTxtTitle());
            txt_time.setText(model.getDate());
            itemView.setOnClickListener(v ->
                    onitemClickList.CLickItem(getAdapterPosition(), model));

        }
    }

}
