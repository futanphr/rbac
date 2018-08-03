<form class="layui-form" style="padding-top: 25px" lay-filter="editForm">
    <div class="layui-form-item">
        <label class="layui-form-label">角色名</label>
        <div class="layui-input-inline">
            <input type="hidden" name="id" value="">
            <input type="text" name="roleName" required lay-verify="required" placeholder="请输入角色名" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">角色编码</label>
        <div class="layui-input-inline">
            <input type="text" name="roleSn" required lay-verify="required" placeholder="请输入角色编码" autocomplete="off" class="layui-input">
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
</script>