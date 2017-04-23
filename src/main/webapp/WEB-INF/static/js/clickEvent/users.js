/**
 * Created by huangrongchao on 2017/4/22.
 */

var $table = $('#users');

$table.bootstrapTable({
    url: "/getUsers",
    pagination: true, //分页
    singleSelect: false,
    search: true, //显示搜索框
    undefinedText: '-',
    pageNumber: 1,
    pageSize: 5,
    pageList: [5, 10, 20],
    sidePagination: "server",
    columns: [
        {
            title: '用户ID',
            field: 'id',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                return '<a href="/user/" ' + value + ' >' + value + '</a> ';
            }
        },
        {
            title: '用户名',
            field: 'username',
            align: 'center',
            valign: 'middle',
        },
        {
            title: '数据库',
            field: 'schema',
            align: 'center'
        },
        {
            title: '数据库用户个数',
            field: 'userNum',
            align: 'center'
        },
        {
            title: '用户注册时间',
            field: 'registerDate',
            align: 'center',
            formatter: function (value) {
                if (value != undefined && value != null) {
                    var datetime = new Date();
                    datetime.setTime(value);
                    var year = datetime.getFullYear();
                    var month = datetime.getMonth() + 1;
                    var date = datetime.getDate();
                    var hour = datetime.getHours();
                    var minute = datetime.getMinutes();
                    var second = datetime.getSeconds();
                    var mseconds = datetime.getMilliseconds();
                    return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second + "." + mseconds;
                }
            }

        },
        {
            title: '操作',
            field: 'id',
            align: 'center',
            formatter: function (value, row, index) {
                var e = '<a href="#" mce_href="#" onclick="edit(\'' + row.id + '\')">编辑</a> ';
                var d = '<a href="#" mce_href="#" onclick="del(\'' + row.id + '\')">删除</a> ';
                return e + d;
            }
        }
    ],
    onLoadSuccess: function (res) {
        console.log(JSON.stringify(res))
    }
});
