package com.lorcanluo.loadmorerecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by can.luo on 2017/1/22.
 */
public class ALoadMoreRecyclerView extends RecyclerView {
  private OnLoadMoreListener onLoadMoreListener;
  private boolean isLoading;

  public ALoadMoreRecyclerView(Context context) {
    this(context, null);
  }

  public ALoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ALoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    addOnScrollListener(new ScrollToBottomScrollListener());
  }

  public void onLoadFinished() {
    isLoading = false;
  }

  public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
    this.onLoadMoreListener = onLoadMoreListener;
  }

  private class ScrollToBottomScrollListener extends OnScrollListener {
    @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      super.onScrolled(recyclerView, dx, dy);
      LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
      int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
      int totalItemCount = linearLayoutManager.getItemCount();
      if (totalItemCount <= lastVisibleItemPosition + 1) {
        if (!isLoading
            && onLoadMoreListener != null
            && ((ALoadMoreAdapter) getAdapter()).isHasMore()) {
          isLoading = true;
          onLoadMoreListener.onLoadMore();
        }
      }
    }
  }

  public interface OnLoadMoreListener {
    void onLoadMore();
  }
}
