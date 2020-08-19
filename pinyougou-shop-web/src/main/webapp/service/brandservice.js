app.service("brandService",function ($http) {

    //查询全部品牌
    this.findAll = function () {
        return $http.get("/brand/list.do");
    }
    //查询全部品牌带分页
    this.findByPage = function (currentPage,pageSize) {
        return $http.get("/brand/findByPage.do?currentPage=" + currentPage + "&pageSize=" + pageSize);
    }

    //新增或者修改
    this.saveBrand = function (url,brand) {
        return $http.post(url,brand);
    }

    //删除
    this.dele = function(selectedItems){
      return  $http.post("/brand/deleteBrand.do",selectedItems);
    }
    //条件查询带分页
    this.searchByCondition = function (currentPage,itemsPerPage,seachEntity) {
        return $http.post("/brand/search.do?currentPage=" + currentPage + "&pageSize=" + itemsPerPage,seachEntity);
    }

    //查询全部品牌，转为id,text类型的map
    this.findAllBrandToMap=function () {
        return $http.get("/brand/findAllBrandToMap.do");
    }
})