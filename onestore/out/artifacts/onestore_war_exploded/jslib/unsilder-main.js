var uid;//用户id
var imgurl;//用户头像地址
var pageSize = 4;//每页多少条记录
$(function() {
    var unslider = $('.banner').unslider({
        dots: true,               //  显示导航点
        fluid: true               //  支持响应式设计
    });
    $('.unslider-arrow').click(function() {
        var fn = this.className.split(' ')[1];

        //  Either do unslider.data('unslider').next() or .prev() depending on the className
        unslider.data('unslider')[fn]();
    });
});


/*小型导航条*/
//控制导航栏链接是否有效果
$(document).ready(function () {
    uid = $("input:first").val();//全局变量 ，用于鉴定user是否登录
    imgurl = $("input[type='hidden']").eq(1).val();//全局变量，得到用户的头像内容

    //$(".limitdiv").hide();

    //当进行页面刷新、进入页面 需要随机查询出商品  首先限制最多8条
    //这里执行一个函数
    getinfo(null,0,null);//这里只传第二个参数，随机查询,把商品显示到主页面 0代表主页面是第一次加载商品，
    // 过滤器会对此参数值进行判断并进行一些操作

    var as = $("#minidirect").find("a");//获得该id下的所有的a节点
    for(var i = 0;i<as.length-1;i++){//最后一个节点不添加check()事件
       as.eq(i).click(function () {
           return check();//检查是否为空
       });
    }


    //对头像部分的控制
    isUserLogin();


    //左边菜单栏的功能查询使用ajax，用json传输，这里绑定点击事件
    $("#categories > li").find("a").click(function () {
        var avalue = this.innerHTML;//得到a标签中的文本值
        //调用ajax函数进行异步提交查询
        //alert(avalue);
        getinfo(null,null,avalue);  //这里根据目录名查找，只传第三个参数
    });


    //对分页信息的 下一页和上一页（分类搜索）   或者前一页和后一页（主搜索） 绑定点击事件
    $(document).on("click",".limitdiv a",function () {
        //根据当前页来设置 上一页 和 下一页 的链接currentPage值

        //当前页的input
        var thisInput = $(".limitdiv input");

        //获得当前页的值
        var currentPage = parseInt(thisInput.val());

        //得到当前所查内容--》以便再次查
        var currentSearch = $(".limitdiv span").eq(0).text().substring(5);

        //获得总页数
        var totalPage = parseInt($(".limitdiv span").eq(1).text().substring(1,2));

        var url;
        var thisText = this.innerHTML;//当前事件的文本内容；因为请求成功的函数中需要用到，所以先取出来
        if(thisText == "上一页"){
            //做上一页查询操作
            //1.判断当前页是不是第一页，若是则提示已经是第一页；若不是执行提交
            alert("上一页");
            if(currentPage != 1){
                url= "/product?method=findProductByCategory&category="+currentSearch
                    +"&currentPage="+(currentPage-1);
            }else {
                alert("已经是第一页！");
            }

        }else if(thisText == "下一页"){
            //做下一页查询操作
            //1.判断当前页是不是最后一页，若是则提示已经是最后页；若不是执行提交
            if(currentPage < totalPage){
                url= "/product?method=findProductByCategory&category="+currentSearch
                    +"&currentPage="+(currentPage+1);
            }else {
                alert("已经是最后一页！");
            }
        }else if(thisText == "前一页"){
            //做前一页查询操作  ----查pname
            //1.判断当前页是不是第一页，若是则提示已经是第一页；若不是执行提交
            if(currentPage != 1){
                url = "/product?method=findProductByPname_Limit&pname="+currentSearch
                    +"&currentPage="+(currentPage-1);
            }else {
                alert("已经是第一页！");
            }
        }else if(thisText == "后一页"){
            //做后一页查询操作  ----查pname
            //1.判断当前页是不是最后一页，若是则提示已经是最后页；若不是执行提交
            if(currentPage < totalPage){
                url = "/product?method=findProductByPname_Limit&pname="+currentSearch
                    +"&currentPage="+(currentPage+1);
            }else {
                alert("已经是最后一页！");
            }
        }

        //为了防止url空指针
        if(url == null){
            return false;
        }

        //进行异步提交
        $.get(url,function (data) {
            //把数据转换成String
            var jsonData = eval(data);

            //进行商品生成
            var div = $("#items-clearfix");
            if(jsonData != null && jsonData != ""){
                div.html("");
                clearAndcreateItems(jsonData,div);
            }else {
                div.html("没有更多相关商品");
            }

            //对结果数据处理完毕后把当前页做修改判断是加还是减
            if(thisText == "上一页" || thisText == "前一页"){
                thisInput.val(currentPage-1);
            }else {
                thisInput.val(currentPage+1);
            }

        })
        //return false;//取肖自动链接跳转
    });






    //对分页信息div中的input绑定改变了内容事件   改变了内容就给出提示是否翻页，然后查询
    $(document).on("change",".limitdiv input",function () {
        //alert("input值改变了");
        //当前页的input
        var thisInput = $(".limitdiv input");

        //获得this的下一个节点的值
        var nextNode = thisInput.next();
        //alert(nextNode.html());
        //return false;

        //获得当前页的值
        var currentPage = parseInt(thisInput.val());

        //得到当前所查内容--》以便再次查
        var currentSearch = $(".limitdiv span").eq(0).text().substring(5);

        //获得总页数
        var totalPage = parseInt($(".limitdiv span").eq(1).text().substring(1,2));

        var url;  //如果大于总页数 或者小于等于0 就给出提示  然后判断是上一页下一页 还是前一页和后一页 来区分url
        if(currentPage > totalPage || currentPage <= 0){
            alert("不存在这一页内容！");
        }else if(nextNode.html() == "下一页"){
            url= "/product?method=findProductByCategory&category="+currentSearch
                +"&currentPage="+currentPage;
        }else if(nextNode.html() == "后一页"){
            url = "/product?method=findProductByPname_Limit&pname="+currentSearch
                +"&currentPage="+currentPage;
        }

        //为了防止url空指针
        if(url == null){
            return false;
        }

        //进行异步提交
        $.get(url,function (data) {
            //把数据转换成String
            var jsonData = eval(data);

            //进行商品生成
            var div = $("#items-clearfix");
            if(jsonData != null && jsonData != ""){
                div.html("");
                clearAndcreateItems(jsonData,div);
            }else {
                div.html("没有更多相关商品");
            }

        })

    });





    //对滚动文字广告的ajax请求得到广告数据 adstate为1的数据
    getTextAd();


    ////对图片广告的ajax请求得到广告数据 adstate为2的数据
    getPictureAd();



    //搜索按钮绑定点击事件，用于提交搜索
    $(".search button").click(function () {
        //得到输入框
        var search_input = $(".search input");

        //alert(search_input);

        //如果搜索框内容为null 则提示输入内容
        if(search_input.val() == null || search_input.val() == ""){
            return false;
        }

        //调用方法查询
        getinfo(search_input.val(),null,null);






    })


});


