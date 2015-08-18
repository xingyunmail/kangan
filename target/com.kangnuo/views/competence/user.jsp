<%--
  Created by IntelliJ IDEA.
  User: yu
  Date: 15-5-18
  Time: 下午5:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>


<div class="block">
    <div class="navbar navbar-inner block-header" >
        <div class="muted pull-left" id="topid">用户管理</div>
    </div>
    <div id="alert">
    </div>
    <div class="block-content collapse in">
        <div class="well well-small  row span12">
            <div class="span3">
                <div class="controls">
                    用户名：
                    <input size="5" type="text" id="userName">
                </div>
            </div>
            <div class="span3">
                <div class="controls">
                    权限组：
                    <input size="5" type="text" id="groupName">
                </div>
            </div>
            <div class="span3">
                <button class="btn order-btn" id="user_search">查 询</button>
            </div>
        </div>
        <div class="row span12">
            <button class="btn order-btn" onclick="add()" id="user_add">新增用户</button>
        </div>
        <span>&nbsp</span>
        <div class="row span12">
            <div class="span11">
            <table class="table table-bordered table-striped table-hover">
                <thead>
                <tr>
                    <td>用户名</td>
                    <td>权限组</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody id="userList">
                </tbody>
            </table>
            <span class="span12" id="pageDiv">
            </span>
            </div>
        </div>
    </div>
    <div id="dialog-message" style="width: 600px;display: none;">

    </div>
</div>

<div id="editModal" class="modal hide" style="width: 50%;left: 40%">
    <div class="modal-header">
        <button data-dismiss="modal" class="close" type="button">&times;</button>
        <h3>编辑用户</h3>
    </div>
    <div class="alert" id="remind_edit" style="display: none;">
    </div>
    <div class="modal-body">
        <div class="controls"><label class='control-label'>用户名：</label><input size='5' type='text' id='userNameNew'><span class="help-inline"></span></div>
        <div class="controls"><label class='control-label'>密码：</label><input type='password' id='passwordNew' size='5'><span class="help-inline"></span></div>
        <div class="controls"><label class='control-label'>确认密码：</label><input type='password' id='repasswordNew' size='5'><span class="help-inline"></span></div>
        <div class="controls"><label class='control-label'>权限组：</label> <div class='checkbox-inline' id='groupTypeNew'></div><span class="help-inline"></span></div>

    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" onclick="updateUser()">提交</button>
        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
    <input type="hidden" id="user_id" value="">
</div>

<div id="myModal" class="modal hide" style="width: 50%;left: 40%">
    <div class="modal-header">
        <button data-dismiss="modal" class="close" type="button">&times;</button>
        <h3>新增用户</h3>
    </div>
    <div class="modal-body">
        <div class="controls"><label class='control-label'>用户ID：</label><input size='5' type='text' id='usernameId'><span class="help-inline"></span></div>
        <div class="controls"><label class='control-label'>用户名：</label><input size='5' type='text' id='user_name'><span class="help-inline"></span></div>
        <div class="controls"><label class='control-label'>密码：</label><input type='password' id='password' size='5'><span class="help-inline"></span></div>
        <div class="controls"><label class='control-label'>确认密码：</label><input type='password' id='repassword' size='5'><span class="help-inline"></span></div>
        <div class="controls"><label class='control-label'>权限组：</label> <div class='checkbox-inline' id='groupType'></div><span class="help-inline"></span></div>

    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" onclick="submit()" id="user_submit">提交</button>
        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>

