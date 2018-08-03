<#assign "baseUrl"=springMacroRequestContext.contextPath/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>登录</title>
</head>
<body class="layui-layout-admin layui-bg-gray">
<div class="layui-header">
    <div class="layui-logo"><img src="${baseUrl}/LOGO.png" alt="" style="width: 118px"
                                 class="layui-anim layui-anim-scaleSpring"></div>
</div>
<div class="layui-container" style="padding-top: 10%">
    <div class="layui-card layui-col-md6 layui-col-sm12 layui-col-xs12 layui-col-md-offset3">
        <div class="layui-card-header">登录</div>
        <div class="layui-card-body">
            <form class="layui-form">
                <div class="layui-form-item">
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-block" style="width: 70%">
                        <input type="text" name="username" required lay-verify="required" placeholder="请输入用户名"
                               autocomplete="off" class="layui-input" value="wordless">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-block" style="width: 70%">
                        <input type="password" name="password" required lay-verify="required" placeholder="请输入密码"
                               autocomplete="off" class="layui-input" value="XiaoMingTongXue@123">
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn" lay-submit lay-filter="login"><i
                                class="layui-icon layui-icon-engine"></i>立即登录
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="layui-footer layui-bg-black" align="center" style="left:0">
    <!-- 底部固定区域 -->
    © layui.com - 不语文化
</div>
</body>
<#include "common/webImport.ftl">
<script>
    var form = layui.form;
    var $ = layui.$;

    form.on("submit(login)", function (form) {
        $.post("${baseUrl}/sublogin", form.field, function (resp) {
            console.log(resp);
            if (resp.code == 0) {
                window.location.href ="${baseUrl}";
            } else {
                layer.alert(resp.msg);
            }
        })
        return false;
    })

</script>
</html>