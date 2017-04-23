/**
 *
 * Created by huangrongchao on 2017/4/20.
 */

$('.panel-heading').click(function () {
    if ($(this).siblings('.panel-body').css('display') != 'none') {
        $(this).siblings('.panel-body').css('display', 'none');
    } else {
        $(this).siblings('.panel-body').css('display', 'block');
    }
});

$('.profile-dropdown').click(function () {
    if (!$(this).hasClass('open')) {
        $(this).addClass('open');
    } else {
        $(this).removeClass('open');
    }
});