<form class="layui-form" style="padding-top: 25px" lay-filter="editForm">
    <div class="layui-form-item">
        <label class="layui-form-label">用户名</label>
        <div class="layui-input-inline">
            <input type="hidden" name="id" value="">
            <input type="text" name="username" required lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">真实姓名</label>
        <div class="layui-input-inline">
            <input type="text" name="realName" required lay-verify="required" placeholder="请输入真实姓名" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">手机号</label>
        <div class="layui-input-inline">
            <input type="text" name="phone" required lay-verify="required|phone" placeholder="请输入手机" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">是否启用</label>
        <div class="layui-input-block">
            <input lay-filter="isBanned" type="checkbox" name="isBanned" lay-skin="switch" lay-text="启用|禁用">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="edit">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置清除</button>
        </div>
    </div>
</form>
<script>
    parent.form.on("submit(edit)",function (obj) {
        $.post("save",obj.field,function (resp) {
            if(resp.code==0){
                layer.closeAll();
                layer.msg("操作成功");
                parent.$("#reload").click();
            }else{
                layer.msg(resp.msg,{icon:5});
            }
        });
        return false;
    })
    parent.form.on("switch(isBanned)",function(data){
        var ban = $(data.elem).prop("checked");
        $("[name='isBanned']").val(ban);
    })
</script>