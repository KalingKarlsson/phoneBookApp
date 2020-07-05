package se.ju.kaan16ak.phonebookapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<ItemModel> arrayList;

    public CustomAdapter(Context context, ArrayList<ItemModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public  View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView ==  null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        }
        TextView name, phone, company, id;
        name = convertView.findViewById(R.id.name);
        phone = convertView.findViewById(R.id.phone);
        company = convertView.findViewById(R.id.company);
        id = convertView.findViewById(R.id.id);

        name.setText(arrayList.get(position).getName());
        phone.setText(arrayList.get(position).getPhone());
        company.setText(arrayList.get(position).getCompany());
        id.setText(arrayList.get(position).getId());

        //onclicklistner to display  more info

        return convertView;
    }
}