package com.example.color_fit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
//import java.util.List;

import static com.example.color_fit.MainActivity.mJsonString;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.CustomViewHolder> implements Filterable {

    private ArrayList<com.example.color_fit.ClothData> mList ; // = null
    private ArrayList<com.example.color_fit.ClothData> mListAll ; // = null
    private Activity context ; // = nul

    public UserAdapter(Activity context, ArrayList<com.example.color_fit.ClothData> list) {
        this.mList = list;      // list
        this.mListAll = list;   // filtered list
        this.context = context;
    }

    private onItemListener mListener;
    public void setOnClickListener(onItemListener listener){
        mListener = listener;
    }
    public void dataSetChanged(ArrayList<com.example.color_fit.ClothData> exampleList){
        mList = exampleList;
        notifyDataSetChanged();
    }


    // onCreateViewHolder------------------------------------------
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    // onBindViewHolder--------------------------------------------
    //onBindViewHolder 호출될때 CustomViewHolder에 데이터를 추가
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, final int position) {

        com.example.color_fit.ClothData item = mList.get(position);
        Glide.with(viewholder.itemView.getContext())
                .load(item.getGoods_image())
                .into(viewholder.image);

        viewholder.name.setText(mList.get(position).getGoods_name());
        viewholder.price.setText(mList.get(position).getGoods_price());

        if(mListener != null){
//            final int pos = position;
            viewholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(position);
                }
            });
        }
    }


    // getItemCount ------------------------------------------------
    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
//        return mList.size();    //임시
    }

    // Filterable implement-------------------------------
    @Override   // 검색 기능
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<com.example.color_fit.ClothData> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mListAll);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (com.example.color_fit.ClothData cd : mListAll) {
                    // TODO filter 대상 setting
                    if (cd.getGoods_name().toLowerCase().contains(filterPattern)) {
                        filteredList.add(cd);
                    }
                }
                mList = filteredList;
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
//            mList.clear();
//            mList.addAll((List) results.values);
            mList = (ArrayList<com.example.color_fit.ClothData>)results.values;
            notifyDataSetChanged();
        }
    };

    public interface onItemListener {
        void onItemClicked(int position);
    }

    // 뷰홀더 클래스 ---------------------------------
    //레이아웃 파일에 있는 UI 컴포넌트를 CustomViewHolder 클래스의 멤버변수와 연결
    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView name;
        protected TextView price;

        public CustomViewHolder(final View view) {
            super(view);
            this.image = (ImageView) view.findViewById(R.id.iv_image);
            this.name = (TextView) view.findViewById(R.id.tv_name);
            this.price = (TextView) view.findViewById(R.id.tv_price);

            // 각 리스트 누를때 웹페이지 연결
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    try {
                        int ii=0;
                        JSONObject jsonObject = new JSONObject(mJsonString);
                        JSONArray jsonArray = jsonObject.getJSONArray("Cloth");

                        String cho = mList.get(pos).getGoods_name();

                        while(true) {
                            JSONObject itemm = jsonArray.getJSONObject(ii);
                            String abc = itemm.getString("g_name");

                            if (!cho.equals(abc)) ii = ii + 1;
                            else break;

                        }

                        JSONObject item = jsonArray.getJSONObject(ii);
                        String link = item.getString("siteurl");


                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                        context.startActivity(intent);

                    }catch (JSONException e){
                        Log.d("phptest_MainActivity","reee : ", e);
                    }
                }
            });

//            view.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//
//                    return false;
//                }
//            });
        }

    }

}

