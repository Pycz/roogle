use syntax::ast;
use syntax::ast_util;
use rustdoc::clean;
use rustdoc::doctree;
use rustdoc::html::format::{FnStyleSpace, RawMutableSpace, MutableSpace};
use serialize::json::{ToJson, Json, String, JsonObject, Object, List, Boolean
                      , Null};
use std::collections::TreeMap;
use cache;


pub struct RItem(pub clean::Item);
pub struct RItemEnum(pub clean::ItemEnum);
pub struct RFunction(pub clean::Function);
pub struct RStruct(pub clean::Struct);
pub struct REnum(pub clean::Enum);
pub struct RTypedef(pub clean::Typedef);
pub struct RTrait(pub clean::Trait);
pub struct RTyMethod(pub clean::TyMethod);
pub struct RImpl(pub clean::Impl);
pub struct RMethod(pub clean::Method);
pub struct RModule(pub clean::Module);
pub struct RTraitMethod(pub clean::TraitMethod);
pub struct RFnDecl(pub clean::FnDecl);
pub struct RGenerics(pub clean::Generics);
pub struct RTyParam(pub clean::TyParam);
pub struct RTyParamBound(pub clean::TyParamBound);
pub struct RArguments(pub clean::Arguments);
pub struct RArgument(pub clean::Argument);
pub struct RType(pub clean::Type);
pub struct RPath(pub clean::Path);
pub struct RPathSegment(pub clean::PathSegment);
pub struct RStructType(pub doctree::StructType);
pub struct RSelfTy(pub clean::SelfTy);
pub struct RClosureDecl(pub clean::ClosureDecl);
pub struct RBareFunctionDecl(pub clean::BareFunctionDecl);
pub struct RMutability(pub clean::Mutability);
pub struct RBorrowedRef(pub clean::Type);


impl ToJson for RItem {
    fn to_json(&self) -> Json {
        let RItem(ref item) = *self;
        let mut obj = TreeMap::new();
        if item.name.is_some() {
            obj.insert(from_str("name").unwrap(),
                       String(item.name.clone().unwrap()));
        }
        obj.insert(from_str("inner").unwrap(),
                   RItemEnum(item.inner.clone()).to_json());
        Object(obj)
    }
}


impl ToJson for RItemEnum {
    fn to_json(&self) -> Json {
        let RItemEnum(ref item) = *self;
        let mut obj = TreeMap::new();

        match *item {
            clean::FunctionItem(ref fun) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("function").unwrap()));
                obj.insert(from_str("value").unwrap(),
                           RFunction(fun.clone()).to_json());
                Object(obj)
            }
            clean::StructItem(ref st) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("struct").unwrap()));
                obj.insert(from_str("value").unwrap(),
                           RStruct(st.clone()).to_json());
                Object(obj)
            }
            clean::EnumItem(ref en) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("enum").unwrap()));
                obj.insert(from_str("value").unwrap(),
                           REnum(en.clone()).to_json());
                Object(obj)
            }
            clean::TypedefItem(ref td) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("typedef").unwrap()));
                obj.insert(from_str("value").unwrap(),
                           RTypedef(td.clone()).to_json());
                Object(obj)
            }
            clean::TraitItem(ref tr) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("trait").unwrap()));
                obj.insert(from_str("value").unwrap(),
                           RTrait(tr.clone()).to_json());
                Object(obj)
            }
            clean::TyMethodItem(ref tm) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("tymethod").unwrap()));
                obj.insert(from_str("value").unwrap(),
                           RTyMethod(tm.clone()).to_json());
                Object(obj)
            }
            clean::ImplItem(ref im) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("impl").unwrap()));
                obj.insert(from_str("value").unwrap(),
                           RImpl(im.clone()).to_json());
                Object(obj)
            }
            clean::MethodItem(ref m) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("method").unwrap()));
                obj.insert(from_str("value").unwrap(),
                           RMethod(m.clone()).to_json());
                Object(obj)
            }
            clean::ModuleItem(ref m) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("module").unwrap()));
                obj.insert(from_str("value").unwrap(),
                           RModule(m.clone()).to_json());
                Object(obj)
            }
            clean::StaticItem(ref st) => {
                println!("StaticItem");
                Null
            }
            clean::ViewItemItem(ref v) => {
                println!("ViewItemItem");
                Null
            }
            clean::StructFieldItem(ref sf) => {
                println!("StructFieldItem");
                Null
            }
            clean::VariantItem(ref v) => {
                println!("VariantItem");
                Null
            }
            clean::ForeignFunctionItem(ref f) => {
                println!("ForeignFunctionItem");
                Null
            }
            clean::ForeignStaticItem(ref st) => {
                println!("ForeignStaticItem");
                Null
            }
            clean::MacroItem(ref m) => {
                println!("MacroItem");
                Null
            }
            clean::PrimitiveItem(ref p) => {
                println!("PrimitiveItem");
                Null
            }
            clean::AssociatedTypeItem => {
                println!("AssociatedTypeItem");
                Null
            }
        }
    }
}


