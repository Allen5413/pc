package com.allen.web.controller.basic.customer;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.Customer;
import com.allen.service.basic.customer.EditCustomerService;
import com.allen.service.basic.customer.FindCustomerByIdService;
import com.allen.util.DateUtil;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2015/4/28.
 */
@Controller
@RequestMapping(value = "/editCustomer")
public class EditCustomerController extends BaseController {

    @Resource
    private EditCustomerService editCustomerService;
    @Resource
    private FindCustomerByIdService findCustomerByIdService;

    /**
     * 打开
     * @return
     */
    @RequestMapping(value = "open")
    public String openEditMenuPage(@RequestParam("id") long id, HttpServletRequest request) throws Exception {
        Customer customer = findCustomerByIdService.find(id);
        request.setAttribute("customer", customer);
        return "basic/customer/edit";
    }

    /**
     * 编辑
     * @param request
     * @return
     */
    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject editor(HttpServletRequest request, Customer customer)throws Exception{
        JSONObject jsonObject = new JSONObject();
        if(null != customer) {
            customer.setOperator(UserUtil.getLoginUserForName(request));
            customer.setOperateTime(DateUtil.getLongNowTime());
            editCustomerService.edit(customer);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
