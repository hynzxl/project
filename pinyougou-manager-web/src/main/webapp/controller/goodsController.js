app.controller("goodsController",function ($scope,goodsService,$controller,itemCatService) {
    // 1.实现对basedController的继承
    $controller("baseController",{$scope:$scope})

    //变量
    $scope.list={};
    $scope.brand={};
    $scope.searchEntity={};//用于查询商品的实体对象
    $scope.status=["未提交","待审核","审核通过","已驳回"];//显示商品状态的数组
    $scope.itemCatList=[];//存放所有分类的数组，id和name相互绑定
    //查找全部品牌
    $scope.findAll = function () {
        goodsService.findAll().success(function (response) {
            $scope.list = response.rows;
        })
    }

    //条件查询带分页
    $scope.searchByCondition = function () {
        goodsService.searchByCondition($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage,$scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    }

    //查询所有分类
    $scope.findAllItemCat=function(){
        itemCatService.findAllItemCat().success(function (response) {
            var itemlists = response.rows;
            for (var i=0;i<itemlists.length;i++) {
                var id = itemlists[i].id;
                $scope.itemCatList[id]=itemlists[i].name;
            }
        })
    }

    //删除商品
    $scope.deleteGoods = function () {
        goodsService.deleteGoods($scope.selectedItems).success(function (response) {
            alert(response.message + "!一共删除了" + response.items + "条数据");
            $scope.selectedItems=[];
            $scope.searchByCondition();
        })
    }

    //审核商品
    $scope.review=function (auditStatus) {
        goodsService.review(auditStatus,$scope.selectedItems).success(function (response) {

            if (response.come==1){
                alert(response.message + "!一共审核了" + response.items + "条数据");
                $scope.selectedItems=[];
                $scope.searchByCondition();
            } else {
                alert(response.message);
                $scope.selectedItems=[];
                $scope.searchByCondition();
            }
        })
    }


})