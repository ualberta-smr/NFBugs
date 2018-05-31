  package com.skocken.efficientadapter.lib.viewholder;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;


public void clearViewsCached() {
        mSparseSparseArrayView.clear();
    }

    /**
     * Allow to clear the cache of view retrieved
     */
    public void clearViewCached(int viewId) {
        clearViewCached(0, viewId);
    }

    /**
     * Allow to clear the cache of view retrieved
     */
    public void clearViewCached(int parentId, int viewId) {
        SparseArray<View> sparseArrayViewsParent = mSparseSparseArrayView.get(parentId);
        if (sparseArrayViewsParent != null) {
            sparseArrayViewsParent.remove(viewId);
        }
    }
