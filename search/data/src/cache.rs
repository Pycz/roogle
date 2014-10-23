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
    pub crate_name: String,
    pub public_item: Option<clean::Item>,
    pub typarams: HashMap<ast::DefId, String>,
}


impl Cache {
    pub fn generics(&mut self, generics: &clean::Generics) {
        for typ in generics.type_params.iter() {
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
    fn fold_json_inner(&self, json: &Json, list: &mut Vec<Json>,
                       module: &mut Vec<Json>);
    fn fold_module(&self, json: &JsonObject, list: &mut Vec<Json>,
                   module: &mut Vec<Json>);
    fn fold_func(&self, json: &JsonObject, list: &mut Vec<Json>,
                 module: &mut Vec<Json>);
}


impl JsonFolder for Cache {
    fn fold_json(&self, json: &Json) -> Json {
        let mut list: Vec<Json> = vec![];
        let mut module: Vec<Json> = vec![];
        self.fold_json_inner(json, &mut list, &mut module);
        List(list)
    }

    fn fold_json_inner(&self, json: &Json, list: &mut Vec<Json>,
                       module: &mut Vec<Json>) {
        let inner_key = "inner".to_string();
        let kind_key = "kind".to_string();

        match *json {
            Object(ref jo) if jo.contains_key(&inner_key) => {
                let inner = jo.find(&inner_key).unwrap();
                let kind = inner.find(&kind_key).unwrap();
                let kind_str = kind.as_string().unwrap();

                match kind_str {
                    "module" => self.fold_module(jo, list, module),
                    "trait" => self.fold_module(jo, list, module),
                    "function" => self.fold_func(jo, list, module),
                    _ => {}
                }
            }
            _ => {}
        }
    }

    fn fold_module(&self, obj: &JsonObject, list: &mut Vec<Json>,
                   module: &mut Vec<Json>) {
        let inner_key = "inner".to_string();
        let value_key = "value".to_string();
        let items_key = "items".to_string();
        let name_key = "name".to_string();
        let inner = obj.find(&inner_key).unwrap();
        let items = inner.find(&value_key).unwrap()
            .find(&items_key).unwrap();

        let module_name = match obj.find(&name_key) {
            Some(mn) => mn.as_string().unwrap().to_string(),
            None => String::new(),
        };

        if module_name.is_empty() && module.is_empty() {
            module.push(String(self.crate_name.clone()));
        } else {
            module.push(String(module_name));
        }

        match *items {
            List(ref l) => {
                for item in l.iter() {
                    self.fold_json_inner(item, list, module);
                }
            },
            _ => {}
        }

        let i = module.len() - 1;
        module.remove(i);
    }

    fn fold_func(&self, func: &JsonObject, list: &mut Vec<Json>,
                 module: &mut Vec<Json>) {
        let mut jo = func.clone();
        jo.insert("module".to_string(), List(module.clone()));
        list.push(Object(jo));
    }
}

local_data_key!(pub cache_key: Cache)
