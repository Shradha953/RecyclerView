package com.example.bitjini.recyclerviewwithimages;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Matrix;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private ArrayList<ItemList> mDataSet;
    private Context mContext;
    private Random mRandom = new Random();
   public View viewRoot;
    LayoutInflater inflater;

   Bitmap bitmap;


    public ImageAdapter(Context context, ArrayList<ItemList> DataSet){
        mDataSet = DataSet;
        mContext = context;
        inflater= LayoutInflater.from(context);

    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        public FixedCenterCrop mImageView;
        public CardView mCardView;

        public ViewHolder(View v) {
            super(v);
            mImageView = (FixedCenterCrop) v.findViewById(R.id.image);
            mCardView = (CardView) v.findViewById(R.id.cardView);



        }
    }



    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
         viewRoot = inflater.from(mContext).inflate(R.layout.custom_view,parent,false);
        ViewHolder vh= new ViewHolder(viewRoot);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
         bitmap = mDataSet.get(position).getThumbnailURL();
        holder.mImageView.setImageBitmap(bitmap);
        holder.mImageView.setId(position);





//


//        holder.mImageView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(mContext,"clicked",Toast.LENGTH_LONG).show();
//                holder.mImageView.setBackgroundColor(Color.RED);
////                Log.e("position",""+ holder.mImageView.getId());
//                return false;
//            }
//        });


        holder.mImageView.setOnTouchListener(new MultiTouchListener());



    }


    @Override
    public int getItemCount(){
        return mDataSet.size();
    }


    // Custom method to apply emboss mask filter to TextView
//    protected void applyEmbossMaskFilter(ImageView tv){
//        EmbossMaskFilter embossFilter = new EmbossMaskFilter(
//                new float[]{1f, 5f, 1f}, // direction of the light source
//                0.8f, // ambient light between 0 to 1
//                8, // specular highlights
//                7f // blur before applying lighting
//        );
//        tv.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
//       // tv.getPaint().setMaskFilter(embossFilter);
//    }

    // Custom method to generate random HSV color
//    protected int getRandomHSVColor(){
//        // Generate a random hue value between 0 to 360
//        int hue = mRandom.nextInt(361);
//        // We make the color depth full
//        float saturation = 1.0f;
//        // We make a full bright color
//        float value = 1.0f;
//        // We avoid color transparency
//        int alpha = 255;
//        // Finally, generate the color
//        int color = Color.HSVToColor(alpha, new float[]{hue, saturation, value});
//        // Return the color
//        return color;
//    }

    // Custom method to create a GradientDrawable object
//    protected GradientDrawable getGradientDrawable(){
//        GradientDrawable gradient = new GradientDrawable();
//        gradient.setGradientType(GradientDrawable.SWEEP_GRADIENT);
//        gradient.setColors(new int[]{getRandomHSVColor(), getRandomHSVColor(),getRandomHSVColor()});
//        return gradient;
//    }

    // Custom method to get a darker color
//    protected int getDarkerColor(int color){
//        float[] hsv = new float[3];
//        Color.colorToHSV(color, hsv);
//        hsv[2] = 0.8f *hsv[2];
//        return Color.HSVToColor(hsv);
//    }

    // Custom method to get a lighter color
//    protected int getLighterColor(int color){
//        float[] hsv = new float[3];
//        Color.colorToHSV(color,hsv);
//        hsv[2] = 0.2f + 0.8f * hsv[2];
//        return Color.HSVToColor(hsv);
//    }

    // Custom method to get reverse color
//    protected int getReverseColor(int color){
//        float[] hsv = new float[3];
//        Color.RGBToHSV(
//                Color.red(color), // Red value
//                Color.green(color), // Green value
//                Color.blue(color), // Blue value
//                hsv
//        );
//        hsv[0] = (hsv[0] + 180) % 360;
//        return Color.HSVToColor(hsv);
//    }
}