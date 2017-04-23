/**
 * Created by huangrongchao on 2017/4/1.
 */

$('#header-nav ul li a').click(function () {
    if ($(this).parent().hasClass('open')) {
        $(this).parent().removeClass('open');
    } else {
        $('#header-nav ul li.open').removeClass('open');
        $(this).parent().addClass('open');
    }
});

$(document).bind("click", function (e) {
    var target = $(e.target);
    if (target.closest("#header-nav ul li.open").length == 0) {
        $("#header-nav ul li.open").removeClass('open');
    }
    if (target.closest(".mobile-search").length == 0) {
        $('.mobile-search').removeClass('active');
    }
});

//sidebar-nav
$('#sidebar-nav ul li a').click(function () {
    if ($(this).parent().hasClass('open')) {
        $(this).parent().removeClass('open');
        $(this).next().css('display', 'none');
    } else {
        $('#sidebar-nav ul li.open').removeClass('open');
        $(this).parent().addClass('open');
        $(this).next().css('display', 'block');
    }
});

//make-small-nav
$('#make-small-nav').click(function () {
    $('#sidebar-nav ul li.open').next().css('display', 'none');
    $('#sidebar-nav ul li.open').removeClass('open');
    $('#page-wrapper').toggleClass('nav-small');
});

$('.mobile-search').click(function (e) {
    e.preventDefault();
    var target = $(e.target);
    if ($(this).hasClass('active') && target.closest(".nav-search-icon").length != 0) {
        //$(this).removeClass('active');
    } else {
        $(this).addClass('active');
    }
    $('.mobile-search form input.form-control').focus();
});

$('#make-small-nav').click(function (e) {
    $('#page-wrapper').toggleClass('nav-small');
});

$('.submenu li a').click(function (e) {
    if ($(this).parent().hasClass('selectUrl')) {
        $(this).parent().removeClass('selectUrl');
    } else {
        $('.submenu li.selectUrl').removeClass('selectUrl');
        $(this).parent().addClass('selectUrl');
    }
    var url = $(this).prev().val();

    $("#load-web").load(url);
});