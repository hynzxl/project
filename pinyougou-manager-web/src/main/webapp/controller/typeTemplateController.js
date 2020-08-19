app.controller("typeTemplateController",function ($scope,typeTemplateService,$controller,brandService,specificationService) {

    // 1.实现对basedController的继承
    $controller("baseController",{$scope:$scope})


    //变量
    $scope.list={};//遍历集合
    $scope.condition="";//查询条件
    $scope.brand={};//所有品牌
    $scope.specification = [];//所有规格
    $scope.typeTemplate={"id":null,"name":"","specIds":[{}],"brandIds":[{}],"customAttributeItems":[{}]}
    //2.查询全部模板
    $scope.findAll=function () {
        typeTemplateService.findAll().success(function (response) {
            $scope.list = response.rows;
        })
    }
    //3.分页查询所有
    $scope.findByPage=function () {
        typeTemplateService.findByPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    }

    //4.分页查询带条件
    $scope.searchByCondition=function () {
        typeTemplateService.searchByCondition($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage,$scope.condition).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    }

    //5.查询所有品牌以及所有的规格,并转换为JSON对象
    $scope.findAllBrandToMap=function(){

        brandService.findAllBrandToMap().success(function (response) {

            $scope.brand = {data:response};
        })
    }
    $scope.findAllSpecificationToMap=function () {
        specificationService.findAllSpecificationToMap().success(function (response) {
            $scope.specification = {data:response};
        })

    }

    //新增扩展属性
    $scope.addCustomAttributeItems=function () {
        $scope.typeTemplate.customAttributeItems.push({});
    }
    //删除扩展属性项
    $scope.deleItems=function (index) {
        $scope.typeTemplate.customAttributeItems.splice(index,1);
    }

    //保存新增模板
    $scope.save=function () {
        if ($scope.typeTemplate.id) {  //id存在，则是修改
            typeTemplateService.updateTypeTemplate($scope.typeTemplate).success(function (response) {
                alert(response.message);
                $scope.searchByCondition();
            })
        }else{           //不存在，则是新增
            typeTemplateService.addTypeTemplate($scope.typeTemplate).success(function (response) {
                alert(response.message + "新增了" + response.items + "条数据");
                $scope.searchByCondition();
            })
        }

    }
    //点击修改方法
    $scope.modifyTypeTemplate=function (type) {
        var brandIds = JSON.parse(type.brandIds);
        var specIds = JSON.parse(type.specIds);
        var customAttributeItems = JSON.parse(type.customAttributeItems);
        $scope.typeTemplate.id = type.id;
        $scope.typeTemplate.name = type.name;
        $scope.typeTemplate.specIds = specIds;
        $scope.typeTemplate.brandIds = brandIds;
        $scope.typeTemplate.customAttributeItems = customAttributeItems;
    }

    //新增选项时，清空所有模板编辑内容
    $scope.clear=function () {
        $scope.typeTemplate={"id":null,"name":"","specIds":[{}],"brandIds":[{}],"customAttributeItems":[{}]};
    }

    //批量删除模板
    $scope.deleTemplate=function () {
        typeTemplateService.deleTemplate($scope.selectedItems).success(function (response) {
            alert(response.message + "你已删除" + response.items + "条数据");
            $scope.selectedItems=[];
            $scope.searchByCondition();
        })
    }

    $scope.jsonToString=function (jsonStr) {
        var json = JSON.parse(jsonStr);
        var str = "";
        for (var i=0;i<json.length;i++) {
            if (i == json.length - 1) {
                str +=json[i]["text"];
            }else{
                str +=json[i]["text"]+"，";
            }
        }

        return str;
    }

})