package za.co.dstv.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import za.co.dstv.R;
import za.co.dstv.utils.AppConstants;

public class DetailFragment extends Fragment {

    private String personName, personId;
    private TextView tvPersonName, tvPersonId;
    private CardView cvDetail;


    public static DetailFragment getInstance(String name, String id) {
        DetailFragment detailFragment = new DetailFragment();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(id)) {
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.TAG_personName, name);
            bundle.putString(AppConstants.TAG_personId, id);
            detailFragment.setArguments(bundle);
        }
        return detailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            personName = bundle.getString(AppConstants.TAG_personName);
            personId = bundle.getString(AppConstants.TAG_personId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        tvPersonName = rootView.findViewById(R.id.tvPersonName);
        tvPersonId = rootView.findViewById(R.id.tvPersonId);
        cvDetail = rootView.findViewById(R.id.cvDetail);

        if (!TextUtils.isEmpty(personName))
            tvPersonName.setText(personName);

        if (!TextUtils.isEmpty(personId))
            tvPersonId.setText(personId);

        cvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rootView;
    }
}