impl ToJson for RType {
    fn to_json(&self) -> Json {
        let RType(ref ty) = *self;
        let mut obj = TreeMap::new();

        match *ty {
            clean::TyParamBinder(id) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("TyParamBinder").unwrap()));
                let c = cache::cache_key.get().unwrap();
                obj.insert(from_str("value").unwrap(),
                           String(c.typarams[ast_util::local_def(id)].clone()));
            }
            clean::Generic(did) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("Generic").unwrap()));
                let c = cache::cache_key.get().unwrap();
                obj.insert(from_str("value").unwrap(),
                           String(c.typarams[did].clone()));
            }
            clean::ResolvedPath{ ref did, ref typarams, ref path } => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("ResolvedPath").unwrap()));
                obj.insert(from_str("value").unwrap(),
                           RPath(path.clone()).to_json());
            }
            clean::Self(..) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("Self").unwrap()));
                obj.insert(from_str("value").unwrap(),
                           String(String::from_str("Self")));
            }
            clean::Primitive(prim) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("Primitive").unwrap()));
                obj.insert(from_str("value").unwrap(),
                           String(String::from_str(prim.to_string())));
            }
            clean::Closure(box ref cl) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("Closure").unwrap()));
                obj.insert(from_str("value").unwrap(),
                           RClosureDecl(cl.clone()).to_json());
            }
            clean::Proc(box ref cl) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("Proc").unwrap()));
                obj.insert(from_str("value").unwrap(),
                           RClosureDecl(cl.clone()).to_json());
            }
            clean::BareFunction(box ref decl) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("BareFunction").unwrap()));
                obj.insert(from_str("value").unwrap(),
                           RBareFunctionDecl(decl.clone()).to_json());
            }
            clean::Tuple(ref typs) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("Tuple").unwrap()));
                obj.insert(from_str("value").unwrap(),
                           types_to_json(typs));
            }
            clean::Vector(box ref t) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("Vector").unwrap()));
                obj.insert(from_str("value").unwrap(),
                           RType(t.clone()).to_json());
            }
            clean::FixedVector(box ref t, ref s) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("FixedVector").unwrap()));
                obj.insert(from_str("size").unwrap(), String(s.clone()));
                obj.insert(from_str("value").unwrap(),
                           RType(t.clone()).to_json());
            }
            clean::Bottom => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("Bottom").unwrap()));
            }
            clean::RawPointer(ref m, box ref t) => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("RawPointer").unwrap()));
                obj.insert(from_str("mutability").unwrap(),
                           RMutability(m.clone()).to_json());
                obj.insert(from_str("value").unwrap(),
                           RType(t.clone()).to_json());
            }
            ref br @ clean::BorrowedRef{..} => {
                obj.insert(from_str("kind").unwrap(),
                           String(from_str("BorrowedRef").unwrap()));
                obj.insert(from_str("value").unwrap(),
                           RBorrowedRef(br.clone()).to_json());
            },
            clean::Unique(..) => {
                fail!("should have been cleaned")
            }
        };
        Object(obj)
    }
}


impl ToJson for RPath {
    fn to_json(&self) -> Json {
        let RPath(ref path) = *self;
        let mut obj = TreeMap::new();
        obj.insert(String::from_str("global"), Boolean(path.global));
        obj.insert(String::from_str("segments"), segments_to_json(&path.segments));
        Object(obj)
    }
}

