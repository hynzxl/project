app.service("shopService",function ($http) {
    this.searchByCondition=function (currentPage,pageSize,searchShop) {
        return $http.post("/shop/searchByCondition.do?currentPage=" + currentPage + "&pageSize=" + pageSize,searchShop);
    }
    this.review=function (sellerId,statu) {
        return $http.get("/shop/review.do?statu=" + statu + "&sellerId=" + sellerId);
    }
})