//前端服务层
//品牌服务层
app.service('brandService',function($http){

    //读取列表数据绑定到表单中
    this.findAll=function(){
        return $http.get('../brand/findAll.do');
    }

    //分页
    this.findPage=function (page, rows) {
        return $http.get('../brand/findPage.do?page='+page+'&rows='+rows);
    }

    //增加
    this.add=function(entity){
        return  $http.post('../brand/add.do',entity );
    }

    //查找实体
    this.findOne=function (id) {
        return $http.post('../brand/findOne.do?id='+id);
    }

    //修改品牌
    this.update=function (entity) {
        return  $http.post('../brand/update.do',entity );
    }

    //删除品牌
    this.delete=function (selectIds) {
        return  $http.post('../brand/delete.do?ids='+selectIds);
    }

    //查找品牌
    this.findPageSearch=function (page,rows) {
        return $http.post('../brand/findPageSearch.do?page='+page+"&rows="+rows,searchEntity);
    }

})