impl ToJson for RPathSegment {
    fn to_json(&self) -> Json {
        let RPathSegment(ref ps) = *self;
        let mut obj = TreeMap::new();
        obj.insert(String::from_str("name"), String(ps.name.clone()));
        obj.insert(String::from_str("lifetimes"),
                   lifetimes_to_json(&ps.lifetimes));
        obj.insert(String::from_str("types"), types_to_json(&ps.types));
        Object(obj)
    }
}


impl ToJson for RArguments {
    fn to_json(&self) -> Json {
        let RArguments(ref args) = *self;
        let mut ret = vec![];
        for arg in args.values.iter() {
            ret.push(RArgument(arg.clone()).to_json());
        }
        List(ret)
    }
}


impl ToJson for RArgument {
    fn to_json(&self) -> Json {
        let RArgument(ref arg) = *self;
        let mut obj = TreeMap::new();
        obj.insert(from_str("type_").unwrap(),
                   RType(arg.type_.clone()).to_json());
        obj.insert(from_str("name").unwrap(), String(arg.name.clone()));
        Object(obj)
    }
}


impl ToJson for RFnDecl {
    fn to_json(&self) -> Json {
        let RFnDecl(ref decl) = *self;
        let mut obj = TreeMap::new();
        obj.insert(from_str("inputs").unwrap(),
                   RArguments(decl.inputs.clone()).to_json());
        obj.insert(from_str("output").unwrap(),
                   RType(decl.output.clone()).to_json());
        Object(obj)
    }
}


impl ToJson for RFunction {
    fn to_json(&self) -> Json {
        let RFunction(ref fun) = *self;
        let mut obj = TreeMap::new();
        obj.insert(from_str("decl").unwrap(),
                   RFnDecl(fun.decl.clone()).to_json());
        Object(obj)
    }
}


impl ToJson for RStruct {
    fn to_json(&self) -> Json {
        let RStruct(ref st) = *self;
        let mut obj = TreeMap::new();
        obj.insert(from_str("struct_type").unwrap(),
                   RStructType(st.struct_type.clone()).to_json());
        obj.insert(from_str("generics").unwrap(),
                   RGenerics(st.generics.clone()).to_json());
        obj.insert(from_str("fields").unwrap(), items_to_json(&st.fields));
        obj.insert(from_str("fields_stripped").unwrap(),
                   Boolean(st.fields_stripped));
        Object(obj)
    }
}


impl ToJson for REnum {
    fn to_json(&self) -> Json {
        let REnum(ref en) = *self;
        let mut obj = TreeMap::new();
        obj.insert(from_str("variants").unwrap(), items_to_json(&en.variants));
        obj.insert(from_str("generics").unwrap(),
                   RGenerics(en.generics.clone()).to_json());
        obj.insert(from_str("variants_stripped").unwrap(),
                   Boolean(en.variants_stripped));
        Object(obj)
    }
}


impl ToJson for RTypedef {
    fn to_json(&self) -> Json {
        let RTypedef(ref td) = *self;
        let mut obj = TreeMap::new();
        obj.insert(from_str("type_").unwrap(), RType(td.type_.clone()).to_json());
        obj.insert(from_str("generics").unwrap(),
                   RGenerics(td.generics.clone()).to_json());
        Object(obj)
    }
}


impl ToJson for RTrait {
    fn to_json(&self) -> Json {
        let RTrait(ref tr) = *self;
        let mut obj = TreeMap::new();
        obj.insert(from_str("items").unwrap(), trait_methods_to_json(&tr.items));
        obj.insert(from_str("generics").unwrap(),
                   RGenerics(tr.generics.clone()).to_json());
        obj.insert(from_str("bounds").unwrap(),
                   typaram_bounds_to_json(&tr.bounds));
        Object(obj)
    }
}


impl ToJson for RTyMethod {
    fn to_json(&self) -> Json {
        let RTyMethod(ref tm) = *self;
        let mut obj = TreeMap::new();
        obj.insert(from_str("decl").unwrap(), RFnDecl(tm.decl.clone()).to_json());
        obj.insert(from_str("generics").unwrap(),
                   RGenerics(tm.generics.clone()).to_json());
        Object(obj)
    }
}


