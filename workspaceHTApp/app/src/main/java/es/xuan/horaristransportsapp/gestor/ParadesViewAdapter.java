package es.xuan.horaristransportsapp.gestor;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import es.xuan.horaristransportsapp.R;
import es.xuan.horaristransportsapp.model.Linia;
import es.xuan.horaristransportsapp.model.Parada;
import es.xuan.horaristransportsapp.utils.Utils;

public class ParadesViewAdapter extends ArrayAdapter<Parada> {

    private final String CTE_L2 = "L2";

    // invoke the suitable constructor of the ArrayAdapter class
    public ParadesViewAdapter(@NonNull Context context, ArrayList<Parada> arrayList) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.llistalinieselement, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        Parada currentNumberPosition = getItem(position);

        // Can Rosés
        String paradaText = currentNumberPosition.getNomParada();
        //
        TextView textView1 = currentItemView.findViewById(R.id.tvLlistaLiniaElement);
        if (currentNumberPosition.getSelected() != 'F' &&
            currentNumberPosition.getSelected() != 0)
            textView1.setText(">"+currentNumberPosition.getSelected()+"< " + paradaText);
        else
            textView1.setText(paradaText);
        //
        textView1.setTextColor(getContext().getColor(R.color.black));
        //
        int iColorFons = getContext().getResources().getIdentifier("nivell3", "color", getContext().getPackageName());
        textView1.setBackground(getContext().getResources().getDrawable(iColorFons, getContext().getTheme()));
        //
        if (currentNumberPosition.getSelected() != 'F' &&
                currentNumberPosition.getSelected() != 0)
            textView1.setTypeface(Typeface.DEFAULT_BOLD);
        else
            textView1.setTypeface(Typeface.DEFAULT);
        //
        return currentItemView;
    }

}
