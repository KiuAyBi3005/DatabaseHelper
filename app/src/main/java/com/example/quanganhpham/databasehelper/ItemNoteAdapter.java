package com.example.quanganhpham.databasehelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuangAnhPham on 3/10/2017.
 */

public class ItemNoteAdapter extends RecyclerView.Adapter<ItemNoteAdapter.RecyclerViewHolder> {
    private List<Note> list = new ArrayList<Note>();
    private Context context;

    public ItemNoteAdapter(Context context, List<Note> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Note note = list.get(position);
        holder.text_title.setText(note.getTitle());
        holder.text_content.setText(note.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(int position, Note note) {
        list.add(position, note);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout container;
        public TextView text_title;
        public TextView text_content;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            container = (LinearLayout) itemView.findViewById(R.id.item_container);
            text_title = (TextView) itemView.findViewById(R.id.text_title);
            text_content = (TextView) itemView.findViewById(R.id.text_content);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            MainActivity.showNote(context, list.get(getPosition()).getId());
        }
    }
}
