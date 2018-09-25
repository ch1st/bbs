//搜素框处理
$("#Search").click(function(e) {
    layer.open({
        title: '',
        type: 1,
        closeBtn: 0,
        offset: 'auto',
        area: ['550px', '78px'],
        content: '<div class="search"><form action="#"><input type="text" name="title" required lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input"/></form></div>'
    });
});

//登录框处理
$('.subLogin').click(function() {
    layer.open({
        title: "",
        type: 2,
        closeBtn: 1,
        offset: 'auto',
        area: ['400px', '400px'],
        content: 'login.html',
        scrolling: 'no'

    });

});

/*
   注册框处理
*/
$('#subReg').click(function() {
    layer.open({
        title: "",
        type: 2,
        closeBtn: 1,
        offset: 'auto',
        area: ['400px', '400px'],
        content: 'register.html',
        scrolling: 'no'

    });
});

/*
* 获取验证码
* */

$('#code').click(function(){
    $.ajax({
        url:'/getImage?'+Date.parse(new Date()),
        type:'get',
        success:function(data){
            $('#code').attr('src',this.url);
        }
    });
})