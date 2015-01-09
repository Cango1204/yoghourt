package com.example.hbjia.pager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hbjia.http.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFrament extends Fragment {

    public ViewPagerFrament() {
        // Required empty public constructor
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_pager_frament, container, false);
        TextView textView = (TextView) v.findViewById(R.id.view_pager_text);
        ImageView imageView = (ImageView) v.findViewById(R.id.view_pager_image);

        Bundle bundle = getArguments();
        if(bundle != null) {
            int upImageId = bundle.getInt("upImageId");
            if(upImageId != 0) {
                imageView.setImageResource(upImageId);
            }
            String text = bundle.getString("text");
            textView.setText(text);
        }
        return v;
    }


}
