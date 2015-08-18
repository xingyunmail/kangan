<%--
  Created by IntelliJ IDEA.
  User: zzq
  Date: 15-5-15
  Time: 下午3:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<link href="resources/css/ui-dialog.css" rel="stylesheet" media="screen">
<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="muted pull-left">订单类型列表</div>
    </div>
    <div id="alert"></div>
    <div class="block-content collapse in">
        <div class="row span12">
            <div class="span3">
                <button class="btn order-btn" id="add" onclick="add()">添 加</button>
            </div>
        </div>
        <span>&nbsp</span>
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <td>订单类型</td>
                <td>对应价格</td>
                <td>预收</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody id="orderType">
            </tbody>
        </table>
        <span class="span12" id="pageDiv"></span>
    </div>
    <div id="dialog-message" style="width: 600px;display: none">

    </div>
</div>
<script src="resources/js/dialog-min.js"></script>
<script src="resources/js/util.js"></script>

<script type="application/javascript">
    var startNum = 1;
    var limit = 20;
    $(function () {
        getTable();
    });
    function competence(idArr){
        var arr = new Array();
        if(idArr.length>0){

            for(var i=0;i<idArr.length;i++){
                for(var j=0;j<idArr[i].length;j++){

                    if(idArr[i][j].substr(0,4) =="edit"){
                        var obj1 = {};
                        obj1["num"]="07102";
                        obj1["id"]= idArr[i][j];
                        //alert(idArr[i][j]);
                        arr.push(obj1);
                    }else{
                        var obj2 = {};
                        obj2["num"]="07103";
                        obj2["id"]= idArr[i][j];
                        arr.push(obj2);
                    }
                }
            }
        }
        var obj3 = {"num":"07101","id":"add"};
        arr.push(obj3);
        buttonJudge(arr);
    }
    function getTable() {
        $("#orderType").empty();
        $.get('orderType/orderTypeInfo', {}, function (data) {
            if (data.status == "success") {
                var idArr = new Array();
                var editArr = new Array();
                var delArr = new Array();
                $.each(data.data, function (i, r) {
                    editArr.push(("edit"+r.id))
                    delArr.push(("del"+r.id));
                    var str = "";
//                    if (r.receiveType == 1) {
//                        str = "应收";
//                    }
                    if (r.receiveType == 2) {
                        str = "预收"
                    }
                    $("#orderType").append("<tr id='" + r.id + "'><td>" + r.name + "</td>" +
                            "<td>" + r.priceTypeName + "</td>" + //
                            "<td>" + str + "</td><td>" +
                            "<a id='edit"+ r.id+"' href=javascript:toEdit('" + r.id + "','" + r.name + "','" + r.priceType + "','" + r.priceTypeName + "','" + r.receiveType + "','" + str + "')>编辑</a>" + "&nbsp;" +
                            "<a id='del"+ r.id+"' href=javascript:del('" + r.id + "')>删除</a>" +
                            "</td></tr>");
                });
                idArr.push(editArr);
                idArr.push(delArr);
                competence(idArr);
                getPagination(data.count, startNum, limit);
            }else{
                message("error","没有符合条件的数据！");
            }
        }, 'json');
    }

    function toEdit(id, name, priceType, priceTypeName, receiveType, receiveName) {
        type_value = $("#" + id + "").html();
        $("#" + id + "").html("<td><input type='text' id='name" + id + "' value='" + name + "'></td>" +
                        "<td><select id='selType" + id + "'><option value='" + priceType + "'>" + priceTypeName + "</option></select></td>" +
                        "<td><select id='selReceive" + id + "'><option value='" + receiveType + "'>" + receiveName + "</option></select></td>" +
                        "<td><a href=javascript:editSave('" + id + "')>保存</a>" +
                        "<a href=javascript:dev_info('" + id + "')>取消</a>" +
                        "</td>"
        );
        $.get('orderType/dicInfo', function (data) {
            if (data.status == "success") {
                $.each(data.data, function (i, r) {
                    if (r.itemValue != priceType)
                        $("#selType" + id + "").append("<option value='" + r.itemValue + "'>" + r.itemDiscrible + "</option>");
                });
//                if (receiveType == 1) {
//                    $("#selReceive" + id + "").append("<option value='" + 2 + "'>预收</option>");
//                }
//                if (receiveType == 2) {
//                    $("#selReceive" + id + "").append("<option value='" + 1 + "'>应收</option>");
//                }
            }
        }, 'json');
    }

    function editSave(id) {
        var name = $("#name" + id + "").val();
        var type = $("#selType" + id + "").val();
        var recevie = $("#selReceive" + id + "").val();
        $.post('orderType/update', {'id': id, 'name': name, 'priceType': type, 'receiveType': recevie}, function (data) {
            if (data.status == "success") {
                message("success","修改成功！");
                getTable();
            }
        }, 'json');
    }

    function del(id) {
        var confirmDialog = dialog({
            id: "confirmDialog",
            title: "提示",
            content: "确认删除该条记录？",
            okValue: "确定",
            ok: function () {
                $.get('orderType/del', {"id": id}, function (data) {
                    if (data.status == "success") {
                        message("success","删除成功！");
                    }
                }, 'json');
                getTable();
            },
            cancelValue: "取消",
            cancel: function () {
            }
        });
        confirmDialog.show();
    }

    function toAdd() {
        $("#dialog-message").empty();
        $("#dialog-message").append(
                        "<div><label class='control-label'>订单类型：</label><input size='5' type='text' id='name'></div>" +
                        "<div><label class='control-label'>对应价格：</label><select id='selPrice'class='span3 m-wrap'></select></div>" +
                        "<div><label class='control-label'>收款方式：</label><select id='selReceive'class='span3 m-wrap'>" +
//                        "<option value='1'>应收</option>" +
                        "<option value='2'>预收</option>" +
                        "</select></div>"
        );
        $.get('orderType/dicInfo', {}, function (data) {
            if (data.status == "success") {
                $.each(data.data, function (i, r) {
                    $("#selPrice").append("<option value='" + r.itemValue + "'>" + r.itemDiscrible + "</option>");
                });
            }
        }, 'json');
    }

    function add() {
        toAdd();
        var confirmDialog = dialog({
            id: "confirmDialog",
//            width: 500,
            title: "添加订单类型",
            content: $("#dialog-message"),
            okValue: "提交",
            ok: function () {
                var name = $("#name").val();
                var pri = $("#selPrice").val();
                var receive = $("#selReceive").val();
                $.post('orderType/add', {"name": name, "priceType": pri, "receiveType": receive},
                        function (data) {
                            if (data.status == "success") {
                                message("success","添加成功！");
                                 getTable();
                            }
                        }, 'json');

            },
            cancelValue: "取消",
            cancel: function () {
            }
        });
        confirmDialog.showModal();
    }
    function dev_info(ordertypeid) {
        $("#" + ordertypeid + "").html(type_value);
    }
    function gotoPage(activePage, limitNum) {
        startNum = activePage;
        limit = limitNum;
        getTable();
    }
</script>
