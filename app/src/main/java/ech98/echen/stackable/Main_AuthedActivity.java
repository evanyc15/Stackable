package ech98.echen.stackable;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.provider.Settings.Secure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Main_AuthedActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    protected static String android_id;
    protected static String client_id = "192d6a3e";
    protected static String client_secret = "9ee32fe1457fffea5bdf7f0ee39c2cba";
    protected static String food_Url = "https://api.iamdata.co:443/v1/products?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android_id = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);

        setContentView(R.layout.activity_main__authed);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main__authed, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Called from the NavigationDrawerFragment's onActivityResult() method. This method will fetch
    // the content fragment and then add and update data in the listview
    public void updateListView(String data){
        FragmentManager fragmentManager = getSupportFragmentManager();
        PlaceholderFragment fragment = (PlaceholderFragment) fragmentManager.findFragmentById(R.id.container);

        if(fragment != null){
            fragment.addtoList(data);
        } else {
            Toast.makeText(Main_AuthedActivity.this, "Cannot load content", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements OnFoodTaskCompleted{
        private ListView mlistView;
        private MyListAdapter adapter;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            adapter = new MyListAdapter(getActivity(), new ArrayList<FoodEssential_Object>());
            View rootView = inflater.inflate(R.layout.fragment_main__authed, container, false);
            mlistView = (ListView) rootView.findViewById(R.id.listview);

            mlistView.setAdapter(adapter);

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Main_AuthedActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        // Adds data to the ListView
        public void addtoList(String data){
            new FoodEss_GetFoodTask(this).execute(food_Url, data, client_id, client_secret);
        }
        public void onFoodTaskCompleted(JSONObject data){
            Object array;
            try{
                array = data.getJSONArray("result").get(0);
                JSONObject productData = new JSONObject(array.toString());

                if((productData.getString("description") == null || productData.getString("description").isEmpty()) && productData.getString("brand") != "null" && !productData.getString("brand").isEmpty()){
                    adapter.add(new FoodEssential_Object(productData.getString("upc"), productData.getString("name"), productData.getString("brand"), productData.getString("large_image")));
                } else if(productData.getString("description") != null && !productData.getString("description").isEmpty() && (productData.getString("brand") == null || productData.getString("brand").isEmpty())){
                    adapter.add(new FoodEssential_Object(productData.getString("upc"), productData.getString("description"), productData.getString("manufacturer"), productData.getString("large_image")));
                } else if(productData.getString("description") != null && !productData.getString("description").isEmpty() && productData.getString("brand") != null && !productData.getString("brand").isEmpty()){
                    adapter.add(new FoodEssential_Object(productData.getString("upc"), productData.getString("description"), productData.getString("brand"), productData.getString("large_image")));
                } else {
                    adapter.add(new FoodEssential_Object(productData.getString("upc"), productData.getString("name"), productData.getString("manufacturer"), productData.getString("large_image")));
                }
            } catch(JSONException e){
                System.out.println("===Error: "+e);
            }
        }
    }

}
