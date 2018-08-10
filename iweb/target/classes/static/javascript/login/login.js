var CodeVal = 0;
Code();

function Code() {
    $.ajax({
        url: "/getVerify",
        type: "get",
        success: function (resp) {
            var c = document.getElementById("myCanvas");
            var ctx = c.getContext("2d");
            ctx.clearRect(0, 0, 1000, 1000);
            ctx.font = "80px 'Hiragino Sans GB'";
            ctx.fillStyle = "#E8DFE8";
            ctx.fillText(resp, 0, 100);
        }
    });
}

function showCheck(a) {
    CodeVal = a;
}

$(document).keypress(function (e) {
    // 回车键事件
    if (e.which == 13) {
        $('input[type="button"]').click();
    }
});
//粒子背景特效
$('body').particleground({
    dotColor: '#E8DFE8',
    lineColor: '#133b88'
});
$('input[name="pwd"]').focus(function () {
    $(this).attr('type', 'password');
});
$('input[type="text"]').focus(function () {
    $(this).prev().animate({'opacity': '1'}, 200);
});
$('input[type="text"],input[type="password"]').blur(function () {
    $(this).prev().animate({'opacity': '.5'}, 200);
});
$('input[name="login"],input[name="pwd"]').keyup(function () {
    var Len = $(this).val().length;
    if (!$(this).val() == '' && Len >= 5) {
        $(this).next().animate({
            'opacity': '1',
            'right': '30'
        }, 200);
    } else {
        $(this).next().animate({
            'opacity': '0',
            'right': '20'
        }, 200);
    }
});
var open = 0;
layui.use('layer', function () {
    //非空验证
    $('input[type="button"]').click(function () {
        var username = $('input[name="username"]').val();
        var pwd = $('input[name="password"]').val();
        var code = $('input[name="imageCode"]').val();
        if (username == '') {
            $.Toast("提示", "请输入您的账号", "error", {
                stack: true,
                has_icon:true,
                has_close_btn:true,
                fullscreen:false,
                timeout:4000,
                sticky:false,
                has_progress:true,
                position_class: "toast-top-right",
                rtl:false
            });
        } else if (pwd == '') {
            $.Toast("提示", "请输入密码", "error", {
                stack: true,
                has_icon:true,
                has_close_btn:true,
                fullscreen:false,
                timeout:4000,
                sticky:false,
                has_progress:true,
                position_class: "toast-top-right",
                rtl:false
            });
        } else if (code == '' || code.length != 4) {
            $.Toast("提示", "输入验证码", "error", {
                stack: true,
                has_icon:true,
                has_close_btn:true,
                fullscreen:false,
                timeout:4000,
                sticky:false,
                has_progress:true,
                position_class: "toast-top-right",
                rtl:false
            });
        } else {
            $('.login').addClass('test'); //倾斜特效
            $('.login').addClass('testtwo'); //平移特效
            $('.authent').show().animate({right: -320}, {
                easing: 'easeOutQuint',
                duration: 600,
                queue: false
            });
            $('.authent').animate({opacity: 1}, {
                duration: 200,
                queue: false
            }).addClass('visible');

            var params = {
                username: username,
                password: pwd,
                imageCode: code
            };

            $.ajax({
                url: 'login',
                data: params,
                type: 'post',
                datType: "JSON",
                contentType: "application/x-www-form-urlencoded",
                success: function (resp) {
                    setTimeout(function () {
                        $('.authent').show().animate({right: 90}, {
                            easing: 'easeOutQuint',
                            duration: 600,
                            queue: false
                        });
                        $('.authent').animate({opacity: 0}, {
                            duration: 200,
                            queue: false
                        }).addClass('visible');
                        $('.login').removeClass('testtwo');
                    }, 2000);
                    setTimeout(function () {
                        $('.authent').hide();
                        $('.login').removeClass('test');
                        if (resp.httpStatus == '200') {
                            //登录成功
                            $('.login div').fadeOut(100);
                            $('.success').fadeIn(1000);
                            location.href = "/home?access_token=" + resp.access_token
                        } else {
                            $('.login').removeClass('testtwo');
                            $('.login').removeClass('test');
                        }
                        $.Toast("登录失败", resp.msg, "error", {
                            stack: true,
                            has_icon:true,
                            has_close_btn:true,
                            fullscreen:false,
                            timeout:4000,
                            sticky:false,
                            has_progress:true,
                            position_class: "toast-top-right",
                            rtl:false
                        });
                    }, 2400);
                },
                error: function (resp) {
                    location.href = "/login";
                }
            });
        }
    })
});
var fullscreen = function () {
    elem = document.body;
    if (elem.webkitRequestFullScreen) {
        elem.webkitRequestFullScreen();
    } else if (elem.mozRequestFullScreen) {
        elem.mozRequestFullScreen();
    } else if (elem.requestFullScreen) {
        elem.requestFullscreen();
    } else {
        //浏览器不支持全屏API或已被禁用
    }
};