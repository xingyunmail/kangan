<%--
  Created by IntelliJ IDEA.
  User: yu
  Date: 15-5-22
  Time: 下午4:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="muted pull-left">权限列表</div>
    </div>
    <div class="block-content collapse in">
        <form class="form-horizontal">
            <div class="span12">
                <div class="well well-small">
                    <div class="control-group">
                        <div class="span6">
                            <label class="control-label" for="competenceId">权限ID:</label>
                            <div class="controls">
                                <input type="text" class="span5" id="competenceId">
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="span6">
                            <label class="control-label" for="competenceName">权限名称:</label>
                            <div class="controls">
                                <select class="span5" id="competenceName">
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="span6">
                            <label class="control-label">加入权限组:</label>
                            <div class="controls" id="grouplist">
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="span6">
                            <div class="span4"></div>
                            <a type="button" class="btn btn-primary" href="javascript:void(0);"
                               onclick="submitData();">提交</a>
                        </div>
                    </div>
                </div>
                <div id="div_discount">
                    <table class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <td>权限ID</td>
                            <td>权限名称</td>
                            <td>所在权限组</td>
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
</div>
<script src="resources/js/util.js"></script>
<script type="application/javascript">

    var startNum = 1;
    var limit = 20;

    $(document).ready(function(){
        grouplist();
        getCompetenceList();
        buttonList();
    });

    function grouplist(){
        $.getJSON("competence/grouplist",function(data){
            if(data.status == "success"){
                $.each(data.data,function(index,item){
                    $("#grouplist").append(
                        '<span class=""><input class="uniform_on" type="checkbox" name="groupinfolist" value="'+item.id+'">'+item.name+'</span>&nbsp;&nbsp;'
                    );
                });
            }
        });
    }

    function buttonList(){
        $("#competenceId").bind("input propertychange", function() {

            var competenceId = $("#competenceId").val();
            $.getJSON("competence/button",{"competenceId":competenceId},function(data){
                if(data.status == "success"){
                    $("#competenceName").empty();
                    $.each(data.data,function(index,item){
                        $("#competenceName").append(
                            '<option value="'+item.id+'">'+ item.name +'</option>'
                        );
                    });
                }
            });

        });
    }

    function getCompetenceList(){

        $.getJSON("competence/infogrouplist",{"startNum":startNum,"limit":limit},function(data){

            if(data.status == "success"){
                $("#tb_discount").empty();
                $.each(data.data,function(index,item){
                    $("#tb_discount").append(
                        "<tr id='tr_"+ item.id +"'>" +
                        "<td>" + item.competenceId + "</td>" +
                        "<td>" + item.name + "</td>" +
                        "<td>" + item.groupName + "</td>" +
                        "</tr>"
                    );
                });
                getPagination(data.count, startNum, limit);
            }
        });
    }

    function submitData(){

        var buttonId = $("#competenceName").val();
        var groupArray = new Array();

        $('input[name="groupinfolist"]:checked').each(function(){
            groupArray.push($(this).val());
        });

        $.post("competence/addcompetence",{"buttonId":buttonId,"groupArray":groupArray.toString()},function(data){
            if(data.status == "success"){
                alert("操作成功!");
                getCompetenceList();
            }else{
                alert("操作失败!");
            }
        });
    }

    function gotoPage(activePage, limitNum) {
        startNum = activePage;
        limit = limitNum;
        getCompetenceList();
    }

</script>
