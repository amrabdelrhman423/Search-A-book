package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
    private OnClickItemListener itemListener;
    public interface OnClickItemListener{
        void OnItemClick(int position);
    }
    public void setItemListener(OnClickItemListener itemListener){
        this.itemListener=itemListener;
    }
    public static class viewHolder extends RecyclerView.ViewHolder{
        ImageView image;
         TextView titel,auther;
        public viewHolder(@NonNull View itemView, final OnClickItemListener listener) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.bookimage);
            titel=(TextView)itemView.findViewById(R.id.booktitel);
            auther=(TextView)itemView.findViewById(R.id.bookauther);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener !=null){
                        int postion =getAdapterPosition();
                        if (postion!=RecyclerView.NO_POSITION){
                            listener.OnItemClick(postion);
                        }
                    }
                }
            });
        }
    }

    Context context;
    List<DataClass> dataClasses;

    public Adapter(Context context, List<DataClass> dataClasses) {
        this.context = context;
        this.dataClasses = dataClasses;
    }

    @NonNull
    @Override
    public Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new viewHolder(view,itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.viewHolder holder, int position) {
        DataClass d =dataClasses.get(position);
        holder.titel.setText(d.getTitel());
        holder.auther.setText(d.getAuther());
        if (d.getImageUrl().length()!=0) {
            Glide.with(context)
                    .load(d.imageUrl)
                    .into(holder.image);
        }else {
            holder.image.setImageResource(R.drawable.notebook);
        }

    }

    @Override
    public int getItemCount() {
        return dataClasses.size();
    }
}
