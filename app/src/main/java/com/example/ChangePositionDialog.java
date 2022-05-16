package com.example;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.project531.Interface.ILocation;
import com.example.project531.R;
import com.google.android.material.textfield.TextInputEditText;

public class ChangePositionDialog extends DialogFragment {

    ILocation iLocation;
    TextInputEditText txt_address_loc;

    Button btn_search_loc;

    public ChangePositionDialog(ILocation iLocation) {
        this.iLocation = iLocation;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_position, container,false);


        btn_search_loc = view.findViewById(R.id.btn_search_loc);
        txt_address_loc = view.findViewById(R.id.txt_address_loc);

        btn_search_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iLocation.Done(txt_address_loc.getText().toString());
            }
        });



        return view;
    }


}
