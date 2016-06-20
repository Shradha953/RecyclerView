package com.example.bitjini.recyclerviewwithimages;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;

    RelativeLayout mRelativeLayout;
    private RecyclerView mRecyclerView;
int id;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<ItemList> bitmap=new ArrayList<>();
     Bitmap selectedBitmap;
    FixedCenterCrop imageView;
    int VerticalCount =0,horizontalCount=0,rotateCount=0;
    int position1;
    Bitmap img,transformedImage,newbmp,newbmp2;
    Button verticalBtn,horizontalBtn,rotateBtn,next;
    int maxHeight,maxWidth;
    int i=0;
    int angle=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Request window feature action bar
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the application context
        mContext = getApplicationContext();
        verticalBtn=(Button) findViewById(R.id.vertical);
        horizontalBtn=(Button)findViewById(R.id.horizontal);
        rotateBtn=(Button) findViewById(R.id.rotate);

        next=(Button) findViewById(R.id.next);
        verticalBtn.setOnClickListener(this);
        horizontalBtn.setOnClickListener(this);
        rotateBtn.setOnClickListener(this);

        horizontalBtn.setEnabled(false);
        verticalBtn.setEnabled(false);
        rotateBtn.setEnabled(false);
        // Change the action bar color
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#FF677589"))
        );

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Initialize a new String array

        final ArrayList<Bitmap> bitmap2=new ArrayList<>();
        Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.uy);
        Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.hh);
        Bitmap bm3 = BitmapFactory.decodeResource(getResources(), R.drawable.uy);
        Bitmap bm4=BitmapFactory.decodeResource(getResources(),R.drawable.aa);
        Bitmap bm5=BitmapFactory.decodeResource(getResources(),R.drawable.ww);
        Bitmap bm6=BitmapFactory.decodeResource(getResources(),R.drawable.rr);
        bitmap.add(new ItemList(1,bm1));
        bitmap.add(new ItemList(2,bm5));
        bitmap.add(new ItemList(3,bm2));
        bitmap.add(new ItemList(4,bm2));
        bitmap.add(new ItemList(5,bm5));
        bitmap.add(new ItemList(6,bm4));


        /*
            GridLayoutManager
                A RecyclerView.LayoutManager implementations that lays out items in a grid.
                By default, each item occupies 1 span. You can change it by providing a custom
                GridLayoutManager.SpanSizeLookup instance via setSpanSizeLookup(SpanSizeLookup).
        */
        /*
            public GridLayoutManager (Context context, int spanCount)
                Creates a vertical GridLayoutManager

            Parameters
                context : Current context, will be used to access resources.
                spanCount : The number of columns in the grid
        */
        // Define a layout for RecyclerView
//        mLayoutManager = new GridLayoutManager(mContext,4);
//        mRecyclerView.setLayoutManager(mLayoutManager);


        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        mLayoutManager = new GridLayoutManager(this,3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(mLayoutManager);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
//        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(2), true));

        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == 6) {

                    return 3; // ITEMS AT POSITION 1 AND 6 OCCUPY 3 SPACES
                }
                if (position == 1)
                {
                    return 2;
                } else {
                    return 1; // OTHER ITEMS OCCUPY ONLY A SINGLE SPACE
                }
            }
        });
        // Initialize a new instance of RecyclerView Adapter instance
        mAdapter = new ImageAdapter(mContext,bitmap);
        // Set the adapter for RecyclerView
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        Toast.makeText(getApplicationContext(),"clicked"+view.getId(),Toast.LENGTH_LONG).show();
                        selectedBitmap=bitmap.get(position).getThumbnailURL();
                        id= (int) bitmap.get(position).getID();
                       position1=position;
                        horizontalBtn.setEnabled(true);
                        verticalBtn.setEnabled(true);
                        rotateBtn.setEnabled(true);
                        view.getTag();
                       maxHeight= view.getHeight();
                        maxWidth=view.getWidth();
//                          view.setDrawingCacheEnabled(true);
                        // this is the important code :)
                        // Without it the view will have a dimension of 0,0 and the bitmap will be null

//
//                 imageView=(FixedCenterCrop)findViewById(view.getId());



                    }
                }) {
                    @Override
                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                    }
                }
        );


