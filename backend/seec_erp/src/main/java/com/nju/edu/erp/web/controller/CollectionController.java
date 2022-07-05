package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.CollectionSheetState;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.vo.Collection.CollectionSheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.CollectionService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/collection")
public class CollectionController {
    private final CollectionService collectionService;

    @Autowired
    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Authorized (roles = {Role.FINANCIAL_STAFF})
    @PostMapping(value = "/sheet-make")
    public Response makeCollectionOrder(UserVO userVO, @RequestBody CollectionSheetVO collectionSheetVO)  {
        collectionService.makeCollectionSheet(userVO, collectionSheetVO);
        return Response.buildSuccess();
    }
    /**
     * 根据状态查看销售单
     */

    @GetMapping(value = "/sheet-show")
    public Response showSheetByState(@RequestParam(value = "state", required = false) CollectionSheetState state)  {
        return Response.buildSuccess(collectionService.getCollectionSheetByState(state));
    }
    /**
     * 总经理审批
     * @param collectionSheetId 进货单id
     * @param state 修改后的状态("审批失败"/"审批完成")
     */
    @Authorized (roles = {Role.GM, Role.ADMIN,Role.FINANCIAL_STAFF})
    @GetMapping(value = "/second-approval")
    public Response secondApproval(@RequestParam("collectionSheetId") String collectionSheetId,
                                   @RequestParam("state") CollectionSheetState state)  {

        if(state.equals(CollectionSheetState.FAILURE) || state.equals(CollectionSheetState.SUCCESS)) {
            collectionService.approval(collectionSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000","操作失败"); // code可能得改一个其他的
        }
    }
    /**
     * 根据销售单Id搜索销售单信息
     * @param id 销售单Id
     * @return 销售单全部信息
     */
    @GetMapping(value = "/find-sheet")
    public Response findBySheetId(@RequestParam(value = "id") String id)  {
        return Response.buildSuccess(collectionService.getCollectionSheetById(id));
    }
}
