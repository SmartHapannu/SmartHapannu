package Adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ishananuranga.smarthapannu.R;

import java.util.List;

import Interfaces.MainActivityChangeListener;
import Model.PaperProvider;

public class PaperProvidersAdapter extends RecyclerView.Adapter<PaperProvidersAdapter.ViewHolder> {

    private Context context;
    private List<PaperProvider> paperProviders;

    public PaperProvidersAdapter(Context context, List<PaperProvider> paperProviders) {
        this.context = context;
        this.paperProviders = paperProviders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.provider_card,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        PaperProvider paperProvider = paperProviders.get(position);

        //TODO change provider card image
        int id = paperProvider.getProvider_id();
        holder.provider_id.setText(Integer.toString(id));
        final byte[] image = paperProvider.getImage();
        holder.provider_image.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));
        holder.title.setText(paperProvider.getTitle());
        holder.provider_name.setText(paperProvider.getProvider_name());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView provider_id = v.findViewById(R.id.text_view_provider_id);
                TextView title = v.findViewById(R.id.text_view_title);

                ImageView imageView = v.findViewById(R.id.image_view_provider);
                //showPaperListFragment();
                MainActivityChangeListener listener = (MainActivityChangeListener) context;
                listener.changeActivity(provider_id.getText().toString(),title.getText().toString());
            }
        });

    }





    /*public void showPaperListFragment()
    {
        Fragment paperListFragment= new PaperListFragment();
        FragmentChangeListner listner = (FragmentChangeListner) context;
        listner.replaceFragment(paperListFragment);
    }*/

    @Override
    public int getItemCount() {
        return paperProviders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView provider_id;
        public ImageView provider_image;
        public TextView title;
        public TextView provider_name;
        public CardView card_view;

        public ViewHolder(View itemView) {
            super(itemView);

            provider_id = itemView.findViewById(R.id.text_view_provider_id);
            provider_image = itemView.findViewById(R.id.image_view_provider);
            title = itemView.findViewById(R.id.text_view_title);
            provider_name = itemView.findViewById(R.id.text_view_provider_name);
            card_view = itemView.findViewById(R.id.main_card_view);
        }
    }
}
