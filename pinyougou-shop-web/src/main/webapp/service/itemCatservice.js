app.service("itemCatService",function ($http) {

    //查询全部分类
    this.findAll = function (grade) {
        return $http.get("/itemCat/list.do?grade=" + grade);
    }
    //条件查询分类带分页
    this.searchByCondition = function (currentPage,itemsPerPage,seachEntity) {
        return $http.post("/itemCat/searchByCondition.do?currentPage=" + currentPage + "&pageSize=" + itemsPerPage,seachEntity);
    }

    this.save=function (url,itemCat) {
        return $http.post(url,itemCat);
    }
    this.dele=function(selectItems){
        return $http.post("/itemCat/deleteItemCat.do",selectItems);
    }
    this.findItemCatByParentId=function (parentId) {
        return $http.get("/itemCat/findItemCatByParentId.do?parentId=" + parentId);
    }
    this.findOne=function (id) {
        return $http.get("/itemCat/findOne.do?id=" +id);
    }
    this.findAllItemCat=function () {
        return $http.get("/itemCat/findAllItemCat.do");
    }

})