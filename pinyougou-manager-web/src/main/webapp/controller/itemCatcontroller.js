app.controller("itemCatController",function ($scope,itemCatService,$controller,typeTemplateService) {
    // 1.实现对basedController的继承
    $controller("baseController",{$scope:$scope})
    $scope.list={};
    $scope.itemCat={}; //新建或者修改itemCat存放变量
    $scope.grade=1;  //判断第几级的标志,初始为第一级，为1
    $scope.searchEntity={};//搜索时存放的变量
    $scope.itemCat_1={};//存放一级分类
    $scope.itemCat_2={};//存放二级分类
    $scope.itemCat_3={};//存放三级分类
    $scope.template={};//模板存放

    //查找全部分类
    $scope.findAll = function (grade) {
        itemCatService.findAll(grade).success(function (response) {
            $scope.list = response.rows;
        })
    }

    //查询下一级
    $scope.findNext=function(itemCat){
        $scope.searchEntity={};
        $scope.paginationConf.currentPage=1;
        switch ($scope.grade) {
            case 1:                  //一级时，传进来的itemCat是一级分类，这时将二级，三级分类清空
                $scope.itemCat_1=itemCat;
                $scope.itemCat_2={};
                $scope.itemCat_3={};
                break;
            case 2:                  //二级时，传进来的itemCat是二级分类，这时将三级分类清空
                $scope.itemCat_2=itemCat;
                $scope.itemCat_3={};
                break;
            case 3:                  //三级时，传进来的itemCat是三级分类
                $scope.itemCat_3=itemCat;
                break;
        }
        $scope.grade +=1;            //下一级grade+1
        $scope.searchByCondition();  //通过该级分类的id，查询下一级分类
    }
    //查询上一级
    $scope.findBefore=function(){
        $scope.paginationConf.currentPage=1;
        $scope.grade -=1;              //等级减一，当前是二级分类，上一级就是一级分类，三级分类，上一级就是二级分类
        switch ($scope.grade) {
            case 1:                   //一级分类，清空所有
                $scope.itemCat_1={};
                $scope.itemCat_2={};
                $scope.itemCat_3={};
                break;
            case 2:                   //二级分类，清空二级和三级
                $scope.itemCat_2={};
                $scope.itemCat_3={};
                break;
            case 3:                   //三级分类，清空三级
                $scope.itemCat_3={};
                break;
        }
        $scope.searchByCondition();  //通过该级分类的id，查询下一级分类
    }

    //点击顶级分类面包屑
    $scope.zero=function(){
        $scope.paginationConf.currentPage=1;
        $scope.grade = 1;       //等级标志设置为1
        $scope.searchByCondition();
        //清空二级和三级分类
        $scope.itemCat_1={};
        $scope.itemCat_2={};
        $scope.itemCat_3={};
    }
    $scope.one=function(){
        $scope.paginationConf.currentPage=1;
        $scope.grade = 2;       //等级标志设置为2,显示的是二级目录
        $scope.searchByCondition();
        $scope.itemCat_2={};
        $scope.itemCat_3={};
    }

    //点击二级分类面包屑id
    $scope.two=function(){
        $scope.paginationConf.currentPage=1;
        $scope.grade = 3;                   //等级标志设置为3
        $scope.searchByCondition();  //通过二级分类的Id查询
        $scope.itemCat_2={};
        $scope.itemCat_3={};                //三级分类清空
    }

    //条件查询带分页
    $scope.searchByCondition = function () {
        switch ($scope.grade) {
            case 1:
                $scope.searchEntity.parentId = 0;  //一级目录查询，则parentId等于0；
                break;
            case 2:
                $scope.searchEntity.parentId = $scope.itemCat_1.id;  //二级目录查询，则parentId等于一级目录id；
                break;
            case 3:
                $scope.searchEntity.parentId = $scope.itemCat_2.id; //三级目录查询，则parentId二级目录id；
                break;
        }
        itemCatService.searchByCondition($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage,$scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    }
    //点击新建按钮
    $scope.add = function () {
        $scope.itemCat={};
        typeTemplateService.findAll().success(function (response) {//先查询全部的模板
            $scope.template = response.rows;
        });
    }


    //保存按钮
    $scope.save = function () {
        //判断$scope.itemCat.id是否存在，存在则是修改操作，不存在则是添加操作
        var url = "/itemCat/addItemCat.do";
        //根据grade的值判断处于什么级别
        switch ($scope.grade) {
            case 1:                  //一级时，设置$scope.itemCat的parentId=0
                $scope.itemCat.parentId=0;
                break;
            case 2:                  //二级时，设置$scope.itemCat的parentId为一级$scope.itemCat_1的id
                $scope.itemCat.parentId=$scope.itemCat_1.id;
                break;
            case 3:                  //三级时，设置$scope.itemCat的parentId为一级$scope.itemCat_2的id
                $scope.itemCat.parentId=$scope.itemCat_2.id;
                break;
        }
        if ($scope.itemCat.id) {
            url = "/itemCat/updateItemCat.do";
        }
        itemCatService.save(url,$scope.itemCat).success(function (response) {
            alert(response.message + "更新了" + response.items + "条数据！");
            $scope.searchByCondition();
            alert($scop.grade)
        })
    }

    //修改点击修改按钮
    $scope.update = function (itemCat) {
        typeTemplateService.findAll().success(function (response) {//先查询全部的模板
            $scope.template = response.rows;
        });
        $scope.itemCat = itemCat;
    }
    //删除类别
    $scope.dele = function () {
        itemCatService.dele($scope.selectedItems).success(function (response) {
            alert(response.message + "!一共删除了" + response.items + "条数据");
            $scope.selectedItems=[];
            $scope.searchByCondition();
        })
    }


})