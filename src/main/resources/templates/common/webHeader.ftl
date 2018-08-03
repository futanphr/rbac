<#assign "baseUrl"=request.contextPath/>
<#assign "currentUrl"=request.requestUri/>
<#assign "userInfo"=Session.token_user/>
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo"><a href="${baseUrl}"><img src="${baseUrl}/LOGO.png" alt="" style="width: 118px"
                                                          class="layui-anim layui-anim-scaleSpring"></a></div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <i class="layui-icon layui-icon-user"></i>
                ${(userInfo.username)!}
                </a>
                <dl class="layui-nav-child">
                    <dd><a onclick="showEditInfoDialog()"><i class="layui-icon layui-icon-edit"
                                                             style="font-size: 16px"></i>&nbsp;个人资料</a></dd>
                    <dd><a onclick="showResetPwdDialog()"><i class="layui-icon layui-icon-password"></i>&nbsp;修改密码</a>
                    </dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="${baseUrl}/logout">退了</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree" lay-filter="test">
                <li class="layui-nav-item">
                    <a class="" href="javascript:;">业务管理</a>
                    <dl class="layui-nav-child">
                    <#--<dd><a href="${baseUrl}/blog/">列表一</a></dd>-->
                        <dd><a href="javascript:;">列表一</a></dd>
                        <dd><a href="javascript:;">列表二</a></dd>
                        <dd><a href="javascript:;">列表三</a></dd>
                    </dl>
                </li>
            <#--<#if userInfo.isAdmin>-->
                <li id="system" class="layui-nav-item">
                    <a>系统设置</a>
                    <dl class="layui-nav-child">
                        <dd><a href="${baseUrl}/user/">用户管理</a></dd>
                        <dd><a href="${baseUrl}/role/">角色管理</a></dd>
                        <dd><a href="${baseUrl}/permission/">权限列表</a></dd>
                    </dl>
                </li>
            <#--</#if>-->
            </ul>
        </div>
    </div>
