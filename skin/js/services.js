(function(angular, _) {
  
var services = angular.module('app.services', []);

services.constant('underscore', _);


services.factory('RType', [
    'RBareFunctionDecl', 'RBorrowedRef', 'RRawPointer', 'RVector', 'RTuple',
    'RProcDecl', 'RClosureDecl', 'RPath', 'underscore',
    function (RBareFunctionDecl, RBorrowedRef, RRawPointer, RVector, RTuple,
              RProcDecl, RClosureDecl, RPath, _) {
        return {
            toStr: function(type) {
                var val = type.value;

                if (type.kind == 'TyParamBinder' || type.kind == 'Generic' ||
                    type.kind == 'Self' || type.kind == 'Primitive' ||
                    type.kind == 'Bottom') {
                    return val;
                } else if (type.kind == 'ResolvedPath') {
                    return RPath.toStr(val);
                } else if (type.kind == 'Closure') {
                    return RClosureDecl.toStr(val);
                } else if (type.kind == 'Proc') {
                    return RProcDecl.toStr(val);
                } else if (type.kind == 'Tuple') {
                    return RTuple.toStr(val);
                } else if (type.kind == 'Vector') {
                    return RVector.toStr(val);
                } else if (type.kind == 'FixedVector') {
                    return RVector.toStr(val, type.size);
                } else if (type.kind == 'RawPointer') {
                    return RRawPointer.toStr(val, type.mutability);
                } else if (type.kind == 'BorrowedRef') {
                    return RBorrowedRef.toStr(val);
                } else if (type.kind == 'BareFunction') {
                    return RBareFunctionDecl.toStr(val);
                } else {
                    return '';
                }
            }
        };
    }
]);


services.factory('RBareFunctionDecl', [
    '$injector', 'RArguments', 'RGenerics', 'underscore',
    function ($injector, RArguments, RGenerics, _) {
        return {
            toStr: function(bf) {
                var RType = $injector.get('RType'),
                    abi = bf.abi,
                    generics = RGenerics.toStr(bf.generics),
                    args = RArguments.toStr(bf.decl.inputs),
                    output = RType.toStr(bf.decl.output),
                    res = '';
                    
                if (_.isEmpty(generics)) {
                    res += '<' + generics + '> ';
                }

                return res + 'extern "' + abi + '" fn(' + args + ') -> ' + output;
            }
        };
    }
]);


services.factory('RBorrowedRef', [
    '$injector', 'RMutability', 'RLifetime', 'underscore',
    function ($injector, RMutability, RLifetime, _) {
        return {
            toStr: function(br) {
                var RType = $injector.get('RType'),
                    lifetime = '',
                    mut = RMutability.toStr(br.mutability),
                    type = RType.toStr(br.type_);
                
                if (!_.isUndefined(br.lifetime)) {
                    lifetime = RLifetime.toStr(br.lifetime);
                }
                
                return '&' + lifetime + ' ' + mut + ' ' + type;
            }
        };
    }
]);


services.factory('RRawPointer', [
    '$injector', 'RMutability', 'underscore',
    function ($injector, RMutability, _) {
        return {
            toStr: function(val, mut) {
                var RType = $injector.get('RType'),
                    m = RMutability.toStr(mut),
                    type = RType.toStr(val);
                return '*' + m + ' ' + type;
            }
        };
    }
]);


services.factory('RMutability', [
    function () {
        return {
            toStr: function(mut) {
                return mut == 'Mutable' ? 'mut' : 'const';
            }
        };
    }
]);


services.factory('RVector', [
    '$injector', 'underscore',
    function ($injector, _) {        
        return {
            toStr: function(vec, size) {
                var RType = $injector.get('RType'),
                    type = RType.toStr(vec);

                if (!_.isUndefined(size)) {
                    return '[' + type + ', ..' + size + ']';
                } else {
                    return '[' + type + ']';
                }
            }
        };
    }
]);


services.factory('RTuple', [
    '$injector', 'underscore',
    function ($injector, _) {
        return {
            toStr: function(types) {
                var RType = $injector.get('RType'),
                    tps = _.map(types, RType.toStr);

                if (_.isEmpty(tps)) {
                    return '()';
                } else if (_.size(tps) == 1) {
                    return '(' + tps[0] + ',)';
                } else {
                    return '(' + tps.join(',') + ')';
                }
            }
        };
    }
]);


services.factory('RProcDecl', [
    '$injector', 'RLifetime', 'RArguments', 'underscore',
    function ($injector, RLifetime, RArguments, _) {
        return {
            toStr: function(pd) {
                var RType = $injector.get('RType'),
                    lifetimes = _.map(pd.lifetimes, RLifetime.toStr),
                    args = RArguments.toStr(pd.decl.inputs),
                    output = RType.toStr(pd.decl.output);

                return 'proc(' + args + ') -> ' + output;
            }
        };
    }
]);


// TODO: bounds
services.factory('RClosureDecl', [
    '$injector', 'RLifetime', 'RArguments', 'underscore', 'isUnit',
    function ($injector, RLifetime, RArguments, _, isUnit) {
        return {
            toStr: function (cl) {
                var RType = $injector.get('RType'),
                    lifetimes = _.map(cl.lifetimes, RLifetime.toStr),
                    args = RArguments.toStr(cl.decl.inputs),
                    output = RType.toStr(cl.decl.output),
                    tail = isUnit(output) ? '' : ' -> ' + output;
                
                return '|' + args + '|' + tail;
            }
        };
    }
]);


services.factory('RArguments', [
    'RArgument', 'underscore',
    function (RArgument, _) {
        return {
            toStr: function (inputs) {
                return _.map(inputs, RArgument.toStr).join(', ');
            }
        };
    }
]);


services.factory('RArgument', [
    '$injector', 'underscore',
    function ($injector, _) {
        return {
            toStr: function(arg) {
                var RType = $injector.get('RType'),
                    type = RType.toStr(arg.type_),
                    name = arg.name;
                return name ? name + ': ' + type : type;
            }
        };
    }
]);

services.factory('RPath', [
    'RPathSegment', 'underscore',
    function (RPathSegment, _) {
        return {
            toStr: function (path) {
                return _.map(path.segments, function(seg) {
                    return RPathSegment.toStr(seg);
                }).join("::");
            }
        };
    }
]);


services.factory('RPathSegment', [
    '$injector', 'RLifetime', 'underscore',
    function ($injector, RLifetime, _) {
        return {
            toStr: function(seg) {
                var RType = $injector.get('RType'),
                    lifetimes = _.map(seg.lifetimes, RLifetime.toStr),
                    types = _.map(seg.types, RType.toStr),
                    res = lifetimes.concat(types);

                if (_.isEmpty(res)) {
                    return seg.name;
                } else {
                    return seg.name + '<' + res.join(', ') + '>';
                }
            }
        };
    }
]);

services.factory('RGenerics', [
    'RLifetime', 'RTyParam', 'underscore',
    function(RLifetime, RTyParam, _) {
        return {
            toStr: function(generics) {
                var lifts = [],
                    typeps = [],
                    i, res;

                for (i = 0; i < generics.lifetimes.length; i++) {
                    lifts.push(RLifetime.toStr(generics.lifetimes[i]));
                }

                for (i = 0; i < generics.type_params.length; i++) {
                    typeps.push(RTyParam.toStr(generics.type_params[i]));
                }

                res = lifts.concat(typeps);
                if (!_.isEmpty(res)) {
                    return res.join(', ');
                } else {
                    return '';
                }
            }
        };
    }
]);


// TODO: default value of type param
services.factory('RTyParam', [
    'RTyParamBound', 'underscore',
    function(RTyParamBound, _) {
        return {
            toStr: function(typ) {
                var bounds = [], s;
                _.each(typ.bounds, function(b) {
                    s = RTyParamBound.toStr(b);
                    if (s) {
                        bounds.push(s);
                    }
                });

                if (_.isEmpty(bounds)) {
                    return typ.name;
                } else {
                    return typ.name + ': ' + bounds.join(' + ');
                }
            }
        };
    }
]);

// TODO: unboxed fn bound
services.factory('RTyParamBound', [
    '$injector',
    function ($injector) {
        return {
            toStr: function(b) {
                var RType = $injector.get('RType');
                if (b.bound_type == 'RegionBound') {
                    return b.bound;
                } else if (b.bound_type == 'TraitBound') {
                    return RType.toStr(b.bound);
                } else {
                    return '';
                }
            }
        };
    }
]);

services.factory('RLifetime', [function() {
    return {
        toStr: function(lifetime) {
            return "'" + lifetime;
        }
    };
}]);

services.value('isUnit', function(t) {
    return t == '()';
});

}(angular, _));
