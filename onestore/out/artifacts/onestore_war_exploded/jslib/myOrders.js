var uid;

$(document).ready(function () {
    //得到uid
    uid = $("input:first").val();


    //让所有的订单详情隐藏
    $(".order-detail").hide();

    //为待支付链接绑定事件
    $(document).on("click",".state>a",function () {

        //先做一个点击确认
        var flag = window.confirm("确定要进行支付吗？");
        if(!flag){
            return false;
        }

        //得到订单号、总金额、用户    此后扩展向第三方支付平台提交数据
        var thisA = $(this);
        var thisOrderItem = $(this).parent().parent().parent();//得到<div class="othersinfo">
        //得到金额
        var price = parseFloat(thisOrderItem.find("#thisPrice").html().substring(3));
        var ordersId = thisOrderItem.find(".othersinfo>span").eq(1).html().substring(4);
        //uid已经存在
        
        //这里就不发送请求支付，直接点击支付后把paystate 设置成1，然后更新此节点内容为 已完成
        $.ajax({
            url:"/orders",
            type:"POST",
            data:{
                "method":"payOrdersByUidAndOrdersid",
                "uid":uid,
                "ordermoney":price,
                "oid":ordersId
            },
            success:function (data) {
                if(data !=null && data != ""){
                    alert("支付成功！");
                    thisA.parent().html("已完成");
                }else {
                    alert("支付失败 请稍后再试");
                }
            },
            error:function () {
                alert("支付失败 请稍后再试");
            }
        })
        return false;
    });



    //对删除订单链接的处理
    $(".order-operate>a:first-child").click(function () {
        //先做一个点击确认
        var flag = window.confirm("确定要删除订单吗？");
        if(!flag){
            return false;
        }

        //需要得到uid、oid
        var thisOrderItem = $(this).parent().parent().parent();//得到<div class="othersinfo">
        var ordersId = thisOrderItem.find(".othersinfo>span").eq(1).html().substring(4);
        $.ajax({
            url:"/orders",
            type:"POST",
            data:{
                "method":"deleteUserOrdersByOid",
                "uid":uid,
                "oid":ordersId
            },
            success:function (data) {
                if(data !=null && data != ""){
                    alert("删除订单成功");
                    thisOrderItem.remove();
                }else {
                    alert("删除订单失败 请稍后再试");
                }
            },
            error:function () {
                alert("删除订单失败 请稍后再试");
            }
        })


        return false;
    });




    var show = 0;//标志是否显示
    //为订单详情a标签绑定事件
    $(".order-operate>a:last-child").click(function () {
        //判断是否隐藏，隐藏则显现，显现则隐藏
        if(show == 0){
            $(this).parent().parent().parent().find(".order-detail").fadeIn();
            show = 1;
        }else {
            $(this).parent().parent().parent().find(".order-detail").fadeOut();
            show = 0;
        }

        return false;
    });


})