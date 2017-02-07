package com.lorcanluo.loadmorerecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author Tu enum@foxmail.com.
 */

public class FootViewHolder extends RecyclerView.ViewHolder {
  private TextView tvLoadMessage;
  private ProgressBar progressBar;

  public FootViewHolder(View view) {
    super(view);
    tvLoadMessage = (TextView) view.findViewById(R.id.load_message);
    progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
  }

  public void doOnNOMore(boolean hasMore) {
    if (!hasMore) {
      tvLoadMessage.setText(R.string.no_more);
      progressBar.setVisibility(View.GONE);
    } else {
      progressBar.setVisibility(View.VISIBLE);
      tvLoadMessage.setText(R.string.loading_message);
    }
  }
}
