package ech98.echen.stackable;

import android.content.Context;
import android.graphics.Rect;
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

import java.util.ArrayList;

/**
 * Created by echen on 5/6/15. This is the adapter that controls the content of the
 * fragment_main_authed ListView
 */
public class MyListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> data;
    static final int ANIMATION_DURATION = 200;

    public MyListAdapter(Context context, ArrayList<String> data) {
        super(context, R.layout.my_list_row_item, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.my_list_row_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.itemtext);
        textView.setText(data.get(position));

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
                delegateArea.right +=100;
                delegateArea.left +=100;
                delegateArea.top +=100;
                delegateArea.bottom +=100;

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
    public ArrayList<String> getValues(){
        return this.data;
    }
}
