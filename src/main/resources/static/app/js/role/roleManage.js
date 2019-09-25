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
        url:'/roles/get-tree-roles',
        method :'get',
        request: {
            name: "title",
            key: "id",
            children: "children",
            checked: "checked",
            disabled: "disabled",
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
    });

// 节点点击事件
    //currentData: {…}, parentData: {…}
    eleTree.on("nodeClick(roles-tree)",function(d) {
        var currentData = d.data.currentData;
        roleTableIns.reload({where:{'parentId':currentData.id}});
    })
//自定义右键菜单
    eleTree.on("nodeAddSameLevel(roles-tree)",function(d) {
        console.group("自定义右键菜单回调nodeTest:")
        console.log(d.data);    // 点击节点对于的数据
        console.log(d.node);    // 点击的dom节点
        console.log(this);      // 与d.node相同
        console.groupEnd();
    });
    eleTree.on("nodeAddChildrenLevel(roles-tree)",function(d) {
        console.group("自定义右键菜单回调nodeAddChildrenLevel:")
        console.log(d.data);    // 点击节点对于的数据
        console.log(d.node);    // 点击的dom节点
        console.log(this);      // 与d.node相同
        console.groupEnd();
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
    // $.ajax({
    //     url: '/roles/get-tree-roles',
    //     type: 'get',
    //     async: false,
    //     success: function (result) {
    //         if (result.code == 22001) {
    //             //树
    //             tree.render({
    //                 elem: '#roles-tree'
    //                 , data: result.data
    //                 , showCheckbox: false  //是否显示复选框
    //                 , id: 'demoId1'
    //                 , edit: ['add', 'update', 'del']
    //                 , isJump: false //是否允许点击节点时弹出新窗口跳转
    //                 , click: function (obj) {
    //                     var data = obj.data;  //获取当前点击的节点数据
    //                     layer.msg(data.id);
    //                     roleTableIns.reload({where:{'parentId':data.id}});
    //                 }
    //                 , operate: function (obj) {
    //                     var type = obj.type; //得到操作类型：add、edit、del
    //                     var data = obj.data; //得到当前节点的数据
    //                     var elem = obj.elem; //得到当前节点元素
    //
    //                     //Ajax 操作
    //                     var id = data.id; //得到节点索引
    //                     if (type === 'add') { //增加节点
    //                         //返回 key 值
    //                         layer.msg(id);
    //                         return 28;
    //                     } else if (type === 'update') { //修改节点
    //                         console.log(elem.find('.layui-tree-txt').html()); //得到修改后的内容
    //                     } else if (type === 'del') { //删除节点
    //
    //                     }
    //                     ;
    //                 }
    //             });
    //         }
    //     },
    //     error: function (result) {
    //         console.log(result);
    //         data = null;
    //     }
    // })


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


