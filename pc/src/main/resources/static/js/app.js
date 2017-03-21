(function($) {
  'use strict';
  $(function() {
    $('#admin-fullscreen').on('click', function() {
      $.AMUI.fullscreen.toggle();
    });

    var getWindowHeight = $(window).height(),
      myappLoginBg = $('.myapp-login-bg');
    myappLoginBg.css('min-height',getWindowHeight + 'px');

    //加载弹出框样式
    layer.config({skin: 'layer-ext-moon', extend:'skin/moon/style.css'});

    //初始化单选复选框样式
    $("input[type='checkbox'], input[type='radio']").uCheck();

    //解决内容页面不能选中文字问题
    //谷歌
    $(".am-tabs-bd").css("-webkit-user-select", "auto");
    $(".am-tabs-bd").css("-webkit-user-drag", "auto");
    //ie
    $(".am-tabs-bd").css("-ms-user-select", "auto");
    $(".am-tabs-bd").css(" -ms-touch-select", "auto");

  });
})(jQuery);

var app = new App();
function App(){
    /*用来存储每个url对应的页面html代码，用去切换tab的时候保存之前请求的结果*/
    this.pageHtmlJSON = {"index":"欢迎使用教材系统"};
}

App.prototype.clickMenu = function(id){
	var cla = $("#"+id).attr("class");
	if("am-icon-angle-right am-fr am-margin-right" == cla){
		$("#"+id).attr("class", "am-icon-angle-down am-fr am-margin-right");
	}else{
		$("#"+id).attr("class", "am-icon-angle-right am-fr am-margin-right");
	}
}

/**
 * alert
 * @param msg
 * @param flag
 */
App.prototype.alert = function(msg, flag){
    if(flag == 0){
        //成功的提示
        layer.alert(msg, {icon: 6});
    }else{
        //失败的提示
        layer.alert(msg, {icon: 5});
    }
}

/**
 * 消息提示
 * @param msg
 * @param flag
 */
App.prototype.msg = function(msg, flag){
    if(flag == 0){
        //成功的提示
        layer.msg(msg, {icon: 6});
    }else{
        //失败的提示
        layer.msg(msg, {icon: 5});
    }
}

App.prototype.confirm = function(content, fun){
    layer.confirm(content, {icon: 3, title:'提示'}, function(index){
        fun();
    });
}

/**
 * 打开一个弹出框，只有关闭按钮
 * @param html
 * @param title
 * @param width
 * @param height
 */
App.prototype.openOneBtnDialog = function(url, title, width, height){
    $.ajax({
        cache: true,
        type: "POST",
        url: url,
        async: false,
        success: function (data) {
            var dialogWidth = app.getWindowWidthSize()*width+'px';
            var dialogHeight = app.getWindowHeightSize()*height+'px';
            if(width > 5){
                dialogWidth = width+'px';
            }
            if(height > 5){
                dialogHeight = height+'px';
            }
            var index = layer.open({
                type: 1,
                title: title,
                area: [dialogWidth, dialogHeight],
                shadeClose: true, //点击遮罩关闭
                content: data,
                shadeClose: false,
                btn: ['关闭'],
                btn2: function(index, layero){
                    layer.close(index);
                }
            });
        }
    });
}

/**
 * 打开一个弹出框
 * @param url
 * @param title
 * @param width
 * @param height
 */
App.prototype.openDialog = function(url, title, width, height, func){
    $.ajax({
        cache: true,
        type: "POST",
        url: url,
        async: false,
        success: function (data) {
            var dialogWidth = app.getWindowWidthSize()*width+'px';
            var dialogHeight = app.getWindowHeightSize()*height+'px';
            if(width > 5){
                dialogWidth = width+'px';
            }
            if(height > 5){
                dialogHeight = height+'px';
            }
            var index = layer.open({
                type: 1,
                title: title,
                area: [dialogWidth, dialogHeight],
                shadeClose: true, //点击遮罩关闭
                content: data,
                shadeClose: false,
                btn: ['提交', '关闭'],
                yes: function(index, layero){
                    func(index);
                },btn2: function(index, layero){
                    layer.close(index);
                }
            });
        }
    });
}

