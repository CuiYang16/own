layui.use('table', function () {
    var table = layui.table;
    var $ = layui.$;
    //第一个实例
    table.render({
        elem: '#users-table'
        , id: 'usersTable'
        , url: '/users/get-users' //数据接口
        , response: {
            statusName: 'code',
            statusCode: 23001 //规定成功的状态码，默认：0
        }
        , method: 'get'
        , toolbar: '#headToolbar' //开启头部工具栏，并为其绑定左侧模板
        , defaultToolbar: ['filter', 'exports', 'print']//自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
        // ,parseData:function(res){
        //     console.log(res.code);
        //     return {
        //         code:res.code,
        //         msg:res.msg,
        //         count:res.count,
        //         data:res.data
        //     };
        // }
        , page: true //开启分页
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left'},
            {
                field: 'id',
                title: 'ID',
                width: 80,
                fixed: 'left',
                templet: '<div><span title="{{d.id}}">{{d.id}}</span></div>'
            }
            , {
                field: 'userName',
                title: '用户名',
                width: 80,
                fixed: 'left',
                templet: '<div><span title="{{d.userName}}">{{d.userName}}</span></div>'
            }
            , {
                field: 'sex', title: '性别', width: 80, templet: function (d) {
                    return d.sex == '1' ? '男' : '女';
                }
            }
            , {
                field: 'birthday',
                title: '生日',
                width: 100,
                templet: '<div><span title="{{d.birthday}}">{{d.birthday}}</span></div>'
            }
            , {
                field: 'eMail',
                title: '邮箱',
                width: 150,
                templet: '<div><span title="{{d.eMail}}">{{d.eMail==null?"--":d.eMail}}</span></div>'
            }

            , {
                field: 'tel',
                title: '电话',
                width: 150,
                templet: '<div><span title="{{d.tel}}">{{d.tel==null?"--":d.tel}}</span></div>'
            }

            , {field: 'loginCount', title: '登录次数', width: 80}
            , {
                field: 'enabled', title: '去激活', width: 100, align: 'center', templet: function (d) {
                    return d.enabled ? '激活' : '<span style="display:block; background-color: red;color: white;">去激活</span> ';
                }
            }
            , {
                field: 'accountNonLocked', title: '锁定', width: 100, align: 'center', templet: function (d) {
                    return d.accountNonLocked ? '未锁定' : '<span style="display:block; background-color: red;color: white;">已锁定</span> ';
                }
            }
            , {
                field: 'credentialsNonExpired', title: '凭证到期', width: 100, align: 'center', templet: function (d) {
                    return d.credentialsNonExpired ? '未到期' : '<span style="display:block; background-color: red;color: white;">已到期</span> ';
                }
            }
            , {
                field: 'accountNonExpired', title: '授权到期', width: 100, align: 'center', templet: function (d) {
                    return d.accountNonExpired ? '未到期' : '<span style="display:block; background-color: red;color: white;">已到期</span> ';
                }
            }
            , {
                field: 'createTime',
                title: '创建时间',
                templet: '<div><span title="{{d.createTime}}">{{d.createTime==null?"--":d.createTime}}</span></div>'
            }
            , {
                field: 'lastLoginTime',
                title: '最后登录时间',
                templet: '<div><span title="{{d.lastLoginTime}}">{{d.lastLoginTime==null?"--":d.lastLoginTime}}</span></div>'
            },
            {title: '操作', fixed: 'right', width: 150, align: 'center', toolbar: '#userBar'}

        ]]
    });
