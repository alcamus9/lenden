package com.example.srinivas.lenden;
/**
 * Created by sushantc on 3/19/16.
 */

import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.srinivas.lenden.Info;
import com.example.srinivas.lenden.objects.User;
import com.example.srinivas.testlogin.R;
import java.util.Collections;
import java.util.List;

/**
 * Created by sushantc on 3/15/16.
 */
public class BillSpecAdapter extends RecyclerView.Adapter<BillSpecAdapter.BillSpecViewHolder> {

    List<InfoBillSpec> dataBillSpec = Collections.emptyList();//what is this collections func
    private LayoutInflater inflater;

    public BillSpecAdapter(Context context, List<InfoBillSpec> dataBillSpec) {

        inflater = LayoutInflater.from(context);
        this.dataBillSpec = dataBillSpec;
    }

    class BillSpecViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        EditText owed;
        EditText paid;
        CheckBox include;

        public BillSpecViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.listName);
            paid = (EditText) itemView.findViewById(R.id.listPaid);
            owed = (EditText) itemView.findViewById(R.id.listOwed);
            include = (CheckBox) itemView.findViewById(R.id.listInclude);
        }
    }

    @Override
    public BillSpecAdapter.BillSpecViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        View view = inflater.inflate(R.layout.custom_row_bill_spec, parent, false);
        BillSpecViewHolder holder = new BillSpecViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder (BillSpecAdapter.BillSpecViewHolder holder,int position){

        InfoBillSpec current = dataBillSpec.get(position);
        holder.name.setText(current.memberName);
            //holder.name.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
            //String s1= holder.paid.getText().toString();
            //current.paidAmt = Double.parseDouble(s1);

            //String s2= holder.owed.getText().toString();
            //current.owedAmt=Double.parseDouble(s2);

            //current.includeMember= holder.include.isChecked();

        holder.paid.setText(Double.toString(current.paidAmt));
        holder.owed.setText(Double.toString(current.owedAmt));
        holder.include.setChecked(current.includeMember);
    }

   // public User getUser(int position) {
   //     return dataBillSpec.get(position).user;
   // }

    @Override
    public int getItemCount () {
            return dataBillSpec.size();
        }
}