/**
 * 打开一个弹出框, 带下一步按钮
 * @param html
 * @param title
 * @param width
 * @param height
 */
App.prototype.openNextBtnDialog = function(url, title, width, height, func){
    $.ajax({
        cache: true,
        type: "POST",
        url: url,
        async: false,
        success: function (data) {
            var dialogWidth = app.getWindowWidthSize()*width+'px';
            var dialogHeight = app.getWindowHeightSize()*height+'px';
            if(width > 5){
                dialogWidth = width+'px';
            }
            if(height > 5){
                dialogHeight = height+'px';
            }
            var index = layer.open({
                type: 1,
                title: title,
                area: [dialogWidth, dialogHeight],
                shadeClose: true, //点击遮罩关闭
                content: data,
                shadeClose: false,
                btn: ['下一步', '关闭'],
                yes: function(index, layero){
                    func(index);
                },btn2: function(index, layero){
                    layer.close(index);
                }
            });
        }
    });
}

/**
 * 打开一个弹出框, 带审核按钮
 * @param html
 * @param title
 * @param width
 * @param height
 */
App.prototype.openAuditBtnDialog = function(url, title, width, height, func){
    $.ajax({
        cache: true,
        type: "POST",
        url: url,
        async: false,
        success: function (data) {
            var dialogWidth = app.getWindowWidthSize() * width + 'px';
            var dialogHeight = app.getWindowHeightSize() * height + 'px';
            if (width > 5) {
                dialogWidth = width + 'px';
            }
            if (height > 5) {
                dialogHeight = height + 'px';
            }
            var index = layer.open({
                type: 1,
                title: title,
                area: [dialogWidth, dialogHeight],
                shadeClose: true, //点击遮罩关闭
                content: data,
                shadeClose: false,
                btn: ['审核通过', '不通过', '关闭'],
                yes: function (index, layero) {
                    func(index, 0);
                }, btn2: function (index, layero) {
                    func(index, 1);
                }, btn3: function (index, layero) {
                    layer.close(index);
                }
            });
        }
    });
}

/**
 * 复选框全选
 */
App.prototype.checkAll = function(name){
	var cheArr = document.getElementsByName(name);
    var checkValues="";
    for(var i = 0;i < cheArr.length;i ++){
        cheArr[i].checked = true;
        checkValues += $(cheArr[i]).val()+",";
    }
}

/**
 * 复选框反选
 */
App.prototype.checkNAll = function(name){
	var cheArr = document.getElementsByName(name);
    var checkValues="";
    for(var i = 0;i < cheArr.length;i ++){
        if( cheArr[i].checked){
            cheArr[i].checked = false;
        }else{
            cheArr[i].checked = true;
        }
    }
}

/**
 * 树
 */
