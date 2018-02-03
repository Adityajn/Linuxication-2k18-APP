package in.mcug.linuxication2k18.Activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.mcug.linuxication2k18.Pojos.CandidatesList;
import in.mcug.linuxication2k18.R;

/**
 * Created by aditya on 2/1/18.
 */

public class RegistrationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static CandidatesList candidates;
    static Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.name) TextView name;
        @BindView(R.id.email) TextView email;
        @BindView(R.id.comment) TextView comment;
        @BindView(R.id.regid) TextView regId;
        @BindView(R.id.mobile) TextView mobile;
        @BindView(R.id.paid) TextView paid;
        @BindView(R.id.pending) TextView pending;
        @BindView(R.id.datetime) TextView datetime;
        @BindView(R.id.college) TextView college;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public RegistrationListAdapter(CandidatesList list, Context context){
        this.context = context;
        this.candidates = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.registration_template,parent,false);
        RegistrationListAdapter.ViewHolder vh = new RegistrationListAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RegistrationListAdapter.ViewHolder vholder = (RegistrationListAdapter.ViewHolder) holder;
        vholder.name.setText("Name : "+candidates.getName(position));
        vholder.comment.setText("Comment :"+candidates.getComment(position));
        vholder.datetime.setText("DateTime :\n"+candidates.getDateTime(position));
        vholder.email.setText("Email :\n"+candidates.getEmail(position));
        vholder.mobile.setText("Mobile : "+candidates.getMobile(position));
        vholder.paid.setText("Paid : Rs "+candidates.getPaid(position));
        vholder.pending.setText("Pending : Rs "+candidates.getPending(position));
        vholder.regId.setText("Reg ID : "+candidates.getRegID(position));
        vholder.college.setText("College :"+candidates.getCollege(position));
    }

    @Override
    public int getItemCount() {
        return candidates.size();
    }
}
