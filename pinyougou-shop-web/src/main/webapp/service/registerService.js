app.service("registerService",function ($http) {
    this.registerSeller=function (register) {
        return $http.post("/seller/registerSeller.do",register);
    }
    this.findSeller=function () {
        return $http.get("/seller/findSeller.do");
    }
})