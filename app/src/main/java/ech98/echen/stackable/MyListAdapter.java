package ech98.echen.stackable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by echen on 5/6/15. This is the adapter that controls the content of the
 * fragment_main_authed ListView
 */
public class MyListAdapter extends ArrayAdapter<FoodEssential_Object> {
    private final Context context;
    private final ArrayList<FoodEssential_Object> data;
    static final int ANIMATION_DURATION = 200;

    public MyListAdapter(Context context, ArrayList<FoodEssential_Object> data) {
        super(context, R.layout.my_list_row_item, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.my_list_row_item, parent, false);
        final ImageView imgView = (ImageView) rowView.findViewById(R.id.item_image);
        TextView upcView = (TextView) rowView.findViewById(R.id.item_upc);
        TextView brandView = (TextView) rowView.findViewById(R.id.item_brand);
        TextView nameView = (TextView) rowView.findViewById(R.id.item_name);

        new AsyncTask<Integer, Void, Void>() {
            private Bitmap bmp;
            @Override
            protected Void doInBackground(Integer... params) {
                try {
                    InputStream in = new URL(data.get(params[0]).getImage()).openStream();
                    bmp = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    // log error
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (bmp != null)
                    imgView.setImageBitmap(bmp);
            }

        }.execute(position);
        upcView.setText(data.get(position).getUpc());
        brandView.setText(data.get(position).getBrand());
        nameView.setText(data.get(position).getName());

        final View passrowView = rowView;
        final int passPosition = position;

        rowView.post(new Runnable() {
            // Post in the parent's message queue to make sure the parent
            // lays out its children before you call getHitRect()
            @Override
            public void run() {
                // The bounds for the delegate view (a Button
                // in this example)
                Rect delegateArea = new Rect();
                Button button = (Button) passrowView.findViewById(R.id.delete_button);
                button.setTag(passPosition);
                button.setEnabled(true);
                // Set click listener to remove item from ListView when imagebutton clicked
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        Animation.AnimationListener al = new Animation.AnimationListener() {
                            @Override
                            public void onAnimationEnd(Animation arg0) {
                                int tag = (Integer) v.getTag();
                                data.remove(tag);
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override
                            public void onAnimationStart(Animation animation) {
                            }
                        };

                        collapse(v, al);
                    }
                });

                // The hit rectangle for the Button
                button.getHitRect(delegateArea);

                // Extend the touch area of the Button beyond its bounds
                delegateArea.right += 100;
                delegateArea.left += 100;
                delegateArea.top += 100;
                delegateArea.bottom += 100;

                // Instantiate a TouchDelegate.
                // "delegateArea" is the bounds in local coordinates of
                // the containing view to be mapped to the delegate view.
                // "button" is the child view that should receive motion
                // events.
                TouchDelegate touchDelegate = new TouchDelegate(delegateArea, button);
                if (View.class.isInstance(button.getParent())) {
                    ((View) button.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });

        return rowView;
    }

    private void collapse(final View v, Animation.AnimationListener al) {
        final int initialHeight = v.getMeasuredHeight();

        Animation anim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                }
                else {
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        if (al!=null) {
            anim.setAnimationListener(al);
        }
        anim.setDuration(ANIMATION_DURATION);
        v.startAnimation(anim);
    }
    public ArrayList<FoodEssential_Object> getValues(){
        return this.data;
    }
}
