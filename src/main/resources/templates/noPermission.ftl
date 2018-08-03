<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>无权限</title>
</head>

<body class="layui-layout-body">
<#include "common/webHeader.ftl">
<div class="layui-body">
    <div class="layui-card">
        <div class="layui-card-header">
            <div class="layui-breadcrumb">
                <a href="${baseUrl}">主页</a>
                <a href="${currentUrl}">无权限</a>
            </div>
        </div>
    </div>
    <img class="layui-anim layui-anim-scale"
         src="${baseUrl}/images/NO_PERMISSION_0.PNG" alt="" width="35%"
         style="position: absolute;bottom: 0px;right: 0px">
</div>
<#include "common/webFooter.ftl">
</body>
<#include "common/webImport.ftl">
<script>
    layer.msg("无权限访问,如有需要请联系管理员", {icon: 2});
</script>
</html>
