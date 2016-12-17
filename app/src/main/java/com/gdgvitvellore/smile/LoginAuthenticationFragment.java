package com.gdgvitvellore.smile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nikhil on 4/4/16.
 */
public class LoginAuthenticationFragment extends Fragment {
    EditText emailET, pass;
    Button loginButton;
    public final String EMAIL = "email";
    SharedPreferences sharedPreferences;
    String email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_auth, container, false);
        emailET = (EditText) view.findViewById(R.id.emailAuth);
        pass = (EditText) view.findViewById(R.id.passAuth);
        loginButton = (Button) view.findViewById(R.id.loginButton);
        FontsOverride.overrideFont(getContext(), view, "fonts/Lato-Regular.ttf");
        FontsOverride.overrideFont(getContext(), view.findViewById(R.id.loginButton), "fonts/Lato-Bold.ttf");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = sharedPreferences.getString("email",emailET.getText().toString().trim());
                if (emailET == null || pass == null) {
                    Toast.makeText(getActivity(), "Please enter the credentials", Toast.LENGTH_SHORT).show();
                } else {
                    ConnectivityManager connMgr = (ConnectivityManager)
                            getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(EMAIL,emailET.getText().toString().trim());
                        editor.apply();
                        RequestQueue queue = Volley.newRequestQueue(getContext());
                        String url = "http://207.46.139.218:6969/v1/login?email="+emailET.getText().toString().trim()+"&pass="+pass.getText().toString().trim();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        System.out.println(response);
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            boolean respone = jsonObject.getBoolean("response");
                                            if(respone){
                                                Intent main = new Intent(getActivity(),MainActivity.class);
                                                startActivity(main);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                },
                                new com.android.volley.Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                        Log.e("error",error.toString());
                                    }
                                });
                        queue.add(stringRequest);
                    } else
                        Toast.makeText(getActivity(), "Network unavailable at the moment", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

}
