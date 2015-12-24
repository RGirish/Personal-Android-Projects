package girish.raman.internationalbirthdayreminder;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.FormViewHolder> {

    List<Contact> contacts;

    RVAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @Override
    public FormViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_details_item, viewGroup, false);
        return new FormViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FormViewHolder formViewHolder, int i) {
        formViewHolder.name.setText(contacts.get(i).name);
        formViewHolder.birthday.setText(contacts.get(i).birthday);
        formViewHolder.id.setText(contacts.get(i).ID);
        if (contacts.get(i).reminder) {
            formViewHolder.reminderSwitch.setChecked(true);
            formViewHolder.timeZone.setText(contacts.get(i).timeZone);
        } else {
            formViewHolder.reminderSwitch.setChecked(false);
            formViewHolder.timeZone.setVisibility(View.GONE);
        }
        formViewHolder.reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class FormViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView birthday;
        TextView id;
        TextView timeZone;
        SwitchCompat reminderSwitch;

        FormViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            birthday = (TextView) itemView.findViewById(R.id.birthday);
            id = (TextView) itemView.findViewById(R.id.contactID);
            timeZone = (TextView) itemView.findViewById(R.id.timeZone);
            reminderSwitch = (SwitchCompat) itemView.findViewById(R.id.reminderSwitch);
        }
    }
}