package com.dev21.customersupportdesk.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dev21.customersupportdesk.R;
import com.dev21.customersupportdesk.activities.AdminDashboard;
import com.dev21.customersupportdesk.activities.UpdateCSFStatusActivity;
import com.dev21.customersupportdesk.helper.SharedPreferenceHelper;
import com.dev21.customersupportdesk.models.CommonBean;
import com.dev21.customersupportdesk.models.ComplaintBean;
import com.dev21.customersupportdesk.models.FeedbackBean;
import com.dev21.customersupportdesk.models.SuggestionBean;
import com.dev21.customersupportdesk.util.Config;

import java.util.ArrayList;

/**
 * Created by Prajwal on 03/04/17.
 */

public class CSFAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = CSFAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<CommonBean> contentList;

    private SharedPreferenceHelper sharedPreferenceHelper;

    private boolean isAdmin;

    public CSFAdapter(Context context, ArrayList<CommonBean> list) {
        contentList = list;
        this.context = context;
        sharedPreferenceHelper = new SharedPreferenceHelper(context, Config.SP_ROOT_NAME);
        isAdmin = sharedPreferenceHelper.getBoolean(Config.SP_ADMIN_LOGIN, false);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_dashboard_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (contentList.get(position).getActionType()) {
            case "Complaint":
                ((CardViewHolder) holder).cardTitle.setText("C");
                break;
            case "Suggestion":
                ((CardViewHolder) holder).cardTitle.setText("S");
                break;
            case "Feedback":
                ((CardViewHolder) holder).cardTitle.setText("F");
                break;
        }
        ((CardViewHolder) holder).complaintID.setText(contentList.get(position).getActionID());
        ((CardViewHolder) holder).title.setText(contentList.get(position).getPostTitle());
        ((CardViewHolder) holder).description.setText(contentList.get(position).getPostDescription());
        ((CardViewHolder) holder).status.setText(contentList.get(position).getPostStatus());

        switch (contentList.get(position).getPostStatus()) {
            case "Pending":
                // Red color
                ((CardViewHolder) holder).status.setTextColor(Color.parseColor("#b20000"));
                break;
            case "Solved":
                // Green color
                ((CardViewHolder) holder).status.setTextColor(Color.parseColor("#006600"));
                break;
            case "Thank you":
                ((CardViewHolder) holder).status.setTextColor(Color.parseColor("#006600"));
                break;
            case "Accepted":
                ((CardViewHolder) holder).status.setTextColor(Color.parseColor("#006600"));
                break;
            case "Considered":
                // Orange color
                ((CardViewHolder) holder).status.setTextColor(Color.parseColor("#ff6600"));
                break;
            case "Hold":
                // Red color
                ((CardViewHolder) holder).status.setTextColor(Color.parseColor("#b20000"));
                break;
            case "Acknowledged":
                // Green color
                ((CardViewHolder) holder).status.setTextColor(Color.parseColor("#006600"));
                break;
        }

        ((CardViewHolder) holder).timeStamp.setText(contentList.get(position).getPostedTime());

        if (contentList.get(position).getPostResponseMessage() != null && !contentList.get(position).getPostResponseMessage().equals("")) {
            ((CardViewHolder) holder).arrow.setVisibility(View.VISIBLE);
            ((CardViewHolder) holder).responseMessage.setText(contentList.get(position).getPostResponseMessage());
        } else {
            ((CardViewHolder) holder).arrow.setVisibility(View.GONE);
        }

        if (contentList.get(position).isExpanded()) {
            ((CardViewHolder) holder).arrow.setImageResource(R.drawable.up_arrow);
            ((CardViewHolder) holder).responseMessageLayout.setVisibility(View.VISIBLE);
        } else {
            ((CardViewHolder) holder).arrow.setImageResource(R.drawable.down_arrow);
            ((CardViewHolder) holder).responseMessageLayout.setVisibility(View.GONE);
        }

        if (contentList.get(position).getPostImage() != null && !contentList.get(position).getPostImage().equals("")) {
            ((CardViewHolder) holder).feedImageLayout.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(contentList.get(position).getPostImage())
                    .into(((CardViewHolder) holder).feedImage);
        } else {
            ((CardViewHolder) holder).feedImageLayout.setVisibility(View.GONE);
        }

        if (isAdmin) {
            ((CardViewHolder) holder).customerDetailLayout.setVisibility(View.VISIBLE);
            ((CardViewHolder) holder).from.setText(" " + contentList.get(position).getPostedBy());
            ((CardViewHolder) holder).mobNum.setText(" " + contentList.get(position).getCustomerMobNumber());
        } else {
            ((CardViewHolder) holder).customerDetailLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (contentList == null) {
            return 0;
        } else {
            return contentList.size();
        }
    }

    private class CardViewHolder extends RecyclerView.ViewHolder {
        TextView cardTitle, title, description, from, complaintID, responseMessage, timeStamp, status, mobNum;
        ImageView feedImage, arrow;
        LinearLayout responseMessageLayout, customerDetailLayout;
        RelativeLayout statusLayout, feedImageLayout;

        CardViewHolder(View itemView) {
            super(itemView);

            cardTitle = (TextView) itemView.findViewById(R.id.cardTitle);
            complaintID = (TextView) itemView.findViewById(R.id.complaintID);
            timeStamp = (TextView) itemView.findViewById(R.id.timeStamp);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);

            feedImage = (ImageView) itemView.findViewById(R.id.feedImage);
            arrow = (ImageView) itemView.findViewById(R.id.arrow);

            status = (TextView) itemView.findViewById(R.id.status);
            responseMessage = (TextView) itemView.findViewById(R.id.responseMessage);

            responseMessageLayout = (LinearLayout) itemView.findViewById(R.id.responseMessageLayout);
            customerDetailLayout = (LinearLayout) itemView.findViewById(R.id.customerDetailLayout);

            statusLayout = (RelativeLayout) itemView.findViewById(R.id.statusLayout);
            feedImageLayout = (RelativeLayout) itemView.findViewById(R.id.feedImageLayout);

            from = (TextView) itemView.findViewById(R.id.from);
            mobNum = (TextView) itemView.findViewById(R.id.mobNum);

            arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (contentList.get(getAdapterPosition()).isExpanded()) {
                        contentList.get(getAdapterPosition()).setExpanded(false);
                        arrow.setImageResource(R.drawable.down_arrow);
                        responseMessageLayout.setVisibility(View.GONE);
                    } else {
                        arrow.setImageResource(R.drawable.up_arrow);
                        contentList.get(getAdapterPosition()).setExpanded(true);
                        responseMessageLayout.setVisibility(View.VISIBLE);
                    }
                }
            });

            statusLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (contentList.get(getAdapterPosition()).getPostResponseMessage() != null && !contentList.get(getAdapterPosition()).getPostResponseMessage().equals("")) {
                        if (contentList.get(getAdapterPosition()).isExpanded()) {
                            contentList.get(getAdapterPosition()).setExpanded(false);
                            arrow.setImageResource(R.drawable.down_arrow);
                            responseMessageLayout.setVisibility(View.GONE);
                        } else {
                            arrow.setImageResource(R.drawable.up_arrow);
                            contentList.get(getAdapterPosition()).setExpanded(true);
                            responseMessageLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            if (sharedPreferenceHelper.getBoolean(Config.SP_ADMIN_LOGIN, false)) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, UpdateCSFStatusActivity.class);
                        intent.putExtra("actionType", contentList.get(getAdapterPosition()).getActionType());

                        switch (AdminDashboard.selectedTab) {
                            case "Complaints":
                                ComplaintBean complaintBean = new ComplaintBean();
                                complaintBean.setComplaintTitle(contentList.get(getAdapterPosition()).getPostTitle());
                                complaintBean.setComplaintDescription(contentList.get(getAdapterPosition()).getPostDescription());
                                complaintBean.setComplaintImage(contentList.get(getAdapterPosition()).getPostImage());
                                complaintBean.setComplaintID(contentList.get(getAdapterPosition()).getActionID());

                                complaintBean.setUserID(contentList.get(getAdapterPosition()).getPostedUserID());
                                complaintBean.setComplaintFrom(contentList.get(getAdapterPosition()).getPostedBy());
                                complaintBean.setCustomerMobNumber(contentList.get(getAdapterPosition()).getCustomerMobNumber());
                                complaintBean.setComplaintStatus(contentList.get(getAdapterPosition()).getPostStatus());
                                complaintBean.setComplaintTimeStamp(contentList.get(getAdapterPosition()).getPostedTime());
                                intent.putExtra("Complaint", complaintBean);
                                break;
                            case "Suggestions":
                                SuggestionBean suggestionBean = new SuggestionBean();
                                suggestionBean.setSuggestionTitle(contentList.get(getAdapterPosition()).getPostTitle());
                                suggestionBean.setSuggestionDescription(contentList.get(getAdapterPosition()).getPostDescription());
                                suggestionBean.setSuggestionImage(contentList.get(getAdapterPosition()).getPostImage());
                                suggestionBean.setSuggestionID(contentList.get(getAdapterPosition()).getActionID());

                                suggestionBean.setUserID(contentList.get(getAdapterPosition()).getPostedUserID());
                                suggestionBean.setSuggestionFrom(contentList.get(getAdapterPosition()).getPostedBy());
                                suggestionBean.setCustomerMobNumber(contentList.get(getAdapterPosition()).getCustomerMobNumber());
                                suggestionBean.setSuggestionStatus(contentList.get(getAdapterPosition()).getPostStatus());
                                suggestionBean.setSuggestionTimeStamp(contentList.get(getAdapterPosition()).getPostedTime());
                                intent.putExtra("Suggestion", suggestionBean);
                                break;
                            case "Feedback":
                                FeedbackBean feedbackBean = new FeedbackBean();
                                feedbackBean.setFeedbackTitle(contentList.get(getAdapterPosition()).getPostTitle());
                                feedbackBean.setFeedbackDescription(contentList.get(getAdapterPosition()).getPostDescription());
                                feedbackBean.setFeedbackImage(contentList.get(getAdapterPosition()).getPostImage());
                                feedbackBean.setFeedbackID(contentList.get(getAdapterPosition()).getActionID());

                                feedbackBean.setUserID(contentList.get(getAdapterPosition()).getPostedUserID());
                                feedbackBean.setFeedbackFrom(contentList.get(getAdapterPosition()).getPostedBy());
                                feedbackBean.setCustomerMobNumber(contentList.get(getAdapterPosition()).getCustomerMobNumber());
                                feedbackBean.setFeedbackStatus(contentList.get(getAdapterPosition()).getPostStatus());
                                feedbackBean.setFeedbackTimestamp(contentList.get(getAdapterPosition()).getPostedTime());
                                intent.putExtra("Feedback", feedbackBean);
                                break;

                            default:
                                break;
                        }

                        context.startActivity(intent);

                    }
                });
            }
        }
    }

    public void refreshDataSet(ArrayList<CommonBean> list) {
        contentList = list;
        notifyDataSetChanged();
    }
}
