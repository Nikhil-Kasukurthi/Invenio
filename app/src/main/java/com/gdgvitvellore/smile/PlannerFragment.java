package com.gdgvitvellore.smile;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by nikhil on 15/4/16.
 */
public class PlannerFragment extends Fragment {
    TextView location,hotel,placeVisit,restaurant;
    ImageButton back;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planner, container, false);
        FontsOverride.overrideFont(getContext(), view, "fonts/Lato-Regular.ttf");
        location =(TextView)view.findViewById(R.id.location_planner);
        hotel =(TextView)view.findViewById(R.id.hotel_planner);
        placeVisit =(TextView)view.findViewById(R.id.place_visit);
        restaurant =(TextView)view.findViewById(R.id.restaurant_planner);
        FontsOverride.overrideFont(getContext(), view.findViewById(R.id.location_planner), "fonts/Lato-Bold.ttf");
        FontsOverride.overrideFont(getContext(), view.findViewById(R.id.hotel_planner), "fonts/Lato-Bold.ttf");
        FontsOverride.overrideFont(getContext(), view.findViewById(R.id.place_visit), "fonts/Lato-Bold.ttf");
        FontsOverride.overrideFont(getContext(), view.findViewById(R.id.restaurant_planner), "fonts/Lato-Bold.ttf");
        back = (ImageButton)view.findViewById(R.id.back_button);
        LinearLayout l1 = (LinearLayout)view.findViewById(R.id.blue1);
        LinearLayout l2 = (LinearLayout)view.findViewById(R.id.blue2);

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"More places coming soon",Toast.LENGTH_SHORT).show();
            }
        });

        l2.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                HotelSelectorFragment frag = new HotelSelectorFragment();
               /* Slide slideTransition = new Slide(Gravity.TOP);
                slideTransition.setDuration(500);
                frag.setEnterTransition(slideTransition);
                frag.setSharedElementEnterTransition(slideTransition);*/
                fragmentTransaction2.addToBackStack("Planner fragment");
                fragmentTransaction2.hide(PlannerFragment.this);
                fragmentTransaction2.add(android.R.id.content, frag);
                fragmentTransaction2.addSharedElement(hotel,"shit");
                fragmentTransaction2.commit();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                HomePageFragment frag = new HomePageFragment();
                fragmentTransaction2.addToBackStack("Planner fragment");
                fragmentTransaction2.hide(PlannerFragment.this);
                fragmentTransaction2.add(android.R.id.content, frag);
                fragmentTransaction2.commit();
            }
        });

        return view;
    }
}