impl ToJson for RImpl {
    fn to_json(&self) -> Json {
        let RImpl(ref im) = *self;
        let mut obj = TreeMap::new();
        obj.insert(from_str("generics").unwrap(),
                   RGenerics(im.generics.clone()).to_json());
        if im.trait_.is_some() {
            obj.insert(from_str("trait_").unwrap(),
                       RType(im.trait_.clone().unwrap()).to_json());
        }
        obj.insert(from_str("for_").unwrap(), RType(im.for_.clone()).to_json());
        obj.insert(from_str("items").unwrap(), items_to_json(&im.items));
        obj.insert(from_str("derived").unwrap(), Boolean(im.derived));
        Object(obj)
    }
}


impl ToJson for RMethod {
    fn to_json(&self) -> Json {
        let RMethod(ref m) = *self;
        let mut obj = TreeMap::new();
        obj.insert(from_str("generics").unwrap(),
                   RGenerics(m.generics.clone()).to_json());
        obj.insert(from_str("decl").unwrap(), RFnDecl(m.decl.clone()).to_json());
        obj.insert(from_str("self_").unwrap(),
                   RSelfTy(m.self_.clone()).to_json());
        Object(obj)
    }
}


impl ToJson for RModule {
    fn to_json(&self) -> Json {
        let RModule(ref m) = *self;
        let mut obj = TreeMap::new();
        obj.insert(from_str("items").unwrap(), items_to_json(&m.items));
        obj.insert(from_str("is_crate").unwrap(), Boolean(m.is_crate));
        Object(obj)
    }
}


impl ToJson for RSelfTy {
    fn to_json(&self) -> Json {
        let RSelfTy(ref m) = *self;
        match *m {
            clean::SelfStatic => String(from_str("SelfStatic").unwrap()),
            clean::SelfValue => String(from_str("SelfValue").unwrap()),
            clean::SelfBorrowed(..) => String(from_str("SelfBorrowed").unwrap()),
            clean::SelfExplicit(..) => String(from_str("SelfExplicit").unwrap()),
        }
    }
}


impl ToJson for RTraitMethod {
    fn to_json(&self) -> Json {
        let RTraitMethod(ref trm) = *self;
        let mut obj = TreeMap::new();
        match *trm {
            clean::RequiredMethod(ref item) => {
                obj.insert(from_str("method_type").unwrap(),
                           String(from_str("RequiredMethod").unwrap()));
                obj.insert(from_str("item").unwrap(),
                           RItem(item.clone()).to_json());
            }
            clean::ProvidedMethod(ref item) => {
                obj.insert(from_str("method_type").unwrap(),
                           String(from_str("ProvidedMethod").unwrap()));
                obj.insert(from_str("item").unwrap(),
                           RItem(item.clone()).to_json());
            }
            clean::TypeTraitItem(ref item) => {
                obj.insert(from_str("method_type").unwrap(),
                           String(from_str("TypeTraitItem").unwrap()));
                obj.insert(from_str("item").unwrap(),
                           RItem(item.clone()).to_json());
            }
        };
        Object(obj)
    }
}


impl ToJson for RGenerics {
    fn to_json(&self) -> Json {
        let RGenerics(ref gen) = *self;
        let mut obj = TreeMap::new();
        obj.insert(from_str("lifetimes").unwrap(),
                   lifetimes_to_json(&gen.lifetimes));
        obj.insert(from_str("type_params").unwrap(),
                   typarams_to_json(&gen.type_params));
        Object(obj)
    }
}


impl ToJson for RTyParam {
    fn to_json(&self) -> Json {
        let RTyParam(ref typ) = *self;
        let mut obj = TreeMap::new();
        obj.insert(from_str("name").unwrap(), String(typ.name.clone()));
        obj.insert(from_str("bounds").unwrap(),
                   typaram_bounds_to_json(&typ.bounds));
        match typ.default {
            Some(ref ty) => {
                obj.insert(from_str("default").unwrap(),
                           RType(ty.clone()).to_json());
            },
            None => {}
        }
        
        Object(obj)
    }
}


impl ToJson for RTyParamBound {
    fn to_json(&self) -> Json {
        let RTyParamBound(ref typb) = *self;
        match *typb {
            clean::RegionBound => String(from_str("RegionBound").unwrap()),
            clean::TraitBound(ref ty) => {
                RType(ty.clone()).to_json()
            }
        }

    }
}

