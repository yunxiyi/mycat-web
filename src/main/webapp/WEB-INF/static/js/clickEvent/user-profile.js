/**
 * Created by huangrongchao on 2017/4/2.
 */

$('.profile-tabs ul li a').click(function () {
    $('.profile-tabs ul li.active').removeClass('active');
    $(this).parent().addClass('active');
    var page = $(this).attr('href');
    var url = "http://localhost:8088/" + page.substring(1, page.length);
    console.log(url);
    load(url);
});

//$('#info').load("http://localhost:8088/userInfo");
$.get("http://localhost:8088/databaseInfo", function (result) {
    $('#info').html(result);
})

$('.profile-dropdown').click(function () {
    if (!$(this).hasClass('open')) {
        $(this).addClass('open');
    } else {
        $(this).removeClass('open');
    }
});
function load(url) {
    $.get(url, function (result) {
        $('#info').html(result);
    })
}

