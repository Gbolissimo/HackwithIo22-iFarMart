package com.gbolissimo.ifarmart;

import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteHolder> {
    public List<Note> notesList;
    private OnItemClickListener listener;
    private String pix;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Note model) {
        holder.title.setText(model.getTitle());
        holder.content.setText(model.getContent());

        if (DateUtils.getRelativeTimeSpanString(Long.parseLong((String)model.getTym())).toString().equals("0 minutes ago")){
            holder.tym.setText("Just now");
        }
        else{
            holder.tym.setText(DateUtils.getRelativeTimeSpanString(Long.parseLong((String) model.getTym())));}

       // holder.tym.setText(model.getTym());

//        holder.bookauthor.setText(model.getAuthor());
    //    holder.textViewPriority.setText(String.valueOf(model.getPriority()));



        holder.ncard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(holder.itcard.getContext(),model.getSchoolname(),Toast.LENGTH_SHORT).show();

                Intent newPostIntentqw = new Intent( holder.ncard.getContext(), NoteDetail.class);
                newPostIntentqw.putExtra("id", model.getId());
                newPostIntentqw.putExtra("title",model.getTitle());
                newPostIntentqw.putExtra("content",model.getContent());
                newPostIntentqw.putExtra("link",model.getLink());
                newPostIntentqw.putExtra("image",model.getImage());
                newPostIntentqw.putExtra("tym",model.getTym());

                //newPostIntentqw.putExtra("dedication",note.getDedication());
                holder.ncard.getContext().startActivity(newPostIntentqw);

            }
        });

    }



    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,
                parent, false);
        return new NoteHolder(v);
    }



    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }




    class NoteHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView content;
        TextView tym;
        ImageView delete;

        LinearLayout ncard;

        public NoteHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvTitle);
            content = itemView.findViewById(R.id.tvContent);
            tym = itemView.findViewById(R.id.tvTym);
            delete = itemView.findViewById(R.id.ivDelete);

            ncard = itemView.findViewById(R.id.ncard);


            itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                       listener.onItemClick(getSnapshots().getSnapshot(position), position);

            //            Snackbar.make(v, "FDFFDFD",Snackbar.LENGTH_SHORT).show();
                    }





                }
            });

            //
  //          hubname.setOnClickListener(new View.OnClickListener() {
    //            @Override
      //          public void onClick(View v) {
        //            int position = getAdapterPosition();
//
  //                  final Hub hub = hubsList.get(position);
                     //   Snackbar.make(v, "FDFFDFD",Snackbar.LENGTH_SHORT).show();

    //                  Toast.makeText(hubname.getContext(), hub.getSchoolname().toString(),
      //                     Toast.LENGTH_SHORT).show();

                  //  hub.getSchoolname();




        //        }
          //  });
            //

        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

      //  Hub hub = documentSnapshot.toObject(Hub.class);

    }



    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}