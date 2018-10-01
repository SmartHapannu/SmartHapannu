package Fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ishananuranga.smarthapannu.R;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import Adapters.PaperProvidersAdapter;
import Model.PaperProvider;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private List<PaperProvider> paperProviders;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private Utlis.DataAdapter mDbHelper;
    private static Cursor paperProvidersData;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.provider_recyclerview);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        paperProviders = new ArrayList<>();
         loadPaperProviders();

        adapter = new PaperProvidersAdapter(getActivity(),paperProviders);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void loadPaperProviders() {
        mDbHelper = new Utlis.DataAdapter(getActivity());
        mDbHelper.createDatabase();
        mDbHelper.open();

        paperProvidersData = mDbHelper.getTestData();

        paperProvidersData.moveToFirst();

        do {
            int provider_id = paperProvidersData.getInt(paperProvidersData.getColumnIndex("Provider_Id"));
            String title = paperProvidersData.getString(paperProvidersData.getColumnIndex("Title"));
            String provider_name = paperProvidersData.getString(paperProvidersData.getColumnIndex("Provider_Name"));
            byte[] image = paperProvidersData.getBlob(paperProvidersData.getColumnIndex("Image"));
            String provider_Description = paperProvidersData.getString(paperProvidersData.getColumnIndex("Description"));

            PaperProvider paperProvider = new PaperProvider(provider_id,title,provider_name,provider_Description,image);
            paperProviders.add(paperProvider);
        }while (paperProvidersData.moveToNext());

        mDbHelper.close();
    }

}
