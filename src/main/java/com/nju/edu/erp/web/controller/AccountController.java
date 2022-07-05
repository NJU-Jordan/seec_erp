package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.AccountVO;
import com.nju.edu.erp.model.vo.CreateProductVO;
import com.nju.edu.erp.model.vo.ProductInfoVO;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @PostMapping("/create")
    @Authorized(roles = {Role.ADMIN,  Role.FINANCIAL_STAFF})
    public Response createAccount(@RequestBody AccountVO accountVO) {
        accountService.createAccount(accountVO);
        return Response.buildSuccess();
    }

    @PostMapping("/update")
    @Authorized(roles = {Role.ADMIN,Role.FINANCIAL_STAFF})
    public Response updateAccount(@RequestBody AccountVO accountVO) {
        accountService.updateAccount(accountVO);
        return Response.buildSuccess();
    }
    @GetMapping("/queryAll")
    @Authorized(roles = {Role.ADMIN,  Role.FINANCIAL_STAFF})
    public Response findAllAccount() {
        return Response.buildSuccess(accountService.queryAllAccount());
    }
    @GetMapping("/delete")
    @Authorized(roles = {Role.ADMIN,  Role.FINANCIAL_STAFF})
    public Response deleteAccount(@RequestParam String id) {
        accountService.deleteById(id);
        return Response.buildSuccess();
    }

}
