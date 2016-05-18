package com.anenn.passcode;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import anenn.com.passcode.R;

/**
 * Created by anenn <anennzxq@gmail.com> on 5/18/16.
 */
public class InputMethodView extends FrameLayout implements AdapterView.OnItemClickListener {

    private GridView mGridView;
    private PassCodeView mPassCodeView;
    private List<PassCodeBO> mPassCodeList;

    public InputMethodView(Context context) {
        this(context, null);
    }

    public InputMethodView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputMethodView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode())
            return;

        initView(context);
        initData();
    }

    private void initView(Context context) {
        mGridView = new GridView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mGridView.setLayoutParams(params);
        mGridView.setNumColumns(3);
        mGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        mGridView.setOnItemClickListener(this);

        addView(mGridView);
    }

    private void initData() {
        mPassCodeList = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            mPassCodeList.add(new PassCodeBO(i, PassCodeBO.TYPE_NUMBER));
        }
        mPassCodeList.add(new PassCodeBO(null, PassCodeBO.TYPE_EMPTY));
        mPassCodeList.add(new PassCodeBO(0, PassCodeBO.TYPE_NUMBER));
        mPassCodeList.add(new PassCodeBO(R.drawable.ic_del, PassCodeBO.TYPE_DELETE));

        PassCodeAdapter mPassCodeAdapter = new PassCodeAdapter(getContext(), mPassCodeList);
        mGridView.setAdapter(mPassCodeAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mPassCodeView != null) {
            if (position >= 0 && position <= 8 || position == 10) {
                mPassCodeView.appendPassCode(mPassCodeList.get(position).getObj().toString());
            } else if (position == 11) {
                mPassCodeView.delPassCode();
            }
        }
    }

    public void setupPassCodeView(PassCodeView passCodeView) {
        mPassCodeView = passCodeView;
    }
}
