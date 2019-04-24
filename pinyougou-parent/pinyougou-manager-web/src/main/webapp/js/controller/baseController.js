//基本控制层
app.controller('baseController', function ($scope) {
    //重新加载列表 数据
    $scope.reloadList=function(){
        //切换页码
        //$scope.findPage( $scope.paginationConf.currentPage,
        $scope.search( $scope.paginationConf.currentPage,
            $scope.paginationConf.itemsPerPage);
    };



    //分页控件配置
    $scope.paginationConf = {
        //当前页码
        currentPage: 1,
        //总条数
        totalItems: 10,
        //每页记录数
        itemsPerPage: 10,
        //页码选项
        perPageOptions: [10, 20, 30, 40, 50],
        //更改页面时触发的选项
        onChange: function(){
            $scope.reloadList();//重新加载
        }
    }

})