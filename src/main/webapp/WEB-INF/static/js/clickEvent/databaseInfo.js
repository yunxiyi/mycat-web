/**
 * Created by huangrongchao on 2017/4/21.
 */

$('.user .fa-plus').click(function () {
    alert('aa')
})

$('.user .fa-plus').mouseenter(function () {
    $(this).css('color', '#2980b9');
});
$('.user .fa-plus').mouseleave(function () {
    $(this).css('color', '#25acdb');
});
