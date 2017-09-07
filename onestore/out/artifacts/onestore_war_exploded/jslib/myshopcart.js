var uid;


var totalPrice=0;
$(document).ready(function () {
    uid  = parseInt($("input[type='hidden']").val());


    //首先把总价计算出
    getTotalPrice();
    $(".allprice span").html(totalPrice);


    //当check box有操作时执行
    $("input[type='checkbox']").change(function () {
        getTotalPrice();
        $(".allprice span").html(totalPrice);
    })
    
    
    //删除超链接的点击事件
    $(".my-li>a").click(function () {
        //获取父节点
        var thisLi = $(this).parent("li");

        //从而得到第一个input的值即是当前商品的pid
        var thisPidValue = thisLi.children("input[type='hidden']").val();
       
        //删除根据当前的pid 和 shopcartid（根据用户id即可）
        $.ajax({
           url:"/shopcart?method=deleteProductByUidAndPid&uid="+uid+"&pid="+thisPidValue,
            success:function () {
                alert("删除商品成功");
                //删除成功就把该节点删除
                thisLi.remove();

            },
            error:function (e) {
                alert(e+" 删除失败");
            }
        });
        return false;

    });




    //提交按钮的点击事件
    $(".allprice button").click(function () {
        //var url = "/orders?method=createOrdersBy"
        //先遍历所有checkbox，若没有则提示选择商品，若有则进行订单生成
        var allChecked =$("input:checkbox:checked");
        if(allChecked.length <= 0){
            alert("请选择需要购买的商品");
        }else {

            //获得ul
            var orderUl = $(".order-items").find("ul");
            orderUl.html("");//清空

            //一个一个赋值
            allChecked.each(function (j) {
                //获得相关值：pname、imgurl、price、count
                var thisLi = $(this).parent().parent();

                var pname = thisLi.children(".my-pname").html();
                var imgurl = thisLi.children(".my-img").find("img").attr("src");
                var price = parseFloat(thisLi.children(".my-price").find("span").html());
                var count = parseInt(thisLi.children(".edit").find("input").val());


                var orderLi = $("<li></li>").appendTo(orderUl);

                $("<input type='hidden' name='pid' value='"+parseInt(thisLi.children("input").eq(0).val())+"'>").appendTo(orderLi);
                $("<div class='my-pname' id='thispname'>"+pname+"</div>").appendTo(orderLi);

                var imgdiv = $("<div class='order-img'></div>").appendTo(orderLi);
                $("<img src='"+imgurl+"' id='thisimg'>").appendTo(imgdiv);

                var pricediv = $("<div class='order-price'></div>").appendTo(orderLi);
                $("<span>"+price+"</span>").appendTo(pricediv);
                $("<span>&nbsp;x&nbsp;</span>").appendTo(pricediv);
                $("<span id='thiscount'>"+count+"</span>").appendTo(pricediv);

                var thisallprice = $("<div class='order-thisprice'>此商品价：</div>").appendTo(orderLi);
                $("<span id='thisallPrice'>"+parseFloat(price*count)+"</span>").appendTo(thisallprice);

            });
            //做后生成总价
            var thisTotalPrice = $("<li></li>").appendTo(orderUl);
            var operatorDiv = $("<div class='operator'></div>").appendTo(thisTotalPrice);
            //收货地址 从数据字典中查到
            $.get("/user?method=getReceiverAddressByUid&uid="+uid,function (data) {
                if(data !=null){
                    var jsonData = eval(data);
                    var select = $("<select></select>").appendTo(operatorDiv);
                    $("<option>--请选择收货地址--</option>").appendTo(select);
                    for (var j = 0;j < jsonData.length;j++){
                        $("<option>"+jsonData[j]["receiverinfo"]+"</option>").appendTo(select);
                    }
                    
                }else {
                    $("<a href='/user?method=findAll&uid="+uid+"'>添加收货地址</a>").appendTo(operatorDiv);
                }
            })


            //备注的输入
            $("<b>备注：</b>").appendTo(operatorDiv);
            $("<input id='remarkInput' name='remark' placeholder='例如 快递、支付方式等'>").appendTo(operatorDiv);
            $("<br/>").appendTo(operatorDiv);//输入备注后加个换行


            $("<span>订单总价：</span>").appendTo(operatorDiv);
            $("<span id='totalPrice'>"+parseFloat( $(".allprice span").html() )+"</span>").appendTo(operatorDiv);
            $("<br/>").appendTo(operatorDiv);//显示完总价后再换个行
            /*<button>确认订单</button>
             <a href="#">取消</a>*/
            $("<button>确认订单</button>").appendTo(operatorDiv);
            $("<a href='#'>取消</a>").appendTo(operatorDiv);

        }
        
    });



    //确认订单的取消链接的点击事件
    $(document).on("click",".operator a",function () {
       var flag = window.confirm("确定要重新选择商品吗");
        if(flag){
            //说明要删除刚刚生成的订单
            $(".order-items>ul").find("li").remove();
        }
        return false
    });


    //确认订单按钮 点击事件
    $(document).on("click",".operator button",function () {
        //获得pid count ordermoney receiverinfo uid remark(备注)
        //1.把所有的pid和count封装到对应的数组中 ：
        var allli = $(".order-items>ul li");
        var pids = [];//创建pid数组
        var counts = [];//创建count数组
        var pnames = [];//名称数组
        var imgurls = [];//图片地址数组
        var prices = [];//此件商品的批量价格
        for(var j = 0;j<allli.length - 1;j++){
            var pid = allli.eq(j).find("input:first").val();//单个pid
            var count = $.trim(allli.eq(j).find("#thiscount").html());
            var pname = $.trim(allli.eq(j).find("#thispname").html());
            var imgurl = allli.eq(j).find("#thisimg").attr("src");
            var price = $.trim(allli.eq(j).find("#thisallPrice").html());

            pids.push(pid);
            counts.push(count);
            pnames.push(pname);
            imgurls.push(imgurl);
            prices.push(price);
        }
        //alert(pnames);
        //return false;
        
        //2.得到总价格
        var ordermoney = $("#totalPrice").html();

        //3.得到地址
        var receiverinfo = $("select").val();
        if(receiverinfo == "--请选择收货地址--"){
            alert("请选择收货地址");
            return false;
        }

        //4.得到备注
        var remark = $("#remarkInput").val();
        
        //进行提交添加订单
        $.ajax({
            url:"/orders?method=createOrders",
            type: "POST",
            data:{ "pids":JSON.stringify(pids),
                "counts":JSON.stringify(counts),
                "pnames":JSON.stringify(pnames),
                "imgurls":JSON.stringify(imgurls),
                "prices":JSON.stringify(prices),
                "ordermoney":ordermoney,
                "receiverinfo":receiverinfo,
                "remark":remark,
                "uid":uid
            },

            success:function (data) {
                if(data != null && data != ""){//若接收到数据，说明操作成功
                    //添加订单成功
                    alert("成功提交订单 请至我的订单中进行支付");
                    //提交成功就刷新页面，让链接跳转即可
                    location.href = "/shopcart?method=findMyShopcartByUid&uid="+uid;
                }else {
                    alert("稍后再试");
                }
            },
            error:function () {
                alert("稍后再试");
            }
        })

        return false;//是连接不执行跳转
    })

});

function getTotalPrice() {
    totalPrice = 0;//先清0，再遍历得到总价
    $("input:checkbox:checked").each(function (j) {
       var thisPrice =  $(this).parent().parent().children(".my-price").find("span");
        var thisCount = $(this).parent().parent().children(".edit").find("input");
        totalPrice=totalPrice + (  parseFloat(thisPrice.html()) * parseInt(thisCount.val())  );
    })
}