App.prototype.tree = function(obj, zNodes){
	var setting = {
		check: {
			enable: true,
			nocheckInherit: true,
            chkboxType : { "Y" : "ps", "N" : "ps" }
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};


	function nocheckNode(e) {
		var zTree = $.fn.zTree.getZTreeObj("myTree"),
		nocheck = e.data.nocheck,
		nodes = zTree.getSelectedNodes();
		if (nodes.length == 0) {
			alert("请先选择一个节点");
		}

		for (var i=0, l=nodes.length; i<l; i++) {
			nodes[i].nocheck = nocheck;
			zTree.updateNode(nodes[i]);
		}
	}

	$(document).ready(function(){
		$.fn.zTree.init(obj, setting, zNodes);
		$("#nocheckTrue").bind("click", {nocheck: true}, nocheckNode);
		$("#nocheckFalse").bind("click", {nocheck: false}, nocheckNode);
	});
}


/**
 * 得到浏览器的宽
 * @returns {{x: number, y: number}}
 */
App.prototype.getWindowWidthSize = function() {
    return app.getWindowSize().x;
}
/**
 * 得到浏览器的高
 * @returns {{x: number, y: number}}
 */
App.prototype.getWindowHeightSize = function() {
    return app.getWindowSize().y;
}
/**
 * 得到浏览器的高和宽
 * @returns {{x: number, y: number}}
 */
App.prototype.getWindowSize = function() {
    var client = {
        x:0,
        y:0
    };

    if(typeof document.compatMode != 'undefined' && document.compatMode == 'CSS1Compat') {
        client.x = document.documentElement.clientWidth;
        client.y = document.documentElement.clientHeight;
    } else if(typeof document.body != 'undefined' && (document.body.scrollLeft || document.body.scrollTop)) {
        client.x = document.body.clientWidth;
        client.y = document.body.clientHeight;
    }
    return client;
}

/**
 * 添加选项卡
 * @param url
 * @param obj
 * @param name
 */
App.prototype.addTab = function(url, name, index, index2, isServiceLoad){
    var isAdd = true;
    $("#tab").find("li").each(function(){
        if($(this).attr("lang") == index+"_"+index2){
            $(this).addClass("am-active");
            isAdd = false;
        }else{
            $(this).removeClass("am-active");
        }
    });
    if(isAdd) {
        var addTab = "<li class='am-active' lang='" + index+"_"+index2 + "'><a lang='"+url+"' href='#contentPage' onclick='app.clickTab(this)' style='float: left'>" + name + "</a></li>";
        $("#tab").append(addTab);
    }
    app.openTab(url, isServiceLoad);
}

/**
 * 点击选项卡
 * @param obj
 */
App.prototype.clickTab = function(obj){
    $("#tab").find("li").each(function(){
        $(this).removeClass("am-active");
    });
    $(obj).parent().addClass("am-active");
    var url = $(obj).attr("lang");
    var falg;
    if(0 <= url.indexOf("?")) {
        falg = url.substring(0, url.indexOf("?")).replace("/", "").replace("/", "").replace(".html", "");
    }else{
        falg = url.replace("/", "").replace("/", "").replace(".html", "");
    }
    var html = app.pageHtmlJSON[falg];
    $("#contentPage").html(html);
}

/**
 * 关闭选项卡
 */
App.prototype.removeTab = function(){
    var isFind = false;
    $("#tab").find("li").each(function(){
        if($(this).attr("class") == "am-active" && $(this).attr("lang") != "0" && !isFind){
            var url;
            if(typeof($(this).next("li").html()) == "undefined"){
                $(this).prev("li").addClass("am-active");
                url = $(this).prev("li").find("a").attr("lang");
            }else{
                $(this).next("li").addClass("am-active");
                url = $(this).next("li").find("a").attr("lang");
            }

            var falg;
            if(0 <= url.indexOf("?")) {
                falg = url.substring(0, url.indexOf("?")).replace("/", "").replace("/", "").replace(".html", "");
            }else{
                falg = url.replace("/", "").replace("/", "").replace(".html", "");
            }
            var html = app.pageHtmlJSON[falg];
            $("#contentPage").html(html);

            $(this).remove();
            isFind = true;
        }
    });
}

/**
 * 打开一个tab
 * @param url
 * @param index
 */
App.prototype.openTab = function(url, isServiceLoad){
    var falg;
    if(0 <= url.indexOf("?")) {
        falg = url.substring(0, url.indexOf("?")).replace("/", "").replace("/", "").replace(".html", "");
    }else{
        falg = url.replace("/", "").replace("/", "").replace(".html", "");
    }
    var html = this.pageHtmlJSON[falg];
    /**
     * 如果该url有之前保留的html代码，就加载之前存的，否则重新请求
     */
    if(typeof (html) == "undefined" || isServiceLoad == 0){
        $.ajax({
            cache: true,
            type: "POST",
            url: url,
            async: false,
            success: function (data) {
                $("#contentPage").html(data);
                app.pageHtmlJSON[falg] = data;
            }
        });
    }else{
        $("#contentPage").html(html);
    }
}

/**
 * 点击查询按钮  btnObj为undefined，说明是从点击页码跳转来的，否则是点击查询按钮来的
 * @param obj
 * @param url
 */
App.prototype.searchFormPage = function(obj, url, btnObj){
    $("#rows").val($("#rows_txt").val());
    if(typeof (btnObj) != "undefined") {
        $("#currentPage").val(1);
    }
    if(url != "") {
        if(typeof (btnObj) != "undefined") {
            $(btnObj).button('loading');
        }
        setTimeout(function(){
            var falg = url.replace("/", "").replace("/", "").replace(".html", "");
            var params = {};
            if(null != obj){
                params = obj.serialize();
            }
            $.ajax({
                cache: true,
                type: "POST",
                url: url,
                async: false,
                data: params,
                success: function (data) {
                    $("#contentPage").html(data);
                    app.pageHtmlJSON[falg] = data
                }
            });
        }, 100);
    }
}

/**
 * ajax查询数据，组装成表格
 * @param obj
 * @param url
 * @param btnObj
 * @param tableId
 * @param tdNum
 * @param isCheckBox
 */
App.prototype.searchForm = function(obj, url, btnObj, tableId, tdNum, isCheckBox){
    if(url != "") {
        $(btnObj).button('loading');
        setTimeout(function(){
            var params = {};
            if(null != obj){
                params = obj.serialize();
            }
            $.ajax({
                cache: true,
                type: "POST",
                url: url,
                async: false,
                data: params,
                success: function (data) {
                    var table = $("#"+tableId);
                    if(typeof (data.jsonData) == "undefined" || 0 == data.jsonData.length){
                        var tr = $("<tr></tr>");
                        var td = $("<td colspan=\"99\" align=\"center\" style=\"color: red;\">没有找到相关数据</td>");
                        tr.append(td);
                        table.append(tr);
                    }else{
                        for(var i=0; i<data.jsonData.length; i++){
                            var json = data.jsonData[i];
                            var tr = $("<tr></tr>");
                            for(var j=0; j<tdNum; j++){
                                var td;
                                if(j == 0) {
                                    if (isCheckBox) {
                                        td = $("<td>" +
                                        "<label class=\"am-checkbox am-secondary\" style=\"margin-top:5px; margin-left:24px;\">" +
                                            "<input type=\"checkbox\" name=\"cb\" value=\"${json.toStuFie},${json.stuCode},${json.stuName}\" onclick=\"changeColor(this)\" data-am-ucheck>" +
                                        "</label></td>");
                                    } else {
                                        td = $("<td>"+(i+1)+"</td>");
                                    }
                                }else{
                                    td = $("<td>"+json[j]+"</td>");
                                }
                                tr.append(td);
                            }
                            table.append(tr);
                        }
                    }
                }
            });
        }, 100);
    }
}

/**
 * 选择省中心，联动学习中心
 * @param provSel
 * @param spotSelId
 * @param contextPath
 */
App.prototype.selectProv = function(provSel, spotSelId, contextPath){
    app.changeSelect(provSel);
    var provCode = $(provSel).val();
    $.ajax({
        cache: true,
        type: "POST",
        url:contextPath+"/findSpotByProvCode/doFindSpotByProvCode.htm",
        data:{"provCode":provCode},
        async: false,
        success: function(result) {
            $("#"+spotSelId+" option").remove();
            $("#"+spotSelId).append("<option value=''></option>");
            $("#"+spotSelId).append("<option value='null'>全部</option>");
            if(typeof(result.spotArray) != "undefined"){
                for(var i=0; i<result.spotArray.length; i++){
                    var spot = result.spotArray[i];
                    $("#"+spotSelId).append("<option value='"+spot.code+"'>["+spot.code+"]"+spot.name+"</option>");
                }
            }
        }
    });

}

/**
 * 常用的新增数据的操作
 * @param openUrl
 * @param doUrl
 * @param params
 * @param title
 * @param width
 * @param height
 */
App.prototype.add = function(url, params, index,callBack){
    $.ajax({
        cache: true,
        type: "POST",
        url:url,
        data:params,
        async: false,
        success: function(data) {
            if(data.state == 0){
                app.msg('提交成功', 0);
                if(typeof(index) != "undefined") {
                    layer.close(index);
                }
                if(callBack){
                    callBack(data.data);
                }else{
                    $("#searchBtn").click();
                }
            }else{
                app.msg(data.msg, 1);
            }
        }
    });
}

/**
 * 常用的新增数据的操作
 * @param url
 * @param params
 * @param btnId
 * @param callBack
 */
App.prototype.addAjax = function(url,params,btnId,callBack){
    $("#"+btnId).button('loading');
    $.ajax({
        cache: true,
        type: "POST",
        url:url,
        data:params,
        async: false,
        success: function(data) {
            $("#"+btnId).button('reset');
            if(data.state == 0){
                app.msg('提交成功', 0);
                if(callBack){
                    callBack(data.data);
                }
            }else{
                app.msg(data.msg, 1);
            }
        },
        error:function(data){
            $("#"+btnId).button('reset');
            app.msg('服务器异常', 0);
        }
    });
}
/**
 * 常用的新增数据的操作
 * @param url
 * @param params
 * @param isShow
 * @param callBack
 */
App.prototype.getAjaxData = function(url,params,isShow,callBack){
    $.ajax({
        cache: true,
        type: "POST",
        url:url,
        data:params,
        async: false,
        success: function(data) {
             if(callBack){
                 callBack(data.data);
             }
        },
        error:function(data){
            app.msg('服务器异常', 0);
        }
    });
}
/**
 * 新增有文件上传的数据操作
 * @param url
 * @param formId
 * @param index
 */
App.prototype.addForFile = function(url, formId, index){
    $("#"+formId).ajaxSubmit({
        url : url,
        dataType : 'json',
        success : function(result, statusText){
            if(0 == result.state) {
                if(typeof (result.str) != "undefined") {
                    if ("" == result.str) {
                        app.msg("提交成功！", 0);
                        layer.close(index);
                        $("#searchBtn").click();
                        return 0;
                    } else {
                        app.msg(result.str, 1);
                        return 1;
                    }
                }else{
                    app.msg("提交成功！", 0);
                    layer.close(index);
                    $("#searchBtn").click();
                    return 0;
                }
            }
            if(1 == result.state) {
                app.msg(result.msg, 1);
                return 1;
            }
        }
    });
}

/**
 * 常用的修改数据的操作
 * @param url
 * @param params
 */
App.prototype.edit = function(url, params, index){
    $.ajax({
        cache: true,
        type: "POST",
        url:url,
        data:params,
        async: false,
        success: function(data) {
            if(data.state == 0){
                app.msg('提交成功', 0);
                if(typeof(index) != "undefined") {
                    layer.close(index);
                }
                $("#searchBtn").click();
            }else{
                app.msg(data.msg, 1);
            }
        }
    });
}

/**
 * 常用的删除数据的操作
 * @param confirmStr
 * @param url
 * @param btnObj
 */
App.prototype.del = function(confirmStr, url, params, btnObj){
    app.confirm(confirmStr, function(){
        if(typeof (btnObj) != "undefined") {
            $(btnObj).button('loading');
        }
        setTimeout(function(){
            $.ajax({
                url:url,
                method : 'POST',
                async:false,
                data:params,
                success:function(data){
                    if(data.state == 0){
                        app.msg("删除成功！", 0);
                        $("#searchBtn").click();
                    }else {
                        app.msg(data.msg, 1);
                        if(typeof (btnObj) != "undefined") {
                            $(btnObj).button('reset');
                        }
                    }
                }
            });
        }, 100);
    });
}

/**
 * 常用的操作数据的操作
 * @param confirmStr
 * @param url
 * @param btnObj
 */
App.prototype.operator = function(confirmStr, url, params, btnObj){
    app.confirm(confirmStr, function(){
        $(btnObj).button('loading');
        setTimeout(function(){
            $.ajax({
                url:url,
                method : 'POST',
                async:false,
                data:params,
                success:function(data){
                    if(data.state == 0){
                        app.msg("操作成功！", 0);
                        $("#searchBtn").click();
                    }else {
                        app.msg(data.msg, 1);
                        $(btnObj).button('reset');
                    }
                }
            });
        }, 100);
    });
}

/**
 * 由于AMAEI UI的select没有选择为空值的情况，所以模拟选中全部的时候，值为空
 * @param confirmStr
 * @param url
 * @param btnObj
 */
App.prototype.changeSelect = function(obj){
    if("null" == $(obj).find("option:selected").val()){
        $(obj).find("option").removeAttr("selected");
    }
}