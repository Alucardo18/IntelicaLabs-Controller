package android.intelica.intelicalabs_controller.view;

import android.content.Context;
import android.intelica.intelicalabs_controller.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BluetoothDevicesList extends BaseAdapter {
    private Context context;
    private int layout;
    private List<String> names;

        public BluetoothDevicesList(Context context, int layout, List<String> names){
            this.context = context;
            this.layout = layout;
            this.names = names;
        }

    @Override
    public int getCount() {
        return this.names.size();
    }

    @Override
    public Object getItem(int position){
        return this.names.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View v = convertView;
        //inflamos la vista con el layout
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        v = layoutInflater.inflate(R.layout.my_listview,null);

        String currentName = names.get(position);
        //currentName = (String) getItem(position);
        TextView textView = (TextView) v.findViewById(R.id.textList);
        textView.setText(currentName);

        return v;

    }
}
