package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.CollectionSheetState;
import com.nju.edu.erp.enums.sheetState.PaySheetState;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.vo.Pay.PaySheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.PayService;
import com.nju.edu.erp.service.PayService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/pay")
public class PayController {
    private final PayService payService;

    @Autowired
    public PayController(PayService payService) {
        this.payService = payService;
    }

    @Authorized (roles = {Role.FINANCIAL_STAFF})
    @PostMapping(value = "/sheet-make")
    public Response makePayOrder(UserVO userVO, @RequestBody PaySheetVO paySheetVO)  {
        payService.makePaySheet(userVO, paySheetVO);
        return Response.buildSuccess();
    }
    /**
     * 根据状态查看销售单
     */

    @GetMapping(value = "/sheet-show")
    public Response showSheetByState(@RequestParam(value = "state", required = false) PaySheetState state)  {
        return Response.buildSuccess(payService.getPaySheetByState(state));
    }
    /**
     * 总经理审批
     * @param paySheetId 进货单id
     * @param state 修改后的状态("审批失败"/"审批完成")
     */
    @Authorized (roles = {Role.GM, Role.ADMIN,Role.FINANCIAL_STAFF})
    @GetMapping(value = "/second-approval")
    public Response secondApproval(@RequestParam("paySheetId") String paySheetId,
                                   @RequestParam("state") PaySheetState state)  {

        if(state.equals(PaySheetState.FAILURE) || state.equals(PaySheetState.SUCCESS)) {
            payService.approval(paySheetId, state);
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
        return Response.buildSuccess(payService.getPaySheetById(id));
    }
}