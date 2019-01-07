package chat.foreseers.com.foreseers.fragment;


import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import chat.foreseers.com.foreseers.R;
import chat.foreseers.com.foreseers.adapter.ListOfPopularAdapter;
import chat.foreseers.com.foreseers.adapter.PeopleAdapter;
import chat.foreseers.com.foreseers.bean.ListOfPopularBean;
import chat.foreseers.com.foreseers.bean.PeopleBean;
import chat.foreseers.com.foreseers.global.BaseMainFragment;

/**
 * 匹配
 */
public class MatchFragment extends BaseMainFragment {


    Unbinder unbinder;
    @BindView(R.id.recycler_list_of_popular)
    RecyclerView recyclerListOfPopular;
    @BindView(R.id.recycler_people)
    RecyclerView recyclerPeople;

    private List<PeopleBean> peopleBeans = new ArrayList<>();
    private PeopleBean peopleBean = new PeopleBean();
    private LinearLayoutManager linearLayoutManager;
    private List<ListOfPopularBean> listOfPopularBeans = new ArrayList<>();
    private ListOfPopularBean listOfPopularBean = new ListOfPopularBean();


    public MatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void initViews() {
//        排行榜
        ListOfPopularAdapter listOfPopularAdapter = new ListOfPopularAdapter(getActivity(), listOfPopularBeans);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerListOfPopular.setLayoutManager(linearLayoutManager);
        recyclerListOfPopular.setAdapter(listOfPopularAdapter);


//      匹配
        PeopleAdapter peopleAdapter = new PeopleAdapter(getActivity(), peopleBeans);
//         StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
//        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerPeople.setLayoutManager(gridLayoutManager);
        recyclerPeople.setAdapter(peopleAdapter);
        peopleAdapter.setNewData(peopleBeans);

    }

    @Override
    public void initDatas() {
        for (int i = 0; i < 10; i++) {
            peopleBean.setCity("asdasdasf");
            peopleBeans.add(peopleBean);


            listOfPopularBean.setName("sds");
            listOfPopularBeans.add(listOfPopularBean);
        }
    }

    @Override
    public void installListeners() {

    }

    @Override
    public void processHandlerMessage(Message msg) {

    }
}