//set up adapter and pass clicked listener this

    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    @Override
    public void onClick(View v) {

            switch (v.getId()) {
                case R.id.horizontal:

                    if(angle==0 || angle==360 || angle==180) {
                        if (horizontalCount == 0) {

                            img = selectedBitmap;

                            Matrix matrix2 = new Matrix();
                            // x=x*-1
                            matrix2.preScale(-1.0f, 1.0f);

                            transformedImage = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix2, true);
                            newbmp = transformedImage;
//                            imageView.setImageBitmap(transformedImage);

                            horizontalCount = 1;
                            VerticalCount = 2;

                            bitmap.get(position1).setThumbnailURL(transformedImage);
                            mAdapter.notifyItemChanged(position1);
                            mAdapter.notifyDataSetChanged();


                        } else if (horizontalCount == 1) {
                            img = selectedBitmap;

//                    Matrix matrix = new Matrix();
//                    // y=y*-1
//                    matrix.preScale(1.0f, -1.0f);
//                    transformedImage = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
                            horizontalCount = 0;
                            VerticalCount = 0;
                            bitmap.get(position1).setThumbnailURL(img);
                            mAdapter.notifyItemChanged(position1);
                            mAdapter.notifyDataSetChanged();
//                            imageView.setImageBitmap(img);

                        } else if (horizontalCount == 2) {
                            Matrix matrix = new Matrix();
                            // y=y*-1
                            matrix.preScale(-1.0f, 1.0f);
                            transformedImage = Bitmap.createBitmap(newbmp, 0, 0, newbmp.getWidth(), newbmp.getHeight(), matrix, true);
                            bitmap.get(position1).setThumbnailURL(transformedImage);
                            mAdapter.notifyItemChanged(position1);
                            mAdapter.notifyDataSetChanged();

                            newbmp = transformedImage;
//                            imageView.setImageBitmap(transformedImage);



                            horizontalCount = 3;
                        } else {
                            Matrix matrix = new Matrix();
                            // y=y*-1
                            matrix.preScale(-1.0f, 1.0f);
                            transformedImage = Bitmap.createBitmap(newbmp, 0, 0, newbmp.getWidth(), newbmp.getHeight(), matrix, true);

//                            imageView.setImageBitmap(transformedImage);
                            bitmap.get(position1).setThumbnailURL(transformedImage);
                            mAdapter.notifyItemChanged(position1);
                            mAdapter.notifyDataSetChanged();

                            newbmp = transformedImage;
                            horizontalCount = 2;

                        }
//                        horizontalBtn.setEnabled(false);
//                        verticalBtn.setEnabled(false);
//                        rotateBtn.setEnabled(false);
                    }
                    if(angle==90 ||angle==270)
                    {
                        if (VerticalCount == 0) {
                            img =selectedBitmap;
                          Toast.makeText(getApplicationContext()," hor case Verti Count" + VerticalCount,Toast.LENGTH_SHORT);
                            Matrix matrix = new Matrix();
                            // y=y*-1
                            matrix.preScale(1.0f, -1.0f);
                            transformedImage = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);

//                            imageView.setImageBitmap(transformedImage);
                            bitmap.get(position1).setThumbnailURL(transformedImage);
                            mAdapter.notifyItemChanged(position1);
                            mAdapter.notifyDataSetChanged();
                            VerticalCount = 1;


                        } else if (VerticalCount == 1) {
                            img =selectedBitmap;
                            Toast.makeText(getApplicationContext()," hor case Verti Count" + VerticalCount,Toast.LENGTH_SHORT);
//                            imageView.setImageBitmap(img);
                            bitmap.get(position1).setThumbnailURL(img);
                            mAdapter.notifyItemChanged(position1);
                            mAdapter.notifyDataSetChanged();
                            VerticalCount = 0;
                            horizontalCount = 0;
                        } else if (VerticalCount == 2) {
                            Matrix matrix = new Matrix();
                            // y=y*-1
                            matrix.preScale(1.0f, -1.0f);
                            transformedImage = Bitmap.createBitmap(newbmp, 0, 0, newbmp.getWidth(), newbmp.getHeight(), matrix, true);

//                            imageView.setImageBitmap(transformedImage);
                            bitmap.get(position1).setThumbnailURL(transformedImage);
                            mAdapter.notifyItemChanged(position1);
                            mAdapter.notifyDataSetChanged();
                            newbmp = transformedImage;
                            horizontalCount = 2;
                            VerticalCount = 3;
                        } else {
                            Matrix matrix = new Matrix();
                            // y=y*-1
                            matrix.preScale(1.0f, -1.0f);
                            transformedImage = Bitmap.createBitmap(newbmp, 0, 0, newbmp.getWidth(), newbmp.getHeight(), matrix, true);

//                            imageView.setImageBitmap(transformedImage);
                            bitmap.get(position1).setThumbnailURL(transformedImage);
                            mAdapter.notifyItemChanged(position1);
                            mAdapter.notifyDataSetChanged();
                            newbmp = transformedImage;
                            VerticalCount = 2;
                        }
//                        horizontalBtn.setEnabled(false);
//                        verticalBtn.setEnabled(false);
//                        rotateBtn.setEnabled(false);
                    }

                    break;
                case R.id.vertical :
                    if(angle==0 || angle==360 || angle==180) {

                        if (VerticalCount == 0) {
                            img = selectedBitmap;

                            Toast.makeText(getApplicationContext(),"VerticalCount "+ VerticalCount,Toast.LENGTH_SHORT);
                            Matrix matrix = new Matrix();
                            // y=y*-1
                            matrix.preScale(1.0f, -1.0f);
                            transformedImage = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);

//                            imageView.setImageBitmap(transformedImage);
                            bitmap.get(position1).setThumbnailURL(transformedImage);
                            mAdapter.notifyItemChanged(position1);
                            mAdapter.notifyDataSetChanged();
                            VerticalCount = 1;


                        } else if (VerticalCount == 1) {
                            img =selectedBitmap;
//
//                            imageView.setImageBitmap(img);
                            bitmap.get(position1).setThumbnailURL(img);
                            mAdapter.notifyItemChanged(position1);
                            mAdapter.notifyDataSetChanged();
                            VerticalCount = 0;
                            horizontalCount = 0;
                        } else if (VerticalCount == 2) {
                            Matrix matrix = new Matrix();
                            // y=y*-1
                            matrix.preScale(1.0f, -1.0f);
                            transformedImage = Bitmap.createBitmap(newbmp, 0, 0, newbmp.getWidth(), newbmp.getHeight(), matrix, true);

//                            imageView.setImageBitmap(transformedImage);
                            bitmap.get(position1).setThumbnailURL(transformedImage);
                            mAdapter.notifyItemChanged(position1);
                            mAdapter.notifyDataSetChanged();
                            newbmp = transformedImage;

                            horizontalCount = 2;
                            VerticalCount = 3;
                        } else {
                            Matrix matrix = new Matrix();
                            // y=y*-1
                            matrix.preScale(1.0f, -1.0f);
                            transformedImage = Bitmap.createBitmap(newbmp, 0, 0, newbmp.getWidth(), newbmp.getHeight(), matrix, true);

//                            imageView.setImageBitmap(transformedImage);
                            bitmap.get(position1).setThumbnailURL(transformedImage);
                            mAdapter.notifyItemChanged(position1);
                            mAdapter.notifyDataSetChanged();
                            newbmp = transformedImage;
                            VerticalCount = 2;
                        }
