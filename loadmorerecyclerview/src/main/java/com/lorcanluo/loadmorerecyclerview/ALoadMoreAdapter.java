package com.lorcanluo.loadmorerecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by can.luo on 2017/1/22.
 */

public abstract class ALoadMoreAdapter extends RecyclerView.Adapter {
  public static final int TYPE_LOAD_MORE_ITEM = -99999;
  private LayoutInflater inflater;
  private boolean hasMore;

  public ALoadMoreAdapter(Context context) {
    this.inflater = LayoutInflater.from(context);
  }

  public boolean isHasMore() {
    return hasMore;
  }

  public void setHasMore(boolean hasMore) {
    this.hasMore = hasMore;
    notifyDataSetChanged();
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case TYPE_LOAD_MORE_ITEM:
        return new FootViewHolder(inflater.inflate(R.layout.item_load_more, parent, false));
      default:
        return onChildCreateViewHolder(parent, viewType);
    }
  }

  public abstract RecyclerView.ViewHolder onChildCreateViewHolder(ViewGroup parent, int viewType);

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    int type = getItemViewType(position);
    switch (type) {
      case TYPE_LOAD_MORE_ITEM:
        FootViewHolder footViewHolder = (FootViewHolder) holder;
        footViewHolder.doOnNOMore(hasMore);
        break;
      default:
        onChildBindViewHolder(holder, position);
        break;
    }
  }

  public abstract void onChildBindViewHolder(RecyclerView.ViewHolder holder, int position);

  @Override public int getItemViewType(int position) {
    if (getItemCount() - 1 == position) {
      return TYPE_LOAD_MORE_ITEM;
    } else {
      return getChildItemViewType(position);
    }
  }

  public abstract int getChildItemViewType(int position);

  @Override public int getItemCount() {
    if (hasMore) {
      return getChildItemCount() + 1;
    }
    return getChildItemCount();
  }

  public abstract int getChildItemCount();
}
