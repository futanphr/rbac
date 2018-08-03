<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>主页</title>
</head>
<body class="layui-layout-body layui-bg-gray">
<#include "common/webHeader.ftl">
<div class="layui-body">
    <div class="layui-fluid" style="margin-top: 15px">
        <div class="layui-row layui-col-space15">
        <#--用户操作日志卡片面板-->
            <div class="layui-col-md12">
                <div class="layui-card ">
                    <div class="layui-card-header">用户操作日志</div>
                    <div class="layui-card-body">
                        <ul class="layui-timeline">

                        </ul>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<#include "common/webFooter.ftl">
</body>
<#include "common/webImport.ftl">
<script>
    var $ = layui.$;
    var flow = layui.flow;
//    用户操作日志流加载
    flow.load({
        elem: '.layui-timeline' //流加载容器
//        , isAuto: false
        , scrollElem: '.layui-body' //滚动条所在元素，一般不用填，此处只是演示需要。
        , done: function (page, next) { //执行下一页的回调

            $.post("operation/log/list", {page: page}, function (res) {
                if (res.code == 0) {
                    var lis = [];
                    var data = res.data;
                    var $temp = $("<div>");
                    for (index in data) {
                        var rowData = data[index];
                        var $item = $("<li>").addClass("layui-timeline-item");
                        var $itemIcon = $("<i>").addClass("layui-icon layui-icon-circle layui-timeline-axis");
                        var $content = $("<p>").html(rowData.name);
                        var $title = $("<h3>").addClass("layui-timeline-title").html(rowData.createTime).append("&nbsp;&nbsp;&nbsp;").append($content);
                        var $div = $("<div>")
                                .addClass("layui-timeline-content layui-text")
                                .append($title).append($content);
                        $item.append($itemIcon).append($div);
                        $temp.append($item);

                    }
                    next($temp[0].innerHTML, page < res.totalPages);
                } else {
                    layer.alert(res.msg, {icon: 5});
                }
            })

        }
    });

    $(function () {
        //登录弹窗
        if (${(userInfo.loginJustNow)?c}) {
            layer.open({
                title: "系统提示"
                , time: 3000
                , anim: 4
                , shade: 0
                , skin: "layui-layer-lan"
                , offset: 'rb'
                , icon: 3
                , area: ["320px", "200px"]
                , content: "<h3>上次登录时间为<p>${(userInfo.lastLoginTime)!}</p></h3>"
                , end: function () {
                    $.post("${baseUrl}/login/msg/read");
                }
            })
        }
    })
</script>
</html>
