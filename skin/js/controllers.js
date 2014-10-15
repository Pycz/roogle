var app = angular.module('app', ['mm.foundation']);

app.controller('MainCtrl', function($scope, $http) {

    $scope.search_items = [
        {
            id: 1,
            anons_text: "Showen 1",
            hidden_text: "Was hidden 1",
            is_hidden: true
        },
        // {
        //     id: 2,
        //     anons_text: "Showen 2",
        //     hidden_text: "Was hidden 2",
        //     is_hidden: true
        // },
        // {
        //     id: 3,
        //     anons_text: "Showen 3",
        //     hidden_text: "Was hidden 3",
        //     is_hidden: true
        // },
        {
            id: 4,
            anons_text: "Showen 4",
            hidden_text: "Was hidden 4",
            is_hidden: true
        }
    ];

    $scope.hideElem = function(id, hide){ // hide - true -> hide it! false -> show it!
        //debugger;
        console.log($scope.search_items[0].is_hidden);
        for(var i = 0; i < $scope.search_items.length; i++){
            if($scope.search_items[i].id == id){
                if(hide){
                    $scope.search_items[i].is_hidden = true;
                }
                else{
                    $scope.search_items[i].is_hidden = false;
                }
                break;
            }
        }
        console.log($scope.search_items[0].is_hidden);
    };
});
