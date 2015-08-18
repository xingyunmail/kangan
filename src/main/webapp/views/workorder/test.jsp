<%--
  Created by IntelliJ IDEA.
  User: zzq
  Date: 15-5-20
  Time: 上午10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<link rel="stylesheet" type="text/css" href="resources/js/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="resources/js/jquery-ui.css" />
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="resources/js/jquery.multiselect.js"></script>
<script type="text/javascript">
    $(function(){

        $("#example").multiselect({
            header: false,
            height: 200,
            minWidth: 200,
            selectedList: 10,//预设值最多显示10被选中项
            hide: ["explode", 500],
            noneSelectedText: 'Please select an option',
            close: function(){
                var values= $("#example").val();
                $("#hfexample").val(values);
            }
        });

    });
</script>
<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="muted pull-left"></div>
    </div>
    <div class="block-content collapse in">
        <div class="well well-small  row span12">
            <div class="span3">
                区域：
                <%--<select id="example" name="example" title="区域" multiple="multiple">--%>
                <%--</select>--%>
                <select id="example" name="example" multiple="multiple" style="width:400px">
                    <option value="option1">Option 1</option>
                    <option value="option2">Option 2</option>
                    <option value="option3">Option 3</option>
                    <option value="option4">Option 4</option>
                    <option value="option5">Option 5</option>
                    <option value="option6">Option 6</option>
                    <option value="option7">Option 7</option>
                </select>
                <input type="hidden" id="hfexample" />
            </div>
            <div class="span3">
                状态：
                <select id='selStus' class='span4 m-wrap'></select>
            </div>
            <div class="span3">
                <button class="btn order-btn" onclick="getComplains()">查 询</button>
            </div>
        </div>
        </div>
</div>
