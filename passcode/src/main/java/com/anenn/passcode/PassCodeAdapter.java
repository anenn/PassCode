package com.anenn.passcode;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import anenn.com.passcode.R;

/**
 * Copyright Youdar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <p>
 * Created by anenn <anennzxq@gmail.com> on 5/18/16.
 */
public class PassCodeAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<PassCodeBO> mPassCodeBOList;

    public PassCodeAdapter(Context context, List<PassCodeBO> passCodeBOList) {
        mContext = context;
        mPassCodeBOList = passCodeBOList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mPassCodeBOList != null ? mPassCodeBOList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return getCount() > position ? mPassCodeBOList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.layout_keyview, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PassCodeBO passCodeBO = (PassCodeBO) getItem(position);
        if (passCodeBO != null) {
            switch (passCodeBO.getType()) {
                case PassCodeBO.TYPE_NUMBER:
                    viewHolder.tvKey.setText(String.valueOf(passCodeBO.getObj()));
                    break;
                case PassCodeBO.TYPE_EMPTY:
                    viewHolder.tvKey.setText(null);
                    break;
                case PassCodeBO.TYPE_DELETE:
                    SpannableString spannableString = new SpannableString(" ");
                    ImageSpan imageSpan = new ImageSpan(getDrawable((Integer) passCodeBO.getObj()));
                    spannableString.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    viewHolder.tvKey.setText(spannableString);
                    break;
            }
        }

        return convertView;
    }

    private Drawable getDrawable(int id) {
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 28.0f,
                mContext.getResources().getDisplayMetrics());
        Drawable drawable = ContextCompat.getDrawable(mContext, id);
        drawable.setBounds(0, 0, size, size);
        return drawable;
    }

    private static class ViewHolder {
        private TextView tvKey;

        public ViewHolder(View view) {
            tvKey = (TextView) view.findViewById(R.id.keyView);
        }
    }
}
