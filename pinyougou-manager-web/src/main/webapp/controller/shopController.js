app.controller("shopController",function ($scope,$controller,shopService) {
    // 1.实现对basedController的继承
    $controller("baseController",{$scope:$scope})

    //变量
    $scope.list={}
    $scope.searchShop={"status":"0"}
    $scope.detailShop={}

    //查询商家列表
    $scope.searchByCondition=function () {
        shopService.searchByCondition($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage,$scope.searchShop).success(function (response) {
            $scope.list=response.rows;
            $scope.paginationConf.totalItems=response.total;
        })
    }
    //详情
    $scope.showDetail=function (shop) {
        $scope.detailShop=shop;
    }
    //审核
    $scope.review=function (sellerId,statu) {
        shopService.review(sellerId,statu).success(function (response) {
            alert(response.message);
            $scope.searchByCondition();
        })
    }


})