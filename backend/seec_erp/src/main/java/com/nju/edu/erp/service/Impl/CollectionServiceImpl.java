package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.CollectionSheetDao;
import com.nju.edu.erp.dao.ProductDao;
import com.nju.edu.erp.dao.PurchaseSheetDao;
import com.nju.edu.erp.enums.sheetState.CollectionSheetState;
import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.Collection.CollectionSheetContentVO;
import com.nju.edu.erp.model.vo.Collection.CollectionSheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetContentVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;
import com.nju.edu.erp.service.*;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {
    CustomerService customerService;
    CollectionSheetDao collectionSheetDao;
    @Autowired
    public CollectionServiceImpl(CollectionSheetDao collectionSheetDao, CustomerService customerService) {
        this.collectionSheetDao = collectionSheetDao;

        this.customerService = customerService;

    }

    @Override
    public void makeCollectionSheet(UserVO userVO, CollectionSheetVO collectionSheetVO) {
        CollectionSheetPO collectionSheetPO = new CollectionSheetPO();
        BeanUtils.copyProperties(collectionSheetVO, collectionSheetPO);
        collectionSheetPO.setOperator(userVO.getName());
        collectionSheetPO.setCreateTime(new Date());
        CollectionSheetPO latest;
        latest = collectionSheetDao.getLatestSheet();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "XKD");
        collectionSheetPO.setId(id);
        collectionSheetPO.setState(CollectionSheetState.PENDING_LEVEL_2);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<CollectionSheetContentPO> pContentPOList = new ArrayList<>();
        for(CollectionSheetContentVO content : collectionSheetVO.getCollectionSheetContent()){
            CollectionSheetContentPO pContentPO = new CollectionSheetContentPO();
            BeanUtils.copyProperties(content,pContentPO);
            pContentPOList.add(pContentPO);
            pContentPO.setCollectionSheetId(id);
            totalAmount = totalAmount.add(pContentPO.getTotalPrice());
        }
        collectionSheetDao.saveBatchSheetContent(pContentPOList);
        collectionSheetPO.setTotalAmount(totalAmount);
        collectionSheetDao.saveSheet(collectionSheetPO);
    }

    @Override
    public List<CollectionSheetVO> getCollectionSheetByState(CollectionSheetState state) {
        List<CollectionSheetVO> res = new ArrayList<>();
        List<CollectionSheetPO> all;
        if(state == null) {
            all = collectionSheetDao.findAllSheet();
        }else {
            all = collectionSheetDao.findAllByState(state);
        }
        for(CollectionSheetPO po: all) {
            CollectionSheetVO vo = new CollectionSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<CollectionSheetContentPO> alll = collectionSheetDao.findContentBySheetId(po.getId());
            List<CollectionSheetContentVO> vos = new ArrayList<>();
            for (CollectionSheetContentPO p : alll) {
                CollectionSheetContentVO v = new CollectionSheetContentVO();
                BeanUtils.copyProperties(p, v);
                vos.add(v);
            }
            vo.setCollectionSheetContent(vos);
            res.add(vo);
        }
        return res;

    }

    @Override
    public void approval(String collectionSheetId, CollectionSheetState state) {
        if(state.equals(CollectionSheetState.FAILURE)) {
            CollectionSheetPO collectionSheet = collectionSheetDao.findSheetById(collectionSheetId);
            if(collectionSheet.getState() == CollectionSheetState.SUCCESS) throw new RuntimeException("状态更新失败");
            int effectLines = collectionSheetDao.updateSheetState(collectionSheetId, state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
        }else{
            CollectionSheetState prevState;
            if(state.equals(CollectionSheetState.SUCCESS)) {
                prevState = CollectionSheetState.PENDING_LEVEL_2;
            }else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = collectionSheetDao.updateSheetStateOnPrev(collectionSheetId, prevState, state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
            if(state.equals(CollectionSheetState.SUCCESS)){
                CollectionSheetPO collectionsheet = collectionSheetDao.findSheetById(collectionSheetId);
                CustomerPO customerPO = customerService.findCustomerById(collectionsheet.getSupplier());
                customerPO.setReceivable(customerPO.getReceivable().subtract(collectionsheet.getTotalAmount()));
                customerService.updateCustomer(customerPO);
            }
        }


    }

    @Override
    public CollectionSheetVO getCollectionSheetById(String collectionSheetId) {
        return null;
    }
}
