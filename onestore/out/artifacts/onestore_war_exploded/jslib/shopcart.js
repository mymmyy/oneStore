var pid;//获取pid的值
var uid;//获取uid的值
var shopid;//获取该商品的shopid
var totalPnum;//获得库存总量

$(document).ready(function () {

    var allHiddenInput = $("input[type = 'hidden']");
    pid = allHiddenInput.eq(0).val();
    uid = allHiddenInput.eq(1).val();
    shopid = allHiddenInput.eq(2).val();
    totalPnum = allHiddenInput.eq(3).val();

    //对增加减少按钮的点击事件触发：
    $("button").click(function () {

        var thisHtml = $(this).html();//得到是 +  还是 -
        var numInput = $(".buy-button-num input");
        var numInputValue = parseInt(numInput.val());
        if(thisHtml == "+"){
            if(numInputValue > totalPnum){
                alert("没有更多库存");
                numInput.val(1);
                return false;
            }
            numInput.val( (numInputValue + 1) );
        }else if(thisHtml == "-"){
            if(numInputValue <= 1){
                alert("不能低于1件");
                return false;
            }
            numInput.val( (numInputValue - 1) );
        }

    });



    //对商品数量的input进行添加发生改变事件
    $(".buy-button-num input").change(function () {
        var numInputValue = parseInt($(this).val());
        if(numInputValue <=0 ){
            alert("不能低于1件");
            $(this).val(1);//把值设置成11
            return false;
        }
        if(numInputValue > totalPnum){
            alert("没有更多库存");
            return false;
        }
    });



    //对加入购物车超链接的事件响应
    $(".buy-button-addcart a").click(function () {

        //得到数量
        var numInput = $(".buy-button-num input");
        if(parseInt(numInput.val()) > totalPnum || parseInt(numInput.val()) <= 0){
            alert("当前数量不正确");
            return false;
        }

        var thisUrl = "/shopcart?method=addShopcart&uid="+uid+"&pid="+pid+"&count="+parseInt(numInput.val());
        $.ajax({
            url:thisUrl,
            success:function () {
               //成功了就提示
                alert("添加成功！");
            },
            error:function () {
                //出了错就提示添加出错
                alert("添加出错 稍后再试");
            }
        })
    })



});