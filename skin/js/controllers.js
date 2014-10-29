(function (angular) {

var ctrls = angular.module('app.controllers', [
    'mm.foundation'
]);

ctrls.controller('MainCtrl', function($scope, $http) {
    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
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

    $scope.results = [
        {
            "doc": " Creates a new synchronous, bounded channel.",
            "inner": {
                "kind": "function",
                "value": {
                    "decl": {
                        "inputs": [
                            {
                                "name": "bound",
                                "type_": {
                                    "kind": "Primitive",
                                    "value": "uint"
                                }
                            }
                        ],
                        "output": {
                            "kind": "Tuple",
                            "value": [
                                {
                                    "kind": "ResolvedPath",
                                    "value": {
                                        "global": false,
                                        "segments": [
                                            {
                                                "lifetimes": [],
                                                "name": "SyncSender",
                                                "types": [
                                                    {
                                                        "kind": "Generic",
                                                        "value": "T"
                                                    }
                                                ]
                                            }
                                        ]
                                    }
                                },
                                {
                                    "kind": "ResolvedPath",
                                    "value": {
                                        "global": false,
                                        "segments": [
                                            {
                                                "lifetimes": [],
                                                "name": "Receiver",
                                                "types": [
                                                    {
                                                        "kind": "Generic",
                                                        "value": "T"
                                                    }
                                                ]
                                            }
                                        ]
                                    }
                                }
                            ]
                        }
                    },
                    "generics": {
                        "lifetimes": [],
                        "type_params": [
                            {
                                "bounds": [
                                    {
                                        "kind": "ResolvedPath",
                                        "value": {
                                            "global": false,
                                            "segments": [
                                                {
                                                    "lifetimes": [],
                                                    "name": "Send",
                                                    "types": []
                                                }
                                            ]
                                        }
                                    }
                                ],
                                "name": "T"
                            }
                        ]
                    }
                }
            },
            "module": [
                "std",
                "comm"
            ],
            "name": "sync_channel",
            "url": "http://doc.rust-lang.org/std/comm/fn.sync_channel.html"
        },
        {
            "doc": " Converts from `u32` to a `char`",
            "inner": {
                "kind": "function",
                "value": {
                    "decl": {
                        "inputs": [
                            {
                                "name": "i",
                                "type_": {
                                    "kind": "Primitive",
                                    "value": "u32"
                                }
                            }
                        ],
                        "output": {
                            "kind": "ResolvedPath",
                            "value": {
                                "global": false,
                                "segments": [
                                    {
                                        "lifetimes": [],
                                        "name": "Option",
                                        "types": [
                                            {
                                                "kind": "Primitive",
                                                "value": "char"
                                            }
                                        ]
                                    }
                                ]
                            }
                        }
                    },
                    "generics": {
                        "lifetimes": [],
                        "type_params": []
                    }
                }
            },
            "module": [
                "std",
                "char"
            ],
            "name": "from_u32",
            "url": "http://doc.rust-lang.org/std/char/fn.from_u32.html"
        },
        {
            "doc": "",
            "inner": {
                "kind": "function",
                "value": {
                    "decl": {
                        "inputs": [
                            {
                                "name": "c",
                                "type_": {
                                    "kind": "Primitive",
                                    "value": "char"
                                }
                            },
                            {
                                "name": "f",
                                "type_": {
                                    "kind": "Closure",
                                    "value": {
                                        "bounds": [],
                                        "decl": {
                                            "inputs": [
                                                {
                                                    "name": "",
                                                    "type_": {
                                                        "kind": "Primitive",
                                                        "value": "char"
                                                    }
                                                }
                                            ],
                                            "output": {
                                                "kind": "Primitive",
                                                "value": "()"
                                            }
                                        },
                                        "lifetimes": []
                                    }
                                }
                            }
                        ],
                        "output": {
                            "kind": "Primitive",
                            "value": "()"
                        }
                    },
                    "generics": {
                        "lifetimes": [],
                        "type_params": []
                    }
                }
            },
            "module": [
                "std",
                "char"
            ],
            "name": "escape_unicode",
            "url": "http://doc.rust-lang.org/std/char/fn.escape_unicode.html"
        }
    ];

    $scope.getResults = function(){
        return $http.post('/php_exec.php', {
                "search": $scope.search,
            })
            .then(function(res){
                $scope.results = res.data;
                return res.data;
            });
    };

    $scope.hideElem = function(id, hide){ // hide - true -> hide it! false -> show it!

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
    };
});
}(angular));
