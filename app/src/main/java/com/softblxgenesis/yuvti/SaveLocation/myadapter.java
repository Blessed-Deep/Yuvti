package com.softblxgenesis.yuvti.SaveLocation;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softblxgenesis.yuvti.R;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>
{
    ArrayList<model> dataholder;

    public myadapter(ArrayList<model> dataholder) {
        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_row_format_display,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position)
    {

        holder.name.setText(dataholder.get(position).getName());
        holder.locality.setText(dataholder.get(position).getLocality());

    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }



    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView name, locality;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v ) {
                    int itemPosition = getLayoutPosition();
                    Intent i = new Intent(v.getContext(), ViewItemSavedLocation.class);
                    i.putExtra("name", dataholder.get(itemPosition).getName());
                    i.putExtra("city", dataholder.get(itemPosition).getCity());
                    i.putExtra("state", dataholder.get(itemPosition).getState());
                    i.putExtra("country", dataholder.get(itemPosition).getCountry());
                    i.putExtra("pin", dataholder.get(itemPosition).getPin());
                    i.putExtra("locality", dataholder.get(itemPosition).getLocality());
                    v.getContext().startActivity(i);
                }
            });
            name =(TextView)itemView.findViewById(R.id.displayname);
            locality=(TextView)itemView.findViewById(R.id.displayLocality);

        }
    }

}
