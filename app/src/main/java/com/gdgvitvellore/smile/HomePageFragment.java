package com.gdgvitvellore.smile;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by nikhil on 9/4/16.
 */
public class HomePageFragment extends Fragment {

    ImageButton travelButton, discover,rating,location,size,age, snap;
    Toolbar toolbar;
    int ratingFilter, sizeFilter, ageFilter;
    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;
    GoogleMap googleMap;
    SupportMapFragment mapFragment;
    int count;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        travelButton  = (ImageButton)view.findViewById(R.id.travel_button);
        snap  = (ImageButton)view.findViewById(R.id.snap);
        rating  = (ImageButton)view.findViewById(R.id.rating);
        location  = (ImageButton)view.findViewById(R.id.location);
        size  = (ImageButton)view.findViewById(R.id.size);
        age  = (ImageButton)view.findViewById(R.id.age);
        FontsOverride.overrideFont(getContext(), view, "fonts/Lato-Regular.ttf");

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        googleMap = mapFragment.getMap();
        googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(12.9716,77.5946) , 10.0f) );

        View marker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_map_marker, null);
        TextView numTxt = (TextView) marker.findViewById(R.id.rating);



        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://207.46.139.218:6969/v1/showrev";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            while(count<jsonArray.length()){
                                JSONObject jo = jsonArray.getJSONObject(count);
                                Double lat, lng;
                                        int rating;
                                lat = jo.getDouble("latitude");
                                lng = jo.getDouble("longitude");
                                rating = jo.getInt("smile");
                                switch (rating){
                                    case 0:
                                        googleMap.addMarker(new MarkerOptions()
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_1))
                                                .position(new LatLng(lat, lng)));
                                        break;

                                    case 1:
                                        googleMap.addMarker(new MarkerOptions()
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_1))
                                                .position(new LatLng(lat, lng)));
                                        break;


                                    case 2:
                                        googleMap.addMarker(new MarkerOptions()
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_2))
                                                .position(new LatLng(lat, lng)));
                                        break;


                                    case 3:
                                        googleMap.addMarker(new MarkerOptions()
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_3))
                                                .position(new LatLng(lat, lng)));
                                        break;


                                    case 4:
                                        googleMap.addMarker(new MarkerOptions()
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_4))
                                                .position(new LatLng(lat, lng)));
                                        break;


                                    case 5:
                                        googleMap.addMarker(new MarkerOptions()
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_5))
                                                .position(new LatLng(lat, lng)));
                                        break;

                                }
                                count++;
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

        FontsOverride.overrideFont(getContext(), view, "fonts/Lato-Regular.ttf");
        toolbar = (Toolbar)view.findViewById(R.id.toolbar_home);


        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogViewRating = inflater.inflate(R.layout.dialog_rating_filter, null);
                Toolbar toolbarRating = (Toolbar) dialogViewRating.findViewById(R.id.toolbar);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    toolbarRating.setTitle("Change Rating");
                }
                DiscreteSeekBar discreteSeekBarRating = (DiscreteSeekBar)dialogViewRating.findViewById(R.id.rating_seek);
                discreteSeekBarRating.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
                    @Override
                    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                        ratingFilter = value;
                    }

                    @Override
                    public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                        System.out.println(ratingFilter);
                    }
                });
                dialogBuilder.setView(dialogViewRating);
                FontsOverride.overrideFont(getContext(), dialogViewRating, "fonts/Lato-Regular.ttf");
                dialogBuilder.show();
            }
        });

        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogViewSize = inflater.inflate(R.layout.dialog_size_filter, null);
                Toolbar toolbarRating = (Toolbar) dialogViewSize.findViewById(R.id.toolbar);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    toolbarRating.setTitle("Change Number of People");
                }
                DiscreteSeekBar discreteSeekBarRating = (DiscreteSeekBar)dialogViewSize.findViewById(R.id.size_seek);
                discreteSeekBarRating.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
                    @Override
                    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                        sizeFilter = value;
                    }

                    @Override
                    public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                        System.out.println(ratingFilter);
                    }
                });
                dialogBuilder.setView(dialogViewSize);
                FontsOverride.overrideFont(getContext(), dialogViewSize, "fonts/Lato-Regular.ttf");
                dialogBuilder.show();
            }
        });


        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogViewAge = inflater.inflate(R.layout.dialog_age_filter, null);
                Toolbar toolbarRating = (Toolbar) dialogViewAge.findViewById(R.id.toolbar);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    toolbarRating.setTitle("Change Age");
                }
                DiscreteSeekBar discreteSeekBarRating = (DiscreteSeekBar)dialogViewAge.findViewById(R.id.age_seek);
                discreteSeekBarRating.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
                    @Override
                    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                        ageFilter = value;
                    }

                    @Override
                    public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                        System.out.println(ageFilter);
                    }
                });
                dialogBuilder.setView(dialogViewAge);
                FontsOverride.overrideFont(getContext(), dialogViewAge, "fonts/Lato-Regular.ttf");
                dialogBuilder.show();
            }
        });


        travelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                PlannerFragment frag = new PlannerFragment();
                fragmentTransaction2.addToBackStack("Home Page Fragment");
                fragmentTransaction2.hide(HomePageFragment.this);
                fragmentTransaction2.add(android.R.id.content, frag);
                fragmentTransaction2.commit();
            }
        });

        snap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photo));
                imageUri = Uri.fromFile(photo);
                startActivityForResult(intent, TAKE_PICTURE);
            }
        });
        return view;
    }




}