//第二个ready
$(document).ready(function () {
//对商品内容div的底部小于850px时的事件绑定


    //此事件通过把查询所有的pid放在本用户的session的属性值中（过滤器实现），然后每次随机查询时从该属性值中获取随机的不重复的pid个数

    var isHaveProduct = true;//标志还有没有商品
    $(window).scroll(function () {
        var win=$(window);
        if(( win.height()-win.scrollTop() )< 888){

            //先判断还有没有商品
            if(!isHaveProduct){
                //没有商品时，就不用继续查询了
                return false;
            }

            var div = $("#items-clearfix");
            //再次执行随机查询
            var url="/product?method=findProductRandom&thisIndex=1";

            $.ajax({
                url:url,
                success:function (data) {
                    //回掉函数
                    var jsonData = eval(data);
                    //alert("查询成功");

                    if(jsonData != null && jsonData != ""){
                        clearAndcreateItems(jsonData,div);

                    }
                },
                error:function () {
                    //alert("没有更多商品了");
                    $("<div>没有更多相关商品...</div>").appendTo(div);
                    isHaveProduct = false;//表示已经没有商品了

                }
            });//ajax请求部分结束

        }
    });


    //第二个 ready****************************************************
});





//验证是否登录
function check() {
    
    if(uid != "" && uid != null){
        return true;
    }
    alert("请先登录!");
    return false;
}

