package com.fsck.k9;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class FollowUpNotificationsListAdapter extends ArrayAdapter<FollowUpNotificationHolder> {

    private Context context;
    private List<FollowUpNotificationHolder> fnHolders = new ArrayList<>();

    public FollowUpNotificationsListAdapter(@NonNull Context context,@LayoutRes int itemLayout, List<FollowUpNotificationHolder> fnHolders) {
        super(context, 0, fnHolders);
        this.context = context;
        this.fnHolders = fnHolders;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;

        if(listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.follow_up_notification_list_item, parent, false);
        }

        FollowUpNotificationHolder fNH = fnHolders.get(position);
        TextView recipients = (TextView) listItem.findViewById(R.id.recipients);

        recipients.setText("TO: "+fNH.getRecipientAddresses());

        TextView dateTime = (TextView) listItem.findViewById(R.id.follow_up_time);
        dateTime.setText("ON: "+fNH.getDateTime());

        return listItem;

    }
}
