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
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import es.xuan.horaristransportsapp.R;
import es.xuan.horaristransportsapp.model.Favorit;

public class FavoritsViewAdapter extends ArrayAdapter<Favorit> {

    private final String CTE_L2 = "L2";

    // invoke the suitable constructor of the ArrayAdapter class
    public FavoritsViewAdapter(@NonNull Context context, ArrayList<Favorit> arrayList) {

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
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.llistafavoritselement, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        Favorit currentNumberPosition = getItem(position);

        // L5
        String liniaText = Repositori.CTE_REPOSITORI_LINIA + currentNumberPosition.getLinia().getIdLinia();
        //
        TextView textView1 = currentItemView.findViewById(R.id.tvElementLinia);
        textView1.setText(liniaText);
        if (liniaText.equals(CTE_L2))
            textView1.setTextColor(getContext().getColor(R.color.black));
        else
            textView1.setTextColor(getContext().getColor(R.color.white));
        int iColorFons = getContext().getResources().getIdentifier("color"+liniaText, "color", getContext().getPackageName());
        textView1.setBackground(getContext().getResources().getDrawable(iColorFons, getContext().getTheme()));
        // ESTACIÓ RUBÍ+D-CAN ROSÉS
        TextView textView2 = currentItemView.findViewById(R.id.tvElementLiniaSentit);
        textView2.setText(currentNumberPosition.getLinia().getNomLinia());
        // ORIGEN: Països Catalans
        TextView textView3 = currentItemView.findViewById(R.id.tvElementOrigen);
        textView3.setText(Repositori.CTE_REPOSITORI_ORIGEN + ": " + currentNumberPosition.getParadaOrigen().getNomParada());
        TextView textView3a = currentItemView.findViewById(R.id.tvElementOrigenHora);
        textView3a.setText(currentNumberPosition.getParadaOrigen().getHora() +
                " - " + currentNumberPosition.getParadaOrigen().getTempsEspera1() + "m " +
                " - " + currentNumberPosition.getParadaOrigen().getTempsEspera2() + "m");
        // DESTÍ: Can Rosés
        TextView textView4 = currentItemView.findViewById(R.id.tvElementDesti);
        textView4.setText(Repositori.CTE_REPOSITORI_DESTI + ": " + currentNumberPosition.getParadaDesti().getNomParada());
        TextView textView4a = currentItemView.findViewById(R.id.tvElementDestiHora);
        textView4a.setText(currentNumberPosition.getParadaDesti().getHora() +
                " - " + currentNumberPosition.getParadaDesti().getTempsEspera1() + "m " +
                " - " + currentNumberPosition.getParadaDesti().getTempsEspera2() + "m");
        // Colors de Linia i Fons
        LinearLayout llElementLinia = currentItemView.findViewById(R.id.llElementLinia);
        int iColor = getContext().getResources().getIdentifier("color"+ liniaText +"Fons", "color", getContext().getPackageName());
        llElementLinia.setBackground(getContext().getResources().getDrawable(iColor, getContext().getTheme()));
        // then return the recyclable view
        return currentItemView;
    }
}