//头工具栏事件
    table.on('toolbar(userTableFilter)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'getCheckData':
                var data = checkStatus.data;
                layer.alert(JSON.stringify(data));
                break;
            case 'addUser':

                layer.open({
                    type: 2,
                    title: '添加用户信息',
                    shadeClose: false,
                    shade: 0.8,
                    area: ['45vw', '80vh'],
                    btn: ['确定', '重置'],
                    content: '/users/create-user',
                    success: function (layero, index) {
                        var iframe = window['layui-layer-iframe' + index];//拿到iframe元素
                        //iframe. editUserDetail(JSON.stringify(data));//向此iframe层方法 传递参数

                    },
                    yes: function (index, layero) {
                        var iframe = window['layui-layer-iframe' + index];
                        var submitID = 'submitUserForm',
                            submit = layero.find('iframe').contents().find('#' + submitID);
                        //
                        var userName = layero.find('iframe').contents().find('#userName').val();
                        console.log(userName)
                        $.ajax({
                            url: "/user/val-user-name",
                            type: 'get',
                            async: false,
                            data: {"userName": userName},
                            success: function (result) {
                                if (result.code == 52002) {
                                    layer.msg(result.msg);
                                    return false;
                                } else {
                                    //监听提交
                                    iframe.layui.form.on('submit(' + submitID + ')', function (data) {
                                        var field = data.field; //获取提交的字段
                                        console.log();
                                        $.ajax({
                                            url: '/users/insert-user',
                                            data: field,
                                            type: 'post',
                                            success: function (result) {
                                                // if(result.code==52002){
                                                //     layer.tips(result.msg, '#userName', {
                                                //         tips: [3, 'red'],
                                                //         time: 4000
                                                //     });
                                                //     return false;
                                                // }
                                                if (result.code == 23003) {

                                                    layer.msg(result.msg);
                                                    table.reload('users-table');
                                                }
                                                if (result.code == 53003) {
                                                    layer.msg(result.code + ':' + result.msg);
                                                }
                                            },
                                            error: function (result) {
                                                layer.msg(result);
                                            }
                                        });
                                        layer.close(index);
                                    })
                                    submit.trigger('click');
                                    return false;
                                }
                            },
                            error: function (result) {
                                console.log(result);
                            }
                        });

                    },
                    btn2: function (index, layero) {
                        var iframe = window['layui-layer-iframe' + index];
                        var submitID = 'resetUserForm',
                            submit = layero.find('iframe').contents().find('#' + submitID);
                        submit.trigger('click');
                        //禁止关闭弹出层
                        return false;
                    }
                })
                break;
            case 'searchUser':
                var idOrName = $('#idOrName').val();
                if (idOrName != null && idOrName != '') {
                    table.reload('usersTable', {
                            where: {'idOrName': idOrName}
                        }
                    );
                } else {
                    layer.msg('查询所有用户！');
                    table.reload('usersTable');
                }
                break;
            //自定义头工具栏右侧图标 - 提示
            case 'LAYTABLE_TIPS':
                layer.alert('这是工具栏右侧自定义的一个图标按钮');
                break;
        }
        ;
    });
//监听工具条
    table.on('tool(userTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'detail') {
            layer.open({
                type: 2,
                title: '查看用户详情',
                shadeClose: false,
                shade: 0.8,
                area: ['45vw', '80vh'],
                content: '/users/user-detail',
                success: function (layero, index) {
                    var iframe = window['layui-layer-iframe' + index];//拿到iframe元素
                    iframe.child(JSON.stringify(data))//向此iframe层方法 传递参数
                }
            });
        } else if (obj.event === 'edit') {
            layer.open({
                type: 2,
                title: '编辑用户信息',
                shadeClose: false,
                shade: 0.8,
                area: ['45vw', '80vh'],
                btn: ['确定', '重置'],
                content: '/users/edit-user',
                success: function (layero, index) {
                    var iframe = window['layui-layer-iframe' + index];//拿到iframe元素
                    iframe.editUserDetail(JSON.stringify(data));//向此iframe层方法 传递参数
                    //不可修改用户名，只读
                    layero.find('iframe').contents().find('#userName').attr('readOnly', true)

                },
                yes: function (index, layero) {

                    var iframe = window['layui-layer-iframe' + index];
                    var submitID = 'submitUserForm',
                        submit = layero.find('iframe').contents().find('#' + submitID);

                    //监听提交
                    iframe.layui.form.on('submit(' + submitID + ')', function (data) {
                        var field = data.field; //获取提交的字段
                        console.log(field);

                        //提交 Ajax后台

                        $.ajax({
                            url: "/users/update-user",
                            type: "post",
                            data: data.field,
                            success: function (result) {
                                if (result.code == 23002) {
                                    layer.msg(result.msg);
                                    table.reload('users-table');
                                } else {
                                    layer.msg(result.code + ':' + result.msg);
                                }
                            },
                            error: function (result) {
                                layer.msg(result.code + ':' + result.msg);
                                console.log(result);
                            }
                        });
                        layer.close(index);
                    });
                    submit.trigger('click');
                    return false;
                },
                btn2: function (index, layero) {
                    var iframe = window['layui-layer-iframe' + index];
                    var submitID = 'resetUserForm',
                        submit = layero.find('iframe').contents().find('#' + submitID);
                    submit.trigger('click');
                    //禁止关闭弹出层
                    return false;
                }
            });
        }
    });
});