app.controller("brandController",function ($scope,brandService,$controller) {
    // 1.实现对basedController的继承
    $controller("baseController",{$scope:$scope})
    $scope.list={};
    $scope.brand={};

    $scope.searchEntity={};
    //查找全部品牌
    $scope.findAll = function () {
        brandService.findAll().success(function (response) {
            $scope.list = response.rows;
        })
    }
    //分页查询全部品牌
    $scope.findByPage=function () {
        brandService.findByPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    }

    //修改点击修改按钮
    $scope.update = function (brand) {
        $scope.brand = brand;
    }

    //新增按钮
    $scope.add = function () {
        $scope.brand = {};
    }

    //添加或者更新品牌
    $scope.save = function (brand) {
        //判断brand.id是否存在，存在则是修改操作，不存在则是添加操作
        var url = "/brand/addBrand.do";
        if (brand.id) {
            url = "/brand/updateBrand.do";
        }
        brandService.saveBrand(url,brand).success(function (response) {
            alert(response.message + "，更新了" + response.items + "条数据！");
            $scope.findByPage();
        })
    }

    //判断所选择元素是否在$scope.selectedItems中，若在，则checked打上勾
    // $scope.isInSelectedItems = function (id) {
    //     alert(id);
    //     var index = $scope.selectedItems.indexOf(id);
    //     if (index >= 0){
    //         $scope.isin = true;
    //     }
    // }

    //删除品牌
    $scope.dele = function (selectItems) {
        brandService.dele(selectItems).success(function (response) {
            alert(response.message + "!一共删除了" + response.items + "条数据");
            $scope.selectedItems=[];
            $scope.findByPage();
        })
    }

    //条件查询带分页
    $scope.searchByCondition = function () {
        brandService.searchByCondition($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage,$scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    }
})