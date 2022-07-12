package com.gbolissimo.ifarmart;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class ProductAdapter extends FirestoreRecyclerAdapter<Product, ProductAdapter.ProductHolder> {
    public List<Product> productsList;
    private OnItemClickListener listener;
    private String pix;

    public ProductAdapter(@NonNull FirestoreRecyclerOptions<Product> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductHolder holder, int position, @NonNull Product model) {
        holder.title.setText(model.getProductname());
        holder.content.setText(model.getQuantity());


        if (DateUtils.getRelativeTimeSpanString(Long.parseLong((String)model.getTtimestamp())).toString().equals("0 minutes ago")){
            holder.am.setText("Just now");
        }
        else{
            holder.am.setText(DateUtils.getRelativeTimeSpanString(Long.parseLong((String) model.getTtimestamp())));}

        if(model.getProductpix()==null) {
            pix = "";

            try {
                Resources res = holder.image.getContext().getResources();
                //Nếu chưa có avatar thì để hình mặc định
                Bitmap src;
                if (pix.equals("")) {
                    src = BitmapFactory.decodeResource(res, R.drawable.placeholder);
                } else {
                    byte[] decodedString = Base64.decode(pix, Base64.DEFAULT);
                    src = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                }

                holder.image.setImageBitmap(src);
            }catch (Exception e){
            }
        }
        else{

            pix=model.getProductpix();

            try {
                Resources res = holder.image.getContext().getResources();
                //Nếu chưa có avatar thì để hình mặc định
                Bitmap src;
                if (pix.equals("")) {
                    src = BitmapFactory.decodeResource(res, R.drawable.placeholder);
                } else {
                    byte[] decodedString = Base64.decode(pix, Base64.DEFAULT);
                    src = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                }

                //   holder.coverimg.setImageBitmap(src);
             //   holder.image.setImageDrawable(ImageUtils.roundedImage(holder.image.getContext(), src));
                Glide.with(holder.image.getContext())
                        .load(src)
                        .placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(holder.image);
            }catch (Exception e){
            }


        }




        holder.ncard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(holder.ncard.getContext(),model.getProductname(), Toast.LENGTH_SHORT).show();

                Intent newPostIntentqw = new Intent( holder.ncard.getContext(), ProductDetail.class);
                newPostIntentqw.putExtra("id", model.getId());
                newPostIntentqw.putExtra("name",model.getProductname());
                newPostIntentqw.putExtra("quantity",model.getQuantity());
                newPostIntentqw.putExtra("address",model.getFarmaddress());
                newPostIntentqw.putExtra("price",model.getPrice());
                newPostIntentqw.putExtra("contact1",model.getContactno1());
                newPostIntentqw.putExtra("contact2",model.getContactno2());
                newPostIntentqw.putExtra("seller",model.getSeller());
                newPostIntentqw.putExtra("selleremail",model.getSelleremail());
                newPostIntentqw.putExtra("verification",model.getVerification());
                newPostIntentqw.putExtra("businessname",model.getBusinessname());
                newPostIntentqw.putExtra("others1",model.getOthers1());
                newPostIntentqw.putExtra("others2",model.getOthers2());
                newPostIntentqw.putExtra("others3",model.getOthers3());
                newPostIntentqw.putExtra("others4",model.getOthers4());
                newPostIntentqw.putExtra("productpix",model.getProductpix());
                newPostIntentqw.putExtra("timestamp",model.getTtimestamp());


                //newPostIntentqw.putExtra("dedication",note.getDedication());
                holder.ncard.getContext().startActivity(newPostIntentqw);

            }
        });

    }



    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,
                parent, false);
        return new ProductHolder(v);
    }



    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }




    class ProductHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView content;
        TextView tym;
        TextView am;
        ImageView image;

        LinearLayout ncard;

        public ProductHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvTitle);
            content = itemView.findViewById(R.id.tvContent);
            tym = itemView.findViewById(R.id.tvTym);
            am = itemView.findViewById(R.id.tvAm);
           image = itemView.findViewById(R.id.ivProduct);

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