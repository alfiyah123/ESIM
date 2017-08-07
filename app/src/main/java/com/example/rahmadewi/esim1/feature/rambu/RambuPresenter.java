package com.example.rahmadewi.esim1.feature.rambu;

import com.example.rahmadewi.esim1.base.ui.BasePresenter;
import com.example.rahmadewi.esim1.database.CrudRambu;
import com.example.rahmadewi.esim1.models.rambu.ResultItem;

import java.util.ArrayList;
import java.util.List;

class RambuPresenter extends BasePresenter<RambuView> {
    RambuPresenter(RambuView view){
        super.attachView(view);
    }

    private List<ResultItem> resultItems = new ArrayList<>();

    List<ResultItem> initData(String item, int PAGE_SIZE ){
        CrudRambu crudRambu = new CrudRambu();

        resultItems = crudRambu.getData(item, PAGE_SIZE, resultItems);

        System.out.println("nomor index :"+Integer.parseInt(resultItems.get(resultItems.size()-1).getIdRambu()));
        view.updateIndex(Integer.parseInt(resultItems.get(resultItems.size()-1).getIdRambu()));

        return resultItems;
    }

    List<ResultItem> loadData(String item, int index, int PAGE_SIZE){
        view.hideLoading();
        CrudRambu crudRambu = new CrudRambu();

        resultItems = crudRambu.getDataNext(item, index, PAGE_SIZE, resultItems);

        System.out.println("nomor index1 :"+Integer.parseInt(resultItems.get(resultItems.size()-1).getIdRambu()));

        if(resultItems.size() != 0) {
            view.updateIndex(Integer.parseInt(resultItems.get(resultItems.size()-1).getIdRambu()));
            return resultItems;
        }else{
            view.endData("Data Berakhir");
            return null;
        }
    }
}
