app.service("specificationService",function ($http) {

    this.searchByCondition=function (currentPage,pageSize,specificationName) {
        return $http.get("/specification/searchByCondition.do?currentPage=" + currentPage + "&pageSize=" + pageSize + "&specificationName=" + specificationName);
    }

    //添加
    this.add=function (specificationOption) {
        return $http.post("/specification/addSpecification.do",specificationOption);
    }

    //根据id查找规格
    this.findOne=function (specId) {
        return $http.get("/specification/findOne.do?specId=" + specId);
    }

    //修改
    this.update = function (specificationOption) {
        return $http.post("/specification/updateSpecification.do",specificationOption);
    }

    //删除
    this.dele=function (selectedItems) {
        return $http.post("/specification/delete.do",selectedItems);
    }
    //查询所有规格为map，对应select2
    this.findAllSpecificationToMap=function () {
        return $http.get("/specification/findAllSpecificationToMap.do");
    }
})