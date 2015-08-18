<%--
  Created by IntelliJ IDEA.
  User: yu
  Date: 15-5-22
  Time: 上午9:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="resources/css/ui-dialog.css" rel="stylesheet" media="screen">
<div class="block">
    <div class="navbar navbar-inner block-header" id="topid">
        <div class="muted pull-left">权限组列表</div>
    </div>
    <div id="alert">
    </div>
    <div class="block-content collapse in">
        <form class="form-horizontal">
            <div class="span12">
                <div class="well well-small">
                    <div class="control-group">
                        <div class="span6">
                            <label class="control-label" for="groupName">权限组名称:</label>
                            <div class="controls">
                                <input type="text" class="input-medium" id="groupName">
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="span12">
                            <label class="control-label">权限:</label>
                            <div class="controls" >
                                <ul class="the-icons clearfix span12" id="complist">

                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="span6">
                            <div class="span4"></div>
                                <a type="button" class="btn btn-primary" href="javascript:void(0);"
                                   onclick="submitData();" id="group_add">提交</a>
                        </div>
                    </div>
                </div>
                <div id="div_discount">
                    <table class="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <td class="span2">权限组</td>
                                <td class="span4">所含权限</td>
                                <td class="span4">组内用户</td>
                                <td class="span2">操作</td>
                            </tr>
                        </thead>
                        <tbody id="tb_discount">

                        </tbody>
                    </table>
                    <span class="span12" id="pageDiv">
                    </span>
                </div>
            </div>
        </form>
    </div>
    <div id="dialog-message" style="width: 800px;display: none">
    </div>
</div>

<div id="myModal" class="modal hide" style="width: 60%;left: 40%">
    <div class="modal-header">
        <button data-dismiss="modal" class="close" type="button">&times;</button>
        <h3>编辑所含权限</h3>
    </div>
    <div class="alert" id="remind" style="display: none;">
    </div>
    <div class="modal-body">
        <div class="controls"><label class='control-label'>权限组： <span id="group_name"></span></label></div>
        <div class="controls"><label class='control-label'>权限列表：</label> <ul class='the-icons clearfix ' id='competencelist'></ul><span class="help-inline"></span></div>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" onclick="submitUpdate()" id="group_submit">提交</button>
        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
    <input type="hidden" id="editGroupId" value="">
</div>
<script src="resources/js/util.js"></script>
<script src="resources/js/dialog-min.js"></script>
<script type="application/javascript">

    var startNum = 1;
    var limit = 20;

    $(document).ready(function(){
        complist();
        getgrouplist();
        competence();
    });

    function complist(){
        $.getJSON("competence/infolist",function(data){
            if(data.status == "success"){
                $("#Terminal").empty();
                $.each(data.data,function(index,item){
                    $("#complist").append(
                        '<li ><label for="competencelist_'+item.competenceId+'"><input class="uniform_on" type="checkbox" name="compinfolist" id="competencelist_'+ item.competenceId +'" value="'+item.id+'">'+item.competenceId + "--" +item.name+'</label></li>'
                    );
                });
            }
        });
    }

    function getgrouplist(){

        $.getJSON("competence/groupinfolist",{"startNum":startNum,"limit":limit},function(data){
            if(data.status == "success"){
                $("#tb_discount").empty();
                $.each(data.data,function(index,item){
                    var user = "";
                    var competence = "";

                    $.each(item.competenceInfoModels,function(i,j){
                        user = user + j.name + ",";
                    });

                    $.each(item.userModels,function(m,n){
                        competence = competence + n.userName + ",";
                    });

                    $("#tb_discount").append(
                        "<tr id='tr_"+ item.id +"'>" +
                        "<td>" + item.name + "</td>" +
                        "<td class='span4'>" + user.substr(0,user.length - 1) + "</td>" +
                        "<td>" + competence.substr(0,competence.length - 1) + "</td>" +
                        "<td id='td_"+ "" +"'>"+ "<a href='javascript:editCompetence(\""+ item.name+"\","+ item.id +")'>编辑所含权限</a>" +"</td>" +
                        "</tr>"
                    );
                });
                getPagination(data.count, startNum, limit);
            }
        });
    }

    function editCompetence(name,id){
        $('#myModal').modal({
            backdrop:'static'
        });
        $("#myModal").modal();
        $("#editGroupId").val(id);
        $("#group_name").html(name);
        $.ajax({
            type: "get",
            url: "competence/getinfobyid",
            data: {"groupId":id},
            async: false,
            dataType: 'json',
            success : function (data) {
                if(data.status == "success"){
                    var idArray = new Array();
                    $.each(data.data,function(index,item){
                        idArray.push(item.infoId);
                    });

                    $.getJSON("competence/infolist",function(data){
                        if(data.status == "success"){
                            $("#competencelist").empty();
                            $.each(data.data,function(index,item){

                                if($.inArray(item.id, idArray) >= 0){
                                    $("#competencelist").append(
                                        '<li ><label for="competence_'+item.competenceId+'"><input class="uniform_on" type="checkbox" checked= "checked" name="competence_list" id="competence_'+ item.competenceId +'" value="'+item.id+'">'+item.competenceId + "--" +item.name+'</label></li>'
                                    );
                                }else{
                                    $("#competencelist").append(
                                        '<li ><label for="competence_'+item.competenceId+'"><input class="uniform_on" type="checkbox" name="competence_list" id="competence_'+ item.competenceId +'" value="'+item.id+'">'+item.competenceId + "--" +item.name+'</label></li>&nbsp;'
                                    );
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    function submitData(){
        var name = $("#groupName").val();
        var infoArray = new Array();
        $('input[name="compinfolist"]:checked').each(function(){
            infoArray.push($(this).val());
        });

        if(name == null || name.length <= 0){
            message("warning","权限组名称不能为空!");
            return false;
        }
        if(infoArray.length <= 0){
            message("warning","请选择对应权限!");
            return false;
        }

        if(infoArray.length > 0){
            $.post("competence/addgroup",{"name":name,"infoIdArray":infoArray.toString()},function(data){
                if(data.status == "success"){
                    message("success","添加成功!");
                    getgrouplist();
                }else{
                    message("error","添加失败!");
                }
            },"json");
        }else{
            message("warning","请选择权限!");
        }
    }

    function submitUpdate(){
        var id = $("#editGroupId").val();
        var infoArray = new Array();
        $('input[name="competence_list"]:checked').each(function(){
            infoArray.push($(this).val());
        });

        if(infoArray.length > 0){
            $("#competencelist").parent().removeClass().addClass("controls");
            $("#competencelist").next().html("");
            $.post("competence/updategroup",{"groupId":id,"infoIdArray":infoArray.toString()},function(data){
                if(data.status == "success"){
                    message("success","修改成功!");
                    $("#myModal").modal("hide");
                    getgrouplist();
                }else{
                    $("#remind").html('修改失败!').show(300).delay(5000).hide(300);
                }
            },"json");
        }else{
            $("#competencelist").parent().removeClass().addClass("control-group error");
            $("#competencelist").next().html("请选择权限!");
        }
    }

    function gotoPage(activePage, limitNum) {
        startNum = activePage;
        limit = limitNum;
        getgrouplist();
    }

    function competence(){
        var arr = new Array();
        var obj1 = {"num":"08201","id":"group_add"};
        var obj2 = {"num":"08202","id":"group_submit"};
        arr.push(obj1);
        arr.push(obj2);
        buttonJudge(arr);
    }

</script>
