<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>角色管理</title>
</head>
<body class="layui-layout-body">
<#include "../common/webHeader.ftl">
<div class="layui-body">
    <div class="layui-card">
        <div class="layui-card-header">
            <div class="layui-breadcrumb">
                <a href="${baseUrl}">主页</a>
                <a href="${currentUrl}">角色管理</a>
            </div>
        </div>
        <div class="layui-card-body">
            <form class="layui-form" lay-filter="list">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">关键字</label>
                        <div class="layui-input-inline">
                            <input name="keyword" type="text" class="layui-input" placeholder="请输入角色名或角色编码">
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
            <script type="text/html" id="toolBar">
                <a class="layui-btn layui-btn-primary layui-btn-sm" lay-event="assignPermission">分配权限</a>
                <a class="layui-btn layui-btn-sm" lay-event="edit">编辑</a>
                <a class="layui-btn layui-btn-danger layui-btn-sm" lay-event="del">删除</a>
            </script>
        <#--表格按钮-->
            <ul class="layui-fixbar">
                <li data-event="add" class="layui-icon layui-icon-add-1 layui-bg-blue" style="display: none"></li>
                <li class="layui-icon layui-icon-up layui-bg-cyan fixbar" id="tableBar"></li>
            </ul>
        <#--新增或编辑操作框-->
            <div id="modal" style="display: none">
            <#include "edit.ftl">
            </div>
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
        $(".layui-fixbar").hover(function () {
            //展开样式
            var up = "layui-icon-up";
            //收起样式
            var down = "layui-icon-down";
            var btns = $(".fixbar").prevAll();
            if ($(".fixbar").hasClass(up)) {
                $(".fixbar").removeClass(up);
                $(".fixbar").addClass(down);
                btns.fadeIn();
            } else {
                $(".fixbar").removeClass(down);
                $(".fixbar").addClass(up);
                btns.hide();
            }
        });

        //工具按钮监听
        $(".layui-fixbar").children().click(function () {
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

    });

    //高级查询
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
        , size: "lg"
        , cellMinWidth: 200
        , method: "post"
        , cols: [[ //表头
            {type: "numbers", fixed: 'left'}
            , {field: 'roleName', title: '角色名'}
            , {field: 'roleSn', title: '角色编码'}
            , {field: 'createTime', title: '创建时间'}
            , {field: 'modifyTime', title: '修改时间'}
            , {title: '操作', align: "center", toolbar: "#toolBar", fixed: "right"}
        ]]
        , done: function (res, curr, count) {
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            if (res.code != 0) {
                layer.alert(res.msg, {icon: 5});
            }
            layui.form.render();
        }
    })

    table.on("tool(table)", function (obj) {
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if (layEvent === 'assignPermission') { //查看
            //todo
            layer.open({
                title: "分配权限"
                , type: 1
                , content: "<table id='permTable' lay-filter='permTable'></table>"
                , area: ["70%", "70%"]
                , btn: ["保存", "关闭"]
                , closeBtn: false
                , btnAlign: "c"
                , yes: function () {
                    var checkStatus = table.checkStatus("permTable")
                    var exprs = new Array();
                    var permList = checkStatus.data;
                    for (index in permList) {
                        exprs.push(permList[index].expr);
                    }
                    $.post("permission/assign", {
                        roleSn: data.roleSn
                        , exprs: JSON.stringify(exprs)
                    }, function (resp) {
                        if (resp.code==0) {
                            layer.msg("角色-权限保存成功", {icon: 6});
                            layer.closeAll();
                        }else{
                            layer.msg(resp.msg, {icon: 5});
                        }
                    })
                }
            })
            //渲染权限表格
            table.render({
                elem: "#permTable"
                , id: "permTable"
                , url: "permission/all"
                , method: "post"
                , limit: 65535
                , skin: "line"
                , scrollbar: false
                , cols: [[
                    {type: "checkbox"}
                    , {field: 'name', title: '权限名'}
                    , {field: 'expr', title: '权限表达式'}
                ]]
                ,where:{
                    roleSn:data.roleSn
                }
            });
        } else if (layEvent === 'del') { //删除
            layer.confirm('真的删除行么', function (index) {
                $.post("delete", {id: obj.data.id}, function (resp) {
                    if (resp.code == 0) {
                        layer.close(index);
                        obj.del();
                        layer.msg("删除成功", {icon: 1});
                    }
                })
            });
        } else if (layEvent === 'edit') { //编辑
            //编辑
            layer.open({
                title: "编辑"
                , type: 1
                , content: $("#modal").html()
                , area: ["600px", "70%"]
                , success: function (index, layero) {
                    form.val('editForm', obj.data);
                }
            });

        }
    });
</script>
</html>
