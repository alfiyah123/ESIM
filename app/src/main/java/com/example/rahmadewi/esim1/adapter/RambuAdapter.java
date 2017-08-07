package com.example.rahmadewi.esim1.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rahmadewi.esim1.R;
import com.example.rahmadewi.esim1.models.rambu.ResultItem;

import java.io.File;
import java.util.List;

/**
 * Created by Rahmadewi on 8/1/2017.
 */

public class RambuAdapter extends RecyclerView.Adapter<RambuAdapter.ContactViewHolder> {

    private List<ResultItem> RambuList;

    public RambuAdapter(List<ResultItem> RambuList) {
        this.RambuList = RambuList;
    }

    @Override
    public int getItemCount() {
        return RambuList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        ResultItem ci = RambuList.get(i);
        contactViewHolder.vRambu.setText(ci.getArti());

        String path = Environment.getExternalStorageDirectory()+ "/SIM_IMAGES/"+ci.getGambar();

        File imgFile = new File(path);
        if(imgFile.exists())
        {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            contactViewHolder.vGambar.setImageBitmap(myBitmap);
        }

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.rambu_detail, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView vRambu;
        ImageView vGambar;

        ContactViewHolder(View v) {
            super(v);
            vRambu =  (TextView) v.findViewById(R.id.txtRambu);
            vGambar = (ImageView) v.findViewById(R.id.imageViewRambu);
        }
    }
}
