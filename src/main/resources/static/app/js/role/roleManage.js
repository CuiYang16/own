
function del(row) {
    alert(row.id)
}

var layout = [
    { name: '菜单名称', treeNodes: true, headerClass: 'value_col', colClass: 'value_col', style: '' },
    { name: 'id', field: 'id', headerClass: 'value_col', colClass: 'value_col', style: ''},
    { name: '创建时间', field: 'createTime', headerClass: 'value_col', colClass: 'value_col', style: ''},
    { name: 'parentId', field: 'parentId', headerClass: 'value_col', colClass: 'value_col', style: ''},
    { name: '描述', field: 'description', headerClass: 'value_col', colClass: 'value_col', style: ''},
    {
        name: '操作',
        headerClass: 'value_col',
        colClass: 'value_col',

        render: function(row) {
            var delBtn="<a class='layui-btn layui-btn-danger layui-btn-sm' > 删除</a>";
            var editBtn="<a class='layui-btn layui-btn-sm' >编辑</a>"
            var addChildren="<a class='layui-btn layui-btn-sm'>添加子节点</a>";
            return addChildren+editBtn+delBtn; //列渲染
        }
    },
];
layui.config({
    base: '/static/app/module/treetable/' //插件路径
}).extend({
    treetable: 'treetable'
});
layui.use(['form', 'treetable', 'layer','laypage'], function() {
    var layer = layui.layer, form = layui.form,laypage=layui.laypage, $ = layui.jquery;
    var data=null;
    var count=0;
    getRoles(1,10);
    var tree = layui.treetable({
        elem: '#roles', //传入元素选择器
        spreadable: false, //设置是否全展开，默认不展开
        checkbox : true,
        nodes: data,
        layout: layout,
        callback: {
            beforeCheck : treetableBeforeCheck,
            onCheck : treetableOnCheck,
            beforeCollapse : treetableBeforeCollapse,
            onCollapse : treetableOnCollapse,
            beforeExpand : treetableBeforeExpand,
            onExpand : treetableOnExpand,
        }
    });

    function treetableBeforeCheck(node){
        alert("回调方法treetableBeforeCheck");
        return true;
    };
    function treetableOnCheck(node){
        alert("回调方法treetableOnCheck");
    };
    function treetableBeforeCollapse(){
        alert("回调方法treetableBeforeCollapse");
        return true;
    };
    function treetableOnCollapse(){
        alert("回调方法treetableOnCollapse");
    };
    function treetableBeforeExpand(){
        alert("回调方法treetableBeforeExpand");
        return true;
    };
    function treetableOnExpand(){
        alert("回调方法treetableOnExpand");
    };

    form.render();

    $('#collapse').on('click', function() {
        tree.collapse();
    });

    $('#expand').on('click', function() {
        tree.expand();
    });

    $('#selected').on('click', function() {
        console.log(tree.getSelected());
        alert(JSON.stringify(tree.getSelected()));
    });

    $('#unSelected').on('click', function() {
        console.log(tree.getUnSelected());
        alert(JSON.stringify(tree.getUnSelected()));
    });

    $('#editNode').on('click', function() {
        var node = tree.getNode("3");
        node.name = '我就是变个名字';
        tree.editNodeName(node);
    });

    $('#removeNode').on('click', function() {
        var node = tree.getNode("2");
        tree.removeNode(node);
    });

    $('#getNode').on('click', function() {
        var node = tree.getNode("2");
        alert(JSON.stringify(node));
    });

    $('#getNodeByParam').on('click', function() {
        var node = tree.getNodeByParam("name", "子节点21", null);
        alert(JSON.stringify(node));
    });

    $('#all').on('click', function() {
        alert(JSON.stringify(tree.getNodes()));
    });

    $('#addChildrenNode').on('click', function() {

        tree.addNode(tree.getNode("2"), { });
    });

    $('#addRootNode').on('click', function() {
        tree.addNode(null, {});
    });

    $('#destory').on('click', function() {
        tree.destory();
    });

    $('#expandNode').on('click', function() {
        tree.expandNode(tree.getNode("2"), false);
    });

    $('#collapseNode').on('click', function() {
        tree.collapseNode(tree.getNode("2"), false);
    });

    $('#checkNode').on('click', function() {
        tree.checkNode(tree.getNode("1"), true);
    });

    $('#uncheckNode').on('click', function() {
        tree.checkNode(tree.getNode("1"), false);
    });

    $('#disableNode').on('click', function() {
        tree.setChkDisabled(tree.getNode("1"), true);
    });

    $('#ableNode').on('click', function() {
        tree.setChkDisabled(tree.getNode("1"), false);
    });

    $('#checkAllNode').on('click', function() {
        tree.checkAllNodes(true);
    });

    $('#unCheckAllNode').on('click', function() {
        tree.checkAllNodes(false);
    });

    function getRoles(page,limit){
        $.ajax({
            url:'/roles/get-roles',
            type:'get',
            async: false,
            data:{'page':page,'limit':limit},
            success:function(result){
                if(result.code==22001){
                    data=result.data;
                    count = result.count;
                }
            },
            error:function(result){
                console.log(result);
            }
        })
    }

    laypage.render({
        elem:'page-content',
        count:count,//总条数
        curr:1,//当前页
        limit:10,//每页的条数
        //limits:[10,20,30],//可选择每页数目
        prev:"<i class='layui-icon'>&#xe603;</i>",//上一页图标
        next:"<i class='layui-icon'>&#xe602;</i>",//下一页图标
        //theme:"#009688",//分页主色
        layout: ['prev', 'page', 'next', 'skip','count',  'limit'],//设置分页组件显示
        jump:function (obj,first) {
            if(!first){ //设置首次渲染分页无需走业务逻辑处理函数，不然会陷入死循环
                currPage = obj.curr;
                limit = obj.limit;
                //业务处理的函数方法
                console.log(currPage,limit)
                getRoles(currPage,limit);

            }

        }
    });

});

