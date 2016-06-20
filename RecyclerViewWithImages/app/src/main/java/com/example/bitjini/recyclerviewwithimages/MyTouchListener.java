package com.example.bitjini.recyclerviewwithimages;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by bitjini on 15/6/16.
 */
public  class MyTouchListener implements View.OnTouchListener {



        private static final String TAG = "Touch";
        @SuppressWarnings("unused")
        private static final float MIN_ZOOM = 10f, MAX_ZOOM = 10f;

        Matrix matrix = new Matrix();
        Matrix savedMatrix = new Matrix();

        // The 3 states (events) which the user is trying to perform
        static final int NONE = 0;
        static final int DRAG = 1;
        static final int ZOOM = 2;
        int mode = NONE;

        // these PointF objects are used to record the point(s) the user is touching
        PointF start = new PointF();
        PointF mid = new PointF();
        float oldDist = 1f;

    public MyTouchListener() {
    }

    @Override
        public boolean onTouch(View v, MotionEvent event) {
            ImageView view = (ImageView) v;
            view.setScaleType(ImageView.ScaleType.MATRIX);
            float scale;

            dumpEvent(event);
            // Handle touch events here...

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN: // first finger down only
                    savedMatrix.set(matrix);
                    start.set(event.getX(), event.getY());
                    Log.d(TAG, "mode=DRAG"); // write to LogCat
                    mode = DRAG;
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:

                    mode = NONE;
                    Log.d(TAG, "mode=NONE");
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    oldDist = spacing(event);
                    Log.d(TAG, "oldDist=" + oldDist);
                    if (oldDist > 5f) {
                        savedMatrix.set(matrix);
                        midPoint(mid, event);
                        mode = ZOOM;
                        Log.d(TAG, "mode=ZOOM");
                    }
                    break;


                case MotionEvent.ACTION_MOVE:
                    if (mode == DRAG) {
                        matrix.set(savedMatrix);
                        matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
                    } else if (mode == ZOOM) {
                        float[] f = new float[9];
                        float newDist = spacing(event);
                        if (newDist > 10f) {
                            matrix.set(savedMatrix);
                            float tScale = newDist / oldDist;
                            matrix.postScale(tScale, tScale, mid.x, mid.y);
                        }
                        matrix.getValues(f);
                        float scaleX = f[Matrix.MSCALE_X];
                        float scaleY = f[Matrix.MSCALE_Y];
                        if (scaleX <= 0.5f) {
                            matrix.postScale((0.5f) / scaleX, (0.5f) / scaleY, mid.x, mid.y);
                        } else if (scaleX >= 6.0f) {
                            matrix.postScale((6.0f) / scaleX, (6.0f) / scaleY, mid.x, mid.y);
                        }
                    }
                    break;
            }

            view.setImageMatrix(matrix); // display the transformation on screen

            return true;
        }

        private float spacing(MotionEvent event) {
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);
            return (float)Math.sqrt(x * x + y * y);
        }


        private void midPoint(PointF point, MotionEvent event) {
            float x = event.getX(0) + event.getX(1);
            float y = event.getY(0) + event.getY(1);
            point.set(x / 2, y / 2);
        }

        private void dumpEvent(MotionEvent event) {
            String names[] = {"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                    "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?"};
            StringBuilder sb = new StringBuilder();
            int action = event.getAction();
            int actionCode = action & MotionEvent.ACTION_MASK;
            sb.append("event ACTION_").append(names[actionCode]);

            if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                    || actionCode == MotionEvent.ACTION_POINTER_UP) {
                sb.append("(pid ").append(
                        action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
                sb.append(")");
            }

            sb.append("[");
            for (int i = 0; i < event.getPointerCount(); i++) {
                sb.append("#").append(i);
                sb.append("(pid ").append(event.getPointerId(i));
                sb.append(")=").append((int) event.getX(i));
                sb.append(",").append((int) event.getY(i));
                if (i + 1 < event.getPointerCount())
                    sb.append(";");
            }

            sb.append("]");
            Log.d("Touch Event", sb.toString());
        }
    }
