package android.com.imgur;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class commentsAdapter extends RecyclerView.Adapter<commentsAdapter.ViewHolder> {

    private List<commentsAdapter> newdata;
    public commentsAdapter(List<commentsAdapter> newdata){this.newdata = newdata;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.comments,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        viewHolder.accHolder.setText(i);
        viewHolder.comments.setText(i);
    }

    @Override
    public int getItemCount() {
        return newdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView accHolder,comments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            accHolder = itemView.findViewById(R.id.accHolder);
            comments = itemView.findViewById(R.id.comments);

        }
    }
}
