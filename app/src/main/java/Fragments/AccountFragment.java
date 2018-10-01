package Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.ishananuranga.smarthapannu.LoginActivity;
import com.ishananuranga.smarthapannu.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private FirebaseAuth mAuth;
    private Button signoutButton;

    private Utlis.DataAdapter mDbHelper;
    private List list;
    private TextView txtEmail;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        mAuth = FirebaseAuth.getInstance();
        txtEmail = view.findViewById(R.id.txtEmail);

        signoutButton = view.findViewById(R.id.btnSignout);

        //TODO delete below line and uncomment commented code block for normal functioning with login
        txtEmail.setText("Login bypassed. For demonstration purposes only");

        /*if (mAuth.getCurrentUser() == null){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            //getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            getActivity().finish();
        }else{

            txtEmail.setText(mAuth.getCurrentUser().getEmail());

            signoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth.signOut();
                    Toast.makeText(getActivity(), "User signed out", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);

                }
            });
        }*/


        return view;
    }

}
