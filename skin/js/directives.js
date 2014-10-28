(function (angular) {

var dirs = angular.module('app.directives', [
    'app.services'
]);


dirs.directive('searchItem', [function() {
    return {
        restrict: 'EA',
        scope: {
            item: '='
        },
        template: [
            '<div class="result_search_item">',
            '<div class="what_we_search">',
            '<fn-signature item="item"></fn-signature>',
            '</div>',
            '<div class="result_razdel" ng-bind="modulePath(item)"></div>',
            '<div class="result_main_anons_block">',
            '<div class="result_anons" ng-bind="item.doc"></div>',
            '</div></div>'
        ].join(''),

        controller: ['$scope', function($scope) {
            $scope.modulePath = function(item) {
                var modules = item.module.join("::");
                return modules + "::" + item.name;
            };
        }]
    };
}]);


dirs.directive('fnSignature', [function() {
    return {
        restrict: 'EA',
        scope: {
            item: '='
        },
        template: [
            'fn <a ng-href="{{ item.url }}" ng-bind="item.name"></a>',
            '<span ng-bind="generics(item)"></span>',
            '(<span ng-bind="inputs(item)"></span>) ',
            '<span ng-if="!isUnit(outType)">',
            ' -> <span ng-bind="outType"></span>',
            '</span>'
        ].join(''),

        controller: [
            '$scope', 'RType', 'RGenerics', 'RArguments', 'isUnit', 'underscore',
            function($scope, RType, RGenerics, RArguments, isUnit, _) {
                $scope.generics = generics;
                $scope.inputs = inputs;
                $scope.output = output;
                $scope.isUnit = isUnit;

                function generics(item) {
                    var gens = RGenerics.toStr(item.inner.value.generics);
                    if (_.isEmpty(gens)) {
                        return '';
                    }
                    return '<' + gens + '>';
                };

                function inputs(item) {
                    var ins = item.inner.value.decl.inputs;
                    return RArguments.toStr(ins);
                };

                 function output(item) {
                    var out = item.inner.value.decl.output;
                    return RType.toStr(out);
                };
            }
        ],
        link: function (scope) {
            scope.outType = scope.output(scope.item);
        }
    };
}]);

}(angular));