impl ToJson for RStructType {
    fn to_json(&self) -> Json {
        let RStructType(ref stype) = *self;
        match *stype {
            doctree::Plain => String(from_str("Plain").unwrap()),
            doctree::Tuple => String(from_str("Tuple").unwrap()),
            doctree::Newtype => String(from_str("Newtype").unwrap()),
            doctree::Unit => String(from_str("Unit").unwrap())
        }
    }
}


impl ToJson for RClosureDecl {
    fn to_json(&self) -> Json {
        let RClosureDecl(ref decl) = *self;
        let mut obj = TreeMap::new();
        obj.insert(from_str("lifetimes").unwrap(),
                   lifetimes_to_json(&decl.lifetimes));
        obj.insert(from_str("decl").unwrap(),
                   RFnDecl(decl.decl.clone()).to_json());
        obj.insert(from_str("bounds").unwrap(),
                   typaram_bounds_to_json(&decl.bounds));
        Object(obj)
    }
}


impl ToJson for RBareFunctionDecl {
    fn to_json(&self) -> Json {
        let RBareFunctionDecl(ref decl) = *self;
        let mut obj = TreeMap::new();
        obj.insert(from_str("generics").unwrap(),
                   RGenerics(decl.generics.clone()).to_json());
        obj.insert(from_str("decl").unwrap(),
                   RFnDecl(decl.decl.clone()).to_json());
        obj.insert(from_str("abi").unwrap(), String(decl.abi.clone()));
        Object(obj)
    }
}


impl ToJson for RBorrowedRef {
    fn to_json(&self) -> Json {
        let RBorrowedRef(ref t) = *self;
        let mut obj = TreeMap::new();
        match *t {
            clean::BorrowedRef{ref lifetime, ref mutability, ref type_} => {
                if lifetime.is_some() {
                    obj.insert(from_str("lifetime").unwrap(),
                               String(format!("{}", lifetime.clone().unwrap())));
                }
                obj.insert(from_str("mutability").unwrap(),
                           RMutability(mutability.clone()).to_json());
                obj.insert(from_str("type_").unwrap(),
                           RType((**type_).clone()).to_json());
            },
            _ => {fail!("only BorrowedRef");}
        }
        Object(obj)
    }
}


impl ToJson for RMutability {
    fn to_json(&self) -> Json {
        let RMutability(ref m) = *self;
        match *m {
            clean::Mutable => String(from_str("Mutable").unwrap()),
            clean::Immutable => String(from_str("Immutable").unwrap()),
        }
    }
}


pub fn items_to_json(items: &Vec<clean::Item>) -> Json {
    let mut ret = vec![];
    for it in items.iter() {
        let item_json = RItem(it.clone()).to_json();
        let is_inner_null = match item_json.search(&"inner".to_string()) {
            Some(ref inner) => inner.is_null(),
            None => true
        };

        if !is_inner_null {
            ret.push(item_json);
        }
    }
    List(ret)
}


fn trait_methods_to_json(items: &Vec<clean::TraitMethod>) -> Json {
    let mut ret = vec![];
    for it in items.iter() {
        ret.push(RTraitMethod(it.clone()).to_json());
    }
    List(ret)
}


fn typarams_to_json(tys: &Vec<clean::TyParam>) -> Json {
    let mut ret = vec![];
    for t in tys.iter() {
        ret.push(RTyParam(t.clone()).to_json());
    }
    List(ret)
}


fn typaram_bounds_to_json(bounds: &Vec<clean::TyParamBound>) -> Json {
    let mut ret = vec![];
    for bound in bounds.iter() {
        ret.push(RTyParamBound(bound.clone()).to_json());
    }
    List(ret)
}

fn segments_to_json(segs: &Vec<clean::PathSegment>) -> Json {
    let mut ret = vec![];
    for s in segs.iter() {
        ret.push(RPathSegment(s.clone()).to_json());
    }
    List(ret)
}

fn lifetimes_to_json(ls: &Vec<clean::Lifetime>) -> Json {
    let mut ret = vec![];
    for l in ls.iter() {
        ret.push(String(format!("{}", l)));
    }
    List(ret)
}

fn types_to_json(ts: &Vec<clean::Type>) -> Json {
    let mut ret = vec![];
    for t in ts.iter() {
        ret.push(RType(t.clone()).to_json());
    }
    List(ret)
}