//对头像部分的控制函数
function isUserLogin() {
    var imgNode= $(".user").find("img");
    var loginDiv = $(".user-a");
    //如果用户登录就显示用户头像
    if(uid != "" && uid !=null){
        imgNode.attr("src",imgurl);//把头像路径
        loginDiv.html("登陆成功");//隐藏登陆和注册的div
    }

    //alert("asd");
}





//查询的ajax，通过参数的值不同形成多态，第一个参数为根据名称查询pname：有值调用根据pname查询（搜索时调用）
//第二个参数为全局随机random：调用随机查询（进首页面调用）  第三个参数为分类category：点击链接时才会传值调用
//使用方法：每次只做一次访问，即查询什么传什么值，其它设置为null
function getinfo(pname,random,category) {
    //需要的信息，根据类别查询
    var url=null;
    if(pname != null){
        url = "/product?method=findProductByPname_Limit&pname="+pname+"&currentPage=1";
        $.get(url,function (data) {
            //回掉函数
            var jsonData = eval(data);

            //然后把clearfix 这个ul中的所有内容清空
            var div = $("#items-clearfix");

            if(jsonData != null && jsonData != ""){
                div.html("");
                $(".banner").hide();//隐藏banner部分
                clearAndcreateItems(jsonData,div);
                

                //准备好要添加的内容：总页数totalPage 、当前第一页（第一次访问分类项目默认是第一页）
                //查询数据库查到总页数  （此方法可能需要优化）
                var totalPage;
                $.get("/product?method=findProductCountByPname&pname="+pname,function (data) {
                    var jsonData = eval(data);
                    totalPage = (jsonData["count"]/pageSize)+1;
                })
                if(totalPage == null){ //若没有找到count 即可能数据库报错等等原因
                    totalPage = parseInt(jsonData.length/pageSize)+1;
                }

                //得到分页div   分页信息放到了json数据的最后，所以查最后
                var limitDiv = $(".limitdiv");
                limitDiv.html("");//先清空，否则重复
                $("<span>当前查询:"+pname+"</span>").appendTo(limitDiv);
                $("<span>共"+totalPage+"页</span>").appendTo(limitDiv);
                //上一页
                $("<a href='#'>前一页</a>").appendTo(limitDiv);
                $("<input value='1'>").appendTo(limitDiv);
                $("<a href='#'>后一页</a>").appendTo(limitDiv);
                
            }else {
                div.html("没有相关商品");
            }
            
        });


    }else if(random != null){
        
        //pname为空则执行随机查找,进入首页面时也进行随机查找，thisIndex参数代表是否是首页面的第一次查找：过滤器根据此参数进行执行一些操作
        //此参数值通过random得到
        
        url="/product?method=findProductRandom&thisIndex="+random;
        $.get(url,function (data) {
            //回掉函数
            var jsonData = eval(data);

            //然后把clearfix 这个ul中的所有内容清空
            var div = $("#items-clearfix");

            if(jsonData != null && jsonData != ""){
                div.html("");
                clearAndcreateItems(jsonData,div);
            }else {
                div.html("没有更多相关商品");
            }
        });
    }else if(category != null){
        //alert("进行提交访问");
        //参数：分类名、当前页设置为1，因为是第一次访问
        url="/product?method=findProductByCategory&category="+category+"&currentPage=1";
        //设置分页大小  全局设置


        $.get(url,function (data) {
            //回掉函数
            var jsonData = eval(data);

            //然后把clearfix 这个ul中的所有内容清空
            var div = $("#items-clearfix");

            if(jsonData != null && jsonData != ""){
                div.html("");//清空商品列表
                $(".banner").hide();//隐藏banner部分
                clearAndcreateItems(jsonData,div);

                //准备好要添加的内容：总页数totalPage 、当前第一页（第一次访问分类项目默认是第一页）
                //查询数据库查到总页数  （此方法可能需要优化）
                var totalPage;
                $.get("/product?method=findProductCountByCategory&category="+category,function (data) {
                    var jsonData = eval(data);
                    totalPage = (jsonData["count"]/pageSize)+1;
                })
                if(totalPage == null){ //若没有找到count 即可能数据库报错等等原因
                    totalPage = parseInt(jsonData.length/pageSize)+1;
                }

                //得到分页div   分页信息放到了json数据的最后，所以查最后
                var limitDiv = $(".limitdiv");
                limitDiv.html("");//先清空，否则重复
                $("<span>当前分类:"+category+"</span>").appendTo(limitDiv);
                $("<span>共"+totalPage+"页</span>").appendTo(limitDiv);
                //上一页
                $("<a href=''>上一页</a>").appendTo(limitDiv);
                $("<input value='1'>").appendTo(limitDiv);
                $("<a href='#'>下一页</a>").appendTo(limitDiv);

            }else {
                div.html("没有相关商品");
            }

        });

    }

}


