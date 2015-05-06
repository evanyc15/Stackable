package ech98.echen.stackable;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by echen on 5/6/15. This is the adapter that controls the content of the
 * fragment_main_authed ListView
 */
public class MyListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> data;

    public MyListAdapter(Context context, ArrayList<String> data) {
        super(context, R.layout.my_list_row_item, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.my_list_row_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.itemtext);
        textView.setText(data.get(position));

        return rowView;
    }
}
