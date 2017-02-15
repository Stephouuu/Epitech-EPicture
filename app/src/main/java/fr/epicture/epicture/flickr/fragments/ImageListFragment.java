package fr.epicture.epicture.flickr.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.epicture.epicture.R;
import fr.epicture.epicture.flickr.adapters.ImageListRecyclerAdapter;
import fr.epicture.epicture.flickr.interfaces.ImageListAdapterInterface;
import fr.epicture.epicture.flickr.interfaces.ImageListInterface;
import fr.epicture.epicture.flickr.utils.ImageElement;

public class ImageListFragment extends Fragment {

    private SwipeRefreshLayout swipe;
    private RecyclerView recyclerView;

    private boolean init;
    private int page;
    private boolean end;

    private ImageListRecyclerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init = false;
        page = 1;
        end = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_list_fragment, container, false);

        swipe = (SwipeRefreshLayout)view.findViewById(R.id.imagelist_swipe);
        recyclerView = (RecyclerView)view.findViewById(R.id.imagelist_recyclerview);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int cLast = manager.findLastCompletelyVisibleItemPosition();

                if (cLast == adapter.getItemCount() - 1 && page > 1) {
                    Log.i("scroll", "scroll");
                    ((ImageListInterface)getActivity()).onRequestImageList(page);
                }
            }
        });


        adapter = new ImageListRecyclerAdapter(getActivity(), true, new ImageListAdapterInterface() {
            @Override
            public void onImageClick() {

            }
        });
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!init) {
            refresh();
            init = true;
        }
    }

    public void refresh() {
        page = 1;
        end = false;
        ((ImageListInterface)getActivity()).onRequestImageList(page);
    }

    public void refreshList(@Nullable List<ImageElement> imageElementList) {
        if (imageElementList != null) {
            adapter.addList(imageElementList);
            ++page;
        }
        refreshSwipe(imageElementList == null);
    }

    private void refreshSwipe(final boolean loading) {
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(loading);
            }
        });
    }

}