//创建商品内容 构建商品内容的方法  参数：jsonData,得到的请求数据；div：所有商品的父节点
// 功能：在此父节点中的内容加动态创建的节点到已有节点后
function clearAndcreateItems(jsonData,div) {

    //alert("该分类有"+jsonData.length+"件商品");
    //这个顶是一个list集合，所以要循环取出
    //需要取出的内容：imgurl、商品名称、价格
    var ul = $("<ul class='clearfix'></ul>").appendTo(div);
    //var li;
    for(var j = 0;j<jsonData.length;j++){

        //分别生成：
        /*<li>
         <a href="">
         <img src="img/logo.png" alt="">
         <p>iphone7 plus现在超级便宜，不要错过</p>
         </a>
         <!-- 显示钱图片和价格 -->
         <i class="icon-money"></i><span>2000</span>
         </li>
         */
        /*if(ul.children().last().is("li")){
         //说明最后一个是li
         ul.find("li").last()
         }else {}*/
        var li = $("<li></li>").appendTo(ul);

        var a=$("<a href='/product?method=findProductByPid_1&pid="+jsonData[j]["pid"]+"'></a>").appendTo(li);
        var img = $("<img src='"+jsonData[j]["imgurl"]+"' alt=''>").appendTo(a);
        var p = $("<p>"+jsonData[j]["pname"]+"</p>").appendTo(a);

        var i = $("<i class='icon-money'></i>").appendTo(li);

        i.after($("<span>"+jsonData[j]["price"]+"</span>"));
    }
}

//对图片广告的数据获取，获取adstate=2的数据
function getPictureAd() {
    //先获得存放pic的div
    var allAdDiv = $(".ad");

    $.get("/product?method=findAllAd&adstate=2",function (data) {
        //把数据转成string
        var jsonData = eval(data);//里面的数据还是key-value
        console.log(jsonData);//查看json数据
        //用a标签替换p标签的内容
        var len = jsonData.length;
        if(len > 0){//判断jsonData中是否有值
            for(var i = 0;i<len;i++){
                allAdDiv.eq(i).html("");//先清空里面的内容
                var aaa = $("<a href='/product?method=findProductByPid_1&pid="+jsonData[i]["pid"]+"'></a>").appendTo(allAdDiv.eq(i));
                $("<img src='"+jsonData[i]["imgurl"]+"'>").appendTo(aaa);
            }
        }
    });
}

//对滚动文字广告的数据获取，获取adstate=1的数据
function getTextAd() {
    //先获得文本p标签的父节点（到时循环赋值给p）
    var pParent = $(".info");
    var allP = pParent.find("p");

    $.get("/product?method=findAllAd&adstate=1",function (data) {
        //把数据转成string
        var jsonData = eval(data);//里面的数据还是key-value
        console.log(jsonData);//查看json数据
        //用a标签替换p标签的内容
        var len = jsonData.length;
        if(len > 0){//判断jsonData中是否有值
            for(var i = 0;i<len;i++){
                allP.eq(i).html("");//先清空里面的内容
                $("<a href='/product?method=findProductByPid_1&pid="+jsonData[i]["pid"]+"'>"
                    +jsonData[i]["description"]+"</a>").appendTo(allP.eq(i));
            }
        }
    });
}




