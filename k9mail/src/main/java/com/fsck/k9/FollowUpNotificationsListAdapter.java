package com.fsck.k9;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;


import java.util.ArrayList;
import java.util.List;

public class FollowUpNotificationsListAdapter extends ArrayAdapter<FollowUpNotificationHolder> {

    private Context context;
    private List<FollowUpNotificationHolder> fnHolders = new ArrayList<FollowUpNotificationHolder>();

    public FollowUpNotificationsListAdapter(@NonNull Context context, ArrayList<FollowUpNotificationHolder> fnHolders) {
        super(context, 0, fnHolders);
        this.context = context;
        this.fnHolders = fnHolders;
    }

}
