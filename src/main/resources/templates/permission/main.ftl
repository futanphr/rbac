<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>权限列表</title>
</head>
<body class="layui-layout-body">
<#include "../common/webHeader.ftl">
<div class="layui-body">
    <div class="layui-card">
        <div class="layui-card-header">
            <div class="layui-breadcrumb">
                <a href="${baseUrl}">主页</a>
                <a href="${currentUrl}">权限列表</a>
            </div>
        </div>
        <div class="layui-card-body">
            <form class="layui-form" lay-filter="list">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">关键字</label>
                        <div class="layui-input-inline">
                            <input name="keyword" type="text" class="layui-input" placeholder="请输入权限名称">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button id="reload" class="layui-btn" lay-submit lay-filter="*"><i
                                class="layui-icon layui-icon-search"></i>查询
                        </button>
                        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                    </div>
                </div>
            </form>
        </div>
        <div class="layui-card-body">
        <#--表格-->
            <table id="table" lay-filter="table"></table>
        <#--工具条-->
            <#--<script type="text/html" id="toolBar">
                <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="setPassword">设置密码</a>
                <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
            </script>-->
        <#--表格按钮-->
            <#--<ul class="layui-fixbar">
                <li data-event="add" class="layui-icon layui-icon-add-1 layui-bg-blue" style="display: none"></li>
                <li class="layui-icon layui-icon-up layui-bg-cyan" id="tableBar"></li>
            </ul>-->
        <#--新增或编辑操作框-->
            <#--<div id="modal" style="display: none">
            <#include "edit.ftl">
            </div>-->
        </div>
    </div>
</div>
<#include "../common/webFooter.ftl">
</body>
<#include "../common/webImport.ftl">
<script>
    var table = layui.table;
    var $ = layui.$;
    var table = layui.table;
    var form = layui.form;
    $(function () {
        //展示面板
        $("#system").addClass("layui-nav-itemed");
        $("[href='${currentUrl}']").addClass("layui-this");
        //表格工具条
        /*$("#tableBar").click(function () {
            //展开样式
            var up = "layui-icon-up";
            //收起样式
            var down = "layui-icon-down";
            var btns = $(this).prevAll();
            if ($(this).hasClass(up)) {
                $(this).removeClass(up);
                $(this).addClass(down);
                btns.fadeIn();
            } else {
                $(this).removeClass(down);
                $(this).addClass(up);
                btns.hide();
            }
        });*/

        //表格工具点击事件
        /*$(".layui-fixbar").children().click(function () {
            var e = $(this).data("event");
            if (e == "add") {
                layer.open({
                    title: "新增"
                    , type: 1
                    , content: $("#modal").html()
                    , area: ["600px", "70%"]
                })
                layui.form.render();
            }
        })
*/
    });

    //查询事件
    form.on("submit(*)", function (d) {
        table.reload('list', {
            url: "list"
            , where: d.field
            , page: {
                curr: 1
            }
        });
        return false;
    });

    //表格初始化
    table.render({
        elem: '#table'
        , id: 'list'
        , url: 'list' //数据接口
        , page: true //开启分页
        , size:"lg"
        , method: "post"
        , cols: [[ //表头
            {type: "numbers", fixed: 'left',title:"序号"}
            , {field: 'name', title: '权限名',align:"center"}
            , {field: 'expr', title: '权限表达式',align:"center"}
        ]]
        , done: function (res, curr, count) {
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            if (res.code != 0) {
                layer.alert(res.msg);
            }
            layui.form.render();
        }
    })

</script>
</html>