//                        horizontalBtn.setEnabled(false);
//                        verticalBtn.setEnabled(false);
//                        rotateBtn.setEnabled(false);
                    }
                    if(angle==90 || angle==270)
                    {
                        if (horizontalCount == 0) {

                            img = selectedBitmap;

                            Matrix matrix2 = new Matrix();
                            // x=x*-1
                            matrix2.preScale(-1.0f, 1.0f);

                            transformedImage = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix2, true);

//                            imageView.setImageBitmap(transformedImage);
                            bitmap.get(position1).setThumbnailURL(transformedImage);
                            mAdapter.notifyItemChanged(position1);
                            mAdapter.notifyDataSetChanged();
                            newbmp = transformedImage;
                            horizontalCount = 1;
                            VerticalCount = 2;


                        } else if (horizontalCount == 1) {
                            img =selectedBitmap;

//                    Matrix matrix = new Matrix();
//                    // y=y*-1
//                    matrix.preScale(1.0f, -1.0f);
//                    transformedImage = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
                            bitmap.get(position1).setThumbnailURL(img);
                            mAdapter.notifyItemChanged(position1);
                            mAdapter.notifyDataSetChanged();
//                            imageView.setImageBitmap(img);
                            horizontalCount = 0;
                            VerticalCount = 0;
                        } else if (horizontalCount == 2) {
                            Matrix matrix = new Matrix();
                            // y=y*-1
                            matrix.preScale(-1.0f, 1.0f);
                            transformedImage = Bitmap.createBitmap(newbmp, 0, 0, newbmp.getWidth(), newbmp.getHeight(), matrix, true);

//                            imageView.setImageBitmap(transformedImage);
                            bitmap.get(position1).setThumbnailURL(transformedImage);
                            mAdapter.notifyItemChanged(position1);
                            mAdapter.notifyDataSetChanged();
                            newbmp = transformedImage;
                            horizontalCount = 3;
                        } else {
                            Matrix matrix = new Matrix();
                            // y=y*-1
                            matrix.preScale(-1.0f, 1.0f);
                            transformedImage = Bitmap.createBitmap(newbmp, 0, 0, newbmp.getWidth(), newbmp.getHeight(), matrix, true);

//                            imageView.setImageBitmap(transformedImage);
                            bitmap.get(position1).setThumbnailURL(transformedImage);
                            mAdapter.notifyItemChanged(position1);
                            mAdapter.notifyDataSetChanged();
                            newbmp = transformedImage;
                            horizontalCount = 2;

                        }
