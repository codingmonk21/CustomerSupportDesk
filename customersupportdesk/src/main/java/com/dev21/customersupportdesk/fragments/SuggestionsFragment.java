package com.dev21.customersupportdesk.fragments;


import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dev21.customersupportdesk.adapters.CSFAdapter;
import com.dev21.customersupportdesk.helper.FireBaseReferenceHelper;
import com.dev21.customersupportdesk.helper.NetworkHelper;
import com.dev21.customersupportdesk.helper.ProgressDialogHelper;
import com.dev21.customersupportdesk.helper.ToastHelper;
import com.dev21.customersupportdesk.models.CommonBean;
import com.dev21.customersupportdesk.util.Config;
import com.dev21.customersupportdesk.R;
import com.dev21.customersupportdesk.models.SuggestionBean;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class SuggestionsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    private TextView noContentLabel;
    private RecyclerView recyclerView;
    private CSFAdapter commonAdapter;
    private ArrayList<CommonBean> suggestionsList;

    private ProgressDialog progressDialog;
    private ProgressDialogHelper progressDialogHelper;

    private FireBaseReferenceHelper fireBaseReferenceHelper;

    public SuggestionsFragment() {
        // Required empty public constructor
    }

    public static SuggestionsFragment newInstance(String param1) {
        SuggestionsFragment fragment = new SuggestionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

        suggestionsList = new ArrayList<>();
        commonAdapter = new CSFAdapter(getActivity(), suggestionsList);

        progressDialogHelper = new ProgressDialogHelper(getContext(), true, true);
        progressDialog = progressDialogHelper.getProgressDialog();

        fireBaseReferenceHelper = new FireBaseReferenceHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggestions, container, false);

        noContentLabel = (TextView) view.findViewById(R.id.noContentLabel);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager rlm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(rlm);
        recyclerView.setAdapter(commonAdapter);

        if (NetworkHelper.isNetworkAvailable(getContext())) {
            showDialog();
            fetchSuggestions();
        } else {
            ToastHelper.showToast(getContext(), "Check your internet connection");
        }

        return view;
    }

    private void fetchSuggestions() {
        fireBaseReferenceHelper.suggestions().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    hideDialog();
                    suggestionsList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        SuggestionBean suggestionBean = snapshot.getValue(SuggestionBean.class);
                        if (suggestionBean != null) {
                            CommonBean commonBean = new CommonBean();
                            commonBean.setPostTitle(suggestionBean.getSuggestionTitle());
                            commonBean.setPostDescription(suggestionBean.getSuggestionDescription());
                            commonBean.setPostImage(suggestionBean.getSuggestionImage());

                            commonBean.setPostedBy(suggestionBean.getSuggestionFrom());
                            commonBean.setPostedUserID(suggestionBean.getUserID());
                            commonBean.setCustomerMobNumber(suggestionBean.getCustomerMobNumber());
                            commonBean.setPostedTime(suggestionBean.getSuggestionTimeStamp());
                            commonBean.setPostStatus(suggestionBean.getSuggestionStatus());
                            commonBean.setPostResponseMessage(suggestionBean.getSuggestionResponseMessage());

                            commonBean.setActionID(suggestionBean.getSuggestionID());
                            commonBean.setActionType(Config.ACTION_TYPE_SUGGESTION);
                            suggestionsList.add(commonBean);
                        }
                    }

                    if (suggestionsList.size() > 0) {
                        noContentLabel.setVisibility(View.GONE);
                        Collections.reverse(suggestionsList);
                        recyclerView.setVisibility(View.VISIBLE);
                        commonAdapter.notifyDataSetChanged();
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        noContentLabel.setText("No suggestion to show");
                    }

                } else {
                    hideDialog();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideDialog();
                ToastHelper.showToast(getContext(), "Couldn't load suggestions. Please try again");
            }
        });
    }

    private void showDialog() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
