//校验的正则表达式：
var username;
var password;
var email;

//提交按钮
function postTo() {
    $("input").each(function(){//最后做一次非空判断
        if(this.value == null && this.value == ""){
            return false;
        }
    })
    return true;
}


$(document).ready(function(){

    //为相关参数赋初值
    //校验的正则表达式：
    username = /^[A-Za-z0-9_-]{3,16}$/;//不区分大小写、数字、下划线，^表示以[]中的开始，3至16位
    password = /^[A-Za-z0-9_-]{6,18}$/;//不区分大小写、数字、下划线，^表示以[]中的开始，6至18位
    email = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
    //邮箱验证：以小写字母，数字，下划线，点，中杠，不限字符数 +@+ 点 + 2至6位小写字母组成
    //asdafg@



    //验证表单格式********************************************************************
    $("input").focusout(function () {

        if($(this).attr("type") != "button"){

            //alert("input事件触发成功");
            var text = $(this).val();

            switch ($(this).attr("name")){
                case "username":if(!text.match(username)){
                    $(this).next().html("请输入正确的用户名！");
                }else{
                    $(this).next().html("");
                };
                    break;

                case "password":if(!text.match(password)){
                    $(this).next().html("请输入正确的密码！");
                }else{
                    $(this).next().html("");
                };
                    break;

                case "password1":if(text != $("#password").val()){
                    $(this).next().html("请输入正确的密码！");
                }else{
                    $(this).next().html("");
                };
                    break;

                case "email":if(!text.match(email)){
                    $(this).next().html("请输入正确的email地址！");
                }else{
                    $(this).next().html("");
                };
                    break;

                case "affirmCode":
                    match(text);
                    //验证码输入框失去焦点就需要进行和后台进行比对
                    break;

                case "nickname":if(text == ""){
                    $(this).next().html("请输入昵称！");
                }else{
                    $(this).next().html("");
                };
                    break;
            }
        }

    });



    //更换验证码
    $("#im").parent().click(function () {

        $("img").attr("src","/checkCode?method=getCheckCode&"+ new Date().getTime());
        //每次请求之后要获取session中的验证码值，这里使用ajax来获取，
        // jsp页面是页面第一次被编译之后的值，不能动态获取，只能同通过动态请求，或者在后台进行验证码的对比
        //这里在服务器端进行验证码的对比，减少客户端于服务器的交互次数
    })


});



//校验验证码
function match(text) {
    $.get("/checkCode?method=check&inputCode="+text,function (data) {
        if(data == "" || data == null){
            $("#imgspan").html("校验失败 刷新再试");
        }

        if(data == "1"){
            $("#imgspan").html("");
        }else if(data == "0"){
            $("#imgspan").html("验证码错误！");
            $("img").attr("src","/checkCode?method=getCheckCode&"+ new Date().getTime());
        }

    });
}