//                        horizontalBtn.setEnabled(false);
//                        verticalBtn.setEnabled(false);
//                        rotateBtn.setEnabled(false);
                    }
                    break;
                case R.id.rotate:
                    Log.e("rotate positon of bmp", "" + position1);
                    img = selectedBitmap;

                    Matrix matrix = new Matrix();
                    angle=angle+90;


//                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(img, img.getWidth(), img.getHeight(), true);
                    int width = img.getWidth();
                    int height = img.getHeight();
                    Log.e("angle", "" + angle +" w"+width +"h "+height );
                    Log.e("maxWidth",""+maxWidth +" maxHeight "+maxHeight );
                    float scaleWidth = ((float) maxWidth) / width;
                    float scaleHeight = ((float) maxHeight) / height;
                    // create a matrix for the manipulation

                    // resize the bit map
                    matrix.postScale(scaleWidth, scaleHeight);
                    matrix.postRotate(angle);
                    // recreate the new Bitmap
                    Bitmap rotated = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
                    Log.e("rotated w",""+rotated.getWidth() +" h "+rotated.getHeight() );
//                    Bitmap rotated = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                    bitmap.get(position1).setThumbnailURL(rotated);
                    mAdapter.notifyItemChanged(position1);
                    mAdapter.notifyDataSetChanged();
//                imageView.setImageBitmap(rotatedBitmap);

//                    imageView.setRotation(imageView.getRotation() + angle);
//
//                    int i= ((int) imageView.getRotation());
//
//                    Toast.makeText(getApplicationContext(),""+i,Toast.LENGTH_SHORT).show();
//
                    if(angle==360)
                    {
                        angle=0;
                    }
//                    horizontalBtn.setEnabled(false);
//                    verticalBtn.setEnabled(false);
//                    rotateBtn.setEnabled(false);

//
//
//
                    break;
            }
    }


    public ArrayList<ItemList> updateItems(ArrayList<ItemList> data) {

       mAdapter.notifyDataSetChanged();
        return null;
    }
}

/**
 * RecyclerView item decoration - give equal margin around grid item
 */
 class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int halfSpace;

    public SpacesItemDecoration(int space) {
        this.halfSpace = space / 2;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getPaddingLeft() != halfSpace) {
            parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace);
            parent.setClipToPadding(false);
        }

        outRect.top = halfSpace;
        outRect.bottom = halfSpace;
        outRect.left = halfSpace;
        outRect.right = halfSpace;
    }
}



