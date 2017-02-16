package com.allen.web.controller.basic.customer;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.Customer;
import com.allen.service.basic.customer.AddCustomerService;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Controller
@RequestMapping("/addCustomer")
public class AddCustomerController extends BaseController {

    @Resource
    private AddCustomerService addCustomerService;

    /**
     * @return
     */
    @RequestMapping(value = "open")
    public String open(){
        return "basic/customer/add";
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, Customer customer) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if(null != customer) {
            customer.setCreator(UserUtil.getLoginUserForName(request));
            customer.setOperator(UserUtil.getLoginUserForName(request));
            addCustomerService.add(customer);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
