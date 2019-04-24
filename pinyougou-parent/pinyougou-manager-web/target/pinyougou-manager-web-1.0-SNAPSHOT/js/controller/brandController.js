// 前端控制层
// 品牌控制层
//控制层
app.controller('brandController' ,function($scope,$controller,brandService){

    $controller('baseController',{$scope:$scope});//继承

    //读取列表数据绑定到表单中
    $scope.findAll=function(){
        brandService.findAll().success(
            function(response){
                $scope.list=response;
            }
        );
    }

   /* //分页
    $scope.findPage=function(page,rows){
        brandService.findPage(page,rows).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }*/

    //查询实体
    $scope.findOne=function(id){
        brandService.findOne(id).success(
            function(response){
                $scope.entity= response;
            }
        );
    }

    //保存
    $scope.save=function(){
        var serviceObject;//服务层对象
        if($scope.entity.id!=null){//如果有ID
            serviceObject=brandService.update( $scope.entity ); //修改
        }else{
            serviceObject=brandService.add( $scope.entity  );//增加
        }
        serviceObject.success(
            function(response){
                if(response.success){
                    //重新查询
                    $scope.reloadList();//重新加载
                }else{
                    alert(response.message);
                }
            }
        );
    }

    $scope.selectIds=[];//选中的id集合
    
    //更新复选
    $scope.updateSelectIds=function($event,id){
        if ($event.target.checked){//如果被选中,则增加进数组
            $scope.selectIds.push(id);
        }else{
            var ids = $scope.selectIds.indexOf(id);//如果用户点击了两次选中之后，则视为取消删除该id的数据
            $scope.selectIds.splice(ids,1);//splice() 方法可删除从 index 处开始的零个或多个元素，并且用参数列表中声明的一个或多个值来替换那些被删除的元素
        }
    }

    //删除操作
    $scope.delete=function () {
        brandService.delete($scope.selectIds).success(
            function (response) {
                if(response.success){
                    $scope.reloadList();//刷新列表 
                }
            }
        )
    }


    $scope.searchEntity={};//定义搜索对象

    //条件查询
    $scope.search=function(page,rows){
        brandService.findPageSearch(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.paginationConf.totalItems=response.total;//总记录数
                $scope.list=response.rows;//给列表变量赋值
            }
        )
    }


});
