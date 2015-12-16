package girish.raman.internationalbirthdayreminder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.FormViewHolder> {

    public static class FormViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView birthday;
        TextView id;

        FormViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            birthday = (TextView) itemView.findViewById(R.id.birthday);
            id = (TextView) itemView.findViewById(R.id.contactID);
        }
    }

    List<ContactDetails> contactDetails;

    RVAdapter(List<ContactDetails> contactDetails){
        this.contactDetails = contactDetails;
    }

    @Override
    public int getItemCount() {
        return contactDetails.size();
    }

    @Override
    public FormViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_details_item, viewGroup, false);
        return new FormViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FormViewHolder formViewHolder, int i) {
        formViewHolder.name.setText(contactDetails.get(i).name);
        formViewHolder.birthday.setText(contactDetails.get(i).birthday);
        formViewHolder.id.setText(contactDetails.get(i).ID);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}