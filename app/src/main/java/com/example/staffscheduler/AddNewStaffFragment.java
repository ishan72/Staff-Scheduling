package com.example.staffscheduler;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.staffscheduler.models.Staff;

public class AddNewStaffFragment extends Fragment {

    EditText firstName, lastName, role;
    Button addStaffBtn;

    DBHandler dbHandler;

    public AddNewStaffFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_staff, container, false);
        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastname);
        role = view.findViewById(R.id.role);
        addStaffBtn = view.findViewById(R.id.addStaffBtn);

        dbHandler = new DBHandler(getContext());

        addStaffBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String firstName_value = firstName.getText().toString();
                String lastName_value = lastName.getText().toString();
                String role_value = role.getText().toString();

                if(firstName_value.equals("") || lastName_value.equals("") || role_value.equals("")){
                    Toast.makeText(getContext(),"Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    Staff staff = new Staff(firstName_value, lastName_value, role_value);
                    boolean isInserted = dbHandler.addNewStaff(staff);
                    if (isInserted){
                        Toast.makeText(getContext(),"Staff added successfully", Toast.LENGTH_SHORT).show();
                        StaffListFragment staffListFragment = new StaffListFragment();
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, staffListFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    else{
                        Toast.makeText(getContext(), "Failed to add staff", Toast.LENGTH_SHORT).show();
                    }
                    hideKeyboard(getActivity());
                }
            }
        });
        return view;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}