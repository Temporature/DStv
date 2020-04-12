package za.co.dstv.utils;

import android.app.Activity;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;

import za.co.dstv.R;


public class AppHelper {

    private double lat, lon;
    private static AppHelper ourInstance = null;
    private Gson gson = new Gson();

    public static AppHelper getInstance() {
        if (ourInstance == null)
            ourInstance = new AppHelper();
        return ourInstance;
    }

    private AppHelper() {
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void replaceFragment(FragmentActivity activity, Fragment fragment, boolean flagIsAddToBackStack) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, AppConstants.FRAGMENT_TAG);
        if (flagIsAddToBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void addFragment(FragmentActivity activity, Fragment fragment, boolean flagIsAddToBackStack) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment, AppConstants.FRAGMENT_TAG);
        if (flagIsAddToBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void addFragment(FragmentActivity activity, Fragment fragment, boolean flagIsAddToBackStack ,int container) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(container, fragment, AppConstants.FRAGMENT_TAG);
        if (flagIsAddToBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void removeFragment(Activity activity, Fragment fragment) {
        FragmentManager fragmentManager = ((AppCompatActivity) activity).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
        fragmentManager.popBackStack();
    }


    /**
     * Method to replace the child fragment
     *
     * @param parentFragment       Parent Fragment
     * @param fragment             Fragment to be replace
     * @param containerId          Container/Holder View Id
     * @param strFragmentTag       Fragment Tag
     * @param flagIsAddToBackStack flag to add fragment to back stack or not
     */
    public void replaceChildFragment(Fragment parentFragment, Fragment fragment, int containerId, String strFragmentTag, boolean flagIsAddToBackStack) {
        FragmentManager fragmentManager = parentFragment.getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId, fragment, strFragmentTag);
        if (flagIsAddToBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static SpannableStringBuilder substringBoldDark(Context context, String source, int positionFrom, int positionTo, boolean shouldBold, boolean shouldDarken) {
        SpannableStringBuilder str = new SpannableStringBuilder(source);
        if (shouldBold) {
            str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), positionFrom, positionTo, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (shouldDarken) {
            str.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.text_color_grey)), positionFrom, positionTo, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return str;
    }

    public String convertObjectToString(Object object) {
        return gson.toJson(object);
    }

    public <T> T convertStringToObject(String strObjectString, Class<?> type) {
        //noinspection unchecked
        return TextUtils.isEmpty(strObjectString) ? null : (T) gson.fromJson(strObjectString, type);
    }
}
