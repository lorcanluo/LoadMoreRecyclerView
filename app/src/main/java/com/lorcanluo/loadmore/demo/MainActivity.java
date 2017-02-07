package com.lorcanluo.loadmore.demo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lorcanluo.loadmorerecyclerview.ALoadMoreRecyclerView;
import com.lorcanluo.loadmorerecyclerview.AbstractLoadMoreAdapter;
import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
  private ALoadMoreRecyclerView recyclerView;
  private MyTestAdapter adapter;
  private DelayHandler handler = new DelayHandler(this);

  private static class DelayHandler extends Handler {
    private WeakReference<MainActivity> activityWeakReference;

    public DelayHandler(MainActivity activity) {
      this.activityWeakReference = new WeakReference<MainActivity>(activity);
    }

    @Override public void handleMessage(Message msg) {
      super.handleMessage(msg);
      MainActivity activity = activityWeakReference.get();
      if (activity == null) {
        return;
      }
      activity.addItems();
    }
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    recyclerView = (ALoadMoreRecyclerView) findViewById(R.id.recyclerView);
    adapter = new MyTestAdapter(this);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setOnLoadMoreListener(new ALoadMoreRecyclerView.OnLoadMoreListener() {
      @Override public void onLoadMore() {
        handler.sendEmptyMessageDelayed(100, 2000);
      }
    });
    adapter.addItem(20);
    adapter.setHasMore(true);
  }

  private void addItems() {
    adapter.addItem(20);
    recyclerView.onLoadFinished();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    switch (id) {
      case R.id.action_add:
        adapter.addItem(20);
        break;
      case R.id.action_no_more:
        adapter.setHasMore(false);
        break;
    }
    return true;
  }

  private class MyTestAdapter extends AbstractLoadMoreAdapter {
    private int[] colors =
        { Color.BLACK, Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.CYAN, Color.DKGRAY };
    private int item;

    public MyTestAdapter(Context context) {
      super(context);
    }

    public void addItem(int count) {
      item = item + count;
      notifyItemInserted(getItemCount());
    }

    @Override
    public RecyclerView.ViewHolder onChildCreateViewHolder(ViewGroup parent, int viewType) {
      View item =
          LayoutInflater.from(MainActivity.this).inflate(R.layout.item_textview, parent, false);
      return new TextViewHolder(item);
    }

    @Override public void onChildBindViewHolder(RecyclerView.ViewHolder holder, int position) {
      TextViewHolder textViewHolder = (TextViewHolder) holder;
      textViewHolder.updateItem(position);
    }

    @Override public int getChildItemViewType(int position) {
      return 0;
    }

    @Override public int getChildItemCount() {
      return item;
    }

    class TextViewHolder extends RecyclerView.ViewHolder {
      private TextView textView;
      private View itemView;

      public TextViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView);
        this.itemView = itemView;
      }

      public void updateItem(int position) {
        textView.setText("" + position);
        itemView.setBackgroundColor(colors[position % 7]);
      }
    }
  }
}
