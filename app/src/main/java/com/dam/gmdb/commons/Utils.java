package com.dam.gmdb.commons;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dam.gmdb.R;
import com.google.android.material.snackbar.Snackbar;

public class Utils {

    public static void showSnackBar(View baseview, String message){
        Snackbar.make(baseview, message, Snackbar.LENGTH_LONG).show();
    }

//    public static void applyGlide(Context context, String image, String place){
//        //Ajout des options pour afficher les affiches
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .error(R.drawable.ic_movie_24_grey)
//                .placeholder(R.drawable.ic_movie_24_grey);
//
//        Context context = filmsViewHolder.ivAffiche.getContext();
//        Glide.with(context)
//                .load(image)
//                .apply(options)
//                .fitCenter()
//                .override(150, 150)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(place.ivAffiche);
//    }


}
