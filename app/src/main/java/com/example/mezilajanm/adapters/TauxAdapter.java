package com.example.mezilajanm.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mezilajanm.R;
import com.example.mezilajanm.models.TauxDeChange;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TauxAdapter extends RecyclerView.Adapter<TauxAdapter.ViewHolder> {

    private ArrayList<TauxDeChange> taux;
    private Context context;

    public TauxAdapter(ArrayList<TauxDeChange> tauxDeChanges,Context mContext) {
        this.taux = tauxDeChanges;
        this.context=mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView achat, vente, devise, date,bank;
        public ImageView logo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.txtDate);
            achat = itemView.findViewById(R.id.txtAchat2);
            devise = itemView.findViewById(R.id.txtDevise);
            vente = itemView.findViewById(R.id.txtVente2);
            bank = itemView.findViewById(R.id.txtBank);
            logo = itemView.findViewById(R.id.ivLogo);
        }
    }

    @NonNull
    @Override
    public TauxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View tauxView = layoutInflater.inflate(R.layout.item_bank, viewGroup, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(tauxView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TauxAdapter.ViewHolder viewHolder, int i) {
        // Get the data model based on position
        TauxDeChange tauxDeChange = taux.get(i);
        viewHolder.date.setText(tauxDeChange.getDate());
        //viewHolder.devise.setText(tauxDeChange.getDevise());
        viewHolder.achat.setText(tauxDeChange.getAchat());
        viewHolder.vente.setText(tauxDeChange.getVente());
        viewHolder.bank.setText(tauxDeChange.getBank());
       // Picasso.with(context).load(tauxDeChange.getLogo()).into(viewHolder.logo);

        Picasso.with(context).load(tauxDeChange.getLogo())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(viewHolder.logo);

    }

    @Override
    public int getItemCount() {
        return taux.size();
    }
}