<link href="resources/css/ui-dialog.css" rel="stylesheet" media="screen">
<script src="resources/js/util.js"></script>
<script src="resources/js/dialog-min.js"></script>
<script type="application/javascript">

    var startNum = 1;
    var limit = 20;

    $(document).ready(function(){
        getList();
        competence();

        $("#user_search").click(function(){
            startNum = 1;
            getList();
        });
    });

    function getList(){
        var userName = $.trim($("#userName").val());
        var groupName = $.trim($("#groupName").val());
        $.getJSON("user/list",{"userName":userName,"groupName":groupName,"startNum":startNum,"limit":limit},function(data){

            if(data.status == "success"){
                $("#userList").empty();
                var status = "";
                $.each(data.data,function(index,item){

                    if(item.status == 1){
                        status = '<a href="javascript:operate(0,\''+ item.userId +'\')">禁用</a>&nbsp;<a href="javascript:edit(\''+item.userId+'\')">编辑</a>';
                    }else{
                        status = '<a href="javascript:operate(1,\''+ item.userId +'\')">启用</a>&nbsp;';
                    }

                    $("#userList").append(
                        "<tr id='tr_"+ item.userId +"'>" +
                        "<td>" + item.userName + "</td>" +
                        "<td>" + item.groupName + "</td>" +
                        "<td id='td_"+ item.userId +"'>" + status + "</td>" +
                        "</tr>"
                    );
                });
                getPagination(data.count, startNum, limit);
            }else{
                message("warning","没有记录!");
            }
        });
    }

    function add(){
        $('#myModal').modal({
            backdrop:'static'
        });
        $("#myModal").modal();
        $.getJSON("competence/grouplist",function(data){
            if(data.status == "success"){
                $("#groupType").empty();
                $.each(data.data,function(index,item){
                    $("#groupType").append(
                        "<input type='radio' name='groupradios' id='radio_"+ item.id+"' value='"+ item.id +"' class='radio'> "+ item.name +"</label>&nbsp;&nbsp;"
                    );
                });
            }
        });
    }

    function submit(){

        var usernameId = $("#usernameId").val();
        var user_name = $("#user_name").val();
        var password = $("#password").val();
        var groupId = $('input[name="groupradios"]:checked').val();

        if(!useridCheck()){
            return false;
        }
        if(!unameCheck()){
            return false;
        }
        if(!passwordCheck()){
            return false;
        }
        if(!groupCheck()){
            return false;
        }
        $.post("user/add",{"userName":user_name,"userId":usernameId,"password":password,"groupId":groupId},function(data){
            if(data.status == "success"){
                $("#myModal").modal("hide");
                message("success","添加成功!");
            }else{
                $("#remind").html('添加失败!').show(300).delay(5000).hide(300);
            }
        },"json");
    }

    function operate(status,id){

        $.post("user/updatestatus",{"userId":id,"status":status},function(data){
            if(data.status == "success"){
                message("success","操作成功!");
                if(status == 1){
                    $("#td_"+id).html('<a href="javascript:operate(0,\''+ id +'\')">禁用</a>&nbsp;<a href="javascript:edit(\''+id+'\')">编辑</a>');
                }
                if(status == 0){
                    $("#td_"+id).html('<a href="javascript:operate(1,\''+ id +'\')">启用</a>&nbsp;');
                }
            }else{
                message("error","操作失败!");
            }
        },"json");

    }

    function edit(id){
        $('#editModal').modal({
            backdrop:'static'
        });

        $("#editModal").modal();
        $("#user_id").val(id);

        $.ajax({
            type: "get",
            url: "competence/grouplist",
            data: {},
            async: false,
            dataType: 'json',
            success : function (data) {
                if(data.status == "success"){
                    $("#groupTypeNew").empty();
                    $.each(data.data,function(index,item){
                        $("#groupTypeNew").append(
                            "<input type='radio' name='groupradios1' id='radio_"+ item.id+"' value='"+ item.id +"' class='radio'> "+ item.name +"</label>&nbsp;&nbsp;"
                        );
                    });
                }
            }
        });

        $.getJSON("user/infoid",{"userId":id},function(data){
            if(data.status == "success"){
                $("#userNameNew").val(data.data.userName);
                checkRadio("groupradios1",data.data.groupId);
            }
        });
    }

    function updateUser(){
        var id = $("#user_id").val();
        var userName = $("#userNameNew").val();
        var password = $("#passwordNew").val();
        var repassword = $("#repasswordNew").val();
        var groupId = $('input[name="groupradios1"]:checked').val();

        if(userName == null || userName.length <=0){
            $("#userNameNew").parent().removeClass().addClass("control-group error");
            $("#userNameNew").next().html("用户名不能为空!");
            return false;
        }else{
            $("#userNameNew").parent().removeClass().addClass("controls");
            $("#userNameNew").next().html("");
        }

        if(password == null || password.length <= 0){
            $("#passwordNew").parent().removeClass().addClass("control-group error");
            $("#passwordNew").next().html("密码不能为空!");
            return false;
        }else{
            $("#passwordNew").parent().removeClass().addClass("controls");
            $("#passwordNew").next().html("");
            if(repassword != password){
                $("#repasswordNew").parent().removeClass().addClass("control-group error");
                $("#repasswordNew").next().html("确认密码与密码不相同!");
                return false;
            }else{
                $("#repasswordNew").parent().removeClass().addClass("controls");
                $("#repasswordNew").next().html("");
            }
        }

        $.post("user/updateinfo",{"userName":userName,"password":password,"groupId":groupId,"userId":id},function(data){

            if(data.status == "success"){
                getList();
                $("#editModal").modal("hide");
                message("success","修改成功!");
            }else{
                $("#remind_edit").html('修改失败!').show(300).delay(5000).hide(300);
            }
        },"json");
    }

    function checkRadio(name,value){
        $("input:radio[name="+name+ "]").each(function(){
            if(this.value == value){
                $(this).attr("checked",true);
            }
        });
    }

    function gotoPage(activePage, limitNum) {
        startNum = activePage;
        limit = limitNum;
        getList();
    }

    function useridCheck(){
        var usernameId = $("#usernameId").val();
        if(usernameId == null || usernameId.length == 0){
            $("#usernameId").parent().removeClass().addClass("control-group error");
            $("#usernameId").next().html("用户ID不能为空!");
            return false;
        }else{
            var status = false;
            $.ajax({
                type: "get",
                url: "user/unamecheck",
                data:{"userId":usernameId},
                dataType: 'json',
                async: false,
                success: function (data) {
                    if (data.status == "success") {
                        $("#usernameId").parent().removeClass().addClass("control-group error");
                        $("#usernameId").next().html("该用户ID已注册,请更换!");
                    }else{
                        $("#usernameId").parent().removeClass().addClass("controls");
                        $("#usernameId").next().html("");
                        status = true;
                    }
                }
            });

            return status;
        }
    }

    function unameCheck(){
        var username = $("#user_name").val();
        if(username == null || username.length <=0){
            $("#user_name").parent().removeClass().addClass("control-group error");
            $("#user_name").next().html("用户名不能为空!");
            return false;
        }else{
            $("#user_name").parent().removeClass().addClass("controls");
            $("#user_name").next().html("");
            return true;
        }
    }

    function passwordCheck(){
        var password = $("#password").val();
        var repassword = $("#repassword").val();
        if(password == null || password.length <= 0){
            $("#password").parent().removeClass().addClass("control-group error");
            $("#password").next().html("密码不能为空!");
            return false;
        }else{
            if(repassword != password){
                $("#repassword").parent().removeClass().addClass("control-group error");
                $("#repassword").next().html("确认密码与密码不相同!");
                return false;
            }else{
                $("#repassword").parent().removeClass().addClass("controls");
                $("#repassword").next().html("");
                return true;
            }
        }
    }

    function groupCheck(){
        var groupId = $('input[name="groupradios"]:checked').val();
        if(groupId == undefined){
            $("#groupType").parent().removeClass().addClass("control-group error");
            $("#groupType").next().html("请选择用户所属权限组!");
            return false;
        }else{
            $("#groupType").parent().removeClass().addClass("controls");
            $("#groupType").next().html("");
            return true;
        }
    }


    function competence(){
        var arr = new Array();
        var obj1 = {"num":"08101","id":"user_search"};
        var obj2 = {"num":"08102","id":"user_add"};
        //var obj3 = {"num":"08103","id":"user_submit"};
        arr.push(obj1);
        arr.push(obj2);
        //arr.push(obj3);
        buttonJudge(arr);
    }
</script>
