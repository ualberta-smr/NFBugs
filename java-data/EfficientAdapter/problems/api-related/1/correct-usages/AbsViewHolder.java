package com.skocken.efficientadapter.lib.viewholder;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public abstract class AbsViewHolder<T> extends RecyclerView.ViewHolder {
  
    private void pattern(int parentId, int id, View viewRetrieve) {
        storeView(parentId, id, viewRetrieve);
      
        clearViewCached(parentId, id);
}
