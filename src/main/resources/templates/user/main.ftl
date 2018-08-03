<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>用户管理</title>
</head>

<body class="layui-layout-body">
<#include "../common/webHeader.ftl">
<div class="layui-body">
    <div class="layui-card">
        <div class="layui-card-header">
            <div class="layui-breadcrumb">
                <a href="${baseUrl}">主页</a>
                <a href="${currentUrl}">用户管理</a>
            </div>
        </div>
        <div class="layui-card-body">
            <form class="layui-form" lay-filter="list">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">用户名</label>
                        <div class="layui-input-inline">
                            <input name="username" type="text" class="layui-input" placeholder="请输入用户名">
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
                <a class="layui-btn layui-btn-primary layui-btn-sm" lay-event="setPassword">设置密码</a>
                <a class="layui-btn layui-btn-warm layui-btn-sm" lay-event="assignRoles">分配角色</a>
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

        //表格工具点击事件
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
        , size: "lg"
        , method: "post"
        , cols: [[ //表头
            {type: "numbers", fixed: 'left'}
            , {field: 'username', title: '用户名'}
            , {field: 'realName', title: '真实姓名'}
            , {field: 'createTime',minWidth:180, title: '创建时间'}
            , {field: 'modifyTime', minWidth:180,title: '修改时间'}
            , {field: 'phone', title: '手机号'}
            , {field: 'lastLoginTime', minWidth:180, title: '最近一次登录时间'}
            , {
                field: 'isBanned', minWidth: 60, event: "ban", fixed: "right", title: '是否启用', templet: function (data) {
                    var checkbox = $("<input>").prop("type", "checkbox")
                            .attr("lay-skin", "switch")
                            .attr("lay-text", "启用|禁用");
                    if (data.isBanned) {
                        checkbox.attr("checked", "");
                    }
                    return $("<div>").html(checkbox[0]).html();
                }
            }
            , {title: '操作', align: "center", toolbar: "#toolBar", fixed: "right", minWidth: 300}
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

    //表格工具条操作
    table.on("tool(table)", function (obj) {
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if (layEvent === 'setPassword') { //查看
            layer.prompt({
                formType: 1,
                value: '',
                title: '请输入新密码',
                maxlength: 16,
                area: ['800px', '350px'] //自定义文本域宽高
            }, function (value, index, elem) {

                $.post("setpwd", {id: obj.data.id, password: value}, function (resp) {
                    if (resp.code == 0) {
                        layer.close(index);
                    } else {
                        layer.msg(resp.msg, {icon: 5});
                    }
                })

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
                , close: function () {
                    $("#reload").click();
                }
            });
            layui.form.render();

        } else if (layEvent === 'ban') {
            $.post("ban", {id: obj.data.id}, function (resp) {
                if (resp.code == 0) {
                    if (resp.data) {
                        layer.msg("启用成功", {icon: 1});
                    } else {
                        layer.msg("已禁用", {icon: 2});
                    }
                    obj.update({
                        isBanned: resp.data
                    })
                }
            });
        } else if (layEvent === "assignRoles") {
            layer.open({
                title: "分配角色"
                , type: 1
                , content: "<table id='roleTable' lay-filter='roleTable'></table>"
                , area: ["70%", "70%"]
                , btn: ["保存", "关闭"]
                , closeBtn: false
                , btnAlign: "c"
                , yes: function () {
                    var checkStatus = table.checkStatus("roleTable")
                    var roleSns = new Array();
                    var roles = checkStatus.data;
                    for (index in roles) {
                        roleSns.push(roles[index].roleSn);
                    }
                    $.post("role/assign", {
                        userId: data.id
                        , roleSns: JSON.stringify(roleSns)
                    }, function (resp) {
                        if (resp.code == 0) {
                            layer.msg("用户-角色保存成功", {icon: 6});
                            layer.closeAll();
                        } else {
                            layer.msg(resp.msg, {icon: 5});
                        }
                    })
                }
            })
            //渲染权限表格
            table.render({
                elem: "#roleTable"
                , id: "roleTable"
                , url: "role/all"
                , method: "post"
                , limit: 65535
                , skin: "line"
                , scrollbar: false
                , cols: [[
                    {type: "checkbox"}
                    , {field: 'roleName', title: '角色名'}
                    , {field: 'roleSn', title: '角色编码'}
                ]]
                , where: {
                    userId: data.id
        }
        })
            ;
        }
    });

</script>
</html>
