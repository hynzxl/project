app.controller("baseController",function ($scope) {
    $scope.selectedItems=[];//删除元素的id索引
    $scope.paginationConf = {
        currentPage: 1,  //当前页
        itemsPerPage: 5,     //每页大小
        totalItems: 100,      //总共条目数
        perPageOptions: [5, 10, 15, 20, 25, 30],//下拉菜单分页选项，每页多少条
        onChange: function () {          //此事件会自动触发，窗体加载完毕就会自动执行
            // 分页查询
           //$scope.findByPage();
            //findByPage();
            //$scope.search($scope.searchEntity,$scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
            $scope.searchByCondition();
            //$scope.findAll();
        }
    }

    //对于批量删除时，选择删除条目的id
    $scope.selectItems = function (event,id) {
        if (event.target.checked){    //选中
            $scope.selectedItems.push(id);
        } else{                       //没选中，查找数组$scope.selectedItems是否有该项id
            var index = $scope.selectedItems.indexOf(id);
            if (index >= 0){          //有id，则删除
                $scope.selectedItems.splice(index,1);
            }
        }
    }
})