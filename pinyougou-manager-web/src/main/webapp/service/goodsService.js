app.service("goodsService",function ($http) {

    //查询全部商品
    this.findAll = function () {
        return $http.get("/goods/findAll.do");
    }

    //条件查询带分页
    this.searchByCondition = function (currentPage,itemsPerPage,seachEntity) {
        return $http.post("/goods/searchByCondition.do?currentPage=" + currentPage + "&pageSize=" + itemsPerPage,seachEntity);
    }

    this.deleteGoods=function (selectedItems) {
        return $http.post("/goods/deleteGoods.do",selectedItems);
    }

    this.review=function (auditStatus,selectedItems) {
        return $http.post("/goods/review.do?auditStatus=" + auditStatus,selectedItems);
    }
})