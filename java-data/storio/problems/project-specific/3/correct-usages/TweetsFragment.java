package com.pushtorefresh.storio.sample.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pushtorefresh.storio.db.StorIODb;
import com.pushtorefresh.storio.db.operation.put.PutCollectionResult;
import com.pushtorefresh.storio.sample.R;
import com.pushtorefresh.storio.sample.SampleApp;
import com.pushtorefresh.storio.sample.db.entity.Tweet;
import com.pushtorefresh.storio.sample.ui.DividerItemDecoration;
import com.pushtorefresh.storio.sample.ui.ToastHelper;
import com.pushtorefresh.storio.sample.ui.UiStateController;
import com.pushtorefresh.storio.sample.ui.adapter.TweetsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TweetsFragment extends BaseFragment {
    void reloadData() {
        final Subscription subscription = storIODb
                .get()
                .listOfObjects(Tweet.class)
                .withMapFunc(Tweet.MAP_FROM_CURSOR)
                .withQuery(Tweet.GET_ALL_QUERY)
                .prepare()
                .createObservableStream() // it will be subscribed to changes in tweets table!
                .delay(1, TimeUnit.SECONDS) // for better User Experience
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Tweet>>() {
                    @Override public void onError(Throwable e) {
                        uiStateController.setUiStateError();
                        tweetsAdapter.setTweets(null);
                    }

        // ...

        unsubscribeOnStop(subscription); // preventing memory leak
    }
}
