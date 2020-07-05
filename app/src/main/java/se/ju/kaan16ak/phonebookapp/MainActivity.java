package se.ju.kaan16ak.phonebookapp;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<ItemModel> arrayList;
    Button fButton;
    Button cwButton;
    ImageButton contactIB;
    EditText userId;
    EditText userName;
    EditText userCompany;
    EditText userPhone;
    EditText changeUserName;
    EditText changeUserId;
    EditText changeUserCompany;
    EditText changeUserPhone;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listBookView);
        arrayList = new ArrayList<>();

        fButton = findViewById(R.id.friendsButton);
        cwButton = findViewById(R.id.coworkerButton);
        contactIB = findViewById(R.id.addUserButton);

        modifyContactInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void parseJson(){
        JSONParser parser = new JSONParser();
        try {

            JSONObject object = (JSONObject) parser.parse(readJSON());
            JSONArray array = (JSONArray) object.get("contacts");

            for (int i = 0; i < array.size(); i++) {

                JSONObject jsonObject = (JSONObject) array.get(i);
                String company = (String) jsonObject.get("company");

                String id = (String) jsonObject.get("_id");
                String name = (String) jsonObject.get("name");
                String phone = (String) jsonObject.get("phone");

                ItemModel friend = new ItemModel();
                friend.setId(id);
                friend.setName(name);
                friend.setCompany(company);
                friend.setPhone(phone);
                arrayList.add(friend);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CustomAdapter adapter = new CustomAdapter(this, arrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void addUserButtonClicked(View vv) {

        final CustomAdapter addAdapter = new CustomAdapter(this, arrayList);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View addView = inflater.inflate(R.layout.add_user_layout, null);

        final AlertDialog builder = new AlertDialog.Builder(MainActivity.this).create();

        builder.setIcon(R.drawable.add);
        builder.setTitle("Add User");

        Button add = addView.findViewById(R.id.addButton);
        Button cancel = addView.findViewById(R.id.cancelAddButton);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = builder.findViewById(R.id.inputName);
                userId = builder.findViewById(R.id.inputID);
                userCompany = builder.findViewById(R.id.inputCompany);
                userPhone = builder.findViewById(R.id.inputPhone);

                String addUserName = userName.getText().toString();
                String addUserId = userId.getText().toString();
                String addUserCompany = userCompany.getText().toString();
                String addUserPhone = userPhone.getText().toString();

                if (arrayList.contains(addUserId)) {
                    Toast.makeText(getBaseContext(), "User is alredasy in contact-list", Toast.LENGTH_LONG).show();
                } else if (addUserName == null || addUserName.equals("")) {
                    Toast.makeText(getBaseContext(), "Invalid input, is empty", Toast.LENGTH_LONG).show();
                } else if (addUserPhone.matches("[0-9]") && addUserPhone.length() > 2) {
                    Toast.makeText(getBaseContext(), "Invalid phone number", Toast.LENGTH_LONG).show();
                } else {
                    ItemModel friend = new ItemModel();
                    friend.setName(addUserName);
                    friend.setId(addUserId);
                    friend.setCompany(addUserCompany);
                    friend.setPhone(addUserPhone);
                    arrayList.add(friend);

                    listView.setAdapter(addAdapter);
                    addAdapter.notifyDataSetChanged();
                    builder.cancel();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
            }
        });

        if (addView.getParent() != null) {
            ((ViewGroup) addView.getParent()).removeView(addView);
        }

        builder.setView(addView);
        builder.show();
    }

    public void friendsButtonClicked(View view) {

        ArrayList <ItemModel> arrayListCopy = new ArrayList<>();

        parseJson();
        try {
            for (int i = 0; i < arrayList.size(); i++) {

                if (arrayList.get(i).getCompany().length() < 1) {

                    String id = arrayList.get(i).getId();
                    String name = arrayList.get(i).getName();
                    String company = arrayList.get(i).getCompany();
                    String phone = arrayList.get(i).getPhone();

                    if (!arrayList.contains(id)) {
                        ItemModel friend = new ItemModel();
                        friend.setId(id);
                        friend.setName(name);
                        friend.setCompany(company);
                        friend.setPhone(phone);
                        arrayListCopy.add(friend);

                    }
                }
            }
            arrayList = arrayListCopy;
        } catch (Exception e) {
            e.printStackTrace();
        }

        CustomAdapter adapter = new CustomAdapter(this, arrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void coworkersButtonClicked(View view) {

        ArrayList <ItemModel> arrayListCopy = new ArrayList<>();

        parseJson();
        try {
            for (int i = 0; i < arrayList.size(); i++) {

                if (arrayList.get(i).getCompany().length() > 0) {

                    String id = arrayList.get(i).getId();
                    String name = arrayList.get(i).getName();
                    String company = arrayList.get(i).getCompany();
                    String phone = arrayList.get(i).getPhone();

                    if (!arrayList.contains(id)) {
                        ItemModel friend = new ItemModel();
                        friend.setId(id);
                        friend.setName(name);
                        friend.setCompany(company);
                        friend.setPhone(phone);
                        arrayListCopy.add(friend);

                    }
                }
            }
            arrayList = arrayListCopy;
        } catch (Exception e) {
            e.printStackTrace();
        }

        CustomAdapter adapter = new CustomAdapter(this, arrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public String readJSON() {
        String json = null;
        try {
            // Opening data.json file
            InputStream inputStream = getAssets().open("phoneBook.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            inputStream.read(buffer);
            inputStream.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return json;
        }
        return json;
    }

    public void modifyContactInfo() {

        final CustomAdapter adapter = new CustomAdapter(this, arrayList);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.modify_user_layout, null);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, final int position, long id) {
                final AlertDialog ad = new AlertDialog.Builder(MainActivity.this).create();
                ad.setTitle("Update or Delete?");
                ad.setMessage("Here you can change or delete " + arrayList.get(position).getName() + " information");


                Button update = view.findViewById(R.id.updateButton);
                Button cancel = view.findViewById(R.id.cancelButton);
                Button delete = view.findViewById(R.id.deleteButton);

                ad.setCancelable(false);

                update.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        changeUserName = ad.findViewById(R.id.changeName);
                        changeUserId = ad.findViewById(R.id.changeID);
                        changeUserCompany = ad.findViewById(R.id.changeCompany);
                        changeUserPhone = ad.findViewById(R.id.changePhone);

                        String changeName = changeUserName.getText().toString();
                        String changeId = changeUserId.getText().toString();
                        String changeCompany = changeUserCompany.getText().toString();
                        String changePhone = changeUserPhone.getText().toString();

                        ItemModel friend = new ItemModel();
                        if(changeName.isEmpty() || changeId.isEmpty() || changePhone.isEmpty()){
                            Toast.makeText(getBaseContext(), "Fill in text first", Toast.LENGTH_LONG).show();
                        }
                        else {
                            friend.setName(changeName);
                            friend.setId(changeId);
                            friend.setCompany(changeCompany);
                            friend.setPhone(changePhone);
                            arrayList.set(position, friend);

                            listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            ad.cancel();
                        }
                        ((EditText) ad.findViewById(R.id.changeName)).setText("");
                        ((EditText) ad.findViewById(R.id.changeName)).setHint("Name: ");

                        ((EditText) ad.findViewById(R.id.changeID)).setText("");
                        ((EditText) ad.findViewById(R.id.changeID)).setHint("ID: ");

                        ((EditText) ad.findViewById(R.id.changeCompany)).setText("");
                        ((EditText) ad.findViewById(R.id.changeCompany)).setHint("Company: ");

                        ((EditText) ad.findViewById(R.id.changePhone)).setText("");
                        ((EditText) ad.findViewById(R.id.changePhone)).setHint("Phone: ");

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ad.cancel();
                    }
                });
                delete.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        ItemModel contact = arrayList.get(position);
                        arrayList.remove(contact);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        ad.cancel();
                    }
                });

                if (view.getParent() != null) {
                    ((ViewGroup) view.getParent()).removeView(view);
                }
                ad.setView(view);
                ad.show();
            }
        });
    }
}