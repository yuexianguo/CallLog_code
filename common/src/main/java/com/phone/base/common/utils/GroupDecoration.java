package com.phone.base.common.utils;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * description ：Only vertical
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2020/4/27
 */

public class GroupDecoration<T extends GroupItem> extends RecyclerView.ItemDecoration {
    private static final String TAG = "GroupDecoration";

    private View groupView;

    private GroupCallback<T> callback;

    private List<T> groupList = new ArrayList<>();

    private SparseBooleanArray groupPositions = new SparseBooleanArray();

    private int groupViewHeight;

    private long itemsCountRecorded = -1;

    public interface GroupCallback<T> {
        void setGroup(List<T> groupList, SparseBooleanArray groupPositions);

        void bindGroupView(View groupView, T t);
    }

    public GroupDecoration(View groupView, GroupCallback<T> callback) {
        this.groupView = groupView;
        this.callback = callback;
    }

    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view, @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getAdapter() == null) {
            return;
        }
        int position = parent.getChildAdapterPosition(view);

        LogUtil.d(TAG, "getItemOffsets: position=" + position + ", items size=" + parent.getAdapter().getItemCount());
        if (isLinearAndVertical(parent)) {
            if (itemsCountRecorded != parent.getAdapter().getItemCount()) {
                measureView(groupView, parent);
                //clear
                groupList.clear();
                groupPositions.clear();
                callback.setGroup(groupList, groupPositions);
                itemsCountRecorded = parent.getAdapter().getItemCount();
            }
            if (groupPositions.get(position)) {
                outRect.top = groupViewHeight;
            }
        }
    }

    private void measureView(View view, View parent) {
        if (view.getLayoutParams() == null) {
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        int parentWidthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
        int childWidthSpec = ViewGroup.getChildMeasureSpec(parentWidthSpec, parent.getPaddingLeft() + parent.getPaddingRight(), view.getLayoutParams().width);

        int childHeightSpec;
        if (view.getLayoutParams().height > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(view.getLayoutParams().height, View.MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }

        view.measure(childWidthSpec, childHeightSpec);

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        groupViewHeight = view.getMeasuredHeight();
    }

    @Override
    public void onDraw(@NotNull Canvas c, @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (groupList.size() == 0 || !isLinearAndVertical(parent)) {
            return;
        }
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            float left = child.getLeft();
            float top = child.getTop();

            int position = parent.getChildAdapterPosition(child);

            //draw
            if (groupPositions.get(position)) {
                c.save();
                c.translate(left, top - groupViewHeight);

                int count = 0;
                int groupIndex = 0;
                for (int j = 0; j < groupList.size(); j++) {
                    count += groupList.get(j).getItems().size();
                    if (position < count) {
                        groupIndex = j;
                        break;
                    }
                }
                callback.bindGroupView(groupView, groupList.get(groupIndex));
                measureView(groupView, parent);
                groupView.draw(c);
                c.restore();
            }
        }
    }

    @Override
    public void onDrawOver(@NotNull Canvas c, @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    private boolean isLinearAndVertical(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        return layoutManager instanceof LinearLayoutManager &&
                ((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.VERTICAL;
    }

}
