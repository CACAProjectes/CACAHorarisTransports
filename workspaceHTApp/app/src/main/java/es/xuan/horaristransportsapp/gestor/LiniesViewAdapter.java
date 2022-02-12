package es.xuan.horaristransportsapp.gestor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import es.xuan.horaristransportsapp.R;
import es.xuan.horaristransportsapp.model.Favorit;
import es.xuan.horaristransportsapp.model.Linia;
import es.xuan.horaristransportsapp.utils.Utils;

public class LiniesViewAdapter extends ArrayAdapter<Linia> {

    private final String CTE_L2 = "L2";

    // invoke the suitable constructor of the ArrayAdapter class
    public LiniesViewAdapter(@NonNull Context context, ArrayList<Linia> arrayList) {

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
        Linia currentNumberPosition = getItem(position);

        // L5
        String liniaText = Utils.parserLinia2String(currentNumberPosition);
        String liniaTextID = Repositori.CTE_REPOSITORI_LINIA + currentNumberPosition.getIdLinia();
        //
        TextView textView1 = currentItemView.findViewById(R.id.tvLlistaLiniaElement);
        textView1.setText(liniaText);
        if (liniaTextID.equals(CTE_L2))
            textView1.setTextColor(getContext().getColor(R.color.black));
        else
            textView1.setTextColor(getContext().getColor(R.color.white));
        int iColorFons = getContext().getResources().getIdentifier("color"+liniaTextID, "color", getContext().getPackageName());
        textView1.setBackground(getContext().getResources().getDrawable(iColorFons, getContext().getTheme()));

        return currentItemView;
    }

}
