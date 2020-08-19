app.controller("specificationController",function ($scope,specificationService,$controller) {
    // 1.实现对basedController的继承
    $controller("baseController",{$scope:$scope})

    //变量
    $scope.list={};
    $scope.specificationName="";
    //新建规格存放的对象，对应实体类specificationOption，json形式为{"specification":"{"id":2,"name":"尺码"}","speOptionS":[{"id":23,"optionName":"长度","speId":2,"":""}}]}
    $scope.specificationOption={"specification":"","speOptions":[{}]};
    //分页查询带条件
    $scope.searchByCondition = function () {
        specificationService.searchByCondition($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage,$scope.specificationName).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    }
    
    //点击新增规格选项时
    $scope.addOptions=function () {
        $scope.specificationOption.speOptions.push({});
    }
    
    //点击删除规格选项时
    $scope.deleteOptions=function (index) {
        $scope.specificationOption.speOptions.splice(index,1);
    }

    //点击保存规格时
    $scope.save=function () {
        if ($scope.specificationOption.specification.id) {   //spec_id存在，则是修改
            specificationService.update($scope.specificationOption).success(function (response) {
                alert(response.message);
                $scope.searchByCondition();
            })
        }else{                                                //spec_id不存在，则是添加
            specificationService.add($scope.specificationOption).success(function (response) {
                alert(response.message);
                $scope.searchByCondition();
            })
        }

    }

    //点击修改按钮
    $scope.modify=function (specId) {
        //1.先根据Id找到该规格选项
        $scope.findOne(specId);
    }

    //根据id查找该规格以及规格的选项
    $scope.findOne=function (specId) {
        specificationService.findOne(specId).success(function (response) {
            $scope.specificationOption = response;
        })
    }

    //删除按钮方法
    $scope.dele=function () {
        specificationService.dele($scope.selectedItems).success(function (response) {
            alert(response.message + "一共删除了" + response.items + "条记录");
            $scope.searchByCondition();
            $scope.selectedItems=[];
        })
    }

})