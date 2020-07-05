package se.ju.kaan16ak.phonebookapp;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<ItemModel> arrayList;
    Button fButton;
    Button cwButton;
    EditText userId;
    EditText userName;
    EditText userCompany;
    EditText userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listBookView);
        arrayList = new ArrayList<>();
        fButton = findViewById(R.id.friendsButton);
        cwButton = findViewById(R.id.coworkerButton);

        try {
            JSONObject object = new JSONObject(readJSON());
            JSONArray array = object.getJSONArray("contacts");
            for (int i = 0; i < array.length(); i++) {

                JSONObject jsonObject = array.getJSONObject(i);

                String company = jsonObject.getString("company");
                String id = jsonObject.getString("_id");
                String name = jsonObject.getString("name");
                String phone = jsonObject.getString("phone");

                ItemModel model = new ItemModel();
                model.setId(id);
                model.setName(name);
                model.setCompany(company);
                model.setPhone(phone);
                arrayList.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CustomAdapter adapter = new CustomAdapter(this, arrayList);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {

            LayoutInflater inflater = LayoutInflater.from(this);
            final View view = inflater.inflate(R.layout.alert_main, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            userId =  findViewById(R.id.inputID);
            userName = findViewById(R.id.inputName);
            userCompany = findViewById(R.id.inputCompany);
            userPhone = findViewById(R.id.inputPhone);

            builder.setIcon(R.drawable.add);
            builder.setTitle("Add User");

            builder.setCancelable(false);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                builder.setPositiveButton(getResources().getString(R.string.add), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // add user to array, clear arrayarrayList.clear();

                        JSONObject obj = new JSONObject();
                        obj.put("name", "facebook");

                        obj.put("name", "mkyong.com");
                        obj.put("age", 100);

                        JSONArray list = new JSONArray();
                        list.add("msg 1");
                        list.add("msg 2");
                        list.add("msg 3");

                        obj.put("messages", list);

                        try (FileWriter file = new FileWriter("c:\\projects\\test.json")) {
                            file.write(obj.toJSONString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        System.out.print(obj);





                        /*
                        String id = userId.getText().toString();
                        String company = userCompany.getText().toString();
                        String name = userName.getText().toString();
                        String phone = userPhone.getText().toString();

                        ItemModel model = new ItemModel();
                        model.setId(id);
                        model.setName(name);
                        model.setCompany(company);
                        model.setPhone(phone);
                        arrayList.add(model);*/


                    }
                });

                CustomAdapter adapter = new CustomAdapter(this, arrayList);
                listView.setAdapter(adapter);
            }
            builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.setView(view);
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

   /* public void parseJson (){

        try {
            JSONObject object = new JSONObject(readJSON());
            JSONArray array = object.getJSONArray("contacts");
            for (int i = 0; i < array.length(); i++) {

                JSONObject jsonObject = array.getJSONObject(i);
                String id = jsonObject.getString("_id");
                String name = jsonObject.getString("name");
                String company = jsonObject.getString("company");
                String phone = jsonObject.getString("phone");

                ItemModel model = new ItemModel();
                model.setId(id);
                model.setName(name);
                model.setCompany(company);
                model.setPhone(phone);
                arrayList.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CustomAdapter adapter = new CustomAdapter(this, arrayList);
        listView.setAdapter(adapter);
    }*/


    public void friendsButtonClicked(View view){

        arrayList.clear();
        try {
            JSONObject object = new JSONObject(readJSON());
            JSONArray array = object.getJSONArray("contacts");
            for (int i = 0; i < array.length(); i++) {

                JSONObject jsonObject = array.getJSONObject(i);

                String company = jsonObject.getString("company");

                if (company.length() <= 0 ){
                    String id = jsonObject.getString("_id");
                    String name = jsonObject.getString("name");
                    String phone = jsonObject.getString("phone");

                    ItemModel model = new ItemModel();
                    model.setId(id);
                    model.setName(name);
                    model.setCompany(company);
                    model.setPhone(phone);
                    arrayList.add(model);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CustomAdapter adapter = new CustomAdapter(this, arrayList);
        listView.setAdapter(adapter);
    }



    public void coworkersButtonClicked(View view){

        arrayList.clear();
        try {
            JSONObject object = new JSONObject(readJSON());
            JSONArray array = object.getJSONArray("contacts");
            for (int i = 0; i < array.length(); i++) {

                JSONObject jsonObject = array.getJSONObject(i);

                String company = jsonObject.getString("company");

                if (company.length() > 0 ){
                    String id = jsonObject.getString("_id");
                    String name = jsonObject.getString("name");
                    String phone = jsonObject.getString("phone");

                    ItemModel model = new ItemModel();
                    model.setId(id);
                    model.setName(name);
                    model.setCompany(company);
                    model.setPhone(phone);
                    arrayList.add(model);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CustomAdapter adapter = new CustomAdapter(this, arrayList);
        listView.setAdapter(adapter);
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
}
