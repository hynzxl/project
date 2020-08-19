app.service("goodsService",function ($http) {

    this.uploadFile=function () {
        var formData = new FormData();//异步传输表单对象
        formData.append("file",file.files[0]);//取到页面的第一个file
        return $http({
            method:'post',
            data:formData,
            url:"/uploadFile.do",
            headers:{'Content-Type':undefined}, //写了这一行即声明enctype类型一定是 multipart/form-data
            transformRequest: angular.identity//序列化上传文件
        })
    }

    this.updateGoods=function (entity) {
        return $http.post("/goods/updateGoods.do",entity);
    }

    this.addGoods=function (entity) {
        return $http.post("/goods/addGoods.do",entity);
    }

    this.findAll=function () {
        return $http.get("/goods/findAll.do");
    }
    this.searchByCondition=function (currentPage,pageSize,searchEntity) {
        return $http.post("/goods/searchByCondition.do?currentPage=" + currentPage + "&pageSize=" + pageSize,searchEntity);
    }
    this.deleteGoods=function (selectedItems) {
        return $http.post("/goods/deleteGoods.do",selectedItems);
    }
    this.review=function (auditStatus,selectedItems) {
        return $http.post("/goods/review.do?auditStatus=" + auditStatus,selectedItems);
    }
    this.findOne=function (id) {
        return $http.get("/goods/findOne.do?id=" + id);
    }
    this.superMarket=function (id,market) {
        return $http.get("/goods/superMarket.do?id=" + id + "&market=" + market);
    }
})