


$(document).ready(function () {
    /*提交时的非空判断*/
    $("input[type='submit']").click(function (e) {
        $("input").each(function() {
            if(this.value == null || this.value == ""){
                e.preventDefault();
                $(this).next("span").html("请输入");
            }
        });
        if($("select").val() == "--请选择类别--"){
            alert("请选择")
            e.preventDefault();
        }

    });

    $("input").blur(function () {
        $(this).next("span").html("");
        if(this.value == null || this.value == ""){
            $(this).next("span").html("请输入");
        }
    });

    $("select").blur(function () {
        if($("select").val() == "--请选择类别--"){
            alert("请选择")
        }
    })
})