/**
 * Project package
 */
package com.example.project5cafeapp;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project5cafeapp.data.MenuItem;
import com.example.project5cafeapp.donut.CakeDonut;
import com.example.project5cafeapp.donut.DonutHole;
import com.example.project5cafeapp.donut.YeastDonut;
import com.example.project5cafeapp.donut.flavors.CakeFlavor;
import com.example.project5cafeapp.donut.flavors.HoleFlavor;
import com.example.project5cafeapp.donut.flavors.YeastFlavor;

import java.util.ArrayList;

/**
 * This class is an adapter class that is used to instantiate an adapter for the RecyclerView
 * The RecyclerView is used for purchasing donuts
 * @author Hyeon Oh, Nikhil Agarwal
 */
class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder>{
    private Context context; //need the context to inflate the layout
    private ArrayList<Item> items; //need the data binding to each row of RecyclerView
    public static final int QUICK_ADD_QUANTITY = 1;

    /**
     * Constructor for Class
     * @param context data for this context
     * @param items list of items
     */
    public ItemsAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    /**
     * This method will inflate the row layout for the items in the RecyclerView
     * @param parent parent layer
     * @param viewType the type of the view
     * @return
     */
    @NonNull
    @Override
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the row layout for the items
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_view, parent, false);

        return new ItemsHolder(view);
    }

    /**
     * Assign data values for each row according to their "position" (index) when the item becomes
     * visible on the screen.
     * @param holder the instance of ItemsHolder
     * @param position the index of the item in the list of items
     */
    @Override
    public void onBindViewHolder(@NonNull ItemsHolder holder, int position) {
        //assign values for each row
        holder.tv_name.setText(items.get(position).getItemName());
        holder.tv_price.setText(items.get(position).getUnitPrice());
        holder.im_item.setImageResource(items.get(position).getImage());
    }

    /**
     * Get the number of items in the ArrayList.
     * @return the number of items in the list.
     */
    @Override
    public int getItemCount() {
        return items.size(); //number of MenuItem in the array list.
    }

    /**
     * Get the views from the row layout file, similar to the onCreate() method.
     */
    public static class ItemsHolder extends RecyclerView.ViewHolder {
        /**
         * TextView for price
         */
        private TextView tv_name, tv_price;
        /**
         * TextView for item
         */
        private ImageView im_item;
        /**
         * Button for info
         */
        private Button btn_add;
        /**
         * constraints for this item
         */
        private ConstraintLayout parentLayout; //this is the row layout

        /**
         * locates each view of the rows in the RecyclerView and also calls a method for the button
         * @param itemView view of items.
         */
        public ItemsHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_flavor);
            tv_price = itemView.findViewById(R.id.tv_price);
            im_item = itemView.findViewById(R.id.im_item);
            btn_add = itemView.findViewById(R.id.btn_add);
            parentLayout = itemView.findViewById(R.id.rowLayout);
            setAddButtonOnClick(itemView); //register the onClicklistener for the button on each row.


            parentLayout.setOnClickListener(new View.OnClickListener() {
                /**
                 * set onClickListener for the row layout,
                 * clicking on a row will navigate to a new activity which is ItemSelectedActivity
                 * In the new view, the user will be able to pick the amount of donuts they wish to purchase
                 * @param view The view that was clicked.
                 */
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), ItemSelectedActivity.class);
                    intent.putExtra("ITEM", tv_name.getText());
                    itemView.getContext().startActivity(intent);
                }
            });

        }

        /**
         * Set the onClickListener for the button on each row.
         * Clicking on the button will create an AlertDialog with the options of YES/NO.
         * @param itemView view of items
         */
        private void setAddButtonOnClick(@NonNull View itemView) {
            btn_add.setOnClickListener(new View.OnClickListener() {
                /**
                 * When clicking the button in any of the rows of the RecyclerView, an AlertDialog will be triggered
                 * The AlertDialog will show the user the donut flavor
                 * @param view The view that was clicked.
                 */
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
                    alert.setTitle("Description");
                    alert.setMessage(tv_name.getText().toString());
                    alert.setPositiveButton("done", new DialogInterface.OnClickListener() {
                        /**
                         * When closing the AlertDialog, a fading message will be shown on the bottom of the screen
                         * The fading message will say "Description Closed."
                         * @param dialog the dialog that received the click
                         * @param which the button that was clicked (ex.
                         *              {@link DialogInterface#BUTTON_POSITIVE}) or the position
                         *              of the item clicked
                         */
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(itemView.getContext(),
                                    "Description Closed.", Toast.LENGTH_LONG).show();
                        }

                    }).setNegativeButton("", new DialogInterface.OnClickListener() {
                        /**
                         * When closing the AlertDialog, a fading message will be shown on the bottom of the screen
                         * The fading message will say "Description Closed."
                         * @param dialog the dialog that received the click
                         * @param which the button that was clicked (ex.
                         *              {@link DialogInterface#BUTTON_POSITIVE}) or the position
                         *              of the item clicked
                         */
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(itemView.getContext(),
                                    "Description Closed.", Toast.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
            });
        }
    }
}

