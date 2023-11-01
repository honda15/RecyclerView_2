package com.example.recyclerview_2;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final Context myContent;
    private final List<Map<String, Object>> myFoodList;

    public MyAdapter(Context context, List<Map<String,Object>> foodList) {
        //存參數
        myContent = context;
        myFoodList = foodList;
    }

    public List<Map<String,Object>> returnData(){
        return myFoodList;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(myContent).inflate(R.layout.item_layuot, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        Map<String, Object> data = myFoodList.get(position);
        String name = data.get("name").toString();
        int price = (int) data.get("price");
        int qty = (int) data.get("qty");
        int picId = (int) data.get("picture");



        // 將上面的元件連結到item_layout上的view
        holder.imageViewItemPic.setImageResource(picId);
        holder.textViewItemName.setText(name);
        holder.textViewItemPrice.setText(String.valueOf(price));
        holder.editTextQty.setText(String.valueOf(qty));

    }

    @Override
    public int getItemCount() {
        return myFoodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView imageViewItemPic;
        private final TextView textViewItemName,textViewItemPrice,textViewMinus,textViewPlus;
        private final EditText editTextQty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // item元件
            imageViewItemPic = itemView.findViewById(R.id.imageView_itemFoodPic);
            textViewItemName = itemView.findViewById(R.id.textView_item_fooditem);
            textViewItemPrice = itemView.findViewById(R.id.textView_itemPrice);
            textViewMinus = itemView.findViewById(R.id.textView_itemMinus);
            textViewPlus = itemView.findViewById(R.id.textView_itemPlus);
            editTextQty = itemView.findViewById(R.id.editText_itemInput);

            textViewPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();
                    Map<String, Object> data = myFoodList.get(index);
                    int qty = (int) data.get("qty");
                    qty++;
                    data.put("qty",qty);
                    editTextQty.setText(String.valueOf(qty));
                }
            });
            textViewMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();
                    Map<String, Object> data = myFoodList.get(index);
                    int qty = (int) data.get("qty");
                    if(qty>0)
                        qty--;
                    data.put("qty",qty);
                    editTextQty.setText(String.valueOf(qty));
                }
            });
            
            editTextQty.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    // 監聽鍵盤的enter鍵
                    if(event.getAction()==KeyEvent.ACTION_DOWN && keyCode==KeyEvent.KEYCODE_ENTER){
                        String value = editTextQty.getText().toString();
                        int qty = Integer.parseInt(value);
                        Log.v("lee","value= " + qty);
                        int index = getAdapterPosition();
                        Map<String, Object> data = myFoodList.get(index);
                        data.put("qty",qty);
                        return true;
                    }
                    String temp=editTextQty.getText().toString();
                    Log.v("lee","onkey()="+temp);
                    return false;
                }
            });
        }
    }
}
