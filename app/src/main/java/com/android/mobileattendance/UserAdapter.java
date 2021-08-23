package com.android.mobileattendance;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAdapter extends BaseAdapter {

    private static final String url = "https://shivaistic-casualti.000webhostapp.com/DeleteProfile.php";

    Activity activity;
    private List<User> item;
    private ArrayList<User> item_list;
    private LayoutInflater inflater;
    private String fullnames;

    public UserAdapter(Activity activity, List<User> item) {
        this.activity = activity;
        this.item = item;
        this.item_list = new ArrayList<User>();
        this.item_list.addAll(item);
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) convertView = inflater.inflate(R.layout.delete_list, null);

        TextView username = (TextView) convertView.findViewById(R.id.username);
        TextView fullname = (TextView) convertView.findViewById(R.id.fullname);
        TextView jabatan = (TextView) convertView.findViewById(R.id.jabatan);
        TextView division = (TextView) convertView.findViewById(R.id.division);
        Button delete = (Button) convertView.findViewById(R.id.delete);

        User user = item.get(position);

        String Username_ = user.getUsername();
        fullnames = user.getTemp();
        username.setText(Username_);
        fullname.setText(user.getFullname());
        jabatan.setText(user.getJabatan());
        division.setText(user.getDivisi());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                post(Username_);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        return convertView;
    }

    public void post(String u){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if(success.equals("0")){
                                Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
                            }
                            if(success.equals("1")){
                                Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
                                Intent adminHome = new Intent(activity, adminHome.class);
                                adminHome.putExtra("fullname", fullnames);
                                activity.startActivity(adminHome);
                                activity.finish();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(activity,"Error ! "+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity,"Volley Error ! "+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", u);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(activity);
        queue.add(stringRequest);
    }
}
