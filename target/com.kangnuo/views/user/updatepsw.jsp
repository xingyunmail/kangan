<%--
  Created by IntelliJ IDEA.
  User: yu
  Date: 15-6-23
  Time: 上午9:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<div class="block">
    <div class="navbar navbar-inner block-header" >
        <div class="muted pull-left" id="topid">密码修改</div>
    </div>
    <div id="alert">
    </div>
    <div class="block-content collapse in">
        <div class="span12">
            <form class="form-horizontal">
                <fieldset>

                    <div class="control-group">
                        <label class="control-label" for="originpsw">初始密码:</label>
                        <div class="controls">
                            <input class="input-xlarge" id="originpsw" type="password">
                            <span class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="newpsw">新密码:</label>
                        <div class="controls">
                            <input class="input-xlarge" id="newpsw" type="password">
                            <span class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="renewpsw">重复密码:</label>
                        <div class="controls">
                            <input class="input-xlarge" id="renewpsw" type="password">
                            <span class="help-inline"></span>
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="button" class="btn btn-primary" id="submit">提交</button>
                        <button type="reset" class="btn" id="reset">重置</button>
                    </div>
                </fieldset>
            </form>

        </div>
    </div>

</div>

<script type="application/javascript">

    $(document).ready(function(){

        $("#submit").click(function(){

            var originpsw = $("#originpsw").val();
            var newpsw = $("#newpsw").val();
            var renewpsw = $("#renewpsw").val();

            if (originpsw == ""){
                $("#originpsw").next().html("原始密码不能为空");
                $("#originpsw").parent().parent().removeClass().addClass("control-group error");
                return;
            }else{
                $("#originpsw").next().html("");
                $("#originpsw").parent().parent().removeClass().addClass("control-group");
            }

            $.ajax({
                url:"user/checkpsw",
                type:"POST",
                dataType: "json",
                data:{"password":originpsw},
                async:false,
                success:function (data) {
                    if (data.status == "success") {
                        $("#originpsw").next().html("");
                        $("#originpsw").parent().parent().removeClass().addClass("control-group");
                    }else{
                        $("#originpsw").next().html("原始密码错误,请重新输入");
                        $("#originpsw").parent().parent().removeClass().addClass("control-group error");
                        return;
                    }
                }
            });

            if(newpsw == ""){
                $("#newpsw").next().html("新密码不能为空");
                $("#newpsw").parent().parent().removeClass().addClass("control-group error");
                return;
            }else if(newpsw.length < 6 || newpsw.length > 15){
                $("#newpsw").next().html("请输入6-15位新密码");
                $("#newpsw").parent().parent().removeClass().addClass("control-group error");
                return;
            }else if(newpsw != renewpsw){
                $("#newpsw").next().html("");
                $("#newpsw").parent().parent().removeClass().addClass("control-group");
                $("#renewpsw").next().html("重复密码和新密码不同");
                $("#renewpsw").parent().parent().removeClass().addClass("control-group error");
                return;
            }else{
                $("#renewpsw").next().html("");
                $("#renewpsw").parent().parent().removeClass().addClass("control-group");
            }

            $.ajax({
                url:"user/updatepsw",
                type:"POST",
                dataType: "json",
                data:{"password":renewpsw},
                async:false,
                success:function (data) {
                    if (data.status == "success") {
                        message("success","修改成功!");
                        $("#reset").click();
                    }else{
                        message("error","修改失败!");
                        return;
                    }
                }
            });
        });
    });

</script>
