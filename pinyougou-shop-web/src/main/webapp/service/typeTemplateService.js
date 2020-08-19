app.service("typeTemplateService",function ($http) {
    this.findAll = function () {
        return $http.get("/typeTemplate/list.do");
    }

    //分页查询所有
    this.findByPage=function (currentPage,pageSize) {
        return $http.get("/typeTemplate/findByPage.do?currentPage=" + currentPage + "&pageSize=" + pageSize);
    }

    //分页查询带条件
    this.searchByCondition=function (currentPage,pageSize,condition) {
        return $http.get("/typeTemplate/searchByCondition.do?currentPage=" + currentPage + "&pageSize=" + pageSize + "&condition=" + condition);
    }

    //保存新增模板
    this.addTypeTemplate=function (typeTemplate) {
        return $http.post("/typeTemplate/addTypeTemplate.do",typeTemplate);
    }

    //修改模板
    this.updateTypeTemplate=function (typeTemplate) {
        return $http.post("/typeTemplate/updateTypeTemplate.do",typeTemplate);
    }

    //批量删除
    this.deleTemplate=function (items) {
        return $http.post("/typeTemplate/deleTemplate.do",items);
    }

    this.findOne=function (id) {
        return $http.get("/typeTemplate/findOne.do?id=" + id);
    }
    this.findSpecificationDetail=function (typeId) {
        return $http.get("/typeTemplate/findSpecificationDetail.do?id=" + typeId);
    }
})