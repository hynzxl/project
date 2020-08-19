app.controller("goodsController",function ($scope,goodsService,$controller,brandService,itemCatService,typeTemplateService,$location) {

    // 1.实现对basedController的继承
    $controller("baseController",{$scope:$scope})


    //变量
    $scope.brandList={};//品牌
    $scope.entity = {"goods":{},"goodsDesc":{"itemImages":[],"customAttributeItems":[],"specificationItems":[]},"items":[]};//对应新建和修改商品的实体
    $scope.entityImage={}; //上传的图片对象，包含color和url两个参数
    $scope.firstItemCat={};//一级分类
    $scope.secondItemCat={};//二级分类
    $scope.thirdItemCat={};//三级分类
    $scope.typeTemplate={};//存放模板
    $scope.specificationDetail={};//详细的模板中的规格选项
    $scope.enable=false;//判断是否显示规格列表的变量
    $scope.list={};//存放显示goods信息
    $scope.status=["未申请","申请中","审核通过","已驳回"]
    $scope.itemCatList=[];//存放所有分类的数组，id和name相互绑定
    $scope.searchEntity={};//查询的条件
    $scope.id;//修改商品时传入的goodsId

    //商品上架
    $scope.superMarket=function(id,market){
        goodsService.superMarket(id,market).success(function (response) {
            alert(response.message);
            $scope.searchByCondition();
        })
    }

    //根据修改的id查询出修改的商品
    $scope.findOne=function(){
        $scope.id = $location.search()["id"];
        goodsService.findOne($scope.id).success(function (response) {
            $scope.entity = response;
            //设置富文本编辑器的内容
            editor.html($scope.entity.goodsDesc.introduction);
            //转换图片为json对象
            $scope.entity.goodsDesc.itemImages=JSON.parse($scope.entity.goodsDesc.itemImages);
            //转换扩展属性
            $scope.entity.goodsDesc.customAttributeItems=JSON.parse($scope.entity.goodsDesc.customAttributeItems);
            //转换规格列表
            $scope.entity.goodsDesc.specificationItems=JSON.parse($scope.entity.goodsDesc.specificationItems);
            //转换item.spec为json对象
            var items = $scope.entity.items;
            for(var i=0;i<items.length;i++){
                items[i].spec =JSON.parse(items[i].spec);
            }
        })
    }

    //判断规格选项是否勾选
    $scope.isUse=function(specIdText,optionName){
        var object = $scope.findListIsExist($scope.entity.goodsDesc.specificationItems,"attributeName",specIdText);
        if (object){
            if (object.attributeValue.indexOf(optionName)>=0) {
                return true;
            }
            return false;
        }
        return false;
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

    //条件查询带分页
    $scope.searchByCondition=function(){
        goodsService.searchByCondition($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage,$scope.searchEntity).success(function (response) {
            $scope.list=response.rows;
            $scope.paginationConf.totalItems=response.total;
        })
    }
    //新建商品
    $scope.addNewGoods=function(){
        window.location.href="goods_edit.html";
    }
    //删除选中商品
    $scope.deleteGoods=function(){
        goodsService.deleteGoods($scope.selectedItems).success(function (response) {
            if (response.come==1) {
                alert(response.message + "一共删除了" + response.items + "条记录");
                $scope.selectedItems=[];
                $scope.searchByCondition();

            }else{
                alert("删除失败！")
            }
        })
    }

    //提交审核
    $scope.review=function(auditStatus){
        goodsService.review(auditStatus,$scope.selectedItems).success(function (response) {
            if (response.come==1) {
                alert(response.message + "一共提交了" + response.items + "条记录");
                $scope.selectedItems=[];
                $scope.searchByCondition();
            }else{
                alert("提交失败！")
            }
        })
    }

    //查询所有品牌
    $scope.findAllBrand=function () {
        brandService.findAll().success(function (response) {
            $scope.brandList=response.rows;
        })
    }

    //保存
    $scope.save=function(){
        $scope.entity.goodsDesc.introduction = editor.html(); //保存的时候，将富文本框的内容赋值给$scope.entity.goodsDesc.introduction（对应TbGoodsDesc表的introduction）
        if ($scope.entity.goods.id) {
            goodsService.updateGoods($scope.entity).success(function (response) {
                alert(response.message + "更新了" + response.items + "条数据")
            })
        }else {
            goodsService.addGoods($scope.entity).success(function (response) {
                alert(response.message + "更新了" + response.items + "条数据")
            })
        }
        //清空选项
        $scope.entity = {"goods":{},"goodsDesc":{"itemImages":[],"customAttributeItems":[],"specificationItems":[]},"items":[]};
        editor.html("");//清空富文本编辑
    }
    
    //上传文件
    $scope.uploadFile=function () {
        goodsService.uploadFile().success(function (response) {
            if (response.come==1) {
                $scope.entityImage.url=response.message; //最好返回对象，不然在图片src的属性中会有双引号，导致显示不出来
            }else{
                alert(response.message)
            }
            console.log($scope.entityImage.url);
        })
    }

    //保存图片
    $scope.saveImage=function () {
        $scope.entity.goodsDesc.itemImages.push($scope.entityImage);
        $scope.entityImage={};
    }

    //删除图片项entityImage
    $scope.deleteItemImage=function (index) {
        $scope.entity.goodsDesc.itemImages.splice(index,1);
    }

    //查询一级分类
    $scope.findFirstItemCat=function () {
        itemCatService.findItemCatByParentId(0).success(function (response) {
            $scope.firstItemCat=response.rows;
        })
    }
    //监听一级分类，发生改变，对于二级分类就相应发生改变
    $scope.$watch("entity.goods.category1Id",function (newValue,oldValue) {
        if (newValue) {
            itemCatService.findItemCatByParentId(newValue).success(function (response) {
                $scope.secondItemCat = response.rows;
            })
        }

    })

    //监听二级分类，发生改变，对于三级分类就相应发生改变
    $scope.$watch("entity.goods.category2Id",function (newValue,oldValue) {
        if (newValue){
            itemCatService.findItemCatByParentId(newValue).success(function (response) {
                $scope.thirdItemCat = response.rows;
            })
        }

    })
    //监听三级分类，发生改变，查出相应三级分类
    $scope.$watch("entity.goods.category3Id",function (newValue,oldValue) {
        if (newValue){
            itemCatService.findOne(newValue).success(function (response) {
                $scope.itemCat3=response;
                typeTemplateService.findOne($scope.itemCat3.typeId).success(function (response) {
                $scope.typeTemplate = response;  //通过三级分类的模板id查到模板的信息
               })
            })
        }
    })

    //监听模板id，当模板Id发生变化的时候，查询出相应的分类
    $scope.$watch("typeTemplate.id",function(newValue,oldValue) {
        if (newValue) {
            $scope.brandList = JSON.parse($scope.typeTemplate.brandIds); //将模板中的品牌转换为Json对象，赋值给品牌,在前台遍历
            if (!$scope.id){    //当商品的id不存在时，才进行自定义属性的查找
               $scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems);//将模板的自定义属性赋值给实体的自定义属性
            }

            typeTemplateService.findSpecificationDetail(newValue).success(function (response) {     //根据模板id查询到具体的规格选项
                $scope.specificationDetail = response;
            })


        }
    })

    //判断一个数组中是否存在某一项，若存在则返回
    $scope.findListIsExist=function(list,name,value){
        for(var i = 0;i<list.length;i++){
            if (list[i][name]==value) {  //存在该项，返回
                return list[i];
            }
        }
        return null;           //不存在，返回空
    }


    //选择复选框时，更新$scope.entity.goodsDesc.specificationItems的值
    $scope.updateAttributeValue=function (event,attributeName,attributeValue) {
        //1.先判断该规格的项在$scope.specNameAndValue时候存在，存在就将其取出来，操作
       var specItem = $scope.findListIsExist($scope.entity.goodsDesc.specificationItems,"attributeName",attributeName);
       if (specItem != null) {                    //该数组中存在attributeName的一个对象
        //2.放值
        // 如果该复选框被选中，就将该复选框的值添加到specItem的attributeValue属性值中
           if (event.target.checked) {
               specItem.attributeValue.push(attributeValue);
           }else{
               var index = specItem.attributeValue.indexOf(attributeValue);
               if (index>=0){
                   specItem.attributeValue.splice(index,1);  //没选中就删除
               }
               if (specItem.attributeValue.length==0) {     //一个都没有时，就将该specItem清空
                   $scope.entity.goodsDesc.specificationItems.splice($scope.entity.goodsDesc.specificationItems.indexOf(attributeName),1);
               }
           }
       }else{               //不存在，则添加一个规格对象
           if (event.target.checked) {
               $scope.entity.goodsDesc.specificationItems.push({"attributeName":attributeName,"attributeValue":[attributeValue]}); //该对象的attributeValue值设置为选中的值
           }
       }
        $scope.createItemList();
    }

    //动态生成sku表格
    $scope.createItemList=function () {
        $scope.entity.items=[{spec:{},price:100,num:200,status:0,isDefault:0}];  //初始化$scope.entity.items对象
        //获取规格列表
        var itemList = $scope.entity.goodsDesc.specificationItems;
        //遍历itemList
        for (var i = 0;i<itemList.length;i++){
            $scope.entity.items = $scope.addColumn($scope.entity.items,itemList[i].attributeName,itemList[i].attributeValue);
        }


    }
    $scope.addColumn=function (list,attributeName,attributeValue) {
        var newLists = [];
        for (var i = 0;i<list.length;i++){
            var oldRow = list[i];
            for (var j=0;j<attributeValue.length;j++){
                var newRows = JSON.parse(JSON.stringify(oldRow));  //得到新行，深克隆
                newRows.spec[attributeName] = attributeValue[j];   //为新行的spec属性，对应数据库的spec项赋值
                newLists.push(newRows);//放入新的集合中
            }
        }
        return newLists;//返回该集合
    }

    //是否启用规格
    $scope.isUseSpecification=function (event) {
        if (event.target.checked){
            $scope.enable=true;
        } else{
            $scope.enable=false;
        }
    }

    //查询所有的goods
    $scope.findAll=function () {
        goodsService.findAll().success(function (response) {
            $scope.list=response.rows;
        })
    }

})