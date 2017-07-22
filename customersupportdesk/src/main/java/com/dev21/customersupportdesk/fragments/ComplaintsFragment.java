package com.dev21.customersupportdesk.fragments;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
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
import com.dev21.customersupportdesk.models.ComplaintBean;
import com.dev21.customersupportdesk.util.Config;
import com.dev21.customersupportdesk.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ComplaintsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    private TextView noContentLabel;
    private RecyclerView recyclerView;
    private CSFAdapter commonAdapter;
    private ArrayList<CommonBean> complaintsList;

    private FireBaseReferenceHelper fireBaseReferenceHelper;

    private ProgressDialog progressDialog;
    private ProgressDialogHelper progressDialogHelper;

    public ComplaintsFragment() {
        // Required empty public constructor
    }

    public static ComplaintsFragment newInstance(String param1) {
        ComplaintsFragment fragment = new ComplaintsFragment();
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
        complaintsList = new ArrayList<>();
        commonAdapter = new CSFAdapter(getActivity(), complaintsList);

        progressDialogHelper = new ProgressDialogHelper(getContext(), true, true);
        progressDialog = progressDialogHelper.getProgressDialog();

        fireBaseReferenceHelper = new FireBaseReferenceHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaints, container, false);

        noContentLabel = (TextView) view.findViewById(R.id.noContentLabel);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager rlm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(rlm);
        recyclerView.setAdapter(commonAdapter);

        if (NetworkHelper.isNetworkAvailable(getContext())) {
            showDialog();
            fetchComplaints();
        } else {
            ToastHelper.showToast(getContext(), "Check your internet connection");
        }

        return view;
    }

    private void fetchComplaints() {
        fireBaseReferenceHelper.complaints().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    hideDialog();
                    complaintsList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ComplaintBean complaintBean = snapshot.getValue(ComplaintBean.class);
                        if (complaintBean != null) {
                            CommonBean commonBean = new CommonBean();
                            commonBean.setPostTitle(complaintBean.getComplaintTitle());
                            commonBean.setPostDescription(complaintBean.getComplaintDescription());
                            commonBean.setPostImage(complaintBean.getComplaintImage());

                            commonBean.setPostedBy(complaintBean.getComplaintFrom());
                            commonBean.setCustomerMobNumber(complaintBean.getCustomerMobNumber());
                            commonBean.setPostedTime(complaintBean.getComplaintTimeStamp());
                            commonBean.setPostedUserID(complaintBean.getUserID());
                            commonBean.setPostStatus(complaintBean.getComplaintStatus());
                            commonBean.setPostResponseMessage(complaintBean.getComplaintResponseMessage());

                            commonBean.setActionID(complaintBean.getComplaintID());
                            commonBean.setActionType(Config.ACTION_TYPE_COMPLAINT);
                            complaintsList.add(commonBean);
                        }
                    }

                    if (complaintsList.size() > 0) {
                        noContentLabel.setVisibility(View.GONE);
                        Collections.reverse(complaintsList);
                        recyclerView.setVisibility(View.VISIBLE);
                        commonAdapter.notifyDataSetChanged();
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        noContentLabel.setText("No complaints to show");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideDialog();
                ToastHelper.showToast(getContext(), "Couldn't load complaints");
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
