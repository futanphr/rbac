<link rel="stylesheet" href="${baseUrl}/css/layui.css">
<#--滚动条样式-->
<style>
    ::-webkit-scrollbar {
        width: 10px;
        height: 10px;
    }

    ::-webkit-scrollbar-button:vertical {
        display: none;
    }

    ::-webkit-scrollbar-track:vertical {
        background-color: black;
    }

    ::-webkit-scrollbar-track-piece {
        background-color: #ffffff;
    }

    ::-webkit-scrollbar-thumb {
        margin-right: 10px;
        background-color: #0080ba;
        border-radius: 10px;
    }

    ::-webkit-scrollbar-thumb:hover {
        margin-right: 10px;
        background-color: #00c1d2;
        border-radius: 10px;
    }

    ::-webkit-scrollbar-corner {
        background-color: #00c1d2;
        border-radius: 10px
    }

    ::-webkit-scrollbar-resizer {
        background-color: #0096ff;
    }
</style>
<script src="${baseUrl}/js/global.js"></script>
<script src="${baseUrl}/layui.all.js"></script>
<#--全局通用的弹窗-->
<script>
    var $ = layui.$;

    function showEditInfoDialog() {
        layer.open({
            title: "修改个人信息"
            , area: ["480px", "300px"]
            , content: $("#editInfoForm").html()
            , btn: false
        });
        layui.form.on("submit(updateUserInfo)", function (form) {
            $.post("${baseUrl}/user/userInfo/update", form.field, function (res) {
                if (res.code == 0) {
                    layer.closeAll();
                    layer.msg("修改成功");
                } else {
                    layer.alert(res.msg);
                }
            })
            return false;
        })
    }

    function showResetPwdDialog() {
        layer.open({
            title: "修改密码"
            , area: ["480px", "300px"]
            , content: $("#resetPwdForm").html()
            , btn: false
        });
        layui.form.on("submit(resetPassword)", function (form) {
            var data = form.field;
            if (data.newPassword != data.confirm) {
                layer.msg("两次输入不一样", {icon: 5});
            } else {
                $.post("${baseUrl}/user/password/reset", data, function (res) {
                    if (res.code == 0) {
                        layer.closeAll();
                        layer.msg("修改成功");
                    } else {
                        layer.alert(res.msg);
                    }
                });
            }
            return false;
        })
    }
</script>