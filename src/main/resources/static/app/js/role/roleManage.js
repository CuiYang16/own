layui.config({
    base: '/static/app/module/eleTree/' //插件路径
}).extend({
    eleTree: 'eleTree'
});


layui.use(['tree', 'util', 'table', 'eleTree'], function () {
    var tree = layui.tree
        , layer = layui.layer,
        table = layui.table,
        eleTree = layui.eleTree,
        $ = layui.$;

   var el =  eleTree.render({
        elem: '#roles-tree',
        highlightCurrent:true,
        showLine:true,
       expandOnClickNode:false,
        url:'/roles/get-tree-roles',
        method :'get',
        request: {
            name: "roleName",
            key: "id",
            children: "children",
            checked: "checked",
            disabled: "isDisabled",
            isLeaf: "isLeaf"
        },
        response: {
            statusName: "code",
            statusCode: 22001,
            dataName: "data"
        },
       contextmenuList:[{eventName: "addSameLevel", text: "新增同级角色"},
           {eventName: "addChildrenLevel", text: "新增子级角色"},
           {eventName: "editRole", text: "编辑角色"},
           {eventName: "delRole", text: "删除角色"},
           ]
    })

    $('#unExpandBtn').click(function(){
        el.unExpandAll();
    });
    $('#expandBtn').click(function(){
        el.expandAll();
    });
    $('#refreshBtn').click(function(){
        el.reload();
        roleTableIns.reload();
    });

// 节点点击事件
    //currentData: {…}, parentData: {…}
    eleTree.on("nodeClick(roles-tree)",function(d) {
        var currentData = d.data.currentData;
        roleTableIns.reload({where:{'parentId':currentData.id}});
    })
//自定义右键菜单

    function editAndAdd(options){
        layer.open({
            type: 2,
            title: options.title,
            shadeClose: false,
            shade: 0.8,
            area: ['45vw', '80vh'],
            btn: ['确定', '重置'],
            content: '/roles/create-role',
            success: function (layero, index) {
                var iframe = window['layui-layer-iframe' + index];//拿到iframe元素
                //不可修改用户名，只读
                layero.find('iframe').contents().find('#roleId').css("display",'none');

            },
            yes: function (index, layero) {

                var iframe = window['layui-layer-iframe' + index];
                var submitID = 'submitRoleForm',
                    submit = layero.find('iframe').contents().find('#' + submitID);

                //监听提交
                iframe.layui.form.on('submit(' + submitID + ')', function (data) {
                    var field = data.field; //获取提交的字段
                    console.log(field)

                    $.ajax({
                        url:'/roles/insert-role',
                        type :'post',
                        data : {
                            'roleName':field.roleName,
                            'description':field.description,
                            'isDisabled':field.isDisabled,
                            'roleCode':field.roleCode,
                            'level':options.nowData.roleLevel,
                            'parentId':options.type=='same'?options.nowData.parentId:options.nowData.id,
                            'addType':options.type
                        },
                        success : function(data){
                            console.log(data)
                            if(data.code==22003){
                                el.reload();
                                roleTableIns.reload();
                            }

                        },
                        error : function(xhr, status, error) {
                            console.log(status);
                        }
                    });
                    layer.close(index);
                });
                submit.trigger('click');
                //return false;
            },
            btn2: function (index, layero) {
                var iframe = window['layui-layer-iframe' + index];
                var submitID = 'resetRoleForm',
                    submit = layero.find('iframe').contents().find('#' + submitID);
                submit.trigger('click');
                //禁止关闭弹出层
                return false;
            }

        });
        $('.layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    }
    eleTree.on("nodeAddSameLevel(roles-tree)",function(d) {

        editAndAdd({title:'新增同级角色',nowData: d.data,type:'same'});
    });
    eleTree.on("nodeAddChildrenLevel(roles-tree)",function(d) {
        editAndAdd({title:'新增子角色',nowData: d.data,type:'children'});
    });
    eleTree.on("nodeEditRole(roles-tree)",function(d) {
        console.group("自定义右键菜单回调nodeEditRole:")
        console.log(d.data);    // 点击节点对于的数据
        console.log(d.node);    // 点击的dom节点
        console.log(this);      // 与d.node相同
        console.groupEnd();
    });
    eleTree.on("nodeDelRole(roles-tree)",function(d) {
        console.group("自定义右键菜单回调nodedelRole:")
        console.log(d.data);    // 点击节点对于的数据
        console.log(d.node);    // 点击的dom节点
        console.log(this);      // 与d.node相同
        console.groupEnd();
    })




    //表格
    var roleTableIns = table.render({
        elem: '#roles-table'
        , id: 'rolesTable'
        , height: 'full-100'
        , url: '/roles/get-table-roles' //数据接口
        , page: true //开启分页
        , method: 'get'
        , response: {
            statusName: 'code',
            statusCode: 22002 //规定成功的状态码，默认：0
        }, toolbar: '#roleHeadToolbar' //开启头部工具栏，并为其绑定左侧模板
        , defaultToolbar: ['filter', 'exports', 'print']//自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left'},
            {field: 'id', title: 'ID', width: 80, fixed: 'left'}
            , {field: 'roleName', title: '角色名', align: 'center'}
            , {field: 'roleCode', title: '角色代码', align: 'center'}
            , {field: 'roleLevel', title: '角色级别', align: 'center'}
            , {
                field: 'isDisabled', title: '是否可用', align: 'center', templet: function (d) {
                    return !d.isDisabled ? '可用' : '<span style="color: red; font-weight: 800">不可用</span> ';
                }
            }
            , {field: 'createTime', title: '创建时间', align: 'center'}
            , {field: 'description', title: '描述', align: 'center'}
            , {title: '操作', fixed: 'right', width: 80, align: 'center', toolbar: '#roleBar'}
        ]], done: function (res, curr, count) {
            this.where = {};
        }
    });
    //头工具栏事件
    table.on('toolbar(rolesTableFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'getCheckData':
                    var data = checkStatus.data;
                    layer.alert(JSON.stringify(data));
                    break;
                case 'searchRole':
                    var idOrName = $('#idOrName').val();
                    layer.msg(idOrName)
                    if (idOrName != null && idOrName != '') {
                        roleTableIns.reload({
                                where: {'idOrName': idOrName}
                            }
                        );
                    } else {
                        //layer.msg('查询所有用户！');
                        roleTableIns.reload();
                    }
                    break;
            }
        }
    );
});


