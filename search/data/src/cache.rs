extern crate rustdoc;
extern crate syntax;

use rustdoc::clean;
use rustdoc::fold::DocFolder;
use syntax::ast;
use std::collections::HashMap;
use serialize::json::{ToJson, Json, String, JsonObject, Object, List, Boolean
                      , Null};


#[deriving(Clone)]
pub struct Cache {
    pub public_item: Option<clean::Item>,
    pub typarams: HashMap<ast::DefId, String>,
}


impl Cache {
    pub fn generics(&mut self, generics: &clean::Generics) {
        println!("generics");
        for typ in generics.type_params.iter() {
            println!("Hello: {}", typ.name);
            self.typarams.insert(typ.did, typ.name.clone());
        }
    }
}


impl DocFolder for Cache {
    fn fold_item(&mut self, item: clean::Item) -> Option<clean::Item> {

        match item.inner {
            clean::StructItem(ref s)          => self.generics(&s.generics),
            clean::EnumItem(ref e)            => self.generics(&e.generics),
            clean::FunctionItem(ref f)        => self.generics(&f.generics),
            clean::TypedefItem(ref t)         => self.generics(&t.generics),
            clean::TraitItem(ref t)           => self.generics(&t.generics),
            clean::ImplItem(ref i)            => self.generics(&i.generics),
            clean::TyMethodItem(ref i)        => self.generics(&i.generics),
            clean::MethodItem(ref i)          => self.generics(&i.generics),
            clean::ForeignFunctionItem(ref f) => self.generics(&f.generics),
            _ => {}
        };

        self.fold_item_recur(item)
    }
}


pub trait JsonFolder {
    fn fold_json(&self, json: &Json) -> Json;
    fn fold_json_inner(&self, json: &Json, list: &mut Vec<Json>);
    fn fold_module(&self, json: &JsonObject, list: &mut Vec<Json>);
    fn fold_func(&self, json: &JsonObject, list: &mut Vec<Json>);
}


impl JsonFolder for Cache {
    fn fold_json(&self, json: &Json) -> Json {
        let mut list: Vec<Json> = vec![];
        self.fold_json_inner(json, &mut list);
        List(list)
    }

    fn fold_json_inner(&self, json: &Json, list: &mut Vec<Json>) {
        let inner_key = "inner".to_string();
        let kind_key = "kind".to_string();

        match *json {
            Object(ref jo) if jo.contains_key(&inner_key) => {
                let inner = jo.find(&inner_key).unwrap();
                let kind = inner.find(&kind_key).unwrap();
                let kind_str = kind.as_string().unwrap();

                match kind_str {
                    "module" => self.fold_module(jo, list),
                    "trait" => self.fold_module(jo, list),
                    "function" => self.fold_func(jo, list),
                    _ => {}
                }
            }
            _ => {}
        }
    }

    fn fold_module(&self, obj: &JsonObject, list: &mut Vec<Json>) {
        let inner_key = "inner".to_string();
        let value_key = "value".to_string();
        let items_key = "items".to_string();
        let items = obj.find(&inner_key).unwrap()
            .find(&value_key).unwrap()
            .find(&items_key).unwrap();

        match *items {
            List(ref l) => {
                for item in l.iter() {
                    self.fold_json_inner(item, list);
                }
            },
            _ => {}
        }
    }

    fn fold_func(&self, func: &JsonObject, list: &mut Vec<Json>) {
        list.push(Object(func.clone()));
    }
}

local_data_key!(pub cache_key: Cache)
