package com.example.imageview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomSheet extends BottomSheetDialogFragment{

    private BottomSheetListener bottomSheetListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout, container,false);

        Button btn_setDate = view.findViewById(R.id.btn_setDate);
        final DatePicker datePicker = view.findViewById(R.id.datePicker);
        btn_setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                bottomSheetListener.onButtonClicked(year, month , day);


                dismiss();
            }
        });
        return view;
    }

    public interface BottomSheetListener{
        void onButtonClicked(int year, int month, int day);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        bottomSheetListener = (BottomSheetListener) context;
    }
}
