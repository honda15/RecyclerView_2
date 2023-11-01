package com.example.recyclerview_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewData;
    private String[] foodName;
    private int[] foodPrice;
    private TypedArray foodPicture;
    private int[] foodQty;
    private List<Map<String, Object>> foodList;
    private LinearLayoutManager myLayoutManager;
    private MyAdapter adapter;
    private TextView textViewResult;
    private Button buttonCancel,buttonCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewData = findViewById(R.id.recycleView_id);
        foodName = getResources().getStringArray(R.array.food_name);
        foodPrice = getResources().getIntArray(R.array.food_price);
        foodPicture = getResources().obtainTypedArray(R.array.food_pic);
        foodQty = new int[foodName.length];      // 使用者可以點選的數量
        for (int i = 0; i < foodName.length; i++)
            foodQty[i] = 0;                     // 將所有項目數量設為0

        foodList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < foodName.length; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("name", foodName[i]);  // 使用 foodName[i]，而不是整个 foodName 数组
            data.put("price", foodPrice[i]); // 使用 foodPrice[i]，而不是整个 foodPrice 数组
            data.put("qty", foodQty[i]);
            data.put("picture", foodPicture.getResourceId(i, 0));
            foodList.add(data);
        }
//        for (int i = 0; i < foodName.length; i++) {
//            Map<String, Object> data = new HashMap<>();
//            data.put("name", foodName);
//            data.put("price", foodPrice);
//            data.put("qty", foodQty[i]);
//            data.put("picture", foodPicture.getResourceId(i, 0));
//            foodList.add(data);
//        }
        myLayoutManager = new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager.VERTICAL,
                false);
        recyclerViewData.setLayoutManager(myLayoutManager);
        adapter = new MyAdapter(MainActivity.this, foodList);
        recyclerViewData.setAdapter(adapter);

        textViewResult = findViewById(R.id.textView_result);
        textViewResult.setText("");

        buttonCancel = findViewById(R.id.btn_cancel);
        buttonCheckout = findViewById(R.id.btn_checkOut);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i < foodName.length;i++){
                    foodQty[i] = 0;
                    Map<String,Object> data = foodList.get(i);
                    data.put("qty",foodQty[i]);
                    foodList.set(i,data);
                }
                adapter.notifyDataSetChanged();
                textViewResult.setText("");
            }
        });

        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Map<String, Object>> myFoodList = adapter.returnData();
                textViewResult.setText("");
                int sum=0;
                for(int i=0; i < myFoodList.size();i++){
                    Map<String, Object> data = myFoodList.get(i);
                    String name = data.get("name").toString();
                    int price = (int) data.get("price");
                    int qty = (int) data.get("qty");
                    sum+=price*qty;
                    if(qty>0)
                        textViewResult.append(name+" : "+price+" x "+qty+" =$ "+
                                price*qty+"\n");
                }
                textViewResult.append("\nThe total fee :$"+sum);
            }
        });

    }
}